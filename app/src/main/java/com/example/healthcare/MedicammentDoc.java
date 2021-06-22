package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class MedicammentDoc extends AppCompatActivity {

    private TextView note;
    private FirebaseFirestore fStore;
    private FirebaseAuth mAuth;
    String name;
    String idP;
    private Button ajouterMed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamment_doc);
        note = (TextView)findViewById(R.id.medicamentDM);
        fStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        Button retour = (Button)findViewById(R.id.retMed);
        name=getIntent().getExtras().getString("patient");
        fStore.collection("patient").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e!=null){
                    Log.d("Appointement","Error : "+e.getMessage());
                }
                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){
                    if(doc.getType() == DocumentChange.Type.ADDED){

                        patient p = doc.getDocument().toObject(patient.class);
                        if(p.getFullName().equals(name)) {
                            idP = doc.getDocument().getId();
                        }

                    }


                }
            }
        });


        fStore.collection("dossier medical").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e!=null){
                    Log.d("Appointement","Error : "+e.getMessage());
                }
                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){
                    if(doc.getType() == DocumentChange.Type.ADDED){

                        String r= doc.getDocument().get("medicament").toString();
                        String p = doc.getDocument().get("patient").toString();

                        if(p!=null && p.equals(idP) && r != "") {
                            note.setText(r);
                        }

                    }


                }
            }
        });
        ajouterMed = (Button)findViewById(R.id.ajouterMedDoc);
        ajouterMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MedicammentDoc.this,ModifierMed.class);
                i.putExtra("name",name);
                i.putExtra("patient",idP);
                startActivity(i);
            }
        });
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( MedicammentDoc.this,DocVisites.class);
                i.putExtra("patient",name);
                startActivity(i);
            }
        });

    }
    }

