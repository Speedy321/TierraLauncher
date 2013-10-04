/*
--- Copyright Speedy321(Christophe-André Gassmann)

--- This file is part of TierraLauncher.

TierraLauncher is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

TierraLauncher is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with TierraLauncher. If not, see http://www.gnu.org/licenses/ .

*/

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
