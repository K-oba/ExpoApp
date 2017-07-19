package com.kaoba.expocr.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.kaoba.expocr.R;
import com.kaoba.expocr.Session;
import com.kaoba.expocr.estimote.BeaconAppManager;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

public class WelcomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        session = new Session(getApplicationContext());
        ImageView image = (ImageView) findViewById(R.id.imageWelcomeMenu);
        Picasso.with(getApplicationContext()).load("http://res.cloudinary.com/duxllywl7/image/upload/v1500407409/beacons-100635065-primary.idge_paxysr.jpg").into(image);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        assert fab != null;
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        Button calendar = (Button) findViewById(R.id.buttonCalendar);
        assert calendar != null;
        calendar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, ViewExpoCalendarActivity.class);
                startActivity(intent);
            }
        });

        Button expoList = (Button) findViewById(R.id.buttonExpoList);
        assert expoList != null;
        expoList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, LiveExpositionsActivity.class);
                startActivity(intent);
            }
        });

//        Button standList = (Button) findViewById(R.id.buttonStands);
//        assert standList != null;
//        expoList.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent intent = new Intent(WelcomeActivity.this, ListStandActivity.class);
//                startActivity(intent);
//            }
//        });



//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        assert drawer != null;
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();

//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        assert navigationView != null;
//        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            session.logout();
            finish();
            Intent i = new Intent(WelcomeActivity.this,LoginActivity.class);
            startActivity(i);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        BeaconAppManager manager = (BeaconAppManager) getApplication();
//
//        if (!SystemRequirementsChecker.checkWithDefaultDialogs(this)){
//            Log.d("ERROR",getString(R.string.error_welcome_screen_permission));
//        }else if(!manager.isBeaconNotificationsEnabled()){
//            manager.enableBeaconNotifications();
//        }
//    }
}
