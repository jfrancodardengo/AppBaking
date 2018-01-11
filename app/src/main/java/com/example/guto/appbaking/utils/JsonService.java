package com.example.guto.appbaking.utils;

import com.example.guto.appbaking.model.IngredientsModel;
import com.example.guto.appbaking.model.RecipeModel;
import com.example.guto.appbaking.model.StepsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GUTO on 28/12/2017.
 */

public class JsonService{
    public static final String JSON_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public JsonService() {
    }

    public static List<RecipeModel> getJsonRecipe(String jsonData) {
        List<RecipeModel> recipeModels = new ArrayList<>();
        List<IngredientsModel> ingredientsModels = null;
        List<StepsModel> stepsModels = null;

        if(jsonData == null || jsonData.isEmpty()) return null;

        try {
            JSONArray baseArray = new JSONArray(jsonData);

            for (int i = 0; i < baseArray.length(); i++) {
                ingredientsModels = new ArrayList<>();
                stepsModels = new ArrayList<>();
                JSONObject jsonObject = baseArray.getJSONObject(i);
                int idRecipe = jsonObject.getInt("id");
                String nameRecipe = jsonObject.getString("name");

                JSONArray jsonArrayIngredients = jsonObject.getJSONArray("ingredients");
                for (int j = 0; j < jsonArrayIngredients.length(); j++) {
                    JSONObject current = jsonArrayIngredients.getJSONObject(j);
                    int quantity = current.getInt("quantity");
                    String measure = current.getString("measure");
                    String ingredient = current.getString("ingredient");

                    ingredientsModels.add(new IngredientsModel(quantity,measure,ingredient));
                }

                JSONArray jsonArraySteps = jsonObject.getJSONArray("steps");
                for (int k = 0; k < jsonArraySteps.length(); k++) {
                    JSONObject current = jsonArraySteps.getJSONObject(k);
                    int idStep = current.getInt("id");
                    String shortDescription = current.getString("shortDescription");
                    String description = current.getString("description");
                    String videoUrl = current.getString("videoURL");
                    String thumbnailUrl = current.getString("thumbnailURL");

                    stepsModels.add(new StepsModel(idStep,shortDescription,description,videoUrl,thumbnailUrl));
                }

                int servings = jsonObject.getInt("servings");
                String image = jsonObject.getString("image");

                recipeModels.add(new RecipeModel(idRecipe,nameRecipe,ingredientsModels,stepsModels,servings,image));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recipeModels;
    }

    public static String download(String url) {
        Object connection = ConnectService.connect(url);
        if (connection.toString().startsWith("Error")) {
            return connection.toString();
        }
        try {
            HttpURLConnection con = (HttpURLConnection) connection;
            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream is = new BufferedInputStream(con.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line;
                StringBuilder jsonData = new StringBuilder();

                while ((line = br.readLine()) != null) {
                    jsonData.append(line).append("\n");
                }
                br.close();
                is.close();
                return jsonData.toString();
            } else {
                return String.format("Error %s", con.getResponseMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return String.format("Error %s", e.getMessage());
        }
    }
}
