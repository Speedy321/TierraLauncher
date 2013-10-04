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

package Speedy.launcher.utils;

public class StringUtils
{

    public StringUtils()
    {
    }

    public static boolean isBlank(CharSequence cs)
    {
        int strLen;
        if(cs == null || (strLen = cs.length()) == 0)
        {
            return true;
        }
        for(int i = 0; i < strLen; i++)
        {
            if(!Character.isWhitespace(cs.charAt(i)))
            {
                return false;
            }
        }

        return true;
    }

    public static boolean isNotBlank(CharSequence cs)
    {
        return !isBlank(cs);
    }
}
