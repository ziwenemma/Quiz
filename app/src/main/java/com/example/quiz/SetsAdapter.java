package com.example.quiz;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class SetsAdapter extends BaseAdapter {

    private int numOfSets;

    public SetsAdapter(int numOfSets){
        this.numOfSets = numOfSets;
    }

    @Override
    public int getCount() {
        return numOfSets;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

       View view;
       if(convertView == null){
           view = LayoutInflater.from(parent.getContext()).inflate(R.layout.set_item_layout,parent,false);
       }
       else {
           view = convertView;
       }

       view.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(parent.getContext(), QuestionActivity.class);
               intent.putExtra("SETNO", position+1);  //calling the set from firebase
               parent.getContext().startActivity(intent);
           }
       });
        ((TextView) view.findViewById(R.id.setNo_tv)).setText(String.valueOf(position+1));
        return view;
    }
}
