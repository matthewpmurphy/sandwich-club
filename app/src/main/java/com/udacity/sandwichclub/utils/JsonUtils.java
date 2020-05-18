package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonUtils {
    //Resuable method to help convert Json Arrays to a List of strings
    private static List<String> ConvertJsonArrayToStringArray(JSONArray jsonArray) throws JSONException {
        String[] sArray = null;
        if(jsonArray == null) {
            return null;
        }
        try {
            int jArrayLength = jsonArray.length();
            sArray = new String[jArrayLength];
            for(int i = 0; i < jArrayLength; i++) {
                sArray[i] = jsonArray.getString(i);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return Arrays.asList(sArray);
    }

    //update parseSandwich to parse JSON input and populate a sandwich object

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        Sandwich sandwich = null;
        String mainName = null;
        List<String> alsoKnownAs = new ArrayList<String>();
        String placeOfOrigin = null;
        String description = null;
        String image = null;
        List<String> ingredients = new ArrayList<String>();

        try {
            JSONObject sandwichJSON = new JSONObject(json);
            JSONObject name = new JSONObject(sandwichJSON.getString("name"));
            mainName = name.getString("mainName");
            alsoKnownAs = ConvertJsonArrayToStringArray(name.getJSONArray("alsoKnownAs"));
            placeOfOrigin = sandwichJSON.getString("placeOfOrigin");
            description = sandwichJSON.getString("description");
            image = sandwichJSON.getString("image");
            ingredients = ConvertJsonArrayToStringArray(sandwichJSON.getJSONArray("ingredients"));
            sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sandwich;
    }
}
