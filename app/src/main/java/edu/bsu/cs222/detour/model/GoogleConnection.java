package edu.bsu.cs222.detour.model;

import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ToanTran on 11/8/15.
 */
public class GoogleConnection {
    private String data = "";
    private HttpURLConnection urlConnection;
    private InputStream iStream;
    private StringBuffer sBuffer = new StringBuffer();

    public String readUrl(String urlString) throws IOException {
        try {
            openURLConnection(urlString);
            iStream = urlConnection.getInputStream();
            BufferedReader bReader = new BufferedReader(new InputStreamReader(
                    iStream));
            appendFilledLines(bReader);
            data = sBuffer.toString();
            bReader.close();
        } catch (Exception e) {
            Log.d("Exception reading url", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    private void openURLConnection(String urlString) {
        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            connect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void connect() {
        try {
            urlConnection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void appendFilledLines(BufferedReader bReader) {
        String line = "";
        try {
            while ((line = bReader.readLine()) != null) {
                sBuffer.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
