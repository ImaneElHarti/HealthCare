package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DossierMedicalP extends AppCompatActivity {

    Button note ;
    Button medicament ;
    Button visite ;
    Button retour;
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dossier_medical_p);

        note = (Button)findViewById(R.id.Note);
        medicament = (Button)findViewById(R.id.Medicament);
        visite = (Button)findViewById(R.id.Visites);
        retour = (Button)findViewById(R.id.RetourDM);
        email=getIntent().getExtras().getString("patient");

        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DossierMedicalP.this,NoteMed.class);
                i.putExtra("patient",email);
                startActivity(i);
            }
        });

        medicament.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DossierMedicalP.this,Medicament.class);
                i.putExtra("patient",email);

                startActivity(i);
            }
        });

        visite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DossierMedicalP.this,Visites.class);
                i.putExtra("patient",email);
                startActivity(i);
            }
        });
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DossierMedicalP.this,Profil.class);
                i.putExtra("patient",email);
                startActivity(i);
            }
        });

    }
}
