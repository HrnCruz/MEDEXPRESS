package com.example.medexexpress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.medexexpress.Interface.ItemClickListener;
import com.example.medexexpress.Model.Category;
import com.example.medexexpress.Model.Fodd;
import com.example.medexexpress.ViewHolder.FoddViewHolder;
import com.example.medexexpress.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class FodList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    DatabaseReference databaseReference;
    FirebaseRecyclerOptions<Fodd> options;
    FirebaseRecyclerAdapter<Fodd, FoddViewHolder> adapter;

    String categoryId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fod_list);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Foods");

        options = new FirebaseRecyclerOptions.Builder<Fodd>()
                .setQuery(databaseReference,Fodd.class).build();

        recyclerView =(RecyclerView)findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if (getIntent() !=null)
            categoryId = getIntent().getStringExtra("CategoryId");
        if (!categoryId.isEmpty() && categoryId !=null)
        {
            loadListFodd(categoryId);
        }




    }

    private void loadListFodd(String categoryId) {

        Query food = FirebaseDatabase.getInstance().getReference("Foods").orderByChild("MenuId").equalTo(categoryId);

        adapter = new FirebaseRecyclerAdapter<Fodd, FoddViewHolder>(options) {

            @Override
            protected void onBindViewHolder( FoddViewHolder viewHolder, int position, Fodd model) {
                viewHolder.fod_Name.setText(model.getName());

            //    Picasso.with(getBaseContext()).load(model.getImage())
              //          .into(viewHolder.fod_image);

                Picasso.get().load(model.getImage())
                        .into(viewHolder.fod_image, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Exception e) {
                                Toast.makeText(FodList.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });


             //   Picasso.with(getBaseContext()).load(model.getImagen())
                     //   .into(viewHolder.imageView);

                final Fodd local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent foddDetail = new Intent(FodList.this,FoddDetail.class);
                        foddDetail.putExtra("FoodId",adapter.getRef(position).getKey());
                        startActivity(foddDetail);
                    }
                });



            }


            @Override
            public FoddViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fod_item,parent,false);
                return new FoddViewHolder(view);
            }
        };
        Log.d("TAG",""+adapter.getItemCount());
        recyclerView.setAdapter(adapter);
    }

    //RECYL ADAPTER
    @Override
    protected void onStart() {
        super.onStart();
        if (adapter !=null)
            adapter.startListening();
    }

    @Override
    protected void onStop() {
        if (adapter !=null)
            adapter.startListening();
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter !=null)
            adapter.startListening();
    }
}