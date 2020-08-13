package com.example.medexexpress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FodList extends AppCompatActivity {


    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    DatabaseReference databaseReference;
    FirebaseRecyclerOptions<Fodd> options;
    FirebaseRecyclerAdapter<Fodd, FoddViewHolder> adapter;

    String categoryId="";

    FirebaseRecyclerAdapter<Fodd, FoddViewHolder> searchAdapter;
    List<String> suggestList = new ArrayList<>();
    MaterialSearchBar materialSearchBar;

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

        materialSearchBar= (MaterialSearchBar)findViewById(R.id.searchBar);
        materialSearchBar.setHint("Enter your Service");
      //  materialSearchBar.setSpeechMode(false);
        loadSuggest();
        materialSearchBar.setLastSuggestions(suggestList);
        materialSearchBar.setCardViewElevation(10);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                List<String> suggest = new ArrayList<String>();
                for (String search:suggestList)
                {
                    if (search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                        suggest.add(search);
                }
                materialSearchBar.setLastSuggestions(suggest);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if (!enabled)
                    recyclerView.setAdapter(adapter);
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text);

            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });


    }

    private void startSearch(CharSequence text) {
        Query fodd = FirebaseDatabase.getInstance().getReference("Foods").orderByChild("Name").equalTo(text.toString());
       searchAdapter = new FirebaseRecyclerAdapter<Fodd, FoddViewHolder>(options) {
           @Override
           protected void onBindViewHolder( FoddViewHolder viewHolder, int position,  Fodd model) {
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
           public FoddViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
               View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fod_item,parent,false);
               return new FoddViewHolder(view);
           }
       };
       recyclerView.setAdapter(searchAdapter);
    }

    private void loadSuggest() {
        databaseReference.orderByChild("MenuId").equalTo(categoryId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot dataSnapshot) {
                     for (DataSnapshot postSnapshot:dataSnapshot.getChildren())
                     {
                         Fodd item = postSnapshot.getValue(Fodd.class);
                         suggestList.add(item.getName());
                     }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
    }

    private void loadListFodd(String categoryId) {

        Query fodd = FirebaseDatabase.getInstance().getReference("Foods").orderByChild("MenuId").equalTo(categoryId);

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