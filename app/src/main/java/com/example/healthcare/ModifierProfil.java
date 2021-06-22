package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class ModifierProfil extends AppCompatActivity {

    Map<String,Object> user;
    EditText fullName;
    EditText email;
    EditText birthDay;
    Spinner speciality;
    EditText password;
    EditText phoneNumber;
    EditText cPassword;
    String type;
    Button bUpdate;
    FirebaseFirestore fStore;
    FirebaseAuth mAuth;
    DocumentReference df;
    String uid;
    String patient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_profil);
        Firebase.setAndroidContext(this);
        patient = getIntent().getExtras().getString("patient");
        fullName = (EditText)findViewById(R.id.Namem);
        email = (EditText)findViewById(R.id.emailm);
        birthDay = (EditText)findViewById(R.id.bdm);
        password = (EditText)findViewById(R.id.pwdm);
        speciality = (Spinner)findViewById(R.id.specialitym);
        cPassword = (EditText)findViewById(R.id.cPwdm);
        phoneNumber = (EditText)findViewById(R.id.Telm);
        Intent pi = getIntent();
        type =pi.getExtras().getString("type");
        fStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();


        bUpdate =(Button)findViewById(R.id.cAm);

        if(type.equals("d")){
            speciality.setVisibility(View.VISIBLE);
            birthDay.setVisibility(View.INVISIBLE);
            uid = mAuth.getUid();
            df = fStore.collection("doctor").document(uid);
        }
        if(type.equals("p")){
            speciality.setVisibility(View.INVISIBLE);
            birthDay.setVisibility(View.VISIBLE);
            uid = mAuth.getUid();
            df = fStore.collection("patient").document(uid);

            df.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    fullName.setText(documentSnapshot.getString("fullName"));
                    email.setText(documentSnapshot.getString("email"));
                    birthDay.setText(documentSnapshot.getString("birthDay"));
                    phoneNumber.setText(documentSnapshot.getString("phone"));
                    password.setText(documentSnapshot.getString("password"));


                }
            });


        }





        // Create an ArrayAdapter using the string array and a default spinner
        final ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(this, R.array.specialite,android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter
                .setDropDownViewResource(R.layout.spinner_item);

        // Apply the adapter to the spinner
        speciality.setAdapter(staticAdapter);

        email.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                email.setText("");
                return false;
            }

        });
        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                password.setText("");
                return false;
            }


        });
        cPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                cPassword.setText("");
                return false;
            }


        });
        fullName.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                fullName.setText("");
                return false;
            }

        });
        birthDay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
               birthDay.setText("");
                return false;
            }


        });
        phoneNumber.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                phoneNumber.setText("");
                return false;
            }


        });



        bUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(email.getText())) {
                    email.setError("Email required");
                    return;
                }
                if (TextUtils.isEmpty(password.getText())) {
                    password.setError("Password required");
                    return;
                }
                if (password.getText().toString() == cPassword.getText().toString()) {
                    cPassword.setError(" the Password are not identical");
                    return;
                }
                if (password.length() < 6) {
                    password.setError("Password must have more than 6 characters");
                    return;
                }


                user = new HashMap<>();
                user.put("email",email.getText().toString());
                user.put("fullName", fullName.getText().toString());
                if(type.equals("p"))
                    user.put("birthDay",birthDay.getText().toString());
                user.put("phone",phoneNumber.getText().toString());
                user.put("password",password.getText().toString());

                df.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("ModifierProfil","Succes");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("ModifierProfil","faillure: "+e.toString());
                    }
                });

                Intent i = new Intent(ModifierProfil.this,ProfilClient.class).putExtra("patient",patient);
                startActivity(i);
            }});


    }
}
