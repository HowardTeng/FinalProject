package com.example.howard.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Main screen for our cocktail database application
 */
public class MainActivity extends AppCompatActivity {
    private Button toCategory, toSearch, toFavorite;
    private static RequestQueue requestQueue;
    private static final String TAG = "FinalProject:Main";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toCategory = findViewById(R.id.category);
        toCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                segue("Category");
            }
        });
        toSearch = findViewById(R.id.search);
        toSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                segue("Search");
            }
        });
        toFavorite = findViewById(R.id.favorite);
        toFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                segue("Favorite");
            }
        });
    }
    public void segue(String destination) {
        Intent intent;
        switch(destination) {
            case "Category":intent = new Intent(this, Category.class);break;
            case "Search": intent = new Intent(this, search.class);break;
            case "Favorite": intent = new Intent(this, Favorite.class); break;
            default: intent = new Intent(this, MainActivity.class);
        }
        startActivity(intent);
    }
    void startAPICall() {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    "https://www.thecocktaildb.com/api/json/v1/1/search.php?s=margarita",
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            Log.d(TAG, response.toString());
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(final VolleyError error) {
                    Log.w(TAG, error.toString());
                }
            });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
