package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText bEmail;
    EditText bPassword;
    FirebaseAuth mAuth;
    String type;
    String email;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(R.layout.activity_main);


        }catch (Exception e){
            Log.e("TAG","",e);
        }
        bEmail = (EditText) findViewById(R.id.tLogin);
        bPassword = (EditText)findViewById(R.id.tPassword);
        mAuth = FirebaseAuth.getInstance();
        Button login = (Button)findViewById(R.id.tSignin);
        Button bCA = (Button)findViewById(R.id.tSignUp);
        Intent pi= getIntent();

        type =pi.getExtras().getString("type");
        bEmail.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                bEmail.setText("");
                return false;
            }
        });
        bPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                bPassword.setText("");
                return false;
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email =bEmail.getText().toString();
                String password = bPassword.getText().toString() ;
                mAuth.signInWithEmailAndPassword(email,password ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this," Logged in successfully ",Toast.LENGTH_SHORT);
                            if(type.equals("p")){
                            Intent i = new Intent(MainActivity.this,Profil.class);
                            i.putExtra("patient",email);
                            startActivity(i);}
                            else
                            {
                             startActivity(new Intent(MainActivity.this,choixMed.class));
                            }
                        }else {
                            Log.w(TAG, "create User With Email:failure :", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        bCA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Create_account.class);
                i.putExtra("type",type);
                startActivity(i);
            }
        });

    }
}
