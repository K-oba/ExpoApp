package com.kaoba.expocr.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kaoba.expocr.R;
import com.kaoba.expocr.Session;
import com.kaoba.expocr.constants.Constants;
import com.kaoba.expocr.constants.VolleyCallBack;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import com.squareup.picasso.Picasso;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

/**
 * Created by Antoni Ramirez on 21/6/2017.
 */

public class ViewExpoByFilter extends AppCompatActivity {

    ListView listView;
    Session session;
    TextView startDate;
    TextView endDate;
    EditText name;

    int mYear, mMonth, mDay;
    private String finalFath = "exposicions/byFilters/";
    private Constants constants;
    private long expoId;
    private static final String DEFAULT_START_DATE = "01-01-1900";
    private static final String DEFAULT_END_DATE = "01-01-2030";

    public String getFinalFath() { return finalFath; }

    public void setFinalFath(String finalFath) {
        this.finalFath = finalFath;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expo_filter);

        startDate = (TextView) findViewById(R.id.startDate);
        endDate = (TextView) findViewById(R.id.endDate);
        name = (EditText) findViewById(R.id.name);

        constants = new Constants();
        listView = (ListView) findViewById(R.id.listView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setFinalFath("exposicions");
        executeRequest("");

        ImageView imgBtnFilter = (ImageView) findViewById(R.id.btnSearchExpoFilter);
        Picasso.with(getApplicationContext()).load("http://res.cloudinary.com/duxllywl7/image/upload/v1501798791/magnify_1_q4zusz.png").into(imgBtnFilter);
        ImageView imgBtnStartDate = (ImageView) findViewById(R.id.imgStartDate);
        Picasso.with(getApplicationContext()).load("http://res.cloudinary.com/duxllywl7/image/upload/v1501798071/calendar-question_2_qshtyf.png").into(imgBtnStartDate);
        ImageView imgBtnEndDate = (ImageView) findViewById(R.id.imgEndDate);
        Picasso.with(getApplicationContext()).load("http://res.cloudinary.com/duxllywl7/image/upload/v1501798075/calendar-range_2_holjdv.png").into(imgBtnEndDate);
        imgBtnFilter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

                    if (!(String.valueOf(startDate.getText()) == "" && String.valueOf(endDate.getText()) != "") && !(String.valueOf(startDate.getText()) != "" && String.valueOf(endDate.getText()) == "")) {
                        Date initialDate;
                        Date finalDate;

                        String expoName = String.valueOf(name.getText());
                        String parameters = "";
                        String start = String.valueOf(startDate.getText()) != "" ? String.valueOf(startDate.getText()) : DEFAULT_START_DATE;
                        String end = String.valueOf(endDate.getText()) != "" ? String.valueOf(endDate.getText()) : DEFAULT_END_DATE;
                        initialDate = dateFormat.parse(start);
                        finalDate = dateFormat.parse(end);

                        if(finalDate.after(initialDate) || finalDate.equals(initialDate)) {
                            if (!expoName.isEmpty() && start.equals(DEFAULT_START_DATE) && end.equals(DEFAULT_END_DATE)) {
                                setFinalFath("exposicions/likeName/");
                                parameters = expoName;
                            } else if (expoName.isEmpty() && !start.equals(DEFAULT_START_DATE) && !end.equals(DEFAULT_END_DATE)) {
                                setFinalFath("exposicions/betweenDates/"); //TODO make the endpoint
                                parameters = start + "/" + end;
                            } else {
                                setFinalFath("exposicions/byFilters/");
                                parameters = start + "/" + end + "/" + expoName;
                            }
                            executeRequest(parameters);
                        }else
                            Toast.makeText(getApplicationContext(), "End date can't be less than start date", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getApplicationContext(), "You need select both dates", Toast.LENGTH_SHORT).show();

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

//        startDate.setMinDate(new Date());
        startDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();

                mYear = mcurrentDate.get(Calendar.YEAR);
                mMonth = mcurrentDate.get(Calendar.MONTH);
                mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(ViewExpoByFilter.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        selectedmonth = selectedmonth + 1;
                        String month = String.valueOf(selectedmonth).length() == 1 ? "0" + selectedmonth : String.valueOf(selectedmonth);
                        String day = String.valueOf(selectedday).length() == 1 ? "0" + selectedday : String.valueOf(selectedday);
                        startDate.setText(day + "-" + month + "-" + selectedyear);
                    }
                }, mYear, mMonth, mDay);

                mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }
        });

        endDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                mYear = mcurrentDate.get(Calendar.YEAR);
                mMonth = mcurrentDate.get(Calendar.MONTH);
                mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(ViewExpoByFilter.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        selectedmonth = selectedmonth + 1;
                        String month = String.valueOf(selectedmonth).length() == 1 ? "0" + selectedmonth : String.valueOf(selectedmonth);
                        String day = String.valueOf(selectedday).length() == 1 ? "0" + selectedday : String.valueOf(selectedday);
                        endDate.setText(day + "-" + month + "-" + selectedyear);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();
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

                for (int i = 0; i < response.length(); i++) {
                    try {
                        HashMap<String, String> item = new HashMap<String, String>();
                        item.put("Name", response.getJSONObject(i).getString("nombre"));
                        item.put("Date", "Start " + response.getJSONObject(i).getString("fechaInicio") + " End " + response.getJSONObject(i).getString("fechaFin"));
                        expoId = response.getJSONObject(i).getLong("id");
                        list.add(item);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                ListAdapter customeAdapter = new ExpoFilterCustomeAdapter(getApplicationContext(),list);
                listView.setAdapter(customeAdapter);
            }

            @Override
            public void onError(String error) {

            }
        }, Volley.newRequestQueue(getApplicationContext()), getFinalFath().concat(parameters));
    }
}
