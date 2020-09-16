package com.amanahgithawisata.aga;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amanahgithawisata.aga.Adapter.SessionManager;
import com.amanahgithawisata.aga.Helper.Help;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NotifSuksesActivity extends AppCompatActivity {

    TextView textview_nmr_va;
    TextView textview_nm_ket_kelompok;
    TextView textview_no_hp;
    TextView textview_email;
    TextView textview_jml_psrt_wisnu;
    TextView textview_jml_psrt_wisman;
    TextView textview_jml_psrt;
    TextView textview_jml_krcs_tmbhn;
    TextView textview_tgl_beli;
    TextView textview_tgl_kunj;
    TextView textview_tgl_plg;
    TextView textview_tgl_red;
    TextView textview_va_red;
    TextView textview_nm_red;
    TextView textview_bank_red;
    TextView textview_grand_ttl_red;
    TextView textview_jam_red;
    TextView textview_nm_lok_wis;


    Button btn_continue;
    ImageView btn_back_regis;
    SessionManager sessionManager;
    LinearLayout linearMohonWisatawan;
    LinearLayout linearMohonPetugas;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif_sukses);
        sessionManager = new SessionManager(getApplicationContext());
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);


//        String _key_kode_ksda = getIntent().getStringExtra("key_kode_ksda");
//        String _key_name = getIntent().getStringExtra("key_name");
//        String _key_email = getIntent().getStringExtra("key_email");
//        String _key_hp = getIntent().getStringExtra("key_hp");
//        String _key_tgl_penjualan = getIntent().getStringExtra("key_tgl_penjualan");
//        String _tgl_kunjungan = getIntent().getStringExtra("tgl_kunjungan");
//        String _key_kode_lokasi = getIntent().getStringExtra("key_kode_lokasi");
//        String _key_id_utama = getIntent().getStringExtra("key_id_utama");
//        String _key_id_tmbhn = getIntent().getStringExtra("key_id_tmbhn");
//        String _jml_wisnu = getIntent().getStringExtra("jml_wisnu");
//        String _jml_wisman = getIntent().getStringExtra("jml_wisman");
//        String _jml_tmbhn = getIntent().getStringExtra("jml_tmbhn");
//        String _key_kode_lok_new = sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi);
//        String _grand_ttl = getIntent().getStringExtra("grand_ttl");
//

        String result_dt_ket = getIntent().getStringExtra("result_dt_ket");
        String _id = getIntent().getStringExtra("_id");
        String _va_no = getIntent().getStringExtra("_va_no");
        String _va_no_berlaku_sd = getIntent().getStringExtra("_va_no_berlaku_sd");
        String _vnama = getIntent().getStringExtra("_vnama");
        String _alamat_email = getIntent().getStringExtra("_alamat_email");
        String _sellular_no = getIntent().getStringExtra("_sellular_no");
        String _jumlah_wisnu = getIntent().getStringExtra("_jumlah_wisnu");
        String _jumlah_wisman = getIntent().getStringExtra("_jumlah_wisman");
        String _jumlah_karcis = getIntent().getStringExtra("_jumlah_karcis");
        String _tgl_penjualan = getIntent().getStringExtra("_tgl_penjualan");
        String _tgl_kunjungan = getIntent().getStringExtra("_tgl_kunjungan");
        String _menit_valid = getIntent().getStringExtra("_menit_valid");
        String _tgl_valid = getIntent().getStringExtra("_tgl_valid");
        String _tagihan_total = getIntent().getStringExtra("_tagihan_total");
        String berhasil = getIntent().getStringExtra("berhasil");
        String result_dt_flag = getIntent().getStringExtra("result_dt_flag");
        String _jumlah_tambahan = getIntent().getStringExtra("_jumlah_tambahan");
        String _nama_lokasi = getIntent().getStringExtra("_nama_lokasi");


         textview_nmr_va = (TextView) findViewById(R.id.textview_nmr_va);
         textview_nm_ket_kelompok = findViewById(R.id.textview_nm_ket_kelompok);
         textview_no_hp = findViewById(R.id.textview_no_hp);
         textview_email = findViewById(R.id.textview_email);
         textview_jml_psrt_wisnu = findViewById(R.id.textview_jml_psrt_wisnu);
         textview_jml_psrt_wisman = findViewById(R.id.textview_jml_psrt_wisman);
         textview_jml_psrt = findViewById(R.id.textview_jml_psrt);
         textview_jml_krcs_tmbhn = findViewById(R.id.textview_jml_krcs_tmbhn);
         textview_tgl_beli = findViewById(R.id.textview_tgl_beli);
         textview_tgl_kunj = findViewById(R.id.textview_tgl_kunj);
         textview_tgl_plg = findViewById(R.id.textview_tgl_plg);
         btn_continue = findViewById(R.id.btn_continue);
         btn_back_regis =  findViewById(R.id.btn_back_regis);;

         textview_tgl_red =  findViewById(R.id.textview_tgl_red);
         textview_va_red =  findViewById(R.id.textview_va_red);
         textview_nm_red =  findViewById(R.id.textview_nm_red);
         textview_bank_red =  findViewById(R.id.textview_bank_red);
         textview_grand_ttl_red =  findViewById(R.id.textview_grand_ttl_red);
         textview_jam_red =  findViewById(R.id.textview_jam_red);
         textview_nm_lok_wis =  findViewById(R.id.textview_nm_lok_wis);
         linearMohonWisatawan = findViewById(R.id.linearMohonWisatawan);
         linearMohonPetugas = findViewById(R.id.linearMohonPetugas);

//        final View vButton = findViewById(R.id.button1);
        if(result_dt_flag.equals("flagPesanKarcisWisatawan")){
            btn_continue.setText("cek tagihan di email anda");
            linearMohonWisatawan.setVisibility(View.VISIBLE);
            linearMohonPetugas.setVisibility(View.GONE);

        }else {
            btn_continue.setText("Lanjutkan");
            linearMohonWisatawan.setVisibility(View.GONE);
            linearMohonPetugas.setVisibility(View.VISIBLE);
        }

        textview_nmr_va.setText( _va_no );
        textview_nm_ket_kelompok.setText(_vnama);
        textview_no_hp.setText(_sellular_no);
        textview_email.setText(_alamat_email);
        textview_jml_psrt_wisnu.setText(_jumlah_wisnu);
        textview_jml_psrt_wisman.setText(_jumlah_wisman);
        textview_jam_red.setText(_menit_valid);
        textview_jml_krcs_tmbhn.setText( _jumlah_tambahan );
        textview_nm_lok_wis.setText(_nama_lokasi);

        String  new_key_tgl_penjualan ;
        String  new_tgl_kunjungan ;

        if( _tgl_penjualan == null ){
            new_key_tgl_penjualan  = "";
        } else {
            new_key_tgl_penjualan  = Help.formatTgl(_tgl_penjualan) ;
        }
        textview_tgl_beli.setText( new_key_tgl_penjualan );


        if( _tgl_kunjungan == null ){
            new_tgl_kunjungan  = "";
        } else {
            new_tgl_kunjungan  = Help.formatTgl(_tgl_kunjungan) ;
        }
        textview_tgl_kunj.setText(new_tgl_kunjungan );


        textview_tgl_red.setText(_tgl_valid);
        textview_va_red.setText(_va_no);
        textview_nm_red.setText(_vnama);
        textview_bank_red.setText("PT Bank BRIsyariah Tbk ");
        textview_grand_ttl_red.setText(_tagihan_total);





        btn_continue.setOnClickListener(v -> {
            findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
            Intent a = new Intent(getApplicationContext(), DashboardWisatawanActivity.class);
            a.putExtra("result_flag_notif", "sukses");
            startActivity(a);


            /*
            inputKarcisWisatawan(
                    "input_wisatawan",
                    _key_kode_ksda,
                    _key_name,
                    _key_email,
                    _key_hp,
                    new_key_tgl_penjualan,
                    new_tgl_kunjungan,
                    _key_kode_lokasi,
                    _key_id_utama,
                    _key_id_tmbhn,
                    _jml_wisnu,
                    _jml_wisman,
                    _jml_tmbhn,
                    _key_kode_lok_new
            );
            */
        });

        btn_back_regis.setOnClickListener(v -> {
                super.onBackPressed();
        });

    }



        private void inputKarcisWisatawan(
                String EP,
                String key_kode_ksda,
                String key_name,
                String key_email,
                String key_hp,
                String key_tgl_penjualan,
                String tgl_kunjungan,
                String key_kode_lokasi,
                String key_id_utama,
                String key_id_tmbhn,
                String jml_wisnu,
                String jml_wisman,
                String jml_tmbhn,
                String key_kode_lok_new
        ) {

        String server_url = "http://kaffah.amanahgitha.com/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue( getApplicationContext() );
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("tag","response= " + response );
                        try {

                            findViewById(R.id.loadingPanel).setVisibility(View.GONE);

                            if( Help.isJSONValid(response) ){

                                JSONObject jObj = new JSONObject(response);
                                String data = jObj.getString("data");
                                JSONObject jsonObject1 = new JSONObject(data);
                                Boolean berhasil = jsonObject1.getBoolean("berhasil");

                                Log.i("tag","success= " + jObj.getBoolean("success") );
                                Log.i("tag","data= " + data );
                                Log.i("tag","berhasil= " + berhasil );

                                if( berhasil ) {
                                    String _id = jsonObject1.getString("id");
                                    String _va_no = jsonObject1.getString("va_no");
                                    String _va_no_berlaku_sd = jsonObject1.getString("va_no_berlaku_sd");
                                    String _vnama = jsonObject1.getString("nama");
                                    String _alamat_email = jsonObject1.getString("alamat_email");
                                    String _sellular_no = jsonObject1.getString("sellular_no");
                                    String _jumlah_wisnu = jsonObject1.getString("jumlah_wisnu");
                                    String _jumlah_wisman = jsonObject1.getString("jumlah_wisman");
                                    String _jumlah_karcis = jsonObject1.getString("jumlah_karcis");
                                    String _tgl_penjualan = jsonObject1.getString("tgl_penjualan");
                                    String _tgl_kunjungan = jsonObject1.getString("tgl_kunjungan");
                                    String _menit_valid = jsonObject1.getString("menit_valid");
                                    String _tgl_valid = jsonObject1.getString("tgl_valid");
                                    String _tagihan_total = jsonObject1.getString("tagihan_total");

                                    Intent i = new Intent(getApplicationContext(), SuccessRegistrasiWisatawanActivity.class);
                                    i.putExtra("result_dt_ket", "Pemesanan Anda Berhasil Silahkan Cek email!");
                                    i.putExtra("result_dt_email", "");
                                    i.putExtra("result_dt_berhasil", berhasil);
                                    i.putExtra("result_dt_flag", "flagPesanKarcisWisatawan");
                                    startActivity(i);
                                }
                            }
                        } catch (JSONException e) {
                            Log.i("", "error pesan karcis wisatawan" + e.toString() );
                            e.printStackTrace();
                        }
                        requestQueue.stop();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("tag", "response =" + error.toString());
                error.printStackTrace();
                requestQueue.stop();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> obj = new HashMap<String, String>();

                String flag_pemesan = "";
//                String key_kode_ksda = getIntent().getStringExtra("key_kode_ksda");
//                String key_name = getIntent().getStringExtra("key_name");
//                String key_email = getIntent().getStringExtra("key_email");
//                String key_hp = getIntent().getStringExtra("key_hp");
//                String key_tgl_penjualan = getIntent().getStringExtra("key_tgl_penjualan");
//                String tgl_kunjungan = getIntent().getStringExtra("tgl_kunjungan");
//                String key_kode_lokasi = getIntent().getStringExtra("key_kode_lokasi");
//                String key_id_utama = getIntent().getStringExtra("key_id_utama");
//                String key_id_tmbhn = getIntent().getStringExtra("key_id_tmbhn");
//                String jml_wisnu = getIntent().getStringExtra("jml_wisnu");
//                String jml_wisman = getIntent().getStringExtra("jml_wisman");
//                String jml_tmbhn = getIntent().getStringExtra("jml_tmbhn");
//                String key_kode_lok_new = sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi);

                Log.e("tag","SessionManager.key_kode_lokasi= "+sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi));
                Log.e("","key_kode_lok_new= "+key_kode_lok_new);

                if ( key_kode_lok_new != null ){
                    flag_pemesan = "1";
                } else {
                    flag_pemesan= "2";
                }

                Log.e("","flag_pemesan ok= "+flag_pemesan);



                Log.e("tag","key_kode_lok_new = " + key_kode_lok_new );
                Log.i("tag","jml_wisnu= " + jml_wisnu );
                Log.i("tag","jml_wisman= " + jml_wisman );
                Log.i("tag","jml_tmbhn= " + jml_tmbhn );

                String jml_wisnu_n;
                String jml_wisman_n;
                String jml_tmbhn_n;

                if(jml_wisnu.equals("")){
                    jml_wisnu_n = "0";
                }else {
                    jml_wisnu_n = jml_wisnu;
                }
                if(jml_wisman.equals("")){
                    jml_wisman_n = "0";
                }else{
                    jml_wisman_n = jml_wisman;
                }
                if(jml_tmbhn.equals("")){
                    jml_tmbhn_n = "0";
                }else{
                    jml_tmbhn_n = jml_tmbhn;
                }


//                Log.i("tag","key_kode_ksda= " + key_kode_ksda );
                Log.i("tag","key_kode_lok_new= " + key_kode_lok_new );
                Log.i("tag","key_name= " + key_name );
                Log.i("tag","key_email= " + key_email );
                Log.i("tag","key_hp= " + key_hp );
                Log.i("tag","key_tgl_penjualan= " + key_tgl_penjualan );
                Log.i("tag","tgl_kunjungan= " + tgl_kunjungan );
                Log.i("tag","key_kode_lokasi= " + key_kode_lokasi );
                Log.i("tag","key_id_utama= " + key_id_utama );
                Log.i("tag","key_id_tmbhn= " + key_id_tmbhn );
                Log.i("tag","jml_wisnu= " + jml_wisnu_n );
                Log.i("tag","jml_wisman= " + jml_wisman_n );
                Log.i("tag","jml_tmbhn= " + jml_tmbhn_n );
                Log.i("tag","flag_pemesan= " + flag_pemesan );


//                obj.put("registration_by","triono.triono1@gmail.com");
//                obj.put("flag_pemesan","1");
//                obj.put("nama","triono");
//                obj.put("sellular_no","081908130663");
//                obj.put("alamat_email","triono.triono1@gmail.com");
//                obj.put("tgl_penjualan","2020-01-1");
//                obj.put("tgl_kunjungan","2020-01-02");
//                obj.put("kode_lokasi","27001");
//                obj.put("id_karcis_utama","2d3154acf88e48ef92058a35a248e503");
//                obj.put("id_karcis_tambahan","852eb90a8f0442de8b63a09175cb9003");
//                obj.put("jumlah_wisnu","1");
//                obj.put("jumlah_wisman","1");
//                obj.put("jumlah_tambahan","1");


                obj.put("registration_by", key_email);
                obj.put("flag_pemesan",flag_pemesan);
                obj.put("nama",key_name);
                obj.put("sellular_no",key_hp);
                obj.put("alamat_email",key_email);
                obj.put("tgl_penjualan",key_tgl_penjualan);
                obj.put("tgl_kunjungan",tgl_kunjungan);
                obj.put("kode_lokasi",key_kode_lokasi);
                obj.put("id_karcis_utama",key_id_utama);
                obj.put("id_karcis_tambahan",key_id_tmbhn);
                obj.put("jumlah_wisnu",jml_wisnu_n);
                obj.put("jumlah_wisman",jml_wisman_n);
                obj.put("jumlah_tambahan",jml_tmbhn_n);
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

}