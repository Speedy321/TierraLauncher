//package Speedy.launcher;
//
//import Speedy.launcher.auth.yggdrasil.YggdrasilAuthenticationService;
//import Speedy.launcher.utils.FileUtils;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.internal.LinkedTreeMap;
//import java.io.File;
//import java.io.IOException;
//import java.lang.reflect.Type;
//
//public class ProfileManager
//{
//
//    private final Gson gson;
//    private YggdrasilAuthenticationService authenticationService;
//
//    public ProfileManager()
//    {
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.enableComplexMapKeySerialization();
//        gson = gsonBuilder.create();
//        authenticationService = new YggdrasilAuthenticationService();
//    }
//
//    public void loadProfile()
//    {
//        try
//        {
//            String rawCredentials = FileUtils.readFileToString(new File(Launcher.getInstance().getBaseDirectory(), "profile.json"));
//            java.lang.reflect.Type typeOfHashMap = (new  Object() class _anm1 {} ).getType();     /* anonymous class not found */ 
//            
//            LinkedTreeMap credentials = (LinkedTreeMap)gson.fromJson(rawCredentials, typeOfHashMap);
//            authenticationService.loadFromStorage(credentials);
//            authenticationService.logIn();
//            saveProfile();
//        }
//        catch(Exception e)
//        {
//            Launcher.getInstance().println("Couldn't load profile.");
//            authenticationService.logOut();
//        }
//    }
//
//    public void saveProfile()
//    {
//        String rawCredentials = gson.toJson(authenticationService.saveForStorage());
//        try
//        {
//            FileUtils.writeStringToFile(new File(Launcher.getInstance().getBaseDirectory(), "profile.json"), rawCredentials);
//        }
//        catch(IOException e)
//        {
//            Launcher.getInstance().println("Couldn't write profile.");
//        }
//    }
//
//    public YggdrasilAuthenticationService getAuthenticationService()
//    {
//        return authenticationService;
//    }
//}
