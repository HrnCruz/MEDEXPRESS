package com.example.medexexpress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public static final int RC_SIGN_IN=7898;

    Button btnSingIn,btnSingUp;
    TextView txtSlogan;

  List<AuthUI.IdpConfig>providers = Arrays.asList(
          new AuthUI.IdpConfig.EmailBuilder().build(),
          new AuthUI.IdpConfig.PhoneBuilder().build(),
          new AuthUI.IdpConfig.GoogleBuilder().build()

  );



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth =FirebaseAuth.getInstance();

        btnSingIn=(Button)findViewById(R.id.btnSingIn);
        btnSingUp=(Button)findViewById(R.id.btnSingUp);

        txtSlogan=(TextView)findViewById(R.id.txtSlogan);
        Typeface face = Typeface.createFromAsset(getAssets(),"fonts/NABILA.TTF");
        txtSlogan.setTypeface(face);

        btnSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent singIn = new Intent(MainActivity.this,SingIn.class);
                startActivity(singIn);

            }
        });

        btnSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent singUp = new Intent(MainActivity.this,SingUp.class);
                startActivity(singUp);
                
            }
        });

        mAuthListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user!=null){
                    Toast.makeText(MainActivity.this, "User signed In", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    startActivityForResult(
                            AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                                    .setLogo(R.drawable.bago)
                                    .setTheme(R.style.LoginTheme)
                            .setIsSmartLockEnabled(false)
                            .build(),RC_SIGN_IN
                    );
                }
            }
        };

    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFirebaseAuth.addAuthStateListener(mAuthListener);
    }

    public void signout(View view) {

        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainActivity.this, "User signed Out", Toast.LENGTH_SHORT).show();

                   finish(); }
                });
    }
}