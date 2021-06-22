package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class choosing extends AppCompatActivity {

    Button doctor ;
    Button patient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosing);
        doctor = (Button)findViewById(R.id.doctor);
        patient = (Button)findViewById(R.id.patient);


        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(choosing.this,MainActivity.class).putExtra("type","d"));
            }
        });
        patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(choosing.this,MainActivity.class).putExtra("type","p"));
            }
        });

    }
}
