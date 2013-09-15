package Speedy.launcher.auth.yggdrasil;

public class RefreshRequest
{

    private String clientToken;
    private String accessToken;
    private GameProfile selectedProfile;

    public RefreshRequest(YggdrasilAuthenticationService authenticationService)
    {
        this(authenticationService, null);
    }

    public RefreshRequest(YggdrasilAuthenticationService authenticationService, GameProfile profile)
    {
        clientToken = authenticationService.getClientToken();
        accessToken = authenticationService.getAccessToken();
        selectedProfile = profile;
    }
}
