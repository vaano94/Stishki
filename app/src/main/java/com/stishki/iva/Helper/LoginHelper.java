package com.stishki.iva.Helper;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Ivan on 10/23/2015.
 */
public class LoginHelper {

    public static String login(JSONObject jsonObject) throws IOException {

        String response = "could not establish connection";

        try {
            URL url = new URL("http://192.168.42.208:8080/rest/login/go");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestMethod("POST");
            //connection.setConnectTimeout(10000);

            OutputStream os = connection.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");

            String b = jsonObject.toString();
            osw.write(b);
            osw.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));

            response = br.readLine();
            Log.v("LOGIN", response);
            br.close();

            osw.flush();
            osw.close();

        }
        catch (MalformedURLException | ProtocolException | UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            return response;
        }

    }

    public static String prelogin(String token) {
        String result = "could not establish connection";
        URL url = null;
        try {

            url = new URL("http://192.168.42.186:8080/rest/login/prelogin");


        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestMethod("POST");

        OutputStream os = connection.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");

        osw.write(token);
        osw.flush();

        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));

        result = br.readLine();
        Log.v("TOKEN", result);
        br.close();

        osw.flush();
        osw.close();
         }catch (MalformedURLException e) {
        e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            return result;
        }

    }

    public static String logout(String token) {
        String result = "could not establish connection";
        URL url = null;
        try {
            url = new URL("http://192.168.42.186:8080/rest/login/logout");


            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestMethod("POST");

            OutputStream os = connection.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
            String s = token;
            osw.write(s);
            osw.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));

            result = br.readLine();
            br.close();

            osw.flush();
            osw.close();
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();

        }
        finally {return result;}

    }
}
