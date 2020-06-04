package com.example.aga;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aga.Adapter.SessionManager;
import com.example.aga.Helper.Help;
import com.example.aga.Model.SpinnerListWisata;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditKarcisStatusActivity extends AppCompatActivity {

    SessionManager sessionManager;
    TextView _tgl_kunjungan_ptgs_eks;
    TextView _jml_krcs_wisnu_ptgs_eks;
    TextView _jml_krcs_wisman_ptgs_eks;
    TextView _ttl_ptgs_eks;
    TextView _jml_krcs_wisman_tmbhn_ptgs_eks;
    TextView _ttl_tmbhn_ptgs_eks;
    TextView _grand_ttl_ptgs_eks;
    TextView _nama_pengunjung_ptgs_eks;
    TextView _hp_pengunjung_ptgs_eks;
    TextView _email_pengunjung_ptgs_eks;

    Spinner _spinner_lok_pintu_ptgs_eks;
    Spinner _spinner_karcis_utama_ptgs_eks;
    Spinner _spinner_karcis_tmbhn_ptgs_eks;
    Spinner _spinner_jns_byr_ptgs_eks;
    Button _btn_order_ptgs_eks;

    ArrayList<SpinnerListWisata> arrListWist = new ArrayList<SpinnerListWisata>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_karcis_status);

        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        final String _va = getIntent().getStringExtra("result_va");

        Log.i("","_va=="+ _va);

//        _atas_nama =(TextView) findViewById(R.id.text_atas_nama);

         _tgl_kunjungan_ptgs_eks = (TextView) findViewById(R.id.tgl_kunjungan_ptgs_eks);
         _jml_krcs_wisnu_ptgs_eks = (TextView) findViewById(R.id.jml_krcs_wisnu_ptgs_eks);
         _jml_krcs_wisman_ptgs_eks = (TextView) findViewById(R.id.jml_krcs_wisman_ptgs_eks);
         _ttl_ptgs_eks = (TextView) findViewById(R.id.ttl_ptgs_eks);
         _jml_krcs_wisman_tmbhn_ptgs_eks = (TextView) findViewById(R.id.jml_krcs_wisman_tmbhn_ptgs_eks);
         _ttl_tmbhn_ptgs_eks = (TextView) findViewById(R.id.ttl_tmbhn_ptgs_eks);
         _grand_ttl_ptgs_eks = (TextView) findViewById(R.id.grand_ttl_ptgs_eks);
         _nama_pengunjung_ptgs_eks = (TextView) findViewById(R.id.nama_pengunjung_ptgs_eks);
         _hp_pengunjung_ptgs_eks = (TextView) findViewById(R.id.hp_pengunjung_ptgs_eks);
         _email_pengunjung_ptgs_eks = (TextView) findViewById(R.id.email_pengunjung_ptgs_eks);

         _spinner_lok_pintu_ptgs_eks = (Spinner) findViewById(R.id.spinner_lok_pintu_ptgs_eks);
         _spinner_karcis_utama_ptgs_eks = (Spinner) findViewById(R.id.spinner_karcis_utama_ptgs_eks);
         _spinner_karcis_tmbhn_ptgs_eks = (Spinner) findViewById(R.id.spinner_karcis_tmbhn_ptgs_eks);
         _spinner_jns_byr_ptgs_eks = (Spinner) findViewById(R.id.spinner_jns_byr_ptgs_eks);
         _btn_order_ptgs_eks = (Button) findViewById(R.id.btn_order_ptgs_eks);

        getDataNova("edit_cari_no_va",_va);
    }



    private void getDataNova(String EP,String NOVA){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://kaffah.amanahgitha.com/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(EditKarcisStatusActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("tag", "response getDataNova =" + response );
                        try {
                            findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                            if( Help.isJSONValid(response) ){
                                JSONObject jsonObject = new JSONObject(response);
                                JSONObject jo = new JSONObject(response);
                                boolean sukses = jo.getBoolean("success");
                                String data = jo.getString("data");
                                JSONObject joChild = new JSONObject(data);

                                String kode_karcis_utama;
                                String nama_karcis_utama;
                                String jumlah_karcis_wisnu;
                                String jumlah_karcis_wisman;
                                String total_tagihan_wisnu_wisman;
                                String jumlah_karcis_tambahan;
                                String tagihan_karcis_tambahan;
                                String tagihan_total;
                                String jenis_bayar;
                                String sellular_no;
                                String alamat_email;
                                arrListWist.clear();

                                if( sukses ) {
//                                    spinnerSearchJnsClaim("cari_jenis_klaim_by_no_va",NOVA);
                                    kode_karcis_utama = joChild.getString("kode_karcis_utama");
                                    nama_karcis_utama = joChild.getString("nama_karcis_utama");
                                    jumlah_karcis_wisnu = joChild.getString("jumlah_karcis_wisnu");
                                    jumlah_karcis_wisman = joChild.getString("jumlah_karcis_wisman");
                                    total_tagihan_wisnu_wisman = joChild.getString("total_tagihan_wisnu_wisman");
                                    jumlah_karcis_tambahan = joChild.getString("jumlah_karcis_tambahan");
                                    tagihan_karcis_tambahan = joChild.getString("tagihan_karcis_tambahan");
                                    tagihan_total = joChild.getString("tagihan_total");
                                    jenis_bayar = joChild.getString("jenis_bayar");
                                    sellular_no = joChild.getString("sellular_no");
                                    alamat_email = joChild.getString("alamat_email");

                                    _jml_krcs_wisnu_ptgs_eks.setText(jumlah_karcis_wisnu.toUpperCase());
                                    _jml_krcs_wisman_ptgs_eks.setText(jumlah_karcis_wisman.toUpperCase());
                                    _jml_krcs_wisman_tmbhn_ptgs_eks.setText(jumlah_karcis_tambahan.toUpperCase());
                                    _ttl_ptgs_eks.setText(total_tagihan_wisnu_wisman.toUpperCase());


//                                    "kode_karcis_utama": "15",
//                                            "nama_karcis_utama": "PNBP Roda 2/Motor",
//                                            "jumlah_karcis_wisnu": 1,
//                                            "jumlah_karcis_wisman": 1,
//                                            "total_tagihan_wisnu_wisman": "406000.00",
//                                            "jumlah_karcis_tambahan": 1,
//                                            "tagihan_karcis_tambahan": "5000.00",
//                                            "tagihan_total": "411000.00",
//                                            "jenis_bayar": "2",
//                                            "sellular_no": "087818889525",
//                                            "alamat_email": "ikatika04@gmail.com"

                                    Log.i("","kode_karcis_utama= "+kode_karcis_utama);
                                    Log.i("","nama_karcis_utama= "+nama_karcis_utama);
                                    Log.i("","alamat_email= "+alamat_email);
//                                    _atas_nama.setText(nama.toUpperCase());
                                } else {
                                }
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(EditKarcisStatusActivity.this);
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
//                            _spinner_jns_klaim_ptgs.setAdapter(new ArrayAdapter<SpinnerJnsClaim>(EditKarcisStatusActivity.this, android.R.layout.simple_spinner_dropdown_item, arrJnsClaim) );
                        } catch (JSONException e) {
                            Log.i("triono", "error ===" + e.toString() );
                            e.printStackTrace();
                        }
                        requestQueue.stop();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("", "response =" + error.toString());
                error.printStackTrace();
                requestQueue.stop();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> obj = new HashMap<String, String>();
//                final String _nova = _no_va.getText().toString();
//                final String _nova = "8820122000000084";
                Log.i("","NOVA= "+ NOVA);
                obj.put("no_va", NOVA);
                return obj;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }
}
