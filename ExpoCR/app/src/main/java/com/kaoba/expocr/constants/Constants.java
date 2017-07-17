package com.kaoba.expocr.constants;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by antonirm on 18/6/2017.
 * Class to execute requests.
 */

public class Constants {

    /***Const variables to validate***/
    public static final String URL = "http://10.0.0.147:8080/api/";
    /**
     *http://10.0.0.147:8080
     * http://192.168.40.207:8080
     **/

    /**
     * Method to execute post or put request.
     *
     * @param obj           json to send
     * @param q
     * @param requestMethod type of request (post : 1 or put : 2)
     * @param finalPath     entity to execute (usuarios, stands, etc);
     * @return
     */
    public String executePostPutRequest(final JSONObject obj, RequestQueue q, int requestMethod, String finalPath) throws Exception {
        final String[] s = {""};
        try {
            RequestQueue queue = q;

            StringRequest postRequest = new StringRequest(requestMethod, URL.concat(finalPath),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            s[0] = response;
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            s[0] = error.getMessage().toString();
                        }
                    }
            ) {
                @Override
                public byte[] getBody() throws AuthFailureError {
                    return obj.toString().getBytes();
                }

                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }
            };
            queue.add(postRequest);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return s[0];
    }

    public void executeGetRequest(final VolleyCallBack callBack ,RequestQueue q, String finalPath) {
        RequestQueue queue = q;
        // prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, URL.concat(finalPath), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callBack.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callBack.onError(error.getMessage());
                    }
                }
        );
        // add it to the RequestQueue
        queue.add(getRequest);
    }

    public void executeGetListRequest(final VolleyCallBack callBack ,RequestQueue q, String finalPath) {
        RequestQueue queue = q;
        // prepare the Request
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, URL.concat(finalPath), null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        callBack.onSuccessList(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callBack.onError(error.getMessage());
                    }
                }
        );
        // add it to the RequestQueue
        queue.add(getRequest);
    }
}
