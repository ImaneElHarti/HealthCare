package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class medcinProfile extends AppCompatActivity {
    String name;
    FirebaseFirestore fStore;
    TextView fullname;
    TextView Phone;
    TextView email;
    TextView speciality;
    Button retour;
    String emailP;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medcin_profile);
        Firebase.setAndroidContext(this);
        mAuth=FirebaseAuth.getInstance();

        retour = (Button)findViewById(R.id.Retour1);
        fullname =(TextView) findViewById(R.id.FName1);
        email =(TextView) findViewById(R.id.AMail1);
        Phone =(TextView) findViewById(R.id.PhoneN1);
        speciality =(TextView) findViewById(R.id.Spec1);
        fStore=FirebaseFirestore.getInstance();
        fStore.collection("doctor").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e!=null){
                    Log.d("ProfilMed","Error : "+e.getMessage());
                }
                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){
                    if(doc.getType() == DocumentChange.Type.ADDED){
                        String user_id = doc.getDocument().getId();
                        doctor doctors= doc.getDocument().toObject(doctor.class);
                        if(user_id.equals(mAuth.getUid())){
                            fullname.setText(doctors.getFullName());
                            email.setText(doctors.getEmail());
                            Phone.setText(doctors.getPhone());
                            speciality.setText(doctors.getSpeciality());
                            emailP = doctors.getEmail();
                        }
                    }

                }
            }
        });

        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(medcinProfile.this,choixMed.class);
                i.putExtra("patient",emailP);
                startActivity(i);
            }
        });

    }
}
