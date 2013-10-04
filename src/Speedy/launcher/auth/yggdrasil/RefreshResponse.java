/*
--- Copyright 2013 Speedy321(Christophe-André Gassmann)

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

package Speedy.launcher.auth.yggdrasil;

import Speedy.launcher.auth.GameProfile;

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
