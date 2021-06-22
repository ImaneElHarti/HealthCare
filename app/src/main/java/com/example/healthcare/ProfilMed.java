package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.client.Firebase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class ProfilMed extends AppCompatActivity {

    String name;
    FirebaseFirestore fStore;
    TextView fullname;
    TextView Phone;
    TextView email;
    TextView speciality;
    Button rdv;
    String emailP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_med);
        Firebase.setAndroidContext(this);
        emailP = getIntent().getExtras().getString("patient");

        rdv = (Button)findViewById(R.id.RDV);
        name = getIntent().getExtras().getString("doctor");
        fullname =(TextView) findViewById(R.id.FName);
        email =(TextView) findViewById(R.id.AMail);
        Phone =(TextView) findViewById(R.id.PhoneN);
        speciality =(TextView) findViewById(R.id.Spec);
        fStore=FirebaseFirestore.getInstance();
        fStore.collection("doctor").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e!=null){
                    Log.d("ProfilMed","Error : "+e.getMessage());
                }
                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){
                    if(doc.getType() == DocumentChange.Type.ADDED){
                        String user_id = doc.getDocument().getId();
                        doctor doctors= doc.getDocument().toObject(doctor.class);
                        if(doctors.getFullName().equals(name)){
                            fullname.setText(doctors.getFullName());
                            email.setText(doctors.getEmail());
                            Phone.setText(doctors.getPhone());
                            speciality.setText(doctors.getSpeciality());

                        }
                    }

                }
            }
        });
        rdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfilMed.this, Rendezvous.class);
                i.putExtra("doctor",name);
                i.putExtra("patient",emailP);
                startActivity(i);

            }
        });
    }
}
