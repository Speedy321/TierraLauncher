package Speedy.launcher.auth.yggdrasil;

public class RefreshResponse extends Response
{

    private String accessToken;
    private String clientToken;
    private GameProfile selectedProfile;
    private GameProfile availableProfiles[];

    public RefreshResponse()
    {
    }

    public String getAccessToken()
    {
        return accessToken;
    }

    public String getClientToken()
    {
        return clientToken;
    }

    public GameProfile[] getAvailableProfiles()
    {
        return availableProfiles;
    }

    public GameProfile getSelectedProfile()
    {
        return selectedProfile;
    }
}
