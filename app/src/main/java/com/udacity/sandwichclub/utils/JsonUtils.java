package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private final static String NAME_CODE = "name";
    private final static String MAIN_NAME_CODE = "mainName";
    private final static String ALSO_KNOWN_AS_CODE = "alsoKnownAs";
    private final static String PLACE_OF_ORIGIN_CODE = "placeOfOrigin";
    private final static String DESCRIPTION_CODE = "description";
    private final static String IMAGE_CODE = "image";
    private final static String INGREDIENTS_CODE = "ingredients";

    public static Sandwich parseSandwichJson(String json) {

        try {
            Sandwich sandwichItem = new Sandwich();
            JSONObject object = new JSONObject(json);

            JSONObject name = object.getJSONObject(NAME_CODE);
            String mainName = name.getString(MAIN_NAME_CODE);
            sandwichItem.setMainName(mainName);

            JSONArray JSONArrayAlsoKnownAs = name.getJSONArray(ALSO_KNOWN_AS_CODE);
            List<String> alsoKnownAsList = convertToList(JSONArrayAlsoKnownAs);
            sandwichItem.setAlsoKnownAs(alsoKnownAsList);

            String placeOfOrigin = object.optString(PLACE_OF_ORIGIN_CODE);
            sandwichItem.setPlaceOfOrigin(placeOfOrigin);

            String description = object.getString(DESCRIPTION_CODE);
            sandwichItem.setDescription(description);

            String image = object.getString(IMAGE_CODE);
            sandwichItem.setImage(image);

            JSONArray JSONArrayIngredients = object.getJSONArray(INGREDIENTS_CODE);
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
