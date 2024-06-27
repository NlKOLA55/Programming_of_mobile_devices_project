package com.example.mobil_projekat;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class QuizViewHolder extends RecyclerView.ViewHolder {
    // NUM1 JE IMENOVAN CUDNO ZATO STO JE PRE BILO VISE BROJEVA
    TextView num1;
    public QuizViewHolder(View itemView) {
        //KAKO JEDAN ELEMENAT IZGLEGA
        super(itemView);
        num1 = itemView.findViewById(R.id.num1);
    }
}