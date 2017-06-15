package com.ITMS.API.Rest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonObject;

public class DataSourceData {

	public static void main(String[] args) throws SQLException {
//		System.out.println("\n\n"+run());
		run();

	}
	public static JSONObject run() throws SQLException {
		String Headers = "";
		String SelectQuery = "SELECT * FROM IOC.TARGET_TABLE_HCS_CAMERA";
		PreparedStatement preparedStatement =ExecuteSelectQuery(SelectQuery);
		JSONObject infoobjects=null;
		try {
			ResultSetMetaData result = preparedStatement.getMetaData();
			int count = result.getColumnCount();
			for (int i = 1; i <= count; i++) {
				Headers += result.getColumnLabel(i) + ",";
			}
			Headers = Headers.substring(0, Headers.length() - 1);
			ResultSet resultSet = preparedStatement.executeQuery();
			JSONArray tempArr = new JSONArray();
			while (resultSet.next()) {
				String values = "";
				for (int i = 1; i <= count; i++) {
					values += resultSet.getString(i) + ",";
				}
				values = values.substring(0, values.length() - 1);
				String payload = createJson(Headers.split(","), values.split(","));
				JSONObject jsonObject = new JSONObject(payload);
				String ignoreableJsonObjects[] = "STARTYEAR,STARTDATE,ENDTIME,ENDMONTH,ENDDAY,DELETEFLAG,STARTHOUR,DATASOURCEID,STARTDAYOFWEEK,ENDHOUR,STARTDAY,ENDDAYOFWEEK,ANNOTATIONID,ENDDATE,STARTTIME,STARTMONTH,ENDYEAR"
						.split(",");
				for (String ignoringobjectString : ignoreableJsonObjects) {
					jsonObject.remove(ignoringobjectString);
				}
				
					infoobjects=new JSONObject();
					tempArr.put(jsonObject);
					infoobjects.put("info", tempArr);
			}
			return infoobjects;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return infoobjects;
	}

	/**
	 * To create Json object for given keys[] and values[]
	 */
	public static String createJson(String[] jsonKey, String[] jsonValues) {
		JsonObject obj = new JsonObject();
		try {
			for (int i = 0; i < jsonValues.length; i++) {
				for (int j = 0; j < jsonKey.length; j++) {
					obj.addProperty(jsonKey[i], jsonValues[i]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj.toString();
	}
	/**
	 * To Execute Select Query
	 * @throws SQLException 
	 */
	public static PreparedStatement ExecuteSelectQuery(String SelectQuery) throws SQLException {
		Connection connection = GetDB2Connection();
		PreparedStatement preparedStatement = connection.prepareStatement(SelectQuery);
		return preparedStatement;
	}

	/**
	 * To get DataBase Connection
	 */
	private static Connection GetDB2Connection() {
		Connection con = null;
		try {
			Class.forName("com.ibm.db2.jcc.DB2Driver");
			con = DriverManager.getConnection("jdbc:db2://192.168.2.68:50002/IOCDATA", "db2inst2", "Welcome@123");
			con.setAutoCommit(false);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();

		}
		return con;
	}
}
