package com.example.medexexpress.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medexexpress.Interface.ItemClickListener;
import com.example.medexexpress.R;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {



    public TextView txtOrdersId,txtOrderStatus,txtOrderPhone,txtOrderAddress;

    private ItemClickListener itemClickListener;


    public OrderViewHolder( View itemView) {
        super(itemView);

        txtOrderAddress =(TextView)itemView.findViewById(R.id.order_address);

        txtOrderPhone =(TextView)itemView.findViewById(R.id.order_phone);

        txtOrderStatus =(TextView)itemView.findViewById(R.id.order_status);

        txtOrdersId =(TextView)itemView.findViewById(R.id.order_id);

       itemView.setOnClickListener(this);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);

    }
}
