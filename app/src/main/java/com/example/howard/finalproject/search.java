package com.example.howard.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.List;

public class search extends AppCompatActivity {
    private static JSONArray searchListJson;
    private String[] searchStrings = new String[0];
    private String TAG_S = "FinalProject_Main";
    private ArrayAdapter<String> searchListAdapter;
    private String[] randomStrings = new String[] {"a", "b", "c"};
    private ArrayList arrayList;
    private EditText searchBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchBar = findViewById(R.id.searchBar);

        Button startSearch = findViewById(R.id.startSearch);
        startSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchAPICall("https://www.thecocktaildb.com/api/json/v1/1/search.php?s=" + searchBar.getText());
            }
        });
        arrayList = new ArrayList(Arrays.asList(searchStrings));
        ListView searchList = findViewById(R.id.searchList);
        searchListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        searchList.setAdapter(searchListAdapter);
        final Intent searchIntent = new Intent(this, Info.class);
        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("FinalProject.Main", searchStrings[position]);
                try {
                    searchIntent.putExtra("INFO_ID", searchListJson.getJSONObject(position).getString("idDrink"));
                    Log.d(TAG_S, searchListJson.getJSONObject(position).toString());
                    startActivity(searchIntent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void searchAPICall(String url) {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            try {
                                searchListJson = response.getJSONArray("drinks");
                                searchStrings = new String[searchListJson.length()];
                                for (int i = 0; i < searchStrings.length; i++) {
                                    try {
                                        searchStrings[i] = searchListJson.getJSONObject(i).getString("strDrink");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                arrayList.clear();
                                arrayList.addAll(new ArrayList(Arrays.asList(searchStrings)));
                                searchListAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Log.d(TAG_S, response.toString());
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(final VolleyError error) {
                    Log.w(TAG_S, error.toString());
                }
            });
            MainActivity.requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
