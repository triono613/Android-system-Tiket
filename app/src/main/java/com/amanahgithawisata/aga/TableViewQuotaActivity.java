package com.amanahgithawisata.aga;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amanahgithawisata.aga.Adapter.SessionManager;
import com.amanahgithawisata.aga.Adapter.TableViewQuotaAdapter;
import com.amanahgithawisata.aga.Helper.Help;
import com.amanahgithawisata.aga.Model.ModelQuotaExample;
import com.amanahgithawisata.aga.Model.ModelQuotaLokWis;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
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


public class TableViewQuotaActivity extends AppCompatActivity {
    SessionManager sessionManager;
    TableViewQuotaAdapter tableViewQuotaAdapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    Button btn_add;



    @Override
    public void onBackPressed() {

//        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), DashboardPetugasActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_quota);
        sessionManager = new SessionManager(getApplicationContext());

        btn_add = findViewById(R.id.btn_add);


        findViewById(R.id.loadingPanel).setVisibility(View.GONE);


        recyclerView = findViewById(R.id.recyclerViewDeliveryProductList);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        calculateData();


        btn_add.setOnClickListener(v -> {

            TextView from_date;
            TextView thru_date;
            TextView quota;
            TextView txtview_update;
            TextView txtview_quota;
            ProgressBar progressBar;
            Button btn_update;
            TextView id;


            View modelBottomSheet = LayoutInflater.from(v.getContext()).inflate(R.layout.layout_bottom_sheet_edit_quota, null);
            BottomSheetDialog dialog = new BottomSheetDialog(v.getContext());

             id = modelBottomSheet.findViewById(R.id.txt_id);
             from_date = modelBottomSheet.findViewById(R.id.txt_from_date);
             thru_date = modelBottomSheet.findViewById(R.id.txt_thru_date);
             quota = modelBottomSheet.findViewById(R.id.txt_quota);
             txtview_update = modelBottomSheet.findViewById(R.id.txtview_update);
             txtview_quota = modelBottomSheet.findViewById(R.id.txtview_quota);
             btn_update = modelBottomSheet.findViewById(R.id.btn_update);
             progressBar = modelBottomSheet.findViewById(R.id.loadingPanelProgressBar);

            txtview_update.setText("Tambah ");
            txtview_quota.setText("Tambah ");
            btn_update.setText("Tambah ");



            modelBottomSheet.findViewById(R.id.btn_update).setOnClickListener(v1 -> {

                 String _fromdate = from_date.getText().toString().trim();
                 String _thru_date = thru_date.getText().toString().trim();
                 String _quota = quota.getText().toString().trim();

                Log.i("","_fromdate "+_fromdate);
                Log.i("","_thru_date "+_thru_date);
                Log.i("","_quota "+_quota);

                if(TextUtils.isEmpty(_fromdate )) {
                    from_date.setError("From date Masih Kosong ");
                }
                else if(TextUtils.isEmpty(_thru_date )) {
                    thru_date.setError("From date Masih Kosong ");
                }
                else if(TextUtils.isEmpty(_quota )) {
                    quota.setError("From date Masih Kosong ");
                }
                else {

                    final String key_kdksda =  sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi).trim();
                    update_quota_lokasi_wisata("update_quota_lokasi_wisata",
                            key_kdksda,"",_fromdate ,_thru_date,_quota
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

            thru_date.setOnFocusChangeListener((v13, hasFocus) -> {
                if (hasFocus){
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR);
                    int mMonth = c.get(Calendar.MONTH);
                    int mDay = c.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(v13.getContext(),
                            (view, year, monthOfYear, dayOfMonth) -> {
                                @SuppressLint("SimpleDateFormat") SimpleDateFormat fr = new SimpleDateFormat("dd-MM-yyyy");
                                thru_date.setText( year + "-" + (monthOfYear + 1)  + "-" +dayOfMonth );

                            },mYear , mMonth,mDay );
                    datePickerDialog.show();

                }
            });


        });

        String _id = getIntent().getStringExtra("_id");
        String _from_date = getIntent().getStringExtra("_from_date");
        String _thru_date = getIntent().getStringExtra("_thru_date");
        String _quota = getIntent().getStringExtra("_quota");

        Log.i("","_id "+_id);
        Log.i("","_from_date "+_from_date);
        Log.i("","_thru_date "+_thru_date);
        Log.i("","_quota "+_quota);

        final String key_kdksda =  sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi).trim();

        Log.i("","key_kdksda "+key_kdksda);

        if (_id != null) {
                update_quota_lokasi_wisata("update_quota_lokasi_wisata",
                        key_kdksda,_id,
                        _from_date,
                        _thru_date,
                        _quota
                );
        }

//        recyclerView = findViewById(R.id.recyclerViewDeliveryProductList);
//        TableViewQuotaAdapter adapter = new TableViewQuotaAdapter(getQuotalist());
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setAdapter(adapter);

    }

    public List<ModelQuotaLokWis> getQuotalist(){
        List<ModelQuotaLokWis> modelQuotaLokWisList = new ArrayList<>();

        int a;
        for (a=0; a<20; a++)
              {
                  modelQuotaLokWisList.add(new ModelQuotaLokWis("1", "2020-02-12","2020-01-02","1300", "500", String.valueOf(a)));
              }
//        modelQuotaLokWisList.add(new ModelQuotaLokWis("1", "2020-02-12","2020-01-02","1300", "500", "50"));
//        modelQuotaLokWisList.add(new ModelQuotaLokWis("1", "2020-02-12","2020-01-02","1300", "500", "50"));
//        modelQuotaLokWisList.add(new ModelQuotaLokWis("1", "2020-02-12","2020-01-02","1300", "500", "50"));
//        modelQuotaLokWisList.add(new ModelQuotaLokWis("1", "2020-02-12","2020-01-02","1300", "500", "50"));
//        modelQuotaLokWisList.add(new ModelQuotaLokWis("1", "2020-02-12","2020-01-02","1300", "500", "50"));

        return modelQuotaLokWisList;
    }

    private List<ModelQuotaExample> getMovieList() {
        List<ModelQuotaExample> movieList = new ArrayList<>();
        // src Wikipedia
        movieList.add(new ModelQuotaExample(1, "Pirates of the Caribbean: On Stranger Tides", 2011, 378));
        movieList.add(new ModelQuotaExample(2, "Avengers: Age of Ultron", 2015, 365));
        movieList.add(new ModelQuotaExample(3, "Avengers: Infinity War", 2018, 316));
        movieList.add(new ModelQuotaExample(4, "Pirates of the Caribbean: At World's End", 2007, 300));
        movieList.add(new ModelQuotaExample(5, "Justice League", 2017, 300));
        movieList.add(new ModelQuotaExample(6, "Solo: A Star Wars Story", 2018, 275));
        movieList.add(new ModelQuotaExample(7, "John Carter", 2012, 264));
        movieList.add(new ModelQuotaExample(8, "Batman v Superman: Dawn of Justice", 2016, 263));
        movieList.add(new ModelQuotaExample(9, "Star Wars: The Last Jedi", 2017, 263));
        movieList.add(new ModelQuotaExample(9, "Star Wars: The Last Jedi", 2017, 263));
        movieList.add(new ModelQuotaExample(9, "Star Wars: The Last Jedi", 2017, 263));
        movieList.add(new ModelQuotaExample(9, "Star Wars: The Last Jedi", 2017, 263));
        movieList.add(new ModelQuotaExample(9, "Star Wars: The Last Jedi", 2017, 263));
        movieList.add(new ModelQuotaExample(9, "Star Wars: The Last Jedi", 2017, 263));
        movieList.add(new ModelQuotaExample(9, "Star Wars: The Last Jedi", 2017, 263));
        movieList.add(new ModelQuotaExample(9, "Star Wars: The Last Jedi", 2017, 263));
        movieList.add(new ModelQuotaExample(9, "Star Wars: The Last Jedi", 2017, 263));
        movieList.add(new ModelQuotaExample(9, "Star Wars: The Last Jedi", 2017, 263));
        movieList.add(new ModelQuotaExample(10, "Tangled", 2010, 260));
        movieList.add(new ModelQuotaExample(10, "Tangled", 2010, 260));
        movieList.add(new ModelQuotaExample(10, "Tangled", 2010, 260));
        movieList.add(new ModelQuotaExample(10, "Tangled", 2010, 260));
        movieList.add(new ModelQuotaExample(10, "Tangled", 2010, 260));
        movieList.add(new ModelQuotaExample(10, "Tangled", 2010, 260));
        movieList.add(new ModelQuotaExample(10, "Tangled", 2010, 260));
        movieList.add(new ModelQuotaExample(10, "Tangled", 2010, 260));
        movieList.add(new ModelQuotaExample(10, "Tangled", 2010, 260));

        return movieList;
    }


    public void calculateData(){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        List<ModelQuotaLokWis> modelQuotaLokWisList = new ArrayList<>();

        String EP ="get_quota_lokasi_wisata";
        String server_url = "http://kaffah.amanahgitha.com/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue( getApplicationContext() );
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                response -> {

                    try {
                        if( Help.isJSONValid(response) ){
                            JSONObject jsonObject = new JSONObject(response);

                            if( jsonObject.getBoolean("success") ) {
                                findViewById(R.id.loadingPanel).setVisibility(View.GONE);

                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i <jsonArray.length();i++ ) {
                                    JSONObject jInnerObject = new JSONObject();

                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                    String id =  jsonObject1.getString("id");
                                    String from_date =  jsonObject1.getString("from_date");
                                    String thru_date =  jsonObject1.getString("thru_date");
                                    String quota =  jsonObject1.getString("quota");
                                    String quota_in =  jsonObject1.getString("quota_in");
                                    String quota_out =  jsonObject1.getString("quota_out");

                                    Log.i("tag","id= "+id);
                                    Log.i("tag","from_date= "+from_date);

                                    modelQuotaLokWisList.add(new ModelQuotaLokWis(id, from_date,thru_date,quota, quota_in, quota_out));

                                }
                                tableViewQuotaAdapter = new TableViewQuotaAdapter(modelQuotaLokWisList);
                                recyclerView.setAdapter(tableViewQuotaAdapter);

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

                final String key_email =  sessionManager.getUserDetail().get(SessionManager.key_email).trim();
                Log.i("","key_email "+key_email);

                obj.put("alamat_email", key_email);
//                obj.put("alamat_email", "petugas-1");
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }

    public void update_quota_lokasi_wisata(String EP,String kdksda, String id, String from_date, String thru_date, String jml_quota){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        List<ModelQuotaLokWis> modelQuotaLokWisList = new ArrayList<>();


        String server_url = "http://kaffah.amanahgitha.com/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue( getApplicationContext() );
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                response -> {

                    try {
                        if( Help.isJSONValid(response) ){
                            JSONObject jObj = new JSONObject(response);
                            String data = jObj.getString("data");
                            JSONObject jsonObject1 = new JSONObject(data);

                            Log.i("","data x"+data);
                            Log.i("","jsonObject1 "+jsonObject1);

                            boolean berhasil = jsonObject1.getBoolean("berhasil");
                            boolean success = jObj.getBoolean("success");

                            Log.i("tag","success= " + jObj.getBoolean("success") );
                            Log.i("tag","data= " + data );
                            Log.i("tag","berhasil= " + berhasil );

                            final String has_id = jsonObject1.getString("id");
                            final boolean has_berhasil = jsonObject1.getBoolean("berhasil");
                            final String has_keterangan = jsonObject1.getString("keterangan");

                            if( success ) {
                                if( berhasil ) {

                                    Intent i = new Intent(getApplicationContext(), SuccessRegistrasiWisatawanActivity.class);
                                    i.putExtra("result_dt_id", has_id);
                                    i.putExtra("result_dt_email", sessionManager.getUserDetail().get(SessionManager.key_email).trim() );
                                    i.putExtra("result_dt_berhasil", has_berhasil);
                                    i.putExtra("result_dt_ket", has_keterangan);
                                    i.putExtra("result_dt_flag", "flagQuotaList");
                                    startActivity(i);
                                } else {

                                    Intent i = new Intent(getApplicationContext(), SuccessRegistrasiWisatawanActivity.class);
                                    i.putExtra("result_dt_ket", has_keterangan);
                                    i.putExtra("result_dt_email", sessionManager.getUserDetail().get(SessionManager.key_email).trim() );
                                    i.putExtra("result_dt_berhasil", berhasil);
                                    i.putExtra("result_dt_flag", "flagQuotaList");
                                    startActivity(i);
                                }
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

                Log.i("","kdksda "+kdksda);
                Log.i("","id "+id);
                Log.i("","from_date "+from_date);
                Log.i("","thru_date "+thru_date);
                Log.i("","jumlah_quota "+jml_quota);

                obj.put("kode_ksda", kdksda);
                obj.put("id", id);
                obj.put("from_date", from_date);
                obj.put("thru_date", thru_date);
                obj.put("jumlah_quota", jml_quota);
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }

}

