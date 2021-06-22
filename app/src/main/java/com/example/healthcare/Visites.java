package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Visites extends AppCompatActivity {

    FirebaseFirestore fStore;
    FirebaseAuth mAuth;
    ArrayList<Rdv> rdv;
    visitListAdap visiteAdap;
    RecyclerView visiteRec;
    String email;
    Button visiteRet ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visites);
        Firebase.setAndroidContext(this);
        fStore= FirebaseFirestore.getInstance();
        mAuth= FirebaseAuth.getInstance();
        visiteRec = (RecyclerView) findViewById(R.id.visitesRec);
        visiteRet = (Button)findViewById(R.id.visiteRet);
        rdv =new ArrayList<>();

        visiteRec.setHasFixedSize(true);
        visiteRec.setLayoutManager(new LinearLayoutManager(this));

        visiteRec.setVisibility(View.VISIBLE);


        try {
            email = getIntent().getExtras().getString("patient");
        }catch (Exception e){
            Log.d("Visites"," error"+e.getMessage());
        }

        visiteAdap = new visitListAdap(rdv);
        visiteRec.setAdapter(visiteAdap);
        fStore.collection("Rdv").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e!=null){
                    Log.d("Visites","Error : "+e.getMessage());
                }
                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){
                    if(doc.getType() == DocumentChange.Type.ADDED){

                        Rdv r= doc.getDocument().toObject(Rdv.class);
                        String p = r.getPatientid();
                        String s = mAuth.getUid();
                        String etat=r.getEtat();
                        if(p!=null && p.equals(s) && etat.equals("confirmé")) {
                            rdv.add(r);
                            visiteAdap.notifyDataSetChanged();
                        }

                    }


                }
            }
        });

        visiteRet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Visites.this,Profil.class);
                i.putExtra("patient",email);
                startActivity(i);
            }
        });

    }
}
