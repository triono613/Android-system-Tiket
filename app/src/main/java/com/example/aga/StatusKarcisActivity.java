package com.example.aga;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aga.Adapter.CustomAdapter;
import com.example.aga.Adapter.SessionManager;
import com.example.aga.Helper.Help;
import com.example.aga.Model.EntityStatusKarcis;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StatusKarcisActivity extends AppCompatActivity {

    private final String JSON_URL ="http://kaffah.amanahgitha.com/~androidwisata/?data=informasi_status_karcis";
    private JsonArrayRequest jsonArrayRequest;
    private RequestQueue requestQueue;
    RecyclerView recyclerView;

    RecyclerView.LayoutManager layoutManager;
    SessionManager sessionManager ;
    ArrayList<EntityStatusKarcis> entityStatusKarcisArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_karcis);

        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        sessionManager = new SessionManager(getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_data_status_karcis);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        jsonrequest("informasi_status_karcis");

    }



    private void jsonrequest(String EP){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://kaffah.amanahgitha.com/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(StatusKarcisActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("triono", "response 1 ===" + response );


                        try {
                            findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                            if( Help.isJSONValid(response) ){

                                ArrayList dx = new ArrayList<>();
                                JSONObject jsonObject = new JSONObject(response.toString());

                                if( jsonObject.getBoolean("success") ) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                                    Log.i("triono", "jsonObject.getBoolean() ===" + jsonObject.getBoolean("success") );
                                    String _va_no;
                                    String _tgl_kunjungan;
                                    String _status;
                                    String _nama;
                                    entityStatusKarcisArrayList = new ArrayList<>();

                                    for (int i = 0; i <jsonArray.length();i++ ) {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                         _va_no = jsonObject1.getString("va_no");
                                        _tgl_kunjungan = jsonObject1.getString("tgl_kunjungan");
                                        _status = jsonObject1.getString("status");
                                        _nama = jsonObject1.getString("nama");

                                        Log.i("wisatawan","i "+ i);
                                        Log.i("wisatawan","_va_no "+_va_no);

                                        entityStatusKarcisArrayList.add(new EntityStatusKarcis(_va_no,_tgl_kunjungan,_status,_nama));

                                    }

                                    CustomAdapter customAdapter = new CustomAdapter( entityStatusKarcisArrayList, StatusKarcisActivity.this);
                                    recyclerView.setAdapter(customAdapter);

                                } else {
                                        // do nothing just pray to Allah
                                }
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(StatusKarcisActivity.this);
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
                final String _email =  sessionManager.getUserDetail().get(SessionManager.key_email);
                obj.put("alamat_email", _email);
                return obj;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

}
