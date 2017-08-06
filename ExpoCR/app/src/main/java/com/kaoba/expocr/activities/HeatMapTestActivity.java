package com.kaoba.expocr.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kaoba.expocr.R;

import java.util.Random;

import ca.hss.heatmaplib.HeatMap;

public class HeatMapTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heat_map_test);
        HeatMap heatMap = (HeatMap) findViewById(R.id.heatmap);
        assert heatMap != null;
        heatMap.setMinimum(0.0);
        heatMap.setMaximum(100.0);
        Random rand = new Random();
        for (int i = 0; i < 20; i++) {
            HeatMap.DataPoint point = new HeatMap.DataPoint(rand.nextFloat(), rand.nextFloat(), rand.nextDouble() * 100.0);
            heatMap.addData(point);
        }
    }
}
