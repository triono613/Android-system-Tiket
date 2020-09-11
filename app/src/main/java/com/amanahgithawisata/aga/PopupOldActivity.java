package com.amanahgithawisata.aga;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.amanahgithawisata.aga.Adapter.CustomAdapterViewPagerLokasiWisata;
import com.amanahgithawisata.aga.Adapter.SessionManager;
import com.amanahgithawisata.aga.Helper.Help;
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

import java.util.HashMap;
import java.util.Map;

public class PopupOldActivity extends AppCompatActivity {

    CustomAdapterViewPagerLokasiWisata adapter1 = new CustomAdapterViewPagerLokasiWisata();
    SessionManager sessionManager;
    TextView _txt_judul;
    ViewPager pagerViewLokasiWisata;
    TextView a1, b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_old);
        sessionManager = new SessionManager(getApplicationContext());

        String sessionIntentFlag = getIntent().getStringExtra("result_dt_flag");
        String sessionIntent_text = getIntent().getStringExtra("result_dt_text");
        String sessionIntent_judul = getIntent().getStringExtra("result_dt_judul");
        Boolean sessionIntentBerhasil = getIntent().getBooleanExtra("result_dt_berhasil",false);

        Log.i("","sessionIntent_text "+sessionIntent_text);

        _txt_judul = (TextView) findViewById(R.id.txt_judul_pop);
        _txt_judul.setText(sessionIntent_judul);
        a1 = findViewById(R.id.a1);
        b1 = findViewById(R.id.b1);


        pagerViewLokasiWisata = findViewById(R.id.pagerLokasiWisataPopup);
        horizontalLokasiWisata("daftar_lokasi_wisata");

    }




    private void horizontalLokasiWisata(String EP){


        String server_url = "http://kaffah.amanahgitha.com/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                response -> {
                    Log.i("triono", "response horizontalLokasiWisata =" + response );
                    try {

                        if( Help.isJSONValid(response) ){
                            JSONObject jsonObject = new JSONObject(response.toString());

                            String nm_obj_wisata;
                            String kd_lokasi;


                            if( jsonObject.getBoolean("success") ) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

//                                b1.setText(" "+ jsonArray.length() );

                                for (int i = 0; i <jsonArray.length();i++ ) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    nm_obj_wisata = jsonObject1.getString("nama");
                                    kd_lokasi = jsonObject1.getString("kode_ksda");

//                                    nm_obj_wisata = jsonObject1.getString("nama");
//                                    kd_ksda = jsonObject1.getString("kode_ksda");

                                    Log.i("","kd_lokasi= "+kd_lokasi);
                                    Log.i("","nm_obj_wisata= "+nm_obj_wisata);


//                                    adapter1.addCardItem(new ModelHorizontalScrollLokasiWisata(kd_lokasi,
//                                            nm_obj_wisata,
//                                            " "));
                                }
                            }
                        }

                        pagerViewLokasiWisata.setAdapter(adapter1);
                        pagerViewLokasiWisata.setOffscreenPageLimit(3);
                        pagerViewLokasiWisata.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                            private int fromPosition = 0;

                            @Override
                            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                fromPosition = position;
                            }

                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onPageSelected(int position) {
                                Log.v("onPageSelected", "scrolled from position " + fromPosition);

                                TextView _zz = findViewById(R.id.tv_judul);

//                                Log.i("","_zz "+_zz.getText(position));


                                final String _jdl = sessionManager.getDataLokWisPesankarcisWisatawanKsda().get(SessionManager.key_kd_ksda);

                                Log.i("","_jdl "+_jdl);

                                int p = position+1;
                                a1.setText(""+p);
                            }

                            @Override
                            public void onPageScrollStateChanged(int state) {
                                Log.e("","state= "+ state);

                            }
                        });


                    } catch (JSONException e) {
                        Log.i("", "error ===" + e.toString() );
                        e.printStackTrace();
                    }
                    requestQueue.stop();
                }, error -> {
            Log.i("triono", "response =" + error.toString());
            error.printStackTrace();
            requestQueue.stop();
            findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
            builder.setMessage("Terjadi Gangguan, Refresh ")
                    .setCancelable(false)
                    .setPositiveButton("Ya", (dialog, id) -> {

//                        horizontalLokasiWisata("daftar_lokasi_pintu",KSDA);
                    })
                    .setNegativeButton("Tidak", (dialog, id) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();

        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> obj = new HashMap<String, String>();
                obj.put("lokasi", "27");
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }


}
