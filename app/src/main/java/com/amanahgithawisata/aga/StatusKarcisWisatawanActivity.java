package com.amanahgithawisata.aga;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amanahgithawisata.aga.Adapter.CustomAdapterEntityWisatawan;
import com.amanahgithawisata.aga.Adapter.SessionManager;
import com.amanahgithawisata.aga.Helper.Help;
import com.amanahgithawisata.aga.Model.EntityStatusKarcisWisatawan;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StatusKarcisWisatawanActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SessionManager sessionManager ;
    ArrayList<EntityStatusKarcisWisatawan> entityStatusKarcisArrayList;
    CustomAdapterEntityWisatawan customAdapter ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_karcis);

        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        sessionManager = new SessionManager(getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_data_status_karcis);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        informasi_status_karcis("informasi_status_karcis");

    }



    private void informasi_status_karcis(String EP){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://"+ Help.domain_api() +"/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(StatusKarcisWisatawanActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                response -> {
                    Log.i("triono", "response 1 ===" + response );
                    try {
                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        if( Help.isJSONValid(response) ){
                            ArrayList dx = new ArrayList<>();
                            JSONObject jsonObject = new JSONObject(response.toString());
                            if( jsonObject.getBoolean("success") ) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                Log.i("", "jsonObject.getBoolean() ===" + jsonObject.getBoolean("success") );
                                String _va_no, _tgl_kunjungan,_status,_nama,_tgl_kunjungan_sd,_jumlah_hari,_bank_name;
                                entityStatusKarcisArrayList = new ArrayList<>();

                                for (int i = 0; i <jsonArray.length();i++ ) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                     _va_no = jsonObject1.getString("va_no");
                                    _tgl_kunjungan = jsonObject1.getString("tgl_kunjungan");
                                    _status = jsonObject1.getString("status");
                                    _nama = jsonObject1.getString("nama");
                                    _tgl_kunjungan_sd = jsonObject1.getString("tgl_kunjungan_sd");
                                    _jumlah_hari = jsonObject1.getString("jumlah_hari");
                                    _bank_name = jsonObject1.getString("bank_name");

                                    Log.i("wisatawan","i "+ i);
                                    Log.i("wisatawan","_va_no "+_va_no);
                                    Log.i("wisatawan","_tgl_kunjungan_sd "+_tgl_kunjungan_sd);
                                    Log.i("wisatawan","_jumlah_hari "+_jumlah_hari);
                                    Log.i("wisatawan","_bank_name "+_bank_name);


                                    entityStatusKarcisArrayList.add(new EntityStatusKarcisWisatawan(_va_no,_tgl_kunjungan,_status,_nama,_tgl_kunjungan_sd,_jumlah_hari,_bank_name));
                                }
                                customAdapter = new CustomAdapterEntityWisatawan( entityStatusKarcisArrayList, StatusKarcisWisatawanActivity.this);
                                recyclerView.setAdapter(customAdapter);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    requestQueue.stop();
                }, error -> {
                    Log.i("triono", "response =" + error.toString());
                    error.printStackTrace();
                    requestQueue.stop();
                    findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                    AlertDialog.Builder builder = new AlertDialog.Builder(StatusKarcisWisatawanActivity.this);
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
                final String _email =  sessionManager.getUserDetail().get(SessionManager.key_email);
                obj.put("alamat_email", _email.trim());
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

}
