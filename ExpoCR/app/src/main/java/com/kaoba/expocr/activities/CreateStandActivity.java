package com.kaoba.expocr.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kaoba.expocr.R;

import org.json.JSONObject;

public class CreateStandActivity extends AppCompatActivity {

    private static final String NAME = "nombre";
    private static final String TYPE = "tipo";
    private static final String URL = "http://10.0.0.147:8080/api/";

    private EditText mName;
    private EditText mType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_stand);
        this.mName  = (EditText) findViewById(R.id.txtName);
        this.mType = (EditText) findViewById(R.id.txtType);
        Button createStandButton = (Button) findViewById(R.id.btnCreateStand);
        createStandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    makePostRequest();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
    }
    private void makePostRequest() throws Exception {

        mName.setError(null);
        mType.setError(null);

        if (isFormValid()) {
            JSONObject obj = new JSONObject();
            obj.put(NAME, mName.getText().toString());
            obj.put(TYPE, mType.getText().toString());
            executeRequest(obj);
        }
    }

    private boolean isFormValid(){
            if (mName.getText().toString().isEmpty()) {
                    mName.setError(getString(R.string.error_field_required));
                    mName.requestFocus();
                    return false;
            }
            if(mType.getText().toString().isEmpty()) {
                mType.setError(getString(R.string.error_field_required));
                mType.requestFocus();
                return false;
            }
            return true;
    }
    private void executeRequest(final JSONObject obj) {

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL.concat("stands"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.getMessage());
                    }
                }
        ) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                return obj.toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        queue.add(postRequest);

    }
}
