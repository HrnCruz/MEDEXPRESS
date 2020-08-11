package com.example.medexexpress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.medexexpress.Model.Fodd;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class FoddDetail extends AppCompatActivity {

    TextView foodd_name,foodd_price,foodd_description;
    ImageView foodd_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;

    String foodId="";
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fodd_detail);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Foods");

        numberButton =(ElegantNumberButton)findViewById(R.id.number_button);
        btnCart=(FloatingActionButton)findViewById(R.id.btnCart);

        foodd_description=(TextView)findViewById(R.id.fodd_description);
        foodd_name=(TextView)findViewById(R.id.fodd_name);
        foodd_price=(TextView)findViewById(R.id.fodd_price);
        foodd_image=(ImageView)findViewById(R.id.img_fodd);

        collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);
        
        if (getIntent() !=null)
            foodId = getIntent().getStringExtra("FoodId");
        if (!foodId.isEmpty())
        {
            getDetailFood(foodId);
        }
    }

    private void getDetailFood(String foodId) {
        databaseReference.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                Fodd FOOD = dataSnapshot.getValue(Fodd.class);

              //  Picasso.with(getBaseContext()).load(FOOD.getImage())
                  //      .into(foodd_image);



                Picasso.get().load(FOOD.getImage())
                        .into(foodd_image);


                collapsingToolbarLayout.setTitle(FOOD.getName());

                foodd_price.setText(FOOD.getPrice());

                foodd_name.setText(FOOD.getName());
                foodd_description.setText(FOOD.getDescription());


                //Picasso.with(getBaseContext()).load(model.getImagen())
                //                            .into(viewHolder.imageView);



            }

            @Override
            public void onCancelled( DatabaseError databaseError) {

            }
        });
    }
}