package com.kaoba.expocr.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.kaoba.expocr.Session;
import com.kaoba.expocr.models.ExpositionPOJO;
import com.kaoba.expocr.R;

import java.util.ArrayList;

public class LiveExpositionsActivity extends AppCompatActivity {

    private static final String URL = "http://192.168.86.235:8080/api/";
    private static final String NAME = "nombre";
    private static final String ID = "id";
    private static final String TAG = "LiveExpo";
    public static final String EXTRA_MESSAGE = "Stand Id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_live_exposition);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        executeRequest();
        ListView listview = (ListView) findViewById(R.id.liveListViewPOJO);
        assert listview != null;
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ExpositionPOJO expositionPOJO = (ExpositionPOJO) parent.getItemAtPosition(position);
                Intent intent = new Intent(LiveExpositionsActivity.this, ShowExpoActivity.class);
                intent.putExtra(EXTRA_MESSAGE, expositionPOJO.getId().toString());
                Session session = new Session(getApplicationContext());
                session.setExpoId(expositionPOJO.getId());
                startActivity(intent);
            }
        });
    }

    private void executeRequest() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL.concat("liveExposicions?page=0&size=50&sort=id"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JsonParser jsonParser = new JsonParser();
                        JsonArray jsonArray = (JsonArray) jsonParser.parse(response);
                        ArrayList<ExpositionPOJO> arrayList = new ArrayList<>();
                        for (JsonElement jsonElement : jsonArray) {
                            ExpositionPOJO expositionPOJO = new ExpositionPOJO();
                            expositionPOJO.setId(jsonElement.getAsJsonObject().get(ID).getAsLong());
                            expositionPOJO.setName(jsonElement.getAsJsonObject().get(NAME).getAsString());
                            arrayList.add(expositionPOJO);
                            Log.d(TAG, "onResponse: ".concat(expositionPOJO.toString()));
                        }
                        Log.d(TAG, "executeRequest");
                        ListView listview = (ListView) findViewById(R.id.liveListViewPOJO);

                        ArrayAdapter<ExpositionPOJO> adapter = new ArrayAdapter<>(LiveExpositionsActivity.this, android.R.layout.simple_list_item_1, arrayList);
                        assert listview != null;
                        listview.setAdapter(adapter);

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