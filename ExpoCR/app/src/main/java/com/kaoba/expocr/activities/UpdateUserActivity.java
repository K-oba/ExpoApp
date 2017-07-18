package com.kaoba.expocr.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.kaoba.expocr.R;
import com.kaoba.expocr.constants.Constants;

import org.json.JSONObject;


/**
 * Created by antonirm on 11/6/2017.
 */

public class UpdateUserActivity extends Activity {


    /***const variables to json***/
    private static final String NOMBRE = "nombre";
    private static final String ID = "id";
    private static final String CLAVE = "clave";
    private static final String CORREO = "correo";

    private static final int REQUEST_METHOD = 2;
    private static final String FINAL_PATH = "usuarios";
    private static final int UPDATE_ACTIVITY = 2;

    private Constants constants;

    /**
     * Auxiliar
     **/
    private static boolean isOk = false;

    private EditText mNameView;
    private Button updateUserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        constants = new Constants();
        setContentView(R.layout.activity_update_user);
        mNameView = (EditText) findViewById(R.id.txtName);
        updateUserButton = (Button) findViewById(R.id.btnUpdate);

        updateUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    makePutRequest();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void makePutRequest() throws Exception {

        // Reset errors.
        mNameView.setError(null);

        String name = mNameView.getText().toString();

        //if not error do the post logic
        Toast toast;
        if (isNameValid(name)) {
            JSONObject obj = new JSONObject();
            obj.put(NOMBRE, name);
            obj.put(ID, 1);
            obj.put(CORREO, "antoniramirezm@gmail.com");
            obj.put(CLAVE, "antoniramirezzm@gmail.com");
            RequestQueue queue = Volley.newRequestQueue(this);
            constants.executePostPutRequest(obj, queue, REQUEST_METHOD, FINAL_PATH, getApplicationContext(),UPDATE_ACTIVITY);
            updateUserButton.setVisibility(View.GONE);
            mNameView.setEnabled(false);
            toast = Toast.makeText(getApplicationContext(), "Update successful", Toast.LENGTH_SHORT);

//            }else{
//                toast = Toast.makeText(getApplicationContext(),"An error occurred",Toast.LENGTH_SHORT);
//            }
            toast.show();
        }
    }

    private boolean isNameValid(String name) {
        if (name.isEmpty()) {
            mNameView.setError(getString(R.string.error_field_required));
            mNameView.requestFocus();
        } else
            isOk = true;
        return isOk;
    }

}