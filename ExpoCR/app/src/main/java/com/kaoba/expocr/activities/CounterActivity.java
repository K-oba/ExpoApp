package com.kaoba.expocr.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.kaoba.expocr.R;
import com.kaoba.expocr.Session;
import com.kaoba.expocr.constants.Constants;
import com.kaoba.expocr.models.ExpositionPOJO;

import java.util.ArrayList;

public class CounterActivity extends AppCompatActivity {
    private Session session;
    private Constants constants;
    private static final String COUNTER_PATH = "historial-usuarios-expos/result/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        executeRequest();

        FloatingActionButton button = (FloatingActionButton) findViewById(R.id.btnReloadCounter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeRequest();
            }
        });
    }

    private void executeRequest() {

        session = new Session(getApplicationContext());

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.URL.concat(COUNTER_PATH+"/"+session.getExpoId()),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        TextView text = (TextView) findViewById(R.id.txtCounter);
                        text.setText(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
