package Speedy.launcher.auth;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.UnknownHostException;

import Speedy.launcher.Main;
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
		
	public static void authenticateUser (String username, String pass, String clientToken) {
		
		String hostAdress = "null";
		Proxy proxy;
		try {
			hostAdress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e1) {
			Main.errorLog(e1);
			e1.printStackTrace();
		}
		
		String payload;
		String answer = "nope";
		
		if (!clientToken.isEmpty()) {
			payload = "\"{\"agent\":{\"name\":\"Minecraft\",\"version\": 1},\"username\":\""+username+"\",\"password\":\""+pass+"\",\"clientToken\":\""+clientToken+"\"}\"";
		} else {
			payload = "\"{\"agent\":{\"name\":\"Minecraft\",\"version\": 1},\"username\":\""+username+"\",\"password\":\""+pass+"\"}\"";
		}
		
		if (hostAdress != "null") { 
			
			proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(hostAdress, 443)); 
		
			if (proxy != null) {
				try {
					answer = Utils.performPost(ROUTE_AUTHENTICATE, payload, proxy, "application/json", true);
				} catch (IOException e) {
					Main.errorLog(e);
					e.printStackTrace();
				} 
			}
		}
		
		Main.log(answer);
		
		
	}
	
	public static void refreshAccessToken (int acToken, String clToken, int profIndent, String plName) {
		
		
		
	}
	
	public static void validateAccessToken (int acToken) {
		
		
		
	}

}
