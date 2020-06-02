package com.example.aga;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
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
import com.example.aga.Adapter.CustomAdapterEntityPetugas;
import com.example.aga.Adapter.SessionManager;
import com.example.aga.Helper.Help;
import com.example.aga.Model.EntityStatusKarcisPetugas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class StatusKarcisPetugasActivity extends AppCompatActivity {

    private final String JSON_URL ="http://kaffah.amanahgitha.com/~androidwisata/?data=informasi_status_karcis";
    private JsonArrayRequest jsonArrayRequest;
    private RequestQueue requestQueue;
    RecyclerView recyclerView;
    Toolbar _toolbar;
    MenuItem _cariToolbar;
    Button _btn_entity_edit;


    RecyclerView.LayoutManager layoutManager;
    SessionManager sessionManager ;
    ArrayList<EntityStatusKarcisPetugas> entityStatusKarcisPetugasArrayList;
    CustomAdapterEntityPetugas adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_karcis_petugas);
//        setContentView(R.layout.entity_status_karcis_petugas);
        entityStatusKarcisPetugasArrayList = new ArrayList<>();
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        sessionManager = new SessionManager(getApplicationContext());

//        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_data_status_karcis_ptgs);
//        jsonrequest("informasi_status_karcis");

        @SuppressLint("InflateParams") View inflatedView = getLayoutInflater().inflate(R.layout.entity_status_karcis, null);
        _btn_entity_edit = (Button) inflatedView.findViewById(R.id.btn_entity_edit);
//        _btn_entity_edit.setText("Hello!");

        _toolbar = (Toolbar)  findViewById(R.id.toolbar_new);
        setSupportActionBar(_toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_data_status_karcis_ptgs);

//        _btn_entity_edit = (Button) findViewById(R.id.btn_entity_edit_ptgs);


        _btn_entity_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(sessionManager.isLoggedIn()) {
                    Intent i = new Intent(StatusKarcisPetugasActivity.this,EditPasswordPetugasActivity.class);
                    startActivity(i);
//                }

            }
        });


        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://kaffah.amanahgitha.com/~androidwisata/?data=informasi_status_karcis_petugas";
        final RequestQueue requestQueue = Volley.newRequestQueue(StatusKarcisPetugasActivity.this);
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
                                    entityStatusKarcisPetugasArrayList = new ArrayList<>();

                                    for (int i = 0; i <jsonArray.length();i++ ) {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                        _va_no = jsonObject1.getString("va_no");
                                        _tgl_kunjungan = jsonObject1.getString("tgl_kunjungan");
                                        _status = jsonObject1.getString("status");
                                        _nama = jsonObject1.getString("nama");
                                        Log.i("petugas","_va_no "+_va_no);
                                        entityStatusKarcisPetugasArrayList.add(new EntityStatusKarcisPetugas(_va_no,_tgl_kunjungan,_status,_nama));
                                    }

                                    DividerItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
                                    layoutManager = new LinearLayoutManager(StatusKarcisPetugasActivity.this);
                                    recyclerView.setLayoutManager(layoutManager);

                                } else {
                                    // do nothing just pray to Allah
                                }
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(StatusKarcisPetugasActivity.this);
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
                obj.put("text_pencarian", "");
                return obj;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);


        DividerItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        adapter = new CustomAdapterEntityPetugas(entityStatusKarcisPetugasArrayList,this);
        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);




    }











    private void fillExampleList() {
        entityStatusKarcisPetugasArrayList = new ArrayList<>();
        entityStatusKarcisPetugasArrayList.add(new EntityStatusKarcisPetugas("00001234" ,"One", "Ten","gunug gede 1"));
        entityStatusKarcisPetugasArrayList.add(new EntityStatusKarcisPetugas("00001235" ,"two", "Ten","gunug gede 2"));
        entityStatusKarcisPetugasArrayList.add(new EntityStatusKarcisPetugas("00001236" ,"three", "Ten","gunug gede 3"));


        DividerItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(getApplicationContext(), R.drawable.line)));
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new CustomAdapterEntityPetugas(entityStatusKarcisPetugasArrayList,this);
        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setUpRecyclerView() {

        RecyclerView recyclerView = findViewById(R.id.recycler_view_data_status_karcis_ptgs);
        recyclerView.setHasFixedSize(true);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(getApplicationContext(), R.drawable.line)));
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new CustomAdapterEntityPetugas(entityStatusKarcisPetugasArrayList,this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }





    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        MenuItem searchIem = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) searchIem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                jsonrequest(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String nextText) {
                nextText = nextText.toLowerCase();
                ArrayList<EntityStatusKarcisPetugas> dataFilter =new ArrayList<EntityStatusKarcisPetugas>();
                for(EntityStatusKarcisPetugas data : entityStatusKarcisPetugasArrayList){
                    String va = data.getVa().toLowerCase();
                    String tgl = data.getTgl().toLowerCase();
                    String status = data.getStatus().toLowerCase();
                    if(va.contains(nextText)){
                        dataFilter.add(data);
                    }
                    if(tgl.contains(nextText)){
                        dataFilter.add(data);
                    }
                    if(status.contains(nextText)){
                        dataFilter.add(data);
                    }
                }
                adapter.setFilter(dataFilter);
                return true;

            }
        });
        return true;
    }


    private void jsonrequest(String EP){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://kaffah.amanahgitha.com/~androidwisata/?data=informasi_status_karcis_petugas";
        final RequestQueue requestQueue = Volley.newRequestQueue(StatusKarcisPetugasActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("TAG", "response 1 ===" + response );
                        try {
                            findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                            if( Help.isJSONValid(response) ){

                                ArrayList dx = new ArrayList<>();
                                JSONObject jsonObject = new JSONObject(response.toString());

                                if( jsonObject.getBoolean("success") ) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                                    String _va_no;
                                    String _tgl_kunjungan;
                                    String _status;
                                    String _nama;
                                    entityStatusKarcisPetugasArrayList = new ArrayList<>();

                                    for (int i = 0; i <jsonArray.length();i++ ) {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                        _va_no = jsonObject1.getString("va_no");
                                        _tgl_kunjungan = jsonObject1.getString("tgl_kunjungan");
                                        _status = jsonObject1.getString("status");
                                        _nama = jsonObject1.getString("nama");

                                        Log.i("petugas","_va_no "+_va_no);

                                        entityStatusKarcisPetugasArrayList.add(new EntityStatusKarcisPetugas(_va_no,_tgl_kunjungan,_status,_nama));

                                    }

                                    DividerItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
                                    itemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(getApplicationContext(), R.drawable.line)));
                                    recyclerView.addItemDecoration(itemDecoration);
                                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                                    CustomAdapterEntityPetugas customAdapterEntityPetugas = new CustomAdapterEntityPetugas( entityStatusKarcisPetugasArrayList, StatusKarcisPetugasActivity.this);
                                    recyclerView.setAdapter(customAdapterEntityPetugas);
                                    layoutManager = new LinearLayoutManager(StatusKarcisPetugasActivity.this);
                                    recyclerView.setLayoutManager(layoutManager);

                                } else {
                                    // do nothing just pray to Allah
                                }
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(StatusKarcisPetugasActivity.this);
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
//                final String _email =  sessionManager.getUserDetail().get(SessionManager.key_email);


                Log.i("","text_pencarian"+ EP);

                obj.put("text_pencarian", EP);
                return obj;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

}
