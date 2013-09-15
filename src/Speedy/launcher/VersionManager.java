package Speedy.launcher;

import java.io.File;
import java.io.IOException;
import java.net.Proxy;
import java.net.URL;
import java.util.*;
import java.util.concurrent.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

public class VersionManager
{

    private final ThreadPoolExecutor executorService;
    private LocalVersionList localVersionList;
    private RemoteVersionList remoteVersionList;

    public VersionManager(LocalVersionList localVersionList, RemoteVersionList remoteVersionList)
    {
        executorService = new ThreadPoolExecutor(8, 8, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue());
        this.localVersionList = localVersionList;
        this.remoteVersionList = remoteVersionList;
    }

    public ThreadPoolExecutor getExecutorService()
    {
        return executorService;
    }

    public void refreshVersions()
        throws IOException
    {
        localVersionList.refreshVersions();
        remoteVersionList.refreshVersions();
        if(localVersionList instanceof LocalVersionList)
        {
            for(Iterator iterator = remoteVersionList.getVersions().iterator(); iterator.hasNext();)
            {
                Version version = (Version)iterator.next();
                String id = version.getId();
                if(localVersionList.getVersion(id) != null)
                {
                    localVersionList.removeVersion(id);
                    localVersionList.addVersion(remoteVersionList.getCompleteVersion(id));
                    localVersionList.saveVersion(localVersionList.getCompleteVersion(id));
                }
            }

        }
    }

    public String[] getVersions()
    {
        VersionList versionList = ((VersionList) (remoteVersionList.getVersions().isEmpty() ? ((VersionList) (localVersionList)) : ((VersionList) (remoteVersionList))));
        String versionIds[] = new String[versionList.getVersions().size()];
        for(int i = 0; i < versionList.getVersions().size(); i++)
        {
            versionIds[i] = ((Version)versionList.getVersions().get(i)).getId();
        }

        return versionIds;
    }

    public VersionSyncInfo getVersionSyncInfo(Version version)
    {
        return getVersionSyncInfo(version.getId());
    }

    public VersionSyncInfo getVersionSyncInfo(String name)
    {
        return getVersionSyncInfo(localVersionList.getVersion(name), remoteVersionList.getVersion(name));
    }

    public VersionSyncInfo getVersionSyncInfo(Version localVersion, Version remoteVersion)
    {
        boolean installed = localVersion != null;
        boolean upToDate = installed;
        if(installed && remoteVersion != null)
        {
            upToDate = !remoteVersion.getUpdatedTime().after(localVersion.getUpdatedTime());
        }
        if(localVersion instanceof CompleteVersion)
        {
            upToDate &= localVersionList.hasAllFiles((CompleteVersion)localVersion, OperatingSystem.getCurrentPlatform());
        }
        return new VersionSyncInfo(localVersion, remoteVersion, installed, upToDate);
    }

    public List getInstalledVersions()
    {
        List result = new ArrayList();
        for(Iterator iterator = localVersionList.getVersions().iterator(); iterator.hasNext();)
        {
            Version version = (Version)iterator.next();
            if(version.getUpdatedTime() != null)
            {
                VersionSyncInfo syncInfo = getVersionSyncInfo(version, remoteVersionList.getVersion(version.getId()));
                result.add(syncInfo);
            }
        }

        return result;
    }

    public VersionList getRemoteVersionList()
    {
        return remoteVersionList;
    }

    public VersionList getLocalVersionList()
    {
        return localVersionList;
    }

    public CompleteVersion getLatestCompleteVersion(VersionSyncInfo syncInfo)
        throws IOException
    {
        if(syncInfo.getLatestSource() == net.aetherteam.aether.launcher.version.VersionSyncInfo.VersionSource.REMOTE)
        {
            CompleteVersion result = null;
            IOException exception = null;
            try
            {
                result = remoteVersionList.getCompleteVersion(syncInfo.getLatestVersion());
            }
            catch(IOException e)
            {
                exception = e;
                try
                {
                    result = localVersionList.getCompleteVersion(syncInfo.getLatestVersion());
                }
                catch(IOException ioexception) { }
            }
            if(result != null)
            {
                return result;
            } else
            {
                throw exception;
            }
        } else
        {
            return localVersionList.getCompleteVersion(syncInfo.getLatestVersion());
        }
    }

    public DownloadJob downloadVersion(VersionSyncInfo syncInfo, DownloadJob job)
        throws IOException
    {
        CompleteVersion version = getLatestCompleteVersion(syncInfo);
        File baseDirectory = localVersionList.getBaseDirectory();
        Proxy proxy = remoteVersionList.getProxy();
        job.addDownloadables(version.getRequiredDownloadables(OperatingSystem.getCurrentPlatform(), proxy, baseDirectory, false));
        String jarFile = (new StringBuilder("versions/")).append(version.getMinecraftVersion()).append("/").append(version.getMinecraftVersion()).append(".jar").toString();
        job.addDownloadables(new Downloadable[] {
            new Downloadable(proxy, new URL((new StringBuilder("https://s3.amazonaws.com/Minecraft.Download/")).append(jarFile).toString()), new File(baseDirectory, jarFile), false)
        });
        return job;
    }

    public Set getResourceFiles(Proxy proxy, File baseDirectory)
    {
        Set result = new HashSet();
        try
        {
            URL resourceUrl = new URL("https://s3.amazonaws.com/Minecraft.Resources/");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(resourceUrl.openStream());
            NodeList nodeLst = doc.getElementsByTagName("Contents");
            long start = System.nanoTime();
            for(int i = 0; i < nodeLst.getLength(); i++)
            {
                Node node = nodeLst.item(i);
                if(node.getNodeType() != 1)
                {
                    continue;
                }
                Element element = (Element)node;
                String key = element.getElementsByTagName("Key").item(0).getChildNodes().item(0).getNodeValue();
                String etag = element.getElementsByTagName("ETag") == null ? "-" : element.getElementsByTagName("ETag").item(0).getChildNodes().item(0).getNodeValue();
                long size = Long.parseLong(element.getElementsByTagName("Size").item(0).getChildNodes().item(0).getNodeValue());
                if(size <= 0L)
                {
                    continue;
                }
                File file = new File(baseDirectory, (new StringBuilder("assets/")).append(key).toString());
                if(etag.length() > 1)
                {
                    etag = Downloadable.getEtag(etag);
                    if(file.isFile() && file.length() == size)
                    {
                        String localMd5 = Downloadable.getMD5(file);
                        if(localMd5.equals(etag))
                        {
                            continue;
                        }
                    }
                }
                Downloadable downloadable = new Downloadable(proxy, new URL((new StringBuilder("https://s3.amazonaws.com/Minecraft.Resources/")).append(key).toString()), file, false);
                downloadable.setExpectedSize(size);
                result.add(downloadable);
            }

            long end = System.nanoTime();
            long delta = end - start;
            Launcher.getInstance().println((new StringBuilder("Delta time to compare resources: ")).append(delta / 0xf4240L).append(" ms ").toString());
        }
        catch(Exception ex)
        {
            Launcher.getInstance().println("Couldn't download resources", ex);
        }
        return result;
    }
}