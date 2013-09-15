//package Speedy.launcher.auth;
//
//import java.io.*;
//import java.util.*;
//import javax.crypto.*;
//import javax.crypto.spec.PBEKeySpec;
//import javax.crypto.spec.PBEParameterSpec;
//
//import Speedy.launcher.Launcher;
//import Speedy.launcher.utils.StringUtils;
//
//public abstract class BaseAuthenticationService implements AuthenticationService {
//
//    private String username;
//    private String password;
//    private GameProfile selectedProfile;
//    private boolean shouldRememberMe;
//    private String selectedVersion;
//
//    public BaseAuthenticationService()
//    {
//        shouldRememberMe = true;
//    }
//
//    public boolean canLogIn()
//    {
//        return !canPlayOnline() && StringUtils.isNotBlank(getUsername()) && StringUtils.isNotBlank(getPassword());
//    }
//
//    public void logOut()
//    {
//        password = null;
//        setSelectedProfile(null);
//    }
//
//    public boolean isLoggedIn()
//    {
//        return getSelectedProfile() != null;
//    }
//
//    public boolean canPlayOnline()
//    {
//        return isLoggedIn() && getSelectedProfile() != null && getSessionToken() != null;
//    }
//
//    public void setUsername(String username)
//    {
//        if(isLoggedIn() && canPlayOnline())
//        {
//            throw new IllegalStateException("Cannot change username whilst logged in & online");
//        } else
//        {
//            this.username = username;
//            return;
//        }
//    }
//
//    public void setPassword(String password)
//    {
//        if(isLoggedIn() && canPlayOnline() && StringUtils.isNotBlank(password))
//        {
//            throw new IllegalStateException("Cannot set password whilst logged in & online");
//        } else
//        {
//            this.password = password;
//            return;
//        }
//    }
//
//    public String getUsername()
//    {
//        return username;
//    }
//
//    protected String getPassword()
//    {
//        return password;
//    }
//
//    public void loadFromStorage(Map credentials)
//    {
//        logOut();
//        if(credentials.containsKey("rememberMe"))
//        {
//            setRememberMe(Boolean.getBoolean((String)credentials.get("rememberMe")));
//        }
//        selectedVersion = (String)credentials.get("selectedVersion");
//        setUsername((String)credentials.get("username"));
//        Launcher.getInstance().setClientToken(UUID.fromString((String)credentials.get("clientToken")));
//        if(credentials.containsKey("displayName") && credentials.containsKey("uuid"))
//        {
//            setSelectedProfile(new GameProfile((String)credentials.get("uuid"), (String)credentials.get("displayName")));
//        }
//    }
//
//    public Map saveForStorage()
//    {
//        Map result = new HashMap();
//        if(!shouldRememberMe())
//        {
//            result.put("rememberMe", Boolean.toString(false));
//            return result;
//        }
//        result.put("selectedVersion", selectedVersion);
//        if(getUsername() != null)
//        {
//            result.put("username", getUsername());
//        }
//        result.put("clientToken", Launcher.getInstance().getClientToken().toString());
//        if(getSelectedProfile() != null)
//        {
//            result.put("displayName", getSelectedProfile().getName());
//            result.put("uuid", getSelectedProfile().getId());
//        }
//        return result;
//    }
//
//    public boolean shouldRememberMe()
//    {
//        return shouldRememberMe;
//    }
//
//    public void setRememberMe(boolean rememberMe)
//    {
//        shouldRememberMe = rememberMe;
//    }
//
//    protected void setSelectedProfile(GameProfile selectedProfile)
//    {
//        this.selectedProfile = selectedProfile;
//    }
//
//    public GameProfile getSelectedProfile()
//    {
//        return selectedProfile;
//    }
//
//    public String getSelectedVersion()
//    {
//        return selectedVersion;
//    }
//
//    public void setSelectedVersion(String selectedVersion)
//    {
//        this.selectedVersion = selectedVersion;
//    }
//
//    public String toString()
//    {
//        StringBuilder result = new StringBuilder();
//        result.append(getClass().getSimpleName());
//        result.append("{");
//        if(isLoggedIn())
//        {
//            result.append("Logged in as ");
//            result.append(getUsername());
//            if(getSelectedProfile() != null)
//            {
//                result.append(" / ");
//                result.append(getSelectedProfile());
//                result.append(" - ");
//                if(canPlayOnline())
//                {
//                    result.append("Online with session token '");
//                    result.append(getSessionToken());
//                    result.append("'");
//                } else
//                {
//                    result.append("Offline");
//                }
//            }
//        } else
//        {
//            result.append("Not logged in");
//        }
//        result.append("}");
//        return result.toString();
//    }
//
//    public String guessPasswordFromSillyOldFormat(File file)
//    {
//        String details[] = getStoredDetails(file);
//        if(details != null && details[0].equals(getUsername()))
//        {
//            return details[1];
//        } else
//        {
//            return null;
//        }
//    }
//
//    public static String[] getStoredDetails(File lastLoginFile)
//    {
//        if(!lastLoginFile.isFile())
//        {
//            return null;
//        }
//        String username;
//        String password;
//        Cipher cipher = getCipher(2, "passwordfile");
//        DataInputStream dis;
//        if(cipher != null)
//        {
//            dis = new DataInputStream(new CipherInputStream(new FileInputStream(lastLoginFile), cipher));
//        } else
//        {
//            dis = new DataInputStream(new FileInputStream(lastLoginFile));
//        }
//        username = dis.readUTF();
//        password = dis.readUTF();
//        dis.close();
//        return (new String[] {
//            username, password
//        });
//        Exception e;
//        e;
//        Launcher.getInstance().println("Couldn't load old lastlogin file", e);
//        return null;
//    }
//
//    private static Cipher getCipher(int mode, String password)
//        throws Exception
//    {
//        Random random = new Random(0x29482c2L);
//        byte salt[] = new byte[8];
//        random.nextBytes(salt);
//        PBEParameterSpec pbeParamSpec = new PBEParameterSpec(salt, 5);
//        javax.crypto.SecretKey pbeKey = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(new PBEKeySpec(password.toCharArray()));
//        Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
//        cipher.init(mode, pbeKey, pbeParamSpec);
//        return cipher;
//    }
//}
