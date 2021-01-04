package com.amanahgithawisata.aga;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.amanahgithawisata.aga.Adapter.SessionManager;
import com.amanahgithawisata.aga.Helper.Help;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class QrCodeScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    public ZXingScannerView mScannerView;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        sessionManager = new SessionManager(getApplicationContext());
    }


    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }


    @Override
    public void handleResult(Result result) {

//        DashboardPetugasActivity._text_dashboard_scan.setText(result.getText());
//        onBackPressed();

        Log.i("","result.getText()="+result.getText().trim());
//        String _NO_VA = "8820122000000105";
        postScannerVa("check_kedatangan", result.getText().trim());
    }


    private void postScannerVa(String EP,String _VA){
        String server_url = "http://"+ Help.domain_api() +"/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(QrCodeScannerActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                response -> {
                    Log.i("tag", "response postScannerVa =" + response );
                    try {
                        if( Help.isJSONValid(response) ){
                            JSONObject jsonObject = new JSONObject(response);

                                    Log.i("","qrcode success "+jsonObject.getBoolean("success"));


                                    String data = jsonObject.getString("data");
                                    JSONObject jsonObject1 = new JSONObject(data);

                                    String  _id = jsonObject1.getString("id");
                                    boolean  _flag = jsonObject1.getBoolean("flag");
                                    String _nama = jsonObject1.getString("nama");
                                    String _alamat_email = jsonObject1.getString("alamat_email");
                                    String _tgl_kunjungan = jsonObject1.getString("tgl_kunjungan");
                                    String _tagihan_karcis_utama = jsonObject1.getString("tagihan_karcis_utama");
                                    String _tagihan_total = jsonObject1.getString("tagihan_total");
                                    String _keterangan = jsonObject1.getString("keterangan");


                                    Log.i("","qrcode _flag "+_flag);
                                    Log.i("","_keterangan "+_keterangan);

//                                        _flag= true;

                                    if( _flag){
                                        Intent i = new Intent(QrCodeScannerActivity.this, SuccessRegistrasiWisatawanActivity.class);
                                        i.putExtra("result_dt_ket", _keterangan);
                                        i.putExtra("result_dt_email",_alamat_email);
                                        i.putExtra("result_dt_berhasil", _flag);
                                        i.putExtra("result_dt_flag", "flagPesanKarcisPetugas");                                           startActivity(i);
                                    } else {
                                        Intent i = new Intent(QrCodeScannerActivity.this, errorPageActivity.class);
                                        i.putExtra("result_dt_ket", _keterangan);
                                        i.putExtra("result_dt_email",_alamat_email);
                                        i.putExtra("result_dt_berhasil", _flag);
                                        i.putExtra("result_dt_flag", "qrcode");
                                        startActivity(i);
                                    }


                        }
                    } catch (JSONException e) {
                        Log.i("", "error =" + e.toString() );
                        e.printStackTrace();
                    }
                    requestQueue.stop();
                }, error -> {
                    Log.i("", "response =" + error.toString());
                    error.printStackTrace();
                    requestQueue.stop();
                    findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                    AlertDialog.Builder builder = new AlertDialog.Builder(QrCodeScannerActivity.this);
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
                obj.put("va_no", _VA.trim() );
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
    }

    @Override
    protected void onDestroy() {
        mScannerView.stopCamera();
        super.onDestroy();
    }



    //    @Override
//    public void handleResult(Result rawResult) {
//        Log.v("TAG", rawResult.getText()); // Prints scan results
//        Log.v("TAG", rawResult.getBarcodeFormat().toString());
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Scan Result");
//        builder.setMessage(rawResult.getText());
//        AlertDialog alertRes = builder.create();
//        alertRes.show();
//
//        mScannerView.resumeCameraPreview(this);
//    }

}
