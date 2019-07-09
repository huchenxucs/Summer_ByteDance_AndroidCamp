package com.example.chapter2;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView mIndexView;
    TextView mNameView;
    TextView mHotnumView;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        mIndexView = itemView.findViewById(R.id.index);
        mNameView = itemView.findViewById(R.id.name);
        mHotnumView = itemView.findViewById(R.id.hotnum);

    }

    public void bind(Data data){
        String index = data.getIndex()+".";
        String name = data.getName();
        String hotnum = data.getHotnum()+"";
        mIndexView.setText(index);
        mNameView.setText(name);
        mHotnumView.setText(hotnum);
        if (data.getIndex()<=3){
            mIndexView.setTextColor(Color.parseColor("#e6face15"));
        }
        else{
            mIndexView.setTextColor(Color.parseColor("#99ffffff"));
        }

    }

}
