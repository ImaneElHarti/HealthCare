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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DocVisites extends AppCompatActivity implements RecycleViewClick{

    FirebaseFirestore fStore;
    FirebaseAuth mAuth;
    ArrayList<Rdv> visiteM;
    ArrayList<RdvWithId> visiteSm;
    RecyclerView recM;
    RecyclerView recSm;
    visitesMotifAdap motifAdap;
    visitesSansMotifAdap sansMotifAdap;
    String patient;
    Rdv r;
    Button ret ;
    String name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_visites);
        ret = (Button)findViewById(R.id.ret);
        fStore=FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();
        recM=(RecyclerView)findViewById(R.id.recM);
        recSm=(RecyclerView)findViewById(R.id.recSm);
        visiteM = new ArrayList<>();
        visiteSm = new ArrayList<>();
        motifAdap = new visitesMotifAdap(visiteM,this);
        sansMotifAdap = new visitesSansMotifAdap(visiteSm,this);

        recM.setHasFixedSize(true);
        recM.setLayoutManager(new LinearLayoutManager(this));
        recM.setAdapter(motifAdap);
        recSm.setHasFixedSize(true);
        recSm.setLayoutManager(new LinearLayoutManager(this));
        recSm.setAdapter(sansMotifAdap);

        patient=getIntent().getExtras().getString("patient");


        fStore.collection("doctor").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e!=null){
                    Log.d("patient","Error : "+e.getMessage());
                }
                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){
                    if(doc.getType() == DocumentChange.Type.ADDED){
                        doctor doct = doc.getDocument().toObject(doctor.class);

                        if(doc.getDocument().getId().equals(mAuth.getUid())){
                            name=doct.getFullName();
                        }


                    }

                }
            }
        });



        fStore.collection("Rdv").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e!=null){
                    Log.d("patient","Error : "+e.getMessage());
                }
                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){
                    if(doc.getType() == DocumentChange.Type.ADDED){
                        r = doc.getDocument().toObject(Rdv.class);
                        r.setName(patient);
                        RdvWithId rdv= new RdvWithId(r,doc.getDocument().getId());

                            if(! r.getEtat().equals("annulé") &&r.getMotif()!=""&& r.getMedcin().equals(name)){
                                visiteM.add(r);
                                motifAdap.notifyDataSetChanged();
                            }
                            if(! r.getEtat().equals("annulé") && r.getMotif().equals("") && r.getMedcin().equals(name)){
                                visiteSm.add(rdv);
                                sansMotifAdap.notifyDataSetChanged();
                            }




                    }

                }
            }
        });
        ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(DocVisites.this,SearchMed.class));
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
    public void onItemClickP(int position, ArrayList<com.example.healthcare.PatientWithId> patients) {

    }

    @Override
    public void onItemClickV(final int position, ArrayList<RdvWithId> visites, final String motif) {
        fStore.collection("Rdv").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                Map<String,Object> visite = new HashMap<String,Object>();

                visite.put("année",visiteSm.get(position).getR().getAnnée());
                visite.put("etat",visiteSm.get(position).getR().getEtat());
                visite.put("moi",visiteSm.get(position).getR().getMoi());
                visite.put("jour",visiteSm.get(position).getR().getJour());
                visite.put("patientid",visiteSm.get(position).getR().getPatientid());
                visite.put("medcin",visiteSm.get(position).getR().getMedcin());
                visite.put("motif",motif);
                visite.put("name",visiteSm.get(position).getR().getName());

                DocumentReference df = fStore.collection("Rdv").document(visiteSm.get(position).getId());
                df.set(visite);
                startActivity(new Intent(DocVisites.this,DocVisites.class).putExtra("patient",patient));

            }
        });

    }

    @Override
    public void onItemClickC(int position, ArrayList<RdvWithId> rdv) {

    }

    @Override
    public void onItemClickA(int position, ArrayList<RdvWithId> rdv) {

    }



}
