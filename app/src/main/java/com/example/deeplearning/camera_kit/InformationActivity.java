package com.example.deeplearning.camera_kit;

import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.ms.square.android.expandabletextview.ExpandableTextView;

public class InformationActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        Resources res = getResources();
        String[] info = res.getStringArray(R.array.info_array);

        ExpandableTextView view_0 = (ExpandableTextView) findViewById(R.id.expand_text_view_0);
        view_0.setText(info[0]);

        ExpandableTextView view_1 = (ExpandableTextView) findViewById(R.id.expand_text_view_1);
        view_1.setText(info[1]);

        ExpandableTextView view_2 = (ExpandableTextView) findViewById(R.id.expand_text_view_2);
        view_2.setText(info[2]);

        ExpandableTextView view_3 = (ExpandableTextView) findViewById(R.id.expand_text_view_3);
        view_3.setText(info[3]);

        ExpandableTextView view_4 = (ExpandableTextView) findViewById(R.id.expand_text_view_4);
        view_4.setText(info[4]);

        ExpandableTextView view_5 = (ExpandableTextView) findViewById(R.id.expand_text_view_5);
        view_5.setText(info[5]);

        ExpandableTextView view_6 = (ExpandableTextView) findViewById(R.id.expand_text_view_6);
        view_6.setText(info[6]);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowTitleEnabled(false);
        //actionbar.setTitle("Home");
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        intent = new Intent(InformationActivity.this, InformationActivity.class);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        switch (menuItem.getItemId()) {
                            case R.id.nav_home:
                                intent = new Intent(InformationActivity.this, MainActivity.class);
                                break;
                            case R.id.nav_grading:
                                intent = new Intent(InformationActivity.this, Grading.class);
                                break;
                            case R.id.nav_information:
                                intent = new Intent(InformationActivity.this, InformationActivity.class);
                                break;
                            case R.id.nav_history:
                                intent = new Intent(InformationActivity.this, HistoryActivity.class);
                                break;
                            case R.id.nav_booking:
                                intent = new Intent(InformationActivity.this, BookingActivity.class);
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
