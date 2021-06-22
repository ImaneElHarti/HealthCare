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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Create_account extends AppCompatActivity {



    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    private String email;
    private String password;
    private String Name;
    private String BirthDate;
    private String Phone;
    EditText etEmail;
    EditText etPassword;
    EditText etCPassword;
    EditText etName;
    EditText etBirth;
    EditText etTel;
    Spinner etSpeciality;
    Button bCA;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    String userID;
    String type;
    String spec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        etEmail  =(EditText) findViewById(R.id.email);
        etPassword = (EditText)findViewById(R.id.pwd);
        etCPassword =(EditText) findViewById(R.id.cPwd);
        etName = (EditText) findViewById(R.id.Name);
        etBirth = (EditText) findViewById(R.id.bd);
        etTel =(EditText)findViewById(R.id.Tel);
        bCA = (Button)findViewById(R.id.cA);
        etSpeciality =(Spinner)findViewById(R.id.speciality);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        Intent pi = getIntent();
        type =pi.getExtras().getString("type");

        if(type.equals("d")){
            etSpeciality.setVisibility(View.VISIBLE);
            etBirth.setVisibility(View.INVISIBLE);
        }
        if(type.equals("p")){
            etSpeciality.setVisibility(View.INVISIBLE);
            etBirth.setVisibility(View.VISIBLE);
        }
        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(this, R.array.specialite,android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter
                .setDropDownViewResource(R.layout.spinner_item);

        // Apply the adapter to the spinner
        etSpeciality.setAdapter(staticAdapter);



        etEmail.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                etEmail.setText("");
                return false;
            }

        });
        etPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                etPassword.setText("");
                return false;
            }


        });
        etCPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                etCPassword.setText("");
                return false;
            }


        });
        etName.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                etName.setText("");
                return false;
            }

        });
        etBirth.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                etBirth.setText("");
                return false;
            }


        });
        etTel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                etTel.setText("");
                return false;
            }


        });



        bCA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(etEmail.getText()) ){
                    etEmail.setError("Email required");
                    return;
                }
                if (TextUtils.isEmpty(etPassword.getText()) ){
                    etPassword.setError("Password required");
                    return;
                }
                password = etPassword.getText().toString();
                String Cpassword = etCPassword.getText().toString();
                if (password==Cpassword){
                    etCPassword.setError(" the Password are not identical");
                    return;
                }
                if(password.length()<6){
                    etPassword.setError("Password must have more than 6 characters");
                    return;
                }
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                Name = etName.getText().toString();
                BirthDate = etBirth.getText().toString();
                Phone = etTel.getText().toString();
                spec = etSpeciality.getSelectedItem().toString();


                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Create_account.this,new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information

                                    progressBar.setVisibility(View.VISIBLE);
                                    Log.d(TAG, "create User With Email:success");
                                    DocumentReference docRef=null;

                                    userID= mAuth.getCurrentUser().getUid();
                                    if (type.equals("d"))
                                        docRef = fStore.collection("doctor").document(userID);
                                    if(type.equals("p"))
                                        docRef = fStore.collection("patient").document(userID);
                                    Map<String,Object> user = new HashMap<>();
                                    user.put("email",email);
                                    user.put("fullName", Name);
                                    if (type.equals("d"))
                                        user.put("speciality",spec);
                                    if(type.equals("p")){
                                        user.put("birthDay",BirthDate);
                                        Map<String,Object> dos = new HashMap<>();
                                        dos.put("note","");
                                        dos.put("visite","");
                                        dos.put("medicament","");
                                        dos.put("patient",mAuth.getUid());
                                        fStore.collection("dossier medical").add(dos);
                                    }
                                    user.put("phone",Phone);
                                    user.put("password",password);
                                    docRef.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG,"Succes");
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG,"faillure: "+e.toString());
                                        }
                                    });


                                    if (type.equals("d")) {
                                        Intent i = new Intent(Create_account.this, choixMed.class);
                                        i.putExtra("patient", email);
                                        startActivity(i);
                                    }
                                    if(type.equals("p")){
                                        Intent i = new Intent(Create_account.this, Profil.class);
                                        i.putExtra("patient", email);
                                        startActivity(i);
                                    }

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "create User With Email:failure :", task.getException());
                                    Toast.makeText(Create_account.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }

                                // ...
                            }
                        });

            }
        });


    }
}
