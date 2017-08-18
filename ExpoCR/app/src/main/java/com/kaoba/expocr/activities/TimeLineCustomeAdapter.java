package com.kaoba.expocr.activities;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaoba.expocr.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by valeriaramirez on 8/17/17.
 */

public class TimeLineCustomeAdapter extends ArrayAdapter<HashMap<String,String>> {
        TimeLineCustomeAdapter(Context context, ArrayList<HashMap<String,String>> list){
        super(context,R.layout.custome_timeline,list);
        }

@Override
public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater timeLineListContent = LayoutInflater.from(getContext());
        View customeView = timeLineListContent.inflate(R.layout.custome_timeline,parent,false);
        String state = getItem(position).get("State");
        //String question = getItem(position).get("Question");
        //ImageView image = (ImageView) customeView.findViewById(R.id.imageViewUsrerQA);
        //image.sour Picasso.with(getContext()).load("https://cloudinary.com/console/media_library#/dialog/image/upload/chart-scatterplot-hexbin_jqzuib");
        TextView stateText = (TextView) customeView.findViewById(R.id.textViewEstado);
        stateText.setText(state);
        return customeView;
        }
}

