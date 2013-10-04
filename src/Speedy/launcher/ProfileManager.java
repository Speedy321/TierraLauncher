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
