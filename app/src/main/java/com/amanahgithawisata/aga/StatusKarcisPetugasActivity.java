package com.amanahgithawisata.aga;

import android.annotation.SuppressLint;
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

import com.amanahgithawisata.aga.Adapter.CustomAdapterEntityPetugas;
import com.amanahgithawisata.aga.Adapter.SessionManager;
import com.amanahgithawisata.aga.Helper.Help;
import com.amanahgithawisata.aga.Model.EntityStatusKarcisPetugas;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class StatusKarcisPetugasActivity extends AppCompatActivity {

    private final String JSON_URL ="http://"+ Help.domain_api() +"/~androidwisata/?data=informasi_status_karcis";
    private JsonArrayRequest jsonArrayRequest;
    private RequestQueue requestQueue;
    RecyclerView recyclerView;
    Toolbar _toolbar;
    MenuItem _cariToolbar;
    Button _btn_entity_edit;


    RecyclerView.LayoutManager layoutManager;
    SessionManager sessionManager ;
    ArrayList<EntityStatusKarcisPetugas> entityStatusKarcisPetugasArrayList;
    CustomAdapterEntityPetugas customAdapter ;

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

        @SuppressLint("InflateParams") View inflatedView = getLayoutInflater().inflate(R.layout.entity_status_karcis_wisatawan, null);
        _btn_entity_edit = (Button) inflatedView.findViewById(R.id.btn_entity_edit);
//        _btn_entity_edit.setText("Hello!");

        _toolbar = (Toolbar)  findViewById(R.id.toolbar_new);
        setSupportActionBar(_toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_data_status_karcis_ptgs);

//        _btn_entity_edit = (Button) findViewById(R.id.btn_entity_edit_ptgs);


        _btn_entity_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sessionManager.isLoggedIn()) {
                    Intent i = new Intent(StatusKarcisPetugasActivity.this,EditPasswordPetugasActivity.class);
                    startActivity(i);
                }

            }
        });


//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setHasFixedSize(true);


        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://"+ Help.domain_api() +"/~androidwisata/?data=informasi_status_karcis_petugas";
        final RequestQueue requestQueue = Volley.newRequestQueue(StatusKarcisPetugasActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                response -> {
                    Log.i("triono", "response 1 ===" + response );
                    try {
                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        if( Help.isJSONValid(response) ){
                            ArrayList dx = new ArrayList<>();
                            JSONObject jsonObject = new JSONObject(response);

                            if( jsonObject.getBoolean("success") ) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                Log.i("triono", "jsonObject.getBoolean() ===" + jsonObject.getBoolean("success") );
                                String _va_no;
                                String _tgl_kunjungan;
                                String _status;
                                String _nm_objk_wis;
                                String _email;
//                                    entityStatusKarcisPetugasArrayList = new ArrayList<>();
                                entityStatusKarcisPetugasArrayList.clear();

                                Log.i("","jsonArray.length() "+jsonArray.length());
                                for (int i = 0; i <jsonArray.length();i++ ) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    _va_no = jsonObject1.getString("va_no");
                                    _tgl_kunjungan = jsonObject1.getString("tgl_kunjungan");
                                    _status = jsonObject1.getString("status");
                                    _nm_objk_wis = jsonObject1.getString("nama");
                                    _email = jsonObject1.getString("alamat_email");

                                    Log.i("petugas","_i "+i);
                                    Log.i("petugas","_va_no "+_va_no);

                                    entityStatusKarcisPetugasArrayList.add(new EntityStatusKarcisPetugas(_va_no,_tgl_kunjungan,_status,_nm_objk_wis,_email));
//                                        entityStatusKarcisPetugasArrayList.add(new EntityStatusKarcisPetugas("20122020000000098","2020-01-02","unPaid","gunung pangrango"));

                                }
                                customAdapter = new CustomAdapterEntityPetugas( entityStatusKarcisPetugasArrayList, getApplicationContext() );
                                recyclerView.setAdapter(customAdapter);
                            }
//                                entityStatusKarcisPetugasArrayList.add(new EntityStatusKarcisPetugas("20122020000000098","2020-01-02","unPaid","gunung pangrango"));
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(StatusKarcisPetugasActivity.this);
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
                obj.put("text_pencarian", "");
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        customAdapter = new CustomAdapterEntityPetugas(entityStatusKarcisPetugasArrayList,this);
        recyclerView.setAdapter(customAdapter);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }











    private void fillExampleList() {
        entityStatusKarcisPetugasArrayList = new ArrayList<>();
        entityStatusKarcisPetugasArrayList.add(new EntityStatusKarcisPetugas("00001234" ,"One", "Ten","gunug gede 1",""));
        entityStatusKarcisPetugasArrayList.add(new EntityStatusKarcisPetugas("00001235" ,"two", "Ten","gunug gede 2",""));
        entityStatusKarcisPetugasArrayList.add(new EntityStatusKarcisPetugas("00001236" ,"three", "Ten","gunug gede 3",""));


        DividerItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(getApplicationContext(), R.drawable.line)));
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        customAdapter = new CustomAdapterEntityPetugas(entityStatusKarcisPetugasArrayList,this);
        recyclerView.setAdapter(customAdapter);
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
        customAdapter = new CustomAdapterEntityPetugas(entityStatusKarcisPetugasArrayList,this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(customAdapter);

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
//                jsonrequest(query);
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
                    else if(tgl.contains(nextText)){
                        dataFilter.add(data);
                    }
                    else if(status.contains(nextText)){
                        dataFilter.add(data);
                    }

                }
                customAdapter.setFilter(dataFilter);
                return true;

            }
        });
        return true;
    }



}
