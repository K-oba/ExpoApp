package com.kaoba.expocr.activities;

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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.kaoba.expocr.R;
import com.kaoba.expocr.Session;
import com.kaoba.expocr.constants.Constants;
import com.kaoba.expocr.constants.VolleyCallBack;
import com.kaoba.expocr.estimote.BeaconAppManager;
import com.kaoba.expocr.models.StandPOJO;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShowExpoActivity extends AppCompatActivity {
    private Constants constants;
    private String TAG = "ShowExpo";

    ArrayAdapter<Object> adapter;
    private static final String EXPO_PATH = "exposicions/";
    private static final String NAME = "nombre";
    private static final String ID = "id";
    public static final String EXTRA_MESSAGE = "Stand Id";
    private String coordenadas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_expo);
        constants = new Constants();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Session session = new Session(getApplicationContext());
        try {
            loadExpoInfo(session.getExpoId());
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, R.string.network_error, Toast.LENGTH_SHORT).show();
        }
        ListView listview = (ListView) findViewById(R.id.ViewExpoList);
        //ImageView logo = (ImageView) findViewById(R.id.imageView6);
        //Picasso.with(getApplicationContext()).load("http://res.cloudinary.com/duxllywl7/image/upload/v1500407409/beacons-100635065-primary.idge_paxysr.jpg").into(logo);
        assert listview != null;
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (parent.getItemAtPosition(position) instanceof StandPOJO) {
                    StandPOJO standPOJO = (StandPOJO) parent.getItemAtPosition(position);
                    Intent intent = new Intent(ShowExpoActivity.this, ShowStandActivity.class);
                    intent.putExtra(EXTRA_MESSAGE, standPOJO.getId().toString());
                    startActivity(intent);
                }
            }
        });
//
//        SupportMapFragment mapFragment =
//                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
    }

    private void loadExpoInfo(Long expoId) throws JSONException {
        final RequestQueue queue = Volley.newRequestQueue(this);
        constants.executeGetRequest(new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    ArrayList<Object> items = new ArrayList<>();
                    items.add(response.getString(NAME));
                    items.add(response.getString("descripcion"));
                    String start = response.getString("fechaInicio").substring(0, 10);
                    String end = response.getString("fechaFin").substring(0, 10);
                    items.add("From ".concat(start).concat(" to ").concat(end));
                    coordenadas = response.getString("coordenadas");

                    if (response.getJSONArray("stands").length() > 0) {
                        JSONArray jsonArray = response.getJSONArray("stands");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            StandPOJO standPOJO = new StandPOJO();
                            standPOJO.setName(jsonArray.getJSONObject(i).getString(NAME));
                            standPOJO.setId(jsonArray.getJSONObject(i).getLong(ID));
                            items.add(standPOJO);
                        }
                    } else {
                        items.add(getString(R.string.expo_no_stands));
                    }
                    //ImageView logo = (ImageView) findViewById(R.id.imageView6);
                    //Picasso.with(getApplicationContext()).load("http://res.cloudinary.com/duxllywl7/image/upload/v1500407409/beacons-100635065-primary.idge_paxysr.jpg").into(logo);
                    adapter = new ArrayAdapter<>(ShowExpoActivity.this, android.R.layout.simple_list_item_1, items);
                    ListView listview = (ListView) findViewById(R.id.ViewExpoList);
                    assert listview != null;
                    listview.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                SupportMapFragment mapFragment =
//                        (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//                mapFragment.getMapAsync(ShowExpoActivity.this);
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

    @Override
    protected void onResume() {
        super.onResume();

        BeaconAppManager manager = (BeaconAppManager) getApplication();

        if (!SystemRequirementsChecker.checkWithDefaultDialogs(this)){
            Log.d("ERROR",getString(R.string.error_welcome_screen_permission));
        }else if(!manager.isBeaconNotificationsEnabled()){
            manager.enableBeaconNotifications();
        }
    }

//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        double[] latLng = new double[2];
//        latLng[0] = Double.parseDouble(coordenadas.split("[1234567890.-]")[0]);
//        latLng[1] = Double.parseDouble(coordenadas.split("[1234567890.-]")[1]);
//
//        googleMap.addMarker(new MarkerOptions().position(new LatLng(latLng[0], latLng[1])).title("Marker"));
//    }
}
