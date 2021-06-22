package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ProfilClient extends AppCompatActivity {

    String name;
    FirebaseFirestore fStore;
    FirebaseAuth mAuth;
    Button retour;
    Button modifier;
    TextView fullName;
    TextView phone;
    TextView email;
    TextView password;
    TextView birhtDay;
    String emailP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_client);
        Firebase.setAndroidContext(this);
        emailP = getIntent().getExtras().getString("patient");
        mAuth = FirebaseAuth.getInstance();
        fullName =(TextView)findViewById(R.id.pFullname);
        birhtDay =(TextView)findViewById(R.id.pBirhtday);
        phone =(TextView)findViewById(R.id.pPhone);
        email =(TextView)findViewById(R.id.pEmail);
        password =(TextView)findViewById(R.id.pPasword);
        modifier =(Button)findViewById(R.id.modiferPRo);
        retour =(Button)findViewById(R.id.retour);

        fStore=FirebaseFirestore.getInstance();
        String uid = mAuth.getUid();
        DocumentReference df = (DocumentReference) fStore.collection("patient").document(uid);
        df.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                fullName.setText(documentSnapshot.getString("fullName"));
                email.setText(documentSnapshot.getString("email"));
                birhtDay.setText(documentSnapshot.getString("birthDay"));
                phone.setText(documentSnapshot.getString("phone"));
                password.setText(documentSnapshot.getString("password"));


            }
        });
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfilClient.this,Profil.class);
                i.putExtra("patient",emailP);
                startActivity(i);
            }
        });
        modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfilClient.this,ModifierProfil.class);
                i.putExtra("email",emailP);
                i.putExtra("type","p");
                startActivity(i);
            }
        });

    }
}
