package edu.bsu.cs222.detour.model;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ToanTran on 11/15/15.
 */
public class UnparsedPolyline {
    private List<List<HashMap<String, String>>> unparsedPolyline;

    public UnparsedPolyline(List<List<HashMap<String, String>>> rawData) {
        this.unparsedPolyline = rawData;
    }

    public List<List<HashMap<String, String>>> getRawData() {
        return unparsedPolyline;
    }
}
