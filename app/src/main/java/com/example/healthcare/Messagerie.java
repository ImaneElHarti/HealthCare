package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Messagerie extends AppCompatActivity {

    RecyclerView rMessages;
    List<Message> tMessage;
    AdapMessage aMessage;
    FirebaseFirestore fStore;
    FirebaseAuth mAuth;
    Message m;
    String idm;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messagerie);
        email= getIntent().getExtras().getString("patient");
        fStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        Button ret = (Button)findViewById(R.id.retMsg);
        tMessage = new ArrayList<>();
        aMessage = new AdapMessage(tMessage);
        rMessages =(RecyclerView)findViewById(R.id.rMessage);

        rMessages.setHasFixedSize(true);
        rMessages.setLayoutManager(new LinearLayoutManager(this));
        rMessages.setAdapter(aMessage);



        fStore.collection("messagerie").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e!=null){
                    Log.d("patient","Error : "+e.getMessage());
                }
                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){
                    if(doc.getType() == DocumentChange.Type.ADDED){
                        m = doc.getDocument().toObject(Message.class);
                        idm=doc.getDocument().getId();
                        String c = mAuth.getUid();
                        String v = m.getPatient();
                        String l = m.getEtat();
                        if(!m.getEtat().equals("lu") && m.getPatient().equals(mAuth.getUid())){
                            fStore.collection("messagerie").addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                    if(e!=null){
                                        Log.d("Appointement","Error : "+e.getMessage());
                                    }
                                    for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){

                                            if(doc.getDocument().getId().equals(idm)){
                                                Map<String,Object> msg = new HashMap<>();
                                                msg.put("patient",m.getPatient());
                                                msg.put("medcin",m.getMedcin());
                                                msg.put("message",m.getMessage());
                                                msg.put("etat","lu");
                                                DocumentReference df = fStore.collection("messagerie").document(idm);
                                                df.set(msg).addOnSuccessListener(new OnSuccessListener<Void>() {
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

                                            }

                                    }
                                }
                            });


                            tMessage.add(m);
                            aMessage.notifyDataSetChanged();
                        }

                    }

                }
            }
        });
        ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Messagerie.this,Profil.class).putExtra("patient",email));
            }
        });
    }
}
