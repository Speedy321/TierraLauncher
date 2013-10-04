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

package Speedy.launcher.auth;

public class GameProfile
{

    private final String id;
    private final String name;

    public GameProfile(String id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }
        if(o == null || getClass() != o.getClass())
        {
            return false;
        }
        GameProfile that = (GameProfile)o;
        if(!id.equals(that.id))
        {
            return false;
        }
        return name.equals(that.name);
    }

    public int hashCode()
    {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    public String toString()
    {
        return (new StringBuilder("GameProfile{id='")).append(id).append('\'').append(", name='").append(name).append('\'').append('}').toString();
    }
}
