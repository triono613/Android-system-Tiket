package com.example.aga;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aga.Adapter.SessionManager;

public class SuccessRegistrasiWisatawanActivity extends AppCompatActivity {

    TextView textview_ket, textview_email;
    Button btn_continue;
    ImageView btn_back_regis;
    SessionManager sessionManager;

    @Override
    public void onBackPressed() {
//        Toast.makeText(SuccessRegistrasiWisatawanActivity.this,"Back Button wstwn is clicked.", Toast.LENGTH_LONG).show();
//        Intent a = new Intent(Intent.ACTION_MAIN);
//        a.addCategory(Intent.CATEGORY_HOME);
//        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(a);


        Intent intent = new Intent(SuccessRegistrasiWisatawanActivity.this, GetStartedActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    public void onSuperBackPressed(){
        super.onBackPressed();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_registrasi_wisatawan);
        sessionManager = new SessionManager(getApplicationContext());

        textview_ket = (TextView) findViewById(R.id.textview_ket);
        textview_email = (TextView) findViewById(R.id.textview_email);
        btn_continue = (Button) findViewById(R.id.btn_continue);
        btn_back_regis = (ImageView) findViewById(R.id.btn_back_regis);

        String sessionIntentFlag = getIntent().getStringExtra("result_dt_flag");
        String sessionIntentKet = getIntent().getStringExtra("result_dt_ket");
        String sessionIntentEmail = getIntent().getStringExtra("result_dt_email");
        Boolean sessionIntentBerhasil = getIntent().getBooleanExtra("result_dt_berhasil",false);

        textview_ket.setText(sessionIntentKet.toUpperCase());
        textview_email.setText(sessionIntentEmail.toLowerCase());



        Log.i("SigninActivity","sessionManager.isLoggedIn() = "+ sessionManager.isLoggedIn() );
        Log.i("SigninActivity","sessionManager.getFlag= "+ sessionManager.getFlag());
        Log.i("SigninActivity","SessionManager.key_kode_lokasi= "+  sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi));




//        Toast.makeText(SuccessRegistrasiWisatawanActivity.this,"check session : "+sessionManager.isLoggedIn()+" - "+sessionManager.getFlag()+" - "+(sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi)), Toast.LENGTH_LONG).show();
//
        if ( sessionIntentBerhasil.equals(true) ){
            textview_ket.setText(sessionIntentKet.toUpperCase());
            textview_email.setText(sessionIntentEmail.toLowerCase());
            findViewById(R.id.btn_back_regis).setVisibility(View.GONE);
        } else {
                    textview_ket.setTextColor(Color.parseColor("#E64788"));
                    textview_ket.setText(sessionIntentKet.toUpperCase());
                    textview_email.setText(sessionIntentEmail.toLowerCase());
                    findViewById(R.id.btn_continue).setVisibility(View.GONE);
//            btn_continue.setText("Kembali");
//            btn_continue.setCompoundDrawables(btn_continue.getContext().getResources().getDrawable( R.drawable.ic_send_black_24dp ),null,null,null);
        }


        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logout();
                Intent i = new Intent(SuccessRegistrasiWisatawanActivity.this, SigninActivity.class);
                startActivity(i);

                overridePendingTransition(R.anim.app_getstarted,R.anim.btt);
            }
        });

        btn_back_regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logout();
                finish();

            }
        });





    }
}
