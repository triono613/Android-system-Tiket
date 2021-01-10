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
    TextView textview_tgl_kunj_sd;

    TextView textview_mode_pembayaran;
    TextView textview_nama_pengunjung;
    TextView textview_no_hp_pengunjung;
    TextView textview_email_pengunjung;
    TextView textview_jml_hari;

    Button btn_continue;
    ImageView btn_back_regis;
    SessionManager sessionManager;
    LinearLayout linearMohonWisatawan;
    LinearLayout linearMohonPetugas;
    LinearLayout linear_hp_red;
    LinearLayout linear_email_red;
    LinearLayout linear_bukti_bayar_red;
    LinearLayout linear_nm_bank_red;
    LinearLayout linear_red_nominal_red;
    LinearLayout linear_red_cash_ptgs_red;
    LinearLayout linear_va_red;
    LinearLayout linear_cash_red;
    LinearLayout linear_cash_bottom_red;
    LinearLayout linear_va_bottom_red;
    Button btnPairUnpair, btnPrint;



@Override
    public void onBackPressed() {
    performOperation();
}

    private void performOperation(  ) {
        String result_dt_flag = getIntent().getStringExtra("result_dt_flag");

        Intent intent;
        if ( result_dt_flag.equals("flagPesanKarcisWisatawan") ){
            intent = new Intent(NotifSuksesActivity.this, DashboardWisatawanActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        else if ( result_dt_flag.equals("detailTagihan") ){
            intent = new Intent(NotifSuksesActivity.this, DashboardWisatawanActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        else  {
            intent = new Intent(NotifSuksesActivity.this, DashboardPetugasActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        finish();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif_sukses);
        sessionManager = new SessionManager(getApplicationContext());
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);



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
        String _berhasil = getIntent().getStringExtra("berhasil");
        String result_dt_flag = getIntent().getStringExtra("result_dt_flag");
        String _jumlah_tambahan = getIntent().getStringExtra("_jumlah_tambahan");
        String _txt_nmlokwis = getIntent().getStringExtra("_txt_nmlokwis");

        String _mode_pembayaran = getIntent().getStringExtra("_mode_pembayaran");
        String _nama_pembayaran = getIntent().getStringExtra("_nama_pembayaran");
        String _nama_pengunjung = getIntent().getStringExtra("_nama_pengunjung");
        String _no_hp_pengunjung = getIntent().getStringExtra("_no_hp_pengunjung");
        String _email_pengunjung = getIntent().getStringExtra("_email_pengunjung");


        String _tgl_kunj_sd = getIntent().getStringExtra("_tgl_kunjungan_sd");
        String _selisih_hari = getIntent().getStringExtra("_selisih_hari");
        String _flag_print = getIntent().getStringExtra("flag_print");

        Log.i("","_selisih_hari notif"+_selisih_hari);


         textview_nmr_va =  findViewById(R.id.textview_nmr_va);
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
         textview_tgl_kunj_sd =  findViewById(R.id.textview_tgl_kunj_sd);
         textview_jml_hari =  findViewById(R.id.textview_jml_hari);
        textview_no_hp_pengunjung = findViewById(R.id.textview_hp_red);
        textview_email_pengunjung = findViewById(R.id.textview_email_red);
        linearMohonWisatawan = findViewById(R.id.linearMohonWisatawan);
        linearMohonPetugas = findViewById(R.id.linearMohonPetugas);
        linear_email_red = findViewById(R.id.linear_email_red);
        linear_hp_red = findViewById(R.id.linear_hp_red);
        linear_bukti_bayar_red = findViewById(R.id.linear_bukti_bayar_red);
        linear_nm_bank_red = findViewById(R.id.linear_nm_bank_red);
        linear_red_nominal_red = findViewById(R.id.linear_red_nominal_red);
        linear_red_cash_ptgs_red = findViewById(R.id.linear_red_cash_ptgs_red);
        linear_va_red = findViewById(R.id.linear_va_red);
        linear_cash_red = findViewById(R.id.linear_cash_red);
        linear_cash_bottom_red = findViewById(R.id.linear_cash_bottom_red);
        linear_va_bottom_red = findViewById(R.id.linear_va_bottom_red);

        btnPairUnpair = findViewById(R.id.btnPairUnpair);
        btnPrint = findViewById(R.id.btnPrint);


        Log.i("","result_dt_flag "+result_dt_flag);
        Log.i("","_email_pengunjung "+_email_pengunjung);
        Log.i("","_no_hp_pengunjung "+_no_hp_pengunjung);
        Log.i("","_mode_pembayaran "+_mode_pembayaran);
        Log.i("","_nama_pembayaran "+_nama_pembayaran);


        linearMohonPetugas.setVisibility(View.GONE);
        linearMohonWisatawan.setVisibility(View.GONE);
        linear_hp_red.setVisibility(View.GONE);
        linear_email_red.setVisibility(View.GONE);
        linear_nm_bank_red.setVisibility(View.GONE);
        linear_bukti_bayar_red.setVisibility(View.GONE);
        linear_red_nominal_red.setVisibility(View.GONE);
        linear_red_cash_ptgs_red.setVisibility(View.GONE);
        linear_va_red.setVisibility(View.GONE);
        linear_cash_red.setVisibility(View.GONE);
        linear_va_bottom_red.setVisibility(View.GONE);
        linear_cash_bottom_red.setVisibility(View.GONE);
        btnPrint.setVisibility(View.GONE);
        btnPairUnpair.setVisibility(View.GONE);

        textview_no_hp_pengunjung.setText(_no_hp_pengunjung);
        textview_email_pengunjung.setText(_email_pengunjung);
        textview_tgl_kunj_sd.setText(_tgl_kunj_sd);
        textview_jml_hari.setText(_selisih_hari);

//        i.putExtra("_tgl_kunjungan_sd", tgl_kunjungan_sd);
//        i.putExtra("_selisih_hari", selisih_hari);

//        Intent i = new Intent(getApplicationContext(), DashboardPrintActivity.class);
//        startActivity(i);

        if(result_dt_flag.equals("flagPesanKarcisWisatawan")) {
            Log.i("", "kesini flagPesanKarcisWisatawan " + result_dt_flag);
            btn_continue.setText("Cek Tagihan di Email Anda");
            linearMohonWisatawan.setVisibility(View.VISIBLE);
            linearMohonPetugas.setVisibility(View.GONE);
            linear_hp_red.setVisibility(View.GONE);
            linear_email_red.setVisibility(View.GONE);
            linear_nm_bank_red.setVisibility(View.VISIBLE);
            linear_va_bottom_red.setVisibility(View.VISIBLE);
            textview_bank_red.setText(_nama_pembayaran);

        }
         else if(result_dt_flag.equals("detailTagihan")){
                Log.i("","kesini detailTagihan "+result_dt_flag);
                btn_continue.setText("Cek Tagihan di Email Anda");
                linearMohonWisatawan.setVisibility(View.VISIBLE);
                linearMohonPetugas.setVisibility(View.GONE);
                linear_hp_red.setVisibility(View.GONE);
                linear_email_red.setVisibility(View.GONE);
                linear_nm_bank_red.setVisibility(View.VISIBLE);
                linear_va_bottom_red.setVisibility(View.VISIBLE);
                textview_bank_red.setText(_nama_pembayaran);

        }else if ( result_dt_flag.equals("flagPesanKarcisPetugas") ) {
            Log.i("","kesini flagPesanKarcisPetugas "+result_dt_flag);
            btn_continue.setText("Cek Tagihan di Email Anda");
            linearMohonWisatawan.setVisibility(View.VISIBLE);
            linearMohonPetugas.setVisibility(View.GONE);
            linear_hp_red.setVisibility(View.VISIBLE);
            linear_email_red.setVisibility(View.VISIBLE);
            linear_nm_bank_red.setVisibility(View.VISIBLE);

            linear_va_red.setVisibility(View.GONE);
            linear_cash_red.setVisibility(View.VISIBLE);
            textview_bank_red.setText(_nama_pembayaran);

            // 1. Cash
            // 2. Virtual Account

            if(_mode_pembayaran != null && _mode_pembayaran.equals("1")) {
                linear_red_nominal_red.setVisibility(View.GONE);
                linear_red_cash_ptgs_red.setVisibility(View.VISIBLE);
                linear_va_red.setVisibility(View.GONE);
                linear_cash_red.setVisibility(View.VISIBLE);
                linear_va_bottom_red.setVisibility(View.GONE);
                linear_cash_bottom_red.setVisibility(View.VISIBLE);

            }
            if(_mode_pembayaran != null &&  _mode_pembayaran.equals("2")){
                linear_red_nominal_red.setVisibility(View.VISIBLE);
                linear_red_cash_ptgs_red.setVisibility(View.GONE);
                linear_va_red.setVisibility(View.VISIBLE);
                linear_cash_red.setVisibility(View.GONE);
                linear_va_bottom_red.setVisibility(View.VISIBLE);
                linear_cash_bottom_red.setVisibility(View.GONE);

            }
            assert _flag_print != null;
            if( _flag_print.equals("1")){
                btnPrint.setVisibility(View.VISIBLE);
                btnPairUnpair.setVisibility(View.VISIBLE);
            }
        }

        textview_nmr_va.setText( _va_no );
        textview_nm_ket_kelompok.setText(_vnama);
        textview_no_hp.setText(_sellular_no);
        textview_email.setText(_alamat_email);
        textview_jml_psrt_wisnu.setText(_jumlah_wisnu);
        textview_jml_psrt_wisman.setText(_jumlah_wisman);
        textview_jam_red.setText(_menit_valid);
        textview_jml_krcs_tmbhn.setText( _jumlah_tambahan );
        textview_nm_lok_wis.setText(_txt_nmlokwis);

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
//        textview_bank_red.setText("PT Bank BRIsyariah Tbk ");
        textview_grand_ttl_red.setText(_tagihan_total);





        btn_continue.setOnClickListener(v -> {
            findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);

            Log.i("","result_dt_flag xxx"+result_dt_flag);

            Intent a;
            switch (result_dt_flag) {
                case "flagPesanKarcisWisatawan":
                case "detailTagihan":
                    a = new Intent(getApplicationContext(), DashboardWisatawanActivity.class);
                    a.putExtra("result_flag_notif", "sukses");
                    startActivity(a);
                    break;
                case "flagPesanKarcisPetugas":
                    a = new Intent(getApplicationContext(), DashboardPetugasActivity.class);
                    a.putExtra("result_flag_notif", "sukses");
                    startActivity(a);
                    break;

            }





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

        String server_url = "http://"+ Help.domain_api() +"/~androidwisata/?data="+ EP;
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
