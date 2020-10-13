package com.amanahgithawisata.aga;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amanahgithawisata.aga.Adapter.ReportKarcisLevel4Adapter;
import com.amanahgithawisata.aga.Adapter.SessionManager;
import com.amanahgithawisata.aga.Helper.Help;
import com.amanahgithawisata.aga.Model.ModelReportKarcis;
import com.amanahgithawisata.aga.Model.ModelReportKarcisLevel4;
import com.amanahgithawisata.aga.Model.SpinnerListWisata;
import com.android.volley.AuthFailureError;
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

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;


public class ReportKarcisL4Activity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    SessionManager sessionManager;
    ReportKarcisLevel4Adapter reportKarcisLevel4Adapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    Button btn_find_l4;
    Button btn_export_l4;
    ModelReportKarcis modelReportKarcis;
    TextView lbl_spinner_laporan;
    public String global_spiner_pintu;


    @Override
    public void onBackPressed() {
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
        setContentView(R.layout.activity_report_karcis_l4);
        sessionManager = new SessionManager(getApplicationContext());
        btn_find_l4 = findViewById(R.id.btn_find_l4);
        btn_export_l4 = findViewById(R.id.btn_export_l4);
        lbl_spinner_laporan  = findViewById(R.id.lbl_spinner_laporan);
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);



        btn_export_l4.setOnClickListener(v -> {
            TextView from_date;
            TextView end_date;
            TextView lbl_spinner_laporan;
            Button btn_update;
            Spinner optionPintu;
            Button btn_send;
            ProgressBar progressBar;
            ArrayList<SpinnerListWisata> arrListWisata = new ArrayList<SpinnerListWisata>();
            List<ModelReportKarcisLevel4> modelReportKarcisLevel4s = new ArrayList<>();


            if (Build.VERSION.SDK_INT >= 23) {
                int permissionCheck = ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ReportKarcisL4Activity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
            }



            View modelBottomSheet = LayoutInflater.from(v.getContext()).inflate(R.layout.layout_bottom_sheet_report_karcis, null);
            BottomSheetDialog dialog = new BottomSheetDialog(v.getContext());




            LinearLayout linearLayout = modelBottomSheet.findViewById(R.id.rootLinearBottomSheet);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
            params.height = getScreenHeight();
            linearLayout.setLayoutParams(params);



            from_date = modelBottomSheet.findViewById(R.id.txt_from_date);
            end_date = modelBottomSheet.findViewById(R.id.txt_end_date);
            optionPintu = modelBottomSheet.findViewById(R.id.optionPintu);
            btn_send = modelBottomSheet.findViewById(R.id.btn_send);
            lbl_spinner_laporan  = modelBottomSheet.findViewById(R.id.lbl_spinner_laporan);
            progressBar = modelBottomSheet.findViewById(R.id.loadingPanelProgressBar);

//            txtview_update.setText("Tambah ");
//            txtview_quota.setText("Tambah ");
            btn_send.setText("Export.. ");
            lbl_spinner_laporan.setText("Lokasi Wisata");

            progressBar.setVisibility(View.VISIBLE);
            String EP = "daftar_ksda_per_regional";
            String server_url = "http://kaffah.amanahgitha.com/~androidwisata/?data="+ EP;
            final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                    response -> {

                        try {
                            if( Help.isJSONValid(response) ){
                                JSONObject jsonObject = new JSONObject(response);
                                Log.i("","spinner pintu daftar_ksda_per_regional= "+response);
                                arrListWisata.clear();

                                if( jsonObject.getBoolean("success") ) {
                                    progressBar.setVisibility(View.GONE);
                                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                                    for (int i = 0; i <jsonArray.length();i++ ) {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                        String kode_lokasi = jsonObject1.getString("kode_lokasi");
                                        String nama = jsonObject1.getString("nama");
                                        String url_image = jsonObject1.getString("url_image");

                                        Log.i("tag","url_image= "+url_image);
                                        Log.i("tag","kode_lokasi= "+kode_lokasi);
                                        Log.i("tag","nama= "+nama);
                                        arrListWisata.add(new SpinnerListWisata(kode_lokasi,nama,"","",""));
                                    }
                                }
                            }
                            optionPintu.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, arrListWisata) );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        requestQueue.stop();
                    }, error -> {
                error.printStackTrace();
                requestQueue.stop();
            }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> obj = new HashMap<String, String>();
                    final String KD_KSDA =  sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi).trim();
                    Log.i("","kode_regional x"+KD_KSDA);
                    obj.put("kode_regional", KD_KSDA);
                    return obj;
                }
            };
            int socketTimeout = 0;
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(policy);
            requestQueue.add(stringRequest);



            optionPintu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    SpinnerListWisata item = (SpinnerListWisata) parent.getItemAtPosition(position);
                    global_spiner_pintu = item.getKdlok();
                    Log.i("","global_spiner_pintu 1 "+global_spiner_pintu);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

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


            modelBottomSheet.findViewById(R.id.btn_send).setOnClickListener(v1 -> {

                String _fromdate = from_date.getText().toString().trim();
                String _end_date = end_date.getText().toString().trim();

                Log.i("","_fromdate "+_fromdate);
                Log.i("","_end_date "+_end_date);

                if(TextUtils.isEmpty(_fromdate )) {
                    from_date.setError("From date Masih Kosong ");
                }
                else if(TextUtils.isEmpty(_end_date )) {
                    end_date.setError("End date Masih Kosong ");
                }

                else {
                    Log.i("","global_spiner_pintu 2 "+global_spiner_pintu);

                    String key_kdksda =  sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi).trim();
                    String key_email =  sessionManager.getUserDetail().get(SessionManager.key_email).trim();

                    export_karcis_level("report_karcis_level_4",
                            key_email,
                            key_kdksda,
                            _fromdate ,
                            _end_date,
                            global_spiner_pintu
                    );
                    dialog.dismiss();
                }
            });

        });


        btn_find_l4.setOnClickListener(v -> {
            TextView from_date;
            TextView end_date;
            TextView lbl_spinner_laporan ;
            Button btn_update;
            Spinner optionPintu;
            Button btn_send;
            ProgressBar progressBar;
            ArrayList<SpinnerListWisata> arrListWisata = new ArrayList<>();

            View modelBottomSheet = LayoutInflater.from(v.getContext()).inflate(R.layout.layout_bottom_sheet_report_karcis, null);
            BottomSheetDialog dialog = new BottomSheetDialog(v.getContext());

            from_date = modelBottomSheet.findViewById(R.id.txt_from_date);
            end_date = modelBottomSheet.findViewById(R.id.txt_end_date);
            optionPintu = modelBottomSheet.findViewById(R.id.optionPintu);
            btn_send = modelBottomSheet.findViewById(R.id.btn_send);
            lbl_spinner_laporan  = modelBottomSheet.findViewById(R.id.lbl_spinner_laporan);
            progressBar = modelBottomSheet.findViewById(R.id.loadingPanelProgressBar);

            btn_send.setText("Cari.. ");
            lbl_spinner_laporan.setText("Lokasi Wisata");

            progressBar.setVisibility(View.VISIBLE);
            String EP = "daftar_ksda_per_regional";
            String server_url = "http://kaffah.amanahgitha.com/~androidwisata/?data="+ EP;
            final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                    response -> {
                        try {
                            if( Help.isJSONValid(response) ){
                                JSONObject jsonObject = new JSONObject(response);
                                Log.i("","spinner pintu daftar_ksda_per_regional= "+response);
                                arrListWisata.clear();

                                if( jsonObject.getBoolean("success") ) {
                                    progressBar.setVisibility(View.GONE);
                                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                                    for (int i = 0; i <jsonArray.length();i++ ) {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                        String kode_lokasi = jsonObject1.getString("kode_lokasi");
                                        String nama = jsonObject1.getString("nama");
                                        String url_image = jsonObject1.getString("url_image");

                                        Log.i("tag","kode_lokasi= "+kode_lokasi);
                                        Log.i("tag","nama= "+nama);
                                        Log.i("tag","url_image= "+url_image);
                                        arrListWisata.add(new SpinnerListWisata(kode_lokasi,nama,"","",url_image));
                                    }
                                }
                            }
                            optionPintu.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, arrListWisata) );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        requestQueue.stop();
                    }, error -> {
                error.printStackTrace();
                requestQueue.stop();
            }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> obj = new HashMap<>();
                    final String KD_KSDA =  sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi).trim();
                    Log.i("","kode_regional x "+KD_KSDA);
                    obj.put("kode_regional", KD_KSDA);
                    return obj;
                }
            };
            int socketTimeout = 0;
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(policy);
            requestQueue.add(stringRequest);



            optionPintu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    SpinnerListWisata item = (SpinnerListWisata) parent.getItemAtPosition(position);
                    global_spiner_pintu = item.getKdlok();
                    Log.i("","global_spiner_pintu 1 "+global_spiner_pintu);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

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


            modelBottomSheet.findViewById(R.id.btn_send).setOnClickListener(v1 -> {

                String _fromdate = from_date.getText().toString().trim();
                String _end_date = end_date.getText().toString().trim();

                Log.i("","_fromdate "+_fromdate);
                Log.i("","_end_date "+_end_date);

                if(TextUtils.isEmpty(_fromdate )) {
                    from_date.setError("From date Masih Kosong ");
                }
                else if(TextUtils.isEmpty(_end_date )) {
                    end_date.setError("End date Masih Kosong ");
                }

                else {

                    Log.i("","global_spiner_pintu 2 "+global_spiner_pintu);

                    String key_kdksda =  sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi).trim();
                    String key_email =  sessionManager.getUserDetail().get(SessionManager.key_email).trim();

                    report_karcis_level("report_karcis_level_4",
                            key_email,
                            key_kdksda,
                            _fromdate ,
                            _end_date,
                            global_spiner_pintu
                    );
                    dialog.dismiss();
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

        }


    }


    public List<ModelReportKarcisLevel4> GETReportKarcisList(){
        List<ModelReportKarcisLevel4> modelReportKarcisLevel4s = new ArrayList<>();

        int a;
        for (a=0; a<5; a++)
        {
            modelReportKarcisLevel4s.add(new ModelReportKarcisLevel4("", "","","", "", String.valueOf(a),"","","",""));
//                    modelQuotaLokWisList.add(new ModelQuotaLokWis("1", "2020-02-12","2020-01-02","1300", "500", "50"));
        }

        return modelReportKarcisLevel4s;
    }



    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public void report_karcis_level(String EP,
                                    String alamat_email,
                                    String kode_regional,
                                    String from_date,
                                    String thru_date,
                                    String kode_pintu )
    {
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        List<ModelReportKarcisLevel4> modelReportKarcisListLevel4 = new ArrayList<>();


        String server_url = "http://kaffah.amanahgitha.com/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue( getApplicationContext() );
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                response -> {
                    try {

                        Log.i("","response "+response);

                        if( Help.isJSONValid(response) ){
                            JSONObject jsonObject = new JSONObject(response);

                            if( jsonObject.getBoolean("success") ) {
                                findViewById(R.id.loadingPanel).setVisibility(View.GONE);

                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i <jsonArray.length();i++ ) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                    String id =  jsonObject1.getString("id");
                                    String nama_lokasi_wisata =  jsonObject1.getString("nama_lokasi_wisata");
                                    String nama_lokasi_pintu =  jsonObject1.getString("nama_lokasi_pintu");
                                    String status_karcis =  jsonObject1.getString("status_karcis");
                                    String nama_lokasi_regional =  jsonObject1.getString("nama_lokasi_regional");
                                    String jumlah_transaksi =  jsonObject1.getString("jumlah_transaksi");
                                    String jumlah_wisnu =  jsonObject1.getString("jumlah_wisnu");
                                    String jumlah_tambahan =  jsonObject1.getString("jumlah_tambahan");
                                    double tagihan_wisata =  jsonObject1.getDouble("tagihan_wisata");
                                    double tagihan_asuransi =  jsonObject1.getDouble("tagihan_asuransi");


                                    Log.i("","nama_lokasi_regional xx "+nama_lokasi_regional);
                                    Log.i("","nama_lokasi_wisata xx "+nama_lokasi_wisata);

                                    NumberFormat formatter = new DecimalFormat("#,###");
                                    String formatted_tagihan_wisata= formatter.format(tagihan_wisata);
                                    String formatted_tagihan_asuransi =formatter.format(tagihan_asuransi);
                                    modelReportKarcisListLevel4.add(new ModelReportKarcisLevel4(
                                                    id,
                                                    nama_lokasi_regional,
                                                    nama_lokasi_wisata,
                                                    nama_lokasi_pintu,
                                                    status_karcis,
                                                    jumlah_transaksi,
                                                    jumlah_wisnu,
                                                    jumlah_tambahan,
                                            formatted_tagihan_wisata,
                                            formatted_tagihan_asuransi
                                            )
                                    );

                                }

                            }
                        }
                        recyclerView = findViewById(R.id.recyclerViewReportKarcisL4);
                        linearLayoutManager = new LinearLayoutManager(this);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        reportKarcisLevel4Adapter = new ReportKarcisLevel4Adapter(modelReportKarcisListLevel4);
                        recyclerView.setAdapter(reportKarcisLevel4Adapter);


                    }

                    catch (JSONException e) {
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

                Log.i("","alamat_email 4 "+alamat_email);
                Log.i("","from_date "+from_date);
                Log.i("","thru_date "+thru_date);
                Log.i("","kode_regional "+kode_regional);
                Log.i("","kode_pintu "+kode_pintu);


                obj.put("alamat_email", alamat_email);
                obj.put("from_date", from_date);
                obj.put("thru_date", thru_date);
                obj.put("kode_regional", kode_regional);
                obj.put("id", "");
                obj.put("kode_ksda", kode_pintu);


//                obj.put("alamat_email", "petugas-4");
//                obj.put("from_date", "2020-08-01");
//                obj.put("thru_date", "2020-12-31");
//                obj.put("kode_regional", "0002");
//                obj.put("id", "");
//                obj.put("kode_ksda", 0001);

                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }


    WritableWorkbook workbook;
    public void export_karcis_level(String EP,
                                    String alamat_email,
                                    String kode_regional,
                                    String from_date,
                                    String thru_date,
                                    String kode_pintu )
    {
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        List<ModelReportKarcisLevel4> modelReportKarcisListLevel4 = new ArrayList<>();

        String server_url = "http://kaffah.amanahgitha.com/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue( getApplicationContext() );
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                response -> {
                    try {
                        if( Help.isJSONValid(response) ){
                            JSONObject jsonObject = new JSONObject(response);

                            if( jsonObject.getBoolean("success") ) {
                                findViewById(R.id.loadingPanel).setVisibility(View.GONE);

                                @SuppressLint("SimpleDateFormat")
                                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"+"-"+"HH:mm:ss");
                                Date date = new Date();
                                String currentTime = dateFormat.format(date);
                                String fileName = "data_laporan_"+currentTime +".xls"; //Name of the file
                                String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                                File folder = new File(extStorageDirectory, "Download");// Name of the folder you want to keep your file in the local storage.
                                folder.mkdir(); //creating the folder
                                File file = new File(folder, fileName);

                                Log.i("","folder= "+folder);
                                Log.i("","file= "+ file);

                                WorkbookSettings wbSettings = new WorkbookSettings();
                                wbSettings.setLocale(new Locale("en", "EN"));
                                workbook = Workbook.createWorkbook(file, wbSettings);

                                Label label_head_0 = new Label(0,0,"Nama Lokasi Regional");
                                Label label_head_1 = new Label(1,0,"Nama Lokasi Wisata");
                                Label label_head_2 = new Label(2,0,"Nama Lokasi Pintu");
                                Label label_head_3 = new Label(3,0,"Status Karcis");
                                Label label_head_4 = new Label(4,0,"Jml Transaksi");
                                Label label_head_5 = new Label(5,0,"Jml Wisnu");
                                Label label_head_6 = new Label(6,0,"Jml Tambahan");
                                Label label_head_7 = new Label(7,0,"Tagihan Wisata");
                                Label label_head_8 = new Label(8,0,"Tagihan Asuransi");
//                                Label label_head_9 = new Label(8,0,"id");

                                WritableSheet sheet = workbook.createSheet("Sheet 1", 0);

                                sheet.addCell(label_head_0);
                                sheet.addCell(label_head_1);
                                sheet.addCell(label_head_2);
                                sheet.addCell(label_head_3);
                                sheet.addCell(label_head_4);
                                sheet.addCell(label_head_5);
                                sheet.addCell(label_head_6);
                                sheet.addCell(label_head_7);
                                sheet.addCell(label_head_8);
//                                sheet.addCell(label_head_9);

                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i <jsonArray.length();i++ ) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String id =  jsonObject1.getString("id");
                                    String nama_lokasi_regional =  jsonObject1.getString("nama_lokasi_regional");
                                    String nama_lokasi_wisata =  jsonObject1.getString("nama_lokasi_wisata");
                                    String nama_lokasi_pintu =  jsonObject1.getString("nama_lokasi_pintu");
                                    String status_karcis =  jsonObject1.getString("status_karcis");
                                    String jumlah_transaksi =  jsonObject1.getString("jumlah_transaksi");
                                    String jumlah_wisnu =  jsonObject1.getString("jumlah_wisnu");
                                    String jumlah_tambahan =  jsonObject1.getString("jumlah_tambahan");
                                    double tagihan_wisata =  jsonObject1.getDouble("tagihan_wisata");
                                    double tagihan_asuransi =  jsonObject1.getDouble("tagihan_asuransi");

                                    NumberFormat formatter = new DecimalFormat("#,###");
                                    String formatted_tagihan_wisata= formatter.format(tagihan_wisata);
                                    String formatted_tagihan_asuransi =formatter.format(tagihan_asuransi);

                                    Log.i("","nama_lokasi_wisata xx "+nama_lokasi_wisata);
                                    Log.i("","tagihan_wisata xx "+tagihan_wisata);
                                    Log.i("","tagihan_asuransi xx "+tagihan_asuransi);


                                    modelReportKarcisListLevel4.add(new ModelReportKarcisLevel4(
                                            id,
                                            nama_lokasi_regional,
                                                    nama_lokasi_wisata,
                                                    nama_lokasi_pintu,
                                                    status_karcis,
                                                    jumlah_transaksi,
                                                    jumlah_wisnu,
                                                    jumlah_tambahan,
                                                    formatted_tagihan_wisata,
                                                    formatted_tagihan_asuransi
                                            )
                                    );

                                    sheet.addCell( new Label(0,i+1,modelReportKarcisListLevel4.get(i).nama_lokasi_regional) );
                                    sheet.addCell( new Label(1,i+1,modelReportKarcisListLevel4.get(i).nama_lokasi_wisata) );
                                    sheet.addCell( new Label(2,i+1,modelReportKarcisListLevel4.get(i).nama_lokasi_pintu) );
                                    sheet.addCell( new Label(3,i+1,modelReportKarcisListLevel4.get(i).status_karcis) );
                                    sheet.addCell( new Label(4,i+1,modelReportKarcisListLevel4.get(i).jumlah_transaksi) );
                                    sheet.addCell( new Label(5,i+1,modelReportKarcisListLevel4.get(i).jumlah_wisnu) );
                                    sheet.addCell( new Label(6,i+1,modelReportKarcisListLevel4.get(i).jumlah_tambahan) );
                                    sheet.addCell( new Label(7,i+1,modelReportKarcisListLevel4.get(i).tagihan_wisata) );
                                    sheet.addCell( new Label(8,i+1,modelReportKarcisListLevel4.get(i).tagihan_asuransi) );
//                                    sheet.addCell( new Label(8,i+1,modelReportKarcisListLevel4.get(i).id) );


                                    Log.i("","i== "+i);

                                }
                                workbook.write();
                                try {
                                    workbook.close();
                                } catch (WriteException e) {
                                    e.printStackTrace();
                                }

                                AlertDialog.Builder builder = new AlertDialog.Builder(ReportKarcisL4Activity.this);
                                builder.setMessage("File excel dengan nama "+fileName+", sukses disimpan dalam folder Download.")
                                        .setCancelable(false)
                                        .setPositiveButton("Lihat ", (dialog, id) -> {
                                            openDir();
                                        })
                                        .setNegativeButton("Tidak", (dialog, id) -> dialog.cancel());
                                AlertDialog alert = builder.create();
                                alert.show();

                            }
                        }

                    }

                    catch (JSONException | IOException e) {
                        Log.i("", "error ===" + e.toString() );
                        e.printStackTrace();
                    } catch (RowsExceededException e) {
                        e.printStackTrace();
                    } catch (WriteException e) {
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

                Log.i("","alamat_email export "+alamat_email);
                Log.i("","from_date "+from_date);
                Log.i("","thru_date "+thru_date);
                Log.i("","kode_regional "+kode_regional);
                Log.i("","kode_ksda "+kode_pintu);


                obj.put("alamat_email", alamat_email);
                obj.put("from_date", from_date);
                obj.put("thru_date", thru_date);
                obj.put("kode_regional", kode_regional);
                obj.put("id", "");
                obj.put("kode_ksda", kode_pintu);

                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }

    public void openDir() {

        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        File folder = new File(extStorageDirectory, "Download");//

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        Uri uri = Uri.parse((Environment.getExternalStorageDirectory().getPath()+"/Download/")); // a directory
        intent.setDataAndType(uri, "*/*");
        startActivity(Intent.createChooser(intent, "Open folder"));

        Log.i(""," URI= "+ uri);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }
}

