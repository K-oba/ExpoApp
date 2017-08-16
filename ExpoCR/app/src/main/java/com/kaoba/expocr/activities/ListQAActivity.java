package com.kaoba.expocr.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.kaoba.expocr.R;
import com.kaoba.expocr.Session;
import com.kaoba.expocr.constants.Constants;
import com.kaoba.expocr.constants.VolleyCallBack;
import com.kaoba.expocr.models.ExpositionPOJO;
import com.kaoba.expocr.models.QAPOJO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListQAActivity extends AppCompatActivity {
    private static final String CHARLA_PATH = "charlasByExpo/";
    private Constants constants;
    //public static final String EXTRA_MESSAGE = "Expo Id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_qa);
        ListView charlasList = (ListView) findViewById(R.id.listViewCharlas);
        try {
            getCharlasList();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        charlasList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                QAPOJO charlaPOJO = (QAPOJO) parent.getItemAtPosition(position);
                Intent intent = new Intent(ListQAActivity.this,QuestionsActivity.class);
                intent.putExtra("charlaId", charlaPOJO.getId().toString());
                Session session = new Session(getApplicationContext());
                session.setCharlaId(charlaPOJO.getId());
                Log.d("CharlaID",session.getCharlaId().toString());
                startActivity(intent);
            }
        });

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
                        charla.setId(object.getLong("id"));
                        arrayList.add(charla);
                        titlesList[i]=object.getString("nombre");
                        //Log.d("Hola",object.getJSONObject("beacon").toString());
                    }
                    //Log.d("Hola",titlesList[0]);
                    ListView listview = (ListView) findViewById(R.id.listViewCharlas);
                    ArrayAdapter<QAPOJO> adapter = new ArrayAdapter<>(ListQAActivity.this, android.R.layout.simple_list_item_1, arrayList);
                    //ListAdapter adapter = new CustomAdapter(ListQAActivity.this,titlesList);
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
