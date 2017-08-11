package com.kaoba.expocr.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.kaoba.expocr.R;
import com.squareup.picasso.Picasso;

public class StatisticsMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stadistics_menu);
        //Images
        ImageView imageStadisticsHeader = (ImageView) findViewById(R.id.imageChartMenu);
        Picasso.with(getApplicationContext()).load("http://res.cloudinary.com/duxllywl7/image/upload/v1500603128/chart-donut_f17qtv.png").into(imageStadisticsHeader);
        ImageView imageHeatMap = (ImageView) findViewById(R.id.heatMapImage);
        Picasso.with(getApplicationContext()).load("http://res.cloudinary.com/duxllywl7/image/upload/v1500655203/chart-scatterplot-hexbin_jqzuib.png").into(imageHeatMap);
        ImageView imagePeopleCounter = (ImageView) findViewById(R.id.peopleCounterImage);
        Picasso.with(getApplicationContext()).load("http://res.cloudinary.com/duxllywl7/image/upload/v1500655200/chart-bar-stacked_cpnxxl.png").into(imagePeopleCounter);
        //Images
    }
}
