package com.kaoba.expocr.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;
import com.kaoba.expocr.R;
import com.kaoba.expocr.Session;
import com.kaoba.expocr.constants.Constants;
import com.kaoba.expocr.constants.VolleyCallBack;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

/**
 * Created by Antoni Ramirez on 21/6/2017.
 */

public class ViewExpoByCurrentDay extends AppCompatActivity {

    ListView listView;
    Session session;
    TextView text;

    private String finalFath = "exposicions/byDay/";
    private Constants constants;
    private long[] idsexpos;
    private long expoId;

    public String getFinalFath() {
        return finalFath;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_day_expo);

        constants = new Constants();

        session = new Session(this);

        ImageView image = (ImageView) findViewById(R.id.imageexpolist);
        Picasso.with(getApplicationContext()).load("http://res.cloudinary.com/duxllywl7/image/upload/v1500393003/beacon-illustration-1024x673_bfpl6y.jpg").into(image);
        ImageView logo = (ImageView) findViewById(R.id.imageimagelogoexpo);
        Picasso.with(getApplicationContext()).load("http://res.cloudinary.com/duxllywl7/image/upload/v1500361962/expocr-vale_720_mnb6qb.png").into(logo);

        listView = (ListView) findViewById(R.id.listView);
        text = (TextView) findViewById(R.id.aux);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        Date date = new Date();
        String parameter = dateFormat.format(date);

        executeRequest(parameter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(getApplicationContext(), ShowExpoActivity.class);
                session.setExpoId(idsexpos[position]);

                intent.putExtra(EXTRA_MESSAGE, Long.toString(expoId));
                startActivity(intent);
            }
        });
    }

    private void executeRequest(String parameters) {
        constants.executeGetListRequest(new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject response) {

            }

            @Override
            public void onSuccessList(JSONArray response) {
                ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>(response.length());
                idsexpos = new long[response.length()];

                for (int i = 0; i < response.length(); i++) {
                    try {
                        HashMap<String, String> item = new HashMap<String, String>();
                        item.put("Name", response.getJSONObject(i).getString("nombre"));
                        item.put("Date", "Start " + response.getJSONObject(i).getString("fechaInicio") + " End " + response.getJSONObject(i).getString("fechaFin"));
                        expoId = response.getJSONObject(i).getLong("id");
                        idsexpos[i] = response.getJSONObject(i).getLong("id");
                        list.add(item);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if(list.size() <= 0){

                    text.setVisibility(View.VISIBLE);
                    text.setText("There are no expos in progress");

                }
                ListAdapter customeAdapter = new ExpoFilterCustomeAdapter(getApplicationContext(), list);
                listView.setAdapter(customeAdapter);
            }

            @Override
            public void onError(String error) {

            }
        }, Volley.newRequestQueue(getApplicationContext()), getFinalFath().concat(parameters));
    }
}
