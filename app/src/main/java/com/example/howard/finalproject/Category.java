package com.example.howard.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Category extends AppCompatActivity {
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
