package com.example.aga;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aga.Adapter.SessionManager;
import com.example.aga.Helper.Help;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DashboardPetugasActivity extends AppCompatActivity {
    Button btn_logout_ptgs;
    SessionManager sessionManager;
    Dialog myDialog;
    TextView txtclose,_textview_email_session_ptgs;
    LinearLayout _card_pemesanan_karcis_ptgs;
    LinearLayout _card_status_karcis_ptgs;
    LinearLayout _card_setup_pintu_ptgs;
    LinearLayout _card_pengajuan_klaim_ptgs;
    LinearLayout _card_ganti_password_ptgs;



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
        _card_ganti_password_ptgs = (LinearLayout) findViewById(R.id.card_ganti_password_ptgs);

        this.bounceNotif("aa");

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

                    final  String _ksda = sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi);
                    checkQuotaTwa("quota_per_twa",_ksda);
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
                    i.putExtra("result_dt_flag", "fromDashboardPetugas");
                    startActivity(i);
                }
            }
        });

        _card_ganti_password_ptgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( sessionManager.isLoggedIn() ) {
                    Intent i = new Intent(DashboardPetugasActivity.this, EditPasswordPetugasActivity.class);
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

    private void bounceNotif(String a){
        Animation animation = AnimationUtils.loadAnimation(
                getApplicationContext(),
                R.anim.notif
        );
        ImageView img = (ImageView) findViewById(R.id.imgBell);
        img.startAnimation(animation);
        animation.setRepeatCount(Animation.INFINITE);
    }


    private void checkQuotaTwa(String EP,String KSDA){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://kaffah.amanahgitha.com/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(DashboardPetugasActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {
                        Log.i("tag", "response checkQuotaTwa =" + response );
                        try {
                            findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                            if( Help.isJSONValid(response) ){

                                JSONObject jsonObject = new JSONObject(response);
                                String data = jsonObject.getString("data");
                                JSONObject jsonObject1 = new JSONObject(data);
                                boolean _berhasil = jsonObject1.getBoolean("berhasil");

//                                if( _berhasil ) {
                                String  _id = jsonObject1.getString("id");
                                int _quota = jsonObject1.getInt("quota");
                                String _ket = jsonObject1.getString("keterangan");
                                Log.i("","_keterangan "+_ket);
//                                Log.i("","_berhasil "+_berhasil);
//                                Log.i("","qouta "+_quota);
//                                Log.i("","_keterangan "+_keterangan);

//                                _quota = 100;
                                Log.i("","_quota"+ _quota);

                                if( _quota > 0  ) {
                                    Intent ix = new Intent(DashboardPetugasActivity.this, PesanKarcisPetugasActivity.class);
                                    startActivity(ix);
                                }else {
                                    Intent i = new Intent(DashboardPetugasActivity.this, SuccessRegistrasiWisatawanActivity.class);
                                    final String _email = sessionManager.getUserDetail().get(SessionManager.key_email);
                                    i.putExtra("result_dt_ket", _ket);
                                    i.putExtra("result_dt_email",_email);
                                    i.putExtra("result_dt_berhasil", false);
                                    i.putExtra("result_dt_flag", "checkQuotaTwa");
                                    startActivity(i);
                                }
                            }

                        } catch (JSONException e) {
                            Log.i("", "error =" + e.toString() );
                            e.printStackTrace();
                        }
                        requestQueue.stop();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("", "response =" + error.toString());
                error.printStackTrace();
                requestQueue.stop();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> obj = new HashMap<String, String>();
                final String _ksda = sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi);
                Log.i("","_ksda"+_ksda);
                obj.put("kode_ksda", _ksda );
                obj.put("tgl_quota",Help.getDateTime());
                return obj;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

}
