package Speedy.launcher;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import Speedy.launcher.auth.AuthenticationService;
import Speedy.launcher.utils.StrSubstitutor;

public class GameLauncher implements DownloadListener, JavaProcessRunnable, Runnable
{

    private CompleteVersion version;
    private File nativeDir;

    public GameLauncher()
    {
    }

    public void playGame()
    {
        Thread thread = new Thread(this);
        thread.start();
    }

    public void run()
    {
        Launcher.getInstance().println("Getting syncinfo for selected version");
        String selectedVersion = Launcher.getInstance().getProfileManager().getAuthenticationService().getSelectedVersion();
        if(selectedVersion == null)
        {
            selectedVersion = Launcher.getInstance().getVersionManager().getVersions()[0];
        }
        VersionSyncInfo syncInfo = Launcher.getInstance().getVersionManager().getVersionSyncInfo(selectedVersion);
        Launcher.getInstance().println("Queueing library & version downloads");
        try
        {
            version = Launcher.getInstance().getVersionManager().getLatestCompleteVersion(syncInfo);
        }
        catch(IOException e)
        {
            Launcher.getInstance().println((new StringBuilder("Couldn't get complete version info for ")).append(syncInfo.getLatestVersion()).toString(), e);
            return;
        }
        if(!version.appliesToCurrentEnvironment())
        {
            String reason = version.getIncompatibilityReason();
            if(reason == null)
            {
                reason = "This version is incompatible with your computer. Please try another one by going \n into Edit Profile and selecting one through the dropdown. Sorry!";
            }
            Launcher.getInstance().println((new StringBuilder("Version ")).append(version.getId()).append(" is incompatible with current environment: ").append(reason).toString());
            return;
        }
        if(version.getMinimumLauncherVersion() > 5)
        {
            Launcher.getInstance().println((new StringBuilder("An update to your launcher is available and is required to play ")).append(version.getId()).append(". Please restart your launcher.").toString());
            return;
        }
        if(!syncInfo.isInstalled())
        {
            try
            {
                net.aetherteam.aether.launcher.version.VersionList localVersionList = Launcher.getInstance().getVersionManager().getLocalVersionList();
                ((LocalVersionList)localVersionList).saveVersion(version);
                Launcher.getInstance().println((new StringBuilder("Installed ")).append(syncInfo.getLatestVersion()).toString());
            }
            catch(IOException e)
            {
                Launcher.getInstance().println((new StringBuilder("Couldn't save version info to install ")).append(syncInfo.getLatestVersion()).toString(), e);
                return;
            }
        }
        try
        {
            DownloadJob job = new DownloadJob("Version & Libraries", false, this);
            Launcher.getInstance().getVersionManager().downloadVersion(syncInfo, job);
            job.addDownloadables(Launcher.getInstance().getVersionManager().getResourceFiles(Launcher.getInstance().getProxy(), Launcher.getInstance().getBaseDirectory()));
            job.startDownloading(Launcher.getInstance().getVersionManager().getExecutorService());
        }
        catch(IOException e)
        {
            Launcher.getInstance().println((new StringBuilder("Couldn't get version info for ")).append(syncInfo.getLatestVersion()).toString(), e);
            return;
        }
    }

    protected void launchGame()
    {
        Launcher.getInstance().println("Copying mods");
        for(Iterator iterator = version.getMods().iterator(); iterator.hasNext();)
        {
            Mod mod = (Mod)iterator.next();
            File source = new File(Launcher.instance.getBaseDirectory(), mod.getVersionPath(version));
            File target = new File(Launcher.instance.getBaseDirectory(), mod.getPath());
            try
            {
                target.getParentFile().mkdirs();
                target.createNewFile();
            }
            catch(IOException e1)
            {
                e1.printStackTrace();
            }
            Launcher.getInstance().println((new StringBuilder("Copying ")).append(source.toString()).append(" to ").append(target.toString()).toString());
            try
            {
                Files.copy(source.toPath(), target.toPath(), new CopyOption[] {
                    StandardCopyOption.REPLACE_EXISTING
                });
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }

        Launcher.getInstance().println("Launching game");
        if(version == null)
        {
            Launcher.getInstance().println("Aborting launch; version is null?");
            return;
        }
        nativeDir = new File(Launcher.getInstance().getBaseDirectory(), (new StringBuilder("versions/")).append(version.getMinecraftVersion()).append("/").append(version.getMinecraftVersion()).append("-natives").toString());
        if(!nativeDir.isDirectory())
        {
            nativeDir.mkdirs();
        }
        Launcher.getInstance().println((new StringBuilder("Unpacking natives to ")).append(nativeDir).toString());
        try
        {
            unpackNatives(version, nativeDir);
        }
        catch(IOException e)
        {
            Launcher.getInstance().println("Couldn't unpack natives!", e);
            return;
        }
        File gameDirectory = Launcher.getInstance().getBaseDirectory();
        Launcher.getInstance().println((new StringBuilder("Launching in ")).append(gameDirectory).toString());
        if(!gameDirectory.exists())
        {
            if(!gameDirectory.mkdirs())
            {
                Launcher.getInstance().println("Aborting launch; couldn't create game directory");
            }
        } else
        if(!gameDirectory.isDirectory())
        {
            Launcher.getInstance().println("Aborting launch; game directory is not actually a directory");
            return;
        }
        JavaProcessLauncher processLauncher = new JavaProcessLauncher(null, new String[0]);
        processLauncher.directory(gameDirectory);
        File assetsDirectory = new File(Launcher.getInstance().getBaseDirectory(), "assets");
        if(OperatingSystem.getCurrentPlatform().equals(OperatingSystem.OSX))
        {
            processLauncher.addCommands(new String[] {
                (new StringBuilder("-Xdock:icon=")).append((new File(assetsDirectory, "icons/minecraft.icns")).getAbsolutePath()).toString(), "-Xdock:name=Minecraft"
            });
        }
        boolean is32Bit = "32".equals(System.getProperty("sun.arch.data.model"));
        String defaultArgument = is32Bit ? "-Xmx512M" : "-Xmx1G";
        processLauncher.addSplitCommands(defaultArgument);
        processLauncher.addCommands(new String[] {
            (new StringBuilder("-Djava.library.path=")).append(nativeDir.getAbsolutePath()).toString()
        });
        processLauncher.addCommands(new String[] {
            "-cp", constructClassPath(version)
        });
        processLauncher.addCommands(new String[] {
            version.getMainClass()
        });
        AuthenticationService auth = Launcher.getInstance().getProfileManager().getAuthenticationService();
        String args[] = getMinecraftArguments(version, auth.getSelectedProfile().getName(), gameDirectory, assetsDirectory, auth);
        processLauncher.addCommands(args);
        if(auth == null || auth.getSelectedProfile() == null)
        {
            processLauncher.addCommands(new String[] {
                "--demo"
            });
        }
        try
        {
            List parts = processLauncher.getFullCommands();
            StringBuilder full = new StringBuilder();
            boolean first = true;
            for(Iterator iterator1 = parts.iterator(); iterator1.hasNext();)
            {
                String part = (String)iterator1.next();
                if(!first)
                {
                    full.append(" ");
                }
                full.append(part);
                first = false;
            }

            Launcher.getInstance().println((new StringBuilder("Running ")).append(full.toString()).toString());
            JavaProcess process = processLauncher.start();
            process.safeSetExitRunnable(this);
        }
        catch(IOException e)
        {
            Launcher.getInstance().println("Couldn't launch game", e);
            return;
        }
        Launcher.instance.getProfileManager().saveProfile();
        LauncherDisplay.instance.terminate();
    }

    private String[] getMinecraftArguments(CompleteVersion version, String player, File gameDirectory, File assetsDirectory, AuthenticationService authentication)
    {
        if(version.getMinecraftArguments() == null)
        {
            Launcher.getInstance().println("Can't run version, missing minecraftArguments");
            return null;
        }
        Map map = new HashMap();
        StrSubstitutor substitutor = new StrSubstitutor(map);
        String split[] = version.getMinecraftArguments().split(" ");
        map.put("auth_username", authentication.getUsername());
        map.put("auth_session", authentication.getSessionToken() != null || !authentication.canPlayOnline() ? ((Object) (authentication.getSessionToken())) : "-");
        if(authentication.getSelectedProfile() != null)
        {
            map.put("auth_player_name", authentication.getSelectedProfile().getName());
            map.put("auth_uuid", authentication.getSelectedProfile().getId());
        } else
        {
            map.put("auth_player_name", "Player");
            map.put("auth_uuid", (new UUID(0L, 0L)).toString());
        }
        map.put("profile_name", player);
        map.put("version_name", version.getMinecraftVersion());
        map.put("game_directory", gameDirectory.getAbsolutePath());
        map.put("game_assets", assetsDirectory.getAbsolutePath());
        for(int i = 0; i < split.length; i++)
        {
            split[i] = substitutor.replace(split[i]);
        }

        return split;
    }

    private String constructClassPath(CompleteVersion version)
    {
        StringBuilder result = new StringBuilder();
        Collection classPath = version.getClassPath(OperatingSystem.getCurrentPlatform(), Launcher.getInstance().getBaseDirectory());
        String separator = System.getProperty("path.separator");
        File file;
        for(Iterator iterator = classPath.iterator(); iterator.hasNext(); result.append(file.getAbsolutePath()))
        {
            file = (File)iterator.next();
            if(!file.isFile())
            {
                throw new RuntimeException((new StringBuilder("Classpath file not found: ")).append(file).toString());
            }
            if(result.length() > 0)
            {
                result.append(separator);
            }
        }

        return result.toString();
    }
/*
    private void unpackNatives(CompleteVersion version, File targetDir)
        throws IOException
    {
        OperatingSystem os;
        Iterator iterator;
        os = OperatingSystem.getCurrentPlatform();
        Collection libraries = version.getRelevantLibraries();
        iterator = libraries.iterator();
          goto _L1
_L7:
        ZipFile zip;
        ExtractRules extractRules;
        Library library = (Library)iterator.next();
        Map nativesPerOs = library.getNatives();
        if(nativesPerOs == null || nativesPerOs.get(os) == null)
        {
            continue; /* Loop/switch isn't completed */ /*
        }
        File file = new File(Launcher.getInstance().getBaseDirectory(), (new StringBuilder("libraries/")).append(library.getArtifactPath((String)nativesPerOs.get(os))).toString());
        zip = new ZipFile(file);
        extractRules = library.getExtractRules();
        Enumeration entries = zip.entries();
          goto _L2
_L5:
        BufferedInputStream inputStream;
        byte buffer[];
        FileOutputStream outputStream;
        BufferedOutputStream bufferedOutputStream;
        ZipEntry entry = (ZipEntry)entries.nextElement();
        if(extractRules != null && !extractRules.shouldExtract(entry.getName()))
        {
            continue; /* Loop/switch isn't completed */ /*
        }
        File targetFile = new File(targetDir, entry.getName());
        if(targetFile.getParentFile() != null)
        {
            targetFile.getParentFile().mkdirs();
        }
        if(entry.isDirectory())
        {
            continue; /* Loop/switch isn't completed */ /*
        }
        inputStream = new BufferedInputStream(zip.getInputStream(entry));
        buffer = new byte[2048];
        outputStream = new FileOutputStream(targetFile);
        bufferedOutputStream = new BufferedOutputStream(outputStream);
        int length;
        while((length = inputStream.read(buffer, 0, buffer.length)) != -1) 
        {
            bufferedOutputStream.write(buffer, 0, length);
        }
          goto _L3
        Exception exception;
        exception;
        Downloadable.closeSilently(bufferedOutputStream);
        Downloadable.closeSilently(outputStream);
        Downloadable.closeSilently(inputStream);
        throw exception;
_L3:
        Downloadable.closeSilently(bufferedOutputStream);
        Downloadable.closeSilently(outputStream);
        Downloadable.closeSilently(inputStream);
_L2:
        if(entries.hasMoreElements()) goto _L5; else goto _L4
_L4:
        break MISSING_BLOCK_LABEL_339;
        Exception exception1;
        exception1;
        zip.close();
        throw exception1;
        zip.close();
_L1:
        if(iterator.hasNext()) goto _L7; else goto _L6
_L6:
    }

    public void onDownloadJobProgressChanged(DownloadJob paramDownloadJob)
    {
        LoadingForm.instance.getProgressbar().setProgress(paramDownloadJob.getProgress());
    }

    public void onJavaProcessEnded(JavaProcess javaprocess)
    {
    }

    public void onDownloadJobFinished(DownloadJob job)
    {
        LoadingForm.instance.getProgressbar().setProgress(1.0F);
        if(job.getFailures() > 0)
        {
            Launcher.getInstance().println((new StringBuilder("Job '")).append(job.getName()).append("' finished with ").append(job.getFailures()).append(" failure(s)!").toString());
        } else
        {
            Launcher.getInstance().println((new StringBuilder("Job '")).append(job.getName()).append("' finished successfully").toString());
            try
            {
                launchGame();
            }
            catch(Throwable ex)
            {
                Launcher.getInstance().println("Fatal error launching game. Report this to http://mojang.atlassian.net please!", ex);
            }
        }
    } */
} 
