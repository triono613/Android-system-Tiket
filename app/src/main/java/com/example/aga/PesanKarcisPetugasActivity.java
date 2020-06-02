package com.example.aga;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.example.aga.Model.SpinnerJnsByr;
import com.example.aga.Model.SpinnerListWisata;
import com.example.aga.Model.SpinnerListWisataKsda;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class PesanKarcisPetugasActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    TextView _tgl_kunjungan_ptgs;
    TextView _jml_krcs_wisnu_ptgs;
    TextView _jml_krcs_wisman_ptgs;
    TextView _ttl_ptgs;
    TextView _jml_krcs_wisman_tmbhn_ptgs;
    TextView _ttl_tmbhn_ptgs;
    TextView _grand_ttl_ptgs;
    TextView _hp_pengunjung_ptgs;
    TextView _email_pengunjung;
    TextView _nama_pengunjung_ptgs;
    Spinner _spinner_karcis_utama_ptgs;
    Spinner _spinner_karcis_tmbhn_ptgs;
    Spinner _spinner_lok_pintu_ptgs;
    Spinner _spinner_jns_byr_ptgs;
    Button _btn_order_ptgs;

    private List<String> arrListUtamaPtgs = new ArrayList<String>();
    private List<String> arrListTambahanPtgs = new ArrayList<String>();
    ArrayList<SpinnerListWisata> arrListWisata = new ArrayList<SpinnerListWisata>();
    ArrayList<SpinnerListWisataKsda> arrListWisataKsda = new ArrayList<SpinnerListWisataKsda>();
    ArrayList<SpinnerJnsByr> arrJnsByr = new ArrayList<SpinnerJnsByr>();

    SessionManager sessionManager;

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        TextView _tgl_kunjungan_order = (TextView) findViewById(R.id.tgl_kunjungan_ptgs);

//        _txt_tgl_kunjungan_order.clearComposingText();

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        _tgl_kunjungan_ptgs.setText(sdf.format(c.getTime()));
        _spinner_lok_pintu_ptgs = (Spinner) findViewById(R.id.spinner_lok_pintu_ptgs);
        _spinner_karcis_utama_ptgs = (Spinner) findViewById(R.id.spinner_karcis_utama_ptgs);
        _spinner_karcis_tmbhn_ptgs  = (Spinner) findViewById(R.id.spinner_karcis_tmbhn_ptgs);;
        final String LP = sessionManager.getDataLokWisPesankarcisWisatawan().get(SessionManager.key_kd_lok);
        spinnerWisatawanUtama("daftar_karcis_wisatawan_utama",LP);
        spinnerWisatawanTambahan("daftar_karcis_wisatawan_tambahan",LP);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan_karcis_petugas);
        sessionManager = new SessionManager(getApplicationContext());
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);

        _tgl_kunjungan_ptgs = (TextView) findViewById(R.id.tgl_kunjungan_ptgs);
        _spinner_lok_pintu_ptgs = (Spinner) findViewById(R.id.spinner_lok_pintu_ptgs);
        _spinner_karcis_utama_ptgs = (Spinner) findViewById(R.id.spinner_karcis_utama_ptgs);
        _spinner_karcis_tmbhn_ptgs =(Spinner) findViewById(R.id.spinner_karcis_tmbhn_ptgs);
        _spinner_jns_byr_ptgs = (Spinner) findViewById(R.id.spinner_jns_byr_ptgs);
        _btn_order_ptgs = (Button) findViewById(R.id.btn_order_ptgs);
        _jml_krcs_wisnu_ptgs = (EditText) findViewById(R.id.jml_krcs_wisnu_ptgs);
        _jml_krcs_wisman_ptgs = (EditText) findViewById(R.id.jml_krcs_wisman_ptgs);
        _jml_krcs_wisman_tmbhn_ptgs = (EditText) findViewById(R.id.jml_krcs_wisman_tmbhn_ptgs);
        _ttl_ptgs = (EditText) findViewById(R.id.ttl_ptgs);
        _ttl_tmbhn_ptgs = (EditText) findViewById(R.id.ttl_tmbhn_ptgs);
        _grand_ttl_ptgs = (EditText) findViewById(R.id.grand_ttl_ptgs);
        _btn_order_ptgs = (Button) findViewById(R.id.btn_order_ptgs);
        _hp_pengunjung_ptgs = (TextView) findViewById(R.id.hp_pengunjung_ptgs);
        _email_pengunjung = (TextView) findViewById(R.id.email_pengunjung_ptgs);
        _nama_pengunjung_ptgs = (TextView) findViewById(R.id.nama_pengunjung_ptgs);

//        spinnerLokPintuPtgs("daftar_lokasi_wisata","");

        final String KL = sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi);

//        spinnerLokPintuPtgs("daftar_lokasi_pintu", KL);

        spinnerLokPintuPtgs("petugas_daftar_lokasi_wisata", KL);
        spinnerJnsByrPtgs("informasi_mode_pembayaran","");
        _tgl_kunjungan_ptgs.setText(Help.getDateTime());
        _tgl_kunjungan_ptgs.setEnabled(false);
        _ttl_ptgs.setEnabled(false);
        _ttl_tmbhn_ptgs.setEnabled(false);
        _grand_ttl_ptgs.setEnabled(false);
        _spinner_lok_pintu_ptgs.setEnabled(false);



        _spinner_lok_pintu_ptgs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerListWisata item = (SpinnerListWisata) parent.getItemAtPosition(position);
                sessionManager = new SessionManager(getApplicationContext());
                sessionManager.createSessionLokWisPesankarcisWisatawan(item.getKdlok(),item.getNmlok());

                spinnerWisatawanUtama("daftar_karcis_wisatawan_utama",item.getKdlok());
                spinnerWisatawanTambahan("daftar_karcis_wisatawan_tambahan",item.getKdlok());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        _spinner_jns_byr_ptgs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerJnsByr item = (SpinnerJnsByr) parent.getItemAtPosition(position);
                sessionManager = new SessionManager(getApplicationContext());
                sessionManager.createSessionJnsByr(item.getMode_pembayaran(),item.getNama_pembayaraan());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        _btn_order_ptgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pesanKarcisPetugas("input_petugas");
            }
        });

        _jml_krcs_wisman_ptgs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                CalculateKarcis();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        _jml_krcs_wisman_tmbhn_ptgs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                CalculateKarcis();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {  CalculateKarcis();  }
        });




    }


    public void CalculateKarcis(){
        final double _jml_krcs_wisnu = Help.ParseDouble(((EditText) findViewById(R.id.jml_krcs_wisnu_ptgs)).getText().toString());
        final double _jml_krcs_wisman = Help.ParseDouble(((EditText) findViewById(R.id.jml_krcs_wisman_ptgs)).getText().toString());
        final double _jml_krcs_tmbhn = Help.ParseDouble(((EditText) findViewById(R.id.jml_krcs_wisman_tmbhn_ptgs)).getText().toString());


        final double hrg_krcs_wisnu = Help.ParseDouble(Objects.requireNonNull(sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_harga_karcis_wisata_wisnu)));
        final double hrg_krcs_wisman = Help.ParseDouble(Objects.requireNonNull(sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_harga_karcis_wisata_wisman)));
        final double hrg_krcs_tmbhn = Help.ParseDouble(Objects.requireNonNull(sessionManager.getDaftarKarcisWisatawanTmbhn().get(SessionManager.key_harga_karcis_wisata_tmbhn)));
        final double hrg_krcs_asrnsi_wisnu = Help.ParseDouble(Objects.requireNonNull(sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_harga_karcis_asuransi_wisnu)));
        final double hrg_krcs_asrnsi_wisman = Help.ParseDouble(Objects.requireNonNull(sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_harga_karcis_asuransi_wisman)));


        Log.i("tag","_jml_krcs_wisnu= "+ _jml_krcs_wisnu);
        Log.i("tag","_jml_krcs_wisman= "+ _jml_krcs_wisman);
        Log.i("tag","_jml_krcs_tmbhn= "+ _jml_krcs_tmbhn);

        Log.i("tag","hrg_krcs_wisnu= "+ hrg_krcs_wisnu);
        Log.i("tag","hrg_krcs_wisman= "+ hrg_krcs_wisman);
        Log.i("tag","hrg_krcs_tmbhn= "+ hrg_krcs_tmbhn);
        Log.i("tag","hrg_krcs_asrnsi_wisnu= "+ hrg_krcs_asrnsi_wisnu);
        Log.i("tag","hrg_krcs_asrnsi_wisman= "+ hrg_krcs_asrnsi_wisman);

        final int  ttl_wisnu = (int) ((hrg_krcs_wisnu+hrg_krcs_asrnsi_wisnu)*_jml_krcs_wisnu);
        final int   ttl_wisman =(int) ((hrg_krcs_wisman+hrg_krcs_asrnsi_wisman)*_jml_krcs_wisman);
        final int ttl_wisnu_wisman = (ttl_wisnu+ttl_wisman);
        final int ttl_tmbhn = (int) (hrg_krcs_tmbhn*_jml_krcs_tmbhn);
        final int grand_ttl =(int) (ttl_wisnu_wisman+ttl_tmbhn);

        _ttl_ptgs.setText(String.valueOf(ttl_wisnu_wisman));
        _ttl_tmbhn_ptgs.setText(String.valueOf(ttl_tmbhn));
        _grand_ttl_ptgs.setText(String.valueOf(grand_ttl));

        Log.i("tag","ttl_wisnu= "+ ttl_wisnu);
        Log.i("tag","ttl_wisman= "+ ttl_wisman);
        Log.i("tag","ttl_wisnu_wisman= "+ttl_wisnu_wisman);
        Log.i("tag","ttl_tmbhn = "+ttl_tmbhn);
        Log.i("tag","grand_ttl = "+grand_ttl);
    }


    private void spinnerJnsByrPtgs(String EP,String KL){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://kaffah.amanahgitha.com/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(PesanKarcisPetugasActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("tag", "response spinnerLokPintuPtgs =" + response );
                        try {
                            findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                            if( Help.isJSONValid(response) ){
                                JSONObject jsonObject = new JSONObject(response);

                                String mode_pembayaran;
                                String nama_pembayaran;
                                arrJnsByr.clear();

                                if( jsonObject.getBoolean("success") ) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                                    for (int i = 0; i <jsonArray.length();i++ ) {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                        mode_pembayaran = jsonObject1.getString("mode_pembayaran");
                                        nama_pembayaran = jsonObject1.getString("nama_pembayaran");

                                        Log.i("","mode_pembayaran= "+mode_pembayaran);
                                        Log.i("","nama_pembayaran= "+nama_pembayaran);

                                        arrJnsByr.add(new SpinnerJnsByr(mode_pembayaran,nama_pembayaran));
                                    }
                                } else {

                                }
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(PesanKarcisPetugasActivity.this);
                                builder.setMessage("Format Json Error !")
                                        .setCancelable(false)
                                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                sessionManager.logout();
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                            _spinner_jns_byr_ptgs.setAdapter(new ArrayAdapter<SpinnerJnsByr>(PesanKarcisPetugasActivity.this, android.R.layout.simple_spinner_dropdown_item, arrJnsByr) );
                        } catch (JSONException e) {
                            Log.i("triono", "error ===" + e.toString() );
                            e.printStackTrace();
                        }
                        requestQueue.stop();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("triono", "response =" + error.toString());
                error.printStackTrace();
                requestQueue.stop();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> obj = new HashMap<String, String>();
                obj.put("kode_ksda", "27");
                return obj;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }



    private void spinnerLokPintuPtgs(String EP,String KL){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://kaffah.amanahgitha.com/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(PesanKarcisPetugasActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("tag", "response spinnerLokPintuPtgs =" + response );
                        try {

                            if( Help.isJSONValid(response) ){
                                JSONObject jsonObject = new JSONObject(response);

                                Log.i("","spinner pintu response= "+response);

                                String lokasi_pintu;
                                String nama_pintu;
                                arrListWisata.clear();

                                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                                if( jsonObject.getBoolean("success") ) {

                                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                                    for (int i = 0; i <jsonArray.length();i++ ) {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                        lokasi_pintu = jsonObject1.getString("lokasi_pintu");
                                        nama_pintu = jsonObject1.getString("nama_pintu");

                                        Log.i("tag","kode_ksda= "+lokasi_pintu);
                                        Log.i("tag","nama= "+nama_pintu);

                                        arrListWisata.add(new SpinnerListWisata(lokasi_pintu,nama_pintu));

                                    }
                                } else {

                                }
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(PesanKarcisPetugasActivity.this);
                                builder.setMessage("Format Json Error !")
                                        .setCancelable(false)
                                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
//                                                sessionManager.logout();
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                            _spinner_lok_pintu_ptgs.setAdapter(new ArrayAdapter<SpinnerListWisata>(PesanKarcisPetugasActivity.this, android.R.layout.simple_spinner_dropdown_item,arrListWisata) );
                            String compareValue = sessionManager.getDataSetupPintu().get(SessionManager.key_index);
                            Log.i("","compareValue "+compareValue);
                        } catch (JSONException e) {
                            Log.i("triono", "error ===" + e.toString() );
                            e.printStackTrace();
                        }
                        requestQueue.stop();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("triono", "response =" + error.toString());
                error.printStackTrace();
                requestQueue.stop();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> obj = new HashMap<String, String>();
                final String key_email =  sessionManager.getUserDetail().get(SessionManager.key_email);
                Log.i("","key_email = "+ key_email);
                obj.put("alamat_email", key_email);
                return obj;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }




    private void spinnerWisatawanUtama(String EP,String LP){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://kaffah.amanahgitha.com/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(PesanKarcisPetugasActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            if( Help.isJSONValid(response) ){
                                JSONObject jsonObject = new JSONObject(response.toString());
                                Log.i("tag","success= " + jsonObject.getBoolean("success") );

                                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                                arrListUtamaPtgs.clear();
                                if( jsonObject.getBoolean("success") ) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                                    Log.i("tag"," dt jsonArray: "+jsonArray.toString());

                                    for (int i = 0; i <jsonArray.length();i++ ) {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                        String _id = jsonObject1.getString("id");
                                        String _kode_ksda = jsonObject1.getString("kode_ksda");
                                        String _kode_lokasi = jsonObject1.getString("kode_lokasi");
                                        String _kode_karcis = jsonObject1.getString("kode_karcis");
                                        String _nama_karcis = jsonObject1.getString("nama_karcis");
                                        String _kode_libur = jsonObject1.getString("kode_libur");
                                        String _harga_karcis_wisata_wisnu = jsonObject1.getString("harga_karcis_wisata_wisnu");
                                        String _harga_karcis_wisata_wisman = jsonObject1.getString("harga_karcis_wisata_wisman");
                                        String _harga_karcis_asuransi_wisnu = jsonObject1.getString("harga_karcis_asuransi_wisnu");
                                        String _harga_karcis_asuransi_wisman = jsonObject1.getString("harga_karcis_asuransi_wisman");

                                        Log.i("tag","create session wisnu= "+ _harga_karcis_wisata_wisnu);
                                        Log.i("tag","create session wisman= "+ _harga_karcis_wisata_wisman);
                                        Log.i("tag","create session ass wisman= "+ _harga_karcis_asuransi_wisnu);
                                        Log.i("tag","create session ass wisman= "+ _harga_karcis_asuransi_wisman);

                                        sessionManager.createSessionWisUtm( _kode_ksda, _kode_lokasi, _kode_karcis,
                                                _nama_karcis, _kode_libur, _harga_karcis_wisata_wisnu,
                                                _harga_karcis_wisata_wisman, _harga_karcis_asuransi_wisnu,
                                                _harga_karcis_asuransi_wisman, _id );


                                        final String key_kode_libur = sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_kode_libur).toString();
                                        final String hrg = sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_harga_karcis_wisata_wisnu).toString();
                                        final String hrg2 = sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_harga_karcis_wisata_wisman).toString();

                                        Log.i("tag","create session key_kode_libur = "+ key_kode_libur);
                                        Log.i("tag","create session hrg = "+ hrg);
                                        Log.i("tag","create session hr2 g= "+ hrg2);

                                        arrListUtamaPtgs.add(i, "Rp."+ (_harga_karcis_wisata_wisnu) + " - (" + _nama_karcis + ") ");
                                    }
                                } else {

                                }
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(PesanKarcisPetugasActivity.this);
                                builder.setMessage("Format Json Error !")
                                        .setCancelable(false)
                                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                sessionManager.logout();
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                            _spinner_karcis_utama_ptgs.setAdapter(new ArrayAdapter<String>(PesanKarcisPetugasActivity.this, android.R.layout.simple_spinner_item,arrListUtamaPtgs) );
                        } catch (JSONException e) {
                            Log.i("triono", "error ===" + e.toString() );
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

                Log.i("tag", "LP Spinner Wisatawan utama =" + LP );
                Log.i("tag", "getDateTime Spinner Wisatawan utama =" + Help.getDateTime() );

                obj.put("lokasi", LP );
                obj.put("tgl_kunjungan",Help.getDateTime());
                return obj;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }




    private void spinnerWisatawanTambahan(String EP, String LP){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://kaffah.amanahgitha.com/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(PesanKarcisPetugasActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            if( Help.isJSONValid(response) ){
                                JSONObject jsonObject = new JSONObject(response.toString());
                                Log.i("tag","success= " + jsonObject.getBoolean("success") );
                                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                                if( jsonObject.getBoolean("success") ) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                                    arrListTambahanPtgs.clear();
                                    for (int i = 0; i <jsonArray.length();i++ ) {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                        String _kode_ksda = jsonObject1.getString("kode_ksda");
                                        String _kode_lokasi = jsonObject1.getString("kode_lokasi");
                                        String _kode_karcis = jsonObject1.getString("kode_karcis");
                                        String _nama_karcis = jsonObject1.getString("nama_karcis");
                                        String _kode_libur = jsonObject1.getString("kode_libur");
                                        String _harga_karcis_wisata = jsonObject1.getString("harga_karcis_wisata");
                                        String _harga_karcis_asuransi = jsonObject1.getString("harga_karcis_asuransi");
                                        String _id = jsonObject1.getString("id");

                                        sessionManager.createSessionWisTmbhn(_kode_ksda, _kode_lokasi, _kode_karcis,
                                                _nama_karcis, _kode_libur, _harga_karcis_wisata, _harga_karcis_asuransi, _id );

                                        arrListTambahanPtgs.add(i, "Rp."+ (_harga_karcis_wisata) + " - (" + _nama_karcis + ") ");
                                    }
                                }
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(PesanKarcisPetugasActivity.this);
                                builder.setMessage("Format Json Error !")
                                        .setCancelable(false)
                                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                sessionManager.logout();
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                            _spinner_karcis_tmbhn_ptgs.setAdapter(new ArrayAdapter<String>(PesanKarcisPetugasActivity.this, android.R.layout.simple_spinner_dropdown_item,arrListTambahanPtgs) );
                        } catch (JSONException e) {
                            Log.i("triono", "error ===" + e.toString() );
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

                Log.i("tag", "LP spinnerWisatawanTambahan =" + LP );
                Log.i("tag", "getDateTime spinnerWisatawanTambahan =" + Help.getDateTime() );

                obj.put("lokasi", LP );
                obj.put("tgl_kunjungan",Help.getDateTime());
                return obj;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }



    private void pesanKarcisPetugas(String EP){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://kaffah.amanahgitha.com/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(PesanKarcisPetugasActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("tag","response= " + response );
                        try {
                            findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                            if( Help.isJSONValid(response) ) {

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
                                    String _mode_pembayaran = jsonObject1.getString("mode_pembayaran");
                                    String _no_hp_pengunjung = jsonObject1.getString("no_hp_pengunjung");
                                    String _email_pengunjung = jsonObject1.getString("email_pengunjung");

                                    Intent i = new Intent(PesanKarcisPetugasActivity.this, SuccessRegistrasiWisatawanActivity.class);
                                    i.putExtra("result_dt_ket", "Pemesanan Anda Berhasil Silahkan Cek email!");
                                    i.putExtra("result_dt_email", "");
                                    i.putExtra("result_dt_berhasil", berhasil);
                                    i.putExtra("result_dt_flag", "flagPesanKarcisPetugas");

                                    startActivity(i);
                                }
                            }


                        } catch (JSONException e) {
                            Log.i("triono", "error ===" + e.toString() );
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

                String flag_pemesan = null;
                final String key_kode_ksda = (String) sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_kode_ksda);
                final String key_name =  (String) sessionManager.getUserDetail().get(SessionManager.key_name);
                final String key_email =  (String) sessionManager.getUserDetail().get(SessionManager.key_email);
                final String key_hp =  (String) sessionManager.getUserDetail().get(SessionManager.key_hp);
                final String key_tgl_penjualan =  Help.getDateTime();
                final String tgl_kunjungan =  _tgl_kunjungan_ptgs.getText().toString().trim();
                final String key_kode_lokasi =   sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_kode_lokasi_wist_order);
                final String key_id_utama =   sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_id_wist_order);
                final String key_id_tmbhn =   sessionManager.getDaftarKarcisWisatawanTmbhn().get(SessionManager.key_id_tmbhn);
                final String jml_wisnu = _jml_krcs_wisnu_ptgs.getText().toString().trim();
                final String jml_wisman = _jml_krcs_wisman_ptgs.getText().toString().trim();
                final String jml_tmhn = _jml_krcs_wisman_tmbhn_ptgs.getText().toString().trim();
                flag_pemesan = (key_kode_ksda == null ? "1" : "2");

                final String jns_byr = sessionManager.getDataJnsByr().get(SessionManager.key_mode_pembayaran);
                final  String hp_pengunjung = _hp_pengunjung_ptgs.getText().toString().trim();
                final String email_pengunjung = _email_pengunjung.getText().toString().trim();
                final String nama_pengunjung = _nama_pengunjung_ptgs.getText().toString().trim();


                Log.i("tag","jns_byr= " + jns_byr );
                Log.i("tag","hp_pengunjung= " + hp_pengunjung );
                Log.i("tag","nama_pengunjung= " + nama_pengunjung );
//                Log.i("tag","key_email= " + key_email );
//                Log.i("tag","key_hp= " + key_hp );
//                Log.i("tag","key_tgl_penjualan= " + key_tgl_penjualan );
//                Log.i("tag","tgl_kunjungan= " + tgl_kunjungan );
//                Log.i("tag","key_kode_lokasi= " + key_kode_lokasi );
//                Log.i("tag","key_id_utama= " + key_id_utama );
//                Log.i("tag","key_id_tmbhn= " + key_id_tmbhn );
//                Log.i("tag","jml_wisnu= " + jml_wisnu );
//                Log.i("tag","jml_wisman= " + jml_wisman );
//                Log.i("tag","jml_tmhn= " + jml_tmhn );
//                Log.i("tag","flag_pemesan= " + flag_pemesan );


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
                obj.put("jumlah_wisnu",jml_wisnu);
                obj.put("jumlah_wisman",jml_wisman);
                obj.put("jumlah_tambahan",jml_tmhn);
                obj.put("mode_pembayaran", jns_byr.toString());
                obj.put("no_hp_pengunjung", hp_pengunjung);
                obj.put("email_pengunjung", email_pengunjung);
                obj.put("nama_pengunjung", nama_pengunjung);

                return obj;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }


}




