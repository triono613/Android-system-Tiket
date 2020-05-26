package com.example.aga;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

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
import com.example.aga.Model.SpinnerListWisata;
import com.example.aga.Model.SpinnerListWisataKsda;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class SetupPintuActivity extends AppCompatActivity {

    Spinner _spinner_lok_wis;
    Spinner _spiner_lok_pintu;
    Button _btn_setup_pintu;

    ArrayList<SpinnerListWisataKsda> lokWisListKsda = new ArrayList<SpinnerListWisataKsda>();
    ArrayList<SpinnerListWisata> lokPintuList = new ArrayList<SpinnerListWisata>();
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_pintu);
        sessionManager = new SessionManager(getApplicationContext());

        _spinner_lok_wis  = (Spinner) findViewById(R.id.spinner_lok_wisata_setup);
        _spiner_lok_pintu = (Spinner) findViewById(R.id.spinner_lok_pintu_setup);
        _btn_setup_pintu = (Button) findViewById(R.id.btn_setup_pintu);

        _spinner_lok_wis.setEnabled(false);
        spinnerLokWisata("daftar_lokasi_wisata");



        String compareValue = sessionManager.getDataSetupPintu().get(SessionManager.key_index);

//        if( sessionManager.getDataSetupPintu().get(SessionManager.key_index == ) ){
//
//        }

//        Log.i("","compareValue "+compareValue);
//        assert compareValue != null;
//        _spiner_lok_pintu.setSelection(Integer.parseInt(compareValue));



        _spinner_lok_wis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                SpinnerListWisataKsda item = (SpinnerListWisataKsda) parent.getItemAtPosition(position);
                sessionManager.createSessionLokWisPesankarcisWisatawanKsda(item.getKdksda(),item.getNmlok());
                Log.i("","item.getKdksda= "+item.getKdksda());
                spinnerLokWisataWisatawan("daftar_lokasi_pintu",item.getKdksda());

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

        _spiner_lok_pintu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                SpinnerListWisata item = (SpinnerListWisata) parent.getItemAtPosition(position);



                sessionManager.createSessionSetupPintu(position ,item.getKdlok(),item.getNmlok());

                Log.i("","item.getKdlok "+item.getKdlok());
                Log.i("","position "+ position );

//                spinnerKarcisWisatawanUtama("daftar_karcis_wisatawan_utama",item.getKdlok());
//                spinnerKarcisWisatawanTambahan("daftar_karcis_wisatawan_tambahan",item.getKdlok());

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
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("triono", "response spinnerLokWisata===" + response );
                        try {
                            findViewById(R.id.loadingPanel).setVisibility(View.GONE);

                            String nm_obj_wisata ;
                            String kd_ksda;
                            lokWisListKsda.clear();
                            if( Help.isJSONValid(response) ){
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
                            _spinner_lok_wis.setAdapter(new ArrayAdapter<SpinnerListWisataKsda>(SetupPintuActivity.this, android.R.layout.simple_spinner_dropdown_item,lokWisListKsda) );
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

                obj.put("lokasi", "");
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
        final RequestQueue requestQueue = Volley.newRequestQueue(SetupPintuActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
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

                                        lokPintuList.add(new SpinnerListWisata(kd_lokasi,nm_obj_wisata));
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
                            _spiner_lok_pintu.setAdapter(new ArrayAdapter<SpinnerListWisata>(SetupPintuActivity.this, android.R.layout.simple_spinner_dropdown_item,lokPintuList) );
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



    private void sendData(String EP,String KSDA){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://kaffah.amanahgitha.com/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(SetupPintuActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("triono", "response 1 ===" + response );
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
                                        flag = jsonObject1.getString("flag");
                                        alamat_email = jsonObject1.getString("alamat_email");
                                        nama_pintu = jsonObject1.getString("nama_pintu");



                                        Log.i("","senddata kode_ksda= "+kode_ksda);

                                        final long index= _spiner_lok_pintu.getSelectedItemId();

                                        Log.i("","index= "+ index);

                                        sessionManager.createSessionSetupPintu((int) index,kode_ksda,nama_pintu);

                                        Intent ii = new Intent(SetupPintuActivity.this, SuccessRegistrasiWisatawanActivity.class);
                                        ii.putExtra("result_dt_ket", keterangan);
                                        ii.putExtra("result_dt_email", alamat_email);
                                        ii.putExtra("result_dt_flag", flag);
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
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> obj = new HashMap<String, String>();
                final String key_kode_lokasi =  sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi);
                final String key_email =  sessionManager.getUserDetail().get(SessionManager.key_email);
                final String key_ksda = sessionManager.getDataSetupPintu().get(SessionManager.key_kd_ksda);


                Log.i("","sendData key_email= "+ key_email);
                Log.i("","sendData key_kode_lokasi= "+ key_kode_lokasi);
                Log.i("","sendData key_ksda= "+ key_ksda);


                obj.put("alamat_email",key_email);
                obj.put("kode_lokasi", key_kode_lokasi);
                obj.put("kode_ksda", key_ksda);
                return obj;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }




}
