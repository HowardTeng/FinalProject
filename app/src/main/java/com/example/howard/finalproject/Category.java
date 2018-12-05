package com.example.howard.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
/**
 * Search class for drinks through pre-given categories.
 */
public class Category extends AppCompatActivity {
    /** String array for all possible categories*/
    private String[] categories = new String[] {"cocktail", "normal drink", "soft drink"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ListView categoryList = (ListView) findViewById(R.id.categoryList);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categories);
        categoryList.setAdapter(categoryAdapter);
    }
}
