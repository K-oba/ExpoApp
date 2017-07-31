package com.kaoba.expocr.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.kaoba.expocr.R;
import com.kaoba.expocr.constants.Constants;
import com.kaoba.expocr.constants.VolleyCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Antoni Ramirez on 21/6/2017.
 */

public class ViewUserActivity extends AppCompatActivity {

    private int rol = 3;
    private static final String EXPO_PATH = "exposicions/user/";
    private static final String USUARIO_PATH = "usuarios/";
    private Constants constants;


    /**
     *
     **/
    private TextView nameUser;
    private TextView emailUser;
    private ListView listView;
    ArrayAdapter<String> adapter;
    HashMap<String, Object> listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        listItem = new HashMap<String, Object>();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);

        nameUser = (TextView) findViewById(R.id.textNamePerson);
        emailUser = (TextView) findViewById(R.id.textEmailPerson);
        listView = (ListView) findViewById(R.id.listView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView.setAdapter(adapter);
        constants = new Constants();
        try {
            loadUserInfo();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void loadUserInfo() throws JSONException {
        final RequestQueue queue = Volley.newRequestQueue(this);
        constants.executeGetRequest(new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    nameUser.setText(response.getString("nombre"));
                    emailUser.setText(response.getString("correo"));
                    if (rol == 3) {
                        adapter.add(response.getString("nombreStand"));
                    }else loadExpoInfo();

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
        }, queue, USUARIO_PATH.concat("4"));/** Require id SESSION */
    }

    private void loadExpoInfo()throws  JSONException{
        final RequestQueue queue = Volley.newRequestQueue(this);
        if (rol == 2) {
            constants.executeGetListRequest(new VolleyCallBack() {
                @Override
                public void onSuccess(JSONObject response) {
                }

                @Override
                public void onSuccessList(JSONArray response) {
                }

                @Override
                public void onError(String error) {

                }
            }, queue, EXPO_PATH.concat("1"));
        }
    }


}
