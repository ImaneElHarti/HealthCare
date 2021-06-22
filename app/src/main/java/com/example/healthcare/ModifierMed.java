package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class ModifierMed extends AppCompatActivity {

    EditText med;
    Button enregistrerMed;
    FirebaseFirestore fStore;
    FirebaseAuth mAuth;
    String patient;
    String p;
    String n;
    String vs;
    String idD;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_med);
        enregistrerMed = (Button)findViewById(R.id.enregistrerMed);
        med =(EditText) findViewById(R.id.modiferMed);
        patient=getIntent().getExtras().getString("patient");
        name = getIntent().getExtras().getString("name");
        fStore =FirebaseFirestore.getInstance();
        fStore.collection("dossier medical").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e!=null){
                    Log.d("Appointement","Error : "+e.getMessage());
                }
                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){
                    if(doc.getType() == DocumentChange.Type.ADDED){

                        String r= doc.getDocument().get("medicament").toString();
                        p = doc.getDocument().get("patient").toString();
                        n = doc.getDocument().get("note").toString();
                        vs = doc.getDocument().get("visite").toString();
                        if(p!=null && p.equals(patient)) {
                            med.setText(r);
                            idD= doc.getDocument().getId();
                        }

                    }


                }
            }
        });

        enregistrerMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String,Object> DM = new HashMap<>();
                DM.put("medicament",med.getText().toString());
                DM.put("patient",patient);
                DM.put("note",n);
                DM.put("visite",vs);
                DocumentReference df = fStore.collection("dossier medical").document(idD);
                df.set(DM).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("","Succes");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("","faillure: "+e.toString());
                    }
                });
                startActivity(new Intent(ModifierMed.this,MedicammentDoc.class).putExtra("patient",name));
            }
        });


    }
}
