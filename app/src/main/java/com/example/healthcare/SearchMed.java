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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SearchMed extends AppCompatActivity implements RecycleViewClick {

    FirebaseFirestore fStore;
    FirebaseAuth mAtuh;
    ArrayList<PatientWithId> patient;
    patientAdap pAdap;
    RecyclerView recPatient;
    String name;
    Rdv  r;
    Button ret;
    String s;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_med);

        fStore = FirebaseFirestore.getInstance();
        mAtuh = FirebaseAuth.getInstance();
        patient = new ArrayList<>();
        pAdap =  new patientAdap(patient,this);
        recPatient = (RecyclerView)findViewById(R.id.patientRecycle);
        ret = (Button)findViewById(R.id.retSM);

        recPatient.setHasFixedSize(true);
        recPatient.setLayoutManager(new LinearLayoutManager(this));

        recPatient.setAdapter(pAdap);

        fStore.collection("doctor").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e!=null){
                    Log.d("patient","Error : "+e.getMessage());
                }
                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){
                    if(doc.getType() == DocumentChange.Type.ADDED){
                        String user_id = doc.getDocument().getId();
                        doctor r = doc.getDocument().toObject(doctor.class);
                        if (mAtuh.getUid().equals(user_id))
                        name = r.getFullName();

                    }

                }
            }
        });


        fStore.collection("Rdv").addSnapshotListener(new EventListener<QuerySnapshot>() {
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e!=null){
                    Log.d("patient","Error : "+e.getMessage());
                }
                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){
                    if(doc.getType() == DocumentChange.Type.ADDED){
                        String user_id = doc.getDocument().getId();
                       r = doc.getDocument().toObject(Rdv.class);
                        if(r.getMedcin().equals(name)){
                           s =  r.getName();
                            fStore.collection("patient").addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                    for(DocumentChange docp: queryDocumentSnapshots.getDocumentChanges()){
                                        if(docp.getType() == DocumentChange.Type.ADDED) {
                                            patient p = docp.getDocument().toObject(patient.class);
                                            String patient_id = docp.getDocument().getId();
                                            PatientWithId pid = new PatientWithId(patient_id, p);
                                            if(p.getFullName().equals(s)){
                                                patient.add(pid);
                                                pAdap.notifyDataSetChanged();
                                            }
                                        }
                                        }
                                    }

                            });
                        }

                    }

                }
            }
        });



        ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(new Intent(SearchMed.this,choixMed.class)));
            }
        });
    }

    @Override
    public void onItemClick(int position, ArrayList<doctor> Med) {

    }

    @Override
    public void onLongItemClick(int position) {

    }

    @Override
    public void onItemClickP(int position, ArrayList<PatientWithId> patients) {
        Intent i = new Intent(SearchMed.this,ProfilPatient.class);
        i.putExtra("patient", patients.get(position).getId());

        startActivity(i);
    }

    @Override
    public void onItemClickV(int position, ArrayList<RdvWithId> visites, String motif) {

    }

    @Override
    public void onItemClickC(int position, ArrayList<RdvWithId> rdv) {

    }

    @Override
    public void onItemClickA(int position, ArrayList<RdvWithId> rdv) {

    }

}
