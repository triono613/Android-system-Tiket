package com.amanahgithawisata.aga;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amanahgithawisata.aga.Adapter.ReportKarcisAdapter;
import com.amanahgithawisata.aga.Adapter.SessionManager;
import com.amanahgithawisata.aga.Helper.Help;
import com.amanahgithawisata.aga.Model.ModelReportKarcis;
import com.amanahgithawisata.aga.Model.SpinnerListWisata;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ReportKarcisActivity extends AppCompatActivity {


    SessionManager sessionManager;
    ReportKarcisAdapter reportKarcisAdapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    Button btn_find_karcis;
    Button btn_update;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_karcis);
        sessionManager = new SessionManager(getApplicationContext());

        btn_find_karcis = findViewById(R.id.btn_find_karcis);
        btn_update = findViewById(R.id.btn_update);

        findViewById(R.id.loadingPanel).setVisibility(View.GONE);

        recyclerView = findViewById(R.id.recyclerViewReportKarcisList);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);





        btn_find_karcis.setOnClickListener(v -> {

            TextView from_date;
            TextView end_date;
            Button btn_update;
            Spinner optionPintu;
            ArrayList<SpinnerListWisata> arrListWisata = new ArrayList<SpinnerListWisata>();

            View modelBottomSheet = LayoutInflater.from(v.getContext()).inflate(R.layout.layout_bottom_sheet_report_karcis, null);
            BottomSheetDialog dialog = new BottomSheetDialog(v.getContext());

            from_date = modelBottomSheet.findViewById(R.id.txt_from_date);
            end_date = modelBottomSheet.findViewById(R.id.txt_end_date);
            optionPintu = modelBottomSheet.findViewById(R.id.optionPintu);
            btn_update = modelBottomSheet.findViewById(R.id.btn_update);

//            txtview_update.setText("Tambah ");
//            txtview_quota.setText("Tambah ");
            btn_update.setText("Tambah ");




                findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
                String EP = "daftar_lokasi_pintu";
                String server_url = "http://kaffah.amanahgitha.com/~androidwisata/?data="+ EP;
                final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("tag", "response spinnerLokPintuPtgs =" + response );
                                try {

                                    if( Help.isJSONValid(response) ){
                                        JSONObject jsonObject = new JSONObject(response);

                                        Log.i("","spinner pintu response= "+response);


                                        arrListWisata.clear();

                                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                                        if( jsonObject.getBoolean("success") ) {

                                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                                            for (int i = 0; i <jsonArray.length();i++ ) {
                                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                                String kode_lokasi = jsonObject1.getString("kode_lokasi");
                                                String nama = jsonObject1.getString("nama");

                                                Log.i("tag","kode_lokasi= "+kode_lokasi);
                                                Log.i("tag","nama= "+nama);
                                                arrListWisata.add(new SpinnerListWisata(kode_lokasi,nama,"","",""));

                                            }
                                        }
                                    }
                                    optionPintu.setAdapter(new ArrayAdapter<SpinnerListWisata>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,arrListWisata) );
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                requestQueue.stop();
                            }
                        }, error -> {
                    error.printStackTrace();
                    requestQueue.stop();
                }
                ) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> obj = new HashMap<String, String>();
                        final String KD_KSDA =  sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi).trim();
                        Log.i("","KD_KSDA x"+KD_KSDA);
                        obj.put("kode_ksda", KD_KSDA);
                        return obj;
                    }
                };
                int socketTimeout = 0;
                RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                stringRequest.setRetryPolicy(policy);
                requestQueue.add(stringRequest);



            modelBottomSheet.findViewById(R.id.btn_update).setOnClickListener(v1 -> {



//                spinnerLokPintu("daftar_lokasi_pintu","");

                String _fromdate = from_date.getText().toString().trim();
                String _end_date = end_date.getText().toString().trim();
//                String _optionPintu = optionPintu.getSelectedItem().toString();

                Spinner spinnerPintu = (Spinner) findViewById(R.id.optionPintu);
                String _optionPintu = spinnerPintu.getSelectedItem().toString();

                Log.i("","_fromdate "+_fromdate);
                Log.i("","_end_date "+_end_date);
                Log.i("","_optionPintu "+_optionPintu);

                if(TextUtils.isEmpty(_fromdate )) {
                    from_date.setError("From date Masih Kosong ");
                }
                else if(TextUtils.isEmpty(_end_date )) {
                    end_date.setError("From date Masih Kosong ");
                }
                else if(TextUtils.isEmpty(_optionPintu )) {
//                    optionPintu.setError("From date Masih Kosong ");
                }
                else {

                    final String key_kdksda =  sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi).trim();
                    report_karcis_level("report_karcis_level_2",
                            key_kdksda,"",_fromdate ,_end_date,_optionPintu
                    );
                    dialog.dismiss();
                }


            });


            dialog.setContentView(modelBottomSheet);
            dialog.show();


            from_date.setOnFocusChangeListener((v13, hasFocus) -> {
                if (hasFocus){
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR);
                    int mMonth = c.get(Calendar.MONTH);
                    int mDay = c.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(v13.getContext(),
                            (view, year, monthOfYear, dayOfMonth) -> {
                                @SuppressLint("SimpleDateFormat") SimpleDateFormat fr = new SimpleDateFormat("dd-MM-yyyy");
                                from_date.setText( year + "-" + (monthOfYear + 1)  + "-" +dayOfMonth );

                            },mYear , mMonth,mDay );
                    datePickerDialog.show();

                }
            });

            end_date.setOnFocusChangeListener((v13, hasFocus) -> {
                if (hasFocus){
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR);
                    int mMonth = c.get(Calendar.MONTH);
                    int mDay = c.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(v13.getContext(),
                            (view, year, monthOfYear, dayOfMonth) -> {
                                @SuppressLint("SimpleDateFormat") SimpleDateFormat fr = new SimpleDateFormat("dd-MM-yyyy");
                                end_date.setText( year + "-" + (monthOfYear + 1)  + "-" +dayOfMonth );

                            },mYear , mMonth,mDay );
                    datePickerDialog.show();

                }
            });


        });


        String _from_date = getIntent().getStringExtra("_from_date");
        String _end_date = getIntent().getStringExtra("_end_date");
        String _option = getIntent().getStringExtra("_option");


        Log.i("","_from_date "+_from_date);
        Log.i("","_end_date "+_end_date);
        Log.i("","_option "+_option);

        final String key_kdksda =  sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi).trim();
        final String alamat_email =  sessionManager.getUserDetail().get(SessionManager.key_email).trim();
        final String kode_pintu =  sessionManager.getUserDetail().get(SessionManager.key_kode_pintu).trim();

        Log.i("","key_kdksda "+key_kdksda);

        if (_from_date != null && _end_date != null) {

            report_karcis_level("report_karcis_level_2",
                    alamat_email,
                    key_kdksda,
                    _from_date,
                    _end_date,
                    kode_pintu
            );
        }






    }








    public void report_karcis_level(String EP,
                                    String alamat_email,
                                    String kode_ksda,
                                    String from_date,
                                    String thru_date,
                                    String kode_pintu )
    {
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        List<ModelReportKarcis> modelReportKarcisList = new ArrayList<>();


        String server_url = "http://kaffah.amanahgitha.com/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue( getApplicationContext() );
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                response -> {

                    try {
                        if( Help.isJSONValid(response) ){
                            JSONObject jObj = new JSONObject(response);
                            String data = jObj.getString("data");
                            JSONObject jsonObject = new JSONObject(data);


                            if( jsonObject.getBoolean("success") ) {
                                findViewById(R.id.loadingPanel).setVisibility(View.GONE);

                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i <jsonArray.length();i++ ) {
                                    JSONObject jInnerObject = new JSONObject();

                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                    String nama_lokasi_wisata =  jsonObject1.getString("nama_lokasi_wisata");
                                    String nama_lokasi_pintu =  jsonObject1.getString("nama_lokasi_pintu");
                                    String status_karcis =  jsonObject1.getString("status_karcis");
                                    String nama_karcis =  jsonObject1.getString("nama_karcis");
                                    String jumlah_transaksi =  jsonObject1.getString("jumlah_transaksi");
                                    String jumlah_wisnu =  jsonObject1.getString("jumlah_wisnu");

                                    String jumlah_tambahan =  jsonObject1.getString("jumlah_tambahan");
                                    String tagihan_wisata =  jsonObject1.getString("tagihan_wisata");
                                    String tagihan_asuransi =  jsonObject1.getString("tagihan_asuransi");



                                    Log.i("tag","nama_lokasi_wisata= "+nama_lokasi_wisata );
                                    Log.i("tag","nama_lokasi_pintu= "+nama_lokasi_pintu );

                                    modelReportKarcisList.add(new ModelReportKarcis(
                                            nama_lokasi_wisata,
                                            nama_lokasi_pintu,
                                            status_karcis,
                                            nama_karcis,
                                            jumlah_transaksi,
                                            jumlah_wisnu,
                                            jumlah_tambahan,
                                            tagihan_wisata,
                                            tagihan_asuransi
                                            )
                                    );

                                }
                                reportKarcisAdapter = new ReportKarcisAdapter(modelReportKarcisList);
                                recyclerView.setAdapter(reportKarcisAdapter);
                            }
                        }
                    } catch (JSONException e) {
                        Log.i("", "error ===" + e.toString() );
                        e.printStackTrace();
                    }
                    requestQueue.stop();
                }, error -> {
            error.printStackTrace();
            requestQueue.stop();
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> obj = new HashMap<String, String>();



                Log.i("","alamat_email "+alamat_email);
                Log.i("","from_date "+from_date);
                Log.i("","thru_date "+thru_date);
                Log.i("","kode_ksda "+kode_ksda);
                Log.i("","kode_pintu "+kode_pintu);



                obj.put("alamat_email", alamat_email);
                obj.put("from_date", from_date);
                obj.put("thru_date", thru_date);
                obj.put("kode_ksda", kode_ksda);
                obj.put("id", "");
                obj.put("kode_pintu", kode_pintu);
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }




}

