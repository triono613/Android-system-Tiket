package com.example.aga;

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

public class DashboardWisatawanActivity extends AppCompatActivity {
    Button btn_logout_wstn;
    LinearLayout _card_pemesanan_karcis_wstn;
    LinearLayout _card_status_karcis_wstn;
    LinearLayout _card_ganti_password_wstwn;
    SessionManager sessionManager;
    TextView _textview_email_session;

    @Override
    public void onBackPressed() {
//        Toast.makeText(DashboardWisatawanActivity.this,"Back Button wstwn is clicked.", Toast.LENGTH_LONG).show();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
//        Toast.makeText(DashboardWisatawanActivity.this, "check session DashboardWisatawanActivity: " + sessionManager.isLoggedIn() + " - " + sessionManager.getFlag() + " - " + (sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi)), Toast.LENGTH_LONG).show();
    }



    public void onSuperBackPressed(){
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_wisatawan);

        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        btn_logout_wstn =(Button) findViewById(R.id.btn_logout_wstn);
        _card_pemesanan_karcis_wstn = (LinearLayout) findViewById(R.id.card_pemesanan_karcis_wstn);
        _card_status_karcis_wstn = (LinearLayout) findViewById(R.id.card_status_karcis_wstn);
        _textview_email_session = (TextView) findViewById(R.id.textview_email_session);
        _card_ganti_password_wstwn = (LinearLayout) findViewById(R.id.card_ganti_password_wstwn);

        sessionManager = new SessionManager(getApplicationContext());



        Log.i("DashboardWisatawanActivity","sessionManager.isLoggedIn() = "+ sessionManager.isLoggedIn() );
        Log.i("DashboardWisatawanActivity","sessionManager.getFlag= "+ sessionManager.getFlag());
        Log.i("DashboardWisatawanActivity","SessionManager.key_kode_lokasi= "+  sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi));


//        Toast.makeText(DashboardWisatawanActivity.this,"check session : "+sessionManager.isLoggedIn()+" - "+sessionManager.getFlag()+" - "+(sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi)), Toast.LENGTH_LONG).show();

        _card_pemesanan_karcis_wstn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardWisatawanActivity.this,PesanKarcisWisatawanActivity.class);
                startActivity(intent);
            }
        });

        btn_logout_wstn.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(DashboardWisatawanActivity.this);
            builder.setMessage("Anda Yakin Akan Keluar ?")
                    .setCancelable(false)
                    .setPositiveButton("Ya", (dialog, id) -> {
//                                DashboardPetugasActivity.this.onSuperBackPressed();
                        sessionManager.logout();
                    })
                    .setNegativeButton("Tidak", (dialog, id) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
        });

        _card_status_karcis_wstn.setOnClickListener(v -> {
            Intent i = new Intent(DashboardWisatawanActivity.this, StatusKarcisActivity.class);
            startActivity(i);
//            overridePendingTransition(R.anim.app_getstarted,R.anim.btt);
        });

        _card_ganti_password_wstwn.setOnClickListener(v -> {
            Intent x = new Intent(DashboardWisatawanActivity.this, EditPasswordWisatawanActivity.class);
            startActivity(x);
        });


        sessionManager = new SessionManager(getApplicationContext());
        String key_name_a = sessionManager.getUserDetail().get(SessionManager.key_name);
        String key_email_a = sessionManager.getUserDetail().get(SessionManager.key_email);
        String is_login_a = sessionManager.getUserDetail().get(SessionManager.is_login);
        String is_flag_a = sessionManager.getUserDetail().get(SessionManager.key_flag);

        _textview_email_session.setText(sessionManager.getUserDetail().get(SessionManager.key_email) );

//        Log.i("triono ","key_name_a " + key_name_a);
//        Log.i("triono ","key_email_a " + key_email_a);
//        Log.i("triono ","is_flag_a " + is_flag_a);


//        user.get(key_email)

//        Log.i("triono ","user.get" + name);

    }
}
