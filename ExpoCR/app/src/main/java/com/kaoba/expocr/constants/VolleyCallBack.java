package com.kaoba.expocr.constants;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Antoni Ramirez on 25/6/2017.
 */

public interface VolleyCallBack {
    //used when return a single object
    void onSuccess(JSONObject response);
    //Used when return a list of objects.
    void onSuccessList(JSONArray response);
    //Used to handling errors
    void onError(String error);
}
