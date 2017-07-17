package com.kaoba.expocr.activities;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;
import com.kaoba.expocr.R;
import com.kaoba.expocr.Session;
import com.kaoba.expocr.constants.Constants;
import com.kaoba.expocr.constants.VolleyCallBack;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

/**
 * Created by Antoni Ramirez on 21/6/2017.
 */
@SuppressLint("SimpleDateFormat")
public class ViewExpoCalendarActivity extends AppCompatActivity {
    private boolean undo = false;
    private CaldroidFragment caldroidFragment;
    private CaldroidFragment dialogCaldroidFragment;
    private Context context;
    Constants constants;
    Session session;
    private HashMap<String, String> item = new HashMap<String, String>();

    private static final String FINAL_PATH = "exposicions";

    private void setCustomResourceForDates() {
        final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        if (caldroidFragment != null) {
            constants.executeGetListRequest(new VolleyCallBack() {
                @Override
                public void onSuccess(JSONObject response) {

                }

                @Override
                public void onSuccessList(JSONArray response) {
                    for (int i = 0; i < response.length(); i++) {

                        try {
                            Date date = formatter.parse(response.getJSONObject(i).getString("fechaInicio"));
                            caldroidFragment.setBackgroundDrawableForDate(new ColorDrawable(getResources().getColor(R.color.caldroid_holo_blue_light)), date);
                            caldroidFragment.setTextColorForDate(R.color.caldroid_white, date);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }

                @Override
                public void onError(String error) {

                }
            }, Volley.newRequestQueue(getApplicationContext()), FINAL_PATH);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expo_calendar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        constants = new Constants();

        final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        caldroidFragment = new CaldroidFragment();

        if (savedInstanceState != null) {
            caldroidFragment.restoreStatesFromKey(savedInstanceState,
                    "CALDROID_SAVED_STATE");
        }
        // If activity is created from fresh
        else {
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
            args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);

            caldroidFragment.setArguments(args);
        }

        setCustomResourceForDates();
        context = getApplicationContext();
        // Attach to the activity
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar1, caldroidFragment);
        t.commit();

        final ListView listView = (ListView) findViewById(R.id.listView);


        // Setup listener
        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {

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
                                item.put("Id",response.getJSONObject(i).getString("id"));
                                list.add(item);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        String[] from = new String[] { "Name", "Date" };

                        int[] to = new int[] { android.R.id.text1, android.R.id.text2 };

                        int nativeLayout = android.R.layout.two_line_list_item;
                        listView.setAdapter(new SimpleAdapter(context, list, nativeLayout , from, to));
                    }

                    @Override
                    public void onError(String error) {


                    }
                }, Volley.newRequestQueue(getApplicationContext()), FINAL_PATH.concat("/byDate/").concat(formatter.format(date)));
            }

            @Override
            public void onChangeMonth(int month, int year) {
                String text = "month: " + month + " year: " + year;
                Toast.makeText(getApplicationContext(), text,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClickDate(Date date, View view) {
                Toast.makeText(getApplicationContext(),
                        "Long click " + formatter.format(date),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCaldroidViewCreated() {
                if (caldroidFragment.getLeftArrowButton() != null) {
                    Toast.makeText(getApplicationContext(),
                            "Caldroid view is created", Toast.LENGTH_SHORT)
                            .show();
                }
            }

        };

        // Setup Caldroid
        caldroidFragment.setCaldroidListener(listener);
         session = new Session(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(context, ShowExpoActivity.class);

                long l = Long.parseLong(item.get("Id"));
                session.setExpoId(l);
                intent.putExtra(EXTRA_MESSAGE,  item.get("Id"));
                startActivity(intent);
            }
        });


    }

    /**
     * Save current states of the Caldroid here
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);

        if (caldroidFragment != null) {
            caldroidFragment.saveStatesToKey(outState, "CALDROID_SAVED_STATE");
        }

        if (dialogCaldroidFragment != null) {
            dialogCaldroidFragment.saveStatesToKey(outState,
                    "DIALOG_CALDROID_SAVED_STATE");
        }
    }
}
