package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

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