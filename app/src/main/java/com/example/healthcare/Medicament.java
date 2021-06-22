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

public class Medicament extends AppCompatActivity {
    FirebaseFirestore fStore;
    FirebaseAuth mAuth;
    String email;
    TextView note;
    Button retour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicament);
        retour=(Button)findViewById(R.id.retMed);

        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Medicament.this,DossierMedicalP.class).putExtra("patient",getIntent().getExtras().getString("email")));
            }
        });
        note = (TextView)findViewById(R.id.medicamentDMDoc);
        fStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
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
                        String s=mAuth.getUid();
                        if(p!=null && p.equals(s)) {
                            note.setText(r);
                        }

                    }


                }
            }
        });

    }
}
