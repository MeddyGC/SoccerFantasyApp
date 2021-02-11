package com.example.finalproject;
/**
 Author : Mohamed Conde
 Due Date: 1/24/2020
 Description: A This activitity get the details entered by the user on a form and prompts them to select an image



 **/

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.drawer_nav);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_addPlayer:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frame_container, new addActivity(), addActivity.FRAGMENT_TAG)
                                .commit();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.editPlayer:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frame_container, new updateActivity(), updateActivity.FRAGMENT_TAG)
                                .commit();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.deletePlayer:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frame_container, new DeleteActivity(), DeleteActivity.FRAGMENT_TAG)
                                .commit();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.viewRoster:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frame_container, new RosterActivity(), RosterActivity.FRAGMENT_TAG)
                                .commit();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.Football:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frame_container, new FootballActivity(), FootballActivity.FRAGMENT_TAG)
                                .commit();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                return true;
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new RosterActivity(),RosterActivity.FRAGMENT_TAG).commit();
    }
}
