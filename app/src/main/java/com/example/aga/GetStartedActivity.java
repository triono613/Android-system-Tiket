package com.example.aga;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GetStartedActivity extends AppCompatActivity {

    Button btn_pendaftaran_petugas ;
    Button btn_pendaftaran_wisatawan ;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        btn_pendaftaran_petugas = findViewById(R.id.btn_pendaftaran_petugas);
        btn_pendaftaran_petugas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goto_regis_petugas = new Intent(GetStartedActivity.this,RegisterPetugasActivity.class);
                startActivity(goto_regis_petugas);
            }
        });


        btn_pendaftaran_wisatawan = findViewById(R.id.btn_pendaftaran_wisatawan);
        btn_pendaftaran_wisatawan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goto_regis_wisatawan = new Intent(GetStartedActivity.this,RegisterWisatawanActivity.class);
                startActivity(goto_regis_wisatawan);
            }
        });

        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goto_login = new Intent(GetStartedActivity.this,SigninActivity.class);
                startActivity(goto_login);
            }
        });


    }
}
