package com.stishki.iva.Helper;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by User1 on 16.11.2015.
 */
public class UserHelper {

    private static String token;

    public static String getUserDetails(String token) throws MalformedURLException {
        String response = "";
        try {
            URL url = new URL("http://192.168.42.208:8080/rest/login/info");
            //URL url = new URL("http://10.0.3.2:8080/rest/registerjson/add");


            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestMethod("POST");

            OutputStream os = connection.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("token", token);
            String b = jsonObject.toString();
            osw.write(b);
            osw.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            while (br.ready()) {
                response += br.readLine();
            }
            //response = br.readLine();

            Log.v("User details Received", response);
            br.close();

            osw.flush();
            osw.close();
        }
        catch (ProtocolException e) {
            Log.e("PROCOTOCOLEX", "PROTOCOL EXCEPTION OCCURED");
        }
        catch (IOException e) {
            Log.e("IOEXCEPTION", "IO EXCEPTION OCCURED");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }

}
