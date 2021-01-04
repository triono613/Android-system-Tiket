package com.amanahgithawisata.aga.Adapter;

        import android.os.Bundle;
import android.util.Log;
        import android.view.View;

        import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amanahgithawisata.aga.Helper.Help;
import com.amanahgithawisata.aga.Model.ModelHorizontalScrollKarcisUtama;
import com.amanahgithawisata.aga.R;
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

public class PetugasAdapterKuActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SessionManager sessionManager ;
    ArrayList<ModelHorizontalScrollKarcisUtama> modelHorizontalScrollKarcisUtamas;
    CustomAdapterEntityKarcisUtamaPetugas customAdapter ;
    ShimmerFrameLayout shimmerFrameLayout;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petugas_adapter_ku);

        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        sessionManager = new SessionManager(getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_data_karcis_utama);
        shimmerFrameLayout =  findViewById(R.id.shimmer_layout_popup);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



        String result_dt_kdlokWis = getIntent().getStringExtra("result_dt_kdlokWis");
        String result_dt_nmlokWis = getIntent().getStringExtra("result_dt_nmlokWis");
        String result_dt_kdlokPintu = getIntent().getStringExtra("result_dt_kd_lokpintu");
        String result_dt_nmlokPintu = getIntent().getStringExtra("result_dt_nm_lokpintu");
        String result_dt_tgl_kunj = getIntent().getStringExtra("result_dt_tgl_kunj_lokwis");
        String result_dt_url_img_lokWisOld = getIntent().getStringExtra("result_dt_url_img_lokWisOld");
        String result_dt_url_img_pintu = getIntent().getStringExtra("result_dt_url_img_pintu");

        String result_dt_jml_karcis_wisnu = getIntent().getStringExtra("txt_krcs_wisnu_ptgs");
        String result_dt_jml_karcis_wisman = getIntent().getStringExtra("txt_krcs_wisman_ptgs");
        String result_dt_ttl_wisnu_wisman = getIntent().getStringExtra("txt_ttl_ptgs");
        String result_dt_jml_karcis_tmbhn = getIntent().getStringExtra("txt_jml_krcs_wisman_tmbhn_ptgs");
        String result_dt_ttl_karcis_tmbhn = getIntent().getStringExtra("txt_ttl_tmbhn_ptgs");
        String result_dt_grand_ttl = getIntent().getStringExtra("txt_grand_ttl_ptgs");
        String result_dt_harga_karcis_wisata_tmbhn = getIntent().getStringExtra("result_dt_harga_karcis_wisata_tmbhn");
        String result_dt_id_karcis_utama= getIntent().getStringExtra("txt_id_ku");
        String result_dt_id_karcis_tmbhn= getIntent().getStringExtra("txt_id_kt");

        String nama_pengunjung = getIntent().getStringExtra("nama_pengunjung");
        String hp_pengunjung = getIntent().getStringExtra("hp_pengunjung");
        String email_pengunjung = getIntent().getStringExtra("email_pengunjung");
        String mode_pembayaran = getIntent().getStringExtra("mode_pembayaran");
        String nama_pembayaran = getIntent().getStringExtra("nama_pembayaran");
        String result_dt_tgl_kunj_2_lokwis = getIntent().getStringExtra("result_dt_tgl_kunj_2_lokwis");




        Log.i("","result_dt_tgl_kunj ku "+result_dt_tgl_kunj);
        Log.i("","result_dt_kdlokWis ku "+result_dt_kdlokWis);
        Log.i("","result_dt_nmlokWis ku "+result_dt_nmlokWis);
        Log.i("","result_dt_kdlokPintu ku "+result_dt_kdlokPintu);
        Log.i("","result_dt_nmlokPintu ku "+result_dt_nmlokPintu);
        Log.i("","result_dt_url_img_lokWisOld ku "+result_dt_url_img_lokWisOld);
        Log.i("","result_dt_url_img_pintu ku "+result_dt_url_img_pintu);
        Log.i("","result_dt_harga_karcis_wisata_tmbhn ku "+result_dt_harga_karcis_wisata_tmbhn);


//        BSheetListQuota bSheetKarcisUtamax = new BSheetListQuota();
//        bSheetKarcisUtamax.show(getSupportFragmentManager(),"TAG");



        jsonrequest("daftar_karcis_wisatawan_utama",
                result_dt_tgl_kunj,
                result_dt_kdlokWis,
                result_dt_nmlokWis,
                result_dt_kdlokPintu,
                result_dt_nmlokPintu,
                "",
                result_dt_url_img_lokWisOld,
                result_dt_url_img_pintu,

                result_dt_jml_karcis_wisnu,
                result_dt_jml_karcis_wisman,
                result_dt_ttl_wisnu_wisman,
                result_dt_jml_karcis_tmbhn,
                result_dt_ttl_karcis_tmbhn,
                result_dt_grand_ttl,
                result_dt_harga_karcis_wisata_tmbhn,
                result_dt_id_karcis_utama,
                result_dt_id_karcis_tmbhn,

                nama_pengunjung,
                hp_pengunjung,
                email_pengunjung,
                mode_pembayaran,
                result_dt_tgl_kunj_2_lokwis,
                nama_pembayaran
        );
    }


    private void jsonrequest(String EP, String tglKunj, String kdLokWis,
                             String nmLokWis,String kdlokPintu,String nmlokPintu,
                             String nmlokOldPintu2,
                             String url_lokWis,
                             String url_pintu,
                             String result_dt_jml_karcis_wisnu,
                             String result_dt_jml_karcis_wisman,
                             String result_dt_ttl_wisnu_wisman,
                             String result_dt_jml_karcis_tmbhn,
                             String result_dt_ttl_karcis_tmbhn,
                             String result_dt_grand_ttl,
                             String result_dt_harga_karcis_wisata_tmbhn,
                             String result_dt_id_karcis_utama,
                             String result_dt_id_karcis_tmbhn,
                             String nama_pengunjung,
                             String hp_pengunjung,
                             String email_pengunjung,
                             String mode_pembayaran,
                             String result_dt_tgl_kunj_2_lokwis,
                             String nama_pembayaran
    ) {
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://"+ Help.domain_api() +"/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext() );
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                response -> {
                    Log.i("triono", "response jsonrequest popupKarcisUtamaActivity" + response );
                    try {
                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        if( Help.isJSONValid(response) ){
                            ArrayList dx = new ArrayList<>();
                            JSONObject jsonObject = new JSONObject(response.toString());
                            if( jsonObject.getBoolean("success") ) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                shimmerFrameLayout.stopShimmer();
                                shimmerFrameLayout.setVisibility(View.GONE);

                                modelHorizontalScrollKarcisUtamas = new ArrayList<>();

                                for (int i = 0; i <jsonArray.length();i++ ) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String _url_ku = jsonObject1.getString("url_image");
                                    String _id = jsonObject1.getString("id");
                                    String _kode_ksda = jsonObject1.getString("kode_ksda");
                                    String _kode_lokasi = jsonObject1.getString("kode_lokasi");
                                    String _kode_karcis = jsonObject1.getString("kode_karcis");
                                    String _nama_karcis = jsonObject1.getString("nama_karcis");
                                    String _kode_libur = jsonObject1.getString("kode_libur");
                                    String _harga_karcis_wisata_wisnu = jsonObject1.getString("harga_karcis_wisata_wisnu");
                                    String _harga_karcis_wisata_wisman = jsonObject1.getString("harga_karcis_wisata_wisman");
                                    String _harga_karcis_asuransi_wisnu = jsonObject1.getString("harga_karcis_asuransi_wisnu");
                                    String _harga_karcis_asuransi_wisman = jsonObject1.getString("harga_karcis_asuransi_wisman");

                                    modelHorizontalScrollKarcisUtamas.add(new ModelHorizontalScrollKarcisUtama(
                                            _url_ku,
                                            url_lokWis,
                                            url_pintu,
                                            _kode_ksda,
                                            nmLokWis,
                                            _kode_lokasi,
                                            nmlokPintu,
                                            _kode_karcis,
                                            _nama_karcis,
                                            _kode_libur,
                                            _harga_karcis_wisata_wisnu,
                                            _harga_karcis_wisata_wisman,
                                            _harga_karcis_asuransi_wisnu,
                                            _harga_karcis_asuransi_wisman,
                                            tglKunj,
                                            _id,
                                            result_dt_jml_karcis_wisnu,
                                            result_dt_jml_karcis_wisman,
                                            result_dt_ttl_wisnu_wisman,
                                            result_dt_jml_karcis_tmbhn,
                                            result_dt_ttl_karcis_tmbhn,
                                            result_dt_grand_ttl,
                                            result_dt_harga_karcis_wisata_tmbhn,

                                            result_dt_id_karcis_utama,
                                            result_dt_id_karcis_tmbhn,

                                            nama_pengunjung,
                                            hp_pengunjung,
                                            email_pengunjung,
                                            mode_pembayaran,
                                            result_dt_tgl_kunj_2_lokwis,
                                            nama_pembayaran
                                    ));

                                    Log.i("","_url_ku popup"+ _url_ku);
                                }

                                Log.i("","url_lokWis "+url_lokWis);
                                Log.i("","url_pintu"+ url_pintu);



                                customAdapter = new CustomAdapterEntityKarcisUtamaPetugas( modelHorizontalScrollKarcisUtamas, getApplicationContext() );
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

                Log.i("","kdlokPintu ku= "+kdlokPintu);
                Log.i("","tglKunj ku= "+tglKunj);

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
