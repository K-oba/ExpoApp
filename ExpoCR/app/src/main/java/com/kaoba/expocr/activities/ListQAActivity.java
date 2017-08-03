package com.kaoba.expocr.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.kaoba.expocr.R;
import com.kaoba.expocr.constants.Constants;
import com.kaoba.expocr.constants.VolleyCallBack;
import com.kaoba.expocr.models.QAPOJO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListQAActivity extends AppCompatActivity {
    private static final String CHARLA_PATH = "charlasByExpo/";
    private Constants constants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_qa);
        try {
            getCharlasList();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getCharlasList() throws JSONException {
        final RequestQueue queue = Volley.newRequestQueue(this);
        constants = new Constants();
        constants.executeGetListRequest(new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject response) {

            }
            @Override
            public void onSuccessList(JSONArray response) {
                try {
                    JSONObject object;
                    JSONArray jsonArrayResponse = response;
                    ArrayList<QAPOJO> arrayList = new ArrayList<>();
                    String [] titlesList  = new String[100];

                    for(int i = 0; i < jsonArrayResponse.length(); i++){
                        QAPOJO charla = new QAPOJO();
                        object = jsonArrayResponse.getJSONObject(i);
                        charla.setName(object.getString("nombre"));
                        arrayList.add(charla);
                        titlesList[i]=object.getString("nombre");
                        //Log.d("Hola",object.getJSONObject("beacon").toString());
                    }
                    Log.d("Hola",titlesList[0]);
                    ListView listview = (ListView) findViewById(R.id.listViewCharlas);
                    //ArrayAdapter<QAPOJO> adapter = new ArrayAdapter<>(ListQAActivity.this, android.R.layout.simple_list_item_1, arrayList);
                    ListAdapter adapter = new CustomAdapter(ListQAActivity.this,titlesList);
                    listview.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {

            }
        }, queue, CHARLA_PATH.concat("1"));/** Require id SESSION */
    }
}
