package com.example.aga;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class SuccessRegistrasiWisatawanActivity extends AppCompatActivity {

    TextView textview_ket, textview_email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_registrasi_wisatawan);

        textview_ket = (TextView) findViewById(R.id.textview_ket);
        textview_email = (TextView) findViewById(R.id.textview_email);
        String sessionIntentKet = getIntent().getStringExtra("result_dt_ket");
        String sessionIntentEmail = getIntent().getStringExtra("result_dt_email");

        Log.i("triono", "sessionIntentKet ===" + sessionIntentKet.toUpperCase() );

        textview_ket.setText(sessionIntentKet.toUpperCase());
        textview_email.setText(sessionIntentEmail.toUpperCase());



    }
}
