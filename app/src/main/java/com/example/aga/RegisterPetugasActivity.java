package com.example.aga;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class RegisterPetugasActivity extends AppCompatActivity {
    ImageView btn_pendaftaran_petugas_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_petugas);



        btn_pendaftaran_petugas_back = findViewById(R.id.btn_pendaftaran_petugas_back);
        btn_pendaftaran_petugas_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent goto_getstarted = new Intent(RegisterPetugasActivity.this,GetStartedActivity.class);
                startActivity(goto_getstarted);
            }

        });

    }
}
