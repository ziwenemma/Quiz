package com.example.quiz;

import androidx.annotation.NonNull;
import  androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainPage extends AppCompatActivity {


    private GridView catGrid;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        bottomNavigationView =(BottomNavigationView)findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Activity activity = null;
                switch (item.getItemId()){
                    case R.id.action_category:
                        Intent myintent = new Intent(MainPage.this, MainPage.class);
                        startActivity(myintent);
                        break;
                    case R.id.action_ranking:
                        Intent intent = new Intent(MainPage.this, ProfilePage.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }

        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Categories");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        catGrid = findViewById(R.id.catCardView);

        List<String> catList = new ArrayList<>();
        catList.add("Cat 1");
        catList.add("Cat 2");
        catList.add("Cat 3");
        catList.add("Cat 4");
        catList.add("Cat 5");
        catList.add("Cat 6");

        CatGridAdapter adapter =new CatGridAdapter(catList);
        catGrid.setAdapter(adapter);


    }


    private void setSupportActionBar(Toolbar toolbar) {
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(item.getItemId()== android.R.id.home){
            MainPage.this.finish();
        }
        if (id == R.id.action_profile) {
            Intent myintent = new Intent(MainPage.this, ProfilePage.class);
            startActivity(myintent);
        }
        else if (id == R.id.aboutPage) {
            Intent myintent = new Intent(MainPage.this, AboutPage.class);
            startActivity(myintent);
        }
        return super.onOptionsItemSelected(item);
    }
    
}