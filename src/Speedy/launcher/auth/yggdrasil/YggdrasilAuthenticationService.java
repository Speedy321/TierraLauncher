package Speedy.launcher.auth.yggdrasil;

import Speedy.launcher.Launcher;
import Speedy.launcher.auth.AuthenticationException;
import Speedy.launcher.auth.BaseAuthenticationService;
import Speedy.launcher.auth.InvalidCredentialsException;
import Speedy.launcher.utils.StringUtils;
import Speedy.launcher.utils.Utils;
import Speedy.launcher.auth.GameProfile;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class YggdrasilAuthenticationService extends BaseAuthenticationService
{

    private static final URL ROUTE_AUTHENTICATE = Utils.constantURL("https://authserver.mojang.com/authenticate");
    private static final URL ROUTE_REFRESH = Utils.constantURL("https://authserver.mojang.com/refresh");
    private final Gson gson = new Gson();
    private final Agent agent;
    private GameProfile profiles[];
    private String accessToken;
    private boolean isOnline;

    public YggdrasilAuthenticationService()
    {
        agent = Agent.MINECRAFT;
    }

    public boolean canLogIn()
    {
        return !canPlayOnline() && StringUtils.isNotBlank(getUsername()) && (StringUtils.isNotBlank(getPassword()) || StringUtils.isNotBlank(getAccessToken()));
    }

    public void logIn()
        throws AuthenticationException
    {
        if(StringUtils.isBlank(getUsername()))
        {
            throw new InvalidCredentialsException("Invalid username");
        }
        if(StringUtils.isNotBlank(getAccessToken()))
        {
            logInWithToken();
        } else
        if(StringUtils.isNotBlank(getPassword()))
        {
            logInWithPassword();
        } else
        {
            throw new InvalidCredentialsException("Invalid password");
        }
    }

    protected void logInWithPassword()
        throws AuthenticationException
    {
        if(StringUtils.isBlank(getUsername()))
        {
            throw new InvalidCredentialsException("Invalid username");
        }
        if(StringUtils.isBlank(getPassword()))
        {
            throw new InvalidCredentialsException("Invalid password");
        }
        Launcher.getInstance().println("Logging in with username & password");
        AuthenticationRequest request = new AuthenticationRequest(this, getPassword());
        AuthenticationResponse response = (AuthenticationResponse)makeRequest(ROUTE_AUTHENTICATE, request, /*net/aetherteam/aether/launcher/authentication/yggdrasil/AuthenticationResponse*/);
        if(!response.getClientToken().equals(getClientToken()))
        {
            throw new AuthenticationException("Server requested we change our client token. Don't know how to handle this!");
        } else
        {
            accessToken = response.getAccessToken();
            profiles = response.getAvailableProfiles();
            setSelectedProfile(response.getSelectedProfile());
            return;
        }
    }

    protected void logInWithToken()
        throws AuthenticationException
    {
        if(StringUtils.isBlank(getUsername()))
        {
            throw new InvalidCredentialsException("Invalid username");
        }
        if(StringUtils.isBlank(getAccessToken()))
        {
            throw new InvalidCredentialsException("Invalid access token");
        }
        Launcher.getInstance().println("Logging in with access token");
        RefreshRequest request = new RefreshRequest(this);
        RefreshResponse response = (RefreshResponse)makeRequest(ROUTE_REFRESH, request, Speedy/launcher/auth/yggdrasil/RefreshResponse);
        if(!response.getClientToken().equals(getClientToken()))
        {
            throw new AuthenticationException("Server requested we change our client token. Don't know how to handle this!");
        } else
        {
            accessToken = response.getAccessToken();
            profiles = response.getAvailableProfiles();
            setSelectedProfile(response.getSelectedProfile());
            return;
        }
    }

    protected Response makeRequest(URL url, Object input, Class classOfT)
        throws AuthenticationException
    {
        Response result;
        String jsonResult = Utils.performPost(url, gson.toJson(input), Launcher.getInstance().getProxy(), "application/json", true);
        result = (Response)gson.fromJson(jsonResult, classOfT);
        if(result == null)
        {
            return null;
        }
        if(StringUtils.isNotBlank(result.getError()))
        {
            if(result.getError().equals("ForbiddenOperationException"))
            {
                throw new InvalidCredentialsException(result.getErrorMessage());
            } else
            {
                throw new AuthenticationException(result.getErrorMessage());
            }
        }
        isOnline = true;
        return result;
        IOException e;
        e;
        throw new AuthenticationException("Cannot contact authentication server", e);
        e;
        throw new AuthenticationException("Cannot contact authentication server", e);
        e;
        throw new AuthenticationException("Cannot contact authentication server", e);
    }

    public void logOut()
    {
        super.logOut();
        accessToken = null;
        profiles = null;
        isOnline = false;
    }

    public GameProfile[] getAvailableProfiles()
    {
        return profiles;
    }

    public String[] getAvailableProfileNames()
    {
        if(profiles == null)
        {
            return (new String[] {
                getSelectedProfile().getName()
            });
        }
        String profileNames[] = new String[profiles.length];
        for(int i = 0; i < profileNames.length; i++)
        {
            profileNames[i] = profiles[i].getName();
        }

        return profileNames;
    }

    public void selectGameProfile(String selectProfile)
    {
        if(profiles == null)
        {
            return;
        }
        GameProfile agameprofile[];
        int j = (agameprofile = profiles).length;
        for(int i = 0; i < j; i++)
        {
            GameProfile profile = agameprofile[i];
            if(profile.equals(selectProfile))
            {
                setSelectedProfile(profile);
            }
        }

    }

    public boolean isLoggedIn()
    {
        return StringUtils.isNotBlank(accessToken);
    }

    public boolean canPlayOnline()
    {
        return isLoggedIn() && getSelectedProfile() != null && isOnline;
    }

    public void loadFromStorage(Map credentials)
    {
        super.loadFromStorage(credentials);
        accessToken = (String)credentials.get("accessToken");
    }

    public Map saveForStorage()
    {
        Map result = super.saveForStorage();
        if(!shouldRememberMe())
        {
            return result;
        }
        if(StringUtils.isNotBlank(getAccessToken()))
        {
            result.put("accessToken", getAccessToken());
        }
        return result;
    }

    public String getSessionToken()
    {
        if(isLoggedIn() && getSelectedProfile() != null && canPlayOnline())
        {
            return String.format("token:%s:%s", new Object[] {
                getAccessToken(), getSelectedProfile().getId()
            });
        } else
        {
            return null;
        }
    }

    public String getAccessToken()
    {
        return accessToken;
    }

    public String getClientToken()
    {
        return Launcher.getInstance().getClientToken().toString();
    }

    public Agent getAgent()
    {
        return agent;
    }

    public String toString()
    {
        return (new StringBuilder("YggdrasilAuthenticationService{agent=")).append(agent).append(", profiles=").append(Arrays.toString(profiles)).append(", selectedProfile=").append(getSelectedProfile()).append(", sessionToken='").append(getSessionToken()).append('\'').append(", username='").append(getUsername()).append('\'').append(", isLoggedIn=").append(isLoggedIn()).append(", canPlayOnline=").append(canPlayOnline()).append(", accessToken='").append(accessToken).append('\'').append(", clientToken='").append(getClientToken()).append('\'').append('}').toString();
    }

}
