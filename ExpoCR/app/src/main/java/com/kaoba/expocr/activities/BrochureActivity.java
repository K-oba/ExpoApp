package com.kaoba.expocr.activities;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.kaoba.expocr.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kaoba.expocr.constants.Constants;
import com.kaoba.expocr.constants.VolleyCallBack;


public class BrochureActivity extends Activity{
//    private static final String api = "http://192.168.43.189:8080/api/brouchures/1";
    private static final String BROCHURE_PATH = "brouchures/";
    private Constants constants;
    private static final String NOMBRE = "nombre";
    private static final String DESCRIPCION = "descripcion";
    private static final String IMG= "urlimagen";

    private EditText titulo;
    private EditText descripcion;
    private ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brochure);
    }

    public void getBrochureInfo() throws JSONException {
        final RequestQueue queue = Volley.newRequestQueue(this);
        constants.executeGetRequest(new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    titulo.setText(response.getString("titulo"));
                    descripcion.setText(response.getString("descripcion"));
                    image.setImageURI(Uri.parse(response.getString("urlImagen")));
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
        }, queue, BROCHURE_PATH.concat("1"));/** Require id SESSION */
    }

}
