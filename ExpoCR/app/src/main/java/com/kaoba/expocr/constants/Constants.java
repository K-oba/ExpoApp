package com.kaoba.expocr.constants;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

/**
 * Created by antonirm on 18/6/2017.
 * Class to execute requests.
 */

public class Constants {

    /***Const variables to validate***/
    private static final String URL = "http://10.0.0.147:8080/api/";


    /**
     * Method to execute post or put request.
     * @param obj json to send
     * @param q
     * @param requestMethod type of request (post or put)
     * @param finalPath entity to execute (usuarios, stands, etc);
     * @return
     */
    public void executePostPutRequest(final JSONObject obj, RequestQueue q, int requestMethod, String finalPath) throws Exception {
        try {
            RequestQueue queue = q;

            StringRequest postRequest = new StringRequest(requestMethod, URL.concat(finalPath),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("Response", response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Response",error.getMessage());
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
        }catch (Exception e){
            throw  new Exception(e.getMessage());
        }
    }
}
