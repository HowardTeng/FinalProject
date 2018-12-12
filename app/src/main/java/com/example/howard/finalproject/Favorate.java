package com.example.howard.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Favorate extends AppCompatActivity {
    private static ArrayList<String> favorite = new ArrayList();
    private static ArrayList<String> favoriteId = new ArrayList();
    private ListView favoriteList;
    ArrayAdapter favoriteListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorate);
        favoriteList = findViewById(R.id.favoriteList);
        favoriteListAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, favorite);
        favoriteList.setAdapter(favoriteListAdapter);
        favoriteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent favoriteIntent = new Intent(Favorate.this, Info.class);
                favoriteIntent.putExtra("INFO_ID", favoriteId.get(position));
                startActivity(favoriteIntent);
            }
        });
    }
    public static void addToList(String name, String id) {
        favorite.add(name);
        favoriteId.add(id);
    }
}
