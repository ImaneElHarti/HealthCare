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
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class ModifierNote extends AppCompatActivity {

    private Button enregistrerMed;
    private EditText med;
    String patient;
    private String name;
    private FirebaseFirestore fStore;
    String p;
    String n;
    String vs;
    String idD;
    String r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_note);

        enregistrerMed = (Button)findViewById(R.id.enregistrerNote);
        med =(EditText) findViewById(R.id.NoteMed);
        patient=getIntent().getExtras().getString("patient");
        name = getIntent().getExtras().getString("name");
        fStore = FirebaseFirestore.getInstance();
        fStore.collection("dossier medical").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e!=null){
                    Log.d("Appointement","Error : "+e.getMessage());
                }
                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){
                    if(doc.getType() == DocumentChange.Type.ADDED){

                        r= doc.getDocument().get("medicament").toString();
                        p = doc.getDocument().get("patient").toString();
                        n = doc.getDocument().get("note").toString();
                        vs = doc.getDocument().get("visite").toString();
                        if(p!=null && p.equals(patient)) {
                            med.setText(n);
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
                DM.put("medicament",r);
                DM.put("patient",patient);
                DM.put("note",med.getText().toString());
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
                startActivity(new Intent(ModifierNote.this,NoteDoc.class).putExtra("patient",name));
            }
        });


    }
}
