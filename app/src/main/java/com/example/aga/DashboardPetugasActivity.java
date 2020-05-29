package com.example.aga;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aga.Adapter.SessionManager;

public class DashboardPetugasActivity extends AppCompatActivity {
    Button btn_logout_ptgs;
    SessionManager sessionManager;
    Dialog myDialog;
    TextView txtclose,_textview_email_session_ptgs;
    LinearLayout _card_pemesanan_karcis_ptgs;
    LinearLayout _card_status_karcis_ptgs;
    LinearLayout _card_setup_pintu_ptgs;
    LinearLayout _card_pengajuan_klaim_ptgs;



    @Override
    public void onBackPressed() {

//        Intent intent = new Intent(DashboardPetugasActivity.this, SigninActivity.class)
//                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        finish();
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);

//        Toast.makeText(DashboardPetugasActivity.this,"Back Button wstwn is clicked.", Toast.LENGTH_LONG).show();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);

//        Toast.makeText(DashboardPetugasActivity.this, "check session DashboardPetugasActivity: " + sessionManager.isLoggedIn() + " - " + sessionManager.getFlag() + " - " + (sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi)), Toast.LENGTH_LONG).show();


    }

    public void onSuperBackPressed(){
        super.onBackPressed();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_petugas);
        sessionManager = new SessionManager(getApplicationContext());

        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        btn_logout_ptgs = (Button) findViewById(R.id.btn_logout_ptgs);
        _card_pemesanan_karcis_ptgs = (LinearLayout) findViewById(R.id.card_pemesanan_karcis_ptgs);
        _textview_email_session_ptgs = (TextView) findViewById(R.id.textview_email_session_ptgs);
        _card_status_karcis_ptgs = (LinearLayout) findViewById(R.id.card_status_karcis_ptgs);
        _card_setup_pintu_ptgs = (LinearLayout) findViewById(R.id.card_setup_pintu_ptgs);
        _card_pengajuan_klaim_ptgs = (LinearLayout) findViewById(R.id.card_pengajuan_klaim_ptgs);


//        Toast.makeText(DashboardPetugasActivity.this,"check session : "+sessionManager.isLoggedIn()+" - "+sessionManager.getFlag()+" - "+(sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi)), Toast.LENGTH_LONG).show();

        Log.i("DashboardPetugasActivity","sessionManager.isLoggedIn() = "+ sessionManager.isLoggedIn() );
        Log.i("DashboardPetugasActivity","sessionManager.getFlag= "+ sessionManager.getFlag());
        Log.i("DashboardPetugasActivity","SessionManager.key_kode_lokasi= "+  sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi));



        myDialog = new Dialog(this);

        btn_logout_ptgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DashboardPetugasActivity.this);
                builder.setMessage("Anda Yakin Akan Keluar ?")
                        .setCancelable(false)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
//                                DashboardPetugasActivity.this.onSuperBackPressed();
                                sessionManager.logout();
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });


        _card_pemesanan_karcis_ptgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sessionManager.isLoggedIn()){
                    Intent i = new Intent(DashboardPetugasActivity.this, PesanKarcisPetugasActivity.class);
                    startActivity(i);
                } else {

                }
            }
        });

        _card_status_karcis_ptgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( sessionManager.isLoggedIn()) {
                    Intent i = new Intent(DashboardPetugasActivity.this,StatusKarcisPetugasActivity.class);
//                    Intent i = new Intent(DashboardPetugasActivity.this,CariActivity.class);
                    startActivity(i);


                }
            }
        });

        _card_setup_pintu_ptgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( sessionManager.isLoggedIn()) {
                    Intent i = new Intent(DashboardPetugasActivity.this,SetupPintuActivity.class);
//                    Intent i = new Intent(DashboardPetugasActivity.this,CariActivity.class);
                    startActivity(i);

                }

            }
        });

        _card_pengajuan_klaim_ptgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( sessionManager.isLoggedIn() ){

                    Intent i = new Intent(DashboardPetugasActivity.this, ClaimPetugasActivity.class);
                    startActivity(i);

                }
            }
        });




        String key_name_a = sessionManager.getUserDetail().get(SessionManager.key_name);

        String key_flag = sessionManager.getUserDetail().get(SessionManager.key_flag);

        Log.i("DashboardPetugasActivity ","sessionManager.isLoggedIn() " + sessionManager.isLoggedIn());
        Log.i("DashboardPetugasActivity ","key_flag " + key_flag);

        _textview_email_session_ptgs.setText(sessionManager.getUserDetail().get(SessionManager.key_email) );



    }
}
