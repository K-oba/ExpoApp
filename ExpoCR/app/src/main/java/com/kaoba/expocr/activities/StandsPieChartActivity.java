package com.kaoba.expocr.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.kaoba.expocr.R;

import java.util.ArrayList;
import java.util.List;

public class StandsPieChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stands_pie_chart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawChart();
    }

    private void drawChart() {

        PieChart pieChart = (PieChart) findViewById(R.id.piechart);
        List<PieEntry> entries = new ArrayList<>();

        entries.add(new PieEntry(18.5f, "Green"));
        entries.add(new PieEntry(26.7f, "Yellow"));
        entries.add(new PieEntry(24.0f, "Red"));
        entries.add(new PieEntry(30.8f, "Blue"));

        PieDataSet set = new PieDataSet(entries, "Election Results");
        PieData data = new PieData(set);
        assert pieChart != null;
        pieChart.setData(data);
        pieChart.animateY(3000, Easing.EasingOption.EaseOutBounce);
        //pieChart.invalidate(); // refresh
    }
}
