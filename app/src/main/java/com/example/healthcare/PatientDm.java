package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PatientDm extends AppCompatActivity {

    Button visite,medicament,note,retour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_dm);
        final String patient = getIntent().getExtras().getString("patient");
        visite = (Button)findViewById(R.id.DocVisites);
        visite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(PatientDm.this,DocVisites.class).putExtra("patient",patient));
            }
        });

        medicament= (Button)findViewById(R.id.DocMedicament);
        medicament.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientDm.this,MedicammentDoc.class).putExtra("patient",patient));
            }
        });
        note = (Button)findViewById(R.id.DocNote);
        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientDm.this,NoteDoc.class).putExtra("patient",patient));

            }
        });
        retour =(Button)findViewById(R.id.RetourDM);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientDm.this,SearchMed.class).putExtra("patient",patient));
            }
        });

    }
}
