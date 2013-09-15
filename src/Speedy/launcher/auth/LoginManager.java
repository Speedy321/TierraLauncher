package Speedy.launcher.auth;

import java.net.URL;

import Speedy.launcher.utils.Utils;

public class LoginManager {
	
	String userName;
	String passWord;
	String clientToken;
	String playerName;
	int accesToken; //hexadecimal
	int profileIdentifier; //hexadecimal
	
	String error;
	String errorMsg;
	
	private static final URL ROUTE_AUTHENTICATE = Utils.constantURL("https://authserver.mojang.com/authenticate");
    private static final URL ROUTE_REFRESH = Utils.constantURL("https://authserver.mojang.com/refresh");
		
	public void authenticateUser (String username, String pass, String clientToken) {
		
		
	}
	
	public void refreshAccessToken (int acToken, String clToken, int profIndent, String plName) {
		
		
	}
	
	public void validateAccessToken (int acToken) {
		
		
	}

}
