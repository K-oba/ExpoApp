package com.kaoba.expocr.activities;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.kaoba.expocr.R;
import android.app.Activity;
import android.widget.TextView;

import com.kaoba.expocr.constants.Constants;
import com.kaoba.expocr.constants.VolleyCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class QuestionsActivity extends Activity {
    private static final String CHARLA_PATH = "charlas/";
    private static final String EXPO_PATH = "exposicions/";
    private Constants constants;
    private JSONArray preguntas;

    private TextView expoName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        constants = new Constants();
        try {
            getExpo();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getCharla() throws JSONException {
        final RequestQueue queue = Volley.newRequestQueue(this);
        constants.executeGetRequest(new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    preguntas = response.getJSONArray("preguntas");
//                    titulo.setText(response.getString("titulo"));
//                    descripcion.setText(response.getString("descripcion"));
//                    image.setImageURI(Uri.parse(response.getString("urlImagen")));
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
        }, queue, CHARLA_PATH.concat("1"));/** Require id SESSION */
    }

    public void getExpo() throws JSONException {
        final RequestQueue queue = Volley.newRequestQueue(this);
        expoName = (TextView) findViewById(R.id.nombreExpo);
        constants.executeGetListRequest(new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    expoName.setText(response.getString("nombre"));
                }catch (JSONException e){
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
