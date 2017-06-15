package com.ITMS.API.Common;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

/**
 * @author OohithVikramRao 19-May-2017
 *
 */
public class Authentication {
	
	static {
		try{
	    	DisableSSL.disableSslVerification();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	static {
		try{
			HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
				
				public boolean verify(String hostname, SSLSession session) {
					if(hostname.equals("192.168.2.120")){
						return true;
					}
					return false;
				}
			});
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void getAuthentication(){
		try{
			java.net.Authenticator.setDefault(new java.net.Authenticator(){
				protected java.net.PasswordAuthentication getPasswordAuthentication(){
					return new java.net.PasswordAuthentication("sysadmin","us3rpa88".toCharArray());					
				}
			});
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
