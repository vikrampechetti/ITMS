/**
 * 
 */
package com.ITMS.API.Common;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * @Author OohithVikramRao 19-May-2017 
 *
 */
public class DisableSSL {
	public static void disableSslVerification() {
	    try
	    {
	        // Create a trust manager that does not validate certificate chains

	    	TrustManager[] trustAllCertificates = new TrustManager[]{
	    			new X509TrustManager() {
						
						public X509Certificate[] getAcceptedIssuers() {
							return null;
						}
						
						public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
						}
						
						public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
						}
					}
	    	};
	        // Install the all-trusting trust manager
	        SSLContext sc = SSLContext.getInstance("SSL");
	        sc.init(null, trustAllCertificates, new java.security.SecureRandom());
	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

	        // Create all-trusting host name verifier
	        HostnameVerifier allHostsValid = new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
	        };
	        // Install the all-trusting host verifier
	        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    } catch (KeyManagementException e) {
	        e.printStackTrace();
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	}
}
