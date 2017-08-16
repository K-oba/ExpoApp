package com.kaoba.expocr.activities;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.kaoba.expocr.R;
import android.app.Activity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.kaoba.expocr.Session;
import com.kaoba.expocr.constants.Constants;
import com.kaoba.expocr.constants.VolleyCallBack;
import com.kaoba.expocr.models.QAPOJO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class QuestionsActivity extends Activity {
    private static final String CHARLA_PATH = "charlas/";
    private static final String USER_PATH = "usuarios/";
    private String NOMBRE;
    private Constants constants;
    private JSONArray preguntas;
    HashMap<String, Object> listItem;
    private String[] requestList = new String[]{};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        constants = new Constants();
        Session session = new Session(getApplicationContext());
        Long idCharla = session.getCharlaId();
        try {
            getCharla(idCharla);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
//
    public void getCharla(Long idCharla) throws JSONException {
        
        final RequestQueue queue = Volley.newRequestQueue(this);
        constants.executeGetRequest(new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    preguntas = response.getJSONArray("preguntas");
                    ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>(response.length());
                    ListView questionsList = (ListView) findViewById(R.id.listViewQuestions);
                    for (int i =0;i<preguntas.length();i++){
                        HashMap<String, String> item = new HashMap<String, String>();
                        Long userId = preguntas.getJSONObject(i).getLong("usuarioId");
                        item.put("Question",preguntas.getJSONObject(i).getString("pregunta"));
                        item.put("User",userId.toString());
                        list.add(item);
                    }
                    getUser(list);
                    //ArrayAdapter<HashMap<String, String>> adapter = new ArrayAdapter<HashMap<String, String>>(QuestionsActivity.this, android.R.layout.simple_list_item_1, list);
                    //ListAdapter customeAdapter = new QACustomeAdapter(QuestionsActivity.this,list);
                    //questionsList.setAdapter(customeAdapter);
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
        }, queue, CHARLA_PATH.concat(idCharla.toString()));/** Require id SESSION */
    }

    public void getUser(ArrayList<HashMap<String, String>> list) throws JSONException {
        final ArrayList<HashMap<String, String>> listSend = new ArrayList<HashMap<String, String>>(list.size());
        ListView questionsList = (ListView) findViewById(R.id.listViewQuestions);
        for(int i = 0; i<list.size();i++){
            String id = list.get(i).get("User");
            final String question = list.get(i).get("Question");
            final RequestQueue queue = Volley.newRequestQueue(this);
            constants.executeGetRequest(new VolleyCallBack() {
                @Override
                public void onSuccess(JSONObject response) {
                    try {
                        HashMap<String, String> item = new HashMap<String, String>();
                        item.put("User", response.getString("nombre"));
                        item.put("Question",question);
                        listSend.add(item);
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
            }, queue, USER_PATH.concat(id));/** Require id SESSION */;
        }
        ListAdapter customeAdapter = new QACustomeAdapter(QuestionsActivity.this,listSend);
        questionsList.setAdapter(customeAdapter);
    }
}
