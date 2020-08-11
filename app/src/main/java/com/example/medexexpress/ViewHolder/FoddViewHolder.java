package com.example.medexexpress.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medexexpress.Interface.ItemClickListener;
import com.example.medexexpress.R;

public class FoddViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView fod_Name;
    public ImageView fod_image;


    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public FoddViewHolder(View itemView) {
        super(itemView);

        fod_Name =(TextView)itemView.findViewById(R.id.fodd_name);
        fod_image =(ImageView)itemView.findViewById(R.id.fodd_imagen);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);


    }
}
