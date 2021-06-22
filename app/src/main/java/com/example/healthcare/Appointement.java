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

public class Appointement extends AppCompatActivity {
    FirebaseFirestore fStore;
    FirebaseAuth mAuth;
    ArrayList<Rdv> rdv;
    AppointementListAdap rdvAdap;
    RecyclerView rdvRec;
    String email;
    Button redvRet ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointement);
        Firebase.setAndroidContext(this);
        fStore= FirebaseFirestore.getInstance();
        mAuth= FirebaseAuth.getInstance();
        rdvRec = (RecyclerView) findViewById(R.id.rdvRecycle);
        redvRet = (Button)findViewById(R.id.RdvRet);
        rdv =new ArrayList<>();

        rdvRec.setHasFixedSize(true);
        rdvRec.setLayoutManager(new LinearLayoutManager(this));
        rdvRec.setVisibility(View.VISIBLE);


        try {
            email = getIntent().getExtras().getString("patient");
        }catch (Exception e){
            Log.d("Appointement"," error"+e.getMessage());
        }

        rdvAdap = new AppointementListAdap(rdv);
        rdvRec.setAdapter(rdvAdap);
        fStore.collection("Rdv").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e!=null){
                    Log.d("Appointement","Error : "+e.getMessage());
                }
                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){
                    if(doc.getType() == DocumentChange.Type.ADDED){

                        Rdv r= doc.getDocument().toObject(Rdv.class);
                        String p = r.getPatientid();
                        String s = mAuth.getUid();
                        if(p!=null && p.equals(s)) {
                            rdv.add(r);
                            rdvAdap.notifyDataSetChanged();
                        }

                    }


                }
            }
        });

        redvRet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Appointement.this,Profil.class);
                i.putExtra("patient",email);
                startActivity(i);

            }
        });
    }
}
