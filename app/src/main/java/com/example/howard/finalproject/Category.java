package com.example.howard.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import java.util.ArrayList;
import java.util.Arrays;

public class Category extends AppCompatActivity {
    public static final String TAG_C= "FinalProject:Main";
    private JSONArray categoryJson;
    private String[] categories = new String[0];
    private ArrayList<String> arr = new ArrayList<String>(Arrays.asList(categories));
    private ArrayAdapter<String> categoryAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        getCategoryList();
        ListView categoryList = (ListView) findViewById(R.id.categoryList);
        categoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr);
        categoryList.setAdapter(categoryAdapter);
        final Intent categoryIntent = new Intent(this, Category2.class);
        categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                categoryIntent.putExtra("CATEGORY_NAME", categories[position]);
                startActivity(categoryIntent);
            }
        });
    }
    private void getCategoryList() {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    "https://www.thecocktaildb.com/api/json/v1/1/list.php?c=list",
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            try {
                                categoryJson = response.getJSONArray("drinks");
                                categories = new String[categoryJson.length()];
                                for (int i = 0; i < categoryJson.length(); i++) {
                                    categories[i] = categoryJson.getJSONObject(i).getString("strCategory");
                                }
                                arr.clear();
                                arr.addAll(new ArrayList(Arrays.asList(categories)));
                                categoryAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Log.d(TAG_C, "API call finished");
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(final VolleyError error) {
                    Log.w(TAG_C, error.toString());
                }
            });
            MainActivity.requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
