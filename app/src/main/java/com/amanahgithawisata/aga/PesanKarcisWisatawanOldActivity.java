package com.amanahgithawisata.aga;

import android.animation.ArgbEvaluator;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.ViewPager;

import com.amanahgithawisata.aga.Adapter.CustomAdapterViewPagerLokasiWisata;
import com.amanahgithawisata.aga.Adapter.DatePickerPesanKarcisWstwn;
import com.amanahgithawisata.aga.Adapter.SessionManager;
import com.amanahgithawisata.aga.Helper.Help;
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

public class PesanKarcisWisatawanOldActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    TextView _txt_tgl_kunjungan_order,_txt_jml_krcs_wisnu,_txt_jml_krcs_wisman, _txt_ttl,_txt_jml_krcs_tmbhn,_txt_ttl_tmbhn,_txt_grand_ttl;
    Spinner _spiner_lok_wis_wstwn_order,_spiner_krc_utm_wstwn_order, _spiner_krc_tmbhn_wstwn_order,_spinner_lok_wis;
    Button _btn_order_wist;
    ViewPager _viewPager;
    CustomAdapterViewPagerLokasiWisata customAdapterViewPagerLokasiWisata;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    Integer[] colors = null;

    private List<String> _listKrcUtmWstwnOrder = new ArrayList<String>();
    private List<String> _listKrcTmbhnWstwnOrder = new ArrayList<String>();
    ArrayList<SpinnerListWisata> lokWisList = new ArrayList<SpinnerListWisata>();
    ArrayList<SpinnerListWisataKsda> lokWisListKsda = new ArrayList<SpinnerListWisataKsda>();
    ArrayList<SpinnerKarcisUtama> arrKarcisUtama = new ArrayList<SpinnerKarcisUtama>();
    ArrayList<SpinnerKarcisTambahan> arrKarcisTambahan = new ArrayList<SpinnerKarcisTambahan>();
    public  int spin1;

    SessionManager sessionManager;

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        TextView _txt_tgl_kunjungan_order = (TextView) findViewById(R.id.txt_tgl_kunjungan_order);

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        _txt_tgl_kunjungan_order.setText(sdf.format(c.getTime()));
//        _spiner_lok_wis_wstwn_order = (Spinner) findViewById(R.id.spinner_lok_wisata_order);
//        _spinner_lok_wis  = (Spinner) findViewById(R.id.spinner_lok_ksda_order);;

        final String LP = sessionManager.getDataLokWisPesankarcisWisatawan().get(SessionManager.key_kd_lokwis);
        final String KSDA = sessionManager.getDataLokWisPesankarcisWisatawanKsda().get(SessionManager.key_kd_ksda);
        Log.i("","LP="+ LP);
        Log.i("","KSDA"+ KSDA);

        spinnerKarcisWisatawanUtama("daftar_karcis_wisatawan_utama",LP);
        spinnerKarcisWisatawanTambahan("daftar_karcis_wisatawan_tambahan",LP);

//        quotaTwa("quota_per_twa",KSDA);

        final String tgl_kunj_val = _txt_tgl_kunjungan_order.getText().toString();

        _txt_tgl_kunjungan_order.setError(null);
        if(TextUtils.isEmpty(tgl_kunj_val) ) {
            _txt_tgl_kunjungan_order.setError("Tgl Masih Kosong!");
        } else {
//            quotaTwa("quota_per_twa",KSDA,tgl_kunj_val);
//            _txt_tgl_kunjungan_order.setError(null);
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan_karcis_wisatawan_old);
        sessionManager = new SessionManager(getApplicationContext());
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);

        _txt_tgl_kunjungan_order = (TextView) findViewById(R.id.txt_tgl_kunjungan_order);
        _spiner_lok_wis_wstwn_order = (Spinner) findViewById(R.id.spinner_lok_wisata_order);
        _spinner_lok_wis = (Spinner) findViewById(R.id.spinner_lok_ksda_order);
        _spiner_krc_utm_wstwn_order = (Spinner) findViewById(R.id.spinner_karcis_utama_order);
        _spiner_krc_tmbhn_wstwn_order =(Spinner) findViewById(R.id.spinner_karcis_tmbhn_order);
        _btn_order_wist = (Button) findViewById(R.id.btn_order_wist);
        _txt_jml_krcs_wisnu = (EditText) findViewById(R.id.txt_jml_krcs_wisnu);
        _txt_jml_krcs_wisman = (EditText) findViewById(R.id.txt_jml_krcs_wisman);
        _txt_jml_krcs_tmbhn = (EditText) findViewById(R.id.txt_jml_krcs_wisman_tmbhn);
        _txt_ttl = (EditText) findViewById(R.id.txt_ttl);
        _txt_ttl_tmbhn = (EditText) findViewById(R.id.txt_ttl_tmbhn);
        _txt_grand_ttl = (EditText) findViewById(R.id.txt_grand_ttl);


        spinnerLokWisata("daftar_lokasi_wisata");
//        spinnerLokWisataWisatawan("daftar_lokasi_pintu","");

        _txt_ttl.setEnabled(false);
        _txt_ttl_tmbhn.setEnabled(false);
        _txt_grand_ttl.setEnabled(false);

        _txt_ttl.setText("0");
        _txt_ttl_tmbhn.setText("0");
        _txt_grand_ttl.setText("0");
//        _txt_jml_krcs_wisnu.setText("0");
//        _txt_jml_krcs_wisman.setText("0");
//        _txt_jml_krcs_tmbhn.setText("0");



        _txt_tgl_kunjungan_order.requestFocus();
        _txt_tgl_kunjungan_order.setFocusable(true);
        _txt_tgl_kunjungan_order.setFocusableInTouchMode(true);
        _txt_tgl_kunjungan_order.performClick();







        _txt_tgl_kunjungan_order.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    DialogFragment datePicker = new DatePickerPesanKarcisWstwn();
                    datePicker.show(getSupportFragmentManager(),"date picker PesanKarcisWstwn");
                }
            }
        });
        _txt_tgl_kunjungan_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerPesanKarcisWstwn();
                datePicker.show(getSupportFragmentManager(),"date picker PesanKarcisWstwn");
            }
        });

        _btn_order_wist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String tgl_kunj_val1 = _txt_tgl_kunjungan_order.getText().toString();

                if( TextUtils.isEmpty(tgl_kunj_val1) ) {
                    _txt_tgl_kunjungan_order.setError("Tgl Kunjungan Masih Kosong");
                    _txt_tgl_kunjungan_order.requestFocus();
                } else {
                    inputKarcisWisatawan("input_wisatawan");
                }
            }
        });

        _spiner_lok_wis_wstwn_order.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                SpinnerListWisata item = (SpinnerListWisata) parent.getItemAtPosition(position);
                sessionManager = new SessionManager(getApplicationContext());
                sessionManager.createSessionLokWisPesankarcisWisatawan(item.getKdlok(),item.getNmlok(),"","","");

                spinnerKarcisWisatawanUtama("daftar_karcis_wisatawan_utama",item.getKdlok());
                spinnerKarcisWisatawanTambahan("daftar_karcis_wisatawan_tambahan",item.getKdlok());

                Log.i("","item.getKdlok()="+item.getKdlok());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        _spinner_lok_wis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerListWisataKsda item = (SpinnerListWisataKsda) parent.getItemAtPosition(position);
                sessionManager.createSessionLokWisPesankarcisWisatawanKsda(item.getKdksda(),item.getNmlok());
                spinnerLokWisataWisatawan("daftar_lokasi_pintu",item.getKdksda());
//                spinnerKarcisWisatawanTambahan("daftar_karcis_wisatawan_tambahan",item.getKdksda());
                Log.i("","item.getKdksda2()"+item.getKdksda());

                final String KSDA = sessionManager.getDataLokWisPesankarcisWisatawanKsda().get(SessionManager.key_kd_ksda);
                Log.i("","KSDA"+ KSDA);

                final String tgl_kunj_val = _txt_tgl_kunjungan_order.getText().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        _spiner_krc_utm_wstwn_order.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        _spiner_krc_tmbhn_wstwn_order.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

            }
        });


        _txt_jml_krcs_wisnu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                CalculateKarcis();
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                CalculateKarcis();
            }
            @Override
            public void afterTextChanged(Editable s) {
                CalculateKarcis();
            }
        });

        _txt_jml_krcs_wisman.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                CalculateKarcis();
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                CalculateKarcis();
            }
            @Override
            public void afterTextChanged(Editable s) {
                CalculateKarcis();
            }
        });

        _txt_jml_krcs_tmbhn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                CalculateKarcis();
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                CalculateKarcis();

            }
            @Override
            public void afterTextChanged(Editable s) {

                CalculateKarcis();
            }
        });

        Log.i("","savedInstanceState "+savedInstanceState);

        if( savedInstanceState != null){
//            spin1 = savedInstanceState.getInt("_spin1");


            if (savedInstanceState.containsKey("_spin1"))
            {
                spin1 = savedInstanceState.getInt("_spin1");

                Log.i("","spin1="+spin1);
            }
//            _spinner_lok_wis.setSelection(spin1);



        }



    }



    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("_spin1", "2");
//        outState.putInt("_spiner_lok_wis_wstwn_order",_spiner_lok_wis_wstwn_order.getSelectedItemPosition());
//        outState.putInt("_spiner_krc_utm_wstwn_order",_spiner_krc_utm_wstwn_order.getSelectedItemPosition());

//        Log.i("","spinner_lok_wis.getSelectedItemPosition() "+ _spinner_lok_wis.getSelectedItemPosition());

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        savedInstanceState.getString("_spin1");

    }


    public void CalculateKarcis(){
        final double _jml_krcs_wisnu = Help.ParseDouble(((EditText) findViewById(R.id.txt_jml_krcs_wisnu)).getText().toString());
        final double _jml_krcs_wisman = Help.ParseDouble(((EditText) findViewById(R.id.txt_jml_krcs_wisman)).getText().toString());
        final double _jml_krcs_tmbhn = Help.ParseDouble(((EditText) findViewById(R.id.txt_jml_krcs_wisman_tmbhn)).getText().toString());

        final double hrg_krcs_wisnu = Help.ParseDouble(sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_harga_karcis_wisata_wisnu));
        final double hrg_krcs_wisman = Help.ParseDouble(sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_harga_karcis_wisata_wisman));
        final double hrg_krcs_tmbhn = Help.ParseDouble(sessionManager.getDaftarKarcisWisatawanTmbhn().get(SessionManager.key_harga_karcis_wisata_tmbhn));
        final double hrg_krcs_asrnsi_wisnu = Help.ParseDouble(sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_harga_karcis_asuransi_wisnu));
        final double hrg_krcs_asrnsi_wisman = Help.ParseDouble(sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_harga_karcis_asuransi_wisman));

        Log.i("tag","_jml_krcs_wisnu= "+ _jml_krcs_wisnu);
        Log.i("tag","_jml_krcs_wisman= "+ _jml_krcs_wisman);
        Log.i("tag","calc _jml_krcs_tmbhn= "+ _jml_krcs_tmbhn);

        final int  ttl_wisnu = (int) ((hrg_krcs_wisnu+hrg_krcs_asrnsi_wisnu)*_jml_krcs_wisnu);
        final int   ttl_wisman =(int) ((hrg_krcs_wisman+hrg_krcs_asrnsi_wisman)*_jml_krcs_wisman);
        final int ttl_wisnu_wisman = (ttl_wisnu+ttl_wisman);
        final int ttl_tmbhn = (int) (hrg_krcs_tmbhn*_jml_krcs_tmbhn);
        final int grand_ttl =(int) (ttl_wisnu_wisman+ttl_tmbhn);

        _txt_ttl.setText(String.valueOf(ttl_wisnu_wisman));
        _txt_ttl_tmbhn.setText(String.valueOf(ttl_tmbhn));
        _txt_grand_ttl.setText(String.valueOf(grand_ttl));

        Log.i("tag","hrg_krcs_wisnu= "+ hrg_krcs_wisnu);
        Log.i("tag","hrg_krcs_wisman= "+ hrg_krcs_wisman);
        Log.i("tag","hrg_krcs_tmbhn= "+ hrg_krcs_tmbhn);
        Log.i("tag","hrg_krcs_asrnsi_wisnu= "+ hrg_krcs_asrnsi_wisnu);
        Log.i("tag","hrg_krcs_asrnsi_wisman= "+ hrg_krcs_asrnsi_wisman);

        Log.i("tag","ttl_wisnu= "+ ttl_wisnu);
        Log.i("tag","ttl_wisman= "+ ttl_wisman);
        Log.i("tag","ttl_wisnu_wisman= "+ttl_wisnu_wisman);
        Log.i("tag","ttl_tmbhn = "+ttl_tmbhn);
        Log.i("tag","grand_ttl = "+grand_ttl);
    }

    public void ClearCalculateKarcis(){
        _txt_jml_krcs_wisnu = (EditText) findViewById(R.id.txt_jml_krcs_wisnu);
        _txt_jml_krcs_wisman = (EditText) findViewById(R.id.txt_jml_krcs_wisman);

        _txt_jml_krcs_wisnu.setText("0");
        _txt_jml_krcs_wisman.setText("0");
        _txt_ttl.setText("0");
        _txt_ttl_tmbhn.setText("0");
        _txt_grand_ttl.setText("0");

    }


    private void spinnerLokWisata(String EP){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://"+ Help.domain_api() +"/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(PesanKarcisWisatawanOldActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                response -> {
                    Log.i("", "response spinnerLokWisata===" + response );
                    try {

                        String nm_obj_wisata ;
                        String kd_ksda;
                        lokWisListKsda.clear();

                        if( Help.isJSONValid(response) ){
                            findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                            JSONObject jsonObject = new JSONObject(response.toString());

                            if( jsonObject.getBoolean("success") ) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i <jsonArray.length();i++ ) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    nm_obj_wisata = jsonObject1.getString("nama");
                                    kd_ksda = jsonObject1.getString("kode_ksda");

                                    Log.i("tag","kd_ksda= "+kd_ksda);
                                    Log.i("tag","nm_obj_wisata= "+nm_obj_wisata);

                                    lokWisListKsda.add(new SpinnerListWisataKsda(kd_ksda,nm_obj_wisata,""));
                                }
                            }
                        }
                        _spinner_lok_wis.setAdapter(new ArrayAdapter<SpinnerListWisataKsda>(PesanKarcisWisatawanOldActivity.this, android.R.layout.simple_spinner_dropdown_item,lokWisListKsda) );
//                        _viewPager.setAdapter(new ArrayAdapter<SpinnerListWisataKsda>(PesanKarcisWisatawanOldActivity.this, android.R.layout.simple_spinner_dropdown_item,lokWisListKsda) );
                    } catch (JSONException e) {
                        Log.i("triono", "error ===" + e.toString() );
                        e.printStackTrace();
                    }
                    requestQueue.stop();
                }, error -> {
                    Log.i("", "response =" + error.toString());
                    error.printStackTrace();
                    requestQueue.stop();

                    findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                    AlertDialog.Builder builder = new AlertDialog.Builder(PesanKarcisWisatawanOldActivity.this);
                    builder.setMessage("Terjadi Gangguan, Refresh ")
                            .setCancelable(false)
                            .setPositiveButton("Ya", (dialog, id) -> {
                                spinnerLokWisata("daftar_lokasi_wisata");
                            })
                            .setNegativeButton("Tidak", (dialog, id) -> dialog.cancel());
                    AlertDialog alert = builder.create();
                    alert.show();
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> obj = new HashMap<String, String>();
                final String ksda_val =  sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi);

                obj.put("lokasi", "27");
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }


    private void spinnerLokWisataWisatawan(String EP,String KSDA){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://"+ Help.domain_api() +"/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(PesanKarcisWisatawanOldActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                response -> {
                    Log.i("triono", "response spinnerLokWisataWisatawan =" + response );
                    try {

                        if( Help.isJSONValid(response) ){
                            JSONObject jsonObject = new JSONObject(response.toString());
                            findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                            String nm_obj_wisata;
                            String kd_lokasi;
                            lokWisList.clear();

                            if( jsonObject.getBoolean("success") ) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i <jsonArray.length();i++ ) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    nm_obj_wisata = jsonObject1.getString("nama");
                                    kd_lokasi = jsonObject1.getString("kode_lokasi");

                                    Log.i("","kd_lokasi= "+kd_lokasi);
                                    Log.i("","nm_obj_wisata= "+nm_obj_wisata);

                                    lokWisList.add(new SpinnerListWisata(kd_lokasi,nm_obj_wisata,"","",""));
                                }
                            }
                        }
                        _spiner_lok_wis_wstwn_order.setAdapter(new ArrayAdapter<SpinnerListWisata>(PesanKarcisWisatawanOldActivity.this, android.R.layout.simple_spinner_dropdown_item,lokWisList) );
                    } catch (JSONException e) {
                        Log.i("", "error ===" + e.toString() );
                        e.printStackTrace();
                    }
                    requestQueue.stop();
                }, error -> {
                        Log.i("triono", "response =" + error.toString());
                        error.printStackTrace();
                        requestQueue.stop();
                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        AlertDialog.Builder builder = new AlertDialog.Builder(PesanKarcisWisatawanOldActivity.this);
                        builder.setMessage("Terjadi Gangguan, Refresh ")
                                .setCancelable(false)
                                .setPositiveButton("Ya", (dialog, id) -> {
                                    spinnerLokWisataWisatawan("daftar_lokasi_pintu",KSDA);
                                })
                                .setNegativeButton("Tidak", (dialog, id) -> dialog.cancel());
                        AlertDialog alert = builder.create();
                        alert.show();

                    }
        ) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> obj = new HashMap<String, String>();
                        obj.put("kode_ksda", KSDA);
                        return obj;
                        }
                    };
           int socketTimeout = 0;
           RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
           stringRequest.setRetryPolicy(policy);
           requestQueue.add(stringRequest);
        }


    private void spinnerKarcisWisatawanUtama(String EP,String LP){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url_s2 = "http://"+ Help.domain_api() +"/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue_s2 = Volley.newRequestQueue(PesanKarcisWisatawanOldActivity.this);
        StringRequest stringRequest_s2 = new StringRequest(Request.Method.POST, server_url_s2,
                (Response.Listener<String>) response -> {

                    try {
                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        if( Help.isJSONValid(response) ){
                            JSONObject jsonObject = new JSONObject(response.toString());
                            Log.i("tag","success= " + jsonObject.getBoolean("success") );


//                                _listKrcUtmWstwnOrder.clear();
                            arrKarcisUtama.clear();

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

//                                        Log.i("tag","create session wisnu= "+ _harga_karcis_wisata_wisnu);
//                                        Log.i("tag","create session wisman= "+ _harga_karcis_wisata_wisman);
//                                        Log.i("tag","create session ass wisman= "+ _harga_karcis_asuransi_wisnu);
//                                        Log.i("tag","create session ass wisman= "+ _harga_karcis_asuransi_wisman);

//                                        sessionManager.createSessionWisUtm( _kode_ksda, _kode_lokasi, _kode_karcis,
//                                                                            _nama_karcis, _kode_libur, _harga_karcis_wisata_wisnu,
//                                                                            _harga_karcis_wisata_wisman, _harga_karcis_asuransi_wisnu,
//                                                                            _harga_karcis_asuransi_wisman, _id );


//                                        final String key_kode_libur = sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_kode_libur).toString();
//                                        final String hrg = sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_harga_karcis_wisata_wisnu).toString();
//                                        final String hrg2 = sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_harga_karcis_wisata_wisman).toString();
//
//                                        Log.i("tag","create session key_kode_libur = "+ key_kode_libur);
//                                        Log.i("tag","create session hrg = "+ hrg);
//                                        Log.i("tag","create session hr2 g= "+ hrg2);

//                                        _listKrcUtmWstwnOrder.add(i, "Rp."+ (_harga_karcis_wisata_wisnu) + " - (" + _nama_karcis + ") ");
//                                        _listKrcUtmWstwnOrder.add(i, _nama_karcis );
                                    arrKarcisUtama.add(new SpinnerKarcisUtama( _kode_ksda, _kode_lokasi, _kode_karcis,
                                            _nama_karcis, _kode_libur, _harga_karcis_wisata_wisnu,
                                            _harga_karcis_wisata_wisman, _harga_karcis_asuransi_wisnu,
                                            _harga_karcis_asuransi_wisman, _id ));
                                }
                            }
                        }
                        _spiner_krc_utm_wstwn_order.setAdapter(new ArrayAdapter<SpinnerKarcisUtama>(PesanKarcisWisatawanOldActivity.this, android.R.layout.simple_spinner_item,arrKarcisUtama) );
                    } catch (JSONException e) {
                        Log.i("triono", "error ===" + e.toString() );
                        e.printStackTrace();
                    }
                    requestQueue_s2.stop();
                }, (Response.ErrorListener) error -> {
                    Log.i("tag", "response =" + error.toString());
                    error.printStackTrace();
                    requestQueue_s2.stop();

                    findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                    AlertDialog.Builder builder = new AlertDialog.Builder(PesanKarcisWisatawanOldActivity.this);
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


                final String ksda_val =  sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi);
                final String tgl_kujungan_val = (String) ""+_txt_tgl_kunjungan_order.getText();

                Log.i("tag", "LP Spinner utama =" + LP );
                obj.put("lokasi", LP.trim() );
                obj.put("tgl_kunjungan",tgl_kujungan_val.trim() );
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest_s2.setRetryPolicy(policy);
        requestQueue_s2.add(stringRequest_s2);
    }


    private void spinnerKarcisWisatawanTambahan(String EP, String LP){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url_s1 = "http://"+ Help.domain_api() +"/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue_s1 = Volley.newRequestQueue(PesanKarcisWisatawanOldActivity.this);
        StringRequest stringRequest_s1 = new StringRequest(Request.Method.POST, server_url_s1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                            if( Help.isJSONValid(response) ){
                                JSONObject jsonObject = new JSONObject(response.toString());
                                Log.i("tag","success= " + jsonObject.getBoolean("success") );

                                arrKarcisTambahan.clear();
                                if( jsonObject.getBoolean("success") ) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("data");

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

//
//                                        _listKrcTmbhnWstwnOrder.add(i, "Rp."+ (_harga_karcis_wisata) + " - (" + _nama_karcis + ") ");
//                                        _listKrcTmbhnWstwnOrder.add(i,  _nama_karcis );

                                        arrKarcisTambahan.add(new SpinnerKarcisTambahan(_kode_karcis,_nama_karcis,_harga_karcis_wisata, _id));
                                    }
                                }
                            }
                            _spiner_krc_tmbhn_wstwn_order.setAdapter(new ArrayAdapter<SpinnerKarcisTambahan>(PesanKarcisWisatawanOldActivity.this, android.R.layout.simple_spinner_dropdown_item,arrKarcisTambahan) );
                        } catch (JSONException e) {
                            Log.i("", "error ===" + e.toString() );
                            e.printStackTrace();
                        }
                        requestQueue_s1.stop();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("tag", "response =" + error.toString());

                error.printStackTrace();
                requestQueue_s1.stop();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> obj = new HashMap<String, String>();

                final String ksda_val =  sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi);
                final String tgl_kujungan_val = (String) ""+_txt_tgl_kunjungan_order.getText();

                Log.i("tag", "LP Spinner tambahan =" + LP );
                obj.put("lokasi", LP.trim() );
                obj.put("tgl_kunjungan",tgl_kujungan_val.trim());
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest_s1.setRetryPolicy(policy);
        requestQueue_s1.add(stringRequest_s1);
    }



    private void inputKarcisWisatawan(String EP){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://"+ Help.domain_api() +"/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(PesanKarcisWisatawanOldActivity.this);
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


//                                        sessionManager.createSessionWisTmbhn(_kode_ksda, _kode_lokasi, _kode_karcis,
//                                                _nama_karcis, _kode_libur, _harga_karcis_wisata, _harga_karcis_asuransi, _id );

                                        Intent i = new Intent(PesanKarcisWisatawanOldActivity.this, SuccessRegistrasiWisatawanActivity.class);
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
                final String key_kode_ksda = (String) sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_kode_ksda);
                final String key_name =  (String) sessionManager.getUserDetail().get(SessionManager.key_name);
                final String key_email =  (String) sessionManager.getUserDetail().get(SessionManager.key_email);
                final String key_hp =  (String) sessionManager.getUserDetail().get(SessionManager.key_hp);
                final String key_tgl_penjualan =  Help.getDateTime();
                final String tgl_kunjungan =  _txt_tgl_kunjungan_order.getText().toString().trim();
                final String key_kode_lokasi =  (String) sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_kode_lokasi_wist_order);
                final String key_id_utama =  (String) sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_id_wist_order);
                final String key_id_tmbhn =  (String) sessionManager.getDaftarKarcisWisatawanTmbhn().get(SessionManager.key_id_tmbhn);
                String jml_wisnu = _txt_jml_krcs_wisnu.getText().toString().trim();
                String jml_wisman = _txt_jml_krcs_wisman.getText().toString().trim();
                String jml_tmbhn = _txt_jml_krcs_tmbhn.getText().toString().trim();
                String key_kode_lok_new = sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi);

                Log.e("tag","SessionManager.key_kode_lokasi= "+sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi));

//                flag_pemesan = (key_kode_lok_new == null ? "1" : "2");

                Log.e("","key_kode_lok_new= "+key_kode_lok_new);

//                String key_kode_lok_new = "12001";

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

                if(jml_wisnu.equals("")){
                    jml_wisnu = "0";
                }
                if(jml_wisman.equals("")){
                    jml_wisman = "0";
                }
                if(jml_tmbhn.equals("")){
                    jml_tmbhn = "0";
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
                Log.i("tag","jml_wisnu= " + jml_wisnu );
                Log.i("tag","jml_wisman= " + jml_wisman );
                Log.i("tag","jml_tmbhn= " + jml_tmbhn );
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
                obj.put("jumlah_wisnu",jml_wisnu);
                obj.put("jumlah_wisman",jml_wisman);
                obj.put("jumlah_tambahan",jml_tmbhn);
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }


    private void quotaTwa(String EP,String KSDA, String TGL){
//        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);

        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url_q = "http://"+ Help.domain_api() +"/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue_q = Volley.newRequestQueue(PesanKarcisWisatawanOldActivity.this);

        StringRequest stringRequest_q = new StringRequest(Request.Method.POST, server_url_q,
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {
                        Log.i("tag", "response quotaTwa =" + response );
                        try {

                            if( Help.isJSONValid(response) ){
                                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                                JSONObject jsonObject = new JSONObject(response);
                                String data = jsonObject.getString("data");
                                JSONObject jsonObject1 = new JSONObject(data);
                                boolean _berhasil = jsonObject1.getBoolean("berhasil");

//                                if( _berhasil ) {
                                String  _id = jsonObject1.getString("id");
                                int _quota = jsonObject1.getInt("quota");
                                String _ket = jsonObject1.getString("keterangan");
                                Log.i("","_keterangan "+_ket);
                                Log.i("","_berhasil "+_berhasil);
                                Log.i("","qouta "+_quota);
                                Log.i("","_keterangan "+_ket);
//                                    if( _quota > 0  ){

//                                Toast.makeText(PesanKarcisWisatawanOldActivity.this,"quota x"+_quota, Toast.LENGTH_LONG).show();


                                if( _quota <= 0  ) {

                                    Intent i = new Intent(PesanKarcisWisatawanOldActivity.this, SuccessRegistrasiWisatawanActivity.class);
                                    final String _email = sessionManager.getUserDetail().get(SessionManager.key_email);
                                    i.putExtra("result_dt_ket", _ket);
                                    i.putExtra("result_dt_email",_email);
                                    i.putExtra("result_dt_berhasil", false);
                                    i.putExtra("result_dt_flag", "checkQuotaTwa");
                                    startActivity(i);

                                } else{
                                    TextView quotax = findViewById(R.id.text_quota_wist);
                                    quotax.setText(String.valueOf("Quota: "+_quota));
                                    quotax.setTextColor(Color.parseColor("#ffe458"));
                                }

//                                    }
                            }
                        } catch (JSONException e) {
                            Log.i("", "error =" + e.toString() );
                            e.printStackTrace();
                        }
                        requestQueue_q.stop();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("", "response =" + error.toString());
                error.printStackTrace();
                requestQueue_q.stop();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> obj = new HashMap<String, String>();



//                final String __tgl_kunj = (String) _tgl_kunj.getText();

                Log.i("","Help.getDateTime()x "+Help.getDateTime());
                Log.i("","KSDA "+ KSDA);
                Log.i("","tgl_kunj "+ TGL);

                obj.put("kode_ksda", KSDA);
                obj.put("tgl_quota", TGL);

//                obj.put("kode_ksda", "27");
//                obj.put("tgl_quota","2020-06-11");
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest_q.setRetryPolicy(policy);
        requestQueue_q.add(stringRequest_q);
    }


}




