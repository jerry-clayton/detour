package edu.bsu.cs222.detour.model;

import android.os.AsyncTask;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ToanTran on 12/10/15.
 */
public class ParserTask extends
        AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

    @Override
    protected List<List<HashMap<String, String>>> doInBackground(
            String... jsonData) {
        JSONObject jObject;
        List<List<HashMap<String, String>>> routes = null;
        int index = 0;
        try {
            jObject = new JSONObject(jsonData[index]);
            JSONParser parser = new JSONParser();
            routes = parser.parse(jObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return routes;
    }

    @Override
    protected void onPostExecute(List<List<HashMap<String, String>>> routes) {
        super.onPostExecute(routes);
    }
}
