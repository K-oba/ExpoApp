package com.kaoba.expocr.activities;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
import com.kaoba.expocr.constants.Constants;
import com.kaoba.expocr.models.ExpositionPOJO;
import com.kaoba.expocr.R;

import java.util.ArrayList;
import com.squareup.picasso.Picasso;

public class LiveExpositionsActivity extends Activity {

    private static final String NAME = "nombre";
    private static final String ID = "id";
    private static final String TAG = "LiveExpo";
    public static final String EXTRA_MESSAGE = "Expo Id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_live_exposition);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        executeRequest();
        //
        ListView listview = (ListView) findViewById(R.id.liveListViewPOJO);
        assert listview != null;
        ImageView image = (ImageView) findViewById(R.id.imageexpolist);
        Picasso.with(getApplicationContext()).load("http://res.cloudinary.com/duxllywl7/image/upload/v1500393003/beacon-illustration-1024x673_bfpl6y.jpg").into(image);
        ImageView logo = (ImageView) findViewById(R.id.imageimagelogoexpo);
        Picasso.with(getApplicationContext()).load("http://res.cloudinary.com/duxllywl7/image/upload/v1500361962/expocr-vale_720_mnb6qb.png").into(logo);
        //
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
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.URL.concat("liveExposicions?page=0&size=50&sort=id"),
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
                        //ImageView image = (ImageView) findViewById(R.id.imageView5);
                        //Picasso.with(getApplicationContext()).load("http://res.cloudinary.com/duxllywl7/image/upload/v1500361962/expocr-vale_720_mnb6qb.png").into(image);
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