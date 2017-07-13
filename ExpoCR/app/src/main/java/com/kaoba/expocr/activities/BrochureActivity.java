package com.kaoba.expocr.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kaoba.expocr.R;

public class BrochureActivity extends AppCompatActivity {
    private static final String api = "http://192.168.43.189:8080/api/brouchures/1";
    private static final String NOMBRE = "nombre";
    private static final String DESCRIPCION = "descripcion";
    private static final String IMG= "urlimagen";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brochure);
    }
}
