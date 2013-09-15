package Speedy.launcher;

import java.io.File;
import java.io.PrintStream;
import java.net.Proxy;
import java.util.UUID;
import java.util.concurrent.ThreadPoolExecutor;

import Speedy.launcher.auth.AuthenticationException;
import Speedy.launcher.auth.yggdrasil.YggdrasilAuthenticationService;

public class Launcher
{

    static Launcher instance;
    private GameLauncher gameLauncher;
    private ProfileManager profileManager;
    private Proxy proxy;
    protected File baseDirectory;
    private UUID clientToken;

    public Launcher()
    {
        gameLauncher = new GameLauncher();
        proxy = Proxy.NO_PROXY;
        clientToken = UUID.randomUUID();
        baseDirectory = OperatingSystem.getCurrentPlatform().getWorkingDirectory();
        instance = this;
        profileManager = new ProfileManager();
        profileManager.loadProfile();
 //       refreshVersions();
    }

 /*   public void refreshVersions()
    {
        versionManager.getExecutorService().submit(new  Object()     /* anonymous class not found */ /* class _anm1 {} );
    } */

    public void login(String username, String password)
    {
        YggdrasilAuthenticationService auth = profileManager.getAuthenticationService();
        auth.setUsername(username);
        auth.setPassword(password);
        try
        {
            auth.logIn();
            profileManager.saveProfile();
        }
        catch(AuthenticationException e)
        {
            println("Invalid creditentials");
        }
    }

    public void println(String string)
    {
        System.out.println(string);
    }

    public void println(String string, Throwable e)
    {
        System.out.println((new StringBuilder("exception, ")).append(string).toString());
    }

    public static Launcher getInstance()
    {
        return instance;
    }

    public File getBaseDirectory()
    {
        return baseDirectory;
    }

 /*   public VersionManager getVersionManager()
    {
        return versionManager;
    } */
 
    public Proxy getProxy()
    {
        return proxy;
    }

    public UUID getClientToken()
    {
        return clientToken;
    }

    public void setClientToken(UUID clientToken)
    {
        this.clientToken = clientToken;
    }

    public ProfileManager getProfileManager()
    {
        return profileManager;
    }

    public GameLauncher getGameLauncher()
    {
        return gameLauncher;
    }

}
