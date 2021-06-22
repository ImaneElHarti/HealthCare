package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.client.Firebase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Search extends AppCompatActivity implements RecycleViewClick {
    private static final String TAG = "MainActivity";
    RecyclerView List1;
    RecyclerView List2;
    RecyclerView List3;
    FirebaseFirestore fStore;
    ArrayList<doctor>  Med1;
    DocListAdap adp1;
    ArrayList<doctor>  Med2;
    DocListAdap adp2;
    ArrayList<doctor>  Med3;
    DocListAdap adp3;
    String email;
    TextView text1;
    TextView text2;
    TextView text3;
    Button retour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Firebase.setAndroidContext(this);
        text1 = (TextView)findViewById(R.id.spec1);
        text2 = (TextView)findViewById(R.id.spec3);
        text3 = (TextView)findViewById(R.id.spec2);

        retour = (Button)findViewById(R.id.retPat);

        List1 =(RecyclerView)findViewById(R.id.listMedSpec1);
        List2 = (RecyclerView)findViewById(R.id.listMedSpec2);
        List3 = (RecyclerView)findViewById(R.id.listMedSpec3);

        fStore = FirebaseFirestore.getInstance();
        // spec 1
        Med1 = new ArrayList<>();
        adp1 = new DocListAdap(Med1,this);
        List1.setHasFixedSize(true);
        List1.setLayoutManager(new LinearLayoutManager(this));
        List1.setAdapter(adp1);
        // spec 2
        Med2 = new ArrayList<>();
        adp2 = new DocListAdap(Med2,this);
        List2.setHasFixedSize(true);
        List2.setLayoutManager(new LinearLayoutManager(this));
        List2.setAdapter(adp2);
        // spec 3
        Med3 = new ArrayList<>();
        adp3 = new DocListAdap(Med3,this);
        List3.setHasFixedSize(true);
        List3.setLayoutManager(new LinearLayoutManager(this));
        List3.setAdapter(adp3);
        try {
            email = getIntent().getExtras().getString("patient");
        }catch (Exception e){
            Log.d("Profil"," error"+e.getMessage());
        }

        //List
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(List1.getVisibility()== View.VISIBLE)
                    List1.setVisibility(View.GONE);
                else List1.setVisibility(View.VISIBLE);
            }
        });
        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(List2.getVisibility()== View.VISIBLE)
                    List2.setVisibility(View.GONE);
                else List2.setVisibility(View.VISIBLE);
            }
        });
        text3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(List3.getVisibility()== View.VISIBLE)
                    List3.setVisibility(View.GONE);
                else List3.setVisibility(View.VISIBLE);
            }
        });



        fStore.collection("doctor").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                   if(e!=null){
                       Log.d(TAG,"Error : "+e.getMessage());
                   }
                   for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){
                        if(doc.getType() == DocumentChange.Type.ADDED){
                            String user_id = doc.getDocument().getId();
                            doctor doctors= doc.getDocument().toObject(doctor.class);
                            if(doctors.getSpeciality().equals("OrthoDentiste")) {
                                Med1.add(doctors);
                                adp1.notifyDataSetChanged();
                            }
                            if(doctors.getSpeciality().equals("Denstite")) {
                                Med2.add(doctors);
                                adp2.notifyDataSetChanged();
                            }
                            if(doctors.getSpeciality().equals("Pediatre")) {
                                Med2.add(doctors);
                                adp2.notifyDataSetChanged();
                            }
                        }

                   }
            }
        });
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(Search.this,Profil.class).putExtra("patient",email));
            }
        });
    }

    @Override
    public void onItemClick(int position, ArrayList<doctor> Med) {
        Intent i = new Intent(Search.this,ProfilMed.class);
        i.putExtra("doctor", Med.get(position).getFullName());
        i.putExtra("patient",email);
        startActivity(i);
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

    }

    @Override
    public void onItemClickA(int position, ArrayList<RdvWithId> rdv) {

    }

}
