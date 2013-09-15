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
