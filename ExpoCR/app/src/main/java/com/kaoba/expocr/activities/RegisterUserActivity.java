package com.kaoba.expocr.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kaoba.expocr.R;
import com.kaoba.expocr.constants.Constants;

import org.json.JSONObject;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

/**
 * Created by antonirm on 11/6/2017.
 */

public class RegisterUserActivity extends AppCompatActivity {


    /***Const variables to validate***/
    private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-zA-Z])(?=.*[@#$%*/&^%!]).{4,20})";

    /***const variables to json***/
    private static final String NOMBRE = "nombre";
    private static final String CLAVE = "clave";
    private static final String CORREO = "correo";

    private static final int REQUEST_METHOD = 1;
    private static final String FINAL_PATH = "usuarios";
    private static final int REGISTER_ACTIVITY = 1;

    private Constants constants;

    /**
     * Auxiliar
     **/
    private static boolean isOk = false;
    private static final String PASSWORD_FORMAT = "Must contain one digit 0-9, One character, One special symbol. At least 4 digits.";

    private EditText mNameView;
    private EditText mEmailView;
    private EditText mPasswordView;
    private EditText mConfirmationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        constants = new Constants();
        setContentView(R.layout.activity_register_user);
        mEmailView = (EditText) findViewById(R.id.txtEmail);
        mPasswordView = (EditText) findViewById(R.id.txtPassword);
        mNameView = (EditText) findViewById(R.id.txtName);
        mConfirmationView = (EditText) findViewById(R.id.txtConfirmation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button createUserButton = (Button) findViewById(R.id.btnCreate);
        createUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    makePostRequest();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
        mPasswordView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mPasswordView.setError(null);
                if (hasFocus)
                    mPasswordView.setError(PASSWORD_FORMAT);
            }
        });
    }

    private void makePostRequest() throws Exception {
        try {
            // Reset errors.
            mEmailView.setError(null);
            mPasswordView.setError(null);
            mConfirmationView.setError(null);
            mNameView.setError(null);
            String email = mEmailView.getText().toString();
            String password = mPasswordView.getText().toString();
            String confirmation = mConfirmationView.getText().toString();
            String name = mNameView.getText().toString();

            //if not error do the post logic
            if (isNameValid(name) && isEmailValid(email) && isPasswordValid(password, confirmation)) {
                JSONObject obj = new JSONObject();
                obj.put(NOMBRE, name);
                obj.put(CLAVE, password);
                obj.put(CORREO, email);
                constants.executePostPutRequest(obj, Volley.newRequestQueue(this), REQUEST_METHOD, FINAL_PATH, getApplicationContext(),REGISTER_ACTIVITY);
//                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                intent.putExtra(EXTRA_MESSAGE, "");
//                startActivity(intent);
            }
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

//    private void executeRequest(final JSONObject obj) {
//
//        RequestQueue queue = Volley.newRequestQueue(this);
//        StringRequest postRequest = new StringRequest(Request.Method.POST, URL.concat("usuarios"),
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        // response
//                        Log.d("Response", response);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // error
//                        Log.d("Error.Response", error.getMessage());
//                    }
//                }
//        ) {
//            @Override
//            public byte[] getBody() throws AuthFailureError {
//                return obj.toString().getBytes();
//            }
//
//            @Override
//            public String getBodyContentType() {
//                return "application/json";
//            }
//        };
//        queue.add(postRequest);
//
//    }

    private boolean isNameValid(String name) {
        if (name.isEmpty()) {
            mNameView.setError(getString(R.string.error_field_required));
            mNameView.requestFocus();
        } else
            isOk = true;
        return isOk;
    }

    private boolean isEmailValid(String email) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            mEmailView.requestFocus();
        } else if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            mEmailView.requestFocus();
        } else
            isOk = true;
        return isOk;
    }

    private boolean isPasswordValid(String password, String confirmation) {
        if (password.isEmpty()) {
            mPasswordView.setError("This field is required");
            mPasswordView.requestFocus();
            isOk = false;
        } else if (confirmation.isEmpty()) {
            mConfirmationView.setError("This field is required");
            mConfirmationView.requestFocus();
            isOk = false;
        } else if (!password.equals(confirmation)) {
            mConfirmationView.setError("Passwords doesn't match");
            mConfirmationView.requestFocus();
            isOk = false;
        } else if (!password.matches(PASSWORD_PATTERN)) {
            mPasswordView.setError("Incorrect format");
            mPasswordView.requestFocus();
            isOk = false;
        } else
            isOk = true;
        return isOk;
    }
}
