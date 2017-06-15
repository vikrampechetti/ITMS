package com.ITMS.API.Common;

import java.net.URI;
import javax.ws.rs.core.MediaType;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * @author OohithVikramRao 19-May-2017
 *
 */
public class CreateDataSourceRecord {
	URI uri;
	Client client = new Client();
	WebResource webResource;
	ClientResponse response;
	
	/**
	 * To Create a Datasource Record
	 */
	public String createDataSourceRecord(String dataSourceID,String payload){
		try{
			uri = new URI("https://192.168.2.120:9443/ibm/ioc/api/data-injection-service/datablocks/"+dataSourceID+"/dataitems");
			webResource = client.resource(uri);
			Authentication.getAuthentication();

			response = webResource
					.header("IBM-Session-ID", "!<fksjsowWoj")
					.accept(MediaType.APPLICATION_JSON)
	                .type(MediaType.APPLICATION_JSON)
					.post(ClientResponse.class,payload);
		}catch(Exception e){
			e.printStackTrace();
		}
		return response.toString();
	}
	
}

