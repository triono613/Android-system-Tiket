package com.amanahgithawisata.aga;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.amanahgithawisata.aga.Adapter.SessionManager;
import com.amanahgithawisata.aga.Helper.Help;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditPasswordWisatawanActivity extends AppCompatActivity {

    SessionManager sessionManager;
    Button btn_logout_ptgs;
    Dialog myDialog;
    TextView _text_email_ep;
    TextView _text_pass_lama_ep;
    TextView _text_pass_baru_ep;
    TextView _text_pass_confirm_ep;
    Button _btn_submit_ep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* setContentView TO  Activity activity_edit_password_petugas because use 1 activity in 2 xml menu*/
        setContentView(R.layout.activity_edit_password_petugas);
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        sessionManager = new SessionManager(getApplicationContext());

        _text_email_ep = (TextView) findViewById(R.id.text_email_ep);
        _text_pass_lama_ep = (TextView) findViewById(R.id.text_pass_lama_ep);
        _text_pass_baru_ep = (TextView) findViewById(R.id.text_pass_baru_ep);
        _text_pass_confirm_ep = (TextView) findViewById(R.id.text_pass_confirm_ep);
        _btn_submit_ep =(Button) findViewById(R.id.btn_submit_ep);

        _text_email_ep.setText(sessionManager.getUserDetail().get(SessionManager.key_email) );
        _btn_submit_ep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( sessionManager.isLoggedIn()) {
                    String __text_pass_lama_ep = _text_pass_lama_ep.getText().toString().trim();
                    String __text_pass_baru_ep = _text_pass_baru_ep.getText().toString().trim();
                    String __text_pass_confirm_ep = _text_pass_confirm_ep.getText().toString().trim();

                    if(TextUtils.isEmpty(__text_pass_lama_ep) || __text_pass_lama_ep.length() < 8 ) {
                        _text_pass_lama_ep.setError("Password Masih Kosong || Password Min 8 Char!");
                    }
                    else if(TextUtils.isEmpty(__text_pass_baru_ep) || __text_pass_baru_ep.length() < 8 ) {
                        _text_pass_baru_ep.setError("Password Masih Kosong || Password Min 8 Char!");
                    }
                    else if( !__text_pass_baru_ep.equals(__text_pass_confirm_ep) ) {
                        _text_pass_confirm_ep.setError("Password Tidak Sesuai !");
                    }
                    else {
                        changePassword("ubah_kata_kunci");
                    }


                }
            }
        });
    }

    public void changePassword(String EP){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://"+ Help.domain_api() +"/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(EditPasswordWisatawanActivity.this);
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
                                        Intent i = new Intent(EditPasswordWisatawanActivity.this, SuccessRegistrasiWisatawanActivity.class);
                                        i.putExtra("result_dt_ket", _keterangan);
                                        i.putExtra("result_dt_email", _alamat_email);
                                        i.putExtra("result_dt_berhasil", berhasil);
                                        i.putExtra("result_dt_flag", "flagEditPasswordPetugasTrue");
                                        startActivity(i);
                                    } else {
                                        Log.i("","! berhasil");
                                        Intent i = new Intent(EditPasswordWisatawanActivity.this, SuccessRegistrasiWisatawanActivity.class);
                                        i.putExtra("result_dt_ket", _keterangan);
                                        i.putExtra("result_dt_email", _alamat_email);
                                        i.putExtra("result_dt_berhasil", berhasil);
                                        i.putExtra("result_dt_flag", "flagEditPasswordPetugasFalse");
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
                }, error -> {
                    Log.i("tag", "response =" + error.toString());
                    error.printStackTrace();
                    requestQueue.stop();

                    findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditPasswordWisatawanActivity.this);
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
//                final String key_kode_ksda = (String) sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_kode_ksda);
                final String __text_email_ep =  _text_email_ep.getText().toString().trim();
                final String __text_pass_lama_ep =  _text_pass_lama_ep.getText().toString().trim();
                final String __text_pass_baru_ep =  _text_pass_baru_ep.getText().toString().trim();
                final String __text_pass_confirm_ep =  _text_pass_confirm_ep.getText().toString().trim();


                Log.i("tag","__text_email_ep= " + __text_email_ep );
                Log.i("tag","__text_pass_lama_ep= " + __text_pass_lama_ep );
                Log.i("tag","__text_pass_baru_ep= " + __text_pass_baru_ep );
                Log.i("tag","__text_pass_confirm_ep= " + __text_pass_confirm_ep );

                obj.put("alamat_email",__text_email_ep);
                obj.put("kata_kunciO", __text_pass_lama_ep);
                obj.put("kata_kunciN",__text_pass_baru_ep);
                obj.put("kata_kunciV",__text_pass_confirm_ep);

                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

}
