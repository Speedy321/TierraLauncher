package Speedy.launcher.auth;

import java.io.File;
import java.util.Map;

public interface AuthenticationService
{

    public static final String STORAGE_KEY_PROFILE_NAME = "displayName";
    public static final String STORAGE_KEY_PROFILE_ID = "uuid";
    public static final String STORAGE_KEY_USERNAME = "username";
    public static final String STORAGE_KEY_REMEMBER_ME = "rememberMe";

    public abstract boolean canLogIn();

    public abstract void logIn()
        throws AuthenticationException;

    public abstract void logOut();

    public abstract boolean isLoggedIn();

    public abstract boolean canPlayOnline();

    public abstract GameProfile[] getAvailableProfiles();

    public abstract GameProfile getSelectedProfile();

    public abstract void loadFromStorage(Map map);

    public abstract Map saveForStorage();

    public abstract String getSessionToken();

    public abstract String getUsername();

    public abstract void setUsername(String s);

    public abstract void setPassword(String s);

    public abstract String guessPasswordFromSillyOldFormat(File file);

    public abstract void setRememberMe(boolean flag);

    public abstract boolean shouldRememberMe();
}
