package com.kaoba.expocr.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.kaoba.expocr.R;
import com.kaoba.expocr.constants.Constants;
import com.kaoba.expocr.constants.VolleyCallBack;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShowStandActivity extends AppCompatActivity {

    private String TAG = "ShowStand";

    ArrayAdapter<String> adapter;
    private static final String STAND_PATH = "stands/";
    private static final String NAME = "nombre";
    private static final String ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_stand);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        Long standId = Long.valueOf(intent.getStringExtra("Stand Id"));
        try {
            loadStandInfo(standId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void loadStandInfo(Long standId) throws JSONException {
        final RequestQueue queue = Volley.newRequestQueue(this);
        Constants constants = new Constants();
        constants.executeGetRequest(new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject response) {
                ArrayList<String> items = new ArrayList<>();
                try {
                    items.add(response.getString(NAME));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter = new ArrayAdapter<>(ShowStandActivity.this, android.R.layout.simple_list_item_1, items);
                ListView listview = (ListView) findViewById(R.id.showStandList);
                assert listview != null;
                listview.setAdapter(adapter);

            }

            @Override
            public void onSuccessList(JSONArray response) {
            }

            @Override
            public void onError(String error) {
                Log.e(TAG, error);
            }
        }, queue, STAND_PATH.concat(standId.toString()));
    }
}
