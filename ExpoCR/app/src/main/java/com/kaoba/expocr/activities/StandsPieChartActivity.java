package com.kaoba.expocr.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.kaoba.expocr.R;
import com.kaoba.expocr.constants.Constants;
import com.kaoba.expocr.constants.VolleyCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StandsPieChartActivity extends AppCompatActivity {
    private static final String HIST_PATH = "historial-usuarios-expos/";
    private Constants constants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stands_pie_chart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try {
            drawChart();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void drawChart() throws JSONException {
        getStandVisitors(1);
        PieChart pieChart = (PieChart) findViewById(R.id.piechart);
        assert pieChart != null;
        Description description = new Description();
        description.setText(getString(R.string.visitors_by_stand));
        pieChart.setDescription(description);
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(4, "Suzuki"));
        entries.add(new PieEntry(6, "Nissan"));
        entries.add(new PieEntry(8, "Toyota"));
        entries.add(new PieEntry(3, "Turtle Wax"));
        PieDataSet set = new PieDataSet(entries, "");
        set.setColors(ColorTemplate.MATERIAL_COLORS);
        PieData data = new PieData(set);
        pieChart.setData(data);
        pieChart.animateY(3000, Easing.EasingOption.EaseOutBounce);
        //pieChart.invalidate(); // refresh
    }

    public void getStandVisitors(int standId) throws JSONException {
        final RequestQueue queue = Volley.newRequestQueue(this);
        constants = new Constants();
        constants.executeGetListRequest(new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject response) {
                String i = response.toString();
            }

            @Override
            public void onSuccessList(JSONArray response) {

            }

            @Override
            public void onError(String error) {
                Log.d("Error fetching visitors","");
            }
        }, queue, HIST_PATH.concat("1/1"));
    }
}
