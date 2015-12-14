package edu.bsu.cs222.detour.model;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by s-knob on 11/18/15.
 */
public class GasStationDataParserTask extends AsyncTask<String, Integer, ArrayList<Place>> {
    private JSONGasStationParser parser = new JSONGasStationParser();
    private JSONObject jObject;
    private ArrayList<Place> placeList;

    @Override
    protected ArrayList<Place> doInBackground(String... jsonData) {
        getJObject(jsonData);
        parseData();
        return placeList;
    }

    private void getJObject(String... jsonData) {
        int index = 0;
        try {
            jObject = new JSONObject(jsonData[index]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseData() {
        try {
            placeList = parser.parse(jObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void onPostExecute(ArrayList<Place> places) {
        super.onPostExecute(places);
    }
}
