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

public class AppointementMed extends AppCompatActivity implements RecycleViewClick{



    ArrayList<Rdv> rdvC;
    RecyclerView recC;
    AppAdapC cadap;
    ArrayList<RdvWithId> rdvD;
    RecyclerView recD;
    AppAdapD dadap;
    FirebaseFirestore fStore;
    FirebaseAuth mAuth;
    Button retAP;
    doctor d;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointement_med);
        retAP = (Button)findViewById(R.id.retAP);
        fStore =FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        recC=(RecyclerView)findViewById(R.id.recC);
        rdvC = new ArrayList<>();


        recD=(RecyclerView)findViewById(R.id.recD);
        rdvD = new ArrayList<>();

        fStore.collection("doctor").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e!=null){
                    Log.d("patient","Error : "+e.getMessage());
                }
                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){
                    if(doc.getType() == DocumentChange.Type.ADDED){
                      if(doc.getDocument().getId().equals(mAuth.getUid())){
                          d=doc.getDocument().toObject(doctor.class);
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
                        Rdv r = doc.getDocument().toObject(Rdv.class);
                        RdvWithId rid = new RdvWithId(r,doc.getDocument().getId());
                        if(r.getEtat().equals("demande")  && d!=null&& d.getFullName().equals(r.getMedcin())){
                            rdvD.add(rid);

                        }if(r.getEtat().equals("confirmé")&& d!=null&& d.getFullName().equals(r.getMedcin())) {
                            rdvC.add(r);

                        }

                    }

                }

                recD.setHasFixedSize(true);
                recD.setLayoutManager(new LinearLayoutManager(AppointementMed.this));
                dadap = new AppAdapD(rdvD,AppointementMed.this);
                recD.setAdapter(dadap);


                recC.setHasFixedSize(true);
                recC.setLayoutManager(new LinearLayoutManager(AppointementMed.this));
                cadap = new AppAdapC(rdvC);
                recC.setAdapter(cadap);
            }
        });

        retAP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppointementMed.this,choixMed.class));
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

    }

    @Override
    public void onItemClickV(int position, ArrayList<RdvWithId> visites, String motif) {

    }

    @Override
    public void onItemClickC(int position, ArrayList<RdvWithId> rdv) {
        Map<String,Object> visite = new HashMap<String,Object>();

        visite.put("année",rdv.get(position).getR().getAnnée());
        visite.put("etat","confirmé");
        visite.put("moi",rdv.get(position).getR().getMoi());
        visite.put("jour",rdv.get(position).getR().getJour());
        visite.put("patientid",rdv.get(position).getR().getPatientid());
        visite.put("medcin",rdv.get(position).getR().getMedcin());
        visite.put("motif",rdv.get(position).getR().getMotif());
        visite.put("name",rdv.get(position).getR().getName());

        DocumentReference df = fStore.collection("Rdv").document(rdv.get(position).getId());
        df.set(visite);
        startActivity(new Intent(AppointementMed.this,AppointementMed.class));
    }

    @Override
    public void onItemClickA(int position, ArrayList<RdvWithId> rdv) {
        Map<String,Object> visite = new HashMap<String,Object>();

        visite.put("année",rdv.get(position).getR().getAnnée());
        visite.put("etat","annulé");
        visite.put("moi",rdv.get(position).getR().getMoi());
        visite.put("jour",rdv.get(position).getR().getJour());
        visite.put("patient",rdv.get(position).getR().getPatientid());
        visite.put("medcin",rdv.get(position).getR().getMedcin());
        visite.put("motif",rdv.get(position).getR().getMotif());
        visite.put("name",rdv.get(position).getR().getName());
        DocumentReference df = fStore.collection("Rdv").document(rdv.get(position).getId());
        df.set(visite);

        Map<String,Object> msg = new HashMap<>();
        msg.put("patient",rdv.get(position).getR().getPatientid());
        msg.put("medcin",mAuth.getUid());
        msg.put("message","Your appointement with the doctor  "+rdv.get(position).getR().getMedcin()+" on  "+ rdv.get(position).getR().getJour()+"/"+rdv.get(position).getR().getMoi()+"/"+rdv.get(position).getR().getAnnée()+" has been canceled");
        msg.put("etat","non lu");
        fStore.collection("messagerie").add(msg);
        startActivity(new Intent(AppointementMed.this,AppointementMed.class));
    }



}
