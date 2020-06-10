package com.example.aga;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

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
import com.example.aga.Model.SpinnerJnsByrEksp;
import com.example.aga.Model.SpinnerListWisataEksp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EditKarcisStatusWisatawanActivity extends AppCompatActivity {

    SessionManager sessionManager;
    TextView _jml_krcs_wisnu_wstwn_eks;
    TextView _jml_krcs_wisman_wstwn_eks;
    TextView _ttl_wstwn_eks;
    TextView _jml_krcs_wisman_tmbhn_wstwn_eks;
    TextView _ttl_tmbhn_wstwn_eks;
    TextView _grand_ttl_wstwn_eks;
    TextView _email_pengunjung_wstwn_eks;
    TextView _karcis_utama_wstwn_eks;
    TextView _karcis_tmbhn_wstwn_eks;
    TextView _phone_wstwn_eks;
    TextView _text_kd_pintu_eksp;
    TextView _text_jns_byr_eksp;

    Spinner _spinner_lok_pintu_wstwn_eks;
    Spinner _spinner_jns_byr_wstwn_eks;
    Button _btn_order_wstwn_eks;

    ArrayList<SpinnerListWisataEksp> arrListWistEksp = new ArrayList<SpinnerListWisataEksp>();
    ArrayList<SpinnerJnsByrEksp> arrJnsByrEksp = new ArrayList<SpinnerJnsByrEksp>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_karcis_status_wisatawan);

        sessionManager = new SessionManager(getApplicationContext());
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        final String _va = getIntent().getStringExtra("result_va");

        Log.i("","_va=="+ _va);

        _jml_krcs_wisnu_wstwn_eks = (TextView) findViewById(R.id.jml_krcs_wisnu_wstwn_eks);
        _jml_krcs_wisman_wstwn_eks = (TextView) findViewById(R.id.jml_krcs_wisman_wstwn_eks);
        _ttl_wstwn_eks = (TextView) findViewById(R.id.ttl_wstwn_eks);
        _jml_krcs_wisman_tmbhn_wstwn_eks = (TextView) findViewById(R.id.jml_krcs_wisman_tmbhn_wstwn_eks);
        _ttl_tmbhn_wstwn_eks = (TextView) findViewById(R.id.ttl_tmbhn_wstwn_eks);
        _grand_ttl_wstwn_eks = (TextView) findViewById(R.id.grand_ttl_wstwn_eks);
        _email_pengunjung_wstwn_eks = (TextView) findViewById(R.id.email_pengunjung_wstwn_eks);
        _karcis_utama_wstwn_eks = (TextView) findViewById(R.id.karcis_utama_wstwn_eks);
        _karcis_tmbhn_wstwn_eks = (TextView) findViewById(R.id.karcis_tmbhn_wstwn_eks);
        _phone_wstwn_eks = (TextView) findViewById(R.id.phone_wstwn_eks);
        _text_kd_pintu_eksp = (TextView) findViewById(R.id.text_kd_pintu_eksp);
        _text_jns_byr_eksp = (TextView) findViewById(R.id.text_jns_byr_eksp);


        _spinner_lok_pintu_wstwn_eks = (Spinner) findViewById(R.id.spinner_lok_pintu_wstwn_eks);
        _spinner_jns_byr_wstwn_eks = (Spinner) findViewById(R.id.spinner_jns_byr_wstwn_eks);
        _btn_order_wstwn_eks = (Button) findViewById(R.id.btn_order_wstwn_eks);

//        _spinner_lok_pintu_wstwn_eks.setEnabled(false);

//        arrListWistEksp.clear();

        _jml_krcs_wisnu_wstwn_eks.setEnabled(false);
        _jml_krcs_wisman_wstwn_eks.setEnabled(false);
        _ttl_wstwn_eks.setEnabled(false);
        _jml_krcs_wisman_tmbhn_wstwn_eks.setEnabled(false);
        _ttl_tmbhn_wstwn_eks.setEnabled(false);
        _grand_ttl_wstwn_eks.setEnabled(false);
        _email_pengunjung_wstwn_eks.setEnabled(false);
        _karcis_utama_wstwn_eks.setEnabled(false);
        _karcis_tmbhn_wstwn_eks.setEnabled(false);
        _phone_wstwn_eks.setEnabled(false);

        final String _kdksda = sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi);

        getDataNova("edit_cari_no_va",_va);

//        spinnerLokWisata("daftar_lokasi_pintu",_kdksda );
//        spinnerJnsByrwstwn("informasi_mode_pembayaran","");


        _btn_order_wstwn_eks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( sessionManager.isLoggedIn()) {

                    changeVa("no_va_diubah",_va);

                }

            }
        });

        _spinner_lok_pintu_wstwn_eks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                SpinnerListWisataEksp item = (SpinnerListWisataEksp) parent.getItemAtPosition(position);
                sessionManager = new SessionManager(getApplicationContext());
                sessionManager.createSessionEksp(item.getKdlok_eksp(),item.getNmlok_eksp());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                _spinner_lok_pintu_wstwn_eks.setSelected(true);
            }
        });

        _spinner_jns_byr_wstwn_eks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerJnsByrEksp item = (SpinnerJnsByrEksp) parent.getItemAtPosition(position);
                sessionManager = new SessionManager(getApplicationContext());
                sessionManager.createSessionJnsByrEksp(item.getMode_pembayaran_eksp(),item.getNama_pembayaraan_eksp());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    public void changeVa(String EP,String va){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://kaffah.amanahgitha.com/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(EditKarcisStatusWisatawanActivity.this);
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
                                boolean berhasil = jsonObject1.getBoolean("berhasil");
                                boolean success = jObj.getBoolean("success");

                                Log.i("tag","success= " + jObj.getBoolean("success") );
                                Log.i("tag","data= " + data );
                                Log.i("tag","berhasil= " + berhasil );


                                final String _id = jsonObject1.getString("id");
                                final String _sellular_no = jsonObject1.getString("sellular_no");
                                final String _alamat_email = jsonObject1.getString("alamat_email");
                                final String _nama = jsonObject1.getString("nama");
                                final String _keterangan = jsonObject1.getString("keterangan");


                                if( success ) {
                                    if( berhasil ) {
                                        Log.i(""," berhasil");
                                        Intent i = new Intent(EditKarcisStatusWisatawanActivity.this, SuccessRegistrasiWisatawanActivity.class);
                                        i.putExtra("result_dt_ket", _keterangan);
                                        i.putExtra("result_dt_email", _alamat_email);
                                        i.putExtra("result_dt_berhasil", berhasil);
                                        i.putExtra("result_dt_flag", "flagEditKarcisStatusPetugasTrue");
                                        startActivity(i);
                                    } else {
                                        Log.i("","! berhasil");
                                        Intent i = new Intent(EditKarcisStatusWisatawanActivity.this, SuccessRegistrasiWisatawanActivity.class);
                                        i.putExtra("result_dt_ket", _keterangan);
                                        i.putExtra("result_dt_email", _alamat_email);
                                        i.putExtra("result_dt_berhasil", berhasil);
                                        i.putExtra("result_dt_flag", "flagEditKarcisStatusPetugasFalse");
                                        startActivity(i);
                                    }
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

                final String kd_pintu = (String) sessionManager.getDataLokEksp().get(SessionManager.key_kd_lok_eksp);
                final String mode_byr = (String) sessionManager.getDataJnsByrEksp().get(SessionManager.key_mode_pembayaran_eksp);

                Log.i("tag","va= " + va );
                Log.i("tag","mode_byr= " + mode_byr );
                Log.i("tag","kd_pintu= " + kd_pintu );

                obj.put("no_va",va);
                obj.put("payment_method", mode_byr);
                obj.put("kode_pintu",kd_pintu);
                return obj;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    private void getDataNova(String EP,String NOVA){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://kaffah.amanahgitha.com/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(EditKarcisStatusWisatawanActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("tag", "response getDataNova =" + response );
                        try {

                            if( Help.isJSONValid(response) ){
                                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                                JSONObject jsonObject = new JSONObject(response);
                                JSONObject jo = new JSONObject(response);
                                boolean sukses = jo.getBoolean("success");
                                String data = jo.getString("data");
                                JSONObject joChild = new JSONObject(data);

                                String kode_karcis_utama;
                                String nama_karcis_utama;
                                String kode_karcis_tmbhn;
                                String nama_karcis_tmbhn;
                                String jumlah_karcis_wisnu;
                                String jumlah_karcis_wisman;
                                String total_tagihan_wisnu_wisman;
                                String jumlah_karcis_tambahan;
                                String tagihan_karcis_tambahan;
                                String tagihan_total;
                                String jenis_bayar;
                                String sellular_no;
                                String alamat_email;
                                String kode_pintu;


                                if( sukses ) {
//                                    spinnerSearchJnsClaim("cari_jenis_klaim_by_no_va",NOVA);
                                    kode_karcis_utama = joChild.getString("kode_karcis_utama");
                                    nama_karcis_utama = joChild.getString("nama_karcis_utama");
                                    kode_karcis_tmbhn = joChild.getString("kode_karcis_tambahan");
                                    nama_karcis_tmbhn = joChild.getString("nama_karcis_tambahan");
                                    jumlah_karcis_wisnu = joChild.getString("jumlah_karcis_wisnu");
                                    jumlah_karcis_wisman = joChild.getString("jumlah_karcis_wisman");
                                    total_tagihan_wisnu_wisman = joChild.getString("total_tagihan_wisnu_wisman");
                                    jumlah_karcis_tambahan = joChild.getString("jumlah_karcis_tambahan");
                                    tagihan_karcis_tambahan = joChild.getString("tagihan_karcis_tambahan");
                                    tagihan_total = joChild.getString("tagihan_total");
                                    kode_pintu = joChild.getString("kode_pintu");
                                    jenis_bayar = joChild.getString("jenis_bayar");
                                    sellular_no = joChild.getString("sellular_no");
                                    alamat_email = joChild.getString("alamat_email");

                                    _karcis_utama_wstwn_eks.setText(nama_karcis_utama);
                                    _karcis_tmbhn_wstwn_eks.setText(nama_karcis_tmbhn);
                                    _jml_krcs_wisnu_wstwn_eks.setText(jumlah_karcis_wisnu);
                                    _jml_krcs_wisman_wstwn_eks.setText(jumlah_karcis_wisman);
                                    _jml_krcs_wisman_tmbhn_wstwn_eks.setText(jumlah_karcis_tambahan);
                                    _ttl_wstwn_eks.setText(total_tagihan_wisnu_wisman);
                                    _ttl_tmbhn_wstwn_eks.setText(tagihan_karcis_tambahan);
                                    _grand_ttl_wstwn_eks.setText(tagihan_total);
                                    _phone_wstwn_eks.setText(sellular_no);
                                    _email_pengunjung_wstwn_eks.setText(alamat_email);
                                    _text_kd_pintu_eksp.setText(kode_pintu);
                                    _text_jns_byr_eksp.setText(jenis_bayar);

                                    sessionManager.createSessionSpinnerEksp(kode_pintu,jenis_bayar);
//                                    sessionManager.createSessionSpinnerEksp("27002",jenis_bayar);

                                    String nm_lokasi = null;
                                    if(kode_pintu.equals("27001")){
                                        nm_lokasi = "TWA Testing";
                                    }
                                    if(kode_pintu.equals("27002")){
                                        nm_lokasi = "TWA Testing - Pintu 2";
                                    }
                                    if(kode_pintu.equals("28001")){
                                        nm_lokasi = "WW Testing - Pintu 1";
                                    }
                                    if(kode_pintu.equals("28002")){
                                        nm_lokasi = "WW Testing - Pintu 2";
                                    }

                                String nama_pembayaran = null;
                                switch (Objects.requireNonNull(jenis_bayar))
                                        {
                                            case "1":
                                                nama_pembayaran = "Cash/Tunai";
                                                break;
                                            case "2":
                                                nama_pembayaran = "Virtual Account";
                                                break;
                                            case "3":
                                                nama_pembayaran = "Transfer Bank";
                                                break;
                                            default:
                                                break;
                                        }
                                    arrListWistEksp.add(new SpinnerListWisataEksp(kode_pintu,nm_lokasi));
                                    arrJnsByrEksp.add(new SpinnerJnsByrEksp(jenis_bayar,nama_pembayaran));

                                }
                            }
//                            _spinner_jns_klaim_wstwn.setAdapter(new ArrayAdapter<SpinnerJnsClaim>(EditKarcisStatusActivity.this, android.R.layout.simple_spinner_dropdown_item, arrJnsClaim) );
                            _spinner_lok_pintu_wstwn_eks.setAdapter(new ArrayAdapter<SpinnerListWisataEksp>(EditKarcisStatusWisatawanActivity.this, android.R.layout.simple_spinner_dropdown_item,arrListWistEksp) );
                            _spinner_jns_byr_wstwn_eks.setAdapter(new ArrayAdapter<SpinnerJnsByrEksp>(EditKarcisStatusWisatawanActivity.this, android.R.layout.simple_spinner_dropdown_item, arrJnsByrEksp) );
                        } catch (JSONException e) {
                            Log.i("triono", "error ===" + e.toString() );
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
//                final String _nova = _no_va.getText().toString();
//                final String _nova = "8820122000000084";
                Log.i("","NOVA= "+ NOVA);
                obj.put("no_va", NOVA);
                return obj;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }


    private void spinnerLokWisata(String EP,String KSDA){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://kaffah.amanahgitha.com/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(EditKarcisStatusWisatawanActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("triono", "response 1 ===" + response );
                        try {

                            if( Help.isJSONValid(response) ){
//                                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                                JSONObject jsonObject = new JSONObject(response.toString());
                                String nm_obj_wisata;
                                String kd_lokasi;
                                arrListWistEksp.clear();

                                if( jsonObject.getBoolean("success") ) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                                    for (int i = 0; i <jsonArray.length();i++ ) {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                        nm_obj_wisata = jsonObject1.getString("nama");
                                        kd_lokasi = jsonObject1.getString("kode_lokasi");

                                        Log.i("","kd_lokasi= "+kd_lokasi);
                                        Log.i("","nm_obj_wisata= "+nm_obj_wisata);
                                        Log.i("","arrListWistEksp.size()= "+ arrListWistEksp.size());

//                                        kd_lokasi = "27002";
                                        arrListWistEksp.add(new SpinnerListWisataEksp(kd_lokasi,nm_obj_wisata));

                                    }
                                } else {

                                }
                            }
                            _spinner_lok_pintu_wstwn_eks.setAdapter(new ArrayAdapter<SpinnerListWisataEksp>(EditKarcisStatusWisatawanActivity.this, android.R.layout.simple_spinner_dropdown_item,arrListWistEksp) );

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
                Log.i("","ksda= "+ KSDA);
                obj.put("kode_ksda", KSDA);
                return obj;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    private void spinnerJnsByrwstwn(String EP,String KL){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://kaffah.amanahgitha.com/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(EditKarcisStatusWisatawanActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("tag", "response spinnerLokPintuwstwn =" + response );
                        try {
                            if( Help.isJSONValid(response) ){
                                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                                JSONObject jsonObject = new JSONObject(response);

                                String mode_pembayaran;
                                String nama_pembayaran;
                                arrJnsByrEksp.clear();

                                if( jsonObject.getBoolean("success") ) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                                    for (int i = 0; i <jsonArray.length();i++ ) {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                        mode_pembayaran = jsonObject1.getString("mode_pembayaran");
                                        nama_pembayaran = jsonObject1.getString("nama_pembayaran");

                                        Log.i("","mode_pembayaran= "+mode_pembayaran);
                                        Log.i("","nama_pembayaran= "+nama_pembayaran);

                                        arrJnsByrEksp.add(new SpinnerJnsByrEksp(mode_pembayaran,nama_pembayaran));
                                    }
                                }
                            }
                            _spinner_jns_byr_wstwn_eks.setAdapter(new ArrayAdapter<SpinnerJnsByrEksp>(EditKarcisStatusWisatawanActivity.this, android.R.layout.simple_spinner_dropdown_item, arrJnsByrEksp) );

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



}