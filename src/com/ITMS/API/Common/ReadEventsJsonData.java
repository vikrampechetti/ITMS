package com.ITMS.API.Common;

import java.sql.Date;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ReadEventsJsonData {

    public String readJSONDataFromURL(String payload) throws JSONException{
        JSONObject responseObj = new JSONObject();
        try {            
            System.out.print(" PAYLOAD >>---->>> "+payload);
            JSONObject payloadObj = new JSONObject(payload);
            JSONObject datasourcePayload = new JSONObject();
            JSONObject alertObj = (JSONObject)payloadObj.get("alert");

            datasourcePayload.put("identifier",alertObj.get("identifier"));
            datasourcePayload.put("sender",alertObj.get("sender"));
            datasourcePayload.put("sent",alertObj.get("sent"));
            datasourcePayload.put("status",alertObj.get("status"));
            datasourcePayload.put("msgType",alertObj.get("msgType"));
            datasourcePayload.put("scope",alertObj.get("scope"));

            JSONObject infoObj = (JSONObject)alertObj.get("info");

            datasourcePayload.put("category",infoObj.get("category"));
            datasourcePayload.put("event",infoObj.get("event"));
            datasourcePayload.put("urgency",infoObj.get("urgency"));
            datasourcePayload.put("severity",infoObj.get("severity"));
            datasourcePayload.put("certainty",infoObj.get("certainty"));
            datasourcePayload.put("senderName",infoObj.get("senderName"));
            datasourcePayload.put("headline",infoObj.get("headline"));
            datasourcePayload.put("description",infoObj.get("description"));
            datasourcePayload.put("instruction",infoObj.get("instruction"));
            datasourcePayload.put("contact",infoObj.get("contact"));

            JSONObject areaObj = (JSONObject)infoObj.get("area");
            areaObj.getString("areaDesc");
            areaObj.getString("geocode");
                    

            String[] latLong = areaObj.getString("geocode").split(",");
            String latitude = latLong[1];
            String longitude = latLong[0];

            String Location = "POINT("+longitude+" "+ latitude+")";
            datasourcePayload.put("LOCATION", Location);
            datasourcePayload.put("effective",infoObj.getString("effective"));
            datasourcePayload.put("onset",infoObj.getString("onset"));
            datasourcePayload.put("expires",infoObj.getString("expires"));
            datasourcePayload.put("STARTDATETIME", currentSysDateTime());
            datasourcePayload.put("ENDDATETIME", currentSysDateTime());
            datasourcePayload.put("LASTUPDATEDATETIME", currentSysDateTime());
            
            JSONArray jsonObject = (JSONArray)infoObj.get("parameter");
            System.out.println(" PARAMETER 1 ::: "+jsonObject);  
            //JSONArray parametrArray = new JSONArray(infoObj.getJSONArray("parameter"));
            System.out.println(" PARAMETER ::: "+jsonObject);            
            
            JSONArray parametrArray = new JSONArray(infoObj.getString("parameter"));  
            for(int count=0; count<parametrArray.length(); count++){
                JSONObject paramObj = new JSONObject(parametrArray.getString(count));
                datasourcePayload.put(paramObj.getString("valueName"), paramObj.getString("value"));
            }

            System.out.println("DATA SOURCE PAYLOAD  ::::  "+datasourcePayload);
            CreateDataSourceRecord createDSRecord = new CreateDataSourceRecord();
            String responseCode = createDSRecord.createDataSourceRecord("7", datasourcePayload.toString());               
            
            if(responseCode.contains("200")){
                responseObj.put("Status", "Success");
            }else{
                responseObj.put("Status", "Failure");
            }
            responseObj.put("Desc", responseCode);
            responseObj.put("Eventid", alertObj.get("identifier"));                        
        } catch (Exception e) {
            responseObj.put("Exception", e.getMessage());
            e.printStackTrace();
        }
        return responseObj.toString();
    }
	
    public String currentSysDateTime(){
            String curDateTime = null;
            try{
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date curDate = new Date(System.currentTimeMillis());
                curDateTime = simpleDateFormat.format(curDate);
            }catch(Exception e){
                    e.printStackTrace();
            }
            return curDateTime;
    }
}
