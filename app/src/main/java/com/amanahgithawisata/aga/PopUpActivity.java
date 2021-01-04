package com.amanahgithawisata.aga;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amanahgithawisata.aga.Adapter.CustomAdapterEntityLokasiWisata;
import com.amanahgithawisata.aga.Adapter.SessionManager;
import com.amanahgithawisata.aga.Helper.Help;
import com.amanahgithawisata.aga.Model.ModelHorizontalScrollLokasiWisata;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PopUpActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SessionManager sessionManager ;
    SearchView svLokWis;
    ArrayList<ModelHorizontalScrollLokasiWisata> modelHorizontalScrollLokasiWisatas;
    CustomAdapterEntityLokasiWisata customAdapter ;
    Toolbar _toolbar;
    ShimmerFrameLayout shimmerFrameLayout;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent = new Intent(PopUpActivity.this, PesanKarcisWisatawanActivity.class)
//                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        finish();
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
    }



    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);

//        jsonrequest("daftar_lokasi_wisata")

//        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        sessionManager = new SessionManager(getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_data_lokasi_wisata);
        shimmerFrameLayout =  findViewById(R.id.shimmer_layout_popup);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        _toolbar = (Toolbar)  findViewById(R.id.toolbar_new);
        setSupportActionBar(_toolbar);

        String result_dt_kd_lokwis = getIntent().getStringExtra("result_dt_kd_lokwis");
        String result_dt_nm_lokwis = getIntent().getStringExtra("result_dt_nm_lokwis");
        String result_dt_url_img_lokwis = getIntent().getStringExtra("result_dt_url_img_lokwis");
        String result_dt_tgl_kunj_lokwis = getIntent().getStringExtra("result_dt_tgl_kunj_lokwis");
        String result_dt_tgl_kunj_2_lokwis = getIntent().getStringExtra("result_dt_tgl_kunj_2_lokwis");

        String txt_kdlokPintu = getIntent().getStringExtra("txt_kdlokPintu");
        String txt_kdlokWis = getIntent().getStringExtra("txt_kdlokWis");
        String txt_nmlokPintux = getIntent().getStringExtra("txt_nmlokPintux");
        String txt_url_img_lokwisOld = getIntent().getStringExtra("txt_url_img_lokwisOld");
        String txt_url_img_lokPintu = getIntent().getStringExtra("txt_url_img_lokPintu");
        String result_dt_jml_karcis_wisnu = getIntent().getStringExtra("result_dt_jml_karcis_wisnu");
        String result_dt_jml_karcis_wisman = getIntent().getStringExtra("result_dt_jml_karcis_wisman");
        String result_dt_ttl_wisnu_wisman = getIntent().getStringExtra("result_dt_ttl_wisnu_wisman");

        String result_dt_jml_karcis_tmbhn = getIntent().getStringExtra("result_dt_jml_karcis_tmbhn");
        String result_dt_ttl_karcis_tmbhn = getIntent().getStringExtra("result_dt_ttl_karcis_tmbhn");
        String result_dt_grand_ttl = getIntent().getStringExtra("result_dt_grand_ttl_ku");
        String txt_harga_karcis_wisata_tmbhn = getIntent().getStringExtra("txt_harga_karcis_wisata_tmbhn");
        String txt_id_karcis_utama = getIntent().getStringExtra("txt_id_karcis_utama");
        String txt_id_karcis_tmbhn = getIntent().getStringExtra("txt_id_karcis_tmbhn");



        jsonrequest("daftar_lokasi_wisata",
                result_dt_kd_lokwis,
                result_dt_nm_lokwis,
                result_dt_url_img_lokwis,
                result_dt_tgl_kunj_lokwis,
                result_dt_tgl_kunj_2_lokwis,
                txt_kdlokPintu,
                txt_kdlokWis,
                txt_nmlokPintux,
                txt_url_img_lokwisOld,
                txt_url_img_lokPintu,
                result_dt_jml_karcis_wisnu ,
                result_dt_jml_karcis_wisman ,
                result_dt_ttl_wisnu_wisman ,

                result_dt_jml_karcis_tmbhn ,
                result_dt_ttl_karcis_tmbhn ,
                result_dt_grand_ttl ,
                txt_harga_karcis_wisata_tmbhn ,
                txt_id_karcis_utama ,
                txt_id_karcis_tmbhn
        );
    }



    private void jsonrequest(String EP,
                             String kd_lokwis,
                             String nm_lokwis,
                             String url_lokwis,
                             String tgl_kunj_lokwis,
                             String tgl_kunj_2_lokwis,
                             String txt_kdlokPintu,
                             String txt_kdlokWis,
                             String txt_nmlokPintux,
                             String txt_url_img_lokwisOld,
                             String txt_url_img_lokPintu,
                             String result_dt_jml_karcis_wisnu ,
                             String result_dt_jml_karcis_wisman ,
                             String result_dt_ttl_wisnu_wisman ,

                             String result_dt_jml_karcis_tmbhn ,
                             String result_dt_ttl_karcis_tmbhn ,
                             String result_dt_grand_ttl ,
                             String txt_harga_karcis_wisata_tmbhn ,
                             String txt_id_karcis_utama ,
                             String txt_id_karcis_tmbhn
    ){
//        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://"+ Help.domain_api() +"/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext() );
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                response -> {
                    Log.i("triono", "response jsonrequest" + response );
                    try {
//                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        if( Help.isJSONValid(response) ){
                            ArrayList dx = new ArrayList<>();
                            JSONObject jsonObject = new JSONObject(response.toString());
                            if( jsonObject.getBoolean("success") ) {

                                shimmerFrameLayout.stopShimmer();
                                shimmerFrameLayout.setVisibility(View.GONE);

                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                Log.i("triono", "jsonObject.getBoolean() ===" + jsonObject.getBoolean("success") );
                                String _kode_ksda;
                                String _nama;
                                String _alamat ;
                                String _kota;
                                String _url_img;
                                modelHorizontalScrollLokasiWisatas = new ArrayList<>();

                                for (int i = 0; i <jsonArray.length();i++ ) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    _kode_ksda = jsonObject1.getString("kode_ksda");
                                    _nama = jsonObject1.getString("nama");
                                    _alamat = jsonObject1.getString("alamat");
                                    _kota = jsonObject1.getString("kota");
                                    _url_img = jsonObject1.getString("url_image");

//                                    String kd_lokwis, String nm_lokwis, String url_lokwis,String tgl_kunj_lokwis

                                    modelHorizontalScrollLokasiWisatas.add(new ModelHorizontalScrollLokasiWisata(
                                            _kode_ksda,
                                            _nama,
                                            _url_img,
                                            0,
                                            _kota,
                                            _alamat,
                                            kd_lokwis,
                                            nm_lokwis,
                                            tgl_kunj_lokwis,
                                            tgl_kunj_2_lokwis,

                                            txt_kdlokPintu,
                                            txt_kdlokWis,
                                            txt_nmlokPintux,
                                            txt_url_img_lokwisOld,
                                            txt_url_img_lokPintu,
                                            result_dt_jml_karcis_wisnu ,
                                            result_dt_jml_karcis_wisman ,
                                            result_dt_ttl_wisnu_wisman ,

                                            result_dt_jml_karcis_tmbhn ,
                                            result_dt_ttl_karcis_tmbhn ,
                                            result_dt_grand_ttl ,
                                            txt_harga_karcis_wisata_tmbhn ,
                                            txt_id_karcis_utama ,
                                            txt_id_karcis_tmbhn
                                    ) );
                                }

                                customAdapter = new CustomAdapterEntityLokasiWisata( modelHorizontalScrollLokasiWisatas, getApplicationContext() );
                                recyclerView.setAdapter(customAdapter);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    requestQueue.stop();
                }, error -> {
            Log.i("", "response =" + error.toString());
            error.printStackTrace();
            requestQueue.stop();
//            findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext() );
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
                obj.put("lokasi", "");
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }



    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        MenuItem searchIem = menu.findItem(R.id.search);
        final androidx.appcompat.widget.SearchView sv = (androidx.appcompat.widget.SearchView) searchIem.getActionView();
//        final androidx.appcompat.widget.SearchView sv  = (androidx.appcompat.widget.SearchView) searchIem.getActionView();

        sv.setImeOptions(EditorInfo.IME_ACTION_DONE);
        sv.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }
            @Override
            public boolean onQueryTextChange(String nextText) {
                nextText = nextText.toLowerCase();
                ArrayList<ModelHorizontalScrollLokasiWisata> dataFilter =new ArrayList<ModelHorizontalScrollLokasiWisata>();
                for(ModelHorizontalScrollLokasiWisata data : modelHorizontalScrollLokasiWisatas){
                    String jdl = data.getJudul().toLowerCase();
                    String almt = data.getAlmt().toLowerCase();
                    String kota = data.getKota().toLowerCase();
                    if(jdl.contains(nextText)){
                        dataFilter.add(data);
                    }
                    else if(almt.contains(nextText)){
                        dataFilter.add(data);
                    }
                    else if(kota.contains(nextText)){
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
