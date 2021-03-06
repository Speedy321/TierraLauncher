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

//package Speedy.launcher;
//
//import java.io.File;
//import java.lang.reflect.Method;
//import java.net.URI;
//
//public class OperatingSystem extends Enum{
//
//    public static final OperatingSystem LINUX;
//    public static final OperatingSystem WINDOWS;
//    public static final OperatingSystem OSX;
//    public static final OperatingSystem UNKNOWN;
//    private final String name;
//    private final String aliases[];
//    private static final OperatingSystem ENUM$VALUES[];
//
//    private OperatingSystem(String s, int i, String name, String aliases[])
//    {
//        super(s, i);
//        this.name = name;
//        this.aliases = aliases != null ? aliases : new String[0];
//    }
//
//    public String getName()
//    {
//        return name;
//    }
//
//    public String[] getAliases()
//    {
//        return aliases;
//    }
//
//    public boolean isSupported()
//    {
//        return this != UNKNOWN;
//    }
//
//    public String getJavaDir()
//    {
//        String separator = System.getProperty("file.separator");
//        String path = (new StringBuilder(String.valueOf(System.getProperty("java.home")))).append(separator).append("bin").append(separator).toString();
//        if(getCurrentPlatform() == WINDOWS && (new File((new StringBuilder(String.valueOf(path))).append("javaw.exe").toString())).isFile())
//        {
//            return (new StringBuilder(String.valueOf(path))).append("javaw.exe").toString();
//        } else
//        {
//            return (new StringBuilder(String.valueOf(path))).append("java").toString();
//        }
//    }
//
//    public static OperatingSystem getCurrentPlatform()
//    {
//        String osName = System.getProperty("os.name").toLowerCase();
//        OperatingSystem aoperatingsystem[];
//        int j = (aoperatingsystem = values()).length;
//        for(int i = 0; i < j; i++)
//        {
//            OperatingSystem os = aoperatingsystem[i];
//            String as[];
//            int l = (as = os.getAliases()).length;
//            for(int k = 0; k < l; k++)
//            {
//                String alias = as[k];
//                if(osName.contains(alias))
//                {
//                    return os;
//                }
//            }
//
//        }
//
//        return UNKNOWN;
//    }
//
//    public static OperatingSystem[] values()
//    {
//        OperatingSystem aoperatingsystem[];
//        int i;
//        OperatingSystem aoperatingsystem1[];
//        System.arraycopy(aoperatingsystem = ENUM$VALUES, 0, aoperatingsystem1 = new OperatingSystem[i = aoperatingsystem.length], 0, i);
//        return aoperatingsystem1;
//    }
//
//    public static OperatingSystem valueOf(String s)
//    {
//        return (OperatingSystem)Enum.valueOf(Speedy/launcher/OperatingSystem, s);
//    } 
//
//	public File getWorkingDirectory() {
//		
//		String userHome = System.getProperty("user.home", ".");
//        File workingDirectory = null;
//        
//public class OperatingSystem extends Enum{
//
//    public static final OperatingSystem LINUX;
//    public static final OperatingSystem WINDOWS;
//    public static final OperatingSystem OSX;
//    public static final OperatingSystem UNKNOWN;
//    private final String name;
//    private final String aliases[];
//    private static final OperatingSystem ENUM$VALUES[];
//
//    private OperatingSystem(String s, int i, String name, String aliases[])
//    {
//        super(s, i);
//        this.name = name;
//        this.aliases = aliases != null ? aliases : new String[0];
//    }
//
//    public String getName()
//    {
//        return name;
//    }
//
//    public String[] getAliases()
//    {
//        return aliases;
//    }
//
//    public boolean isSupported()
//    {
//        return this != UNKNOWN;
//    }
//
//    public String getJavaDir()
//    {
//        String separator = System.getProperty("file.separator");
//        String path = (new StringBuilder(String.valueOf(System.getProperty("java.home")))).append(separator).append("bin").append(separator).toString();
//        if(getCurrentPlatform() == WINDOWS && (new File((new StringBuilder(String.valueOf(path))).append("javaw.exe").toString())).isFile())
//        {
//            return (new StringBuilder(String.valueOf(path))).append("javaw.exe").toString();
//        } else
//        {
//            return (new StringBuilder(String.valueOf(path))).append("java").toString();
//        }
//    }
//
//    public static OperatingSystem getCurrentPlatform()
//    {
//        String osName = System.getProperty("os.name").toLowerCase();
//        OperatingSystem aoperatingsystem[];
//        int j = (aoperatingsystem = values()).length;
//        for(int i = 0; i < j; i++)
//        {
//            OperatingSystem os = aoperatingsystem[i];
//            String as[];
//            int l = (as = os.getAliases()).length;
//            for(int k = 0; k < l; k++)
//            {
//                String alias = as[k];
//                if(osName.contains(alias))
//                {
//                    return os;
//                }
//            }
//
//        }
//
//        return UNKNOWN;
//    }
//
//    public static OperatingSystem[] values()
//    {
//        OperatingSystem aoperatingsystem[];
//        int i;
//        OperatingSystem aoperatingsystem1[];
//        System.arraycopy(aoperatingsystem = ENUM$VALUES, 0, aoperatingsystem1 = new OperatingSystem[i = aoperatingsystem.length], 0, i);
//        return aoperatingsystem1;
//    }
//
//    public static OperatingSystem valueOf(String s)
//    {
//        return (OperatingSystem)Enum.valueOf(Speedy/launcher/OperatingSystem, s);
//    } 
//
//	public File getWorkingDirectory() {
//		
//		String userHome = System.getProperty("user.home", ".");
//        File workingDirectory;
//        
//		return workingDirectory;
//	}
//    
//}
