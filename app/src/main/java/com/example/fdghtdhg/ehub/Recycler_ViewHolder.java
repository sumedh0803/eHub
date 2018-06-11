package com.example.fdghtdhg.ehub;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class Recycler_ViewHolder extends RecyclerView.ViewHolder {

    public TextView data;

    public Recycler_ViewHolder(View itemView) {
        super(itemView);

        data = itemView.findViewById(R.id.data);

    }

}