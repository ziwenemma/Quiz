package com.example.quiz;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;


public class MainPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private BottomNavigationView bottomNavigationView;
    private FrameLayout main_frame;

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.nav_about:
                        setFragment(new AboutFragment());
                        return true;
                        case R.id.nav_home:
                            setFragment(new CategoryFragment());
                        return true;
                        case R.id.nav_account:
                            setFragment(new ListFragment());
                            return true;
                    }
                    return false;
                }
            };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigationView=findViewById(R.id.bottom_nav_bar);
        main_frame=findViewById(R.id.main_frame);

        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);


        DrawerLayout drawer=(DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView=(NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setFragment(new CategoryFragment());
    }




    @Override
   public void onBackPressed(){
        DrawerLayout drawer=(DrawerLayout)findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item){
        int id= item.getItemId();

        if(id== R.id.nav_home){
            setFragment(new CategoryFragment());
            return true;
    }else if(id==R.id.nav_list) {
            setFragment(new ListFragment());
            return true;
        }else if(id==R.id.nav_about){
                setFragment(new AboutFragment());
                return true;
    }else if(id==R.id.nav_share) {
            setFragment(new ContactFragment());
            return true;
        }
        DrawerLayout drawer=(DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return  true;
        }



        private void setFragment(Fragment fragment){
            FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
            transaction.replace(main_frame.getId(),fragment);
            transaction.commit();


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
        else if (id == R.id.contactus) {
            Intent myintent2 = new Intent(MainPage.this, Contactus.class);
            startActivity(myintent2);
        }
        return super.onOptionsItemSelected(item);
    }


}