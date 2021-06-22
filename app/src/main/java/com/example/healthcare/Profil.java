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

public class Profil extends AppCompatActivity {
    String email;
    private Message m;
    private FirebaseFirestore fStore;
    TextView tmsg ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        Button logout = (Button)findViewById(R.id.Lout);
        Button profil = (Button)findViewById(R.id.ProfileMed);
        Button rdv = (Button)findViewById(R.id.dateMed);
        Button DM =(Button)findViewById(R.id.Medical_Folder);
        Button msg = (Button)findViewById(R.id.messagerie);
        tmsg = (TextView)findViewById(R.id.tmsg);
        final Button Search = (Button)findViewById(R.id.searchMed);
        fStore = FirebaseFirestore.getInstance();
        email=getIntent().getExtras().getString("patient");
        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Profil.this,ProfilClient.class);
                i.putExtra("patient",email);
                startActivity(i);
            }
        });
        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Profil.this,Search.class);
                i.putExtra("patient",email);
                startActivity(i);
            }
        });
        rdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Profil.this,Appointement.class);
                i.putExtra("patient",email);
                startActivity(i);
            }
        });
        DM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Profil.this,DossierMedicalP.class);
                i.putExtra("patient",email);
                startActivity(i);
            }
        });
        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Profil.this,Messagerie.class);
                i.putExtra("patient",email);
                startActivity(i);
            }
        });

        fStore.collection("messagerie").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e!=null){
                    Log.d("patient","Error : "+e.getMessage());
                }
                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){
                    if(doc.getType() == DocumentChange.Type.ADDED){
                        m = doc.getDocument().toObject(Message.class);

                        if( m.getEtat()!= null && !m.getEtat().equals("lu") ){

                            tmsg.setVisibility(View.VISIBLE);
                        }

                    }

                }
            }
        });
    }
    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(Profil.this,choosing.class));
    }
}
