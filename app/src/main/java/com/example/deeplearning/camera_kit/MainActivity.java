package com.example.deeplearning.camera_kit;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    Intent intent;

    Button gradingButton;
    Button historyButton;
    Button infoButton;
    Button bookButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);

        gradingButton = (Button) findViewById(R.id.button_grading);
        historyButton = (Button) findViewById(R.id.button_history);
        infoButton = (Button) findViewById(R.id.button_information);
        bookButton = (Button) findViewById(R.id.button_booking);

        gradingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gradingIntent = new Intent(MainActivity.this, Grading.class);
                startActivity(gradingIntent);
            }
        });

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gradingIntent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(gradingIntent);
            }
        });

        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gradingIntent = new Intent(MainActivity.this, InformationActivity.class);
                startActivity(gradingIntent);
            }
        });

        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gradingIntent = new Intent(MainActivity.this, BookingActivity.class);
                startActivity(gradingIntent);
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowTitleEnabled(false);
        //actionbar.setTitle("Home");
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        intent = new Intent(MainActivity.this, MainActivity.class);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        switch (menuItem.getItemId()) {
                            case R.id.nav_home:
                                intent = new Intent(MainActivity.this, MainActivity.class);
                                break;
                            case R.id.nav_grading:
                                intent = new Intent(MainActivity.this, Grading.class);
                                break;
                            case R.id.nav_information:
                                intent = new Intent(MainActivity.this, InformationActivity.class);
                                break;
                            case R.id.nav_history:
                                intent = new Intent(MainActivity.this, HistoryActivity.class);
                                break;
                            case R.id.nav_booking:
                                intent = new Intent(MainActivity.this, BookingActivity.class);
                                break;
                        }
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();
                        //startActivity(intent);
                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        return true;
                    }
                });

        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                startActivity(intent);
            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });
    };
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
