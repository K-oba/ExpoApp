package com.kaoba.expocr.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.kaoba.expocr.R;
import com.kaoba.expocr.Session;
import com.kaoba.expocr.constants.Constants;
import com.kaoba.expocr.constants.VolleyCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShowExpoActivity extends AppCompatActivity {
    private Constants constants;
    private String TAG = "ShowExpo";

    ArrayAdapter<String> adapter;
    private static final String EXPO_PATH = "exposicions/";
    private String coordenadas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_expo);
        constants = new Constants();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        String message = "Expo ID is ";
//        message = message.concat(intent.getStringExtra(LiveExpositionsActivity.EXTRA_MESSAGE));
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        message = "Expo ID from session ";
        Session session = new Session(getApplicationContext());
        message = message.concat(session.getExpoId().toString());
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        try {
            loadExpoInfo(session.getExpoId());
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, R.string.network_error, Toast.LENGTH_SHORT).show();
        }
    }

    private void loadExpoInfo(Long expoId) throws JSONException {
        final RequestQueue queue = Volley.newRequestQueue(this);
        constants.executeGetRequest(new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    ArrayList<String> items = new ArrayList<>();
                    items.add(response.getString("nombre"));
                    items.add(response.getString("descripcion"));
                    items.add(response.getString("fechaInicio"));
                    items.add(response.getString("fechaFin"));
                    coordenadas = response.getString("coordenadas");

                    adapter = new ArrayAdapter<>(ShowExpoActivity.this, android.R.layout.simple_list_item_1, items);
                    ListView listview = (ListView) findViewById(R.id.ViewExpoList);
                    assert listview != null;
                    listview.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onSuccessList(JSONArray response) {

            }

            @Override
            public void onError(String error) {
                Log.e(TAG, error);
            }
        }, queue, EXPO_PATH.concat(expoId.toString()));
    }
}
