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

        int jArrayLength = jsonArray.length();
        sArray = new String[jArrayLength];
        for(int i = 0; i < jArrayLength; i++) {
            sArray[i] = jsonArray.optString(i);
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
            JSONObject name = sandwichJSON.optJSONObject(Constants.SANDWICH_NAME);
            mainName = name.optString(Constants.SANDWICH_MAIN_NAME);
            alsoKnownAs = ConvertJsonArrayToStringArray(name.optJSONArray(Constants.SANDWICH_ALSO_KNOWN_AS));
            placeOfOrigin = sandwichJSON.optString(Constants.SANDWICH_PLACE_OF_ORIGIN);
            description = sandwichJSON.optString(Constants.SANDWICH_DESCRIPTION);
            image = sandwichJSON.optString(Constants.SANDWICH_IMAGE);
            ingredients = ConvertJsonArrayToStringArray(sandwichJSON.optJSONArray(Constants.SANDWICH_INGREDIENTS));
            sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sandwich;
    }
}
