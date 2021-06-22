package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class choixMed extends AppCompatActivity {

    Button search ;
    Button profil, appointement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_med);

        search =(Button)findViewById(R.id.searchMed);
        profil =(Button)findViewById(R.id.ProfileMed2);
        appointement =(Button)findViewById(R.id.dateMed);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(choixMed.this,SearchMed.class);
                startActivity(i);

            }
        });
        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(choixMed.this,medcinProfile.class);
                startActivity(i);
            }
        });
        appointement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(choixMed.this,AppointementMed.class));

            }
        });


    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(choixMed.this,choosing.class));
    }
}
