package com.kaoba.expocr.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.kaoba.expocr.R;
import com.kaoba.expocr.Session;

public class ShowExpoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_stand);
        Intent intent = getIntent();
        String message = "Expo ID is ";
        message = message.concat(intent.getStringExtra(LiveExpositionsActivity.EXTRA_MESSAGE));
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        message = "Expo ID from session ";
        Session session = new Session(getApplicationContext());
        message = message.concat(session.getExpoId().toString());
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

}
