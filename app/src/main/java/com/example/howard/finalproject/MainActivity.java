package com.example.howard.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private Button toCategory, toSearch, toFavorate, randomCocktail;
    public static RequestQueue requestQueue;
    private static final String TAG = "FinalProject:Main";
    Intent randomIntent;
    private String randomId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(this);
        randomIntent = new Intent(this, Info.class);
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
        toFavorate = findViewById(R.id.favorate);
        toFavorate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                segue("Favorite");
            }
        });
        randomCocktail = findViewById(R.id.randomCocktail);
        randomCocktail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRandom();
                Intent randomIntent = new Intent(MainActivity.this, Info.class);
                randomIntent.putExtra("INFO_ID", randomId);
                startActivity(randomIntent);
            }
        });
    }
    public void segue(String destination) {
        Intent intent;
        switch(destination) {
            case "Category":intent = new Intent(this, Category.class);break;
            case "Search": intent = new Intent(this, search.class);break;
            case "Favorite": intent = new Intent(this, Favorate.class); break;
            default: intent = new Intent(this, MainActivity.class);
        }
        startActivity(intent);
    }
    private void getRandom() {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    "https://www.thecocktaildb.com/api/json/v1/1/random.php",
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            try {
                                randomId = response.getJSONArray("drinks").getJSONObject(0).getString("idDrink");
                                Log.d(TAG, randomId);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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
