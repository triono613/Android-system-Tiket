package com.example.aga;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aga.Adapter.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;



public class SigninActivity extends AppCompatActivity {
    SessionManager sessionManager;
    Button btn_login_user;
    EditText txt_login_email;

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(SigninActivity.this, GetStartedActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
//        Toast.makeText(SigninActivity.this,"sessionManager.isLoggedIn()= "+sessionManager.isLoggedIn(), Toast.LENGTH_LONG).show();
        super.onBackPressed();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        setupView();
        btn_login_user.setEnabled(true);
        btn_login_user.setText("Login");

        sessionManager = new SessionManager(getApplicationContext());
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        txt_login_email = (EditText) findViewById(R.id.txt_login_email);


        Log.i("SigninActivity","sessionManager.isLoggedIn() = "+ sessionManager.isLoggedIn() );
        Log.i("SigninActivity","sessionManager.getFlag= "+ sessionManager.getFlag());
        Log.i("SigninActivity","SessionManager.key_kode_lokasi= "+  sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi));



//        if (sessionManager.isLoggedIn()) {
//            if (sessionManager.getFlag().equals("1")) {
//                if (sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi) == "1") {
//                    Intent intent = new Intent(SigninActivity.this, DashboardPetugasActivity.class);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    Intent intent = new Intent(SigninActivity.this, DashboardWisatawanActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//            }
//
//        }
//        else
//        {
//            Toast.makeText(SigninActivity.this, "check session SigninActivity: " + sessionManager.isLoggedIn() + " - " + sessionManager.getFlag() + " - " + (sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi)), Toast.LENGTH_LONG).show();
//            Intent intent = new Intent(SigninActivity.this, GetStartedActivity.class);
//            startActivity(intent);
//            finish();
//        }



        if( sessionManager.isLoggedIn() ){

            Intent intent;

//            switch (sessionManager.getFlag())
//            {
//                case "1":
//                    intent = new Intent(SigninActivity.this, DashboardWisatawanActivity.class);
//                    break;
//                case "0":
//                    intent = new Intent(SigninActivity.this, DashboardPetugasActivity.class);
//                    break;
//                default:
//                    intent = new Intent(SigninActivity.this,SigninActivity.class);
//                    break;
//            }

            switch (sessionManager.getFlag())
            {
                case "1":
                    if (Objects.equals(sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi), "null")){
                        intent = new Intent(SigninActivity.this, DashboardWisatawanActivity.class);
                        break;
                    } else {
                        intent = new Intent(SigninActivity.this, DashboardPetugasActivity.class);
                        break;
                    }

                case "0":
                    intent = new Intent(SigninActivity.this, DashboardPetugasActivity.class);
                    break;
                default:
                    intent = new Intent(SigninActivity.this,SigninActivity.class);
                    break;
            }
            startActivity(intent);
            finish();
        }


        btn_login_user.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                getDataPostVolley();

            }
        });


    }


    private void getDataPostVolley() {
        EditText loginEmail, loginPsswd;

        loginEmail = (EditText) findViewById(R.id.txt_login_email);
        loginPsswd = (EditText) findViewById(R.id.txt_login_passwd);


        final String email_val = loginEmail.getText().toString();
        final String passwd_val = loginPsswd.getText().toString();


        if  (!EMAIL_ADDRESS_PATTERN.matcher(email_val).matches() ) {
            loginEmail.setError("Alamat Email Invalid!");
        }
        else if(TextUtils.isEmpty(passwd_val) ) {
            loginPsswd.setError("Password Masih Kosong!");
        }
        else {
            newPostLogin();
        }
    }

    @SuppressLint("SetTextI18n")
    void newPostLogin(){

        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        btn_login_user.setEnabled(false);
        btn_login_user.setText("Loading...");

        String server_url = "http://kaffah.amanahgitha.com/~androidwisata/?data=login_wisata";
        final RequestQueue requestQueue = Volley.newRequestQueue(SigninActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        try {
                            JSONObject jObj = new JSONObject(response);
                            Boolean sukses = jObj.getBoolean("success");

                            Log.i("triono ","jObj "+ jObj);
                            Log.i("triono ","sukses "+ sukses);

                            if(sukses) {

                                String data = jObj.getString("data");
                                JSONObject jObj_child = new JSONObject(data);

                                Log.i("triono", "sukses= " + sukses);
                                Log.i("triono", "data ===" + data);

                                Boolean is_login = true;
                                String val_id = jObj_child.getString("id");
                                String val_email = jObj_child.getString("alamat_email");
                                String val_flag = jObj_child.getString("flag");
                                String val_name = jObj_child.getString("full_name");
                                String val_ket = jObj_child.getString("keterangan");
                                String val_kode_lokasi = jObj_child.getString("kode_lokasi");
                                String val_nama_lokasi = jObj_child.getString("nama_lokasi");
                                String val_tgl_lahir = jObj_child.getString("tgl_lahir");
                                String val_jenis_kelamin = jObj_child.getString("jenis_kelamin");
                                String val_sellular_no = jObj_child.getString("sellular_no");

                                Log.i("SigninActivity", "val_flag ===" + val_flag);
                                Log.i("SigninActivity", "val_kode_lokasi ===" + val_kode_lokasi);

                                if (jObj_child.getString("flag").equals("0")) {

                                    Intent goto_register_success_petugas = new Intent(SigninActivity.this, SuccessRegistrasiWisatawanActivity.class);
                                    goto_register_success_petugas.putExtra("result_dt_ket", "Belum Dilakukan Verifikasi/Belum Registrasi");
                                    goto_register_success_petugas.putExtra("result_dt_email", "-");
                                    goto_register_success_petugas.putExtra("result_dt_berhasil", false);
                                    startActivity(goto_register_success_petugas);


                                } else if (jObj_child.getString("flag").equals("1")) {

                                    sessionManager.createSession( val_email, val_flag, val_name, val_id, val_ket, val_kode_lokasi,
                                                                val_nama_lokasi, val_tgl_lahir, val_jenis_kelamin, val_sellular_no);

                                    if (jObj_child.getString("kode_lokasi").equals("null")) {

                                        /*  activity for Wisatawan */

                                        Intent goto_register_success_petugas = new Intent(SigninActivity.this, DashboardWisatawanActivity.class);
                                        goto_register_success_petugas.putExtra("result_dt_ket", val_ket.toString());
                                        goto_register_success_petugas.putExtra("result_dt_email", val_email.toString());
                                        goto_register_success_petugas.putExtra("result_dt_berhasil", true);
                                        startActivity(goto_register_success_petugas);
                                        overridePendingTransition(R.anim.app_getstarted,R.anim.btt);
                                    } else {
                                        /*  activity for Petugas */

                                        Intent goto_register_success_petugas = new Intent(SigninActivity.this, DashboardPetugasActivity.class);
                                        goto_register_success_petugas.putExtra("result_dt_ket", val_ket.toString());
                                        goto_register_success_petugas.putExtra("result_dt_email", val_email.toString());
                                        goto_register_success_petugas.putExtra("result_dt_berhasil", true);
                                        startActivity(goto_register_success_petugas);
                                        overridePendingTransition(R.anim.app_getstarted,R.anim.btt);
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
                Log.i("triono", "response =" + error.toString());
                error.printStackTrace();
                requestQueue.stop();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> obj = new HashMap<String, String>();

                EditText loginEmail, loginPsswd ;
                Spinner spinnerLokWisata;

                loginEmail = (EditText) findViewById(R.id.txt_login_email);
                loginPsswd = (EditText) findViewById(R.id.txt_login_passwd);

                final String email_val = loginEmail.getText().toString();
                final String passwd1_val = loginPsswd.getText().toString();

                obj.put("alamat_email",email_val );
                obj.put("kata_kunci", passwd1_val );
                return obj;
            }

        };
        requestQueue.add(stringRequest);
    }

    private void setupView() {
        btn_login_user = (Button) findViewById(R.id.btn_login_user);
        txt_login_email = (EditText) findViewById(R.id.txt_login_email);

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

}
