package com.amanahgithawisata.aga;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.amanahgithawisata.aga.Adapter.SessionManager;
import com.amanahgithawisata.aga.Helper.Help;
import com.amanahgithawisata.aga.Model.EntityStatusKarcisWisatawan;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DashboardWisatawanOLdActivity extends AppCompatActivity {
    Button btn_logout_wstn;
    LinearLayout _card_pemesanan_karcis_wstn;
    LinearLayout _card_status_karcis_wstn;
    LinearLayout _card_ganti_password_wstwn;
    LinearLayout _card_horizontal_wstwn;
    LinearLayout _card_order_ticket;
    LinearLayout _card_dashboard_wisatawan_new;

    SessionManager sessionManager;
    TextView _textview_email_session;
    ArrayList<EntityStatusKarcisWisatawan> entityStatusKarcisArrayList;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    public void onBackPressed() {
//        Toast.makeText(DashboardWisatawanOLdActivity.this,"Back Button wstwn is clicked.", Toast.LENGTH_LONG).show();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
//        Toast.makeText(DashboardWisatawanOLdActivity.this, "check session DashboardWisatawanOLdActivity: " + sessionManager.isLoggedIn() + " - " + sessionManager.getFlag() + " - " + (sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi)), Toast.LENGTH_LONG).show();
    }



    public void onSuperBackPressed(){
        super.onBackPressed();
    }

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_wisatawan_old);

        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        btn_logout_wstn =(Button) findViewById(R.id.btn_logout_wstn);
        _card_pemesanan_karcis_wstn = (LinearLayout) findViewById(R.id.card_pemesanan_karcis_wstn);
        _card_status_karcis_wstn = (LinearLayout) findViewById(R.id.card_status_karcis_wstn);
        _textview_email_session = (TextView) findViewById(R.id.textview_email_session);
        _card_ganti_password_wstwn = (LinearLayout) findViewById(R.id.card_ganti_password_wstwn);
        _card_horizontal_wstwn = (LinearLayout) findViewById(R.id.card_horizontal_wstwn);
        _card_order_ticket = (LinearLayout) findViewById(R.id.card_order_ticket);
        _card_dashboard_wisatawan_new = findViewById(R.id.card_dashboard_wisatawan_new);
        sessionManager = new SessionManager(getApplicationContext());

        String result_flag_pesan_karcis_sukses = getIntent().getStringExtra("result_flag_pesan_karcis_sukses");

        Log.i("DashboardWisatawanOLdActivity","result_flag_pesan_karcis_sukses dash = "+ result_flag_pesan_karcis_sukses );
        Log.i("DashboardWisatawanOLdActivity","sessionManager.isLoggedIn() = "+ sessionManager.isLoggedIn() );
        Log.i("DashboardWisatawanOLdActivity","sessionManager.getFlag= "+ sessionManager.getFlag());
        Log.i("DashboardWisatawanOLdActivity","SessionManager.key_kode_lokasi= "+  sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi));


//        Toast.makeText(DashboardWisatawanOLdActivity.this,"check session : "+sessionManager.isLoggedIn()+" - "+sessionManager.getFlag()+" - "+(sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi)), Toast.LENGTH_LONG).show();

        _card_order_ticket.setOnClickListener(v -> {
            Intent i = new Intent(DashboardWisatawanOLdActivity.this, OrderTicketWisatawanActivity.class);
            startActivity(i);
        });

        _card_pemesanan_karcis_wstn.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardWisatawanOLdActivity.this, PesanKarcisWisatawanActivity.class);
//                Intent intent = new Intent(DashboardWisatawanOLdActivity.this, LokasiWisataScrollViewActivity.class);


            if( result_flag_pesan_karcis_sukses != null ){
                Log.i("","dashboard sukses");
                intent.putExtra("result_flag_pesan_karcis_sukses", "sukses");
            }else {
                Log.i("","dashboard not sukses");
                intent.putExtra("result_flag_pesan_karcis_sukses", "");
            }

            startActivity(intent);
        });

        btn_logout_wstn.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(DashboardWisatawanOLdActivity.this);
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
            Intent i = new Intent(DashboardWisatawanOLdActivity.this, StatusKarcisWisatawanActivity.class);
            startActivity(i);
//            overridePendingTransition(R.anim.app_getstarted,R.anim.btt);
        });

        _card_ganti_password_wstwn.setOnClickListener(v -> {
            Intent x = new Intent(DashboardWisatawanOLdActivity.this, EditPasswordWisatawanActivity.class);
            startActivity(x);
        });

        _card_horizontal_wstwn.setOnClickListener(v -> {

            Intent x = new Intent(DashboardWisatawanOLdActivity.this, PesanKarcisWisatawanOldActivity.class);
            startActivity(x);

        });

        _card_dashboard_wisatawan_new.setOnClickListener(v -> {
            Intent x = new Intent(getApplicationContext(), DashboardWisatawanActivity.class);
            startActivity(x);
        });


//        sessionManager = new SessionManager(getApplicationContext());
        String key_name_a = sessionManager.getUserDetail().get(SessionManager.key_name);
        String key_email_a = sessionManager.getUserDetail().get(SessionManager.key_email);
        String is_login_a = sessionManager.getUserDetail().get(SessionManager.is_login);
        String is_flag_a = sessionManager.getUserDetail().get(SessionManager.key_flag);

        _textview_email_session.setText(sessionManager.getUserDetail().get(SessionManager.key_email) );

//        Log.i("triono ","key_name_a " + key_name_a);

        informasiStatusKarcis("informasi_status_karcis");




    }


    private void informasiStatusKarcis(String EP){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://"+ Help.domain_api() +"/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(DashboardWisatawanOLdActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                response -> {
                    Log.i("triono", "response informasiStatusKarcis= " + response );
                    try {
                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        if( Help.isJSONValid(response) ){
                            ArrayList dx = new ArrayList<>();
                            JSONObject jsonObject = new JSONObject(response.toString());
                            if( jsonObject.getBoolean("success") ) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                String _va_no;
                                String _tgl_kunjungan;
                                String _status;
                                String _nama;
                                entityStatusKarcisArrayList = new ArrayList<>();

                                Log.i("","jsonArray.length()= "+jsonArray.length());
                                TextView dtSts = (TextView) findViewById(R.id.text_sts_wist);
                                if( jsonArray.length() > 0 ) {
                                    dtSts.setText(String.valueOf("Data ditemukan: "+ jsonArray.length()));
                                    dtSts.setTextColor(Color.parseColor("#ffe458"));
                                } else{
                                    dtSts.setText(String.valueOf("Data tidak ditemukan"));
                                    dtSts.setTextColor(Color.parseColor("#ffe458"));
                                }

                                for (int i = 0; i <jsonArray.length();i++ ) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    _va_no = jsonObject1.getString("va_no");
                                    _tgl_kunjungan = jsonObject1.getString("tgl_kunjungan");
                                    _status = jsonObject1.getString("status");
                                    _nama = jsonObject1.getString("nama");

                                    Log.i("","i "+ i);

                                    Log.i("","_va_no "+_va_no);
                                    Log.i("","_nama "+ _nama);
                                    Log.i("","_tgl_kunjungan "+ _tgl_kunjungan);

//                                        entityStatusKarcisArrayList.add(new EntityStatusKarcisWisatawan(_va_no,_tgl_kunjungan,_status,_nama));
                                }
//                                    CustomAdapterEntityWisatawan customAdapter = new CustomAdapterEntityWisatawan( entityStatusKarcisArrayList, DashboardWisatawanOLdActivity.this);
//                                    recyclerView.setAdapter(customAdapter);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    requestQueue.stop();
                }, (Response.ErrorListener) error -> {
                    Log.i("", "response =" + error.toString());
                    error.printStackTrace();
                    requestQueue.stop();

                    findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                    AlertDialog.Builder builder = new AlertDialog.Builder(DashboardWisatawanOLdActivity.this);
                    builder.setMessage("Terjadi Gangguan, Refresh ")
                            .setCancelable(false)
                            .setPositiveButton("Ya", (dialog, id) -> {
                                informasiStatusKarcis("informasi_status_karcis");
                            })
                            .setNegativeButton("Tidak", (dialog, id) -> dialog.cancel());
                    AlertDialog alert = builder.create();
                    alert.show();

                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> obj = new HashMap<String, String>();
                final String _email =  sessionManager.getUserDetail().get(SessionManager.key_email);
                obj.put("alamat_email", _email);
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }
}
