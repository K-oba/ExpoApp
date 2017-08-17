package com.kaoba.expocr.constants;

import android.content.Context;
import android.content.Intent;
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
import com.kaoba.expocr.activities.LoginActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.kaoba.expocr.activities.LiveExpositionsActivity.EXTRA_MESSAGE;

/**
 * Created by antonirm on 18/6/2017.
 * Class to execute requests.
 */

public class Constants {

    /***Const variables to validate***/
    public static final String URL = "http://192.168.10.132:8080/api/";
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
    public void executePostPutRequest(final JSONObject obj, RequestQueue q, int requestMethod, String finalPath, final Context context, final int activity) throws Exception {
        try {
            RequestQueue queue = q;

            StringRequest postRequest = new StringRequest(requestMethod, URL.concat(finalPath),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(activity == 1){
                                Intent intent = new Intent(context, LoginActivity.class);
                                intent.putExtra(EXTRA_MESSAGE, "");
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                            if(activity ==2){
                                Log.d("Stay","stay");
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context,error.getMessage(), Toast.LENGTH_LONG).show();
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
    }

    public void executeGetRequest(final VolleyCallBack callBack, RequestQueue q, String finalPath) {
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

    public void executeGetListRequest(final VolleyCallBack callBack, RequestQueue q, String finalPath) {
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
