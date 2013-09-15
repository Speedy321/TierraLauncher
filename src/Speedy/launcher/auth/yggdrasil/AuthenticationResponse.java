package Speedy.launcher.auth.yggdrasil;

public class AuthenticationResponse extends Response
{

    private String accessToken;
    private String clientToken;
    private GameProfile selectedProfile;
    private GameProfile availableProfiles[];

    public AuthenticationResponse()
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
