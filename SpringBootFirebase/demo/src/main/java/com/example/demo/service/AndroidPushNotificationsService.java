package com.example.demo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class AndroidPushNotificationsService {

    private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";
    private static final String FIREBASE_SERVER_KEY = "AAAAindXims:APA91bE9jFHt-n0Qo5RYG9H23zwgw6qMwzqbw1POYI1gn8s94C6arN4wLBms3KzgXzIDTltoFm4FtdQkF2oapKZ9PhCW6sEpT4qEYXH818emGvgqKY-yXNcFvGO2wh3HR6KzQSxUSpFk";

    public static String sendPushNotification(String deviceToken, String Message, String Message1,String title, String body) throws IOException {

        String result = "";
        URL url = new URL(FIREBASE_API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "key=" + FIREBASE_SERVER_KEY);
        conn.setRequestProperty("Content-Type", "application/json");

        JSONObject json = new JSONObject();

        try {

            json.put("to", deviceToken.trim());

            JSONObject data = new JSONObject();
            data.put("Key-1", Message);
            data.put("Key-2", Message1);
            json.put("data", data);
            JSONObject info = new JSONObject();
            info.put("title", title); // Notification title
            info.put("body", body); // Notification
            info.put("message", "hello user"); // body
            json.put("notification", info);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        try {
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(json.toString());
            wr.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }
            result = "succcess";
        } catch (Exception e) {
            e.printStackTrace();
            result = "failure";
        }
        System.out.println("GCM Notification is sent successfully");

        return result;

    }
}