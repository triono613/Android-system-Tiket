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
import androidx.fragment.app.DialogFragment;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aga.Adapter.DatePickerPesanKarcisWstwn;
import com.example.aga.Adapter.SessionManager;
import com.example.aga.Helper.Help;
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

public class PesanKarcisWisatawanActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    TextView _txt_tgl_kunjungan_order,_txt_jml_krcs_wisnu,_txt_jml_krcs_wisman, _txt_ttl,_txt_jml_krcs_tmbhn,_txt_ttl_tmbhn,_txt_grand_ttl;
    Spinner _spiner_lok_wis_wstwn_order,_spiner_krc_utm_wstwn_order, _spiner_krc_tmbhn_wstwn_order,_spinner_lok_wis;
    Button _btn_order_wist;

    private List<String> _listKrcUtmWstwnOrder = new ArrayList<String>();
    private List<String> _listKrcTmbhnWstwnOrder = new ArrayList<String>();
    ArrayList<SpinnerListWisata> lokWisList = new ArrayList<SpinnerListWisata>();
    ArrayList<SpinnerListWisataKsda> lokWisListKsda = new ArrayList<SpinnerListWisataKsda>();

    SessionManager sessionManager;

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        TextView _txt_tgl_kunjungan_order = (TextView) findViewById(R.id.txt_tgl_kunjungan_order);

//        _txt_tgl_kunjungan_order.clearComposingText();

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        _txt_tgl_kunjungan_order.setText(sdf.format(c.getTime()));
        _spiner_lok_wis_wstwn_order = (Spinner) findViewById(R.id.spinner_lok_wisata_order);
        _spinner_lok_wis  = (Spinner) findViewById(R.id.spinner_lok_ksda_order);;
        final String LP = sessionManager.getDataLokWisPesankarcisWisatawan().get(SessionManager.key_kd_lok);
        spinnerKarcisWisatawanUtama("daftar_karcis_wisatawan_utama",LP);
        spinnerKarcisWisatawanTambahan("daftar_karcis_wisatawan_tambahan",LP);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan_karcis_wisatawan);
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
//        spinnerLokWisataWisatawan("daftar_lokasi_pintu");

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
                pesanKarcisWisatawan("input_wisatawan");
            }
        });

        _spiner_lok_wis_wstwn_order.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                SpinnerListWisata item = (SpinnerListWisata) parent.getItemAtPosition(position);
                sessionManager = new SessionManager(getApplicationContext());
                sessionManager.createSessionLokWisPesankarcisWisatawan(item.getKdlok(),item.getNmlok());

                spinnerKarcisWisatawanUtama("daftar_karcis_wisatawan_utama",item.getKdlok());
                spinnerKarcisWisatawanTambahan("daftar_karcis_wisatawan_tambahan",item.getKdlok());

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

                Log.i("","item.getKdksda= "+item.getKdksda());

                spinnerLokWisataWisatawan("daftar_lokasi_pintu",item.getKdksda());
//                spinnerKarcisWisatawanTambahan("daftar_karcis_wisatawan_tambahan",item.getKdksda());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        _spiner_krc_utm_wstwn_order.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerKarcisWisatawanTambahan("daftar_karcis_wisatawan_tambahan","");
                String item = _spiner_krc_utm_wstwn_order.getItemAtPosition(_spiner_krc_utm_wstwn_order.getSelectedItemPosition()).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        _txt_jml_krcs_wisnu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        _txt_jml_krcs_wisman.addTextChangedListener(new TextWatcher() {
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

        _txt_jml_krcs_tmbhn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                CalculateKarcis();
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                CalculateKarcis();
            }
        });

    }


    public void CalculateKarcis(){
        final double _jml_krcs_wisnu = Help.ParseDouble(((EditText) findViewById(R.id.txt_jml_krcs_wisnu)).getText().toString());
        final double _jml_krcs_wisman = Help.ParseDouble(((EditText) findViewById(R.id.txt_jml_krcs_wisman)).getText().toString());
        final double _jml_krcs_tmbhn = Help.ParseDouble(((EditText) findViewById(R.id.txt_jml_krcs_wisman_tmbhn)).getText().toString());



        final double hrg_krcs_wisnu = Help.ParseDouble(Objects.requireNonNull(sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_harga_karcis_wisata_wisnu)));
        final double hrg_krcs_wisman = Help.ParseDouble(Objects.requireNonNull(sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_harga_karcis_wisata_wisman)));
        final double hrg_krcs_tmbhn = Help.ParseDouble(Objects.requireNonNull(sessionManager.getDaftarKarcisWisatawanTmbhn().get(SessionManager.key_harga_karcis_wisata_tmbhn)));
        final double hrg_krcs_asrnsi_wisnu = Help.ParseDouble(Objects.requireNonNull(sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_harga_karcis_asuransi_wisnu)));
        final double hrg_krcs_asrnsi_wisman = Help.ParseDouble(Objects.requireNonNull(sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_harga_karcis_asuransi_wisman)));

        Log.i("tag","_jml_krcs_tmbhn= "+ _jml_krcs_tmbhn);

        final int  ttl_wisnu = (int) ((hrg_krcs_wisnu+hrg_krcs_asrnsi_wisnu)*_jml_krcs_wisnu);
        final int   ttl_wisman =(int) ((hrg_krcs_wisman+hrg_krcs_asrnsi_wisman)*_jml_krcs_wisman);
        final int ttl_wisnu_wisman = (ttl_wisnu+ttl_wisman);
        final int ttl_tmbhn = (int) (hrg_krcs_tmbhn*_jml_krcs_tmbhn);
        final int grand_ttl =(int) (ttl_wisnu_wisman+ttl_tmbhn);

        _txt_ttl.setText(String.valueOf(ttl_wisnu_wisman));
        _txt_ttl_tmbhn.setText(String.valueOf(ttl_tmbhn));
        _txt_grand_ttl.setText(String.valueOf(grand_ttl));

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
        String server_url = "http://kaffah.amanahgitha.com/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(PesanKarcisWisatawanActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("triono", "response spinnerLokWisata===" + response );
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

                                        lokWisListKsda.add(new SpinnerListWisataKsda(kd_ksda,nm_obj_wisata));
                                    }
                                } else {

                                }
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(PesanKarcisWisatawanActivity.this);
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
                            _spinner_lok_wis.setAdapter(new ArrayAdapter<SpinnerListWisataKsda>(PesanKarcisWisatawanActivity.this, android.R.layout.simple_spinner_dropdown_item,lokWisListKsda) );
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
                final String ksda_val =  sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi);

                obj.put("lokasi", "27");
                return obj;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }


    private void spinnerLokWisataWisatawan(String EP,String KSDA){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://kaffah.amanahgitha.com/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(PesanKarcisWisatawanActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("triono", "response 1 ===" + response );
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

                                        lokWisList.add(new SpinnerListWisata(kd_lokasi,nm_obj_wisata));
                                    }
                                } else {

                                }
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(PesanKarcisWisatawanActivity.this);
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
                            _spiner_lok_wis_wstwn_order.setAdapter(new ArrayAdapter<SpinnerListWisata>(PesanKarcisWisatawanActivity.this, android.R.layout.simple_spinner_dropdown_item,lokWisList) );
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
                        final String ksda_val =  sessionManager.getDataLokWisPesankarcisWisatawanKsda().get(SessionManager.key_kd_ksda);
                        Log.i("","ksda_val pintu= "+ KSDA);
                        obj.put("kode_ksda", KSDA);
                        return obj;
                        }
                    };
           int socketTimeout = 30000;
           RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
           stringRequest.setRetryPolicy(policy);
           requestQueue.add(stringRequest);
        }


    private void spinnerKarcisWisatawanUtama(String EP,String LP){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://kaffah.amanahgitha.com/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(PesanKarcisWisatawanActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                            if( Help.isJSONValid(response) ){
                                JSONObject jsonObject = new JSONObject(response.toString());
                                Log.i("tag","success= " + jsonObject.getBoolean("success") );


                                _listKrcUtmWstwnOrder.clear();
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

                                        _listKrcUtmWstwnOrder.add(i, "Rp."+ (_harga_karcis_wisata_wisnu) + " - (" + _nama_karcis + ") ");
//                                        _listKrcUtmWstwnOrder.add(i, _nama_karcis );
                                    }
                                } else {
//                                    _listKrcUtmWstwnOrder.add(0, "-" );
                                }
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(PesanKarcisWisatawanActivity.this);
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
                            _spiner_krc_utm_wstwn_order.setAdapter(new ArrayAdapter<String>(PesanKarcisWisatawanActivity.this, android.R.layout.simple_spinner_item,_listKrcUtmWstwnOrder) );
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


                final String ksda_val =  sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi);
                final String tgl_kujungan_val = (String) ""+_txt_tgl_kunjungan_order.getText();

                Log.i("tag", "LP Spinner utama =" + LP );
                obj.put("lokasi", LP );
                obj.put("tgl_kunjungan",tgl_kujungan_val);
                return obj;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }


    private void spinnerKarcisWisatawanTambahan(String EP, String LP){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://kaffah.amanahgitha.com/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(PesanKarcisWisatawanActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                            if( Help.isJSONValid(response) ){
                                JSONObject jsonObject = new JSONObject(response.toString());
                                Log.i("tag","success= " + jsonObject.getBoolean("success") );

//                                _listKrcTmbhnWstwnOrder.clear();
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

                                        sessionManager.createSessionWisTmbhn(_kode_ksda, _kode_lokasi, _kode_karcis,
                                                _nama_karcis, _kode_libur, _harga_karcis_wisata, _harga_karcis_asuransi, _id );

                                        _listKrcTmbhnWstwnOrder.add(i, "Rp."+ (_harga_karcis_wisata) + " - (" + _nama_karcis + ") ");
                                    }
                                } else {    _listKrcUtmWstwnOrder.add(0, "-" );  }
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(PesanKarcisWisatawanActivity.this);
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
                            _spiner_krc_tmbhn_wstwn_order.setAdapter(new ArrayAdapter<String>(PesanKarcisWisatawanActivity.this, android.R.layout.simple_spinner_dropdown_item,_listKrcTmbhnWstwnOrder) );
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

                final String ksda_val =  sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi);
                final String tgl_kujungan_val = (String) ""+_txt_tgl_kunjungan_order.getText();

                Log.i("tag", "LP Spinner tambahan =" + LP );
                obj.put("lokasi", LP );
                obj.put("tgl_kunjungan",tgl_kujungan_val);
                return obj;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }



    private void pesanKarcisWisatawan(String EP){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://kaffah.amanahgitha.com/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(PesanKarcisWisatawanActivity.this);
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

                                        Intent i = new Intent(PesanKarcisWisatawanActivity.this, SuccessRegistrasiWisatawanActivity.class);
                                        i.putExtra("result_dt_ket", "Pemesanan Anda Berhasil Silahkan Cek email!");
                                        i.putExtra("result_dt_email", "");
                                        i.putExtra("result_dt_berhasil", berhasil);
                                        i.putExtra("result_dt_flag", "flagPesanKarcisWisatawan");
                                        startActivity(i);
//                                        finish();

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
                final String key_tgl_penjualan =  "2020-06-05";
                final String tgl_kunjungan =  _txt_tgl_kunjungan_order.getText().toString().trim();
                final String key_kode_lokasi =  (String) sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_kode_lokasi_wist_order);
                final String key_id_utama =  (String) sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_id_wist_order);
                final String key_id_tmbhn =  (String) sessionManager.getDaftarKarcisWisatawanTmbhn().get(SessionManager.key_id_tmbhn);
                final String jml_wisnu = _txt_jml_krcs_wisnu.getText().toString().trim();
                final String jml_wisman = _txt_jml_krcs_wisman.getText().toString().trim();
                final String jml_tmhn = _txt_jml_krcs_tmbhn.getText().toString().trim();
                flag_pemesan = (key_kode_ksda == null ? "1" : "2");


                Log.i("tag","key_kode_ksda= " + key_kode_ksda );
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
                Log.i("tag","jml_tmhn= " + jml_tmhn );
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
                obj.put("jumlah_tambahan",jml_tmhn);
                return obj;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }


}




