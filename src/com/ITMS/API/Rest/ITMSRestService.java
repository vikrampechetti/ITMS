package com.ITMS.API.Rest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.json.JSONException;
import org.json.JSONObject;

import com.ITMS.API.Common.ReadEventsJsonData;

@Path("/")
public class ITMSRestService {

	@POST
	@Path("/Events")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response ITMSEventsREST(InputStream incomingData) throws JSONException, ConfigurationException {
		StringBuilder Builder = new StringBuilder();
		XMLConfiguration writeconfig = new XMLConfiguration();
		writeconfig.setBasePath("F:/response.xml");
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				Builder.append(line);
			}

		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		String payload = Builder.toString();
		writeconfig.addProperty("ITMSResponse", new JSONObject(payload));
		JSONObject IOCjs = new JSONObject(new ReadEventsJsonData().readJSONDataFromURL(payload));
		writeconfig.addProperty("IOCResponse", IOCjs);
		try {
			writeconfig.save("F:/EventsResponse.xml");
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(200).entity(payload).build();
	}

	@GET
	@Path("/Events")
	@Produces(MediaType.TEXT_PLAIN)
	public Response ITMSEventsRESTService(InputStream incomingData) throws ConfigurationException {
		XMLConfiguration writeconfig = new XMLConfiguration("F:/message.xml");
		String result = writeconfig.getProperty("ITMSEventsResponse").toString();
		return Response.status(200).entity(result).build();
	}
}
