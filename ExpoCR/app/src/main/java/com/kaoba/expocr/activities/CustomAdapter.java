package com.kaoba.expocr.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kaoba.expocr.R;
import android.util.Log;

/**
 * Created by valeriaramirez on 8/1/17.
 */

class CustomAdapter extends ArrayAdapter<String>{
    CustomAdapter(Context  context, String [] namesArray){
       super(context,R.layout.custome_qa_list,namesArray);
        //Log.d("Hola Aqui estoy", namesArray[0]);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater qaListContent = LayoutInflater.from(getContext());
        View customeView = qaListContent.inflate(R.layout.custome_qa_list,parent,false);

        String singleCharla = getItem(position);
        //Log.d("Metodo", singleCharla);
        TextView textViewOutPut = (TextView) customeView.findViewById(R.id.textViewListCustome);

        textViewOutPut.setText(singleCharla);
        return customeView;
    }
}
