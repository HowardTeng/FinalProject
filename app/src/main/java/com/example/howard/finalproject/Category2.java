package com.example.howard.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class Category2 extends AppCompatActivity {
    private ArrayList<String> arrayList = new ArrayList();
    private JSONArray CategoryJson2;
    private ArrayAdapter Category2Adapter;
    private JSONObject result;
    Intent category2Intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category2);
        Intent intent = getIntent();
        String C = intent.getStringExtra("CATEGORY_NAME");
        Category2APICall("https://www.thecocktaildb.com/api/json/v1/1/filter.php?c=" + C);
        ListView CategoryList2 = findViewById(R.id.Category2);
        Category2Adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        CategoryList2.setAdapter(Category2Adapter);
        category2Intent = new Intent(this, Info.class);
        CategoryList2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    category2Intent.putExtra("INFO_ID", CategoryJson2.getJSONObject(position).getString("idDrink"));
                    startActivity(category2Intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void Category2APICall(String url) {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            try {
                                CategoryJson2 = response.getJSONArray("drinks");
                                for (int i = 0; i < CategoryJson2.length(); i++) {
                                    try {
                                        arrayList.add(CategoryJson2.getJSONObject(i).getString("strDrink"));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                Category2Adapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Log.d(Category.TAG_C, response.toString());
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
