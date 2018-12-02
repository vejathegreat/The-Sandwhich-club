package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private final static String NAME = "name";
    private final static String MAIN_NAME = "mainName";
    private final static String ALSO_KNOWN_AS = "alsoKnownAs";
    private final static String PLACE_OF_ORIGIN = "placeOfOrigin";
    private final static String DESCRIPTION = "description";
    private final static String IMAGE_URL = "image";
    private final static String INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {

        try {
            Sandwich sandwichItem = new Sandwich();
            JSONObject object = new JSONObject(json);

            JSONObject name = object.getJSONObject(NAME);
            String mainName = name.getString(MAIN_NAME);
            sandwichItem.setMainName(mainName);

            JSONArray JSONArrayAlsoKnownAs = name.getJSONArray(ALSO_KNOWN_AS);
            List<String> alsoKnownAsList = convertToList(JSONArrayAlsoKnownAs);
            sandwichItem.setAlsoKnownAs(alsoKnownAsList);

            String placeOfOrigin = object.optString(PLACE_OF_ORIGIN);
            sandwichItem.setPlaceOfOrigin(placeOfOrigin);

            String description = object.getString(DESCRIPTION);
            sandwichItem.setDescription(description);

            String image = object.getString(IMAGE_URL);
            sandwichItem.setImage(image);

            JSONArray JSONArrayIngredients = object.getJSONArray(INGREDIENTS);
            List<String> ingredientsList = convertToList(JSONArrayIngredients);
            sandwichItem.setIngredients(ingredientsList);

            return sandwichItem;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static List<String> convertToList(JSONArray jsonArray) throws JSONException {
        List<String> list = new ArrayList<>(jsonArray.length());

        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));
        }

        return list;
    }
}
