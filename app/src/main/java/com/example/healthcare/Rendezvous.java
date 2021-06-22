package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Rendezvous extends AppCompatActivity {
    FirebaseFirestore fStore;
    FirebaseAuth mAuth;
    String rdvID;
    String email;
    DocumentChange doc;
    String name;
    int day;
    int month;
    int year;
    String medcin;
    DatePicker simpleDatePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rdv);
       medcin = getIntent().getExtras().getString("doctor");
        simpleDatePicker = (DatePicker)findViewById(R.id.simpleDatePicker);
        simpleDatePicker.setSpinnersShown(false);
        fStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        doc = null;


        rdvID= mAuth.getCurrentUser().getUid();
        email = getIntent().getExtras().getString("patient");


        Button b =(Button)findViewById(R.id.cRdv);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                fStore.collection("patient").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if(e!=null){
                            Log.d("Visites","Error : "+e.getMessage());
                        }
                        for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){
                            if(doc.getType() == DocumentChange.Type.ADDED){

                                patient temp = doc.getDocument().toObject(patient.class);
                                if(doc.getDocument().getId().equals(mAuth.getUid())){
                                    day = simpleDatePicker.getDayOfMonth(); // get the selected day of the month
                                    month = simpleDatePicker.getMonth(); // get the selected month
                                    year = simpleDatePicker.getYear(); // get the selected year
                                    Map<String,Object> rdv = new HashMap<>();

                                    rdv.put("jour",day);
                                    rdv.put("moi",month +1);
                                    rdv.put("année",year);
                                    rdv.put("medcin",medcin);
                                    rdv.put("patientid",mAuth.getUid());
                                    rdv.put("motif","");
                                    rdv.put("etat","demande");
                                    rdv.put("name",doc.getDocument().get("fullName"));
                                    fStore.collection("Rdv").add(rdv).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Log.d("Rdv", "DocumentSnapshot written with ID: " + documentReference.getId());

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d("Rdv","Error adding document", e);
                                        }
                                    });
                                }
                            }


                        }
                        Intent i = new Intent(Rendezvous.this,confirmation.class);
                        i.putExtra("patient",email);
                        startActivity(i);
                    }
                });

            }
        });
    }
}
