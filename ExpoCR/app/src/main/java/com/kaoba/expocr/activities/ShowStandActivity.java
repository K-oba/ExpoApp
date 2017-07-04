package com.kaoba.expocr.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kaoba.expocr.R;

import org.json.JSONObject;

public class ShowStandActivity extends AppCompatActivity {

    private static final String NAME = "nombre";
    private static final String TYPE = "tipo";
    private static final String URL = "http://192.168.86.204:8080/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_stand);
        Intent intent = getIntent();
        String message = "Expo ID is ";
        message = message.concat(intent.getStringExtra(LiveExpositionsActivity.EXTRA_MESSAGE));
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }
}
