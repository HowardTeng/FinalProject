package com.example.howard.finalproject;

import android.content.Intent;
import android.service.autofill.TextValueSanitizer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class Info extends AppCompatActivity {
    TextView drinkName, instructions, alcoholic;
    ImageView image;
    Button addFavorite;
    String id;
    JSONObject infoJson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Intent intent = getIntent();
        drinkName = findViewById(R.id.infoName);
        instructions = findViewById(R.id.infoInstructions);
        alcoholic = findViewById(R.id.alcoholic);
        image = findViewById(R.id.infoImage);
        addFavorite = findViewById(R.id.addFavorite);
        id = intent.getStringExtra("INFO_ID");
        addFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Favorate.addToList(infoJson.getString("strDrink"), id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        searchById(id);
    }
    private void searchById(String id) {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    "https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i=" + id,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            try {
                                infoJson = response.getJSONArray("drinks").getJSONObject(0);
                                Log.d(Category.TAG_C, infoJson.toString());
                                drinkName.setText("Name: " + infoJson.getString("strDrink"));
                                alcoholic.setText("Alcoholic: " + infoJson.getString("strAlcoholic"));
                                String ingredientsText;
                                ingredientsText = "Ingredients: " + infoJson.getString("strMeasure1") + infoJson.getString("strIngredient1");
                                for (int i = 2; i <= 15; i++) {
                                    if (!infoJson.getString("strIngredient" + Integer.toString(i)).equals("")) {
                                        ingredientsText = ingredientsText + ", " + infoJson.getString("strMeasure" + Integer.toString(i))
                                                + infoJson.getString("strIngredient" + Integer.toString(i));
                                    }
                                }
                                instructions.setText(ingredientsText +"\n\n" + "Instructions:\n" + infoJson.getString("strInstructions"));
                                Picasso.with(Info.this).load(infoJson.getString("strDrinkThumb")).into(image);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(final VolleyError error) {
                    Log.w(Category.TAG_C, error.toString());
                }
            });
            MainActivity.requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
