package com.amanahgithawisata.aga;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amanahgithawisata.aga.Adapter.SessionManager;

import java.util.Objects;

public class GetStartedActivity extends AppCompatActivity {
    Animation appSplash, btt;
    TextView lbl_aga_getstarted,lbl_wanawisata_getstarted;
    ImageView logo_aga_getstarted;
    ImageView _img_utama;

    Button btn_pendaftaran_petugas ;
    Button btn_pendaftaran_wisatawan ;
    Button btn_login;

    SessionManager sessionManager;


    @Override
    public void onBackPressed() {

//        Intent a = new Intent(Intent.ACTION_MAIN);
//        a.addCategory(Intent.CATEGORY_HOME);
//        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(a);
//        sessionManager.logout();
        finish();

//        Toast.makeText(GetStartedActivity.this, "check session GetStartedActivity: " + sessionManager.isLoggedIn() + " - " + sessionManager.getFlag() + " - " + (sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi)), Toast.LENGTH_LONG).show();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        btn_login = findViewById(R.id.btn_login);
        btn_pendaftaran_petugas = findViewById(R.id.btn_pendaftaran_petugas);
        btn_pendaftaran_wisatawan = findViewById(R.id.btn_pendaftaran_wisatawan);

        appSplash = AnimationUtils.loadAnimation(this,R.anim.app_splash);
        btt  = AnimationUtils.loadAnimation(this,R.anim.btt);

        lbl_wanawisata_getstarted = (TextView) findViewById(R.id.lbl_wanawisata_getstarted);
        lbl_aga_getstarted = (TextView) findViewById(R.id.lbl_aga_getstarted);
        logo_aga_getstarted = (ImageView) findViewById(R.id.logo_aga_getstarted);
        _img_utama= (ImageView) findViewById(R.id.img_utama);

        lbl_wanawisata_getstarted.startAnimation(appSplash);
        lbl_aga_getstarted.startAnimation(appSplash);
        logo_aga_getstarted.startAnimation(appSplash);
        _img_utama.startAnimation(appSplash);
        btn_login.startAnimation(btt);
        btn_pendaftaran_petugas.startAnimation(btt);
        btn_pendaftaran_wisatawan.startAnimation(btt);

        sessionManager = new SessionManager(getApplicationContext());


//        Toast.makeText(GetStartedActivity.this,"check session : "+sessionManager.isLoggedIn()+" - "+sessionManager.getFlag()+" - "+(sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi)), Toast.LENGTH_LONG).show();


        Log.i("GetStartedActivity","sessionManager.isLoggedIn() = "+ sessionManager.isLoggedIn() );
        Log.i("GetStartedActivity","sessionManager.getFlag= "+ sessionManager.getFlag());
        Log.i("GetStartedActivity","SessionManager.key_kode_lokasi= "+  sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi));


        if( sessionManager.isLoggedIn() ){

            Intent intent;

            switch (sessionManager.getFlag())
            {
                case "1":
                    if (Objects.equals(sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi), "null")){
                        intent = new Intent(GetStartedActivity.this, DashboardWisatawanActivity.class);
                        break;
                    } else {
                        intent = new Intent(GetStartedActivity.this, DashboardPetugasActivity.class);
                    }

                case "0":
                    intent = new Intent(GetStartedActivity.this, DashboardPetugasActivity.class);
                    break;
                default:
                    intent = new Intent(GetStartedActivity.this,SigninActivity.class);
                    break;
            }
            startActivity(intent);
            finish();
        }


        btn_pendaftaran_petugas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goto_regis_petugas = new Intent(GetStartedActivity.this,RegisterPetugasActivity.class);
                startActivity(goto_regis_petugas);
            }
        });



        btn_pendaftaran_wisatawan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goto_regis_wisatawan = new Intent(GetStartedActivity.this,RegisterWisatawanActivity.class);
                startActivity(goto_regis_wisatawan);
            }
        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goto_login = new Intent(GetStartedActivity.this,SigninActivity.class);
                startActivity(goto_login);
            }
        });


    }
}
