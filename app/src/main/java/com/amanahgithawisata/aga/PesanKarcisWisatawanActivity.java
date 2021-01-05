package com.amanahgithawisata.aga;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.amanahgithawisata.aga.Adapter.CustomAdapterViewPagerLokasiPintu;
import com.amanahgithawisata.aga.Adapter.CustomAdapterViewPagerLokasiWisata;
import com.amanahgithawisata.aga.Adapter.DatePickerPesanKarcisWstwn;
import com.amanahgithawisata.aga.Adapter.SessionManager;
import com.amanahgithawisata.aga.Helper.Help;
import com.amanahgithawisata.aga.Model.SpinnerKarcisTambahan;
import com.amanahgithawisata.aga.Model.SpinnerKarcisUtama;
import com.amanahgithawisata.aga.Model.SpinnerListWisata;
import com.amanahgithawisata.aga.Model.SpinnerListWisataKsda;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

//public class PesanKarcisWisatawanActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener  {
public class PesanKarcisWisatawanActivity extends AppCompatActivity implements  DatePickerDialog.OnDateSetListener  {
    TextView _txt_tgl_kunjungan_order, _txt_tgl_kunjungan_2_order;
    TextView _txt_jml_krcs_wisnu;
    TextView _txt_jml_krcs_wisman;
    TextView _txt_ttl;
    EditText _txt_jml_krcs_tmbhn;
    TextView _txt_ttl_tmbhn;
    TextView _txt_grand_ttl;

    Spinner _spiner_krc_utm_wstwn_order;
    Spinner _spiner_krc_tmbhn_wstwn_order;
    Spinner _spinner_lok_wis;
    Button _btn_order_wist;
    Button _btnH;
    TextView _txt_kdlokwis;
    TextView _txt_nmlokwis;
    TextView _txt_urlLokWis;
    TextView _txt_urlLokPintu;
    TextView _txt_urlLokWisOld;

    TextView _txt_kode_ksda;
    TextView _txt_kode_lokasi;
    TextView _txt_kode_karcis;
    TextView _txt_nama_karcis;
    TextView _txt_harga_karcis_wisata_wisnu;
    TextView _txt_harga_karcis_wisata_wisman;
    TextView _txt_harga_karcis_asuransi_wisnu;
    TextView _txt_harga_karcis_asuransi_wisman;
    TextView _txt_id_karcis_utama;
    TextView _txt_url_karcis_utama;

    TextView _harga_karcis_wisata_wisnu_ku;
    TextView _harga_karcis_wisata_wisman_ku;
    TextView _harga_karcis_asuransi_wisnu_ku;
    TextView _harga_karcis_asuransi_wisman_ku;

    TextView _txt_kode_ksda_tmbhn;
    TextView _txt_kode_lokasi_tmbhn;
    TextView _txt_kode_karcis_tmbhn;
    TextView _txt_nama_karcis_tmbhn;
    TextView _txt_harga_karcis_wisata_tmbhn;
    TextView _txt_harga_karcis_asuransi_tmbhn;
    TextView _txt_url_karcis_tmbhn;
    TextView _txt_day;

    LinearLayout _linearLayoutKdLok;
    TextView _txt_kdlokPintu;
    TextView _txt_nmlokPintu;
    TextView _txt_id_karcis_tmbhn;
    LinearLayout _linearLayoutKdPintu;
    LinearLayout _linearLayoutKarcisUtama;
    LinearLayout _linearLayoutKarcisTmbhn;

    Button btn_detail_lokwis;
    Button btn_detail_pintu;
    Button btn_detail_ku;
    Button btn_detail_kt;
    RadioButton rb;
    ProgressBar progress_bar_popup;
    ShimmerFrameLayout shimmer_layout_popup;
    ViewGroup rg_cara_bayar;
    RadioButton rbNew;

//    CustomAdapterViewPagerLokasiWisata customAdapterViewPagerLokasiWisata;
    CustomAdapterViewPagerLokasiWisata _adapter_1 = new CustomAdapterViewPagerLokasiWisata();
    CustomAdapterViewPagerLokasiPintu _adapter_2 = new CustomAdapterViewPagerLokasiPintu();


    private List<String> _listKrcUtmWstwnOrder = new ArrayList<String>();
    private List<String> _listKrcTmbhnWstwnOrder = new ArrayList<String>();
    ArrayList<SpinnerListWisata> lokWisList = new ArrayList<SpinnerListWisata>();
    ArrayList<SpinnerListWisataKsda> lokWisListKsda = new ArrayList<SpinnerListWisataKsda>();
    ArrayList<SpinnerKarcisUtama> arrKarcisUtama = new ArrayList<SpinnerKarcisUtama>();
    ArrayList<SpinnerKarcisTambahan> arrKarcisTambahan = new ArrayList<SpinnerKarcisTambahan>();
    public  int spin1;

    SessionManager sessionManager;

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        TextView _txt_tgl_kunjungan_order = (TextView) findViewById(R.id.txt_tgl_kunjungan_order);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        _txt_tgl_kunjungan_order.setText(sdf.format(c.getTime()));

        String LP = getIntent().getStringExtra("result_dt_kdlokPintu2");
        final String KSDA = sessionManager.getDataLokWisPesankarcisWisatawan().get(SessionManager.key_kd_lokwis);
        Log.i("","LP dateset="+ LP);
        Log.i("","KSDA"+ KSDA);

//        spinnerKarcisWisatawanUtama("daftar_karcis_wisatawan_utama",LP);
//        spinnerKarcisWisatawanTambahan("daftar_karcis_wisatawan_tambahan",LP);

        if(LP == null){
            LP = _txt_kdlokPintu.getText().toString();
        }
        Log.i("","LP AFTER="+ LP);

//        horizontalKarcisWisatawanUtama("daftar_karcis_wisatawan_utama",LP);
//        horizontalKarcisWisatawanTambahan("daftar_karcis_wisatawan_tambahan",LP);

        final String tgl_kunj_val = _txt_tgl_kunjungan_order.getText().toString();

        _txt_tgl_kunjungan_order.setError(null);
        if(TextUtils.isEmpty(tgl_kunj_val) ) {
            _txt_tgl_kunjungan_order.setError("Tgl Masih Kosong!");
        } else {
            Log.i("","tgl_kunj_val="+tgl_kunj_val);
            quotaTwa("quota_per_twa",KSDA,tgl_kunj_val);
            _txt_tgl_kunjungan_order.setError(null);
        }
        try {
            CalculateKarcis();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    public void onBackPressed() {

        Intent intent = new Intent(PesanKarcisWisatawanActivity.this, DashboardWisatawanActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        ClearCalculateKarcis();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan_karcis_wisatawan);
        sessionManager = new SessionManager(getApplicationContext());
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);

        _txt_tgl_kunjungan_order = (TextView) findViewById(R.id.txt_tgl_kunjungan_order);
        _txt_tgl_kunjungan_2_order = (TextView) findViewById(R.id.txt_tgl_kunjungan_2_order);

        _spinner_lok_wis = (Spinner) findViewById(R.id.spinner_lok_ksda_order);
        _spiner_krc_utm_wstwn_order = (Spinner) findViewById(R.id.spinner_karcis_utama_order);
        _spiner_krc_tmbhn_wstwn_order =(Spinner) findViewById(R.id.spinner_karcis_tmbhn_order);
        _btn_order_wist = (Button) findViewById(R.id.btn_order_wist);
        _txt_jml_krcs_wisnu = (TextView) findViewById(R.id.txt_jml_krcs_wisnu_ori);
        _txt_jml_krcs_wisman = (TextView) findViewById(R.id.txt_jml_krcs_wisman_ori);
        _txt_jml_krcs_tmbhn = (EditText) findViewById(R.id.txt_jml_krcs_wisman_tmbhn_ori);
        _txt_ttl = (TextView) findViewById(R.id.txt_ttl);
        _txt_ttl_tmbhn = (TextView) findViewById(R.id.txt_ttl_tmbhn);
        _txt_grand_ttl = (TextView) findViewById(R.id.txt_grand_ttl);
        _btnH = (Button) findViewById(R.id.buttonH);
        rg_cara_bayar = (ViewGroup) findViewById(R.id.rg_cara_bayar);

        _txt_kdlokwis = findViewById(R.id.txt_kdlokwis);
        _txt_nmlokwis = findViewById(R.id.txt_nmlokwis);
        _txt_kdlokPintu = findViewById(R.id.txt_kdlokPintu);
        _txt_urlLokWis = findViewById(R.id.txt_urlLokWis);
        _txt_urlLokPintu = findViewById(R.id.txt_urlLokPintu);
        _txt_urlLokWisOld = findViewById(R.id.txt_urlLokWisOld);
        _txt_nmlokPintu = findViewById(R.id.txt_nmlokPintu);
        _linearLayoutKdLok = findViewById(R.id.linearLayoutKdLok);
        _linearLayoutKdPintu = findViewById(R.id.linearLayoutKdPintu);

        _txt_kode_ksda = findViewById(R.id.txt_kode_ksda);
        _txt_kode_lokasi = findViewById(R.id.txt_kode_lokasi);
         _txt_kode_karcis = findViewById(R.id.txt_kode_karcis);
         _txt_nama_karcis = findViewById(R.id.txt_nama_karcis);
         _txt_harga_karcis_wisata_wisnu = findViewById(R.id.txt_harga_karcis_wisata_wisnu);
         _txt_harga_karcis_wisata_wisman = findViewById(R.id.txt_harga_karcis_wisata_wisman);
         _txt_harga_karcis_asuransi_wisnu = findViewById(R.id.txt_harga_karcis_asuransi_wisnu);
         _txt_harga_karcis_asuransi_wisman = findViewById(R.id.txt_harga_karcis_asuransi_wisman);
        _txt_url_karcis_utama = findViewById(R.id.txt_url_karcis_utama);


        _harga_karcis_wisata_wisnu_ku = findViewById(R.id.harga_karcis_wisata_wisnu_ku);
        _harga_karcis_wisata_wisman_ku = findViewById(R.id.harga_karcis_wisata_wisman_ku);
        _harga_karcis_asuransi_wisnu_ku = findViewById(R.id.harga_karcis_asuransi_wisnu_ku);
        _harga_karcis_asuransi_wisman_ku = findViewById(R.id.harga_karcis_asuransi_wisman_ku);

        _txt_kode_ksda_tmbhn = findViewById(R.id.txt_kode_ksda_tmbhn);
        _txt_kode_lokasi_tmbhn = findViewById(R.id.txt_kode_lokasi_tmbhn);
        _txt_kode_karcis_tmbhn = findViewById(R.id.txt_kode_karcis_tmbhn);
        _txt_nama_karcis_tmbhn = findViewById(R.id.txt_nama_karcis_tmbhn);
        _txt_harga_karcis_wisata_tmbhn = findViewById(R.id.txt_harga_karcis_wisata_tmbhn);
        _txt_harga_karcis_asuransi_tmbhn = findViewById(R.id.txt_harga_karcis_asuransi_tmbhn);
        _txt_id_karcis_utama = findViewById(R.id.txt_id_karcis_utama);
        _txt_id_karcis_tmbhn = findViewById(R.id.txt_id_karcis_tmbhn);
        _txt_url_karcis_tmbhn = findViewById(R.id.txt_url_karcis_tmbhn);


        _linearLayoutKarcisUtama = findViewById(R.id.linearKarcisUtama);
        _linearLayoutKarcisTmbhn = findViewById(R.id.linearKarcisTambahan);

        btn_detail_lokwis = findViewById(R.id.btn_detail_lokwis);
        btn_detail_pintu = findViewById(R.id.btn_detail_pintu);
        btn_detail_ku = findViewById(R.id.btn_detail_ku);
        btn_detail_kt = findViewById(R.id.btn_detail_kt);

        progress_bar_popup = findViewById(R.id.progress_bar_popup);
        shimmer_layout_popup = findViewById(R.id.shimmer_layout_popup);
        _txt_day = findViewById(R.id.txt_day);


        /*  ini di hide  */
        _txt_kdlokwis.setVisibility(View.GONE);
        _txt_nmlokwis.setVisibility(View.VISIBLE);
        _txt_kdlokPintu.setVisibility(View.GONE);
        _txt_urlLokWis.setVisibility(View.GONE);
        _txt_urlLokPintu.setVisibility(View.GONE);

        /*  ini di hide  */
        _txt_urlLokWisOld.setVisibility(View.GONE);
        _txt_nmlokPintu.setVisibility(View.VISIBLE);
        _txt_id_karcis_utama.setVisibility(View.GONE);
        _txt_url_karcis_utama.setVisibility(View.GONE);
        _txt_kode_ksda.setVisibility(View.GONE);
        _txt_kode_lokasi.setVisibility(View.GONE);
        _txt_kode_karcis.setVisibility(View.GONE);
        _txt_nama_karcis.setVisibility(View.VISIBLE);


        _txt_harga_karcis_wisata_wisnu.setVisibility(View.GONE);
        _txt_harga_karcis_wisata_wisman.setVisibility(View.GONE);
        _txt_harga_karcis_asuransi_wisnu.setVisibility(View.GONE);
        _txt_harga_karcis_asuransi_wisman.setVisibility(View.GONE);
        _txt_kode_ksda_tmbhn.setVisibility(View.GONE);
        _txt_harga_karcis_wisata_tmbhn.setVisibility(View.GONE);
        _txt_harga_karcis_asuransi_tmbhn.setVisibility(View.GONE);


        _txt_id_karcis_tmbhn.setVisibility(View.GONE);
        _txt_url_karcis_tmbhn.setVisibility(View.GONE);
        _txt_kode_lokasi_tmbhn.setVisibility(View.GONE);
        _txt_kode_karcis_tmbhn.setVisibility(View.GONE);
        _txt_nama_karcis_tmbhn.setVisibility(View.VISIBLE);

        _harga_karcis_wisata_wisnu_ku.setVisibility(View.GONE);
        _harga_karcis_wisata_wisman_ku.setVisibility(View.GONE);
        _harga_karcis_asuransi_wisnu_ku.setVisibility(View.GONE);
        _harga_karcis_asuransi_wisman_ku.setVisibility(View.GONE);

//        String sessionIntentJudul = "";
//        String sessionIntentText = "";
//        String sessionIntentAlmt = "";
//        String sessionIntentKota = "";
//        String result_dt_judul_pintu = "";
//        String sessionIntentTextPintu = "";
//        String sessionIntentKdOldPintu = "";
        Log.i("","xsxs"+_txt_jml_krcs_tmbhn.getText().toString().trim());
        Log.i("","xsxs"+_txt_jml_krcs_tmbhn.getText().toString().trim().matches(""));

//        if (_txt_jml_krcs_tmbhn.getText().toString().trim().matches("")) {
//            Toast.makeText(this, "You did null", Toast.LENGTH_SHORT).show();
//            return;
//        }


        /* this intent from adapter Entity lokasi Wisata */
        String result_dt_kd_lokwis_adapter = getIntent().getStringExtra("result_dt_kd_lokwis_adapter");
        String result_dt_nm_lokwis_adapter = getIntent().getStringExtra("result_dt_nm_lokwis_adapter");
        String result_dt_almt_adapter = getIntent().getStringExtra("result_dt_almt_adapter");
        String result_dt_kota_adapter = getIntent().getStringExtra("result_dt_kota_adapter");
        String result_dt_url_img_lokwis_adapter = getIntent().getStringExtra("result_dt_url_img_lokwis_adapter");
        String result_dt_tgl_kunj_lokwis_adapter = getIntent().getStringExtra("result_dt_tgl_kunj_lokwis_adapter");
        String result_dt_tgl_kunj_lokwis_2_adapter = getIntent().getStringExtra("result_dt_tgl_kunj_lokwis_2_adapter");


        String result_dt_judul_pintu = getIntent().getStringExtra("result_dt_judul_pintu");
        String sessionIntentTextPintu = getIntent().getStringExtra("result_dt_text_pintu");
        String sessionIntentKdOldPintu = getIntent().getStringExtra("result_dt_kdOld_pintu");
        String result_dt_url_img_lokwis = getIntent().getStringExtra("result_dt_url_img_lokwis");
        String result_dt_url_img_lokPintu = getIntent().getStringExtra("result_dt_url_img_lokPintu");
        String result_dt_tgl_kunj_lokwis = getIntent().getStringExtra("result_dt_tgl_kunj_lokwis");

        String result_dt_txt_kdlokPintu_adapter = getIntent().getStringExtra("txt_kdlokPintu");
        String result_dt_txt_kdlokWis_adapter  = getIntent().getStringExtra("txt_kdlokwis");
        String result_dt_txt_nmlokPintux_adapter = getIntent().getStringExtra("txt_nmlokPintux");
        String result_dt_txt_url_img_lokwisOld_adapter = getIntent().getStringExtra("txt_url_img_lokwisOld");
        String result_dt_txt_url_img_lokPintu_adapter = getIntent().getStringExtra("txt_url_img_lokPintu");
        String result_dt_jml_karcis_wisnu_adapter = getIntent().getStringExtra("result_dt_jml_karcis_wisnu");
        String result_dt_jml_karcis_wisman_adapter = getIntent().getStringExtra("result_dt_jml_karcis_wisman");
        String result_dt_ttl_wisnu_wisman_adapter = getIntent().getStringExtra("result_dt_ttl_wisnu_wisman");

        String result_dt_jml_karcis_tmbhn_adapter = getIntent().getStringExtra("result_dt_jml_karcis_tmbhn");
        String result_dt_ttl_karcis_tmbhn_adapter = getIntent().getStringExtra("result_dt_ttl_karcis_tmbhn");
        String result_dt_grand_ttl_adapter = getIntent().getStringExtra("result_dt_grand_ttl");
        String result_dt_txt_harga_karcis_wisata_tmbhn_adapter = getIntent().getStringExtra("result_dt_txt_harga_karcis_wisata_tmbhn");
        String result_dt_txt_id_karcis_utama_adapter = getIntent().getStringExtra("result_dt_txt_id_karcis_utama");
        String result_dt_txt_id_karcis_tmbhn_adapter = getIntent().getStringExtra("result_dt_txt_id_karcis_tmbhn");



        String result_flag_pesan_karcis_sukses = getIntent().getStringExtra("result_flag_pesan_karcis_sukses");

        Log.i("","result_flag_pesan_karcis_sukses "+result_flag_pesan_karcis_sukses);


        /* this intent from adapter Entity lokasi Pintu */
        String result_dt_kdlokPintu2 = getIntent().getStringExtra("result_dt_kdlokPintu2");
        String result_dt_textlokPintu2 = getIntent().getStringExtra("result_dt_textlokPintu2");
        String result_dt_kdlokOldPintu2 = getIntent().getStringExtra("result_dt_kdlokOldPintu2");
        String result_dt_nmlokOldPintu2 = getIntent().getStringExtra("result_dt_nmlokOldPintu2");
        String result_dt_url_img_pintu_fromAdapterLokPintu = getIntent().getStringExtra("result_dt_url_img_pintu_fromAdapterLokPintu");
        String result_dt_url_img_lokWisOld_fromAdapterLokPintu = getIntent().getStringExtra("result_dt_url_img_lokWisOld_fromAdapterLokPintu");
        String result_dt_tgl_kunj_pintux = getIntent().getStringExtra("result_dt_tgl_kunj_pintux");
        String result_dt_tgl_kunj_pintu_2 = getIntent().getStringExtra("result_dt_tgl_kunj_pintu_2");

        String result_jml_karcis_wisnu_lp = getIntent().getStringExtra("result_jml_karcis_wisnu_lp");
        String result_jml_karcis_wisman_lp = getIntent().getStringExtra("result_jml_karcis_wisman_lp");
        String result_jml_karcis_tmbhn_lp = getIntent().getStringExtra("result_jml_karcis_tmbhn_lp");
        String result_ttl_wisnu_wisman_lp = getIntent().getStringExtra("result_ttl_wisnu_wisman_lp");
        String result_ttl_karcis_tmbhn_lp = getIntent().getStringExtra("result_ttl_karcis_tmbhn_lp");
        String result_grand_ttl_lp = getIntent().getStringExtra("result_grand_ttl_lp");
        String txt_harga_karcis_wisata_tmbhn_lp = getIntent().getStringExtra("txt_harga_karcis_wisata_tmbhn_lp");

        String result_dt_kodeKarcis = getIntent().getStringExtra("result_dt_kodeKarcis");
        String result_dt_namaKarcis = getIntent().getStringExtra("result_dt_namaKarcis");
        String result_dt_harga_karcis_wisata_wisnu = getIntent().getStringExtra("result_dt_harga_karcis_wisata_wisnu");
        String result_dt_harga_karcis_wisata_wisman = getIntent().getStringExtra("result_dt_harga_karcis_wisata_wisman");
        String result_dt_harga_karcis_asuransi_wisnu = getIntent().getStringExtra("result_dt_harga_karcis_asuransi_wisnu");
        String result_dt_harga_karcis_asuransi_wisman = getIntent().getStringExtra("result_dt_harga_karcis_asuransi_wisman");
        String result_dt_kdlokWis = getIntent().getStringExtra("result_dt_kdlokWis");
        String result_dt_nmlokWis = getIntent().getStringExtra("result_dt_nmlokWis");
        String result_dt_kdlokPintu = getIntent().getStringExtra("result_dt_kdlokPintu");
        String result_dt_nmlokPintu = getIntent().getStringExtra("result_dt_nmlokPintu");
        String result_dt_tgl_kunj = getIntent().getStringExtra("result_dt_tgl_kunj");
        String result_dt_url_img_lokWisOld = getIntent().getStringExtra("result_dt_url_img_lokWisOld");
        String result_dt_url_img_lokPintuOld = getIntent().getStringExtra("result_dt_url_img_lokPintuOld");
        String result_dt_url_img_ku = getIntent().getStringExtra("result_dt_url_img_ku");
        String result_dt_id_ku = getIntent().getStringExtra("result_dt_id_ku");
        String result_dt_tgl_kunj_2_ku = getIntent().getStringExtra("result_dt_tgl_kunj_2_ku");


        String result_jml_karcis_wisnu_ku = getIntent().getStringExtra("result_jml_karcis_wisnu");
        String result_jml_karcis_wisman_ku = getIntent().getStringExtra("result_jml_karcis_wisman");
        String result_ttl_wisnu_wisman_ku = getIntent().getStringExtra("result_ttl_wisnu_wisman");
        String result_jml_karcis_tmbhn_ku = getIntent().getStringExtra("result_jml_karcis_tmbhn");
        String result_ttl_karcis_tmbhn_ku = getIntent().getStringExtra("result_ttl_karcis_tmbhn");
        String result_grand_ttl_ku = getIntent().getStringExtra("result_grand_ttl");
        String result_dt_harga_karcis_wisata_tmbhn = getIntent().getStringExtra("result_dt_harga_karcis_wisata_tmbhn");

        String result_dt_id_karcis_utama = getIntent().getStringExtra("result_dt_id_karcis_utama");
        String result_dt_id_karcis_tmbhn = getIntent().getStringExtra("result_dt_id_karcis_tmbhn");



        Log.i("","result_dt_harga_karcis_wisata_tmbhn "+result_dt_harga_karcis_wisata_tmbhn);
        Log.i("","result_dt_id_ku "+result_dt_id_ku);
        Log.i("","result_dt_id_karcis_utama "+result_dt_id_karcis_utama);
        Log.i("","result_dt_id_karcis_tmbhn "+result_dt_id_karcis_tmbhn);
        Log.i("","result_ttl_karcis_tmbhn_ku "+result_ttl_karcis_tmbhn_ku);
        Log.i("","result_dt_url_img_lokWisOld_fromAdapterLokPintu="+result_dt_url_img_lokWisOld_fromAdapterLokPintu);
        Log.i("","result_dt_url_img_pintu_fromAdapterLokPintu="+result_dt_url_img_pintu_fromAdapterLokPintu);

        String result_dt_id_kt = getIntent().getStringExtra("result_dt_id_kt");
        String result_dt_kodeKarcis_kt = getIntent().getStringExtra("result_dt_kodeKarcis_kt");
        String result_dt_namaKarcis_kt = getIntent().getStringExtra("result_dt_namaKarcis_kt");
        String result_dt_harga_karcis_tmbhn_kt = getIntent().getStringExtra("result_dt_harga_karcis_wisata_kt");
        String result_dt_harga_karcis_asuransi_kt = getIntent().getStringExtra("result_dt_harga_karcis_asuransi_kt");
        String result_dt_kdlokWis_kt = getIntent().getStringExtra("result_dt_kdlokWis_kt");
        String result_dt_nmlokWis_kt = getIntent().getStringExtra("result_dt_nmlokWis_kt");
        String result_dt_kdlokPintu_kt = getIntent().getStringExtra("result_dt_kdlokPintu_kt");
        String result_dt_nmlokPintu_kt = getIntent().getStringExtra("result_dt_nmlokPintu_kt");
        String result_dt_tgl_kunj_kt = getIntent().getStringExtra("result_dt_tgl_kunj_kt");
        String result_dt_url_img_lokWisOld_kt = getIntent().getStringExtra("result_dt_url_img_lokWisOld_kt");
        String result_dt_url_img_lokPintuOld_kt = getIntent().getStringExtra("result_dt_url_img_lokPintuOld_kt");

        String result_dt_jml_krcs_wisnu_kt = getIntent().getStringExtra("result_dt_jml_krcs_wisnu_kt");
        String result_dt_jml_krcs_wisman_kt = getIntent().getStringExtra("result_dt_jml_krcs_wisman_kt");
        String _jml_krcs_wisnu_wisman = getIntent().getStringExtra("_jml_krcs_wisnu_wisman");
        String result_dt_jml_krcs_tmbhn_kt = getIntent().getStringExtra("result_dt_jml_krcs_tmbhn_kt");
        String result_dt_ttl_krcs_tmbhn_kt = getIntent().getStringExtra("result_dt_ttl_krcs_tmbhn_kt");
        String result_dt_grand_ttl = getIntent().getStringExtra("result_dt_grand_ttl");
        String result_dt_id_karcis_utama_kt = getIntent().getStringExtra("result_dt_id_karcis_utama");
        String result_dt_id_karcis_tmbhn_kt = getIntent().getStringExtra("result_dt_id_karcis_tmbhn");

        String harga_karcis_wisata_wisnu_kt = getIntent().getStringExtra("harga_karcis_wisata_wisnu_kt");
        String harga_karcis_wisata_wisman_kt = getIntent().getStringExtra("harga_karcis_wisata_wisman_kt");
        String harga_karcis_wisata_tmbhn_kt = getIntent().getStringExtra("harga_karcis_wisata_tmbhn_kt");
        String harga_karcis_asuransi_wisnu_kt = getIntent().getStringExtra("harga_karcis_asuransi_wisnu_kt");
        String harga_karcis_asuransi_wisman_kt = getIntent().getStringExtra("harga_karcis_asuransi_wisman_kt");
        String tgl_kunj_2_kt = getIntent().getStringExtra("tgl_kujungan_2_kt");


        Log.i("","result_dt_id_kt= "+result_dt_id_kt);
        Log.i("","result_dt_jml_krcs_wisnu_kt= "+result_dt_jml_krcs_wisnu_kt);
        Log.i("","result_dt_jml_krcs_wisman_kt= "+result_dt_jml_krcs_wisman_kt);
        Log.i("","result_dt_harga_karcis_tmbhn_kt= "+result_dt_harga_karcis_tmbhn_kt);
        Log.i("","result_dt_harga_karcis_asuransi_kt= "+result_dt_harga_karcis_asuransi_kt);
        Log.i("","result_dt_tgl_kunj_kt= "+result_dt_tgl_kunj_kt);
        Log.i("","_jml_krcs_wisnu_wisman= "+_jml_krcs_wisnu_wisman);
        Log.i("","result_dt_kd_lokwis_adapter= "+result_dt_kd_lokwis_adapter);
        Log.i("","result_dt_judul_pintu x= "+result_dt_judul_pintu);
        Log.i("","sessionIntentKdOldPintu= "+sessionIntentKdOldPintu);
        Log.i("","result_dt_url_img_lokwis_adapter= "+result_dt_url_img_lokwis_adapter);


        Log.i("","result_dt_kdlokPintu2 "+ result_dt_kdlokPintu2);
        Log.i("","result_dt_textlokPintu2 " +result_dt_textlokPintu2 );
        Log.i("","result_dt_kdlokOldPintu2 "+result_dt_kdlokOldPintu2);
        Log.i("","result_dt_nmlokOldPintu2 "+result_dt_nmlokOldPintu2);



        Log.i("","result_dt_kodeKarcis"+result_dt_kodeKarcis);
        Log.i("","result_dt_namaKarcis"+result_dt_namaKarcis);
        Log.i("","result_dt_harga_karcis_wisata_wisnu"+result_dt_harga_karcis_wisata_wisnu);
        Log.i("","result_dt_harga_karcis_wisata_wisman"+result_dt_harga_karcis_wisata_wisman);
        Log.i("","result_dt_harga_karcis_asuransi_wisnu"+result_dt_harga_karcis_asuransi_wisnu);
        Log.i("","result_dt_harga_karcis_asuransi_wisman"+result_dt_harga_karcis_asuransi_wisman);
        Log.i("","result_dt_kdlokWis"+result_dt_kdlokWis);
        Log.i("","result_dt_nmlokWis"+result_dt_nmlokWis);
        Log.i("","result_dt_kdlokPintu"+result_dt_kdlokPintu);
        Log.i("","result_dt_nmlokPintu"+result_dt_nmlokPintu);
        Log.i("","result_dt_tgl_kunj"+result_dt_tgl_kunj);
        Log.i("","result_dt_url_img_lokWisOld "+result_dt_url_img_lokWisOld);
        Log.i("","result_dt_url_img_lokPintuOld "+result_dt_url_img_lokPintuOld);


//        horizontalLokasiWisata("daftar_lokasi_wisata","","","","");
//        horizontalLokasiPintu("daftar_lokasi_pintu", sessionIntentJudul, getApplicationContext(),"","");
//        getDataLokWisPesankarcisWisatawanKsda
//        sessionManager.createSessionForHorizontalPintu("","");

        String kd_ksda_pintux = sessionManager.getDataHorizontalPintu().get(SessionManager.key_kd_ksda_horz_1);
        Log.i("", "kd_ksda_pintux = " + kd_ksda_pintux  );


        final String session_kd_lok_wis = sessionManager.getDataLokWisPesankarcisWisatawan().get(SessionManager.key_kd_lokwis);
        final String session_nm_lok_wis = sessionManager.getDataLokWisPesankarcisWisatawan().get(SessionManager.key_nm_lokwis);
        final String session_url_lok_wis = sessionManager.getDataLokWisPesankarcisWisatawan().get(SessionManager.key_url_img_lokwis);

        final String session_kd_lok_pintu = sessionManager.getDataLokPintuPesankarcisWisatawan().get(SessionManager.key_kd_lokPintu);
        final String session_nm_lok_pintu = sessionManager.getDataLokPintuPesankarcisWisatawan().get(SessionManager.key_nm_lokPintu);
        final String session_url_lok_pintu = sessionManager.getDataLokPintuPesankarcisWisatawan().get(SessionManager.key_url_img_lokPintu);


        final String session_kodeKarcis = sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_kode_karcis);
        final String session_namaKarcis = sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_nama_karcis);
        final String session__harga_karcis_wisata_wisnu = sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_harga_karcis_wisata_wisnu);
        final String session_harga_karcis_wisata_wisman = sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_harga_karcis_wisata_wisman);
        final String session_harga_karcis_asuransi_wisnu = sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_harga_karcis_asuransi_wisnu);
        final String session_harga_karcis_asuransi_wisman = sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_harga_karcis_asuransi_wisman);
        final String session_id_karcis_utama= sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_id_karcis_utama);

        final String session_JmlKarcisWisnu_state = sessionManager.getDataJmlKarcisWisnu().get(SessionManager.key_JmlKarcisWisnu);
        final String session_JmlKarcisWisman_state = sessionManager.getDataJmlKarcisWisman().get(SessionManager.key_JmlKarcisWisman);
        final String session_TtlKarcisWisnuWisman_state = sessionManager.getDataTtlKarcisWisnuWisman().get(SessionManager.key_TtlKarcisWisnuWisman);
        final String session_JmlKarcisTmbhn_state = sessionManager.getDataJmlKarcisTmbhn().get(SessionManager.key_JmlKarcisTmbhn);
        final String session_TtlKarcisTmbhn_state = sessionManager.getDataTtlKarcisTmbhn().get(SessionManager.key_TtlKarcisTmbhn);
        final String session_GrandTtlKarcis_state = sessionManager.getDataGrandTtlKarcis().get(SessionManager.key_GrandTtlKarcis);

        final String session_nama_karcis = sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_nama_karcis);
        final String session_url_karcis_utama = sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_url_img_wist_utama);
        final double session_hrg_krcs_wisnu = Help.ParseDouble(sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_harga_karcis_wisata_wisnu));
        final double session_hrg_krcs_wisman = Help.ParseDouble(sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_harga_karcis_wisata_wisman));
        final double session_hrg_krcs_asrnsi_wisnu = Help.ParseDouble(sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_harga_karcis_asuransi_wisnu));
        final double session_hrg_krcs_asrnsi_wisman = Help.ParseDouble(sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_harga_karcis_asuransi_wisman));

        final String session_kd_krcis_tmbhn = sessionManager.getDaftarKarcisWisatawanTmbhn().get(SessionManager.key_kode_karcis_tmbhn);
        final String session_nm_krcis_tmbhn = sessionManager.getDaftarKarcisWisatawanTmbhn().get(SessionManager.key_nama_karcis_tmbhn);
        final String session_harga_karcis_wisata_tmbhn = sessionManager.getDaftarKarcisWisatawanTmbhn().get(SessionManager.key_harga_karcis_wisata_tmbhn);
        final String session_id_tmbhn = sessionManager.getDaftarKarcisWisatawanTmbhn().get(SessionManager.key_id_tmbhn);
        final String session_url_img_tmbhn = sessionManager.getDaftarKarcisWisatawanTmbhn().get(SessionManager.key_url_img_tmbhn);

        final String session_tgl_kunj = sessionManager.getTglKunjungan().get(SessionManager.key_tgl_kunjungan);



//    Log.i("","session_kd_lok_wis "+session_kd_lok_wis);
        Log.i("","session_nm_lok_wis "+session_nm_lok_wis);
        Log.i("","session_url_lok_wis "+session_url_lok_wis);

        Log.i("","session_kodeKarcis "+session_kodeKarcis);
        Log.i("","session_namaKarcis "+session_namaKarcis);
        Log.i("","session__harga_karcis_wisata_wisnu "+session__harga_karcis_wisata_wisnu);
        Log.i("","session_harga_karcis_wisata_wisman "+session_harga_karcis_wisata_wisman);
        Log.i("","session_harga_karcis_asuransi_wisnu "+session_harga_karcis_asuransi_wisnu);
        Log.i("","session_harga_karcis_asuransi_wisman "+session_harga_karcis_asuransi_wisman);
        Log.i("","session_id_karcis_utama "+session_id_karcis_utama);

        Log.i("","result_dt_kodeKarcis_kt "+result_dt_kodeKarcis_kt);
        Log.i("","result_dt_namaKarcis_kt "+result_dt_namaKarcis_kt);
        Log.i("","result_dt_harga_karcis_tmbhn_kt "+result_dt_harga_karcis_tmbhn_kt);
        Log.i("","result_dt_harga_karcis_asuransi_kt "+result_dt_harga_karcis_asuransi_kt);
        Log.i("","result_dt_kdlokWis_kt "+result_dt_kdlokWis_kt);
        Log.i("","result_dt_nmlokWis_kt "+result_dt_nmlokWis_kt);
        Log.i("","result_dt_kdlokPintu_k "+result_dt_kdlokPintu_kt);
        Log.i("","result_dt_nmlokPintu_kt "+result_dt_nmlokPintu_kt);
        Log.i("","result_dt_tgl_kunj_kt "+result_dt_tgl_kunj_kt);
        Log.i("","result_dt_url_img_lokWisOld_kt "+result_dt_url_img_lokWisOld_kt);
        Log.i("","result_dt_url_img_lokPintuOld_kt "+result_dt_url_img_lokPintuOld_kt);


        Log.i("","session_JmlKarcisWisnu_state "+session_JmlKarcisWisnu_state);
        Log.i("","session_JmlKarcisWisman_state "+session_JmlKarcisWisman_state);
        Log.i("","session_TtlKarcisWisnuWisman_state "+session_TtlKarcisWisnuWisman_state);


        Log.i("","session_JmlKarcisTmbhn_state "+ session_JmlKarcisTmbhn_state );
        Log.i("","session_TtlKarcisTmbhn_state "+ session_TtlKarcisTmbhn_state );

        _txt_ttl.setEnabled(false);
        _txt_ttl_tmbhn.setEnabled(false);
        _txt_grand_ttl.setEnabled(false);
        _txt_tgl_kunjungan_order.setText("");

//        _btn_order_wist.setEnabled(false);
//        _btn_order_wist.setBackgroundColor(getResources().getColor(R.color.blackPrimary));
//        _btn_order_wist.getBackground().setColorFilter(0xFFFF0000, PorterDuff.Mode.MULTIPLY);

        _txt_ttl.setText("0");
        _txt_ttl_tmbhn.setText("0");
        _txt_grand_ttl.setText("0");

        @SuppressLint("CutPasteId") RadioGroup rg_cara_bayarn = (RadioGroup)findViewById(R.id.rg_cara_bayar);

        final String[] mode_pembayaran = new String[1];
        final String[] nama_pembayaran = new String[1];
        rg_cara_bayarn.setOnCheckedChangeListener((group, checkedId) -> {
            rbNew = findViewById(checkedId);
            boolean isChecked = rbNew.isChecked();


            if( isChecked ) {
                Log.i("","isChecked "+rbNew.getText() );
                Log.i("","isChecked "+rbNew.getId() );
//                Log.i("","id txt "+ txtId );
                mode_pembayaran[0] = String.valueOf(rbNew.getId());
                nama_pembayaran[0] = String.valueOf(rbNew.getText());
            } else {
                mode_pembayaran[0] = "";
                nama_pembayaran[0] = "";
            }
        });

        String kode_pintu;
        String sess_ksda_par;

        _txt_jml_krcs_tmbhn.performClick();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            getRbCaraBayar("wisatawan_mode_pembayaran",mode_pembayaran[0],"1");
            getRbCaraBayar("list_bank",mode_pembayaran[0],"1");
        }

        /*  this when first time load activity PesanKarcis*/
        if( result_dt_kodeKarcis == null &&  result_dt_kd_lokwis_adapter == null && result_dt_judul_pintu == null && result_dt_kdlokPintu2 == null && result_dt_kodeKarcis_kt == null) {

            Log.i("", "result_dt_kd_lokwis_adapter && result_dt_judul_pintu null = " + result_dt_kd_lokwis_adapter + " "+result_dt_judul_pintu+ " "+result_dt_kdlokPintu2 );
            _txt_tgl_kunjungan_order.setText(result_dt_tgl_kunj_lokwis);
            horizontalLokasiWisata("daftar_lokasi_wisata","","","","");

        }
        /*  this after click adapter Entity Lokasi Wisata */
        else if ( result_dt_kodeKarcis == null && result_dt_kd_lokwis_adapter != null && result_dt_judul_pintu == null && result_dt_kdlokPintu2 == null && result_dt_kodeKarcis_kt == null) {

            _txt_kdlokwis.setText(result_dt_txt_kdlokWis_adapter);
            _txt_jml_krcs_wisnu.setText(result_dt_jml_karcis_wisnu_adapter);
            _txt_jml_krcs_wisman.setText(result_dt_jml_karcis_wisman_adapter);
            _txt_ttl.setText(result_dt_ttl_wisnu_wisman_adapter);
            _txt_jml_krcs_tmbhn.setText(result_dt_jml_karcis_tmbhn_adapter);
            _txt_ttl_tmbhn.setText(result_dt_ttl_karcis_tmbhn_adapter);
            _txt_grand_ttl.setText(result_dt_grand_ttl_adapter);


            _txt_kdlokwis.setText(result_dt_kd_lokwis_adapter);
            _txt_nmlokwis.setText(result_dt_nm_lokwis_adapter);
            _txt_kdlokPintu.setText(result_dt_txt_kdlokPintu_adapter);
            _txt_nmlokPintu.setText(result_dt_txt_nmlokPintux_adapter);
            _txt_urlLokWis.setText(result_dt_url_img_lokwis_adapter);
            _txt_urlLokPintu.setText(result_dt_txt_url_img_lokPintu_adapter);
            _txt_tgl_kunjungan_order.setText(result_dt_tgl_kunj_lokwis_adapter);


            if ( result_dt_tgl_kunj_lokwis_adapter != null ) {
                _txt_tgl_kunjungan_order.setText( result_dt_tgl_kunj_lokwis_adapter );

                final String KSDA = sessionManager.getDataLokWisPesankarcisWisatawan().get(SessionManager.key_kd_lokwis);

                Log.i("","result_dt_tgl_kunj_lokwis_adapter "+result_dt_tgl_kunj_lokwis_adapter);

                quotaTwa("quota_per_twa",KSDA,result_dt_tgl_kunj_lokwis_adapter);
                _txt_tgl_kunjungan_order.setError(null);

            }
            if( result_dt_tgl_kunj_lokwis_adapter != null ){
                _txt_tgl_kunjungan_2_order.setText(result_dt_tgl_kunj_lokwis_2_adapter);
            }



           final ImageView img1 = findViewById(R.id.lokwisPicasso);
            Transformation transformation = new RoundedTransformationBuilder()
//                    .borderColor(Color.BLACK)
//                    .borderWidthDp(3)
                    .borderWidthDp(1)
                    .oval(false)
                    .build();

            Picasso.with(getApplicationContext())
                    .load(result_dt_url_img_lokwis_adapter)
                    .fit()
                    .transform(transformation)
                    .placeholder(R.drawable.loading_animation)
                    .into(img1);



            Log.i("", "result_dt_kd_lokwis_adapter not null && result_dt_judul_pintu null = " + result_dt_kd_lokwis_adapter + " "+result_dt_judul_pintu);


            if( result_dt_kd_lokwis_adapter != null) {
                sess_ksda_par = result_dt_kd_lokwis_adapter;
            } else {
                sess_ksda_par ="0001";
            }

            horizontalLokasiPintuForWisata("daftar_lokasi_pintu", sess_ksda_par, getApplicationContext(),"","","null");



            if( result_dt_txt_kdlokPintu_adapter != null) {
                kode_pintu = result_dt_txt_kdlokPintu_adapter;
            } else {
                kode_pintu ="00011";
            }




        }

        /* this after click Adapter Entity Lokasi Pintu */
        else if ( result_dt_kodeKarcis == null && result_dt_kdlokPintu2 != null && result_dt_textlokPintu2 != null && result_dt_kodeKarcis_kt == null) {

            Log.i("", "sessionIntentKdOldPintu not null && result_dt_judul_pintu not null = " + result_dt_kdlokPintu2 + " "+result_dt_kdlokPintu2);

            _txt_kdlokwis.setText(result_dt_kdlokOldPintu2);
            _txt_nmlokwis.setText(result_dt_nmlokOldPintu2);
            _txt_kdlokPintu.setText(result_dt_kdlokPintu2);
            _txt_nmlokPintu.setText(result_dt_textlokPintu2);
            _txt_urlLokWis.setText(result_dt_url_img_lokWisOld_fromAdapterLokPintu);
            _txt_urlLokPintu.setText(result_dt_url_img_pintu_fromAdapterLokPintu);
            _txt_tgl_kunjungan_order.setText(result_dt_tgl_kunj_pintux);

            _txt_jml_krcs_wisnu.setText(result_jml_karcis_wisnu_lp);
            _txt_jml_krcs_wisman.setText(result_jml_karcis_wisman_lp);
            _txt_jml_krcs_tmbhn.setText(result_jml_karcis_tmbhn_lp);
            _txt_ttl.setText(result_ttl_wisnu_wisman_lp);
            _txt_ttl_tmbhn.setText(result_ttl_karcis_tmbhn_lp);
            _txt_grand_ttl.setText(result_grand_ttl_lp);
            _txt_harga_karcis_wisata_tmbhn.setText(txt_harga_karcis_wisata_tmbhn_lp);

            if ( result_dt_tgl_kunj_pintux != null ) {
                _txt_tgl_kunjungan_order.setText( result_dt_tgl_kunj_pintux );
                final String KSDA = sessionManager.getDataLokWisPesankarcisWisatawan().get(SessionManager.key_kd_lokwis);
                quotaTwa("quota_per_twa",result_dt_kdlokOldPintu2,session_tgl_kunj);
                _txt_tgl_kunjungan_order.setError(null);
            }
            if( result_dt_tgl_kunj_pintu_2 != null ) {
                _txt_tgl_kunjungan_2_order.setText(result_dt_tgl_kunj_pintu_2);

            }

            final ImageView img1 =(ImageView)findViewById(R.id.lokwisPicasso);
            Transformation transformation = new RoundedTransformationBuilder()
//                    .borderColor(Color.BLACK)
//                    .borderWidthDp(3)
                    .borderWidthDp(1)
                    .oval(false)
                    .build();

            Picasso.with(getApplicationContext())
                    .load(result_dt_url_img_lokWisOld_fromAdapterLokPintu)
                    .fit()
                    .transform(transformation)
                    .into(img1);

            final ImageView img2 = findViewById(R.id.lokPintuPicasso);
            Picasso.with(getApplicationContext())
                    .load(result_dt_url_img_pintu_fromAdapterLokPintu)
                    .fit()
                    .transform(transformation)
                    .placeholder(R.drawable.loading_animation)
                    .into(img2);

            if( result_dt_kdlokPintu2 != null) {
                kode_pintu = result_dt_kdlokPintu2;
            } else {
                kode_pintu ="00011";
            }

            horizontalKarcisWisatawanUtamaFirst("daftar_karcis_wisatawan_utama",kode_pintu);
            horizontalKarcisWisatawanTambahanFirst("daftar_karcis_wisatawan_tambahan",kode_pintu);

            try {
                CalculateKarcis();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        /* this after click Adapter Entity Karcis Utama */
        else if( result_dt_kodeKarcis != null && result_dt_kd_lokwis_adapter == null && result_dt_judul_pintu == null && result_dt_kdlokPintu2 == null && result_dt_kodeKarcis_kt == null ) {

            Log.i("","this after click Adapter Entity Karcis Utama");

            Log.i("","result_dt_kodeKarcis xx"+result_dt_kodeKarcis);
            Log.i("","result_dt_namaKarcis utama"+result_dt_namaKarcis);
            Log.i("","result_dt_harga_karcis_wisata_wisnu"+result_dt_harga_karcis_wisata_wisnu);
            Log.i("","result_dt_harga_karcis_wisata_wisman"+result_dt_harga_karcis_wisata_wisman);
            Log.i("","result_dt_harga_karcis_asuransi_wisnu"+result_dt_harga_karcis_asuransi_wisnu);
            Log.i("","result_dt_harga_karcis_asuransi_wisman"+result_dt_harga_karcis_asuransi_wisman);
            Log.i("","result_dt_kdlokWis"+result_dt_kdlokWis);
            Log.i("","result_dt_nmlokWis"+result_dt_nmlokWis);
            Log.i("","result_dt_kdlokPintu"+result_dt_kdlokPintu);
            Log.i("","result_dt_nmlokPintu"+result_dt_nmlokPintu);
            Log.i("","result_dt_tgl_kunj"+result_dt_tgl_kunj);

            Log.i("","result_dt_url_img_lokWisOld "+result_dt_url_img_lokWisOld);
            Log.i("","result_dt_url_img_lokPintu "+result_dt_url_img_lokPintu);


            _txt_kdlokwis.setText(result_dt_kdlokWis);
            _txt_nmlokwis.setText(result_dt_nmlokWis);
            _txt_kdlokPintu.setText(result_dt_kdlokPintu);
            _txt_nmlokPintu.setText(result_dt_nmlokPintu);
            _txt_urlLokWis.setText(result_dt_url_img_lokWisOld);
            _txt_urlLokPintu.setText(result_dt_url_img_lokPintuOld);
            _txt_tgl_kunjungan_order.setText(result_dt_tgl_kunj);
            _txt_tgl_kunjungan_2_order.setText(result_dt_tgl_kunj_2_ku);


            _txt_kode_ksda.setText(result_dt_kdlokWis);
            _txt_kode_lokasi.setText(result_dt_kdlokPintu);
            _txt_kode_karcis.setText(result_dt_kdlokWis);
            _txt_nama_karcis.setText(result_dt_namaKarcis);
            _txt_harga_karcis_wisata_wisnu.setText(result_dt_harga_karcis_wisata_wisnu);
            _txt_harga_karcis_wisata_wisman.setText(result_dt_harga_karcis_wisata_wisman);
            _txt_harga_karcis_asuransi_wisnu.setText(result_dt_harga_karcis_asuransi_wisnu);
            _txt_harga_karcis_asuransi_wisman.setText(result_dt_harga_karcis_asuransi_wisman);
            _txt_url_karcis_utama.setText(result_dt_url_img_ku);

            _txt_id_karcis_utama.setText(result_dt_id_karcis_utama);
            _txt_id_karcis_tmbhn.setText(result_dt_id_karcis_tmbhn);

//            if(result_flag_pesan_karcis_sukses == null){
                _txt_jml_krcs_wisnu.setText(result_jml_karcis_wisnu_ku);
                _txt_jml_krcs_wisman.setText(result_jml_karcis_wisman_ku);
                _txt_ttl.setText(result_ttl_wisnu_wisman_ku);
                _txt_jml_krcs_tmbhn.setText(result_jml_karcis_tmbhn_ku);
                _txt_ttl_tmbhn.setText(result_ttl_karcis_tmbhn_ku);
                _txt_grand_ttl.setText(result_grand_ttl_ku);
                _txt_harga_karcis_wisata_tmbhn.setText(result_dt_harga_karcis_wisata_tmbhn);


            if ( result_dt_tgl_kunj != null ) {
                _txt_tgl_kunjungan_order.setText( result_dt_tgl_kunj );
                final String KSDA = sessionManager.getDataLokWisPesankarcisWisatawan().get(SessionManager.key_kd_lokwis);
                quotaTwa("quota_per_twa",result_dt_kdlokWis,result_dt_tgl_kunj);
                _txt_tgl_kunjungan_order.setError(null);
            }
            if ( result_dt_tgl_kunj_2_ku != null ) {
                _txt_tgl_kunjungan_2_order.setText(result_dt_tgl_kunj_2_ku);
            }


            final ImageView img1 =(ImageView)findViewById(R.id.lokwisPicasso);

            Transformation transformation = new RoundedTransformationBuilder()
//                    .borderColor(Color.BLACK)
//                    .borderWidthDp(3)
                    .borderWidthDp(1)
                    .oval(false)
                    .build();

            Picasso.with(getApplicationContext())
                    .load(result_dt_url_img_lokWisOld)
                    .fit()
                    .transform(transformation)
                    .placeholder(R.drawable.loading_animation)
                    .into(img1);

            final ImageView img2 = findViewById(R.id.lokPintuPicasso);
            Picasso.with(getApplicationContext())
                    .load(result_dt_url_img_lokPintuOld)
                    .fit()
                    .placeholder(R.drawable.loading_animation)
                    .transform(transformation)
                    .into(img2);

            final ImageView img3 = findViewById(R.id.krcsUtamaPicasso);
            Picasso.with(getApplicationContext())
                    .load(result_dt_url_img_ku)
                    .fit()
                    .placeholder(R.drawable.loading_animation)
                    .transform(transformation)
                    .into(img3);

            if( result_dt_kdlokPintu != null) {
                kode_pintu = result_dt_kdlokPintu;
            } else {
                kode_pintu ="00011";
            }

            horizontalKarcisWisatawanTambahanFirst("daftar_karcis_wisatawan_tambahan",kode_pintu);
            try {
                CalculateKarcis();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        /* this after click Adapter Entity Karcis Tambahan */
        else if( result_dt_kodeKarcis_kt != null && result_dt_kodeKarcis == null && result_dt_kd_lokwis_adapter == null && result_dt_judul_pintu == null && result_dt_kdlokPintu2 == null ) {

            Log.i("","this after click Adapter Entity Karcis Tambahan");

            _txt_id_karcis_utama.setText(result_dt_id_karcis_utama_kt);
            _txt_id_karcis_tmbhn.setText(result_dt_id_karcis_tmbhn_kt);
            _txt_kdlokwis.setText(result_dt_kdlokWis_kt);
            _txt_nmlokwis.setText(result_dt_nmlokWis_kt);
            _txt_kdlokPintu.setText(result_dt_kdlokPintu_kt);
            _txt_nmlokPintu.setText(result_dt_nmlokPintu_kt);
//            _txt_urlLokWisOld.setText(session_url_lok_wis);
            _txt_urlLokPintu.setText(result_dt_url_img_lokPintuOld_kt);
            _txt_tgl_kunjungan_order.setText(result_dt_tgl_kunj_kt);

            _txt_kode_ksda.setText(result_dt_kdlokWis_kt);
            _txt_kode_lokasi.setText(result_dt_kdlokPintu_kt);
            _txt_harga_karcis_wisata_tmbhn.setText(result_dt_harga_karcis_tmbhn_kt);
            _txt_nama_karcis_tmbhn.setText(result_dt_namaKarcis_kt);


                _txt_jml_krcs_wisnu.setText( result_dt_jml_krcs_wisnu_kt );
                _txt_jml_krcs_wisman.setText(result_dt_jml_krcs_wisman_kt );
                _txt_ttl.setText( result_dt_ttl_krcs_tmbhn_kt );
                _txt_jml_krcs_tmbhn.setText(result_dt_jml_krcs_tmbhn_kt);
                _txt_ttl_tmbhn.setText(result_dt_ttl_krcs_tmbhn_kt);


            Log.i("","session_TtlKarcisTmbhn_state entity karcis tambahan"+session_TtlKarcisTmbhn_state);

                    if ( result_dt_tgl_kunj_kt != null ) {
                        _txt_tgl_kunjungan_order.setText( result_dt_tgl_kunj_kt );
                        quotaTwa("quota_per_twa",result_dt_kdlokWis_kt,result_dt_tgl_kunj_kt);
                        _txt_tgl_kunjungan_order.setError(null);
                    }

                    if( tgl_kunj_2_kt != null ) {
                        _txt_tgl_kunjungan_2_order.setText( tgl_kunj_2_kt );
                    }


            final ImageView img1 =(ImageView)findViewById(R.id.lokwisPicasso);
            Transformation transformation = new RoundedTransformationBuilder()
//                    .borderColor(Color.BLACK)
//                    .borderWidthDp(3)
                    .borderWidthDp(1)
                    .oval(false)
                    .build();

            Picasso.with(getApplicationContext())
                    .load(session_url_lok_wis)
                    .fit()
                    .placeholder(R.drawable.loading_animation)
                    .transform(transformation)
                    .into(img1);

            final ImageView img2 = findViewById(R.id.lokPintuPicasso);
            Picasso.with(getApplicationContext())
                    .load(session_url_lok_pintu)
                    .fit()
                    .placeholder(R.drawable.loading_animation)
                    .transform(transformation)
                    .into(img2);

            final ImageView img3 = findViewById(R.id.krcsUtamaPicasso);
            Picasso.with(getApplicationContext())
                    .load(session_url_karcis_utama)
                    .fit()
                    .transform(transformation)
                    .placeholder(R.drawable.loading_animation)
                    .into(img3);

            final ImageView img4 = findViewById(R.id.krcsTmbhnPicasso);
            Picasso.with(getApplicationContext())
                    .load(session_url_img_tmbhn)
                    .fit()
                    .placeholder(R.drawable.loading_animation)
                    .transform(transformation)
                    .into(img4);


            if( result_dt_kdlokPintu_kt != null) {
                kode_pintu = result_dt_kdlokPintu_kt;
            } else {
                kode_pintu ="00011";
            }

            horizontalKarcisWisatawanUtamaFirst("daftar_karcis_wisatawan_utama",kode_pintu);

            final double _jml_krcs_wisnu = Help.ParseDouble(result_dt_jml_krcs_wisnu_kt);
            final double _jml_krcs_wisman = Help.ParseDouble(result_dt_jml_krcs_wisman_kt);
            final double _jml_krcs_tmbhn = Help.ParseDouble(result_dt_jml_krcs_tmbhn_kt);

            final double hrg_krcs_wisnu = Help.ParseDouble(harga_karcis_wisata_wisnu_kt);
            final double hrg_krcs_wisman = Help.ParseDouble(harga_karcis_wisata_wisman_kt);
            final double hrg_krcs_tmbhn =  Help.ParseDouble(result_dt_harga_karcis_tmbhn_kt);
            final double hrg_krcs_asrnsi_wisnu = Help.ParseDouble(harga_karcis_asuransi_wisnu_kt);
            final double hrg_krcs_asrnsi_wisman = Help.ParseDouble(harga_karcis_asuransi_wisman_kt);


            final int ttl_wisnu ;
            final int ttl_wisman;
            final int ttl_wisnu_wisman ;
            final int ttl_tmbhn ;
            final int grand_ttl ;
            long selisih_day = 0;
            try {
                selisih_day = get_selisih_day();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            int new_ttl;
            if( selisih_day >0 ){

                ttl_wisnu = (int) ((hrg_krcs_wisnu+hrg_krcs_asrnsi_wisnu)*_jml_krcs_wisnu)* (int) selisih_day;
                ttl_wisman =(int) ((hrg_krcs_wisman+hrg_krcs_asrnsi_wisman)*_jml_krcs_wisman * (int) selisih_day);
            } else {
                ttl_wisnu = (int) ((hrg_krcs_wisnu+hrg_krcs_asrnsi_wisnu)*_jml_krcs_wisnu);
                ttl_wisman =(int) ((hrg_krcs_wisman+hrg_krcs_asrnsi_wisman)*_jml_krcs_wisman);
            }


            ttl_wisnu_wisman = (ttl_wisnu+ttl_wisman);
            if( selisih_day >0 ){
                ttl_tmbhn = (int) ((hrg_krcs_tmbhn*_jml_krcs_tmbhn)*  (int) selisih_day);
            } else {
                ttl_tmbhn = (int) (hrg_krcs_tmbhn*_jml_krcs_tmbhn);
            }

            grand_ttl =(int) (ttl_wisnu_wisman+ttl_tmbhn);

            _txt_ttl.setText(String.valueOf(ttl_wisnu_wisman));
            _txt_ttl_tmbhn.setText(String.valueOf(ttl_tmbhn));
            _txt_grand_ttl.setText(String.valueOf(grand_ttl));

        }



        _txt_tgl_kunjungan_order.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus){
                DialogFragment datePicker = new DatePickerPesanKarcisWstwn();
                datePicker.show(getSupportFragmentManager(),"date picker PesanKarcisWstwn");
            }
        });
        _txt_tgl_kunjungan_order.setOnClickListener(v -> {
            DialogFragment datePicker = new DatePickerPesanKarcisWstwn();
            datePicker.show(getSupportFragmentManager(),"date picker PesanKarcisWstwn");
        });





        _txt_tgl_kunjungan_2_order.setOnFocusChangeListener((v13, hasFocus) -> {
            if (hasFocus){
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                @SuppressLint("SetTextI18n") DatePickerDialog datePickerDialog = new DatePickerDialog(v13.getContext(),
                        (view, year, monthOfYear, dayOfMonth) -> {
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat fr = new SimpleDateFormat("dd-MM-yyyy");
                            _txt_tgl_kunjungan_2_order.setText( year + "-" + (monthOfYear + 1)  + "-" +dayOfMonth );
                            try {
                                CalculateKarcis();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        },mYear , mMonth,mDay );
                datePickerDialog.show();

            }
        });



        _btn_order_wist.setOnClickListener(v -> {
            final String tgl_kunj_val1 = _txt_tgl_kunjungan_order.getText().toString();
            final String tgl_kunj_val2 = _txt_tgl_kunjungan_2_order.getText().toString();
            final String key_kd_lokwis = _txt_kdlokwis.getText().toString().trim();
            if( TextUtils.isEmpty(tgl_kunj_val1) ) {
                _txt_tgl_kunjungan_order.setError("Tgl Kunjungan Harus Diisi");
                _txt_tgl_kunjungan_order.requestFocus();
            }
            else if( TextUtils.isEmpty(tgl_kunj_val2) ){
                _txt_tgl_kunjungan_2_order.setError("Tgl Rentang Kunjungan Harus Diisi");
                _txt_tgl_kunjungan_2_order.requestFocus();
            }
            else {
                Log.i("","nama_pembayaran[0]="+nama_pembayaran[0]);
                quotaTwaForBtnOrderWist("quota_per_twa",key_kd_lokwis,tgl_kunj_val1,tgl_kunj_val2,nama_pembayaran[0]);
            }
        });




        _txt_jml_krcs_wisnu.setOnClickListener(v -> {
            try {
                CalculateKarcis();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            sessionManager.createSessionStateJmlKarcisWisnu( _txt_jml_krcs_wisnu.getText().toString() );
            Log.i("Ram = ", "_txt_jml_krcs_wisnu ONcLICK"+ _txt_jml_krcs_wisnu.getText().toString());
        });



        _txt_jml_krcs_wisnu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                try {
                    CalculateKarcis();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                sessionManager.createSessionStateJmlKarcisWisnu( _txt_jml_krcs_wisnu.getText().toString() );
                if (s.toString().length() > 0) {
                    Log.i("Ram = ", "_txt_jml_krcs_wisnu not Empty 1 TEXTcHANGED"+ _txt_jml_krcs_wisnu.getText().toString());
                    try {
                        CalculateKarcis();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Log.i("Ram = ", "_txt_jml_krcs_wisnu not Empty 2");
                try {
                    CalculateKarcis();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                sessionManager.createSessionStateJmlKarcisWisnu( _txt_jml_krcs_wisnu.getText().toString() );
                sessionManager.createSessionStateTtlKarcisWisnuWisman( _txt_ttl.getText().toString() );
                sessionManager.createSessionStateGrandTtlKarcis( _txt_grand_ttl.getText().toString() );
                try {
                    CalculateKarcis();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                Log.i("Ram = ", "_txt_jml_krcs_wisnu not Empty 3");
                try {
                    CalculateKarcis();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                sessionManager.createSessionStateJmlKarcisWisnu( _txt_jml_krcs_wisnu.getText().toString() );
                sessionManager.createSessionStateTtlKarcisWisnuWisman( _txt_ttl.getText().toString() );
                sessionManager.createSessionStateGrandTtlKarcis( _txt_grand_ttl.getText().toString() );
                sessionManager.createSessionStateGrandTtlKarcis( _txt_grand_ttl.getText().toString() );
                try {
                    CalculateKarcis();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        _txt_jml_krcs_wisman.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                try {
                    CalculateKarcis();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                sessionManager.createSessionStateJmlKarcisWisman( _txt_jml_krcs_wisman.getText().toString() );
                if (s.toString().length() > 0) {
                    Log.i("Ram = ", "not Empty");
                    try {
                        CalculateKarcis();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    CalculateKarcis();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                sessionManager.createSessionStateJmlKarcisWisman( _txt_jml_krcs_wisman.getText().toString() );
                sessionManager.createSessionStateTtlKarcisWisnuWisman( _txt_ttl.getText().toString() );
                sessionManager.createSessionStateGrandTtlKarcis( _txt_grand_ttl.getText().toString() );
                try {
                    CalculateKarcis();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    CalculateKarcis();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                sessionManager.createSessionStateJmlKarcisWisman( _txt_jml_krcs_wisman.getText().toString() );
                sessionManager.createSessionStateTtlKarcisWisnuWisman( _txt_ttl.getText().toString() );
                sessionManager.createSessionStateGrandTtlKarcis( _txt_grand_ttl.getText().toString() );
                try {
                    CalculateKarcis();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });


        _txt_jml_krcs_tmbhn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                sessionManager.createSessionStateJmlKarcisTmbhn( _txt_jml_krcs_tmbhn.getText().toString() );
                sessionManager.createSessionStateTtlKarcisTmbhn( _txt_ttl_tmbhn.getText().toString() );
                sessionManager.createSessionStateGrandTtlKarcis( _txt_grand_ttl.getText().toString() );
                Log.i("","kesini oke 2");
                try {
                    CalculateKarcis();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sessionManager.createSessionStateJmlKarcisTmbhn( _txt_jml_krcs_tmbhn.getText().toString() );
                sessionManager.createSessionStateTtlKarcisTmbhn( _txt_ttl_tmbhn.getText().toString() );
                sessionManager.createSessionStateGrandTtlKarcis( _txt_grand_ttl.getText().toString() );
                Log.i("","kesini oke 3");
                try {
                    CalculateKarcis();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                sessionManager.createSessionStateJmlKarcisTmbhn( _txt_jml_krcs_tmbhn.getText().toString() );
                sessionManager.createSessionStateTtlKarcisTmbhn( _txt_ttl_tmbhn.getText().toString() );
                sessionManager.createSessionStateGrandTtlKarcis( _txt_grand_ttl.getText().toString() );
                Log.i("","kesini oke 4");
                try {
                    CalculateKarcis();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });




        btn_detail_lokwis.setOnClickListener(v -> {
            String txt_kdlokwis = _txt_kdlokwis.getText().toString().trim();
            String txt_nmlokWis = _txt_nmlokwis.getText().toString().trim();
            String txt_url_img_lokwis = _txt_urlLokWis.getText().toString().trim();
            String tgl_kujungan_val = _txt_tgl_kunjungan_order.getText().toString().trim();
            String tgl_kujungan_2_val = _txt_tgl_kunjungan_2_order.getText().toString().trim();

            String txt_id_karcis_utama = _txt_id_karcis_utama.getText().toString().trim();
            String txt_id_karcis_tmbhn = _txt_id_karcis_tmbhn.getText().toString().trim();
            String txt_kdlokPintu = _txt_kdlokPintu.getText().toString().trim();
            String txt_nmlokPintux =  _txt_nmlokPintu.getText().toString().trim();
            String txt_url_img_lokwisOld = _txt_urlLokWis.getText().toString().trim();
            String txt_url_img_lokPintu = _txt_urlLokPintu.getText().toString().trim();

            String result_dt_jml_karcis_wisnu = _txt_jml_krcs_wisnu.getText().toString().trim();
            String result_dt_jml_karcis_wisman = _txt_jml_krcs_wisman.getText().toString().trim();
            String result_dt_ttl_wisnu_wisman = _txt_ttl.getText().toString().trim();
            String result_dt_jml_karcis_tmbhn = _txt_jml_krcs_tmbhn.getText().toString().trim();
            String result_dt_ttl_karcis_tmbhn = _txt_ttl_tmbhn.getText().toString().trim();
            String result_dt_grand_ttl_ku = _txt_grand_ttl.getText().toString().trim();
            String txt_harga_karcis_wisata_tmbhn = _txt_harga_karcis_wisata_tmbhn.getText().toString().trim();



            Intent i = new Intent(v.getContext(), PopUpActivity.class);
            i.putExtra("result_dt_kd_lokwis", txt_kdlokwis);
            i.putExtra("result_dt_nm_lokwis",txt_nmlokWis );
            i.putExtra("result_dt_url_img_lokwis", txt_url_img_lokwis);
            i.putExtra("result_dt_tgl_kunj_lokwis",tgl_kujungan_val);
            i.putExtra("result_dt_tgl_kunj_2_lokwis",tgl_kujungan_2_val);


            i.putExtra("txt_id_karcis_utama", txt_id_karcis_utama);
            i.putExtra("txt_kdlokPintu",txt_kdlokPintu);
            i.putExtra("txt_kdlokWis", txt_kdlokwis);
            i.putExtra("txt_nmlokPintux", txt_nmlokPintux);
            i.putExtra("txt_url_img_lokwisOld", txt_url_img_lokwisOld);
            i.putExtra("txt_url_img_lokPintu", txt_url_img_lokPintu);
            i.putExtra("result_dt_jml_karcis_wisnu", result_dt_jml_karcis_wisnu);
            i.putExtra("result_dt_jml_karcis_wisman", result_dt_jml_karcis_wisman);
            i.putExtra("result_dt_ttl_wisnu_wisman", result_dt_ttl_wisnu_wisman);

            i.putExtra("result_dt_jml_karcis_tmbhn", result_dt_jml_karcis_tmbhn);
            i.putExtra("result_dt_ttl_karcis_tmbhn", result_dt_ttl_karcis_tmbhn);
            i.putExtra("result_dt_grand_ttl_ku", result_dt_grand_ttl_ku);

            i.putExtra("txt_harga_karcis_wisata_tmbhn", txt_harga_karcis_wisata_tmbhn);
            i.putExtra("txt_id_karcis_utama", txt_id_karcis_utama);
            i.putExtra("txt_id_karcis_tmbhn", txt_id_karcis_tmbhn);

            startActivity(i);

        });

        btn_detail_pintu.setOnClickListener(v -> {
            String txt_kdlokPintu = _txt_kdlokPintu.getText().toString().trim();
            String txt_kdlokWis = _txt_kdlokwis.getText().toString().trim();
            String txt_nmlokWis = _txt_nmlokwis.getText().toString().trim();
            String txt_url_img_lokwis = _txt_urlLokWis.getText().toString().trim();

            String txt_url_img_lokPintu = _txt_urlLokPintu.getText().toString().trim();
            String tgl_kujungan_val = _txt_tgl_kunjungan_order.getText().toString().trim();
            String tgl_kujungan_2_val = _txt_tgl_kunjungan_2_order.getText().toString().trim();

            String result_jml_karcis_wisnu = _txt_jml_krcs_wisnu.getText().toString().trim();
            String result_jml_karcis_wisman = _txt_jml_krcs_wisman.getText().toString().trim();
            String result_ttl_wisnu_wisman = _txt_ttl.getText().toString().trim();
            String result_jml_karcis_tmbhn = _txt_jml_krcs_tmbhn.getText().toString().trim();
            String result_ttl_karcis_tmbhn = _txt_ttl_tmbhn.getText().toString().trim();
            String result_grand_ttl = _txt_grand_ttl.getText().toString().trim();
            String txt_harga_karcis_wisata_tmbhn = _txt_harga_karcis_wisata_tmbhn.getText().toString().trim();

            Intent i = new Intent(v.getContext(), PopUpPintuActivity.class);
            i.putExtra("result_dt_kdlokWis", txt_kdlokWis);
            i.putExtra("result_dt_kdlokPintu",txt_kdlokPintu);
            i.putExtra("result_dt_nmlokWis",txt_nmlokWis);
            i.putExtra("result_dt_url_img_lokwis",txt_url_img_lokwis);
            i.putExtra("result_dt_url_img_lokPintu",txt_url_img_lokPintu);
            i.putExtra("result_dt_tgl_kunj_pintu",tgl_kujungan_val);
            i.putExtra("tgl_kujungan_2_val", tgl_kujungan_2_val);

            i.putExtra("result_jml_karcis_wisnu", result_jml_karcis_wisnu);
            i.putExtra("result_jml_karcis_wisman", result_jml_karcis_wisman);
            i.putExtra("result_ttl_wisnu_wisman", result_ttl_wisnu_wisman);
            i.putExtra("result_jml_karcis_tmbhn", result_jml_karcis_tmbhn);
            i.putExtra("result_ttl_karcis_tmbhn", result_ttl_karcis_tmbhn);
            i.putExtra("result_grand_ttl", result_grand_ttl);
            i.putExtra("txt_harga_karcis_wisata_tmbhn", txt_harga_karcis_wisata_tmbhn);

            Log.i("","txt_url_img_lokwis pintu"+txt_url_img_lokwis);
            Log.i("","txt_url_img_lokPintu pintu"+txt_url_img_lokPintu);

            startActivity(i);

        });

        btn_detail_ku.setOnClickListener(v -> {

            String txt_id_karcis_utama = _txt_id_karcis_utama.getText().toString().trim();
            String txt_kdlokPintu = _txt_kdlokPintu.getText().toString().trim();
            String txt_kdlokWis = _txt_kdlokwis.getText().toString().trim();
            String txt_nmlokWis = _txt_nmlokwis.getText().toString().trim();
            String tgl_kujungan_val = _txt_tgl_kunjungan_order.getText().toString().trim();
            String txt_nmlokPintux =  _txt_nmlokPintu.getText().toString().trim();
            String txt_url_img_lokwisOld = _txt_urlLokWis.getText().toString().trim();
            String txt_url_img_lokPintu = _txt_urlLokPintu.getText().toString().trim();
            String tgl_kujungan_2_val = _txt_tgl_kunjungan_2_order.getText().toString().trim();

            String result_dt_jml_karcis_wisnu = _txt_jml_krcs_wisnu.getText().toString().trim();
            String result_dt_jml_karcis_wisman = _txt_jml_krcs_wisman.getText().toString().trim();
            String result_dt_ttl_wisnu_wisman = _txt_ttl.getText().toString().trim();
            String result_dt_jml_karcis_tmbhn = _txt_jml_krcs_tmbhn.getText().toString().trim();
            String result_dt_ttl_karcis_tmbhn = _txt_ttl_tmbhn.getText().toString().trim();
            String result_dt_grand_ttl_ku = _txt_grand_ttl.getText().toString().trim();
            String txt_harga_karcis_wisata_tmbhn = _txt_harga_karcis_wisata_tmbhn.getText().toString().trim();
            String txt_id_karcis_tmbhn = _txt_id_karcis_tmbhn.getText().toString().trim();


            Log.i("","txt_url_img_lokwisOld "+ txt_url_img_lokwisOld);
            Log.i("","txt_url_img_lokPintu "+ txt_url_img_lokPintu);
            Log.i("","txt_harga_karcis_wisata_tmbhn "+txt_harga_karcis_wisata_tmbhn);


            Intent i = new Intent(v.getContext(), PopUpKarcisUtamaActivity.class);

            i.putExtra("result_dt_kdlokWis", txt_kdlokWis);
            i.putExtra("result_dt_nmlokWis", txt_nmlokWis);
            i.putExtra("result_dt_kdlokPintu",txt_kdlokPintu);
            i.putExtra("result_dt_nmlokPintu",txt_nmlokPintux);
            i.putExtra("result_dt_tgl_kunj",tgl_kujungan_val);
            i.putExtra("result_dt_url_img_lokWisOld",txt_url_img_lokwisOld);
            i.putExtra("result_dt_url_img_pintu",txt_url_img_lokPintu);

            i.putExtra("result_dt_jml_karcis_wisnu",result_dt_jml_karcis_wisnu);
            i.putExtra("result_dt_jml_karcis_wisman",result_dt_jml_karcis_wisman);
            i.putExtra("result_dt_ttl_wisnu_wisman",result_dt_ttl_wisnu_wisman);
            i.putExtra("result_dt_jml_karcis_tmbhn",result_dt_jml_karcis_tmbhn);
            i.putExtra("result_dt_ttl_karcis_tmbhn",result_dt_ttl_karcis_tmbhn);
            i.putExtra("result_dt_grand_ttl",result_dt_grand_ttl);
            i.putExtra("result_dt_harga_karcis_wisata_tmbhn",txt_harga_karcis_wisata_tmbhn);
            i.putExtra("result_dt_id_karcis_utama",txt_id_karcis_utama);
            i.putExtra("result_dt_id_karcis_tmbhn", txt_id_karcis_tmbhn);
            i.putExtra("result_dt_tgl_kunj_2",tgl_kujungan_2_val);




            String tgl_kunj_val = _txt_tgl_kunjungan_order.getText().toString().trim();
            final String KSDA = sessionManager.getDataLokWisPesankarcisWisatawan().get(SessionManager.key_kd_lokwis);
            if(TextUtils.isEmpty(tgl_kunj_val) ) {
                _txt_tgl_kunjungan_order.setError("Tgl Kunjungan Masih Kosong!");
                return;
            } else {
                startActivity(i);
                quotaTwa("quota_per_twa",KSDA,tgl_kunj_val);
                _txt_tgl_kunjungan_order.setError(null);
            }


        });

        btn_detail_kt.setOnClickListener( v -> {
//        _linearLayoutKarcisTmbhn.setOnClickListener( v -> {

            String txt_kdlokPintu = _txt_kdlokPintu.getText().toString().trim();
            String txt_nmlokPintu =  _txt_nmlokPintu.getText().toString().trim();
            String txt_kdlokWis = _txt_kdlokwis.getText().toString().trim();
            String txt_nmlokWis = _txt_nmlokwis.getText().toString().trim();
            String tgl_kujungan_val = _txt_tgl_kunjungan_order.getText().toString().trim();
            String tgl_kujungan_2_val = _txt_tgl_kunjungan_2_order.getText().toString().trim();

            String txt_url_img_lokwisOld = _txt_urlLokWis.getText().toString().trim();
            String txt_url_img_lokPintu = _txt_urlLokPintu.getText().toString().trim();

            String txt_id_karcis_utama = _txt_id_karcis_utama.getText().toString().trim();
            String txt_id_karcis_tmbhn = _txt_id_karcis_tmbhn.getText().toString().trim();

            String _kd_ksda_tmbhn = _txt_kode_ksda_tmbhn.getText().toString().trim();
            String _kode_lokasi_tmbhn =  _txt_kode_lokasi_tmbhn.getText().toString().trim();
            String _kode_karcis_tmbhn = _txt_kode_karcis_tmbhn.getText().toString().trim();
            String _nama_karcis_tmbhn = _txt_nama_karcis_tmbhn.getText().toString().trim();
            String  _harga_karcis_wisata_tmbhn = _txt_harga_karcis_wisata_tmbhn.getText().toString().trim();
            String _harga_karcis_asuransi_tmbhn = _txt_harga_karcis_asuransi_tmbhn.getText().toString().trim();

            String hrg_krcs_wisnu = _txt_harga_karcis_wisata_wisnu.getText().toString();
            String  hrg_krcs_wisman = _txt_harga_karcis_wisata_wisman.getText().toString();

            String  hrg_krcs_tmbhn =  "";
            String  hrg_krcs_asrnsi_wisnu = _txt_harga_karcis_asuransi_wisnu.getText().toString();
            String  hrg_krcs_asrnsi_wisman = _txt_harga_karcis_asuransi_wisman.getText().toString();

            String __txt_jml_krcs_wisnu = _txt_jml_krcs_wisnu.getText().toString().trim();
            String __txt_jml_krcs_wisman= _txt_jml_krcs_wisman.getText().toString().trim();
            String __txt_ttl = _txt_ttl.getText().toString().trim();

            String ___txt_jml_krcs_tmbhn = _txt_jml_krcs_tmbhn.getText().toString().trim();
            String ___txt_ttl_tmbhn = _txt_ttl_tmbhn.getText().toString().trim();
            String ___txt_grand_ttl = _txt_grand_ttl.getText().toString().trim();


            Log.i("","txt_url_img_lokwisOld "+ txt_url_img_lokwisOld);
            Log.i("","txt_url_img_lokPintu "+ txt_url_img_lokPintu);

            Log.i("","_kd_ksda_tmbhn "+ _kd_ksda_tmbhn);
            Log.i("","_kode_lokasi_tmbhn "+ _kode_lokasi_tmbhn);
            Log.i("","_kode_karcis_tmbhn "+ _kode_karcis_tmbhn);
            Log.i("","_nama_karcis_tmbhn "+ _nama_karcis_tmbhn);
            Log.i("","_harga_karcis_wisata_tmbhn "+ _harga_karcis_wisata_tmbhn);
            Log.i("","_harga_karcis_asuransi_tmbhn "+ _harga_karcis_asuransi_tmbhn);
            Log.i("","__txt_jml_krcs_wisnu "+ __txt_jml_krcs_wisnu);
            Log.i("","_txt_jml_krcs_wisman "+ __txt_jml_krcs_wisman);
            Log.i("","__txt_ttl "+ __txt_ttl);


            Intent i = new Intent(v.getContext(), PopUpKarcisTambahanActivity.class);

            i.putExtra("result_dt_kdlokWis", txt_kdlokWis);
            i.putExtra("result_dt_nmlokWis", txt_nmlokWis);
            i.putExtra("result_dt_kdlokPintu",txt_kdlokPintu);
            i.putExtra("result_dt_nmlokPintu",txt_nmlokPintu);
            i.putExtra("result_dt_tgl_kunj",tgl_kujungan_val);
            i.putExtra("result_dt_url_img_lokWisOld",session_url_lok_wis);
            i.putExtra("result_dt_url_img_pintu",txt_url_img_lokPintu);

            i.putExtra("result_dt_jml_krcs_wisnu",__txt_jml_krcs_wisnu);
            i.putExtra("result_dt_jml_krcs_wisman",__txt_jml_krcs_wisman);
            i.putExtra("result_dt_ttl_jml_krcs_wisnu_wisman",__txt_ttl);
            i.putExtra("result_dt_jml_krcs_tmbhn",___txt_jml_krcs_tmbhn);
            i.putExtra("result_dt_ttl_krcs_wisman",___txt_ttl_tmbhn);
            i.putExtra("result_dt_grand_ttl",___txt_grand_ttl);
            i.putExtra("result_dt_id_karcis_utama",txt_id_karcis_utama);
            i.putExtra("result_dt_id_karcis_tmbhn", txt_id_karcis_tmbhn);

            i.putExtra("hrg_krcs_wisnu", hrg_krcs_wisnu);
            i.putExtra("hrg_krcs_wisman", hrg_krcs_wisman);
            i.putExtra("hrg_krcs_tmbhn", hrg_krcs_tmbhn);
            i.putExtra("hrg_krcs_asrnsi_wisnu", hrg_krcs_asrnsi_wisnu);
            i.putExtra("hrg_krcs_asrnsi_wisman", hrg_krcs_asrnsi_wisman);
            i.putExtra("tgl_kujungan_2_val", tgl_kujungan_2_val);


            startActivity(i);

        });

    }

    private void horizontalKarcisWisatawanUtamaFirst (String EP,String Lksi){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url_s2 = "http://"+ Help.domain_api() +"/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue_s2 = Volley.newRequestQueue(getApplicationContext() );
        StringRequest stringRequest_s2 = new StringRequest(Request.Method.POST, server_url_s2,
                response -> {

                    try {
                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        if( Help.isJSONValid(response) ){
                            JSONObject jsonObject = new JSONObject(response.toString());
                            Log.i("tag","success horizontalKarcisWisatawanUtama First= " + jsonObject.getBoolean("success") );

                            arrKarcisUtama.clear();
                            if( jsonObject.getBoolean("success") ) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                Log.i("tag"," dt jsonArray First 1: "+jsonArray.toString());

//                                for (int i = 0; i <jsonArray.length();i++ ) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                                    String _url_image = jsonObject1.getString("url_image");
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


                                    _txt_id_karcis_utama.setText(_id);
                                    _txt_kode_ksda.setText( _kode_ksda );
                                    _txt_kode_lokasi.setText( _kode_lokasi );
                                    _txt_kode_karcis.setText( _kode_karcis );
                                    _txt_nama_karcis.setText( _nama_karcis );
                                    _txt_harga_karcis_wisata_wisnu.setText( _harga_karcis_wisata_wisnu );
                                    _txt_harga_karcis_wisata_wisman.setText( _harga_karcis_wisata_wisman );
                                    _txt_harga_karcis_asuransi_wisnu.setText(_harga_karcis_asuransi_wisnu);
                                    _txt_harga_karcis_asuransi_wisman.setText(_harga_karcis_asuransi_wisman);

                                    _harga_karcis_wisata_wisnu_ku.setText(_harga_karcis_wisata_wisnu);
                                    _harga_karcis_wisata_wisman_ku.setText(_harga_karcis_wisata_wisman);
                                    _harga_karcis_asuransi_wisnu_ku.setText(_harga_karcis_asuransi_wisnu);
                                    _harga_karcis_asuransi_wisman_ku.setText(_harga_karcis_asuransi_wisman);

                                sessionManager.createSessionWisUtm(
                                        _kode_ksda,
                                        _kode_lokasi,
                                        _kode_karcis,
                                        _nama_karcis,
                                        _kode_libur,
                                        _harga_karcis_wisata_wisnu,
                                        _harga_karcis_wisata_wisman,
                                        _harga_karcis_asuransi_wisnu,
                                        _harga_karcis_asuransi_wisman,
                                        _id ,
                                        _url_image );

                                final ImageView img1 =(ImageView)findViewById(R.id.krcsUtamaPicasso);
                                Transformation transformation = new RoundedTransformationBuilder()
//                                        .borderColor()
                                        .borderWidthDp(1)
                                        .oval(false)
                                        .build();

                                Picasso.with(getApplicationContext())
                                        .load(_url_image)
                                        .fit()
                                        .placeholder(R.drawable.loading_animation)
                                        .transform(transformation)
                                        .into(img1);


                            }
                        }

                    } catch (JSONException e) {
                        Log.i("", "error horizontalKarcisWisatawanUtama=" + e.toString() );
                        e.printStackTrace();
                    }
                    requestQueue_s2.stop();
                }, error -> {
            Log.i("tag", "response =" + error.toString());
            error.printStackTrace();
            requestQueue_s2.stop();
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> obj = new HashMap<String, String>();

                Log.i("tag", "Lksi =" + Lksi );
                obj.put("lokasi", Lksi );
                obj.put("tgl_kunjungan", Help.getDateTime().trim() );
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest_s2.setRetryPolicy(policy);
        requestQueue_s2.add(stringRequest_s2);
    }


    private void horizontalKarcisWisatawanTambahanFirst (String EP,String LP){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url_s2 = "http://"+ Help.domain_api() +"/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue_s2 = Volley.newRequestQueue(getApplicationContext() );
        StringRequest stringRequest_s2 = new StringRequest(Request.Method.POST, server_url_s2,
                response -> {

                    try {
                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        if( Help.isJSONValid(response) ){
                            JSONObject jsonObject = new JSONObject(response.toString());
                            Log.i("tag","success horizontalKarcisWisatawanTambahan= " + jsonObject.getBoolean("success") );

                            arrKarcisUtama.clear();
                            if( jsonObject.getBoolean("success") ) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                Log.i("tag"," dt jsonArray Tambahan: "+jsonArray.toString());

//                                for (int i = 0; i <jsonArray.length();i++ ) {

                                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                                String _url_image = jsonObject1.getString("url_image");
                                String _id = jsonObject1.getString("id");
                                String _kode_ksda = jsonObject1.getString("kode_ksda");
                                String _kode_lokasi = jsonObject1.getString("kode_lokasi");
                                String _kode_karcis = jsonObject1.getString("kode_karcis");
                                String _nama_karcis = jsonObject1.getString("nama_karcis");
                                String _kode_libur = jsonObject1.getString("kode_libur");
                                String _harga_karcis_wisata = jsonObject1.getString("harga_karcis_wisata");
                                String _harga_karcis_asuransi = jsonObject1.getString("harga_karcis_asuransi");

//                                    arrKarcisTambahan.add(new SpinnerKarcisTambahan(_kode_karcis,_nama_karcis,_harga_karcis_wisata, _id));

                                _txt_id_karcis_tmbhn.setText(_id);
                                _txt_kode_ksda_tmbhn.setText( _kode_ksda );
                                _txt_kode_lokasi_tmbhn.setText( _kode_lokasi );
                                _txt_kode_karcis_tmbhn.setText( _kode_karcis );
                                _txt_nama_karcis_tmbhn.setText( _nama_karcis );
                                _txt_harga_karcis_wisata_tmbhn.setText( _harga_karcis_wisata );
                                _txt_harga_karcis_asuransi_tmbhn.setText(_harga_karcis_asuransi);

                                Log.i("","_harga_karcis_wisata x "+_harga_karcis_wisata);


                                final ImageView img2 = findViewById(R.id.krcsTmbhnPicasso);
                                Transformation transformation = new RoundedTransformationBuilder()
                                        .borderWidthDp(1)
                                        .oval(false)
                                        .build();

                                Picasso.with(getApplicationContext())
                                        .load(_url_image)
                                        .fit()
                                        .transform(transformation)
                                        .placeholder(R.drawable.loading_animation)
                                        .into(img2);

                            }
                        }

                    } catch (JSONException e) {
                        Log.i("", "error horizontalKarcisWisatawanUtama=" + e.toString() );
                        e.printStackTrace();
                    }
                    requestQueue_s2.stop();
                }, error -> {
            Log.i("tag", "response =" + error.toString());
            error.printStackTrace();
            requestQueue_s2.stop();
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> obj = new HashMap<String, String>();
                obj.put("lokasi", LP );
                obj.put("tgl_kunjungan", Help.getDateTime().trim() );
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest_s2.setRetryPolicy(policy);
        requestQueue_s2.add(stringRequest_s2);
    }



    private void horizontalKarcisWisatawanTambahanFirstforKT (String EP,String LP){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url_s2 = "http://"+ Help.domain_api() +"/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue_s2 = Volley.newRequestQueue(getApplicationContext() );
        StringRequest stringRequest_s2 = new StringRequest(Request.Method.POST, server_url_s2,
                response -> {

                    try {
                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        if( Help.isJSONValid(response) ){
                            JSONObject jsonObject = new JSONObject(response.toString());
                            Log.i("tag","success horizontalKarcisWisatawanTambahan= " + jsonObject.getBoolean("success") );

                            arrKarcisUtama.clear();
                            if( jsonObject.getBoolean("success") ) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                Log.i("tag"," dt jsonArray: "+jsonArray.toString());

//                                for (int i = 0; i <jsonArray.length();i++ ) {

                                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                                String _url_image = jsonObject1.getString("url_image");
                                String _id = jsonObject1.getString("id");
                                String _kode_ksda = jsonObject1.getString("kode_ksda");
                                String _kode_lokasi = jsonObject1.getString("kode_lokasi");
                                String _kode_karcis = jsonObject1.getString("kode_karcis");
                                String _nama_karcis = jsonObject1.getString("nama_karcis");
                                String _kode_libur = jsonObject1.getString("kode_libur");
                                String _harga_karcis_wisata = jsonObject1.getString("harga_karcis_wisata");
                                String _harga_karcis_asuransi = jsonObject1.getString("harga_karcis_asuransi");

//                                    arrKarcisTambahan.add(new SpinnerKarcisTambahan(_kode_karcis,_nama_karcis,_harga_karcis_wisata, _id));

                                _txt_id_karcis_tmbhn.setText(_id);
                                _txt_kode_ksda_tmbhn.setText( _kode_ksda );
                                _txt_kode_lokasi_tmbhn.setText( _kode_lokasi );
                                _txt_kode_karcis_tmbhn.setText( _kode_karcis );
                                _txt_nama_karcis_tmbhn.setText( _nama_karcis );
                                _txt_harga_karcis_wisata_tmbhn.setText( _harga_karcis_wisata );
//                                _txt_harga_karcis_wisata_tmbhn.setText( "xxx" );
                                _txt_harga_karcis_asuransi_tmbhn.setText(_harga_karcis_asuransi);

                                Log.i("","_harga_karcis_wisata x "+_harga_karcis_wisata);



                                final double _jml_krcs_wisnu = Help.ParseDouble(((EditText) findViewById(R.id.txt_jml_krcs_wisnu_ori)).getText().toString());
                                final double _jml_krcs_wisman = Help.ParseDouble(((EditText) findViewById(R.id.txt_jml_krcs_wisman_ori)).getText().toString());
                                final double _jml_krcs_tmbhn = Help.ParseDouble(((EditText) findViewById(R.id.txt_jml_krcs_wisman_tmbhn_ori)).getText().toString());


                                final double hrg_krcs_wisnu = Help.ParseDouble(((TextView) findViewById(R.id.txt_harga_karcis_wisata_wisnu)).getText().toString());
                                final double hrg_krcs_wisman = Help.ParseDouble(((TextView) findViewById(R.id.txt_harga_karcis_wisata_wisman)).getText().toString());

                                final double hrg_krcs_tmbhn =  Help.ParseDouble(_harga_karcis_wisata);
                                final double hrg_krcs_asrnsi_wisnu = Help.ParseDouble(((TextView) findViewById(R.id.txt_harga_karcis_asuransi_wisnu)).getText().toString());
                                final double hrg_krcs_asrnsi_wisman = Help.ParseDouble(((TextView) findViewById(R.id.txt_harga_karcis_asuransi_wisman)).getText().toString());


                                /* Rumus perhitungan karcis dan biaya asuransi  */
                                final int  ttl_wisnu = (int) ((hrg_krcs_wisnu+hrg_krcs_asrnsi_wisnu)*_jml_krcs_wisnu);
                                final int   ttl_wisman =(int) ((hrg_krcs_wisman+hrg_krcs_asrnsi_wisman)*_jml_krcs_wisman);
                                final int ttl_wisnu_wisman = (ttl_wisnu+ttl_wisman);
                                final int ttl_tmbhn = (int) (hrg_krcs_tmbhn*_jml_krcs_tmbhn);
                                final int grand_ttl =(int) (ttl_wisnu_wisman+ttl_tmbhn);

                                Log.i("tag","_jml_krcs_wisnu= "+ _jml_krcs_wisnu);
                                Log.i("tag","_jml_krcs_wisman= "+ _jml_krcs_wisman);
                                Log.i("tag","calc _jml_krcs_tmbhn= "+ _jml_krcs_tmbhn);

                                Log.i("tag","hrg_krcs_wisnu= "+ hrg_krcs_wisnu);
                                Log.i("tag","hrg_krcs_wisman= "+ hrg_krcs_wisman);
                                Log.i("tag","hrg_krcs_tmbhn= "+ hrg_krcs_tmbhn);
                                Log.i("tag","hrg_krcs_asrnsi_wisnu= "+ hrg_krcs_asrnsi_wisnu);
                                Log.i("tag","hrg_krcs_asrnsi_wisman= "+ hrg_krcs_asrnsi_wisman);

                                Log.i("tag","calc ttl_wisnu= "+ ttl_wisnu);
                                Log.i("tag","calc ttl_wisman= "+ ttl_wisman);
                                Log.i("tag","ttl_wisnu_wisman= "+ttl_wisnu_wisman);
                                Log.i("tag","ttl_tmbhn = "+ttl_tmbhn);
                                Log.i("tag","grand_ttl xx = "+grand_ttl);



                                _txt_ttl.setText(String.valueOf(ttl_wisnu_wisman));
                                _txt_ttl_tmbhn.setText(String.valueOf(ttl_tmbhn));
                                _txt_grand_ttl.setText(String.valueOf(grand_ttl));




                                final ImageView img2 = findViewById(R.id.krcsTmbhnPicasso);
                                Transformation transformation = new RoundedTransformationBuilder()
//                                        .borderColor(Color.BLACK)
//                                        .borderWidthDp(3)
//                                        .cornerRadiusDp(30)
                                        .borderWidthDp(1)
                                        .oval(false)
                                        .build();

                                Picasso.with(getApplicationContext())
                                        .load(_url_image)
                                        .fit()
                                        .transform(transformation)
                                        .placeholder(R.drawable.loading_animation)
                                        .into(img2);

                            }
                        }

                    } catch (JSONException e) {
                        Log.i("", "error horizontalKarcisWisatawanUtama=" + e.toString() );
                        e.printStackTrace();
                    }
                    requestQueue_s2.stop();
                }, error -> {
            Log.i("tag", "response =" + error.toString());
            error.printStackTrace();
            requestQueue_s2.stop();
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> obj = new HashMap<String, String>();
                obj.put("lokasi", LP );
                obj.put("tgl_kunjungan", Help.getDateTime().trim() );
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest_s2.setRetryPolicy(policy);
        requestQueue_s2.add(stringRequest_s2);
    }

    private void horizontalKarcisWisatawanTambahanFirstforLokWis (String EP,String LP){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url_s2 = "http://"+ Help.domain_api() +"/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue_s2 = Volley.newRequestQueue(getApplicationContext() );
        StringRequest stringRequest_s2 = new StringRequest(Request.Method.POST, server_url_s2,
                response -> {

                    try {
                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        if( Help.isJSONValid(response) ){
                            JSONObject jsonObject = new JSONObject(response.toString());
                            Log.i("tag","success horizontalKarcisWisatawanTambahan= " + jsonObject.getBoolean("success") );

                            arrKarcisUtama.clear();
                            if( jsonObject.getBoolean("success") ) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                Log.i("tag"," dt jsonArray: "+jsonArray.toString());

//                                for (int i = 0; i <jsonArray.length();i++ ) {

                                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                                    String _url_image = jsonObject1.getString("url_image");
                                    String _id = jsonObject1.getString("id");
                                    String _kode_ksda = jsonObject1.getString("kode_ksda");
                                    String _kode_lokasi = jsonObject1.getString("kode_lokasi");
                                    String _kode_karcis = jsonObject1.getString("kode_karcis");
                                    String _nama_karcis = jsonObject1.getString("nama_karcis");
                                    String _kode_libur = jsonObject1.getString("kode_libur");
                                    String _harga_karcis_wisata = jsonObject1.getString("harga_karcis_wisata");
                                    String _harga_karcis_asuransi = jsonObject1.getString("harga_karcis_asuransi");


                                    _txt_id_karcis_tmbhn.setText(_id);
                                    _txt_kode_ksda_tmbhn.setText( _kode_ksda );
                                    _txt_kode_lokasi_tmbhn.setText( _kode_lokasi );
                                    _txt_kode_karcis_tmbhn.setText( _kode_karcis );
                                    _txt_nama_karcis_tmbhn.setText( _nama_karcis );
                                    _txt_harga_karcis_wisata_tmbhn.setText( _harga_karcis_wisata );
//                                _txt_harga_karcis_wisata_tmbhn.setText( "xxx" );
                                    _txt_harga_karcis_asuransi_tmbhn.setText(_harga_karcis_asuransi);

                                    Log.i("","_harga_karcis_wisata lokwis "+_harga_karcis_wisata);


                                long selisih_hari = get_selisih_day();
                                final double _jml_krcs_wisnu = Help.ParseDouble(((EditText) findViewById(R.id.txt_jml_krcs_wisnu_ori)).getText().toString());
                                final double _jml_krcs_wisman = Help.ParseDouble(((EditText) findViewById(R.id.txt_jml_krcs_wisman_ori)).getText().toString());
                                final double _jml_krcs_tmbhn = Help.ParseDouble(((EditText) findViewById(R.id.txt_jml_krcs_wisman_tmbhn_ori)).getText().toString());


                                final double hrg_krcs_wisnu = Help.ParseDouble(((TextView) findViewById(R.id.txt_harga_karcis_wisata_wisnu)).getText().toString());
                                final double hrg_krcs_wisman = Help.ParseDouble(((TextView) findViewById(R.id.txt_harga_karcis_wisata_wisman)).getText().toString());

                                final double hrg_krcs_tmbhn =  Help.ParseDouble(_harga_karcis_wisata);
                                final double hrg_krcs_asrnsi_wisnu = Help.ParseDouble(((TextView) findViewById(R.id.txt_harga_karcis_asuransi_wisnu)).getText().toString());
                                final double hrg_krcs_asrnsi_wisman = Help.ParseDouble(((TextView) findViewById(R.id.txt_harga_karcis_asuransi_wisman)).getText().toString());


                                /* Rumus perhitungan karcis dan biaya asuransi  */
                                final int  ttl_wisnu ;
                                final int   ttl_wisman;
                                final int ttl_wisnu_wisman ;
                                final int ttl_tmbhn ;
                                final int grand_ttl ;

                                long selisih_day = get_selisih_day();
                                Log.i("","selisih_day calc"+selisih_day);

                                int new_ttl;
                                if( selisih_day >0 ){
                                    ttl_wisnu = (int) ((hrg_krcs_wisnu+hrg_krcs_asrnsi_wisnu)*_jml_krcs_wisnu)* (int) selisih_day;
                                    ttl_wisman =(int) ((hrg_krcs_wisman+hrg_krcs_asrnsi_wisman)*_jml_krcs_wisman * (int) selisih_day);
                                } else {
                                    ttl_wisnu = (int) ((hrg_krcs_wisnu+hrg_krcs_asrnsi_wisnu)*_jml_krcs_wisnu);
                                    ttl_wisman =(int) ((hrg_krcs_wisman+hrg_krcs_asrnsi_wisman)*_jml_krcs_wisman);
                                }

                                ttl_wisnu_wisman = (ttl_wisnu+ttl_wisman);
                                if( selisih_day >0 ){
                                    ttl_tmbhn = (int) ((hrg_krcs_tmbhn*_jml_krcs_tmbhn)*  (int) selisih_day);
                                } else {
                                    ttl_tmbhn = (int) (hrg_krcs_tmbhn*_jml_krcs_tmbhn);
                                }

                                grand_ttl =(int) (ttl_wisnu_wisman+ttl_tmbhn);

                                _txt_ttl.setText(String.valueOf(ttl_wisnu_wisman));
                                _txt_ttl_tmbhn.setText(String.valueOf(ttl_tmbhn));
                                _txt_grand_ttl.setText(String.valueOf(grand_ttl));


                                final ImageView img2 = findViewById(R.id.krcsTmbhnPicasso);
                                Transformation transformation = new RoundedTransformationBuilder()
//                                        .borderColor(Color.BLACK)
//                                        .borderWidthDp(3)
//                                        .cornerRadiusDp(30)
                                        .borderWidthDp(1)
                                        .oval(false)
                                        .build();

                                Picasso.with(getApplicationContext())
                                        .load(_url_image)
                                        .fit()
                                        .transform(transformation)
                                        .placeholder(R.drawable.loading_animation)
                                        .into(img2);

                            }
                        }

                    } catch (JSONException | ParseException e) {
                        Log.i("", "error horizontalKarcisWisatawanUtama=" + e.toString() );
                        e.printStackTrace();
                    }
                    requestQueue_s2.stop();
                }, error -> {
            Log.i("tag", "response =" + error.toString());
            error.printStackTrace();
            requestQueue_s2.stop();
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> obj = new HashMap<String, String>();
                obj.put("lokasi", LP );
                obj.put("tgl_kunjungan", Help.getDateTime().trim() );
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest_s2.setRetryPolicy(policy);
        requestQueue_s2.add(stringRequest_s2);
    }

    private void horizontalKarcisWisatawanUtamaFirstForLoKwIS (String EP,String Lksi) {
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url_s2 = "http://"+ Help.domain_api() +"/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue_s2 = Volley.newRequestQueue(getApplicationContext() );
        StringRequest stringRequest_s2 = new StringRequest(Request.Method.POST, server_url_s2,
                response -> {

                    try {
                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        if( Help.isJSONValid(response) ){
                            JSONObject jsonObject = new JSONObject(response.toString());
                            Log.i("tag","success horizontalKarcisWisatawanUtama First= " + jsonObject.getBoolean("success") );

                            arrKarcisUtama.clear();
                            if( jsonObject.getBoolean("success") ) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                Log.i("tag"," dt jsonArray First 2: "+jsonArray.toString());

//                                for (int i = 0; i <jsonArray.length();i++ ) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                                String _url_image = jsonObject1.getString("url_image");
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


                                _txt_id_karcis_utama.setText(_id);
                                _txt_kode_ksda.setText( _kode_ksda );
                                _txt_kode_lokasi.setText( _kode_lokasi );
                                _txt_kode_karcis.setText( _kode_karcis );
                                _txt_nama_karcis.setText( _nama_karcis );
                                _txt_harga_karcis_wisata_wisnu.setText( _harga_karcis_wisata_wisnu );
                                _txt_harga_karcis_wisata_wisman.setText( _harga_karcis_wisata_wisman );
                                _txt_harga_karcis_asuransi_wisnu.setText(_harga_karcis_asuransi_wisnu);
                                _txt_harga_karcis_asuransi_wisman.setText(_harga_karcis_asuransi_wisman);

                                _harga_karcis_wisata_wisnu_ku.setText(_harga_karcis_wisata_wisnu);
                                _harga_karcis_wisata_wisman_ku.setText(_harga_karcis_wisata_wisman);
                                _harga_karcis_asuransi_wisnu_ku.setText(_harga_karcis_asuransi_wisnu);
                                _harga_karcis_asuransi_wisman_ku.setText(_harga_karcis_asuransi_wisman);


                                horizontalKarcisWisatawanTambahanFirstforLokWis("daftar_karcis_wisatawan_tambahan",_kode_lokasi);


                                sessionManager.createSessionWisUtm(
                                        _kode_ksda,
                                        _kode_lokasi,
                                        _kode_karcis,
                                        _nama_karcis,
                                        _kode_libur,
                                        _harga_karcis_wisata_wisnu,
                                        _harga_karcis_wisata_wisman,
                                        _harga_karcis_asuransi_wisnu,
                                        _harga_karcis_asuransi_wisman,
                                        _id ,
                                        _url_image );

                                final ImageView img1 =(ImageView)findViewById(R.id.krcsUtamaPicasso);
                                Transformation transformation = new RoundedTransformationBuilder()
//                                        .borderColor()
//                                        .borderWidthDp(3)
                                        .borderWidthDp(1)
                                        .oval(false)
                                        .build();

                                Picasso.with(getApplicationContext())
                                        .load(_url_image)
                                        .fit()
                                        .placeholder(R.drawable.loading_animation)
                                        .transform(transformation)
                                        .into(img1);


                            }
                        }

                    } catch (JSONException e) {
                        Log.i("", "error horizontalKarcisWisatawanUtama=" + e.toString() );
                        e.printStackTrace();
                    }
                    requestQueue_s2.stop();
                }, error -> {
            Log.i("tag", "response =" + error.toString());
            error.printStackTrace();
            requestQueue_s2.stop();
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> obj = new HashMap<String, String>();

                Log.i("tag", "Lksi =" + Lksi );
                obj.put("lokasi", Lksi );
                obj.put("tgl_kunjungan", Help.getDateTime().trim() );
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest_s2.setRetryPolicy(policy);
        requestQueue_s2.add(stringRequest_s2);
    }


    private void horizontalKarcisWisatawanUtama (String EP,String LP){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url_s2 = "http://"+ Help.domain_api() +"/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue_s2 = Volley.newRequestQueue(getApplicationContext() );
        StringRequest stringRequest_s2 = new StringRequest(Request.Method.POST, server_url_s2,
                response -> {

                    try {
                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        if( Help.isJSONValid(response) ){
                            JSONObject jsonObject = new JSONObject(response.toString());
                            Log.i("tag","success horizontalKarcisWisatawanUtama= " + jsonObject.getBoolean("success") );

                            arrKarcisUtama.clear();
                            if( jsonObject.getBoolean("success") ) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                Log.i("tag"," dt jsonArray: "+jsonArray.toString());

                                for (int i = 0; i <jsonArray.length();i++ ) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String _url_image = jsonObject1.getString("url_image");
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

                                    arrKarcisUtama.add(new SpinnerKarcisUtama( _kode_ksda, _kode_lokasi, _kode_karcis,
                                            _nama_karcis, _kode_libur, _harga_karcis_wisata_wisnu,
                                            _harga_karcis_wisata_wisman, _harga_karcis_asuransi_wisnu,
                                            _harga_karcis_asuransi_wisman, _id ));

                                    sessionManager.createSessionWisUtm(_kode_lokasi,
                                            _kode_lokasi,
                                            _kode_karcis,
                                            _nama_karcis,_kode_libur,
                                            _harga_karcis_wisata_wisnu,
                                            _harga_karcis_wisata_wisman,
                                            _harga_karcis_asuransi_wisnu,
                                            _harga_karcis_asuransi_wisman,
                                            _id,
                                            _url_image
                                    );

                                    _txt_kode_ksda.setText( _kode_ksda );
                                    _txt_kode_lokasi.setText( _kode_lokasi );
                                    _txt_kode_karcis.setText( _kode_karcis );
//                                    _txt_nama_karcis.setText( _nama_karcis );
                                    _txt_harga_karcis_wisata_wisnu.setText( _harga_karcis_wisata_wisnu );
                                    _txt_harga_karcis_wisata_wisman.setText( _harga_karcis_wisata_wisman );
                                    _txt_harga_karcis_asuransi_wisnu.setText(_harga_karcis_asuransi_wisnu);
                                    _txt_harga_karcis_asuransi_wisman.setText(_harga_karcis_asuransi_wisman);
                                }
                            }
                        }
//                        _spiner_krc_utm_wstwn_order.setAdapter(new ArrayAdapter<SpinnerKarcisUtama>(PesanKarcisWisatawanActivity.this, android.R.layout.simple_spinner_item,arrKarcisUtama) );
                    } catch (JSONException e) {
                        Log.i("", "error horizontalKarcisWisatawanUtama=" + e.toString() );
                        e.printStackTrace();
                    }
                    requestQueue_s2.stop();
                }, error -> {
            Log.i("tag", "response =" + error.toString());
            error.printStackTrace();
            requestQueue_s2.stop();

            findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            AlertDialog.Builder builder = new AlertDialog.Builder(PesanKarcisWisatawanActivity.this);
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
                String tgl_kujungan_val = (String) ""+_txt_tgl_kunjungan_order.getText();

                if( tgl_kujungan_val == "" ){
                    tgl_kujungan_val = Help.getDateTime();
                }

                Log.i("tag", "LP Spinner utama =" + LP );
                obj.put("lokasi", LP );
                obj.put("tgl_kunjungan",tgl_kujungan_val.trim() );
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest_s2.setRetryPolicy(policy);
        requestQueue_s2.add(stringRequest_s2);
    }

    private void horizontalKarcisWisatawanTambahan (String EP,String LP){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url_s2 = "http://"+ Help.domain_api() +"/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue_s2 = Volley.newRequestQueue(getApplicationContext() );
        StringRequest stringRequest_s2 = new StringRequest(Request.Method.POST, server_url_s2,
                response -> {

                    try {
                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        if( Help.isJSONValid(response) ){
                            JSONObject jsonObject = new JSONObject(response.toString());
                            Log.i("tag","success horizontalKarcisWisatawanTambahan= " + jsonObject.getBoolean("success") );

                            arrKarcisUtama.clear();
                            if( jsonObject.getBoolean("success") ) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                Log.i("tag"," dt jsonArray: "+jsonArray.toString());

                                for (int i = 0; i <jsonArray.length();i++ ) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String _id = jsonObject1.getString("id");
                                    String _kode_ksda = jsonObject1.getString("kode_ksda");
                                    String _kode_lokasi = jsonObject1.getString("kode_lokasi");
                                    String _kode_karcis = jsonObject1.getString("kode_karcis");
                                    String _nama_karcis = jsonObject1.getString("nama_karcis");
                                    String _kode_libur = jsonObject1.getString("kode_libur");
                                    String _harga_karcis_wisata = jsonObject1.getString("harga_karcis_wisata");
                                    String _harga_karcis_asuransi = jsonObject1.getString("harga_karcis_asuransi");

                                    arrKarcisTambahan.add(new SpinnerKarcisTambahan(_kode_karcis,_nama_karcis,_harga_karcis_wisata, _id));

                                    _txt_kode_ksda_tmbhn.setText( _kode_ksda );
                                    _txt_kode_lokasi_tmbhn.setText( _kode_lokasi );
                                    _txt_kode_karcis_tmbhn.setText( _kode_karcis );
                                    _txt_nama_karcis_tmbhn.setText( _nama_karcis );
                                    _txt_harga_karcis_wisata_tmbhn.setText( _harga_karcis_wisata );
                                    _txt_harga_karcis_asuransi_tmbhn.setText(_harga_karcis_asuransi);
                                }
                            }
                        }

                    } catch (JSONException e) {
                        Log.i("", "error horizontalKarcisWisatawanUtama=" + e.toString() );
                        e.printStackTrace();
                    }
                    requestQueue_s2.stop();
                }, error -> {
            Log.i("tag", "response =" + error.toString());
            error.printStackTrace();
            requestQueue_s2.stop();

            findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            AlertDialog.Builder builder = new AlertDialog.Builder(PesanKarcisWisatawanActivity.this);
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
                String tgl_kujungan_val = (String) ""+_txt_tgl_kunjungan_order.getText();
                if( tgl_kujungan_val == "" ){
                    tgl_kujungan_val = Help.getDateTime();
                }
                obj.put("lokasi", LP );
                obj.put("tgl_kunjungan",tgl_kujungan_val.trim() );
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest_s2.setRetryPolicy(policy);
        requestQueue_s2.add(stringRequest_s2);
    }

    private void horizontalLokasiWisata(String EP,String jdl, String text,String alamat,String kota){

        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://"+ Help.domain_api() +"/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue( getApplicationContext() );
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                response -> {
                    Log.i("triono", "response horizontalLokasiWisata =" + response );
                    try {

                        if( Help.isJSONValid(response) ){
                            JSONObject jsonObject = new JSONObject(response.toString());
                            findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                            String _nm_obj_wisata;
                            String _kd_lokasi;
                            String _alamat;
                            String _kota;
                            String _url_image;


                            if( jsonObject.getBoolean("success") ) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");


//                                for (int i = 0; i <jsonArray.length();i++ ) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                                    _nm_obj_wisata = jsonObject1.getString("nama");
                                    _kd_lokasi = jsonObject1.getString("kode_ksda");
                                    _alamat = jsonObject1.getString("alamat");
                                    _kota = jsonObject1.getString("kota");
                                    _url_image = jsonObject1.getString("url_image");

                                    Log.i("","_kd_lokasi= "+_kd_lokasi);
                                    Log.i("","_nm_obj_wisata= "+_nm_obj_wisata);

                                horizontalLokasiPintu("daftar_lokasi_pintu", _kd_lokasi, getApplicationContext(),"","","null");

                                final String session_kd_lok_wis = sessionManager.getDataLokWisPesankarcisWisatawan().get(SessionManager.key_kd_lokwis);
                                final String session_nm_lok_wis = sessionManager.getDataLokWisPesankarcisWisatawan().get(SessionManager.key_nm_lokwis);
                                final String session_url_lok_wis = sessionManager.getDataLokWisPesankarcisWisatawan().get(SessionManager.key_url_img_lokwis);


                                    _txt_kdlokwis.setText(_kd_lokasi);
                                    _txt_nmlokwis.setText(_nm_obj_wisata);
                                    _txt_urlLokWis.setText(_url_image);

                                sessionManager.createSessionLokWisPesankarcisWisatawan(_kd_lokasi,_nm_obj_wisata,_alamat,_kota,_url_image);



                                ImageView img1 =(ImageView)findViewById(R.id.lokwisPicasso);
                                Transformation transformation = new RoundedTransformationBuilder()
//                                        .borderColor(Color.BLUE)
//                                        .borderWidthDp(5)
                                        .borderWidthDp(1)
                                        .oval(false)
                                        .build();

                                Picasso.with(getApplicationContext())
                                        .load(_url_image)
                                        .fit()
                                        .transform(transformation)
                                        .placeholder(R.drawable.loading_animation)
                                        .into(img1);

                            }
                        }
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
            AlertDialog.Builder builder = new AlertDialog.Builder(PesanKarcisWisatawanActivity.this);
            builder.setMessage("Terjadi Gangguan, Refresh ")
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



    public void horizontalLokasiPintu(String EP, String KSDA_PAR, Context par, String kdLokOld, String nmLokOld, String url_img_lokwis){

        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://"+ Help.domain_api() +"/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext() );
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                response -> {
                    Log.i("", "response horizontalLokasiPintu =" + response );
                    try {

                        if( Help.isJSONValid(response) ){
                            JSONObject jsonObject = new JSONObject(response.toString());
                            findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                            String nm_obj_wisata_pintu;
                            String kd_lokasi_pintu;
                            String url_image;
                            lokWisList.clear();

                            if( jsonObject.getBoolean("success") ) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

//                                b2.setText(" "+ jsonArray.length() );

//                                for (int i = 0; i <jsonArray.length();i++ ) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                                    nm_obj_wisata_pintu = jsonObject1.getString("nama");
                                    kd_lokasi_pintu = jsonObject1.getString("kode_lokasi");
                                   url_image = jsonObject1.getString("url_image");

//                                    Log.i("","kd_lokasi pintu= "+kd_lokasi_pintu);
                                    Log.i("","url_image pintu= "+url_image);
                                _txt_urlLokWisOld.setText(url_img_lokwis);

                                horizontalKarcisWisatawanUtamaFirst("daftar_karcis_wisatawan_utama",kd_lokasi_pintu);
                                horizontalKarcisWisatawanTambahanFirst("daftar_karcis_wisatawan_tambahan",kd_lokasi_pintu);

                                final String session_kd_lok_pintu = sessionManager.getDataLokPintuPesankarcisWisatawan().get(SessionManager.key_kd_lokPintu);
                                final String session_nm_lok_pintu = sessionManager.getDataLokPintuPesankarcisWisatawan().get(SessionManager.key_nm_lokPintu);
                                final String session_url_lok_pintu = sessionManager.getDataLokPintuPesankarcisWisatawan().get(SessionManager.key_url_img_lokPintu);



                                Log.i("","session_kd_lok_pintu = "+session_kd_lok_pintu);
                                Log.i("","session_nm_lok_pintu = "+session_nm_lok_pintu);
                                Log.i("","session_url_lok_pintu = "+session_url_lok_pintu);


                                    _txt_kdlokPintu.setText(kd_lokasi_pintu);
                                    _txt_nmlokPintu.setText(nm_obj_wisata_pintu);
                                    _txt_urlLokPintu.setText(url_image);

                                sessionManager.createSessionLokPintuPesankarcisWisatawan(kd_lokasi_pintu,nm_obj_wisata_pintu,url_image);

                                ImageView img1 =(ImageView)findViewById(R.id.lokPintuPicasso);
                                Transformation transformation = new RoundedTransformationBuilder()
//                                        .borderColor(Color.BLACK)
//                                        .borderWidthDp(3)
                                        .borderWidthDp(1)
                                        .oval(false)
                                        .build();

                                Picasso.with(getApplicationContext())
                                        .load(url_image)
                                        .fit()
                                        .transform(transformation)
                                        .placeholder(R.drawable.loading_animation)
                                        .into(img1);

//                                }
                            }
                        }
                    } catch (JSONException e) {
                        Log.i("", "error ===" + e.toString() );
                        e.printStackTrace();
                    }
                    requestQueue.stop();
                }, error -> {
            Log.i("", "response error horizontalLokasiPintu=" + error.toString());
            error.printStackTrace();
            requestQueue.stop();
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> obj = new HashMap<String, String>();
                Log.e("","KSDA_PAR= "+KSDA_PAR);
                obj.put("kode_ksda", KSDA_PAR);
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }


    public void horizontalLokasiPintuForWisata(String EP, String KSDA_PAR, Context par, String kdLokOld, String nmLokOld, String url_img_lokwis){

        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://"+ Help.domain_api() +"/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext() );
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                response -> {
                    Log.i("", "response horizontalLokasiPintu =" + response );
                    try {

                        if( Help.isJSONValid(response) ){
                            JSONObject jsonObject = new JSONObject(response.toString());
                            findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                            String nm_obj_wisata_pintu;
                            String kd_lokasi_pintu;
                            String url_image;
                            lokWisList.clear();

                            if( jsonObject.getBoolean("success") ) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

//                                b2.setText(" "+ jsonArray.length() );

//                                for (int i = 0; i <jsonArray.length();i++ ) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                                nm_obj_wisata_pintu = jsonObject1.getString("nama");
                                kd_lokasi_pintu = jsonObject1.getString("kode_lokasi");
                                url_image = jsonObject1.getString("url_image");

//                                    Log.i("","kd_lokasi pintu= "+kd_lokasi_pintu);
                                Log.i("","url_image pintu= "+url_image);

                                horizontalKarcisWisatawanUtamaFirstForLoKwIS("daftar_karcis_wisatawan_utama",kd_lokasi_pintu);

                                _txt_urlLokWisOld.setText(url_img_lokwis);

                                final String session_kd_lok_pintu = sessionManager.getDataLokPintuPesankarcisWisatawan().get(SessionManager.key_kd_lokPintu);
                                final String session_nm_lok_pintu = sessionManager.getDataLokPintuPesankarcisWisatawan().get(SessionManager.key_nm_lokPintu);
                                final String session_url_lok_pintu = sessionManager.getDataLokPintuPesankarcisWisatawan().get(SessionManager.key_url_img_lokPintu);

                                Log.i("","session_kd_lok_pintu = "+session_kd_lok_pintu);
                                Log.i("","session_nm_lok_pintu = "+session_nm_lok_pintu);
                                Log.i("","session_url_lok_pintu = "+session_url_lok_pintu);


                                _txt_kdlokPintu.setText(kd_lokasi_pintu);
                                _txt_nmlokPintu.setText(nm_obj_wisata_pintu);
                                _txt_urlLokPintu.setText(url_image);


                                sessionManager.createSessionLokPintuPesankarcisWisatawan(kd_lokasi_pintu,nm_obj_wisata_pintu,url_image);

                                ImageView img1 =(ImageView)findViewById(R.id.lokPintuPicasso);
                                Transformation transformation = new RoundedTransformationBuilder()
//                                        .borderColor(Color.BLACK)
//                                        .borderWidthDp(3)
                                        .borderWidthDp(1)
                                        .oval(false)
                                        .build();

                                Picasso.with(getApplicationContext())
                                        .load(url_image)
                                        .fit()
                                        .transform(transformation)
                                        .placeholder(R.drawable.loading_animation)
                                        .into(img1);

//                                }
                            }
                        }
                    } catch (JSONException e) {
                        Log.i("", "error ===" + e.toString() );
                        e.printStackTrace();
                    }
                    requestQueue.stop();
                }, error -> {
            Log.i("", "response error horizontalLokasiPintu=" + error.toString());
            error.printStackTrace();
            requestQueue.stop();
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> obj = new HashMap<String, String>();
                Log.e("","KSDA_PAR= "+KSDA_PAR);
                obj.put("kode_ksda", KSDA_PAR);
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }



    @SuppressLint("SetTextI18n")
    public void CalculateKarcis() throws ParseException {
        final double _jml_krcs_wisnu = Help.ParseDouble(((EditText) findViewById(R.id.txt_jml_krcs_wisnu_ori)).getText().toString());
        final double _jml_krcs_wisman = Help.ParseDouble(((EditText) findViewById(R.id.txt_jml_krcs_wisman_ori)).getText().toString());
        final double _jml_krcs_tmbhn = Help.ParseDouble(((EditText) findViewById(R.id.txt_jml_krcs_wisman_tmbhn_ori)).getText().toString());


        final double hrg_krcs_wisnu = Help.ParseDouble(((TextView) findViewById(R.id.txt_harga_karcis_wisata_wisnu)).getText().toString());
        final double hrg_krcs_wisman = Help.ParseDouble(((TextView) findViewById(R.id.txt_harga_karcis_wisata_wisman)).getText().toString());
        final double hrg_krcs_tmbhn = Help.ParseDouble(((TextView) findViewById(R.id.txt_harga_karcis_wisata_tmbhn)).getText().toString());
//        final String hrg_krcs_tmbhn =  _txt_harga_karcis_wisata_tmbhn.getText().toString();
        final double hrg_krcs_asrnsi_wisnu = Help.ParseDouble(((TextView) findViewById(R.id.txt_harga_karcis_asuransi_wisnu)).getText().toString());
        final double hrg_krcs_asrnsi_wisman = Help.ParseDouble(((TextView) findViewById(R.id.txt_harga_karcis_asuransi_wisman)).getText().toString());

        final int  ttl_wisnu ;
        final int   ttl_wisman;
        final int ttl_wisnu_wisman ;
        final int ttl_tmbhn ;
        final int grand_ttl ;


//        final  String TGL = findViewById(R.id.txt_tgl_kunjungan_order);
//        final String TGL =  _txt_tgl_kunjungan_order.getText().toString().trim();
//        final String TGL2 =  _txt_tgl_kunjungan_2_order.getText().toString().trim();
//        String dateStr1 = TGL;
//        String dateStr2 = TGL2;
//
//        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date dateOne = sdf.parse(dateStr1);
//        Date dateTwo = sdf.parse(dateStr2);
//
//        long timeOne = dateOne.getTime();
//        long timeTwo = dateTwo.getTime();
//        long oneDay = 1000 * 60 * 60 * 24;
//        long selisih_hari = (timeTwo - timeOne) / oneDay;
//
//        Log.i("","calc selisih_hari "+selisih_hari);
//        Log.i("","TGL "+TGL);
//        Log.i("","TGL2 "+TGL2);

        long selisih_day = get_selisih_day();
        Log.i("","selisih_day calc"+selisih_day);

        /* Rumus perhitungan karcis dan biaya asuransi  */
        int new_ttl;
        if( selisih_day >0 ){
            ttl_wisnu = (int) ((hrg_krcs_wisnu+hrg_krcs_asrnsi_wisnu)*_jml_krcs_wisnu)* (int) selisih_day;
            ttl_wisman =(int) ((hrg_krcs_wisman+hrg_krcs_asrnsi_wisman)*_jml_krcs_wisman * (int) selisih_day);
        } else {
            ttl_wisnu = (int) ((hrg_krcs_wisnu+hrg_krcs_asrnsi_wisnu)*_jml_krcs_wisnu);
            ttl_wisman =(int) ((hrg_krcs_wisman+hrg_krcs_asrnsi_wisman)*_jml_krcs_wisman);
        }


        ttl_wisnu_wisman = (ttl_wisnu+ttl_wisman);
        if( selisih_day >0 ){
            ttl_tmbhn = (int) ((hrg_krcs_tmbhn*_jml_krcs_tmbhn)*  (int) selisih_day);
        } else {
            ttl_tmbhn = (int) (hrg_krcs_tmbhn*_jml_krcs_tmbhn);
        }

        grand_ttl =(int) (ttl_wisnu_wisman+ttl_tmbhn);

        Log.i("tag","_jml_krcs_wisnu= "+ _jml_krcs_wisnu);
        Log.i("tag","_jml_krcs_wisman= "+ _jml_krcs_wisman);
        Log.i("tag","calc _jml_krcs_tmbhn= "+ _jml_krcs_tmbhn);

        Log.i("tag","hrg_krcs_wisnu= "+ hrg_krcs_wisnu);
        Log.i("tag","hrg_krcs_wisman= "+ hrg_krcs_wisman);
        Log.i("tag","hrg_krcs_tmbhn= "+ hrg_krcs_tmbhn);
        Log.i("tag","hrg_krcs_asrnsi_wisnu= "+ hrg_krcs_asrnsi_wisnu);
        Log.i("tag","hrg_krcs_asrnsi_wisman= "+ hrg_krcs_asrnsi_wisman);

        Log.i("tag","calc ttl_wisnu= "+ ttl_wisnu);
        Log.i("tag","calc ttl_wisman= "+ ttl_wisman);
        Log.i("tag","ttl_wisnu_wisman= "+ttl_wisnu_wisman);
        Log.i("tag","ttl_tmbhn = "+ttl_tmbhn);
        Log.i("tag","grand_ttl = "+grand_ttl);

        _txt_ttl.setText(String.valueOf(ttl_wisnu_wisman));
        _txt_ttl_tmbhn.setText(String.valueOf(ttl_tmbhn));
        _txt_grand_ttl.setText(String.valueOf(grand_ttl));

//        _txt_day.setText(" x "+ selisih_day +" Hari.");

    }

    public void ClearCalculateKarcis(){

        _txt_jml_krcs_wisnu.setText("");
        _txt_jml_krcs_wisman.setText("");
        _txt_ttl.setText("0");
        _txt_tgl_kunjungan_order.setText("");

        _txt_ttl_tmbhn.setText("0");
        _txt_grand_ttl.setText("0");

    }

public long get_selisih_day() throws ParseException {
    final String TGL =  _txt_tgl_kunjungan_order.getText().toString().trim();
    final String TGL2 =  _txt_tgl_kunjungan_2_order.getText().toString().trim();
    String dateStr1 = TGL;
    String dateStr2 = TGL2;

    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date dateOne = sdf.parse(dateStr1);
    Date dateTwo = sdf.parse(dateStr2);

    long timeOne = dateOne.getTime();
    long timeTwo = dateTwo.getTime();
    long oneDay = 1000 * 60 * 60 * 24;
    long selisih_hari = ((timeTwo - timeOne) / oneDay)+1;

//    Log.i("","calc selisih_hari fn "+selisih_hari);
//    Log.i("","TGL "+TGL);
//    Log.i("","TGL2 "+TGL2);

    return selisih_hari;

}

    private void inputKarcisWisatawan(String EP, long selisih_hari, String nama_pembayaran_par ) {
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://"+ Help.domain_api() +"/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(PesanKarcisWisatawanActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                (Response.Listener<String>) response -> {
                    Log.i("tag","response= " + response );
                    try {
                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        if( Help.isJSONValid(response) ){

                            JSONObject jObj = new JSONObject(response);
                            String data = jObj.getString("data");
                            JSONObject jsonObject1 = new JSONObject(data);
                            Boolean berhasil = jsonObject1.getBoolean("berhasil");

                            Log.i("tag","success= " + jObj.getBoolean("success") );
                            Log.i("tag","data= " + data );
                            Log.i("tag","berhasil= " + berhasil );

                            if( berhasil ) {
                                String _id = jsonObject1.getString("id");
                                String _va_no = jsonObject1.getString("va_no");
                                String _va_no_berlaku_sd = jsonObject1.getString("va_no_berlaku_sd");
                                String _vnama = jsonObject1.getString("nama");
                                String _alamat_email = jsonObject1.getString("alamat_email");
                                String _sellular_no = jsonObject1.getString("sellular_no");
                                String _jumlah_wisnu = jsonObject1.getString("jumlah_wisnu");
                                String _jumlah_wisman = jsonObject1.getString("jumlah_wisman");
                                String _jumlah_karcis = jsonObject1.getString("jumlah_karcis");
                                String _tgl_penjualan = jsonObject1.getString("tgl_penjualan");
                                String _tgl_kunjungan = jsonObject1.getString("tgl_kunjungan");
                                String _menit_valid = jsonObject1.getString("menit_valid");
                                String _tgl_valid = jsonObject1.getString("tgl_valid");
                                String _tagihan_total = jsonObject1.getString("tagihan_total");
//                                String _nama_lokasi = jsonObject1.getString("nama_lokasi");
//                                String _jumlah_tambahan = jsonObject1.getString("jumlah_tambahan");

                                final String tgl_kunjungan_sd =  _txt_tgl_kunjungan_2_order.getText().toString().trim();

                                Intent i = new Intent(PesanKarcisWisatawanActivity.this, NotifSuksesActivity.class);
                                i.putExtra("result_dt_ket", "Pemesanan Anda Berhasil Silahkan Cek email!");
                                i.putExtra("_id", _id);
                                i.putExtra("_va_no", _va_no);
                                i.putExtra("_va_no_berlaku_sd", _va_no_berlaku_sd);
                                i.putExtra("_vnama", _vnama);
                                i.putExtra("_alamat_email", _alamat_email);
                                i.putExtra("_sellular_no", _sellular_no);
                                i.putExtra("_jumlah_wisnu", _jumlah_wisnu);
                                i.putExtra("_jumlah_wisman", _jumlah_wisman);
                                i.putExtra("_jumlah_karcis", _jumlah_karcis);
                                i.putExtra("_tgl_penjualan", _tgl_penjualan);
                                i.putExtra("_tgl_kunjungan", _tgl_kunjungan);
                                i.putExtra("_menit_valid", _menit_valid);
                                i.putExtra("_tgl_valid", _tgl_valid);
                                i.putExtra("_tagihan_total", _tagihan_total);
                                i.putExtra("_txt_nmlokwis", _txt_nmlokwis.getText().toString());
                                i.putExtra("_jumlah_tambahan", _txt_jml_krcs_tmbhn.getText().toString());
//                                i.putExtra("_nama_lokasi", "");
                                i.putExtra("_tgl_kunjungan_sd", tgl_kunjungan_sd);
                                i.putExtra("_selisih_hari", String.valueOf(selisih_hari) );
                                i.putExtra("result_dt_berhasil", berhasil);
                                i.putExtra("_nama_pembayaran", nama_pembayaran_par );
                                i.putExtra("result_dt_flag", "flagPesanKarcisWisatawan");
                                startActivity(i);
                            }
                        }
                    } catch (JSONException e) {
                        Log.i("", "error pesan karcis wisatawan" + e.toString() );
                        e.printStackTrace();
                    }
                    requestQueue.stop();
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("tag", "response =" + error.toString());
                error.printStackTrace();
                requestQueue.stop();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> obj = new HashMap<String, String>();


//                String flag_pemesan = "";
//                final String key_kode_ksda = (String) sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_kode_ksda);
//                final String key_name =  (String) sessionManager.getUserDetail().get(SessionManager.key_name);
//                final String key_email =  (String) sessionManager.getUserDetail().get(SessionManager.key_email);
//                final String key_hp =  (String) sessionManager.getUserDetail().get(SessionManager.key_hp);
//                final String key_tgl_penjualan =  Help.getDateTime();
//                final String tgl_kunjungan =  _txt_tgl_kunjungan_order.getText().toString().trim();
//                final String key_kode_lokasi =  (String) sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_kode_lokasi_wist_order);
//                final String key_id_utama =  (String) sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_id_wist_order);
//                final String key_id_tmbhn =  (String) sessionManager.getDaftarKarcisWisatawanTmbhn().get(SessionManager.key_id_tmbhn);
//                String jml_wisnu = _txt_jml_krcs_wisnu.getText().toString().trim();
//                String jml_wisman = _txt_jml_krcs_wisman.getText().toString().trim();
//                String jml_tmbhn = _txt_jml_krcs_tmbhn.getText().toString().trim();
//                String key_kode_lok_new = sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi);



                String flag_pemesan = "";
                final String key_kode_ksda = _txt_kode_ksda.getText().toString();
                final String key_name =  (String) sessionManager.getUserDetail().get(SessionManager.key_name);
                final String key_email =  (String) sessionManager.getUserDetail().get(SessionManager.key_email);
                final String key_hp =  (String) sessionManager.getUserDetail().get(SessionManager.key_hp);
                final String key_tgl_penjualan =  Help.getDateTime();
                final String tgl_kunjungan =  _txt_tgl_kunjungan_order.getText().toString().trim();
                final String tgl_kunjungan_sd =  _txt_tgl_kunjungan_2_order.getText().toString().trim();
                final String key_kode_lokasi =  _txt_kode_lokasi.getText().toString();
                final String key_id_utama =  _txt_id_karcis_utama.getText().toString();
                final String key_id_tmbhn =   _txt_id_karcis_tmbhn.getText().toString();
                String jml_wisnu = _txt_jml_krcs_wisnu.getText().toString().trim();
                String jml_wisman = _txt_jml_krcs_wisman.getText().toString().trim();
                String jml_tmbhn = _txt_jml_krcs_tmbhn.getText().toString().trim();
                String key_kode_lok_new = sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi);
                String jumlah_hari = String.valueOf(selisih_hari);

                Log.e("tag","SessionManager.key_kode_lokasi= "+sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi));
                Log.e("","key_kode_lok_new= "+key_kode_lok_new);

                if ( key_kode_lok_new != null ){
                    flag_pemesan = "1";
                } else {
                    flag_pemesan= "2";
                }

                Log.e("","flag_pemesan ok= "+flag_pemesan);



                Log.e("tag","key_kode_lok_new = " + key_kode_lok_new );
                Log.i("tag","jml_wisnu= " + jml_wisnu );
                Log.i("tag","jml_wisman= " + jml_wisman );
                Log.i("tag","jml_tmbhn= " + jml_tmbhn );

                if(jml_wisnu.equals("")){
                    jml_wisnu = "0";
                }
                if(jml_wisman.equals("")){
                    jml_wisman = "0";
                }
                if(jml_tmbhn.equals("")){
                    jml_tmbhn = "0";
                }


//                Log.i("tag","key_kode_ksda= " + key_kode_ksda );
                Log.i("tag","key_kode_lok_new= " + key_kode_lok_new );
                Log.i("tag","key_name= " + key_name );
                Log.i("tag","key_email= " + key_email );
                Log.i("tag","key_hp= " + key_hp );
                Log.i("tag","key_tgl_penjualan= " + key_tgl_penjualan );
                Log.i("tag","tgl_kunjungan= " + tgl_kunjungan );
                Log.i("tag","tgl_kunjungan_sd= " + tgl_kunjungan_sd );

                Log.i("tag","key_kode_lokasi= " + key_kode_lokasi );
                Log.i("tag","key_id_utama= " + key_id_utama );
                Log.i("tag","key_id_tmbhn= " + key_id_tmbhn );
                Log.i("tag","jml_wisnu= " + jml_wisnu );
                Log.i("tag","jml_wisman= " + jml_wisman );
                Log.i("tag","jml_tmbhn= " + jml_tmbhn );
                Log.i("tag","flag_pemesan= " + flag_pemesan );
                Log.i("tag","jumlah_hari= " + jumlah_hari );



//                obj.put("registration_by","triono.triono1@gmail.com");
//                obj.put("flag_pemesan","1");
//                obj.put("nama","triono");
//                obj.put("sellular_no","081908130663");
//                obj.put("alamat_email","triono.triono1@gmail.com");
//                obj.put("tgl_penjualan","2020-01-1");
//                obj.put("tgl_kunjungan","2020-01-02");
//                obj.put("kode_lokasi","27001");
//                obj.put("id_karcis_utama","2d3154acf88e48ef92058a35a248e503");
//                obj.put("id_karcis_tambahan","852eb90a8f0442de8b63a09175cb9003");
//                obj.put("jumlah_wisnu","1");
//                obj.put("jumlah_wisman","1");
//                obj.put("jumlah_tambahan","1");


                obj.put("registration_by", key_email);
                obj.put("flag_pemesan",flag_pemesan);
                obj.put("nama",key_name);
                obj.put("sellular_no",key_hp);
                obj.put("alamat_email",key_email);
                obj.put("tgl_penjualan",key_tgl_penjualan);
                obj.put("tgl_kunjungan",tgl_kunjungan);
                obj.put("tgl_kunjungan_sd",tgl_kunjungan_sd);
                obj.put("kode_lokasi",key_kode_lokasi);
                obj.put("id_karcis_utama",key_id_utama);
                obj.put("id_karcis_tambahan",key_id_tmbhn);
                obj.put("jumlah_wisnu",jml_wisnu);
                obj.put("jumlah_wisman",jml_wisman);
                obj.put("jumlah_tambahan",jml_tmbhn);
                obj.put("jumlah_hari",jumlah_hari);
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }


    private void quotaTwa(String EP,String KSDA, String TGL){

                            findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
                            String server_url = "http://"+ Help.domain_api() +"/~androidwisata/?data="+ EP;
                            final RequestQueue requestQueue = Volley.newRequestQueue(PesanKarcisWisatawanActivity.this);
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                                    (Response.Listener<String>) response -> {
                                        Log.i("tag","response= " + response );
                         try {
                            if( Help.isJSONValid(response) ){
                                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                                JSONObject jsonObject = new JSONObject(response);
                                String data = jsonObject.getString("data");
                                JSONObject jsonObject1 = new JSONObject(data);
                                boolean _berhasil = jsonObject1.getBoolean("berhasil");

//                                if( _berhasil ) {
                                String  _id = jsonObject1.getString("id");
                                int _quota = jsonObject1.getInt("quota");
                                String _ket = jsonObject1.getString("keterangan");
                                Log.i("","_keterangan "+_ket);
                                Log.i("","_berhasil "+_berhasil);
                                Log.i("","qouta "+_quota);
                                Log.i("","_keterangan "+_ket);

                                if( _quota <= 0  ) {

                                    Intent i = new Intent(PesanKarcisWisatawanActivity.this, SuccessRegistrasiWisatawanActivity.class);
                                    final String _email = sessionManager.getUserDetail().get(SessionManager.key_email);
                                    i.putExtra("result_dt_ket", _ket);
                                    i.putExtra("result_dt_email",_email);
                                    i.putExtra("result_dt_berhasil", false);
                                    i.putExtra("result_dt_flag", "checkQuotaTwaOk");
                                    startActivity(i);

                                } else{
                                    TextView quotax = findViewById(R.id.text_quota_wist);
                                    quotax.setText(String.valueOf("Quota: "+_quota));
                                    quotax.setTextColor(Color.parseColor("#ffe458"));
                                    sessionManager.createSessionTglKunjungan(TGL);
                                    _btn_order_wist.setEnabled(true);
//                                    _btn_order_wist.setBackgroundColor(getResources().getColor(R.color.bluePrimary));
                                }

//                                    }
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
                }
                            ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> obj = new HashMap<String, String>();

                Log.i("","quota KSDA 2"+KSDA);
                Log.i("","quota TGL 2"+TGL);

                obj.put("kode_ksda", KSDA);
                obj.put("tgl_quota", TGL);
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    private void quotaTwaForBtnOrderWist(String EP,String KSDA, String TGL, String TGL2, String nama_pembayaran_par ){

        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url_q = "http://"+ Help.domain_api() +"/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue_q = Volley.newRequestQueue(PesanKarcisWisatawanActivity.this);

        StringRequest stringRequest_q = new StringRequest(Request.Method.POST, server_url_q,
                (Response.Listener<String>) response -> {
                    Log.i("tag", "response quotaTwaForBtnOrderWist =" + response );
                    try {

                        if( Help.isJSONValid(response) ){
                            findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                            JSONObject jsonObject = new JSONObject(response);
                            String data = jsonObject.getString("data");
                            JSONObject jsonObject1 = new JSONObject(data);
                            boolean _berhasil = jsonObject1.getBoolean("berhasil");

//                                if( _berhasil ) {
                            String  _id = jsonObject1.getString("id");
                            int _quota = jsonObject1.getInt("quota");
                            String _ket = jsonObject1.getString("keterangan");
                            Log.i("","_keterangan "+_ket);
                            Log.i("","_berhasil "+_berhasil);
                            Log.i("","qouta "+_quota);
                            Log.i("","_keterangan "+_ket);

                            if( _quota <= 0  ) {

                                Intent i = new Intent(PesanKarcisWisatawanActivity.this, SuccessRegistrasiWisatawanActivity.class);
                                final String _email = sessionManager.getUserDetail().get(SessionManager.key_email);
                                i.putExtra("result_dt_ket", _ket);
                                i.putExtra("result_dt_email",_email);
                                i.putExtra("result_dt_berhasil", false);
                                i.putExtra("result_dt_flag", "checkQuotaTwaOk");
                                startActivity(i);

                            } else {

                                long selisih_day = get_selisih_day();
                                Log.i("","selisih_day "+selisih_day);

                                inputKarcisWisatawan("new_input_wisatawan",selisih_day, nama_pembayaran_par);
                            }
                        }

                    } catch (JSONException | ParseException e) {
                        Log.i("", "error =" + e.toString() );
                        e.printStackTrace();
                    }
                    requestQueue_q.stop();
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("", "response =" + error.toString());
                error.printStackTrace();
                requestQueue_q.stop();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> obj = new HashMap<String, String>();

                Log.i("","quota KSDA 1"+KSDA);
                Log.i("","quota TGL 1"+TGL);

                obj.put("kode_ksda", KSDA);
                obj.put("tgl_quota", TGL);
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest_q.setRetryPolicy(policy);
        requestQueue_q.add(stringRequest_q);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getRbCaraBayar(String EP,String mode_pembayaran_par, String def_value){

        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://"+ Help.domain_api() +"/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                response -> {
                    Log.i("tag", "response getRbCaraBayar =" + response );
                    try {
                        if( Help.isJSONValid(response) ){
                            JSONObject jsonObject = new JSONObject(response);
                            findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                            if( jsonObject.getBoolean("success") ) {

                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i <jsonArray.length();i++ ) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                                    String id_x =  jsonObject1.getString("id");
                                    String bank_code =  jsonObject1.getString("bank_code");
                                    String bank_name =  jsonObject1.getString("bank_name");
                                    String bank_branch =  jsonObject1.getString("bank_branch");
                                    String bank_address =  jsonObject1.getString("bank_address");

                                    Log.i("tag","id_x= "+id_x);
                                    Log.i("tag","bank_code_x= "+bank_code);
                                    sessionManager.createSessionJnsByr(bank_code, bank_name);

                                    RadioButton button = new RadioButton(this);
                                    button.setId(Integer.parseInt(bank_code));
                                    button.setText(bank_name);
                                    button.setBackgroundResource(R.drawable.cardview);
                                    button.setWidth(700);
                                    rg_cara_bayar.addView(button);

                                    if( def_value != null ){
                                        if( i == 0 ) {
                                            button.setChecked(true);
                                            button.setSelected(true);
                                        }
                                    }

                                    if( bank_code.equals(mode_pembayaran_par)){
                                        button.setChecked(true);
                                        button.setSelected(true);
                                    }

                                    Log.i("","def_value "+  def_value);
                                    Log.i("","rdbtn.getId() "+button.getId());
                                    Log.i("","rdbtn.getText() "+button.getText());
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
                final String key_email =  sessionManager.getUserDetail().get(SessionManager.key_email).trim();
                obj.put("lokasi", "JELAJAH");
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);



    }

}
