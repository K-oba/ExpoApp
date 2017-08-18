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
 * Created by valeriaramirez on 8/10/17.
 */

//public class QACustomeAdapter extends ArrayList{
//    CustomAdapter(Context context, ArrayList<HashMap<String,String>> questionsList){
////        super(context, R.layout.custome_qa,questionsList);
//        super(context,R.layout.custome_qa,questionsList);
//        Log.d("Hola Aqui estoy", namesArray[0]);
//    }
//
//}

public class QACustomeAdapter extends ArrayAdapter<HashMap<String,String>>{
    QACustomeAdapter(Context context, ArrayList<HashMap<String,String>> list){
        super(context,R.layout.custome_qa,list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater qaListContent = LayoutInflater.from(getContext());
        View customeView = qaListContent.inflate(R.layout.custome_qa,parent,false);
        String userName = getItem(position).get("User");
        String question = getItem(position).get("Question");
        //ImageView image = (ImageView) customeView.findViewById(R.id.imageViewUsrerQA);
        //image.sour Picasso.with(getContext()).load("https://cloudinary.com/console/media_library#/dialog/image/upload/chart-scatterplot-hexbin_jqzuib");
        TextView user = (TextView) customeView.findViewById(R.id.textViewUserName);
        user.setText(userName);
        TextView questionText = (TextView) customeView.findViewById(R.id.textViewQuestion);
        questionText.setText(question);
        return customeView;
    }
}
