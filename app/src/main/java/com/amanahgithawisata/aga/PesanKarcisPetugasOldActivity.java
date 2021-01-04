package com.amanahgithawisata.aga;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.amanahgithawisata.aga.Adapter.SessionManager;
import com.amanahgithawisata.aga.Helper.Help;
import com.amanahgithawisata.aga.Model.SpinnerJnsByr;
import com.amanahgithawisata.aga.Model.SpinnerKarcisTambahan;
import com.amanahgithawisata.aga.Model.SpinnerKarcisUtama;
import com.amanahgithawisata.aga.Model.SpinnerListWisata;
import com.amanahgithawisata.aga.Model.SpinnerListWisataKsda;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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

public class PesanKarcisPetugasOldActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
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
    TextView _text_quota;

    Spinner _spinner_karcis_utama_ptgs;
    Spinner _spinner_karcis_tmbhn_ptgs;
    Spinner _spinner_lok_pintu_ptgs;
    Spinner _spinner_jns_byr_ptgs;
    Button _btn_order_ptgs;
    ImageView _imgv;

    private List<String> arrListUtamaPtgs = new ArrayList<String>();
    private List<String> arrListTambahanPtgs = new ArrayList<String>();
    ArrayList<SpinnerListWisata> arrListWisata = new ArrayList<SpinnerListWisata>();
    ArrayList<SpinnerListWisataKsda> arrListWisataKsda = new ArrayList<SpinnerListWisataKsda>();
    ArrayList<SpinnerJnsByr> arrJnsByr = new ArrayList<SpinnerJnsByr>();
    ArrayList<SpinnerKarcisUtama> arrKarcisUtama = new ArrayList<SpinnerKarcisUtama>();
    ArrayList<SpinnerKarcisTambahan> arrKarcisTambahan = new ArrayList<SpinnerKarcisTambahan>();

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
        _text_quota = (TextView) findViewById(R.id.text_quota);

        final String LP = sessionManager.getDataLokWisPesankarcisWisatawan().get(SessionManager.key_kd_lokwis);
        spinnerWisatawanUtama("daftar_karcis_wisatawan_utama",LP);
        spinnerWisatawanTambahan("daftar_karcis_wisatawan_tambahan",LP);

        Log.i("","LP1="+LP);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan_karcis_petugas_old);
        sessionManager = new SessionManager(getApplicationContext());
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);

        _tgl_kunjungan_ptgs = (TextView) findViewById(R.id.tgl_kunjungan_ptgs);
        _spinner_lok_pintu_ptgs = (Spinner) findViewById(R.id.spinner_lok_pintu_ptgs);
        _spinner_karcis_utama_ptgs = (Spinner) findViewById(R.id.spinner_karcis_utama_ptgs);
        _spinner_karcis_tmbhn_ptgs =(Spinner) findViewById(R.id.spinner_karcis_tmbhn_ptgs);
        _spinner_jns_byr_ptgs = (Spinner) findViewById(R.id.spinner_jns_byr_ptgs);
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
//        _imgv = (ImageView) findViewById(R.id.imgBell);
//        _imgv.setVisibility(View.GONE);

//        spinnerLokPintuPtgs("daftar_lokasi_wisata","");

        final String KL = sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi);

//        spinnerLokPintuPtgs("daftar_lokasi_pintu", KL);

        spinnerLokPintuPtgs("petugas_daftar_lokasi_wisata", KL);
        spinnerJnsByrPtgs("informasi_mode_pembayaran","");
//        quotaTwa("quota_per_twa","");

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
                sessionManager.createSessionLokWisPesankarcisWisatawan(item.getKdlok(),item.getNmlok(),item.getAlmtlok(),item.getKotalok(),item.getUrl_img_lok());

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




        _spinner_karcis_utama_ptgs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerKarcisUtama item = (SpinnerKarcisUtama) parent.getItemAtPosition(position);
                sessionManager = new SessionManager(getApplicationContext());

                sessionManager.createSessionWisUtm( item.getKode_ksda(),
                        item.getKode_lokasi(),
                        item.getKode_karcis(),
                        item.getNama_karcis(),
                        item.getKode_libur(),
                        item.getHarga_karcis_wisata_wisnu(),
                        item.getHarga_karcis_wisata_wisman(),
                        item.getHarga_karcis_asuransi_wisnu(),
                        item.getHarga_karcis_asuransi_wisman(),
                        item.getId(),"" );

                Log.i("tag"," getNama_karcis = "+ item.getNama_karcis());
                Log.i("tag"," getHarga_karcis_wisata_wisnu = "+ item.getHarga_karcis_wisata_wisnu());

                CalculateKarcis();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                CalculateKarcis();

            }
        });

        _spinner_karcis_tmbhn_ptgs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                SpinnerKarcisTambahan item = (SpinnerKarcisTambahan) parent.getItemAtPosition(position);

                sessionManager = new SessionManager(getApplicationContext());
                sessionManager.createSessionWisTmbhn(   item.getKode_karcis(), item.getNama_karcis(),
                        item.getHarga_karcis_wisata(),item.getId_tmbhn(),"" );

                CalculateKarcis();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                CalculateKarcis();
            }
        });


        _btn_order_ptgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inputKarcisPetugas("input_petugas");

            }
        });

        _jml_krcs_wisnu_ptgs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                CalculateKarcis();
            }
        });


        _jml_krcs_wisman_ptgs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                CalculateKarcis();
            }
        });

        _jml_krcs_wisman_tmbhn_ptgs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                CalculateKarcis();
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


        final double hrg_krcs_wisnu = Help.ParseDouble(sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_harga_karcis_wisata_wisnu));
        final double hrg_krcs_wisman = Help.ParseDouble(sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_harga_karcis_wisata_wisman));
        final double hrg_krcs_tmbhn = Help.ParseDouble(sessionManager.getDaftarKarcisWisatawanTmbhn().get(SessionManager.key_harga_karcis_wisata_tmbhn));
        final double hrg_krcs_asrnsi_wisnu = Help.ParseDouble(sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_harga_karcis_asuransi_wisnu));
        final double hrg_krcs_asrnsi_wisman = Help.ParseDouble(sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_harga_karcis_asuransi_wisman));

        Log.i("tag","hrg_krcs_tmbhn= "+ hrg_krcs_tmbhn);

        Log.i("tag","_jml_krcs_wisnu= "+ _jml_krcs_wisnu);
        Log.i("tag","_jml_krcs_wisman= "+ _jml_krcs_wisman);
        Log.i("tag","_jml_krcs_tmbhn= "+ _jml_krcs_tmbhn);

        Log.i("tag","hrg_krcs_wisnu= "+ hrg_krcs_wisnu);
        Log.i("tag","hrg_krcs_wisman= "+ hrg_krcs_wisman);

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


    private void quotaTwa(String EP,String KSDA){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://"+ Help.domain_api() +"/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {
                        Log.i("tag", "response quotaTwa =" + response );
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
                                String _keterangan = jsonObject1.getString("keterangan");
                                Log.i("","_keterangan "+_keterangan);
//                                Log.i("","_berhasil "+_berhasil);
//                                Log.i("","qouta "+_quota);
//                                Log.i("","_keterangan "+_keterangan);
//                                    if( _quota > 0  ){

//                                        Toast.makeText(PesanKarcisPetugasActivity.this,"quota > 0.", Toast.LENGTH_LONG).show();

                                TextView quotax = (TextView)findViewById(R.id.text_quota);
                                ImageView imgv = (ImageView) findViewById(R.id.imgBell);
                                quotax.setText(String.valueOf("Quota: "+_quota));
                                quotax.setTextColor(Color.parseColor("#ffe458"));
//                                        imgv.setVisibility(View.VISIBLE);
//                                    }
                            }
                            _spinner_jns_byr_ptgs.setAdapter(new ArrayAdapter<SpinnerJnsByr>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, arrJnsByr) );
                        } catch (JSONException e) {
                            Log.i("", "error =" + e.toString() );
                            e.printStackTrace();
                        }
                        requestQueue.stop();
                    }
                }, (Response.ErrorListener) error -> {
            Log.i("", "response quotaTwa=" + error.toString());
            error.printStackTrace();
            requestQueue.stop();

            findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
            builder.setMessage("Terjadi Gangguan, Refresh Halaman")
                    .setCancelable(false)
                    .setPositiveButton("Ya", (dialog, id) -> {
                        finish();
                        startActivity(getIntent());
                    })
                    .setNegativeButton("Tidak", (dialog, id) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> obj = new HashMap<String, String>();

                Log.i("","KSDAx "+KSDA);
                Log.i("","Help.getDateTime()x "+Help.getDateTime());

                obj.put("kode_ksda", KSDA);
                obj.put("tgl_quota",Help.getDateTime());

//                obj.put("kode_ksda", "27");
//                obj.put("tgl_quota","2020-06-11");
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    private void spinnerJnsByrPtgs(String EP,String KL){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://"+ Help.domain_api() +"/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("tag", "response spinnerJnsByrPtgs =" + response );
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
                                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
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
                            _spinner_jns_byr_ptgs.setAdapter(new ArrayAdapter<SpinnerJnsByr>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, arrJnsByr) );
                        } catch (JSONException e) {
                            Log.i("triono", "error ===" + e.toString() );
                            e.printStackTrace();
                        }
                        requestQueue.stop();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("triono", "response spinnerJnsByrPtgs=" + error.toString());
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
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }



    private void spinnerLokPintuPtgs(String EP,String KL){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://"+ Help.domain_api() +"/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("tag", "response spinnerLokPintuPtgs =" + response );
                        try {

                            if( Help.isJSONValid(response) ){
                                JSONObject jsonObject = new JSONObject(response);

                                Log.i("","spinner pintu response= "+response);


                                arrListWisata.clear();

                                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                                if( jsonObject.getBoolean("success") ) {

                                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                                    for (int i = 0; i <jsonArray.length();i++ ) {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                        String kode_ksda = jsonObject1.getString("kode_ksda");
                                        String lokasi_pintu = jsonObject1.getString("lokasi_pintu");
                                        String nama_pintu = jsonObject1.getString("nama_pintu");

                                        sessionManager.createSessionEksp(kode_ksda,nama_pintu);

                                        Log.i("tag","kode_ksda= "+kode_ksda);
                                        Log.i("tag","nama= "+nama_pintu);
                                        arrListWisata.add(new SpinnerListWisata(lokasi_pintu,nama_pintu,"","",""));
                                        quotaTwa("quota_per_twa",kode_ksda);
                                    }
                                }
                            }
                            _spinner_lok_pintu_ptgs.setAdapter(new ArrayAdapter<SpinnerListWisata>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,arrListWisata) );
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
                Log.i("triono", "response spinnerLokPintuPtgs=" + error.toString());
                error.printStackTrace();
                requestQueue.stop();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> obj = new HashMap<String, String>();
                final String key_email =  sessionManager.getUserDetail().get(SessionManager.key_email).trim();
                obj.put("alamat_email", key_email);
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }




    private void spinnerWisatawanUtama(String EP,String LP){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://"+ Help.domain_api() +"/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
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

                                        Log.i("tag"," wisnu= "+ _harga_karcis_wisata_wisnu);
                                        Log.i("tag"," wisman= "+ _harga_karcis_wisata_wisman);
                                        Log.i("tag"," wisman= "+ _harga_karcis_asuransi_wisnu);
                                        Log.i("tag"," wisman= "+ _harga_karcis_asuransi_wisman);
//
//                                        sessionManager.createSessionWisUtm( _kode_ksda, _kode_lokasi, _kode_karcis,
//                                                _nama_karcis, _kode_libur, _harga_karcis_wisata_wisnu,
//                                                _harga_karcis_wisata_wisman, _harga_karcis_asuransi_wisnu,
//                                                _harga_karcis_asuransi_wisman, _id );


//                                        arrListUtamaPtgs.add(i, "Rp."+ (_harga_karcis_wisata_wisnu) + " - (" + _nama_karcis + ") ");
//                                        arrListUtamaPtgs.add(i, _nama_karcis);


                                        arrKarcisUtama.add(new SpinnerKarcisUtama( _kode_ksda, _kode_lokasi, _kode_karcis,
                                                _nama_karcis, _kode_libur, _harga_karcis_wisata_wisnu,
                                                _harga_karcis_wisata_wisman, _harga_karcis_asuransi_wisnu,
                                                _harga_karcis_asuransi_wisman, _id ));
                                    }
                                }
                            }
                            _spinner_karcis_utama_ptgs.setAdapter(new ArrayAdapter<SpinnerKarcisUtama>(getApplicationContext(), android.R.layout.simple_spinner_item,arrKarcisUtama) );
                        } catch (JSONException e) {
                            Log.i("", "error spinnerWisatawanUtama=" + e.toString() );
                            e.printStackTrace();
                        }
                        requestQueue.stop();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("tag", "response spinnerWisatawanUtama=" + error.toString());
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

                obj.put("lokasi", LP.trim() );
                obj.put("tgl_kunjungan",Help.getDateTime().trim());
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }



    private void spinnerWisatawanTambahan(String EP, String LP){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://"+ Help.domain_api() +"/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
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

                                    arrKarcisTambahan.clear();
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

//                                        sessionManager.createSessionWisTmbhn(_kode_ksda,
//                                                                            _kode_lokasi,
//                                                                            _kode_karcis,
//                                                                            _nama_karcis,
//                                                                            _kode_libur,
//                                                                            _harga_karcis_wisata,
//                                                                            _harga_karcis_asuransi,
//                                                                            _id );

//                                        arrListTambahanPtgs.add(i, "Rp."+ (_harga_karcis_wisata) + " - (" + _nama_karcis + ") ");
//                                        arrKarcisTambahan.add(i, _nama_karcis );
                                        arrKarcisTambahan.add(new SpinnerKarcisTambahan(_kode_karcis,_nama_karcis,_harga_karcis_wisata, _id));

                                        Log.i("","tambahan _kode_ksda "+_kode_ksda);
                                        Log.i("","tambahan _kode_lokasi "+_kode_lokasi);
                                        Log.i("","tambahan _kode_karcis "+_kode_karcis);
                                        Log.i("","tambahan _nama_karcis "+_nama_karcis);
                                        Log.i("","tambahan _kode_libur "+_kode_libur);
                                        Log.i("","tambahan _harga_karcis_wisata "+_harga_karcis_wisata);
                                        Log.i("","tambahan _harga_karcis_asuransi "+_harga_karcis_asuransi);
                                    }
                                }
                            }
                            _spinner_karcis_tmbhn_ptgs.setAdapter(new ArrayAdapter<SpinnerKarcisTambahan>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,arrKarcisTambahan) );
                        } catch (JSONException e) {
                            Log.i("triono", "error ===" + e.toString() );
                            e.printStackTrace();
                        }
                        requestQueue.stop();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("tag", "response spinnerWisatawanTambahan=" + error.toString());
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

                obj.put("lokasi", LP.trim() );
                obj.put("tgl_kunjungan",Help.getDateTime().trim());
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }



    private void inputKarcisPetugas(String EP){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://"+ Help.domain_api() +"/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
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
                                boolean berhasil = jsonObject1.getBoolean("berhasil");

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

                                    Intent i = new Intent(getApplicationContext(), SuccessRegistrasiWisatawanActivity.class);

                                    i.putExtra("result_dt_ket","Pesanan Karcis berhasil, harap check email Anda");
                                    i.putExtra("result_dt_email", _alamat_email);
                                    i.putExtra("result_dt_berhasil", true);
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
                Log.i("tag", "response inputKarcisPetugas=" + error.toString());
                error.printStackTrace();
                requestQueue.stop();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> obj = new HashMap<String, String>();

                String flag_pemesan = null;
                final String key_kode_ksda = (String) sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_kode_ksda).trim();
                final String key_name =  (String) sessionManager.getUserDetail().get(SessionManager.key_name).trim();
                final String key_email =  (String) sessionManager.getUserDetail().get(SessionManager.key_email).trim();
                final String key_hp =  (String) sessionManager.getUserDetail().get(SessionManager.key_hp).trim();
                final String key_tgl_penjualan =  Help.getDateTime().trim();
                final String tgl_kunjungan =  _tgl_kunjungan_ptgs.getText().toString().trim();
                final String key_kode_lokasi =   sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_kode_lokasi_wist_order).trim();
                final String key_id_utama =   sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_id_wist_order).trim();
                final String key_id_tmbhn =   sessionManager.getDaftarKarcisWisatawanTmbhn().get(SessionManager.key_id_tmbhn).trim();
                String jml_wisnu = _jml_krcs_wisnu_ptgs.getText().toString().trim().trim();
                String jml_wisman = _jml_krcs_wisman_ptgs.getText().toString().trim();
                String jml_tmhn = _jml_krcs_wisman_tmbhn_ptgs.getText().toString().trim();
                final String key_kode_lok_new = (String) sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi);
//                flag_pemesan = (key_kode_lok_new == null ? "1" : "2");

                if ( key_kode_lok_new.isEmpty() ){
                    flag_pemesan = "1";
                } else {
                    flag_pemesan= "2";
                }

                final String jns_byr = sessionManager.getDataJnsByr().get(SessionManager.key_mode_pembayaran).trim();
                final  String hp_pengunjung = _hp_pengunjung_ptgs.getText().toString().trim();
                final String email_pengunjung = _email_pengunjung.getText().toString().trim();
                final String nama_pengunjung = _nama_pengunjung_ptgs.getText().toString().trim();

                if(jml_wisnu.equals("")){
                    jml_wisnu = "0";
                }
                if(jml_wisman.equals("")){
                    jml_wisman = "0";
                }
                if(jml_tmhn.equals("")){
                    jml_tmhn = "0";
                }


                Log.i("tag","jns_byr= " + jns_byr );
                Log.i("tag","hp_pengunjung= " + hp_pengunjung );
                Log.i("tag","nama_pengunjung= " + nama_pengunjung );
                Log.i("tag","key_email= " + key_email );
                Log.i("tag","key_hp= " + key_hp );
                Log.i("tag","key_tgl_penjualan= " + key_tgl_penjualan );
                Log.i("tag","tgl_kunjungan= " + tgl_kunjungan );
                Log.i("tag","key_kode_lokasi= " + key_kode_lokasi );
                Log.i("tag","key_id_utama= " + key_id_utama );
                Log.i("tag","key_id_tmbhn= " + key_id_tmbhn );
                Log.i("tag","jml_wisnu= " + jml_wisnu );
                Log.i("tag","jml_wisman= " + jml_wisman );
                Log.i("tag","jml_tmhn= " + jml_tmhn );
                Log.i("tag","flag_pemesan= " + flag_pemesan );


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
                assert jns_byr != null;
                obj.put("mode_pembayaran", jns_byr.toString());
                obj.put("no_hp_pengunjung", hp_pengunjung);
                obj.put("email_pengunjung", email_pengunjung);
                obj.put("nama_pengunjung", nama_pengunjung);

                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }


}




