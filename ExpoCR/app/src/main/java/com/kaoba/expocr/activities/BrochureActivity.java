package com.kaoba.expocr.activities;

import android.app.Activity;
import android.graphics.Bitmap;
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
import com.cloudinary.Cloudinary;
import java.util.HashMap;
import java.util.Map;
import com.squareup.picasso.Picasso;


public class BrochureActivity extends Activity{
//    private static final String api = "http://192.168.43.189:8080/api/brouchures/1";
    private static final String BROCHURE_PATH = "brouchures/";
    private Constants constants;
    private static final String NOMBRE = "nombre";
    private static final String DESCRIPCION = "descripcion";
    private static final String IMG= "urlimagen";

    private TextView titulo;
    private TextView descripcion;
    private ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brochure);
        constants = new Constants();
        ListView listview = (ListView) findViewById(R.id.liveListViewPOJO);
        try {
            getBrochureInfo();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void getBrochureInfo() throws JSONException {
        titulo = (TextView) findViewById(R.id.txtTitulo);
        descripcion = (TextView) findViewById(R.id.txtDescripcion);
        image = (ImageView) findViewById(R.id.imageView2);
        final RequestQueue queue = Volley.newRequestQueue(this);
        constants.executeGetRequest(new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    titulo.setText(response.getString("nombre"));
                    descripcion.setText(response.getString("descripcion"));
                    Picasso.with(getApplicationContext()).load("http://res.cloudinary.com/duxllywl7/image/upload/v1499220342/lbzm42t978jvdcfd0aab.png").into(image);

                    //image.setImageURI(Uri.parse(response.getString("urlImagen")));
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
        }, queue, BROCHURE_PATH.concat(getIntent().getStringExtra("idStand")));/** Require id SESSION */
    }

}
