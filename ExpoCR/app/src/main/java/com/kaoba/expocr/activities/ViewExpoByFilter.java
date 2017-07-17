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
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;

import com.android.volley.toolbox.Volley;
import com.kaoba.expocr.R;
import com.kaoba.expocr.constants.Constants;
import com.kaoba.expocr.constants.VolleyCallBack;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

/**
 * Created by Antoni Ramirez on 21/6/2017.
 */

public class ViewExpoByFilter extends AppCompatActivity {

    SearchView searchView;
    ListView listView;
//    EditText editText;
//    int mYear, mMonth,mDay;
    private String finalFath = "exposicions/findByFilters/";
    private Constants constants;
    private HashMap<String, String> item = new HashMap<String, String>();
    public String getFinalFath() {
        return finalFath;
    }

    public void setFinalFath(String finalFath) {
        this.finalFath = finalFath;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expo_filter);

//        editText = (EditText) findViewById(R.id.edittext);
        constants = new Constants();
        listView = (ListView) findViewById(R.id.listView);
        searchView=(SearchView) findViewById(R.id.searchView);
        searchView.setQueryHint("Search View");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                executeRequest(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty())
                    setFinalFath("exposicions");
                else
                    setFinalFath("exposicions/likeName/");
                executeRequest(newText);
                return false;
            }
        });
        //TODO
//        editText.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                //To show current date in the datepicker
//                Calendar mcurrentDate=Calendar.getInstance();
//                mYear=mcurrentDate.get(Calendar.YEAR);
//                mMonth=mcurrentDate.get(Calendar.MONTH);
//                mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);
//
//                DatePickerDialog mDatePicker=new DatePickerDialog(ViewExpoByFilter.this, new DatePickerDialog.OnDateSetListener() {
//                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
//                        // TODO Auto-generated method stub
//                    /*      Your code   to get date and time    */
//                        editText.setText(selectedday + "-0" + selectedmonth +"-" + selectedyear );
//                    }
//                },mYear, mMonth, mDay);
//                mDatePicker.setTitle("Select date");
//                mDatePicker.show();  }
//        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(getApplicationContext(), ShowExpoActivity.class);
                intent.putExtra(EXTRA_MESSAGE, item.get("Id").toString());
                startActivity(intent);
            }
        });
    }

    private void executeRequest(String name){
        constants.executeGetListRequest(new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject response) {

            }

            @Override
            public void onSuccessList(JSONArray response) {
                ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>(response.length());

                for (int i = 0; i < response.length(); i++) {
                    try {
                        item.put("Name", response.getJSONObject(i).getString("nombre"));
                        item.put("Date",response.getJSONObject(i).getString("fechaFin"));
                        item.put("Id", response.getJSONObject(i).getString("id"));
                        list.add(item);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                String[] from = new String[] { "Name", "Date" };

                int[] to = new int[] { android.R.id.text1, android.R.id.text2 };

                int nativeLayout = android.R.layout.two_line_list_item;
                listView.setAdapter(new SimpleAdapter(getApplicationContext(), list, nativeLayout , from, to));
            }

            @Override
            public void onError(String error) {

            }
        }, Volley.newRequestQueue(getApplicationContext()), getFinalFath().concat(name));
    }
}
