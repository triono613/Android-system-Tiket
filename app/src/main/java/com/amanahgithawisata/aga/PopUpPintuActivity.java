package com.amanahgithawisata.aga;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amanahgithawisata.aga.Adapter.CustomAdapterEntityLokasiPintu;
import com.amanahgithawisata.aga.Adapter.SessionManager;
import com.amanahgithawisata.aga.Helper.Help;
import com.amanahgithawisata.aga.Model.ModelHorizontalScrollLokasiPintu;
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

public class PopUpPintuActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SessionManager sessionManager ;
    ArrayList<ModelHorizontalScrollLokasiPintu> modelHorizontalScrollLokasiPintus;
    CustomAdapterEntityLokasiPintu customAdapter ;
    ShimmerFrameLayout shimmerFrameLayout;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_pintu);

//        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        sessionManager = new SessionManager(getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_data_lokasi_pintu);
        shimmerFrameLayout =  findViewById(R.id.shimmer_layout_popup);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        String result_dt_kdlokPintu = getIntent().getStringExtra("result_dt_kdlokPintu");
        String result_dt_kdlokWis = getIntent().getStringExtra("result_dt_kdlokWis");
        String result_dt_nmlokWis = getIntent().getStringExtra("result_dt_nmlokWis");
        String result_dt_url_img_lokwis = getIntent().getStringExtra("result_dt_url_img_lokwis");
        String result_dt_url_img_lokPintu = getIntent().getStringExtra("result_dt_url_img_lokPintu");
        String tgl_kunj_pintu = getIntent().getStringExtra("result_dt_tgl_kunj_pintu");
        String tgl_kunj_pintu_2 = getIntent().getStringExtra("tgl_kujungan_2_val");



        String result_jml_karcis_wisnu = getIntent().getStringExtra("result_jml_karcis_wisnu");
        String result_jml_karcis_wisman = getIntent().getStringExtra("result_jml_karcis_wisman");
        String result_ttl_wisnu_wisman = getIntent().getStringExtra("result_ttl_wisnu_wisman");
        String result_jml_karcis_tmbhn = getIntent().getStringExtra("result_jml_karcis_tmbhn");
        String result_ttl_karcis_tmbhn = getIntent().getStringExtra("result_ttl_karcis_tmbhn");
        String result_grand_ttl = getIntent().getStringExtra("result_grand_ttl");
        String txt_harga_karcis_wisata_tmbhn  = getIntent().getStringExtra("txt_harga_karcis_wisata_tmbhn");


        Log.i("","result_dt_kdlokPintu pop= "+result_dt_kdlokPintu);
        Log.i("","result_dt_kdlokWis pop= "+result_dt_kdlokWis);
        Log.i("","result_dt_url_img_lokwis pop= "+result_dt_url_img_lokwis);
        Log.i("","result_dt_url_img_lokPintu pop= "+result_dt_url_img_lokPintu);

//        String parnew = sessionManager.getDataHorizontalPintu().get(SessionManager.key_kd_ksda_horz_1);;

        jsonrequest("daftar_lokasi_pintu",
                result_dt_kdlokPintu,
                result_dt_kdlokWis,
                result_dt_nmlokWis,
                result_dt_url_img_lokwis,
                result_dt_url_img_lokPintu,
                tgl_kunj_pintu,
                tgl_kunj_pintu_2,

                 result_jml_karcis_wisnu ,
                 result_jml_karcis_wisman ,
                 result_ttl_wisnu_wisman ,
                 result_jml_karcis_tmbhn ,
                 result_ttl_karcis_tmbhn ,
                 result_grand_ttl ,
                 txt_harga_karcis_wisata_tmbhn
        );

    }



    private void jsonrequest(String EP,
                             String par_kd_ksda,
                             String KdLokOld,
                             String NmLokOld,
                             String result_dt_url_img_lokwis,
                             String result_dt_url_img_lokPintu,
                             String tgl_kunj_pintu,
                             String tgl_kunj_pintu_2,

				             String result_jml_karcis_wisnu ,
                             String result_jml_karcis_wisman ,
                             String result_ttl_wisnu_wisman ,
                             String result_jml_karcis_tmbhn ,
                             String result_ttl_karcis_tmbhn ,
                             String result_grand_ttl ,
                             String txt_harga_karcis_wisata_tmbhn
    )
    {
//        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://"+ Help.domain_api() +"/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext() );
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                response -> {
                    Log.i("triono", "response jsonrequest" + response );
                    try {
//                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        if( Help.isJSONValid(response) ){

                            shimmerFrameLayout.stopShimmer();
                            shimmerFrameLayout.setVisibility(View.GONE);

                            ArrayList dx = new ArrayList<>();
                            JSONObject jsonObject = new JSONObject(response.toString());

                            Log.i("", "jsonObject ===" + jsonObject );

                            if( jsonObject.getBoolean("success") ) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                Log.i("triono", "jsonObject.getBoolean() ===" + jsonObject.getBoolean("success") );
                                Log.i("triono", "JSONArray pintu ===" + jsonArray );

                                String _kode_lokasi;
                                String _nama;
                                String _url_image;

                                modelHorizontalScrollLokasiPintus = new ArrayList<>();

                                for (int i = 0; i <jsonArray.length();i++ ) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    _kode_lokasi= jsonObject1.getString("kode_lokasi");
                                    _nama = jsonObject1.getString("nama");
                                    _url_image = jsonObject1.getString("url_image");

                                    Log.i("","_kode_lokasi popupPintu "+_kode_lokasi);
                                    Log.i("","_nama popupPintu "+_nama);

                                    modelHorizontalScrollLokasiPintus.add(new ModelHorizontalScrollLokasiPintu(
                                            _kode_lokasi,
                                            _nama,_url_image,
                                            0,
                                            result_dt_url_img_lokPintu,
                                            0,
                                            KdLokOld,
                                            NmLokOld,
                                            tgl_kunj_pintu,
                                            result_dt_url_img_lokwis,

                                            result_jml_karcis_wisnu ,
                                            result_jml_karcis_wisman ,
                                            result_ttl_wisnu_wisman ,
                                            result_jml_karcis_tmbhn ,
                                            result_ttl_karcis_tmbhn ,
                                            result_grand_ttl ,
                                            txt_harga_karcis_wisata_tmbhn,
                                            tgl_kunj_pintu_2
                                    ));
                                }

//                                modelHorizontalScrollLokasiWisatas.add(new ModelHorizontalScrollLokasiWisata("001","Pangrango ","","jl raya Puncak","Bogor"));

                                customAdapter = new CustomAdapterEntityLokasiPintu( modelHorizontalScrollLokasiPintus, getApplicationContext() );
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
                Log.i("","KdLokOld="+KdLokOld);
                obj.put("kode_ksda", KdLokOld);
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

}
