package com.amanahgithawisata.aga;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.amanahgithawisata.aga.Adapter.SessionManager;
import com.amanahgithawisata.aga.Helper.Help;
import com.amanahgithawisata.aga.Model.SpinnerListWisata;
import com.amanahgithawisata.aga.Model.SpinnerListWisataKsdaPetugas;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class SetupPintuActivity extends AppCompatActivity {

    Spinner _spinner_lok_wisata_setup;
    Spinner _spinner_lok_pintu_setup;
    EditText _text_jml_pengunjung;
    EditText _text_jml_berkunjung;
    EditText _text_jml_semua;
    Button _btn_setup_pintu;

    ArrayList<SpinnerListWisataKsdaPetugas> lokWisListKsda = new ArrayList<SpinnerListWisataKsdaPetugas>();
    ArrayList<SpinnerListWisata> lokPintuList = new ArrayList<>();
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_pintu);
        sessionManager = new SessionManager(getApplicationContext());

        _spinner_lok_wisata_setup  = (Spinner) findViewById(R.id.spinner_lok_wisata_setup);
        _spinner_lok_pintu_setup = (Spinner) findViewById(R.id.spinner_lok_pintu_setup);
        _btn_setup_pintu = (Button) findViewById(R.id.btn_setup_pintu);
        _text_jml_pengunjung = (EditText) findViewById(R.id.text_jml_pengunjung);
        _text_jml_berkunjung = (EditText) findViewById(R.id.text_jml_berkunjung);
        _text_jml_semua = (EditText) findViewById(R.id.text_jml_semua);

        _text_jml_pengunjung.setEnabled(false);
        _text_jml_berkunjung.setEnabled(false);
        _text_jml_semua.setEnabled(false);
        _spinner_lok_pintu_setup.setEnabled(false);


        spinnerLokWisata("petugas_daftar_lokasi_wisata");
//        spinnerLokWisata("daftar_lokasi_wisata");

        String compareValue = sessionManager.getDataSetupPintu().get(SessionManager.key_index);


        _spinner_lok_wisata_setup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                /* set.Selection for index spinner must be set in here ..okay.. */

                SpinnerListWisataKsdaPetugas item = (SpinnerListWisataKsdaPetugas) parent.getItemAtPosition(position);
                sessionManager.createSessionLokWisPesankarcisWisatawanKsda(item.getKdksda(),item.getNmlok());
                Log.i("","item.getKdksda= "+item.getKdksda());
//                spinnerLokPintu("daftar_lokasi_pintu",item.getKdksda());

//                _spinner_lok_wisata_setup.setSelection(1);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        _btn_setup_pintu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData("setup_pintu","");
            }
        });

        _spinner_lok_pintu_setup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerListWisata item = (SpinnerListWisata) parent.getItemAtPosition(position);
                Log.e("","item.getKdlok() "+item.getKdlok());

                sessionManager.createSessionSetupPintu(position ,item.getKdlok(),item.getNmlok());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }



    private void spinnerLokWisata(String EP){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://kaffah.amanahgitha.com/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(SetupPintuActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                response -> {
                    Log.i("", "response spinnerLokWisata===" + response );
                    try {
                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        String nama ;
                        String kode_ksda;
                        String ttl_pengunjung;
                        String ttl_berkunjung;
                        String ttl_berkunjung_semua;

                        lokWisListKsda.clear();
                        if( Help.isJSONValid(response) ){
                            JSONObject jsonObject = new JSONObject(response);

                            if( jsonObject.getBoolean("success") ) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                int has =  jsonArray.length();

                                    for (int i = 0; i <jsonArray.length();i++ ) {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                        kode_ksda = jsonObject1.getString("kode_ksda");
                                        nama = jsonObject1.getString("nama");
                                        ttl_pengunjung = jsonObject1.getString("total_pengunjung");
                                        ttl_berkunjung = jsonObject1.getString("total_berkunjung");
                                        ttl_berkunjung_semua = jsonObject1.getString("total_semua");

                                        String id  = jsonObject1.getString("id");
                                        String alamat  = jsonObject1.getString("alamat");
                                        String kota  = jsonObject1.getString("kota");
                                        String email1  = jsonObject1.getString("email1");
                                        String email2  = jsonObject1.getString("email2");
                                        String email3  = jsonObject1.getString("email3");
                                        String android_flag  = jsonObject1.getString("android_flag");
                                        String detail_flag  = jsonObject1.getString("detail_flag");
                                        String lokasi_pintu  = jsonObject1.getString("lokasi_pintu");
                                        String nama_pintu  = jsonObject1.getString("nama_pintu");

                                        lokPintuList.add(new SpinnerListWisata(lokasi_pintu,nama_pintu,"","",""));

                                        Log.i("tag","kode_ksda= "+kode_ksda);
                                        Log.i("tag","nama= "+nama);
                                        Log.i("tag","ttl_pengunjung= "+ttl_pengunjung);
                                        Log.i("tag","ttl_berkunjung= "+ttl_berkunjung);
                                        Log.i("tag","ttl_berkunjung_semua= "+ttl_berkunjung_semua);
                                        lokWisListKsda.add(new SpinnerListWisataKsdaPetugas(kode_ksda,nama,ttl_pengunjung,ttl_berkunjung,
                                                                                    ttl_berkunjung_semua));

                                        _text_jml_pengunjung.setText(ttl_pengunjung.trim());
                                        _text_jml_berkunjung.setText(ttl_berkunjung.trim());
                                        _text_jml_semua.setText(ttl_berkunjung_semua.trim());

                                    }
                                    _spinner_lok_wisata_setup.setEnabled(false);
                            }

                            _spinner_lok_wisata_setup.setAdapter(new ArrayAdapter<>(SetupPintuActivity.this, android.R.layout.simple_spinner_dropdown_item, lokWisListKsda) );
                            _spinner_lok_pintu_setup.setAdapter(new ArrayAdapter<>(SetupPintuActivity.this, android.R.layout.simple_spinner_dropdown_item, lokPintuList) );
                        }
                    } catch (JSONException e) {
                        Log.i("triono", "error ===" + e.toString() );
                        e.printStackTrace();
                    }
                    requestQueue.stop();
                }, error -> {
                    Log.i("triono", "response =" + error.toString());
                    error.printStackTrace();
                    requestQueue.stop();

                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> obj = new HashMap<String, String>();
                final String key_email =  sessionManager.getUserDetail().get(SessionManager.key_email);
                obj.put("alamat_email", key_email.trim());
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }



    private void spinnerLokPintu(String EP,String KSDA){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://kaffah.amanahgitha.com/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(SetupPintuActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                response -> {
                    Log.i("triono", "response 1 ===" + response );
                    try {
                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        if( Help.isJSONValid(response) ){
                            JSONObject jsonObject = new JSONObject(response.toString());

                            String nm_obj_wisata;
                            String kd_lokasi;
                            lokPintuList.clear();

                            if( jsonObject.getBoolean("success") ) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i <jsonArray.length();i++ ) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    nm_obj_wisata = jsonObject1.getString("nama");
                                    kd_lokasi = jsonObject1.getString("kode_lokasi");

                                    Log.i("","kd_lokasi= "+kd_lokasi);
                                    Log.i("","nm_obj_wisata= "+nm_obj_wisata);

//                                    lokPintuList.add(new SpinnerListWisata(kd_lokasi,nm_obj_wisata,"","",""));

                                }
                            } else {

                            }
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(SetupPintuActivity.this);
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
//                        lokPintuList.add(new SpinnerListWisata("","","","",""));
                        _spinner_lok_pintu_setup.setAdapter(new ArrayAdapter<>(SetupPintuActivity.this, android.R.layout.simple_spinner_dropdown_item, lokPintuList) );
                    } catch (JSONException e) {
                        Log.i("triono", "error ===" + e.toString() );
                        e.printStackTrace();
                    }
                    requestQueue.stop();
                }, error -> {
                    Log.i("triono", "response =" + error.toString());
                    error.printStackTrace();
                    requestQueue.stop();
                    findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                    AlertDialog.Builder builder = new AlertDialog.Builder(SetupPintuActivity.this);
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

                Log.i("","ksda_val pintu= "+ KSDA);
                obj.put("kode_ksda", KSDA.trim());
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }



    private void sendData(String EP,String KSDA){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://kaffah.amanahgitha.com/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(SetupPintuActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("", "response sendData =" + response );
                        try {
                            findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                            if( Help.isJSONValid(response) ){
                                JSONObject jsonObject = new JSONObject(response.toString());

                                String full_name;
                                String kode_ksda;
                                String keterangan;
                                String flag;
                                String alamat_email;
                                String nama_pintu;

                                if( jsonObject.getBoolean("success") ) {

                                    String data = jsonObject.getString("data");
                                    JSONObject jsonObject1 = new JSONObject(data);

                                    full_name = jsonObject1.getString("full_name");
                                    kode_ksda = jsonObject1.getString("kode_ksda");
                                    keterangan = jsonObject1.getString("keterangan");
//                                        flag = jsonObject1.getString("flag");
                                    alamat_email = jsonObject1.getString("alamat_email");
                                    nama_pintu = jsonObject1.getString("nama_pintu");


                                    final long index= _spinner_lok_pintu_setup.getSelectedItemId();
                                    Log.i("","index= "+ index);

                                    sessionManager.createSessionSetupPintu((int) index,kode_ksda,nama_pintu);

                                    Intent ii = new Intent(SetupPintuActivity.this, SuccessRegistrasiWisatawanActivity.class);
                                    ii.putExtra("result_dt_ket", keterangan);
                                    ii.putExtra("result_dt_email", alamat_email);
                                    ii.putExtra("result_dt_flag", "flagSetupPintu");
                                    ii.putExtra("result_dt_berhasil", true);
                                    startActivity(ii);

                                } else {

                                }
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SetupPintuActivity.this);
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
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                AlertDialog.Builder builder = new AlertDialog.Builder(SetupPintuActivity.this);
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
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> obj = new HashMap<String, String>();
                final String key_kode_lokasi =  sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi);
                final String key_email =  sessionManager.getUserDetail().get(SessionManager.key_email);
                final String key_ksda = sessionManager.getDataSetupPintu().get(SessionManager.key_kd_ksda);

                Log.i("","sendData alamat_email= "+ key_email);
                Log.i("","sendData kode_lokasi= "+ key_kode_lokasi);
                Log.i("","sendData key_ksda= "+ key_ksda);

//                obj.put("alamat_email",key_email);
//                obj.put("kode_lokasi", key_kode_lokasi);
//                obj.put("kode_ksda", key_ksda);
//                getDataSetupPintu

                obj.put("alamat_email",key_email.trim());
                obj.put("kode_lokasi", key_ksda.trim());
                obj.put("kode_ksda", key_kode_lokasi.trim());
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }




}
