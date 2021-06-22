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

public class ProfilPatient extends AppCompatActivity {

    FirebaseFirestore fStore;
    FirebaseAuth mAuth;
    Button retour;
    Button dm;
    TextView fullName;
    TextView phone;
    TextView email;
    TextView password;
    TextView birhtDay;
    String idP;
    String emailP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_patient);
        Firebase.setAndroidContext(this);
        idP = getIntent().getExtras().getString("patient");
        mAuth = FirebaseAuth.getInstance();
        fullName =(TextView)findViewById(R.id.pFullname1);
        birhtDay =(TextView)findViewById(R.id.pBirhtday1);
        phone =(TextView)findViewById(R.id.pPhone1);
        email =(TextView)findViewById(R.id.pEmail1);
        password =(TextView)findViewById(R.id.pPasword1);
        dm =(Button)findViewById(R.id.modiferPRo1);
        retour =(Button)findViewById(R.id.retour2);

        fStore= FirebaseFirestore.getInstance();
        String uid = mAuth.getUid();
        fStore.collection("patient").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e!=null){
                    Log.d("patient","Error : "+e.getMessage());
                }
                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){
                    if(doc.getType() == DocumentChange.Type.ADDED){
                            if(doc.getDocument().getId().equals(idP)){
                                fullName.setText(doc.getDocument().getString("fullName"));
                                email.setText(doc.getDocument().getString("email"));
                                birhtDay.setText(doc.getDocument().getString("birthDay"));
                                phone.setText(doc.getDocument().getString("phone"));
                                password.setText(doc.getDocument().getString("password"));
                                emailP=doc.getDocument().get("fullName").toString();

                            }
                    }
                }
            }
        });
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfilPatient.this,SearchMed.class);
                i.putExtra("patient",idP);
                startActivity(i);
            }
        });
        dm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfilPatient.this,PatientDm.class);
                i.putExtra("patient",emailP);
                startActivity(i);

            }
        });

    }
}
