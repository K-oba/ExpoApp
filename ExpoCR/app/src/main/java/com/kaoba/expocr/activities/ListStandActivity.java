package com.kaoba.expocr.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.kaoba.expocr.R;
import com.kaoba.expocr.constants.Constants;
import com.kaoba.expocr.constants.VolleyCallBack;
import com.kaoba.expocr.models.StandPOJO;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ListStandActivity extends AppCompatActivity {
    private static final String EXPO_PATH = "exposicions/";
    private Constants constants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_stand);
        constants = new Constants();

        try {
            getExpoInfo();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getExpoInfo() throws JSONException {
        final RequestQueue queue = Volley.newRequestQueue(this);
        constants.executeGetRequest(new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    JsonParser jsonParser = new JsonParser();
                    JSONObject object;
                    String nombre = response.getString("nombre");
                    JSONArray jsonArray = response.getJSONArray("stands");
                    //JsonArray jsonArray = (JsonArray) jsonParser.parse(response);
                    ArrayList<StandPOJO> arrayList = new ArrayList<>();
                    for(int i = 0; i < jsonArray.length(); i++){
                        StandPOJO stand = new StandPOJO();
                        object = jsonArray.getJSONObject(i);
                        stand.setName(object.getString("nombre"));
                        stand.setBeaconId(object.getLong("beaconId"));
                        stand.setId(object.getLong("id"));
                        arrayList.add(stand);
                        Log.d("Hola",object.getString("nombre"));
                    }
                    ListView listview = (ListView) findViewById(R.id.standListView);
                    ArrayAdapter<StandPOJO> adapter = new ArrayAdapter<>(ListStandActivity.this, android.R.layout.simple_list_item_1, arrayList);
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

            }
        }, queue, EXPO_PATH.concat("1"));/** Require id SESSION */
    }

}
