package com.example.medexexpress;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.medexexpress.Common.Common;
import com.example.medexexpress.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SingIn extends AppCompatActivity {

    EditText  edtPhone,edtPassword;
    Button btnSingIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);

        edtPassword=(MaterialEditText)findViewById(R.id.edtPassword);
        edtPhone=(MaterialEditText)findViewById(R.id.edtPhone);
        btnSingIn=(Button)findViewById(R.id.btnSingIn);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mDialog = new ProgressDialog(SingIn.this);
                mDialog.setMessage("Please waiting..");
                mDialog.show();



                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot dataSnapshot) {

                        if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {
                            mDialog.dismiss();

                            User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);
                            user.setPhone(edtPhone.getText().toString());


                            if (user.getPassword().equals(edtPassword.getText().toString())) {

                                {
                                    Intent homeIntent = new Intent(SingIn.this,Home.class);
                                    Common.curruntUser = user;
                                    startActivity(homeIntent);
                                    finish();


                                }
                            } else {
                                Toast.makeText(SingIn.this, "Wrong Password ", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else
                        {
                            mDialog.dismiss();
                            Toast.makeText(SingIn.this, "User not exist in data base ", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled( DatabaseError databaseError) {

                    }
                });
            }
        });

    }
}