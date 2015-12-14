package edu.bsu.cs222.detour.model;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by ToanTran on 11/11/15.
 */
public class ConnectionTask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... url) {
        String data = "";
        int index=0;
        try {
            GoogleConnection httpConnection = new GoogleConnection();
            data = httpConnection.readUrl(url[index]);
        } catch (Exception e) {
            Log.d("Background Task", e.toString());
        }
        return data;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }
}
