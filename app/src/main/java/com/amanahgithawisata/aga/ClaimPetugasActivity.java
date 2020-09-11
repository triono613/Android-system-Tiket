package com.amanahgithawisata.aga;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.amanahgithawisata.aga.Adapter.DatePickerPesanKarcisWstwn;
import com.amanahgithawisata.aga.Adapter.SessionManager;
import com.amanahgithawisata.aga.Helper.Help;
import com.amanahgithawisata.aga.Model.SpinnerJnsClaim;
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
import java.util.Locale;
import java.util.Map;

public class ClaimPetugasActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    //AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    SessionManager sessionManager;
    Button _btn_find_va;
    Button _btn_order_claim_ptgs;
    TextView _no_va;
    TextView _atas_nama;
    TextView _text_contact_pelapor;
    TextView _text_nama_pengunjung;
    TextView _text_contact_pengunjung;
    Spinner _spinner_jns_klaim_ptgs;
    TextView _text_nom_claim;
    TextView _text_ket_claim;
    TextView _tgl_kejadian;
    TextView _text_lokasi_kejadian;

    ArrayList<SpinnerJnsClaim> arrJnsClaim = new ArrayList<SpinnerJnsClaim>();



    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        TextView _tgl_kejadian = (TextView) findViewById(R.id.tgl_kejadian);

//        _txt_tgl_kunjungan_order.clearComposingText();

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        _tgl_kejadian.setText(sdf.format(c.getTime()));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim_petugas);

        sessionManager = new SessionManager(getApplicationContext());

        findViewById(R.id.loadingPanel).setVisibility(View.GONE);

//        i.putExtra("result_dt_flag", "fromDashboardPetugas");


        _no_va = (TextView) findViewById(R.id.no_va);
        _atas_nama =(TextView) findViewById(R.id.text_atas_nama);
        _text_contact_pelapor=(TextView) findViewById(R.id.text_contact_pelapor);
        _text_nama_pengunjung=(TextView) findViewById(R.id.text_nama_pengunjung);
        _text_contact_pengunjung=(TextView) findViewById(R.id.text_contact_pengunjung);
        _spinner_jns_klaim_ptgs = (Spinner) findViewById(R.id.spinner_jns_klaim_ptgs);
        _text_nom_claim = (TextView) findViewById(R.id.text_nom_claim);
        _text_ket_claim = (TextView) findViewById(R.id.text_ket_claim);
        _tgl_kejadian = (TextView) findViewById(R.id.tgl_kejadian);
        _text_lokasi_kejadian= (TextView) findViewById(R.id.text_lokasi_kejadian);
        _btn_find_va = (Button) findViewById(R.id.btn_find_va);
        _btn_order_claim_ptgs = (Button) findViewById(R.id.btn_order_claim_ptgs);

        String _va = "";
        String _flag = "";
        _va = getIntent().getStringExtra("result_va");
        _flag = getIntent().getStringExtra("result_dt_flag");


        Log.i("","_vax="+_va);
        Log.i("","_flagx="+_flag);

        assert _flag != null;
        String _xx = null;
        if(_va == null && _flag.equals("fromDashboardPetugas")) {
            _xx = "0";
//            Toast.makeText(getApplicationContext(), "toast _va null",Toast.LENGTH_LONG).show();
        }else {
            assert _va != null;
            _xx= _va.trim();
            _no_va.setText(_xx.trim());
//            Toast.makeText(getApplicationContext(), "toast _va terisi",Toast.LENGTH_LONG).show();
        }
        Log.i("","_xx="+_xx);




        _btn_find_va.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String nova_val = _no_va.getText().toString();

                Log.i("","nova_val "+nova_val);
                    if(TextUtils.isEmpty(nova_val) ) {
                        _no_va.setError("No VA Masih Kosong!");
                        _no_va.setFocusable(true);
                    } else {
                        getDataNova("cari_no_va",nova_val);
                    }
            }
        });

        _tgl_kejadian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DialogFragment datepicker = new DatePickerPesanKarcisWstwn();
//                datepicker.show(getSupportFragmentManager());

                DialogFragment datePicker = new DatePickerPesanKarcisWstwn();
                datePicker.show(getSupportFragmentManager(),"date picker Claim petugas");

            }
        });

        _btn_order_claim_ptgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                final String no_va = _no_va.getText().toString();
                final String atas_nama = _atas_nama.getText().toString();
                final String text_contact_pelapor = _text_contact_pelapor.getText().toString();
                final String text_nama_pengunjung = _text_nama_pengunjung.getText().toString();
                final String text_contact_pengunjung = _text_contact_pengunjung.getText().toString();
                final String text_nom_claim = _text_nom_claim.getText().toString();
                final String text_ket_claim  = _text_ket_claim.getText().toString();
                final String tgl_kejadian  = _tgl_kejadian.getText().toString();
                final String text_lokasi_kejadian  = _text_lokasi_kejadian.getText().toString();



                if(TextUtils.isEmpty(no_va) ) {
                    _no_va.setError("No va Masih Kosong!");
                }
                else if(TextUtils.isEmpty(atas_nama) ) {
                    _atas_nama.setError("Atas Nama Masih Kosong!");
                }
//                if  (!EMAIL_ADDRESS_PATTERN.matcher(email_val).matches() ) {
//                    ptgsEmail.setError("Alamat Email Invalid!");
//                }

                else if(TextUtils.isEmpty(text_contact_pelapor) ) {
                    _text_contact_pelapor.setError("Kontak Pelapor Masih Kosong!");
                }
                else if(TextUtils.isEmpty(text_nama_pengunjung) ) {
                    _text_nama_pengunjung.setError("Nama Pengunjung Masih Kosong!");
                }
                else if(TextUtils.isEmpty(text_contact_pengunjung) ) {
                    _text_contact_pengunjung.setError("Kontak Pengunjung Masih Kosong ");
                }
                else if(TextUtils.isEmpty(text_nom_claim) ) {
                    _text_nom_claim.setError("Nominal Klaim Masih Kosong!");
                }
                else if(TextUtils.isEmpty(text_ket_claim) ) {
                    _text_ket_claim.setError("Nominal Klaim Masih Kosong!");
                }
                else if(TextUtils.isEmpty(tgl_kejadian) ) {
                    _tgl_kejadian.setError("Tgl Kejadian Masih Kosong!");
                }
                else if(TextUtils.isEmpty(text_lokasi_kejadian)){
                    _text_lokasi_kejadian.setError("Lokasi Kejadian Masih Kosong!");
                }
                else {
                    sendData("input_data_klaim","");
                }
            }
        });

        _spinner_jns_klaim_ptgs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerJnsClaim item = (SpinnerJnsClaim) parent.getItemAtPosition(position);
                sessionManager.createSessionJnsClaim(String.valueOf(position) ,item.getJenis_claim(),item.getNama_claim());

                Log.i(""," position "+ position);
                Log.i("","getJenis_claim() "+item.getJenis_claim());
                Log.i("","getNama_claim() "+item.getNama_claim());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    private void getDataNova(String EP,String NOVA){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://kaffah.amanahgitha.com/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(ClaimPetugasActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("tag", "response getDataNova =" + response );
                        try {
                            findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                            if( Help.isJSONValid(response) ){
                                JSONObject jsonObject = new JSONObject(response);
                                JSONObject jo = new JSONObject(response);
                                boolean sukses = jo.getBoolean("success");
                                String data = jo.getString("data");
                                JSONObject joChild = new JSONObject(data);

                                String nama;
                                String sellular_no;
                                String alamat_email;
                                arrJnsClaim.clear();

                                if( sukses ) {
                                    spinnerSearchJnsClaim("cari_jenis_klaim_by_no_va",NOVA);
                                    nama = joChild.getString("nama");
                                    sellular_no = joChild.getString("sellular_no");
                                    alamat_email = joChild.getString("alamat_email");
                                        Log.i("","nama= "+nama);
                                        Log.i("","sellular_no= "+sellular_no);
                                        Log.i("","alamat_email= "+alamat_email);
                                        _atas_nama.setText(nama.toUpperCase());
                                } else {
                                }
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ClaimPetugasActivity.this);
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
                            _spinner_jns_klaim_ptgs.setAdapter(new ArrayAdapter<SpinnerJnsClaim>(ClaimPetugasActivity.this, android.R.layout.simple_spinner_dropdown_item, arrJnsClaim) );
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
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                AlertDialog.Builder builder = new AlertDialog.Builder(ClaimPetugasActivity.this);
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
//                final String _nova = _no_va.getText().toString();
//                final String _nova = "8820122000000084";
                Log.i("","NOVA= "+ NOVA);
                obj.put("no_va", NOVA);
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }


    private void spinnerSearchJnsClaim(String EP,String NOVA){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://kaffah.amanahgitha.com/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(ClaimPetugasActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                response -> {
                    Log.i("tag", "response spinnerLokPintuPtgs =" + response );
                    try {
                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        if( Help.isJSONValid(response) ){
                            JSONObject jsonObject = new JSONObject(response);
                            String jenis_klaim;
                            String nama_klaim;
                            arrJnsClaim.clear();
                            if( jsonObject.getBoolean("success") ) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i <jsonArray.length();i++ ) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    jenis_klaim = jsonObject1.getString("jenis_klaim");
                                    nama_klaim = jsonObject1.getString("nama_klaim");
                                    Log.i("","jenis_klaim= "+jenis_klaim);
                                    Log.i("","nama_klaim= "+nama_klaim);
                                    arrJnsClaim.add(new SpinnerJnsClaim(jenis_klaim,nama_klaim));
                                }
                            }
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ClaimPetugasActivity.this);
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
                        _spinner_jns_klaim_ptgs.setAdapter(new ArrayAdapter<SpinnerJnsClaim>(ClaimPetugasActivity.this, android.R.layout.simple_spinner_dropdown_item, arrJnsClaim) );
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(ClaimPetugasActivity.this);
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
//                final String _nova = "8820122000000084";
                obj.put("no_va", NOVA);
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
        final RequestQueue requestQueue = Volley.newRequestQueue(ClaimPetugasActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                response -> {
                    Log.i("", "response sendData ===" + response );
                    try {
                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        if( Help.isJSONValid(response) ){
                            JSONObject jsonObject = new JSONObject(response.toString());

                            String registration_by;
                            String va_no;
                            String kontak_pelapor;
                            String nama_pengunjung;
                            String kontak_pengunjung;
                            String jenis_klaim;
                            String nominal_klaim;
                            String keterangan_klaim;
                            String tgl_kejadian;
                            String lokasi_kejadian;

                            if( jsonObject.getBoolean("success") ) {
                                String data = jsonObject.getString("data");
                                JSONObject jsonObject1 = new JSONObject(data);
                                va_no = jsonObject1.getString("va_no");
//                                    keterangan_klaim = jsonObject1.getString("keterangan_klaim");


                                /*
                                registration_by:triono@amanahgitha.com
                                va_no:8820122000000081
                                kontak_pelapor:triono 089508161088
                                nama_pengunjung:Nama Pengunjung
                                kontak_pengunjung:+6289508161088
                                jenis_klaim:0002
                                nominal_klaim:1500000
                                keterangan_klaim:terpeleset terbentur kepala
                                tgl_kejadian:2020-05-28
                                lokasi_kejadian:Gunung Pangrango
                                */


//                                    Intent ii = new Intent(ClaimPetugasActivity.this, SuccessClaimActivity.class);
                                Intent ii = new Intent(ClaimPetugasActivity.this, SuccessRegistrasiWisatawanActivity.class);
                                ii.putExtra("result_dt_ket", "Claim berhasil Diinput");
                                ii.putExtra("result_dt_email", va_no);
                                ii.putExtra("result_dt_flag", "ClaimPetugas");
                                ii.putExtra("result_dt_berhasil", true);
                                startActivity(ii);
                            } else {
                            }
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ClaimPetugasActivity.this);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(ClaimPetugasActivity.this);
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
                final String registration_by = sessionManager.getUserDetail().get(SessionManager.key_email).trim();
                final String va_no = _no_va.getText().toString().trim();
                final String kontak_pelapor = _text_contact_pelapor.getText().toString().trim();
                final String nama_pengunjung = _text_nama_pengunjung.getText().toString().trim();
                final String kontak_pengunjung = _text_contact_pengunjung.getText().toString().trim();
                final String jenis_klaim = sessionManager.getDataJnsClaim().get(SessionManager.key_jns_claim).trim();
                final String nominal_klaim = _text_nom_claim.getText().toString().trim();
                final String keterangan_klaim = _text_ket_claim.getText().toString().trim();
                final String tgl_kejadian = _tgl_kejadian.getText().toString().trim();
                final String lokasi_kejadian = _text_lokasi_kejadian.getText().toString().trim();

                Log.i("","sendData registration_by= "+ registration_by);
                Log.i("","sendData va_no= "+ va_no);
                Log.i("","sendData kontak_pelapor= "+ kontak_pelapor);
                Log.i("","sendData nama_pengunjung= "+ nama_pengunjung);
                Log.i("","sendData kontak_pengunjung= "+ kontak_pengunjung);
                Log.i("","sendData jenis_klaim= "+ jenis_klaim);
                Log.i("","sendData nominal_klaim= "+ nominal_klaim);
                Log.i("","sendData keterangan_klaim= "+ keterangan_klaim);
                Log.i("","sendData tgl_kejadian= "+ tgl_kejadian);
                Log.i("","sendData lokasi_kejadian= "+ lokasi_kejadian);


                obj.put("registration_by",registration_by);
                obj.put("va_no", va_no);
                obj.put("kontak_pelapor", kontak_pelapor);
                obj.put("nama_pengunjung", nama_pengunjung);
                obj.put("kontak_pengunjung", kontak_pengunjung);
                obj.put("jenis_klaim", jenis_klaim);
                obj.put("nominal_klaim", nominal_klaim);
                obj.put("keterangan_klaim", keterangan_klaim);
                obj.put("tgl_kejadian", tgl_kejadian);
                obj.put("lokasi_kejadian", lokasi_kejadian);
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }



}
