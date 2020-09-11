package com.amanahgithawisata.aga;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
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
import androidx.fragment.app.DialogFragment;

import com.amanahgithawisata.aga.Adapter.DatePickerTwoFragment;
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
import java.util.regex.Pattern;

public class RegisterPetugasActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    ImageView btn_pendaftaran_petugas_back;
    Button btn_daftar_petugas;
    Spinner spinner ;
    List<String> listLokasiWisata = new ArrayList<String>();

    TextView txt_view_petugas_email,txt_petugas_tgl_lhr;
    String server;
    Dialog mydialog;
    SessionManager sessionManager;


    public void Showpopup (View v){
        TextView txtclose;
        Button btnFollow;
        mydialog.setContentView(R.layout.activity_popup_old);
        txtclose=(TextView)mydialog.findViewById(R.id.txtclose);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog.dismiss();

        }
    });

    mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mydialog.show();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_petugas);
        sessionManager = new SessionManager(getApplicationContext());

        btn_pendaftaran_petugas_back = (ImageView) findViewById(R.id.btn_pendaftaran_petugas_back);
        txt_petugas_tgl_lhr = (EditText) findViewById(R.id.txt_petugas_tgl_lhr);
        btn_daftar_petugas = findViewById(R.id.btn_daftar_petugas);
        spinner =  findViewById(R.id.spinner_lok_wisata);
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);

        loadSpinnerData("daftar_lokasi_wisata");
        mydialog=new Dialog(this);

        btn_pendaftaran_petugas_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goto_getstarted = new Intent(RegisterPetugasActivity.this, GetStartedActivity.class);
                startActivity(goto_getstarted);
            }
        });




        btn_daftar_petugas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataPostVolley();
            }
        });

        txt_petugas_tgl_lhr.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    DialogFragment datePicker2 = new DatePickerTwoFragment();
                    datePicker2.show(getSupportFragmentManager(),"xxxxxx");
                }
            }
        });



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String wisata = spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
                Log.i("triono", "spinner " + wisata);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        TextView txt_petugas_tgl_lhr = (TextView) findViewById(R.id.txt_petugas_tgl_lhr);

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        txt_petugas_tgl_lhr.setText(sdf.format(c.getTime()));
    }


    void loadSpinnerData(String EP) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://kaffah.amanahgitha.com/~androidwisata/?data="+ EP;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i("triono","response= " + response );

                            if( Help.isJSONValid(response) ){
                                JSONObject jsonObject = new JSONObject(response.toString());
//                                Log.i("triono","success= " + jsonObject.getBoolean("success") );

                                if( jsonObject.getBoolean("success") ) {

                                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                                    for (int i = 0; i <jsonArray.length();i++ ) {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                        String nm_obj_wisata = jsonObject1.getString("nama");
                                        String kd_obj_wisata = jsonObject1.getString("kode_ksda");
                                        int kd = Integer.parseInt(kd_obj_wisata);

//                                        Log.i("triono","nm_obj_wisata= " + nm_obj_wisata);
//                                        Log.i("triono","kd_obj_wisata= " + kd);

                                        listLokasiWisata.add(i, kd + "-" + nm_obj_wisata );
                                    }
                                } else {
                                    listLokasiWisata.add(0, "27" + "- dummy" );
                                }
                            }
                        spinner.setAdapter(new ArrayAdapter<String>(RegisterPetugasActivity.this, android.R.layout.simple_spinner_dropdown_item,listLokasiWisata) );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, error -> {
                    Log.d("Error Response", error.toString());

                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterPetugasActivity.this);
                        builder.setMessage("Terjadi Gangguan, Refresh Halaman")
                                .setCancelable(false)
                                .setPositiveButton("Ya", (dialog, id) -> {
                                    finish();
                                    startActivity(getIntent());
                                })
                                .setNegativeButton("Tidak", (dialog, id) -> dialog.cancel());
                        AlertDialog alert = builder.create();
                        alert.show();
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> obj = new HashMap<String, String>();
                final String ksda_val =  sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi).trim();
                Log.i("triono","ksda_val= "+ ksda_val);
                obj.put("lokasi", "27");
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        queue.add(stringRequest);

    }




    private void getDataPostVolley() {

        EditText ptgsName = (EditText) findViewById(R.id.txt_petugas_name);
        EditText  ptgsEmail = (EditText) findViewById(R.id.txt_petugas_email);
        EditText ptgsTgllhr = (EditText) findViewById(R.id.txt_petugas_tgl_lhr);
        EditText ptgsHp = (EditText) findViewById(R.id.txt_petugas_hp);
        EditText ptgsPsswd1 = (EditText) findViewById(R.id.txt_petugas_passwd);
        EditText ptgsPsswd2 = (EditText) findViewById(R.id.txt_petugas_passwd_2);
        Spinner spinnerLokWisata = (Spinner) findViewById(R.id.spinner_lok_wisata);
        EditText _txt_passwd_twa = (EditText) findViewById(R.id.txt_passwd_twa);

//        Log.i("triono","ptgsName= "+ ptgsName.getText().toString());
//        Log.i("triono","ptgsEmail= "+ ptgsEmail.getText().toString());
//        Log.i("triono","ptgsTgllhr= "+ ptgsTgllhr.getText().toString());
//        Log.i("triono","ptgsHp= "+ ptgsHp.getText().toString());
//        Log.i("triono","ptgsPsswd1= "+ ptgsPsswd1.getText().toString());
//        Log.i("triono","ptgsPsswd2= "+ ptgsPsswd2.getText().toString());
//        Log.i("triono","spinnerLokWisata= "+ spinnerLokWisata.getSelectedItem());


        final String nama_val = ptgsName.getText().toString();
        final String email_val = ptgsEmail.getText().toString();
        final String tgllhr_val = ptgsTgllhr.getText().toString();
        final String phone_val = ptgsHp.getText().toString();
        final String passwd1_val = ptgsPsswd1.getText().toString();
        final String passwd2_val = ptgsPsswd2.getText().toString();
        final String passwd_twa_val  = _txt_passwd_twa.getText().toString();
        final String spinnerLokWisata_val = spinnerLokWisata.getSelectedItem().toString();


        if(TextUtils.isEmpty(nama_val) ) {
            ptgsName.setError("Nama Masih Kosong!");
        }

        if  (!EMAIL_ADDRESS_PATTERN.matcher(email_val).matches() ) {
            ptgsEmail.setError("Alamat Email Invalid!");
        }

        else if(TextUtils.isEmpty(tgllhr_val) ) {
            ptgsTgllhr.setError("Tgl Lahir Masih Kosong!");
        }
        else if(TextUtils.isEmpty(phone_val) ) {
            ptgsHp.setError("Nmr Telp Masih Kosong!");
        }
        else if(TextUtils.isEmpty(passwd1_val) || passwd1_val.length() < 8 ) {
            ptgsPsswd1.setError("Password Masih Kosong || Password Min 8 Char!");
        }
        else if( !passwd1_val.toString().equals(passwd2_val.toString()) ) {
            ptgsPsswd2.setError("Password Tidak Sesuai !");
        }
        if(TextUtils.isEmpty(passwd_twa_val) ) {
            ptgsName.setError("Password TWA Masih Kosong!");
        }
        else {
            newPostDt("daftar_petugas");
        }
    }

    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9+._%-+]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
                    "(" +
                    "." +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
                    ")+"
    );

    void newPostDt(String EP){

        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://kaffah.amanahgitha.com/~androidwisata/?data="+EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(RegisterPetugasActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("triono", "response 1 ===" + response );
                        try {

                            findViewById(R.id.loadingPanel).setVisibility(View.GONE);

                            JSONObject jObj = new JSONObject(response);
                            String data = jObj.getString("data");
                            JSONObject jObj2 = new JSONObject(data);
                            Boolean berhasil = jObj2.getBoolean("berhasil");
                            String ket = jObj2.getString("keterangan");
                            String email = jObj2.getString("alamat_email");
                            String name = jObj2.getString("nama");
                            boolean sukses = jObj.getBoolean("success");

//                            Log.i("triono", "data ===" + data );
//                            Log.i("triono", "keterangan ===" + ket );
//                            Log.i("triono", "sukses ===" + sukses );

                            if (sukses){
                                Intent i = new Intent(RegisterPetugasActivity.this, SuccessRegistrasiWisatawanActivity.class);
                                i.putExtra("result_dt_ket", ket);
                                i.putExtra("result_dt_email", email);
                                i.putExtra("result_dt_berhasil", berhasil);
                                i.putExtra("result_dt_flag", "");

                                startActivity(i);
//                                overridePendingTransition(R.anim.app_getstarted,R.anim.app_splash);
                            }
                        } catch (JSONException e) {
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
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> obj = new HashMap<String, String>();

                EditText  ptgsName = (EditText) findViewById(R.id.txt_petugas_name);
                EditText ptgsEmail = (EditText) findViewById(R.id.txt_petugas_email);
                EditText ptgsTgllhr = (EditText) findViewById(R.id.txt_petugas_tgl_lhr);
                EditText ptgsHp = (EditText) findViewById(R.id.txt_petugas_hp);
                EditText  ptgsPsswd1 = (EditText) findViewById(R.id.txt_petugas_passwd);
                EditText ptgsPsswd2 = (EditText) findViewById(R.id.txt_petugas_passwd_2);
                EditText passwd_twa = (EditText) findViewById(R.id.txt_passwd_twa);
                Spinner spinnerLokWisata = (Spinner) findViewById(R.id.spinner_lok_wisata);


                final String nama_val = ptgsName.getText().toString().trim();
                final String email_val = ptgsEmail.getText().toString().trim();
                final String tgllhr_val = ptgsTgllhr.getText().toString().trim();
                final String phone_val = ptgsHp.getText().toString().trim();
                final String passwd1_val = ptgsPsswd1.getText().toString().trim();
                final String passwd2_val = ptgsPsswd2.getText().toString().trim();
                final String passwd_twa_val = passwd_twa.getText().toString().trim();
                final String spinnerLokWisata_val = spinnerLokWisata.getSelectedItem().toString().trim();
                final String[] split =  spinnerLokWisata_val.split("-");

                Log.i("","nama_val= "+nama_val);
                Log.i("","email_val= "+email_val);
                Log.i("","tgllhr_val= "+tgllhr_val);
                Log.i("","phone_val= "+phone_val);
                Log.i("","passwd1_val= "+passwd1_val);
                Log.i("","passwd2_val= "+passwd2_val);
                Log.i("","passwd_twa_val= "+passwd_twa_val);
                Log.i("","kode_lokasi= "+split[0]);

                obj.put("nama",nama_val);
                obj.put("tgl_lahir", tgllhr_val);
                obj.put("sellular_no", phone_val);
                obj.put("alamat_email",email_val );
                obj.put("jenis_kelamin", "L");
                obj.put("kata_kunci", passwd1_val );
                obj.put("kode_lokasi", split[0].toString());
                obj.put("kunci_twa", passwd_twa_val);
//                obj.put("flag", "daftar_petugas");
                return obj;
            }

        };
        requestQueue.add(stringRequest);
    }


}
