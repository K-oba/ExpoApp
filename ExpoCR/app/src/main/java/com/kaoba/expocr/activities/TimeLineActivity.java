package com.kaoba.expocr.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.kaoba.expocr.R;
import com.kaoba.expocr.Session;
import com.kaoba.expocr.constants.Constants;
import com.kaoba.expocr.constants.VolleyCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TimeLineActivity extends AppCompatActivity {

    private Constants constants;
    private static final String TIMELINE_PATH = "timeLineByExpo/";
    private static final String POSTS_PATH = "getPostByTimeLine/";
    private static final String ADDPOST_PATH = "posts";
    private ListAdapter customeAdapter;
    private ArrayList<HashMap<String, String>> list;
    private String idTimeLine = "";
    private String TIMELINEID = "ID";
    private String MENSAJE = "MNJ";
    private String IMAGEN = "URL";
    private ListView postListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Session session = new Session(getApplicationContext());
        if(session.getUserId() != null){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_time_line);
            Button postButton = (Button) findViewById(R.id.btnPublish);

            try {
                getTimeLine();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            postButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        addPost();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            });
        }
    }

    public void getTimeLine() throws JSONException {
        final RequestQueue queue = Volley.newRequestQueue(this);
        constants = new Constants();
        constants.executeGetListRequest(new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject response) {

            }
            @Override
            public void onSuccessList(JSONArray response) {
                Session session = new Session(getApplicationContext());
                try {
                    JSONObject object;
                    JSONArray jsonArrayResponse = response;
                    object = jsonArrayResponse.getJSONObject(0);
                    Long timeLineId = object.getLong("id");
                    session.setTimelineId(timeLineId);
                    String userName = session.getUsername();
                    TextView txtusername = (TextView) findViewById(R.id.textViewTLUserName);
                    txtusername.setText(userName);
                    Log.d("User Name",userName);
                    getPosts(timeLineId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {

            }
        }, queue, TIMELINE_PATH.concat("1"));/** Require id SESSION */
    }

    public void getPosts(Long timeLine) throws JSONException {
        final RequestQueue queue = Volley.newRequestQueue(this);
        constants = new Constants();
        constants.executeGetListRequest(new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject response) {

            }
            @Override
            public void onSuccessList(JSONArray response) {
                try {
                    JSONArray postList = response;
                    list = new ArrayList<HashMap<String, String>>(response.length());
                    postListView = (ListView) findViewById(R.id.timeLineListView);
                    for (int i =0;i<response.length();i++){
                        HashMap<String, String> item = new HashMap<String, String>();
                        item.put("State",response.getJSONObject(i).getString("mensaje"));
//                      item.put("Image",response.getJSONObject(i).getString("imagen"));
                        list.add(item);
                    }
                    //customeAdapter = new QACustomeAdapter(QuestionsActivity.this,listSend);
                    customeAdapter = new TimeLineCustomeAdapter(TimeLineActivity.this,list);
                    postListView.setAdapter(customeAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {

            }
        }, queue, POSTS_PATH.concat(timeLine.toString()));/** Require id SESSION */
    }

    public void addPost() throws Exception{
        EditText postText = (EditText) findViewById(R.id.PosteditText);

        if(postText.getText().toString().isEmpty() == false){
            HashMap<String, String> item = new HashMap<String, String>();
            item.put("State", postText.getText().toString());
            //item.put("Image",questionText.getText().toString());
            list.add(item);
            customeAdapter = new TimeLineCustomeAdapter(TimeLineActivity.this,list);
            postListView.setAdapter(customeAdapter);
            try {
                Session session = new Session(getApplicationContext());
                JSONObject obj = new JSONObject();
                JSONObject response = new JSONObject();
                obj.put("mensaje", postText.getText().toString());
                obj.put("timelineId", session.getTimelineId());
                Log.d("OBJ",obj.toString());
                //obj.put("IMAGEN", postText.getText().toString());
                constants.executePostPutRequest(obj, Volley.newRequestQueue(this), 1, ADDPOST_PATH, getApplicationContext(), 2);
            }catch (Exception e){
                throw new Exception(e.getMessage());
            }

        }else{
            Log.d("Nooo","error");
        }

    }
}
