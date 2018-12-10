package com.example.howard.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

/**
 * Search class for drinks through pre-given categories.
 */
public class Category extends AppCompatActivity {
    /** String array for all possible categories*/
    private String[] categories = new String[] {"cocktail", "normal drink", "soft drink"};
    private static final String TAG = "FinalProject: Category";
    private static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //categories();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ListView categoryList = (ListView) findViewById(R.id.categoryList);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categories);
        categoryList.setAdapter(categoryAdapter);

    }
}
