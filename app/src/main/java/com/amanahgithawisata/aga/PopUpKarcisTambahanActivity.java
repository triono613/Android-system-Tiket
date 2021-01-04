package com.amanahgithawisata.aga;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amanahgithawisata.aga.Adapter.CustomAdapterEntityKarcisTambahan;
import com.amanahgithawisata.aga.Adapter.SessionManager;
import com.amanahgithawisata.aga.Helper.Help;
import com.amanahgithawisata.aga.Model.ModelHorizontalScrollKarcisTambahan;
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

public class PopUpKarcisTambahanActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SessionManager sessionManager ;
    ArrayList<ModelHorizontalScrollKarcisTambahan> modelHorizontalScrollKarcisTambahans;
    CustomAdapterEntityKarcisTambahan customAdapter ;
    ShimmerFrameLayout shimmerFrameLayout;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_karcis_tambahan);

//        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        sessionManager = new SessionManager(getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_data_karcis_tambahan);
        shimmerFrameLayout =  findViewById(R.id.shimmer_layout_popup);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        String result_dt_kdlokWis = getIntent().getStringExtra("result_dt_kdlokWis");
        String result_dt_nmlokWis = getIntent().getStringExtra("result_dt_nmlokWis");
        String result_dt_kdlokPintu = getIntent().getStringExtra("result_dt_kdlokPintu");
        String result_dt_nmlokPintu = getIntent().getStringExtra("result_dt_nmlokPintu");
        String result_dt_tgl_kunj = getIntent().getStringExtra("result_dt_tgl_kunj");
        String result_dt_url_img_lokWisOld = getIntent().getStringExtra("result_dt_url_img_lokWisOld");
        String result_dt_url_img_pintu = getIntent().getStringExtra("result_dt_url_img_pintu");

        String result_dt_jml_krcs_wisnu = getIntent().getStringExtra("result_dt_jml_krcs_wisnu");
        String result_dt_jml_krcs_wisman = getIntent().getStringExtra("result_dt_jml_krcs_wisman");
        String result_dt_ttl_jml_krcs_wisnu_wisman = getIntent().getStringExtra("result_dt_ttl_jml_krcs_wisnu_wisman");

        String result_dt_jml_krcs_tmbhn = getIntent().getStringExtra("result_dt_jml_krcs_tmbhn");
        String result_dt_ttl_krcs_wisman = getIntent().getStringExtra("result_dt_ttl_krcs_wisman");
        String result_dt_grand_ttl = getIntent().getStringExtra("result_dt_grand_ttl");

        String result_dt_id_karcis_utama = getIntent().getStringExtra("result_dt_id_karcis_utama");
        String result_dt_id_karcis_tmbhn = getIntent().getStringExtra("result_dt_id_karcis_tmbhn");

        String harga_karcis_wisata_wisnu = getIntent().getStringExtra("hrg_krcs_wisnu");
        String harga_karcis_wisata_wisman = getIntent().getStringExtra("hrg_krcs_wisman");
        String harga_karcis_wisata_tmbhn = getIntent().getStringExtra("hrg_krcs_tmbhn");
        String harga_karcis_asuransi_wisnu = getIntent().getStringExtra("hrg_krcs_asrnsi_wisnu");
        String harga_karcis_asuransi_wisman = getIntent().getStringExtra("hrg_krcs_asrnsi_wisman");
        String tgl_kujungan_2_val = getIntent().getStringExtra("tgl_kujungan_2_val");



        Log.i("","result_dt_tgl_kunj kt "+result_dt_tgl_kunj);
        Log.i("","result_dt_kdlokWis kt "+result_dt_kdlokWis);
        Log.i("","result_dt_nmlokWis kt "+result_dt_nmlokWis);
        Log.i("","result_dt_kdlokPintu kt "+result_dt_kdlokPintu);
        Log.i("","result_dt_nmlokPintu kt "+result_dt_nmlokPintu);
        Log.i("","result_dt_url_img_lokWisOld kt "+result_dt_url_img_lokWisOld);
        Log.i("","result_dt_url_img_pintu kt "+result_dt_url_img_pintu);
        Log.i("","result_dt_id_karcis_utama kt "+result_dt_id_karcis_utama);
        Log.i("","result_dt_id_karcis_tmbhn kt "+result_dt_id_karcis_tmbhn);

        Log.i("","harga_karcis_wisata_wisnu kt x"+harga_karcis_wisata_wisnu);
        Log.i("","harga_karcis_wisata_wisman kt "+harga_karcis_wisata_wisman);
        Log.i("","harga_karcis_wisata_tmbhn kt "+harga_karcis_wisata_tmbhn);
        Log.i("","harga_karcis_asuransi_wisnu kt "+harga_karcis_asuransi_wisnu);
        Log.i("","harga_karcis_asuransi_wisman kt "+harga_karcis_asuransi_wisman);



        jsonrequest("daftar_karcis_wisatawan_tambahan",
                result_dt_tgl_kunj,
                result_dt_kdlokWis,
                result_dt_nmlokWis,
                result_dt_kdlokPintu,
                result_dt_nmlokPintu,
                "",
                result_dt_url_img_lokWisOld,
                result_dt_url_img_pintu,

                result_dt_jml_krcs_wisnu,
                result_dt_jml_krcs_wisman,
                result_dt_ttl_jml_krcs_wisnu_wisman,
                result_dt_jml_krcs_tmbhn,
                result_dt_ttl_krcs_wisman,
                result_dt_grand_ttl,
                result_dt_id_karcis_utama,
                result_dt_id_karcis_tmbhn,

                harga_karcis_wisata_wisnu,
                harga_karcis_wisata_wisman,
                harga_karcis_wisata_tmbhn,
                harga_karcis_asuransi_wisnu,
                harga_karcis_asuransi_wisman,
                tgl_kujungan_2_val
        );


    }



    private void jsonrequest(String EP,
                             String tglKunj,
                             String kdLokWis,
                             String nmLokWis,
                             String kdlokPintu,
                             String nmlokPintu,
                             String nmlokOldPintu2,
                             String url_lokWis,
                             String url_pintu,
                             String result_dt_jml_krcs_wisnu,
                             String result_dt_jml_krcs_wisman,
                             String result_dt_ttl_jml_krcs_wisnu_wisman,
                             String result_dt_jml_krcs_tmbhn,
                             String result_dt_ttl_krcs_wisman,
                             String result_dt_grand_ttl,
                             String result_dt_id_karcis_utama,
                             String result_dt_id_karcis_tmbhn,

                             String harga_karcis_wisata_wisnu,
                             String harga_karcis_wisata_wisman,
                             String harga_karcis_wisata_tmbhn,
                             String harga_karcis_asuransi_wisnu,
                             String harga_karcis_asuransi_wisman,
                             String tgl_kujungan_2_val

    ) {
//        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://"+ Help.domain_api() +"/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext() );
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                response -> {
                    Log.i("", "response jsonrequest popupKarcisUtamaActivity" + response );
                    try {
//                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        if( Help.isJSONValid(response) ){
                            ArrayList dx = new ArrayList<>();
                            JSONObject jsonObject = new JSONObject(response.toString());
                            if( jsonObject.getBoolean("success") ) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                shimmerFrameLayout.stopShimmer();
                                shimmerFrameLayout.setVisibility(View.GONE);

                                Log.i("", "nmLokWis=" +nmLokWis );
                                Log.i("", "nmlokPintu=" +nmlokPintu );


                                modelHorizontalScrollKarcisTambahans = new ArrayList<>();

                                for (int i = 0; i <jsonArray.length();i++ ) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String _url_kt = jsonObject1.getString("url_image");
                                    String _id = jsonObject1.getString("id");
                                    String _kode_ksda = jsonObject1.getString("kode_ksda");
                                    String _kode_lokasi = jsonObject1.getString("kode_lokasi");
                                    String _kode_karcis = jsonObject1.getString("kode_karcis");
                                    String _nama_karcis = jsonObject1.getString("nama_karcis");
                                    String _kode_libur = jsonObject1.getString("kode_libur");
                                    String _harga_karcis_wisata = jsonObject1.getString("harga_karcis_wisata");
                                    String _harga_karcis_asuransi = jsonObject1.getString("harga_karcis_asuransi");

                                    modelHorizontalScrollKarcisTambahans.add(new ModelHorizontalScrollKarcisTambahan(
                                            url_lokWis,
                                            url_pintu,
                                            _kode_ksda,
                                            nmLokWis,
                                            _kode_lokasi,
                                            nmlokPintu,
                                            _kode_karcis,
                                            _nama_karcis,
                                            _kode_libur,
                                            _harga_karcis_wisata,
                                            _harga_karcis_asuransi,
                                            tglKunj,
                                            _id,
                                            _url_kt,
                                             result_dt_jml_krcs_wisnu,
                                             result_dt_jml_krcs_wisman,
                                             result_dt_ttl_jml_krcs_wisnu_wisman,
                                            result_dt_jml_krcs_tmbhn,
                                            result_dt_ttl_krcs_wisman,
                                            result_dt_grand_ttl,
                                            result_dt_id_karcis_utama,
                                            result_dt_id_karcis_tmbhn,

                                            harga_karcis_wisata_wisnu,
                                            harga_karcis_wisata_wisman,
                                            harga_karcis_wisata_tmbhn,
                                            harga_karcis_asuransi_wisnu,
                                            harga_karcis_asuransi_wisman,

                                            "",
                                            "",
                                            "",
                                            "",
                                            tgl_kujungan_2_val,
                                            ""
                                    ));
                                }

                                Log.i("","url_lokWis "+url_lokWis);
                                Log.i("","url_pintu"+ url_pintu);

                                customAdapter = new CustomAdapterEntityKarcisTambahan( modelHorizontalScrollKarcisTambahans, getApplicationContext() );
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

        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> obj = new HashMap<String, String>();

                Log.i("","kdlokPintu pop "+ kdlokPintu);
                Log.i("","tglKunj pop"+ tglKunj);

                obj.put("lokasi", kdlokPintu.trim() );
                obj.put("tgl_kunjungan", tglKunj.trim() );
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

}
