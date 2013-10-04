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

package Speedy.launcher.utils;

import java.util.Map;

public class StrSubstitutor
{

    private Map map;

    public StrSubstitutor(Map map)
    {
        this.map = map;
    }

    public String replace(String str)
    {
        StringBuffer sb = new StringBuffer();
        char strArray[] = str.toCharArray();
        int i;
        for(i = 0; i < strArray.length - 1;)
        {
            if(strArray[i] == '$' && strArray[i + 1] == '{')
            {
                int begin = i += 2;
                for(; strArray[i] != '}'; i++) { }
                sb.append((String)map.get(str.substring(begin, i++)));
            } else
            {
                sb.append(strArray[i]);
                i++;
            }
        }

        if(i < strArray.length)
        {
            sb.append(strArray[i]);
        }
        return sb.toString();
    }
}
