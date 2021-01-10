package com.amanahgithawisata.aga;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.amanahgithawisata.aga.Adapter.PetugasAdapterKtActivity;
import com.amanahgithawisata.aga.Adapter.PetugasAdapterKuActivity;
import com.amanahgithawisata.aga.Adapter.SessionManager;
import com.amanahgithawisata.aga.Helper.Help;
import com.amanahgithawisata.aga.Model.SpinnerJnsByr;
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
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class PesanKarcisPetugasActivity<Hundler> extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    TextView _tgl_kunjungan_ptgs,_tgl_kunjungan_ptgs_2;
    TextView _jml_krcs_wisnu_ptgs;
    TextView _jml_krcs_wisman_ptgs;
    TextView _ttl_ptgs;
    TextView _jml_krcs_wisman_tmbhn_ptgs;
    TextView _ttl_tmbhn_ptgs;
    TextView _grand_ttl_ptgs;
    TextView _hp_pengunjung_ptgs;
    TextView _email_pengunjung;
    TextView _nama_pengunjung_ptgs;
    TextView _text_quota;

    TextView _txt_kdlokwis;
    TextView _txt_nmlokwis;
    TextView _txt_tgl_kunjungan_order;
    TextView _txt_kdlokPintu;
    TextView _txt_nmlokPintu;
    ScrollView scrollView;

    TextView	_kode_ksda_ku;
    TextView    _kode_lokasi_ku;
    TextView    _kode_karcis_ku;
    TextView    _nama_karcis_ku;
    TextView    _kode_libur_ku;
    TextView    _harga_karcis_wisata_wisnu_ku;
    TextView    _harga_karcis_wisata_wisman_ku;
    TextView    _harga_karcis_asuransi_wisnu_ku;
    TextView    _harga_karcis_asuransi_wisman_ku;
    TextView    _id_ku;
    ViewGroup  rg_cara_bayar;
    RadioGroup rgNew;
    RadioButton rbNew;

    TextView _kode_ksda_kt;
    TextView _kode_lokasi_kt;
    TextView _kode_karcis_kt;
    TextView _nama_karcis_kt;
    TextView _kode_libur_kt;
    TextView _harga_karcis_wisata_kt;
    TextView _harga_karcis_asuransi_kt;
    TextView _id_kt;
    TextView _url_image_kt;

    Button _btn_order_ptgs;
    Button btn_detail_ku;
    Button btn_detail_kt;

    ImageView _imgv;
    LinearLayout _linearLayoutKarcisUtama;
    LinearLayout _linearLayoutKarcisTambahan;
    BluetoothDevice bluetoothDevice;
    BluetoothAdapter bluetoothAdapter;
    BluetoothSocket bluetoothSocket;

    protected static final String TAG = "TAG";
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    Button mScan, mPrint, mDisc;

    private UUID applicationUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ProgressDialog mBluetoothConnectProgressDialog;

    private List<String> arrListUtamaPtgs = new ArrayList<String>();
    private List<String> arrListTambahanPtgs = new ArrayList<String>();
    ArrayList<SpinnerListWisata> arrListWisata = new ArrayList<SpinnerListWisata>();
    ArrayList<SpinnerListWisataKsda> arrListWisataKsda = new ArrayList<SpinnerListWisataKsda>();
    ArrayList<SpinnerJnsByr> arrJnsByr = new ArrayList<SpinnerJnsByr>();
    ArrayList<SpinnerKarcisUtama> arrKarcisUtama = new ArrayList<SpinnerKarcisUtama>();
    ArrayList<SpinnerKarcisTambahan> arrKarcisTambahan = new ArrayList<SpinnerKarcisTambahan>();

    SessionManager sessionManager;

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        TextView _tgl_kunjungan_order = (TextView) findViewById(R.id.tgl_kunjungan_ptgs);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        _tgl_kunjungan_ptgs.setText(sdf.format(c.getTime()));

        _text_quota = (TextView) findViewById(R.id.text_quota);

        final String LP = sessionManager.getDataLokWisPesankarcisWisatawan().get(SessionManager.key_kd_lokwis);

//        spinnerWisatawanTambahan("daftar_karcis_wisatawan_tambahan",LP);

        Log.i("","LP1="+LP);

    }



    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntArray("SCROLL_POSITION", new int[] {scrollView.getScrollX(),scrollView.getScrollY()} );
        Log.i("","scrollView.getScrollX() "+scrollView.getScrollX());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        final int[] position = savedInstanceState.getIntArray("SCROLL_POSITION");
        Log.i("","position "+ Arrays.toString(position));

        if (position != null ) {
            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    scrollView.scrollTo(position[0], position[1]);
                }
            });
           }
        }


    @Override
    public void onBackPressed() {
//                super.onBackPressed();
        Intent intent = new Intent(PesanKarcisPetugasActivity.this, DashboardPetugasActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan_karcis_petugas);
        sessionManager = new SessionManager(getApplicationContext());
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);



        _tgl_kunjungan_ptgs = (TextView) findViewById(R.id.tgl_kunjungan_ptgs);
        _tgl_kunjungan_ptgs_2 = (TextView) findViewById(R.id.tgl_kunjungan_ptgs_2);
        _jml_krcs_wisnu_ptgs = (EditText) findViewById(R.id.jml_krcs_wisnu_ptgs);
        _jml_krcs_wisman_ptgs = (EditText) findViewById(R.id.jml_krcs_wisman_ptgs);
        _jml_krcs_wisman_tmbhn_ptgs = (EditText) findViewById(R.id.jml_krcs_wisman_tmbhn_ptgs);
        _ttl_ptgs = (EditText) findViewById(R.id.ttl_ptgs);
        _ttl_tmbhn_ptgs = (EditText) findViewById(R.id.ttl_tmbhn_ptgs);
        _grand_ttl_ptgs = (EditText) findViewById(R.id.grand_ttl_ptgs);
        _btn_order_ptgs = (Button) findViewById(R.id.btn_order_ptgs);
        _hp_pengunjung_ptgs = (TextView) findViewById(R.id.hp_pengunjung_ptgs);
        _email_pengunjung = (TextView) findViewById(R.id.email_pengunjung_ptgs);
        _nama_pengunjung_ptgs = (TextView) findViewById(R.id.nama_pengunjung_ptgs);
        _linearLayoutKarcisUtama  = (LinearLayout) findViewById(R.id.linearLayoutKarcisUtama);;

        _txt_kdlokwis = findViewById(R.id.txt_kdlokwis);
        _txt_nmlokwis = findViewById(R.id.txt_nmlokwis);
        _txt_tgl_kunjungan_order = findViewById(R.id.tgl_kunjungan_ptgs);
        _txt_kdlokPintu = findViewById(R.id.txt_kdlokPintu);
        _txt_nmlokPintu = findViewById(R.id.txt_nmlokPintu);
        scrollView = (ScrollView) findViewById(R.id.scrollview_ptgs);

        _kode_ksda_ku  =  findViewById(R.id.kode_ksda_ku);
        _kode_lokasi_ku =  findViewById(R.id.kode_lokasi_ku);
        _kode_karcis_ku =  findViewById(R.id.kode_karcis_ku);
        _nama_karcis_ku =  findViewById(R.id.nama_karcis_ku);
        _kode_libur_ku =  findViewById(R.id.kode_libur_ku) ;
        _harga_karcis_wisata_wisnu_ku =  findViewById(R.id.harga_karcis_wisata_wisnu_ku);
        _harga_karcis_wisata_wisman_ku =  findViewById(R.id.harga_karcis_wisata_wisman_ku);
        _harga_karcis_asuransi_wisnu_ku =  findViewById(R.id.harga_karcis_asuransi_wisnu_ku);
        _harga_karcis_asuransi_wisman_ku =  findViewById(R.id.harga_karcis_asuransi_wisman_ku);
        _id_ku =  findViewById(R.id.id_ku);
        rg_cara_bayar = (ViewGroup) findViewById(R.id.rg_cara_bayar);

        _kode_ksda_kt = findViewById(R.id.kode_ksda_kt);
        _kode_lokasi_kt = findViewById(R.id.kode_lokasi_kt);
        _kode_karcis_kt = findViewById(R.id.kode_karcis_kt);
        _nama_karcis_kt = findViewById(R.id.nama_karcis_kt);
        _kode_libur_kt = findViewById(R.id.kode_libur_kt);
        _harga_karcis_wisata_kt = findViewById(R.id.harga_karcis_wisata_kt);
        _harga_karcis_asuransi_kt = findViewById(R.id.harga_karcis_asuransi_kt);
        _id_kt = findViewById(R.id.id_kt);
        _linearLayoutKarcisTambahan = findViewById(R.id.linearLayoutKarcisTambahan);
        btn_detail_ku = findViewById(R.id.btn_detail_ku);
        btn_detail_kt = findViewById(R.id.btn_detail_kt);

        Log.i("","savedInstanceState "+savedInstanceState);

        if (savedInstanceState != null) {

            final int[] position = savedInstanceState.getIntArray("SCROLL_POSITION");
            Log.i("","position "+ Arrays.toString(position));

            if (position != null ) {
                scrollView.post(() -> scrollView.scrollTo(position[0], position[1]));
            }

        } else {
            // Probably initialize members with default values for a new instance

        }




        final String KL = sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi);

//        spinnerLokPintuPtgs("daftar_lokasi_pintu", KL);
//        spinnerLokPintuPtgs("petugas_daftar_lokasi_wisata", KL);
//        spinnerJnsByrPtgs("informasi_mode_pembayaran","");
//        quotaTwa("quota_per_twa","");
        Log.i("","kl= "+ KL);



        _tgl_kunjungan_ptgs.setText(Help.getDateTime());
        _tgl_kunjungan_ptgs.setEnabled(false);
        _ttl_ptgs.setEnabled(false);
        _ttl_tmbhn_ptgs.setEnabled(false);
        _grand_ttl_ptgs.setEnabled(false);
        _txt_kdlokPintu.setVisibility(View.GONE);
        _txt_kdlokwis.setVisibility(View.GONE);

        _id_ku.setVisibility(View.GONE);
        _kode_ksda_ku.setVisibility(View.GONE);
        _kode_lokasi_ku.setVisibility(View.GONE);
        _kode_karcis_ku.setVisibility(View.GONE);
        _kode_libur_ku.setVisibility(View.GONE);
        _harga_karcis_wisata_wisnu_ku.setVisibility(View.GONE);
        _harga_karcis_wisata_wisman_ku.setVisibility(View.GONE);
        _harga_karcis_asuransi_wisnu_ku.setVisibility(View.GONE);
        _harga_karcis_asuransi_wisman_ku.setVisibility(View.GONE);

        _id_kt.setVisibility(View.GONE);
        _kode_ksda_kt.setVisibility(View.GONE);
        _kode_karcis_kt.setVisibility(View.GONE);
        _kode_libur_kt.setVisibility(View.GONE);
        _kode_lokasi_kt.setVisibility(View.GONE);
        _harga_karcis_asuransi_kt.setVisibility(View.GONE);
        _harga_karcis_wisata_kt.setVisibility(View.GONE);


        RadioGroup rg_cara_bayarn = (RadioGroup)findViewById(R.id.rg_cara_bayar);

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




        _tgl_kunjungan_ptgs_2.setOnFocusChangeListener((v13, hasFocus) -> {
            if (hasFocus){
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                @SuppressLint("SetTextI18n") DatePickerDialog datePickerDialog = new DatePickerDialog(v13.getContext(),
                        (view, year, monthOfYear, dayOfMonth) -> {
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat fr = new SimpleDateFormat("dd-MM-yyyy");

                            c.set(Calendar.YEAR, year);
                            c.set(Calendar.MONTH, monthOfYear);
                            c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                            String myFormat = "yyyy-MM-dd";
                            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                            _tgl_kunjungan_ptgs_2.setText(sdf.format(c.getTime()));

//                            _tgl_kunjungan_ptgs_2.setText( year + "-" + (monthOfYear + 1)  + "-" +dayOfMonth );
                            try {
                                CalculateKarcis();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        },mYear , mMonth,mDay );
                datePickerDialog.show();

            }
        });


        btn_detail_ku.setOnClickListener(v -> {
            Log.i("","mode_pembayaran x"+ mode_pembayaran[0]);

            String txt_kdlokwis = _txt_kdlokwis.getText().toString().trim();
            String txt_nmlokWis = _txt_nmlokwis.getText().toString().trim();
            String txt_kdlokPintu = _txt_kdlokPintu.getText().toString().trim();
            String txt_nmlokPintu = _txt_nmlokPintu.getText().toString().trim();
            String tgl_kujungan_val = _txt_tgl_kunjungan_order.getText().toString().trim();
            String nama_pengunjung = _nama_pengunjung_ptgs.getText().toString().trim();
            String hp_pengunjung = _hp_pengunjung_ptgs.getText().toString().trim();
            String email_pengunjung = _email_pengunjung.getText().toString().trim();
            String tgl_kunjungan_ptgs_2 = _tgl_kunjungan_ptgs_2.getText().toString().trim();

            String txt_krcs_wisnu_ptgs = _jml_krcs_wisnu_ptgs.getText().toString();
            String txt_krcs_wisman_ptgs = _jml_krcs_wisman_ptgs.getText().toString();
            String txt_ttl_ptgs = _ttl_ptgs.getText().toString().trim();
            String txt_jml_krcs_wisman_tmbhn_ptgs = _jml_krcs_wisman_tmbhn_ptgs.getText().toString().trim();
            String txt_ttl_tmbhn_ptgs = _ttl_tmbhn_ptgs.getText().toString().trim();
            String txt_grand_ttl_ptgs = _grand_ttl_ptgs.getText().toString().trim();

            String	  txt_kode_ksda_ku = _kode_ksda_ku.getText().toString().trim();
            String    txt_kode_lokasi_ku = _kode_lokasi_ku.getText().toString().trim();
            String    txt_kode_karcis_ku = _kode_karcis_ku.getText().toString().trim();
            String    txt_nama_karcis_ku = _nama_karcis_ku.getText().toString().trim();
            String    txt_kode_libur_ku = _kode_libur_ku.getText().toString().trim();
            String    txt_harga_karcis_wisata_wisnu_ku = _harga_karcis_wisata_wisnu_ku.getText().toString().trim();
            String    txt_harga_karcis_wisata_wisman_ku = _harga_karcis_wisata_wisman_ku.getText().toString().trim();
            String    txt_harga_karcis_asuransi_wisnu_ku = _harga_karcis_asuransi_wisnu_ku.getText().toString().trim();
            String    txt_harga_karcis_asuransi_wisman_ku = _harga_karcis_asuransi_wisman_ku.getText().toString().trim();
            String    txt_id_ku = _id_ku.getText().toString().trim();;
            String    txt_id_kt = _id_kt.getText().toString().trim();;

            String txt_harga_karcis_wisata_kt = _harga_karcis_wisata_kt.getText().toString().trim();
            String txt_harga_karcis_asuransi_kt = _harga_karcis_asuransi_kt.getText().toString().trim();

            Intent i = new Intent(v.getContext(), PetugasAdapterKuActivity.class);
            i.putExtra("result_dt_kd_lokwis", txt_kdlokwis);
            i.putExtra("result_dt_nm_lokwis",txt_nmlokWis );
            i.putExtra("result_dt_kd_lokpintu", txt_kdlokPintu);
            i.putExtra("result_dt_nm_lokpintu",txt_nmlokPintu );
            i.putExtra("result_dt_tgl_kunj_lokwis",tgl_kujungan_val);
            i.putExtra("result_dt_tgl_kunj_2_lokwis",tgl_kunjungan_ptgs_2);


            i.putExtra("nama_pengunjung",nama_pengunjung);
            i.putExtra("hp_pengunjung",hp_pengunjung);
            i.putExtra("email_pengunjung",email_pengunjung);

            i.putExtra("txt_kode_ksda_ku", txt_kode_ksda_ku);
            i.putExtra("txt_kode_lokasi_ku", txt_kode_lokasi_ku);
            i.putExtra("txt_kode_karcis_ku", txt_kode_karcis_ku);
            i.putExtra("txt_nama_karcis_ku", txt_nama_karcis_ku);
            i.putExtra("txt_kode_libur_ku", txt_kode_libur_ku);
            i.putExtra("txt_harga_karcis_wisata_wisnu_ku", txt_harga_karcis_wisata_wisnu_ku);
            i.putExtra("txt_harga_karcis_wisata_wisman_ku", txt_harga_karcis_wisata_wisman_ku);
            i.putExtra("txt_harga_karcis_asuransi_wisman_ku", txt_harga_karcis_asuransi_wisnu_ku);
            i.putExtra("txt_harga_karcis_asuransi_wisnu_ku", txt_harga_karcis_asuransi_wisman_ku);
            i.putExtra("txt_id_ku", txt_id_ku);
            i.putExtra("txt_id_kt", txt_id_kt);

            i.putExtra("txt_krcs_wisnu_ptgs", txt_krcs_wisnu_ptgs);
            i.putExtra("txt_krcs_wisman_ptgs", txt_krcs_wisman_ptgs);
            i.putExtra("txt_ttl_ptgs", txt_ttl_ptgs);
            i.putExtra("txt_jml_krcs_wisman_tmbhn_ptgs", txt_jml_krcs_wisman_tmbhn_ptgs);
            i.putExtra("txt_ttl_tmbhn_ptgs", txt_ttl_tmbhn_ptgs);
            i.putExtra("txt_grand_ttl_ptgs", txt_grand_ttl_ptgs);

            i.putExtra("txt_harga_karcis_wisata_kt", txt_harga_karcis_wisata_kt);
            i.putExtra("txt_harga_karcis_asuransi_kt", txt_harga_karcis_asuransi_kt);
            i.putExtra("mode_pembayaran", mode_pembayaran[0]);
            i.putExtra("nama_pembayaran", nama_pembayaran[0]);

            startActivity(i);
        });


        btn_detail_kt.setOnClickListener(v -> {

            String txt_kdlokwis = _txt_kdlokwis.getText().toString().trim();
            String txt_nmlokWis = _txt_nmlokwis.getText().toString().trim();
            String txt_kdlokPintu = _txt_kdlokPintu.getText().toString().trim();
            String txt_nmlokPintu = _txt_nmlokPintu.getText().toString().trim();
            String tgl_kujungan_val = _txt_tgl_kunjungan_order.getText().toString().trim();
            String nama_pengunjung = _nama_pengunjung_ptgs.getText().toString().trim();
            String hp_pengunjung = _hp_pengunjung_ptgs.getText().toString().trim();
            String email_pengunjung = _email_pengunjung.getText().toString().trim();
            String tgl_kunjungan_ptgs_2 = _tgl_kunjungan_ptgs_2.getText().toString().trim();

            String txt_krcs_wisnu_ptgs = _jml_krcs_wisnu_ptgs.getText().toString();
            String txt_krcs_wisman_ptgs = _jml_krcs_wisman_ptgs.getText().toString();
            String txt_ttl_ptgs = _ttl_ptgs.getText().toString().trim();
            String txt_jml_krcs_wisman_tmbhn_ptgs = _jml_krcs_wisman_tmbhn_ptgs.getText().toString().trim();
            String txt_ttl_tmbhn_ptgs = _ttl_tmbhn_ptgs.getText().toString().trim();
            String txt_grand_ttl_ptgs = _grand_ttl_ptgs.getText().toString().trim();

            String	  txt_kode_ksda_ku = _kode_ksda_ku.getText().toString().trim();
            String    txt_kode_lokasi_ku = _kode_lokasi_ku.getText().toString().trim();
            String    txt_kode_karcis_ku = _kode_karcis_ku.getText().toString().trim();
            String    txt_nama_karcis_ku = _nama_karcis_ku.getText().toString().trim();
            String    txt_kode_libur_ku = _kode_libur_ku.getText().toString().trim();
            String    txt_harga_karcis_wisata_wisnu_ku = _harga_karcis_wisata_wisnu_ku.getText().toString().trim();
            String    txt_harga_karcis_wisata_wisman_ku = _harga_karcis_wisata_wisman_ku.getText().toString().trim();
            String    txt_harga_karcis_asuransi_wisnu_ku = _harga_karcis_asuransi_wisnu_ku.getText().toString().trim();
            String    txt_harga_karcis_asuransi_wisman_ku = _harga_karcis_asuransi_wisman_ku.getText().toString().trim();
            String    txt_id_ku = _id_ku.getText().toString().trim();;
            String    txt_id_kt = _id_kt.getText().toString().trim();;

            String txt_kode_ksda_kt = _kode_ksda_kt.getText().toString().trim();
            String txt_kode_lokasi_kt = _kode_lokasi_kt.getText().toString().trim();
            String txt_kode_karcis_kt = _kode_karcis_kt.getText().toString().trim();
            String txt_nama_karcis_kt = _nama_karcis_kt.getText().toString().trim();
            String txt_kode_libur_kt = _kode_libur_kt.getText().toString().trim();
            String txt_harga_karcis_wisata_kt = _harga_karcis_wisata_kt.getText().toString().trim();
            String txt_harga_karcis_asuransi_kt = _harga_karcis_asuransi_kt.getText().toString().trim();


            Log.i("","result_dt_kd_lokpintu kt"+txt_kdlokPintu);
            Log.i("","txt_harga_karcis_wisata_kt kt"+txt_harga_karcis_wisata_kt);


            Intent i = new Intent(v.getContext(), PetugasAdapterKtActivity.class);

            i.putExtra("txt_kode_ksda_kt", txt_kode_ksda_kt);
            i.putExtra("txt_kode_lokasi_kt", txt_kode_lokasi_kt);
            i.putExtra("txt_kode_karcis_kt", txt_kode_karcis_kt);
            i.putExtra("txt_nama_karcis_kt", txt_nama_karcis_kt);
            i.putExtra("txt_kode_libur_kt", txt_kode_libur_kt);
            i.putExtra("txt_harga_karcis_wisata_kt", txt_harga_karcis_wisata_kt);
            i.putExtra("txt_harga_karcis_asuransi_kt", txt_harga_karcis_asuransi_kt);
            i.putExtra("txt_id_kt", txt_id_kt);
            i.putExtra("txt_id_ku", txt_id_ku);
            i.putExtra("tgl_kunjungan_ptgs_2",tgl_kunjungan_ptgs_2);

            i.putExtra("result_dt_kd_lokwis", txt_kdlokwis);
            i.putExtra("result_dt_nm_lokwis",txt_nmlokWis );
            i.putExtra("result_dt_kd_lokpintu", txt_kdlokPintu);
            i.putExtra("result_dt_nm_lokpintu",txt_nmlokPintu );
            i.putExtra("result_dt_tgl_kunj_lokwis",tgl_kujungan_val);

            i.putExtra("nama_pengunjung",nama_pengunjung);
            i.putExtra("hp_pengunjung",hp_pengunjung);
            i.putExtra("email_pengunjung",email_pengunjung);

            i.putExtra("txt_kode_ksda_ku", txt_kode_ksda_ku);
            i.putExtra("txt_kode_lokasi_ku", txt_kode_lokasi_ku);
            i.putExtra("txt_kode_karcis_ku", txt_kode_karcis_ku);
            i.putExtra("txt_nama_karcis_ku", txt_nama_karcis_ku);
            i.putExtra("txt_kode_libur_ku", txt_kode_libur_ku);
            i.putExtra("txt_harga_karcis_wisata_wisnu_ku", txt_harga_karcis_wisata_wisnu_ku);
            i.putExtra("txt_harga_karcis_wisata_wisman_ku", txt_harga_karcis_wisata_wisman_ku);
            i.putExtra("txt_harga_karcis_asuransi_wisnu_ku", txt_harga_karcis_asuransi_wisnu_ku);
            i.putExtra("txt_harga_karcis_asuransi_wisman_ku", txt_harga_karcis_asuransi_wisman_ku);
            i.putExtra("txt_id_ku", txt_id_ku);

            i.putExtra("txt_krcs_wisnu_ptgs", txt_krcs_wisnu_ptgs);
            i.putExtra("txt_krcs_wisman_ptgs", txt_krcs_wisman_ptgs);
            i.putExtra("txt_ttl_ptgs", txt_ttl_ptgs);
            i.putExtra("txt_jml_krcs_wisman_tmbhn_ptgs", txt_jml_krcs_wisman_tmbhn_ptgs);
            i.putExtra("txt_ttl_tmbhn_ptgs", txt_ttl_tmbhn_ptgs);
            i.putExtra("txt_grand_ttl_ptgs", txt_grand_ttl_ptgs);
            i.putExtra("mode_pembayaran", mode_pembayaran[0]);
            i.putExtra("nama_pembayaran", nama_pembayaran[0]);

            startActivity(i);
        });


        _btn_order_ptgs.setOnClickListener(v -> {

            RadioGroup rg_cara_bayarn1 = (RadioGroup)findViewById(R.id.rg_cara_bayar);
            final  String hp_pengunjung = _hp_pengunjung_ptgs.getText().toString().trim();
            final String email_pengunjung = _email_pengunjung.getText().toString().trim();
            final String nama_pengunjung = _nama_pengunjung_ptgs.getText().toString().trim();
            final String _nama_lokasi = _txt_nmlokwis.getText().toString().trim();
            final String _nmlokPintu = _txt_nmlokPintu.getText().toString().trim();
            final String _jml_krcs_tmbhn = _jml_krcs_wisman_tmbhn_ptgs.getText().toString().trim();
            String tgl_kunjungan_ptgs_2 = _tgl_kunjungan_ptgs_2.getText().toString().trim();
            String txt_grand_ttl_ptgs = _grand_ttl_ptgs.getText().toString().trim();


             if(TextUtils.isEmpty(hp_pengunjung) ) {
                _hp_pengunjung_ptgs.setError("Nomor Hp Tidak Boleh Kosong!");
            }
            else if(TextUtils.isEmpty(email_pengunjung) ) {
                _email_pengunjung.setError("Email Tidak Boleh Kosong!");
            }
            else if(TextUtils.isEmpty(nama_pengunjung) ) {
                _nama_pengunjung_ptgs.setError("Nama Tidak Boleh Kosong!");
            }
            else if( TextUtils.isEmpty(tgl_kunjungan_ptgs_2) ) {
                 _tgl_kunjungan_ptgs_2.setError("Rentang Tgl Tidak Boleh Kosong!");
                 _tgl_kunjungan_ptgs_2.requestFocus();
             }
            else {
                 try {
                     if (mode_pembayaran[0].equals("1")) {
                         AlertDialog.Builder builder = new AlertDialog.Builder(PesanKarcisPetugasActivity.this);
                         builder.setMessage("Print Struk")
                                 .setCancelable(false)
                                 .setPositiveButton("Ya", (dialog, id) -> {
//                                     findBluetooth();
                                     Intent i = new Intent(getApplicationContext(), DashboardPrintActivity.class);

                                     String flag_pemesan = null;
                                     final String key_name =  _nama_pengunjung_ptgs.getText().toString().trim().trim();
                                     final String key_alamat_email =  sessionManager.getUserDetail().get(SessionManager.key_alamat_email);
                                     final String key_email =  sessionManager.getUserDetail().get(SessionManager.key_email);
                                     final String key_email_pengunjung =  _email_pengunjung.getText().toString().trim().trim();
                                     final String key_hp =  _hp_pengunjung_ptgs.getText().toString().trim().trim();
                                     final String key_tgl_penjualan =  Help.getDateTime().trim();
                                     final String tgl_kunjungan =  _tgl_kunjungan_ptgs.getText().toString().trim();
                                     final String key_kode_lokasi =   _kode_lokasi_ku.getText().toString().trim().trim();
                                     final String key_id_utama =   _id_ku.getText().toString().trim().trim();
                                     final String key_id_tmbhn =   _id_kt.getText().toString().trim().trim();
                                     String jml_wisnu = _jml_krcs_wisnu_ptgs.getText().toString().trim().trim();
                                     String jml_wisman = _jml_krcs_wisman_ptgs.getText().toString().trim();
                                     String jml_tmhn = _jml_krcs_wisman_tmbhn_ptgs.getText().toString().trim();
                                     final String key_kode_lok_new = (String) sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi);
                                     if ( key_kode_lok_new.isEmpty() ){  flag_pemesan = "1"; } else { flag_pemesan= "2";  }
                                     final String tgl_kunjungan_sd =  _tgl_kunjungan_ptgs_2.getText().toString();

                                     if(jml_wisnu.equals("")){  jml_wisnu = "0";  }
                                     if(jml_wisman.equals("")){  jml_wisman = "0";  }
                                     if(jml_tmhn.equals("")){  jml_tmhn = "0";  }


                                     i.putExtra("nama_lokasi",_nama_lokasi);
                                     i.putExtra("nama_pintu",_nmlokPintu);
                                     i.putExtra("registration_by", key_email);
                                     i.putExtra("flag_pemesan", flag_pemesan);
                                     i.putExtra("nama", key_name);
                                     i.putExtra("sellular_no",key_hp);
                                     i.putExtra("alamat_email",key_email);
                                     i.putExtra("tgl_penjualan",key_tgl_penjualan);
                                     i.putExtra("kode_lokasi",key_kode_lokasi);
                                     i.putExtra("id_karcis_utama",key_id_utama);
                                     i.putExtra("id_karcis_tambahan",key_id_tmbhn);
                                     i.putExtra("jumlah_wisnu",jml_wisnu);
                                     i.putExtra("jumlah_wisman",jml_wisman);
                                     i.putExtra("jumlah_tambahan",jml_tmhn);
                                     i.putExtra("mode_pembayaran", mode_pembayaran[0]);
                                     i.putExtra("no_hp_pengunjung", hp_pengunjung);
                                     i.putExtra("email_pengunjung", key_email_pengunjung);
                                     i.putExtra("nama_pengunjung", nama_pengunjung);
                                     i.putExtra("tgl_kunjungan",tgl_kunjungan);
                                     i.putExtra("tgl_kunjungan_sd",tgl_kunjungan_sd);
                                     i.putExtra("grand_ttl_ptgs", txt_grand_ttl_ptgs);
                                     i.putExtra("nama_pembayaran", nama_pembayaran[0]);
                                     try {
                                         i.putExtra("jumlah_hari", String.valueOf(get_selisih_day()) );
                                     } catch (ParseException e) {
                                         e.printStackTrace();
                                     }
                                     startActivity(i);

                                 })
                                 .setNegativeButton("Tidak", (dialog, id) -> {
                                     try {
                                         inputKarcisPetugas("new_input_petugas", mode_pembayaran[0], _nama_lokasi, _jml_krcs_tmbhn, get_selisih_day(), "0", nama_pembayaran[0] );
                                     } catch (ParseException e) {
                                         e.printStackTrace();
                                     }
                                 });
                         AlertDialog alert = builder.create();
                         alert.show();
                     } else {
                         inputKarcisPetugas("new_input_petugas", mode_pembayaran[0], _nama_lokasi, _jml_krcs_tmbhn, get_selisih_day(), "0" , nama_pembayaran[0]);
                     }

                 } catch (ParseException e) {
                     e.printStackTrace();
                 }
             }

        });

        _jml_krcs_wisnu_ptgs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    CalculateKarcis();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });


        _jml_krcs_wisman_ptgs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    CalculateKarcis();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        _jml_krcs_wisman_tmbhn_ptgs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                CalculateKarcis();
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    CalculateKarcis();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });







        /* this for adapter karcis utama */
        String result_dt_jml_karcis_wisnu = getIntent().getStringExtra("result_jml_karcis_wisnu");
        String result_dt_jml_karcis_wisman = getIntent().getStringExtra("result_jml_karcis_wisman");
        String result_dt_ttl_wisnu_wisman = getIntent().getStringExtra("result_ttl_wisnu_wisman");
        String result_dt_jml_karcis_tmbhn = getIntent().getStringExtra("result_jml_karcis_tmbhn");
        String result_dt_ttl_karcis_tmbhn = getIntent().getStringExtra("result_ttl_karcis_tmbhn");
        String result_dt_grand_ttl = getIntent().getStringExtra("result_grand_ttl");

        String result_dt_nama_pengunjung = getIntent().getStringExtra("result_dt_nama_pengunjung");
        String result_dt_hp_pengunjung = getIntent().getStringExtra("result_dt_hp_pengunjung");
        String result_dt_email_pengunjung = getIntent().getStringExtra("result_dt_email_pengunjung");
        boolean result_dt_flag_ku = getIntent().getBooleanExtra("result_dt_flag_ku",false);
        String result_dt_tgl_kunj_2 = getIntent().getStringExtra("result_dt_tgl_kunj_2");

        String result_dt_id_ku = getIntent().getStringExtra("result_dt_id_ku");
        String result_dt_kodeKarcis_ku = getIntent().getStringExtra("result_dt_kodeKarcis");
        String result_dt_namaKarcis_ku = getIntent().getStringExtra("result_dt_namaKarcis");
        String result_dt_harga_karcis_wisata_wisnu_ku = getIntent().getStringExtra("result_dt_harga_karcis_wisata_wisnu");
        String result_dt_harga_karcis_wisata_wisman_ku = getIntent().getStringExtra("result_dt_harga_karcis_wisata_wisman");
        String result_dt_harga_karcis_asuransi_wisnu_ku = getIntent().getStringExtra("result_dt_harga_karcis_asuransi_wisnu");
        String result_dt_harga_karcis_asuransi_wisman_ku = getIntent().getStringExtra("result_dt_harga_karcis_asuransi_wisman");
        String result_dt_harga_karcis_wisata_tmbhn_ku = getIntent().getStringExtra("result_dt_harga_karcis_wisata_tmbhn");

        String result_dt_kdlokWis_ku = getIntent().getStringExtra("result_dt_kdlokWis");
        String result_dt_nmlokWis_ku = getIntent().getStringExtra("result_dt_nmlokWis");
        String result_dt_kdlokPintu_ku = getIntent().getStringExtra("result_dt_kdlokPintu");
        String result_dt_nmlokPintu_ku = getIntent().getStringExtra("result_dt_nmlokPintu");
        String result_dt_tgl_kunj_ku = getIntent().getStringExtra("result_dt_tgl_kunj");
        String result_dt_url_img_lokWis_ku = getIntent().getStringExtra("result_dt_url_img_lokWisOld");
        boolean result_dt_flag_kt = getIntent().getBooleanExtra("result_dt_flag_kt",false);
        String result_dt_mode_pembayaran = getIntent().getStringExtra("result_dt_mode_pembayaran");
        String result_dt_tgl_kunjungan_ptgs_2_kt_ptgs = getIntent().getStringExtra("result_dt_tgl_kunjungan_ptgs_2_kt_ptgs");


        /*  this for adapter karcis tambahan  */
        String result_dt_id_kt = getIntent().getStringExtra("result_dt_id_kt");
        String result_dt_kodeKarcis_kt = getIntent().getStringExtra("result_dt_kodeKarcis_kt");
        String result_dt_namaKarcis_kt = getIntent().getStringExtra("result_dt_namaKarcis_kt");
        String result_dt_harga_karcis_wisata_kt = getIntent().getStringExtra("result_dt_harga_karcis_wisata_kt");
        String result_dt_harga_karcis_asuransi_kt = getIntent().getStringExtra("result_dt_harga_karcis_asuransi_kt");
        String result_dt_kdlokPintu_kt = getIntent().getStringExtra("result_dt_kdlokPintu_kt");



        Log.i("", "result_dt_harga_karcis_wisata_tmbhn_ku  " + result_dt_harga_karcis_wisata_tmbhn_ku );
        Log.i("", "result_dt_jml_karcis_wisnu  " + result_dt_jml_karcis_wisnu );
        Log.i("", "result_dt_jml_karcis_wisman  " + result_dt_jml_karcis_wisman );
        Log.i("", "result_dt_flag_ku  " + result_dt_flag_ku );
        Log.i("", "result_dt_id_ku  " + result_dt_id_ku );
        Log.i("", "result_dt_namaKarcis_ku  " + result_dt_namaKarcis_ku );


        /* this for load first time  */
        if(!result_dt_flag_ku && !result_dt_flag_kt) {

            Log.i("","masuk kesini 1"+result_dt_flag_ku);
            Log.i("","masuk kesini kl 1"+KL);

            apiLokwisPtgsFirst("petugas_daftar_lokasi_wisata",KL);
            getRbCaraBayar("list_bank","","1");

        }

        /* this after click linear karcis utama   */
        else if( result_dt_flag_ku && !result_dt_flag_kt ) {

            Log.i("","masuk kesini 2"+result_dt_flag_ku);

            _id_ku.setText(result_dt_id_ku);
            _nama_karcis_ku.setText(result_dt_namaKarcis_ku);
            _kode_lokasi_ku.setText(result_dt_kdlokPintu_ku);
            _kode_karcis_ku.setText(result_dt_kodeKarcis_ku);
            _harga_karcis_wisata_wisnu_ku.setText(result_dt_harga_karcis_wisata_wisnu_ku);
            _harga_karcis_wisata_wisman_ku.setText(result_dt_harga_karcis_wisata_wisman_ku);
            _harga_karcis_asuransi_wisnu_ku.setText(result_dt_harga_karcis_asuransi_wisnu_ku);
            _harga_karcis_asuransi_wisman_ku.setText(result_dt_harga_karcis_asuransi_wisman_ku);


            _jml_krcs_wisnu_ptgs.setText(result_dt_jml_karcis_wisnu);
            _jml_krcs_wisman_ptgs.setText(result_dt_jml_karcis_wisman);
            _ttl_ptgs.setText(result_dt_ttl_wisnu_wisman);
            _jml_krcs_wisman_tmbhn_ptgs.setText(result_dt_jml_karcis_tmbhn);
            _hp_pengunjung_ptgs.setText(result_dt_hp_pengunjung);
            _email_pengunjung.setText(result_dt_email_pengunjung);
            _nama_pengunjung_ptgs.setText(result_dt_nama_pengunjung);
            _ttl_tmbhn_ptgs.setText(result_dt_ttl_karcis_tmbhn);
            _grand_ttl_ptgs.setText(result_dt_grand_ttl);
            _tgl_kunjungan_ptgs_2.setText(result_dt_tgl_kunj_2);

            _id_kt.setText(result_dt_id_kt);

            findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
            apiLokwisPtgs("petugas_daftar_lokasi_wisata",
                    sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi),
                    result_dt_kdlokPintu_ku
            );
//            apiWisatawanUtamaFirst("daftar_karcis_wisatawan_utama",result_dt_kdlokPintu_ku);
            String _id_kt_par = result_dt_id_kt;
            apiWisatawanTambahanForKUKT("daftar_karcis_wisatawan_tambahan",
                    result_dt_kdlokPintu_ku,_id_kt_par,
                    result_dt_harga_karcis_wisata_wisnu_ku,
                    result_dt_harga_karcis_wisata_wisman_ku,
                    result_dt_harga_karcis_asuransi_wisnu_ku,
                    result_dt_harga_karcis_asuransi_wisman_ku,

                    result_dt_jml_karcis_wisnu,
                    result_dt_jml_karcis_wisman,
                    result_dt_jml_karcis_tmbhn
            );

            getRbCaraBayar("list_bank",result_dt_mode_pembayaran,"");
        }


        /* this after click linear karcis tambahan   */
        else if( result_dt_flag_kt && !result_dt_flag_ku ) {

            Log.i("","masuk kesini 3"+result_dt_flag_kt);

            _id_ku.setText(result_dt_id_ku);
            _id_kt.setText(result_dt_id_kt);
            _harga_karcis_wisata_kt.setText(result_dt_harga_karcis_wisata_kt);

            _jml_krcs_wisnu_ptgs.setText(result_dt_jml_karcis_wisnu);
            _jml_krcs_wisman_ptgs.setText(result_dt_jml_karcis_wisman);
            _ttl_ptgs.setText(result_dt_ttl_wisnu_wisman);
            _jml_krcs_wisman_tmbhn_ptgs.setText(result_dt_jml_karcis_tmbhn);
            _hp_pengunjung_ptgs.setText(result_dt_hp_pengunjung);
            _email_pengunjung.setText(result_dt_email_pengunjung);
            _nama_pengunjung_ptgs.setText(result_dt_nama_pengunjung);
            _ttl_tmbhn_ptgs.setText(result_dt_ttl_karcis_tmbhn);
            _grand_ttl_ptgs.setText(result_dt_grand_ttl);
            _tgl_kunjungan_ptgs_2.setText(result_dt_tgl_kunjungan_ptgs_2_kt_ptgs);

            Log.i("","result_dt_kdlokPintu_kt kt"+result_dt_kdlokPintu_kt);
            Log.i("","result_dt_id_ku kt"+result_dt_id_ku);
            Log.i("","result_dt_id_kt kt"+result_dt_id_kt);

            apiLokwisPtgs("petugas_daftar_lokasi_wisata",
                    sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi),
                    result_dt_kdlokPintu_kt
            );
            String _id_ku_par = result_dt_id_ku;
            apiWisatawanUtamaForKUKT("daftar_karcis_wisatawan_utama",
                    result_dt_kdlokPintu_kt,
                    _id_ku_par,
                    result_dt_jml_karcis_wisnu,
                    result_dt_jml_karcis_wisman,
                    result_dt_jml_karcis_tmbhn,
                    result_dt_harga_karcis_wisata_kt
                );
            String _id_kt_par = result_dt_id_kt;
            apiWisatawanTambahanForKUKT("daftar_karcis_wisatawan_tambahan",
                    result_dt_kdlokPintu_kt,
                    _id_kt_par,
                    result_dt_harga_karcis_wisata_wisnu_ku,
                    result_dt_harga_karcis_wisata_wisman_ku,
                    result_dt_harga_karcis_asuransi_wisnu_ku,
                    result_dt_harga_karcis_asuransi_wisman_ku,

                    result_dt_jml_karcis_wisnu,
                    result_dt_jml_karcis_wisman,
                    result_dt_jml_karcis_tmbhn
            );

            getRbCaraBayar("list_bank",result_dt_mode_pembayaran,"");

        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent dataIntent) {
        super.onActivityResult(requestCode, resultCode, dataIntent);

        Log.i("","requestCode= "+requestCode);
        Log.i("","resultCode= "+resultCode);
        Log.i("","dataIntent= "+dataIntent);

        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE:
                if (requestCode == Activity.RESULT_OK) {
                    Bundle mExtra = dataIntent.getExtras();
                    String mDeviceAddress = mExtra.getString("DeviceAddress");

                    bluetoothDevice = bluetoothAdapter.getRemoteDevice(mDeviceAddress);
                    mBluetoothConnectProgressDialog = ProgressDialog.show(this, "connecting...",
                            bluetoothDevice.getName() + " : " + bluetoothDevice.getAddress(), true, false);

                    Thread mBlutoothConnectThread = new Thread((Runnable) this);
                    mBlutoothConnectThread.start();
                }
                break;

            case REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK) {
//                    ListPairedDevices();
//                    Intent connectIntent = new Intent(MainActivity.this,
//                            DeviceListActivity.class);
//                    startActivityForResult(connectIntent, REQUEST_CONNECT_DEVICE);
//                } else {
//                    Toast.makeText(MainActivity.this, "Message", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void ListPairedDevices() {
        Set<BluetoothDevice> mPairedDevices = bluetoothAdapter
                .getBondedDevices();
        if (mPairedDevices.size() > 0) {
            for (BluetoothDevice mDevice : mPairedDevices) {
                Log.v(TAG, "PairedDevices: " + mDevice.getName() + "  "
                        + mDevice.getAddress());
            }
        }
    }

    public void run() {
        try {
            bluetoothSocket = bluetoothDevice
                    .createRfcommSocketToServiceRecord(applicationUUID);
            bluetoothAdapter.cancelDiscovery();
            bluetoothSocket.connect();

        } catch (IOException eConnectException) {
            Log.d(TAG, "CouldNotConnectToSocket", eConnectException);
            closeSocket(bluetoothSocket);
            return;
        }
    }

    private void closeSocket(BluetoothSocket nOpenSocket) {
        try {
            nOpenSocket.close();
            Log.d(TAG, "SocketClosed");
        } catch (IOException ex) {
            Log.d(TAG, "CouldNotCloseSocket");
        }
    }





    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void findBluetooth(){

        try {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            Log.i("","bluetoothAdapter= "+bluetoothAdapter);
            if( bluetoothAdapter == null  ){
                Log.i("","bluetooth disable");

            }
            if ( bluetoothAdapter.isEnabled()){
                Log.i("","bluetooth connect");
//                Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                startActivityForResult(enableBT,0);

                ListPairedDevices();

//                Intent connectIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                startActivityForResult(connectIntent,
//                        REQUEST_CONNECT_DEVICE);

                Set<BluetoothDevice> pairedDevice = bluetoothAdapter.getBondedDevices();
                Log.i("","pairedDevice.size()= "+pairedDevice.size());

                if( pairedDevice.size() > 0 ) {
                    for(BluetoothDevice pairedDev:pairedDevice){
                        Log.i("","pairedDev.getAddress "+pairedDev.getAddress());
                        Log.i("","pairedDev.getName "+pairedDev.getName());
                        Log.i("","pairedDev.getUuids "+ Arrays.toString(pairedDev.getUuids()));
                        Log.i("","pairedDev.getType "+pairedDev.getType());
                        Log.i("","pairedDev.getBondState "+pairedDev.getBondState());

                        if(pairedDev.getName().equals("58Printer") ) {

                            bluetoothAdapter.cancelDiscovery();
                            String mDeviceInfo =  pairedDev.getName() + "\n" + pairedDev.getAddress();
                            String mDeviceAddress = mDeviceInfo.substring(mDeviceInfo.length() - 17);

                            Log.v("", "mDeviceInfo xc " + mDeviceInfo);
                            Log.v("", "Device_Address xc " + mDeviceAddress);



                            print_struk();

//                            Bundle mBundle = new Bundle();
//                            mBundle.putString("DeviceAddress", mDeviceAddress);
//                            Intent mBackIntent = new Intent();
//                            mBackIntent.putExtras(mBundle);
//                            setResult(Activity.RESULT_OK, mBackIntent);

//                            startActivityForResult(connectIntent,
//                        REQUEST_CONNECT_DEVICE);
//                                Intent enableBT = new Intent(String.valueOf(Activity.RESULT_OK));
//                                startActivityForResult(enableBT,0);


                            bluetoothDevice = pairedDev;
                            Log.i("","bluetoothDevice "+bluetoothDevice );
                            break;
                        }


                    }
                }


            }




        }catch (Exception ignored){

        }
    }

    public void print_struk(){
        Thread t =new Thread(){
            @Override
            public void run() {

                try {
                    UUID applicationUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
                    bluetoothSocket = bluetoothDevice
                            .createRfcommSocketToServiceRecord(applicationUUID);
                    bluetoothAdapter.cancelDiscovery();
                    bluetoothSocket.connect();
                    OutputStream os = bluetoothSocket
                            .getOutputStream();
                    String BILL = "";

                    BILL = "                   XXXX MART    \n"
                            + "                   XX.AA.BB.CC.     \n " +
                            "                 NO 25 ABC ABCDE    \n" +
                            "                  XXXXX YYYYYY      \n" +
                            "                   MMM 590019091      \n";
                    BILL = BILL
                            + "-----------------------------------------------\n";


                    BILL = BILL + String.format("%1$-10s %2$10s %3$13s %4$10s", "Item", "Qty", "Rate", "Totel");
                    BILL = BILL + "\n";
                    BILL = BILL
                            + "-----------------------------------------------";
                    BILL = BILL + "\n " + String.format("%1$-10s %2$10s %3$11s %4$10s", "item-001", "5", "10", "50.00");
                    BILL = BILL + "\n " + String.format("%1$-10s %2$10s %3$11s %4$10s", "item-002", "10", "5", "50.00");
                    BILL = BILL + "\n " + String.format("%1$-10s %2$10s %3$11s %4$10s", "item-003", "20", "10", "200.00");
                    BILL = BILL + "\n " + String.format("%1$-10s %2$10s %3$11s %4$10s", "item-004", "50", "10", "500.00");

                    BILL = BILL
                            + "\n-----------------------------------------------";
                    BILL = BILL + "\n\n ";

                    BILL = BILL + "                   Total Qty:" + "      " + "85" + "\n";
                    BILL = BILL + "                   Total Value:" + "     " + "700.00" + "\n";

                    BILL = BILL
                            + "-----------------------------------------------\n";
                    BILL = BILL + "\n\n ";
                    os.write(BILL.getBytes());
                    //This is printer specific code you can comment ==== > Start

                    // Setting height
                    int gs = 29;
//                    os.write(intToByteArray(gs));
                    os.write(gs);
                    int h = 104;
//                    os.write(intToByteArray(h));
                    os.write(h);
                    int n = 162;
//                    os.write(intToByteArray(n));
                    os.write(n);

                    // Setting Width
                    int gs_width = 29;
//                    os.write(intToByteArray(gs_width));
                    os.write(gs_width);
                    int w = 119;
//                    os.write(intToByteArray(w));
                    os.write(w);
                    int n_width = 2;
//                    os.write(intToByteArray(n_width));
                    os.write(n_width);


                } catch (Exception e) {
                    Log.e("MainActivity", "Exe ", e);
                }

            }

        };
        t.start();
    }

    public static byte intToByteArray(int value) {
        byte[] b = ByteBuffer.allocate(4).putInt(value).array();

        for (int k = 0; k < b.length; k++) {
            System.out.println("Selva  [" + k + "] = " + "0x"
                    + Help.byteToHex(b[k]));
        }

        return b[3];
    }


    //    final BroadcastReceiver mReceiver = new BroadcastReceiver(){
        private final BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                Log.i("","device found");
            }
            else if(BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)){
                Log.i("","device is connected");
            }
            else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
                Log.i("","done searching");
            }
            else if(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED.equals(action)){
                Log.i("","device about disconnected");
            }
            else if(BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)){
                Log.i("","device disconnected");
            }

        }
    };


        @RequiresApi(api = Build.VERSION_CODES.O)
        public void getRbCaraBayar(String EP,String mode_pembayaran_par, String def_value){
//                rg_cara_bayar.setOrientation(LinearLayout.HORIZONTAL);

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
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> obj = new HashMap<String, String>();
                        final String key_email =  sessionManager.getUserDetail().get(SessionManager.key_email).trim();
                        obj.put("lokasi", "JELAJAH");
                        obj.put("petugas","1");
                        return obj;
                    }
                };
                int socketTimeout = 0;
                RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                stringRequest.setRetryPolicy(policy);
                requestQueue.add(stringRequest);



        }


    public void CalculateKarcis() throws ParseException {
        final double _jml_krcs_wisnu = Help.ParseDouble(((EditText) findViewById(R.id.jml_krcs_wisnu_ptgs)).getText().toString());
        final double _jml_krcs_wisman = Help.ParseDouble(((EditText) findViewById(R.id.jml_krcs_wisman_ptgs)).getText().toString());
        final double _jml_krcs_tmbhn = Help.ParseDouble(((EditText) findViewById(R.id.jml_krcs_wisman_tmbhn_ptgs)).getText().toString());

        final double hrg_krcs_wisnu = Help.ParseDouble(((TextView) findViewById(R.id.harga_karcis_wisata_wisnu_ku)).getText().toString());
        final double hrg_krcs_wisman = Help.ParseDouble(((TextView) findViewById(R.id.harga_karcis_wisata_wisman_ku)).getText().toString());
        final double hrg_krcs_tmbhn = Help.ParseDouble(((TextView) findViewById(R.id.harga_karcis_wisata_kt)).getText().toString());
        final double hrg_krcs_asrnsi_wisnu = Help.ParseDouble(((TextView) findViewById(R.id.harga_karcis_asuransi_wisnu_ku)).getText().toString());
        final double hrg_krcs_asrnsi_wisman = Help.ParseDouble(((TextView) findViewById(R.id.harga_karcis_asuransi_wisman_ku)).getText().toString());

//        final int  ttl_wisnu = (int) ((hrg_krcs_wisnu+hrg_krcs_asrnsi_wisnu)*_jml_krcs_wisnu);
//        final int   ttl_wisman =(int) ((hrg_krcs_wisman+hrg_krcs_asrnsi_wisman)*_jml_krcs_wisman);
//        final int ttl_wisnu_wisman = (ttl_wisnu+ttl_wisman);
//        final int ttl_tmbhn = (int) (hrg_krcs_tmbhn*_jml_krcs_tmbhn);
//        final int grand_ttl =(int) (ttl_wisnu_wisman+ttl_tmbhn);

        final int  ttl_wisnu ;
        final int   ttl_wisman;
        final int ttl_wisnu_wisman ;
        final int ttl_tmbhn ;
        final int grand_ttl ;


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

        _ttl_ptgs.setText(String.valueOf(ttl_wisnu_wisman));
        _ttl_tmbhn_ptgs.setText(String.valueOf(ttl_tmbhn));
        _grand_ttl_ptgs.setText(String.valueOf(grand_ttl));

//        _txt_day.setText(" x "+ selisih_day +" Hari.");

//        _ttl_ptgs.setText(String.valueOf(ttl_wisnu_wisman));
//        _ttl_tmbhn_ptgs.setText(String.valueOf(ttl_tmbhn));
//        _grand_ttl_ptgs.setText(String.valueOf(grand_ttl));

    }


    public long get_selisih_day() throws ParseException {
        final String TGL =  _txt_tgl_kunjungan_order.getText().toString().trim();
        final String TGL2 =  _tgl_kunjungan_ptgs_2.getText().toString().trim();
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

    private void quotaTwa(String EP,String KSDA){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://"+ Help.domain_api() +"/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(PesanKarcisPetugasActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {
                        Log.i("tag", "response quotaTwa =" + response );
                        try {
                            findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                            if( Help.isJSONValid(response) ){

                                JSONObject jsonObject = new JSONObject(response);
                                String data = jsonObject.getString("data");
                                JSONObject jsonObject1 = new JSONObject(data);
                                boolean _berhasil = jsonObject1.getBoolean("berhasil");

//                                if( _berhasil ) {
                                   String  _id = jsonObject1.getString("id");
                                    int _quota = jsonObject1.getInt("quota");
                                    String _keterangan = jsonObject1.getString("keterangan");
                                    Log.i("","_keterangan "+_keterangan);
//                                Log.i("","_berhasil "+_berhasil);
//                                Log.i("","qouta "+_quota);
//                                Log.i("","_keterangan "+_keterangan);
//                                    if( _quota > 0  ){

//                                        Toast.makeText(PesanKarcisPetugasActivity.this,"quota > 0.", Toast.LENGTH_LONG).show();

                                        TextView quotax = (TextView)findViewById(R.id.text_quota);
                                        ImageView imgv = (ImageView) findViewById(R.id.imgBell);
                                        quotax.setText(String.valueOf("Quota: "+_quota));
                                        quotax.setTextColor(Color.parseColor("#ffe458"));
//                                        imgv.setVisibility(View.VISIBLE);
//                                    }
                            }

                        } catch (JSONException e) {
                            Log.i("", "error =" + e.toString() );
                            e.printStackTrace();
                        }
                        requestQueue.stop();
                    }
                }, (Response.ErrorListener) error -> {
                    Log.i("", "response quotaTwa=" + error.toString());
                    error.printStackTrace();
                    requestQueue.stop();

                    findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                    AlertDialog.Builder builder = new AlertDialog.Builder(PesanKarcisPetugasActivity.this);
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

                Log.i("","KSDAx "+KSDA);
                Log.i("","Help.getDateTime()x "+Help.getDateTime());

                obj.put("kode_ksda", KSDA);
                obj.put("tgl_quota",Help.getDateTime());

//                obj.put("kode_ksda", "27");
//                obj.put("tgl_quota","2020-06-11");
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }



    private void apiLokwisPtgs(String EP,String KL,String kdlokPintu){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://"+ Help.domain_api() +"/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(PesanKarcisPetugasActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                (Response.Listener<String>) response -> {
                    Log.i("tag", "response apiLokwisPtgs =" + response );
                    try {

                        if( Help.isJSONValid(response) ){
                            JSONObject jsonObject = new JSONObject(response);

                            Log.i("","apiLokwisPtgs= "+response);

                            arrListWisata.clear();

                            findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                            if( jsonObject.getBoolean("success") ) {

                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i <jsonArray.length();i++ ) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                    String id =  jsonObject1.getString("id");
                                    final String kode_ksda =  jsonObject1.getString("kode_ksda");
                                    String nama =  jsonObject1.getString("nama");
                                    String alamat =  jsonObject1.getString("alamat");
                                    String kota =  jsonObject1.getString("kota");
                                    String email1 =  jsonObject1.getString("email1");
                                    String email2 =  jsonObject1.getString("email2");
                                    String email3 =  jsonObject1.getString("email3");
                                    String android_flag =  jsonObject1.getString("android_flag");
                                    String detail_flag =  jsonObject1.getString("detail_flag");
                                    String url_image =  jsonObject1.getString("url_image");
                                    String lokasi_pintu =  jsonObject1.getString("lokasi_pintu");
                                    String nama_pintu = jsonObject1.getString("nama_pintu");
                                    String total_pengunjung =  jsonObject1.getString("total_pengunjung");
                                    String total_berkunjung = jsonObject1.getString("total_berkunjung");
                                    String total_semua =  jsonObject1.getString("total_semua");
                                    String url_image_pintu =  jsonObject1.getString("url_image_pintu");

                                    _txt_kdlokwis.setText(kode_ksda);
                                    _txt_nmlokwis.setText(nama);
                                    _txt_kdlokPintu.setText(lokasi_pintu);
                                    _txt_nmlokPintu.setText(nama_pintu);

                                    sessionManager.createSessionEksp(kode_ksda,nama_pintu);

                                    Log.i("tag","kode_ksda= "+kode_ksda);
                                    Log.i("tag","nama= "+nama);
                                    Log.i("tag","lokasi_pintu= "+lokasi_pintu);
                                    Log.i("tag","nama_pintu= "+nama_pintu);


//                                        arrListWisata.add(new SpinnerListWisata(lokasi_pintu,nama_pintu,"","",""));

                                    quotaTwa("quota_per_twa",kode_ksda);


                                    ImageView img1 =(ImageView)findViewById(R.id.lokwisPicasso);
                                    Transformation transformation = new RoundedTransformationBuilder()
                                            .oval(false)
                                            .build();

                                    Picasso.with(getApplicationContext())
                                            .load(url_image)
                                            .fit()
                                            .transform(transformation)
                                            .placeholder(R.drawable.loading_animation)
                                            .into(img1);

                                    ImageView img2 =(ImageView)findViewById(R.id.lokPintuPicasso);

                                    Picasso.with(getApplicationContext())
                                            .load(url_image_pintu)
                                            .fit()
                                            .transform(transformation)
                                            .placeholder(R.drawable.loading_animation)
                                            .into(img2);

                                    Log.i("","kdlokPintu x"+kdlokPintu);

//                                        apiWisatawanUtamaFirst("daftar_karcis_wisatawan_utama",kdlokPintu);
//                                        apiWisatawanTambahanFirst("daftar_karcis_wisatawan_tambahan",lokasi_pintu);

                                }
                            }
                        }

                        String compareValue = sessionManager.getDataSetupPintu().get(SessionManager.key_index);
                        Log.i("","compareValue "+compareValue);
                    } catch (JSONException e) {
                        Log.i("", "error ===" + e.toString() );
                        e.printStackTrace();
                    }
                    requestQueue.stop();
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("triono", "response spinnerLokPintuPtgs=" + error.toString());
                error.printStackTrace();
                requestQueue.stop();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> obj = new HashMap<String, String>();
                final String key_email =  sessionManager.getUserDetail().get(SessionManager.key_email).trim();
                obj.put("alamat_email", key_email);
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    private void apiLokwisPtgsFirst(String EP,String KL){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://"+ Help.domain_api() +"/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(PesanKarcisPetugasActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("tag", "response apiLokwisPtgsFirst =" + response );
                        try {

                            if( Help.isJSONValid(response) ){
                                JSONObject jsonObject = new JSONObject(response);

                                Log.i("","apiLokwisPtgsFirst= "+response);

                                arrListWisata.clear();

                                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                                if( jsonObject.getBoolean("success") ) {

                                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                                    for (int i = 0; i <jsonArray.length();i++ ) {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                        String id =  jsonObject1.getString("id");
                                        final String kode_ksda =  jsonObject1.getString("kode_ksda");
                                        String nama =  jsonObject1.getString("nama");
                                        String alamat =  jsonObject1.getString("alamat");
                                        String kota =  jsonObject1.getString("kota");
                                        String email1 =  jsonObject1.getString("email1");
                                        String email2 =  jsonObject1.getString("email2");
                                        String email3 =  jsonObject1.getString("email3");
                                        String android_flag =  jsonObject1.getString("android_flag");
                                        String detail_flag =  jsonObject1.getString("detail_flag");
                                        String url_image =  jsonObject1.getString("url_image");
                                        String lokasi_pintu =  jsonObject1.getString("lokasi_pintu");
                                        String nama_pintu = jsonObject1.getString("nama_pintu");
                                        String total_pengunjung =  jsonObject1.getString("total_pengunjung");
                                        String total_berkunjung = jsonObject1.getString("total_berkunjung");
                                        String total_semua =  jsonObject1.getString("total_semua");
                                        String url_image_pintu =  jsonObject1.getString("url_image_pintu");

                                        _txt_kdlokwis.setText(kode_ksda);
                                        _txt_nmlokwis.setText(nama);
                                        _txt_kdlokPintu.setText(lokasi_pintu);
                                        _txt_nmlokPintu.setText(nama_pintu);




                                        sessionManager.createSessionEksp(kode_ksda,nama_pintu);

                                        Log.i("tag","kode_ksda= "+kode_ksda);
                                        Log.i("tag","nama= "+nama);
                                        Log.i("tag","lokasi_pintu first= "+lokasi_pintu);
                                        Log.i("tag","nama_pintu= "+nama_pintu);


//                                        arrListWisata.add(new SpinnerListWisata(lokasi_pintu,nama_pintu,"","",""));

                                        quotaTwa("quota_per_twa",kode_ksda);


                                        ImageView img1 =(ImageView)findViewById(R.id.lokwisPicasso);
                                        Transformation transformation = new RoundedTransformationBuilder()
                                                .oval(false)
                                                .build();

                                        Picasso.with(getApplicationContext())
                                                .load(url_image)
                                                .fit()
                                                .transform(transformation)
                                                .placeholder(R.drawable.loading_animation)
                                                .into(img1);

                                        ImageView img2 =(ImageView)findViewById(R.id.lokPintuPicasso);

                                        Picasso.with(getApplicationContext())
                                                .load(url_image_pintu)
                                                .fit()
                                                .transform(transformation)
                                                .placeholder(R.drawable.loading_animation)
                                                .into(img2);


                                        apiWisatawanUtamaFirst("daftar_karcis_wisatawan_utama",lokasi_pintu);
                                        apiWisatawanTambahanFirst("daftar_karcis_wisatawan_tambahan",lokasi_pintu,"");

                                    }
                                }
                            }

                            String compareValue = sessionManager.getDataSetupPintu().get(SessionManager.key_index);
                            Log.i("","compareValue "+compareValue);
                        } catch (JSONException e) {
                            Log.i("", "error ===" + e.toString() );
                            e.printStackTrace();
                        }
                        requestQueue.stop();
                    }
                }, (Response.ErrorListener) error -> {
                    Log.i("triono", "response spinnerLokPintuPtgs=" + error.toString());
                    error.printStackTrace();
                    requestQueue.stop();
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> obj = new HashMap<String, String>();
                final String key_email =  sessionManager.getUserDetail().get(SessionManager.key_email).trim();
                obj.put("alamat_email", key_email);
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    private void apiWisatawanUtamaFirst(String EP,String LP){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://"+ Help.domain_api() +"/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(PesanKarcisPetugasActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            if( Help.isJSONValid(response) ){
                                JSONObject jsonObject = new JSONObject(response.toString());
                                Log.i("tag","success= " + jsonObject.getBoolean("success") );

                                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                                arrListUtamaPtgs.clear();
                                if( jsonObject.getBoolean("success") ) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                                    Log.i("tag"," dt jsonArray: "+jsonArray.toString());

//                                    for (int i = 0; i <jsonArray.length();i++ ) {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                                        String id_ku = jsonObject1.getString("id");
                                        String kode_ksda_ku = jsonObject1.getString("kode_ksda");
                                        String kode_lokasi_ku = jsonObject1.getString("kode_lokasi");
                                        String kode_karcis_ku = jsonObject1.getString("kode_karcis");
                                        String nama_karcis_ku = jsonObject1.getString("nama_karcis");
                                        String kode_libur_ku = jsonObject1.getString("kode_libur");
                                        String harga_karcis_wisata_wisnu_ku = jsonObject1.getString("harga_karcis_wisata_wisnu");
                                        String harga_karcis_wisata_wisman_ku = jsonObject1.getString("harga_karcis_wisata_wisman");
                                        String harga_karcis_asuransi_wisnu_ku = jsonObject1.getString("harga_karcis_asuransi_wisnu");
                                        String harga_karcis_asuransi_wisman_ku = jsonObject1.getString("harga_karcis_asuransi_wisman");
                                        String url_image_ku = jsonObject1.getString("url_image");



                                        _kode_ksda_ku.setText(kode_ksda_ku);
                                        _kode_lokasi_ku.setText(kode_lokasi_ku);
                                        _kode_karcis_ku.setText(kode_karcis_ku);

                                        _nama_karcis_ku.setText(nama_karcis_ku);
                                        _kode_libur_ku.setText(kode_libur_ku);
                                        _harga_karcis_wisata_wisnu_ku.setText(harga_karcis_wisata_wisnu_ku);
                                        _harga_karcis_wisata_wisman_ku.setText(harga_karcis_wisata_wisman_ku);
                                        _harga_karcis_asuransi_wisnu_ku.setText(harga_karcis_asuransi_wisnu_ku);
                                        _harga_karcis_asuransi_wisman_ku.setText(harga_karcis_asuransi_wisman_ku);
                                        _id_ku.setText(id_ku);


                                        Log.i("tag"," wisnu_ku= "+ harga_karcis_wisata_wisnu_ku);
                                        Log.i("tag"," wisman_ku= "+ harga_karcis_wisata_wisman_ku);
                                        Log.i("tag"," wisman_ku= "+ harga_karcis_asuransi_wisnu_ku);
                                        Log.i("tag"," wisman_ku= "+ harga_karcis_asuransi_wisman_ku);


                                }
                            }

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
                obj.put("lokasi", LP.trim() );
                obj.put("tgl_kunjungan",Help.getDateTime().trim());
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    private void apiWisatawanTambahanFirst(String EP, String LP,String _id_kt_par ){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://"+ Help.domain_api() +"/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(PesanKarcisPetugasActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            if( Help.isJSONValid(response) ){
                                JSONObject jsonObject = new JSONObject(response.toString());
                                Log.i("tag","success= " + jsonObject.getBoolean("success") );
                                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                                if( jsonObject.getBoolean("success") ) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("data");


                                    arrKarcisTambahan.clear();
//                                    for (int i = 0; i <jsonArray.length();i++ ) {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                                        String _kode_ksda = jsonObject1.getString("kode_ksda");
                                        String _kode_lokasi = jsonObject1.getString("kode_lokasi");
                                        String _kode_karcis = jsonObject1.getString("kode_karcis");
                                        String _nama_karcis = jsonObject1.getString("nama_karcis");
                                        String _kode_libur = jsonObject1.getString("kode_libur");
                                        String _harga_karcis_wisata = jsonObject1.getString("harga_karcis_wisata");
                                        String _harga_karcis_asuransi = jsonObject1.getString("harga_karcis_asuransi");
                                        String _id = jsonObject1.getString("id");


                                            _id_kt.setText(_id);
                                            _kode_ksda_kt.setText(_kode_ksda);
                                            _kode_lokasi_kt.setText(_kode_lokasi);
                                            _kode_karcis_kt.setText(_kode_karcis);
                                            _nama_karcis_kt.setText(_nama_karcis);
                                            _kode_libur_kt.setText(_kode_libur);
                                            _harga_karcis_wisata_kt.setText(_harga_karcis_wisata);
                                            _harga_karcis_asuransi_kt.setText(_harga_karcis_asuransi);


                                        Log.i("","tambahan _kode_ksda "+_kode_ksda);
                                        Log.i("","tambahan _kode_lokasi "+_kode_lokasi);
                                        Log.i("","tambahan _kode_karcis "+_kode_karcis);
                                        Log.i("","tambahan _nama_karcis "+_nama_karcis);
                                        Log.i("","tambahan _kode_libur "+_kode_libur);
                                        Log.i("","tambahan _harga_karcis_wisata "+_harga_karcis_wisata);
                                        Log.i("","tambahan _harga_karcis_asuransi "+_harga_karcis_asuransi);

//                                    }

                                }
                            }

                        } catch (JSONException e) {
                            Log.i("triono", "error ===" + e.toString() );
                            e.printStackTrace();
                        }
                        requestQueue.stop();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("tag", "response spinnerWisatawanTambahan=" + error.toString());
                error.printStackTrace();
                requestQueue.stop();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> obj = new HashMap<String, String>();

                Log.i("tag", "LP spinnerWisatawanTambahan =" + LP );
                Log.i("tag", "getDateTime spinnerWisatawanTambahan =" + Help.getDateTime() );

                obj.put("lokasi", LP.trim() );
                obj.put("tgl_kunjungan",Help.getDateTime().trim());
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }


    private void apiWisatawanUtamaForKUKT(String EP,
                                          String LP,
                                          String _ID_KU,
                                          String _jml_karcis_wisnu,
                                          String _jml_karcis_wisman,
                                          String _jml_karcis_tmbhn,
                                          String _hrg_karcis_tmbhn
    ){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://"+ Help.domain_api() +"/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(PesanKarcisPetugasActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            if( Help.isJSONValid(response) ){
                                JSONObject jsonObject = new JSONObject(response.toString());
                                Log.i("tag","success= " + jsonObject.getBoolean("success") );

                                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                                arrListUtamaPtgs.clear();
                                if( jsonObject.getBoolean("success") ) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("data");



                                    for (int i = 0; i <jsonArray.length();i++ ) {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                        String id_ku = jsonObject1.getString("id");
                                        String kode_ksda_ku = jsonObject1.getString("kode_ksda");
                                        String kode_lokasi_ku = jsonObject1.getString("kode_lokasi");
                                        String kode_karcis_ku = jsonObject1.getString("kode_karcis");
                                        String nama_karcis_ku = jsonObject1.getString("nama_karcis");
                                        String kode_libur_ku = jsonObject1.getString("kode_libur");
                                        String harga_karcis_wisata_wisnu_ku = jsonObject1.getString("harga_karcis_wisata_wisnu");
                                        String harga_karcis_wisata_wisman_ku = jsonObject1.getString("harga_karcis_wisata_wisman");
                                        String harga_karcis_asuransi_wisnu_ku = jsonObject1.getString("harga_karcis_asuransi_wisnu");
                                        String harga_karcis_asuransi_wisman_ku = jsonObject1.getString("harga_karcis_asuransi_wisman");
                                        String url_image_ku = jsonObject1.getString("url_image");

                                        Log.i("tag"," dt _ID_KU: "+ _ID_KU);
                                        Log.i("tag"," dt ID_KU: "+ id_ku);

                                        if (_ID_KU.equals(id_ku)) {
                                            _kode_ksda_ku.setText(kode_ksda_ku);
                                            _kode_lokasi_ku.setText(kode_lokasi_ku);
                                            _kode_karcis_ku.setText(kode_karcis_ku);
                                            _nama_karcis_ku.setText(nama_karcis_ku);
                                            _kode_libur_ku.setText(kode_libur_ku);
                                            _harga_karcis_wisata_wisnu_ku.setText(harga_karcis_wisata_wisnu_ku);
                                            _harga_karcis_wisata_wisman_ku.setText(harga_karcis_wisata_wisman_ku);
                                            _harga_karcis_asuransi_wisnu_ku.setText(harga_karcis_asuransi_wisnu_ku);
                                            _harga_karcis_asuransi_wisman_ku.setText(harga_karcis_asuransi_wisman_ku);
                                            _id_ku.setText(id_ku);


                                            final double _jml_krcs_wisnu = Help.ParseDouble( _jml_karcis_wisnu );
                                            final double _jml_krcs_wisman = Help.ParseDouble( _jml_karcis_wisman );
                                            final double _jml_krcs_tmbhn = Help.ParseDouble( _jml_karcis_tmbhn );


                                            final double hrg_krcs_wisnu = Help.ParseDouble(harga_karcis_wisata_wisnu_ku);
                                            final double hrg_krcs_wisman = Help.ParseDouble(harga_karcis_wisata_wisman_ku);
                                            final double hrg_krcs_tmbhn = Help.ParseDouble(_hrg_karcis_tmbhn);
                                            final double hrg_krcs_asrnsi_wisnu = Help.ParseDouble(harga_karcis_asuransi_wisnu_ku);
                                            final double hrg_krcs_asrnsi_wisman = Help.ParseDouble(harga_karcis_asuransi_wisman_ku);


//                                            final int  ttl_wisnu = (int) ((hrg_krcs_wisnu+hrg_krcs_asrnsi_wisnu)*_jml_krcs_wisnu);
//                                            final int   ttl_wisman =(int) ((hrg_krcs_wisman+hrg_krcs_asrnsi_wisman)*_jml_krcs_wisman);
//                                            final int ttl_wisnu_wisman = (ttl_wisnu+ttl_wisman);
//                                            final int ttl_tmbhn = (int) (hrg_krcs_tmbhn*_jml_krcs_tmbhn);
//                                            final int grand_ttl =(int) (ttl_wisnu_wisman+ttl_tmbhn);
//
//                                            _ttl_ptgs.setText(String.valueOf(ttl_wisnu_wisman));
//                                            _ttl_tmbhn_ptgs.setText(String.valueOf(ttl_tmbhn));
//                                            _grand_ttl_ptgs.setText(String.valueOf(grand_ttl));


                                            final int  ttl_wisnu ;
                                            final int   ttl_wisman;
                                            final int ttl_wisnu_wisman ;
                                            final int ttl_tmbhn ;
                                            final int grand_ttl ;


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

                                            _ttl_ptgs.setText(String.valueOf(ttl_wisnu_wisman));
                                            _ttl_tmbhn_ptgs.setText(String.valueOf(ttl_tmbhn));
                                            _grand_ttl_ptgs.setText(String.valueOf(grand_ttl));


                                        }


                                        Log.i("tag", " wisnu_ku= " + harga_karcis_wisata_wisnu_ku);
                                        Log.i("tag", " wisman_ku= " + harga_karcis_wisata_wisman_ku);
                                        Log.i("tag", " wisman_ku= " + harga_karcis_asuransi_wisnu_ku);
                                        Log.i("tag", " wisman_ku= " + harga_karcis_asuransi_wisman_ku);

                                    }
                                }
                            }

                        } catch (JSONException | ParseException e) {
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

                Log.i("","LP.trim() "+LP.trim());

                obj.put("lokasi", LP.trim() );
                obj.put("tgl_kunjungan",Help.getDateTime().trim());
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    private void apiWisatawanTambahanForKUKT(String EP,
                                             String LP,
                                             String _id_kt_par ,
                                             String harga_karcis_wisata_wisnu_ku,
                                             String harga_karcis_wisata_wisman_ku,
                                             String harga_karcis_asuransi_wisnu_ku,
                                             String harga_karcis_asuransi_wisman_ku,

                                             String jml_karcis_wisnu,
                                             String jml_karcis_wisman,
                                             String jml_karcis_tmbhn

    ){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://"+ Help.domain_api() +"/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(PesanKarcisPetugasActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            if( Help.isJSONValid(response) ){
                                JSONObject jsonObject = new JSONObject(response.toString());
                                Log.i("tag","success= " + jsonObject.getBoolean("success") );
                                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                                if( jsonObject.getBoolean("success") ) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("data");

//                                    String _id_kt_par="6de838a1296e4621b3bf4cd5736b8161";
                                    Log.i("","_id_kt_par "+_id_kt_par);

                                    arrKarcisTambahan.clear();
                                    for (int i = 0; i <jsonArray.length();i++ ) {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                        String _kode_ksda = jsonObject1.getString("kode_ksda");
                                        String _kode_lokasi = jsonObject1.getString("kode_lokasi");
                                        String _kode_karcis = jsonObject1.getString("kode_karcis");
                                        String _nama_karcis = jsonObject1.getString("nama_karcis");
                                        String _kode_libur = jsonObject1.getString("kode_libur");
                                        String _harga_karcis_wisata = jsonObject1.getString("harga_karcis_wisata");
                                        String _harga_karcis_asuransi = jsonObject1.getString("harga_karcis_asuransi");
                                        String _id = jsonObject1.getString("id");


                                        if( _id.equals(_id_kt_par)) {

                                            _id_kt.setText(_id);
                                            _kode_ksda_kt.setText(_kode_ksda);
                                            _kode_lokasi_kt.setText(_kode_lokasi);
                                            _kode_karcis_kt.setText(_kode_karcis);
                                            _nama_karcis_kt.setText(_nama_karcis);
                                            _kode_libur_kt.setText(_kode_libur);
                                            _harga_karcis_wisata_kt.setText(_harga_karcis_wisata);
                                            _harga_karcis_asuransi_kt.setText(_harga_karcis_asuransi);


                                            final double _jml_krcs_wisnu = Help.ParseDouble( jml_karcis_wisnu );
                                            final double _jml_krcs_wisman = Help.ParseDouble( jml_karcis_wisman );
                                            final double _jml_krcs_tmbhn = Help.ParseDouble( jml_karcis_tmbhn );

                                            final double hrg_krcs_wisnu = Help.ParseDouble( harga_karcis_wisata_wisnu_ku);
                                            final double hrg_krcs_wisman = Help.ParseDouble(harga_karcis_wisata_wisman_ku);
                                            final double hrg_krcs_tmbhn = Help.ParseDouble(_harga_karcis_wisata);
                                            final double hrg_krcs_asrnsi_wisnu = Help.ParseDouble(harga_karcis_asuransi_wisnu_ku);
                                            final double hrg_krcs_asrnsi_wisman = Help.ParseDouble(harga_karcis_asuransi_wisman_ku);


//                                            final int  ttl_wisnu = (int) ((hrg_krcs_wisnu+hrg_krcs_asrnsi_wisnu)*_jml_krcs_wisnu);
//                                            final int   ttl_wisman =(int) ((hrg_krcs_wisman+hrg_krcs_asrnsi_wisman)*_jml_krcs_wisman);
//                                            final int ttl_wisnu_wisman = (ttl_wisnu+ttl_wisman);
//                                            final int ttl_tmbhn = (int) (hrg_krcs_tmbhn*_jml_krcs_tmbhn);
//                                            final int grand_ttl =(int) (ttl_wisnu_wisman+ttl_tmbhn);
//
//                                            _ttl_ptgs.setText(String.valueOf(ttl_wisnu_wisman));
//                                            _ttl_tmbhn_ptgs.setText(String.valueOf(ttl_tmbhn));
//                                            _grand_ttl_ptgs.setText(String.valueOf(grand_ttl));

                                            final int  ttl_wisnu ;
                                            final int   ttl_wisman;
                                            final int ttl_wisnu_wisman ;
                                            final int ttl_tmbhn ;
                                            final int grand_ttl ;

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

                                            _ttl_ptgs.setText(String.valueOf(ttl_wisnu_wisman));
                                            _ttl_tmbhn_ptgs.setText(String.valueOf(ttl_tmbhn));
                                            _grand_ttl_ptgs.setText(String.valueOf(grand_ttl));

                                        }

                                        Log.i("","tambahan _kode_ksda "+_kode_ksda);
                                        Log.i("","tambahan _kode_lokasi "+_kode_lokasi);
                                        Log.i("","tambahan _kode_karcis "+_kode_karcis);
                                        Log.i("","tambahan _nama_karcis "+_nama_karcis);
                                        Log.i("","tambahan _kode_libur "+_kode_libur);
                                        Log.i("","tambahan _harga_karcis_wisata "+_harga_karcis_wisata);
                                        Log.i("","tambahan _harga_karcis_asuransi "+_harga_karcis_asuransi);
                                    }
                                }
                            }

                        } catch (JSONException | ParseException e) {
                            Log.i("triono", "error ===" + e.toString() );
                            e.printStackTrace();
                        }
                        requestQueue.stop();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("tag", "response spinnerWisatawanTambahan=" + error.toString());
                error.printStackTrace();
                requestQueue.stop();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> obj = new HashMap<String, String>();

                Log.i("tag", "LP spinnerWisatawanTambahan =" + LP );
                Log.i("tag", "getDateTime spinnerWisatawanTambahan =" + Help.getDateTime() );

                obj.put("lokasi", LP.trim() );
                obj.put("tgl_kunjungan",Help.getDateTime().trim());
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }






    private void spinnerWisatawanTambahan(String EP, String LP){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://"+ Help.domain_api() +"/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(PesanKarcisPetugasActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                response -> {

                    try {
                        if( Help.isJSONValid(response) ){
                            JSONObject jsonObject = new JSONObject(response.toString());
                            Log.i("tag","success= " + jsonObject.getBoolean("success") );
                            findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                            if( jsonObject.getBoolean("success") ) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                arrKarcisTambahan.clear();
                                for (int i = 0; i <jsonArray.length();i++ ) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String _kode_ksda = jsonObject1.getString("kode_ksda");
                                    String _kode_lokasi = jsonObject1.getString("kode_lokasi");
                                    String _kode_karcis = jsonObject1.getString("kode_karcis");
                                    String _nama_karcis = jsonObject1.getString("nama_karcis");
                                    String _kode_libur = jsonObject1.getString("kode_libur");
                                    String _harga_karcis_wisata = jsonObject1.getString("harga_karcis_wisata");
                                    String _harga_karcis_asuransi = jsonObject1.getString("harga_karcis_asuransi");
                                    String _id = jsonObject1.getString("id");

//                                        sessionManager.createSessionWisTmbhn(_kode_ksda,
//                                                                            _kode_lokasi,
//                                                                            _kode_karcis,
//                                                                            _nama_karcis,
//                                                                            _kode_libur,
//                                                                            _harga_karcis_wisata,
//                                                                            _harga_karcis_asuransi,
//                                                                            _id );

//                                        arrListTambahanPtgs.add(i, "Rp."+ (_harga_karcis_wisata) + " - (" + _nama_karcis + ") ");
//                                        arrKarcisTambahan.add(i, _nama_karcis );
                                    arrKarcisTambahan.add(new SpinnerKarcisTambahan(_kode_karcis,_nama_karcis,_harga_karcis_wisata, _id));

                                    Log.i("","tambahan _kode_ksda "+_kode_ksda);
                                    Log.i("","tambahan _kode_lokasi "+_kode_lokasi);
                                    Log.i("","tambahan _kode_karcis "+_kode_karcis);
                                    Log.i("","tambahan _nama_karcis "+_nama_karcis);
                                    Log.i("","tambahan _kode_libur "+_kode_libur);
                                    Log.i("","tambahan _harga_karcis_wisata "+_harga_karcis_wisata);
                                    Log.i("","tambahan _harga_karcis_asuransi "+_harga_karcis_asuransi);
                                }
                            }
                        }

                    } catch (JSONException e) {
                        Log.i("triono", "error ===" + e.toString() );
                        e.printStackTrace();
                    }
                    requestQueue.stop();
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("tag", "response spinnerWisatawanTambahan=" + error.toString());
                error.printStackTrace();
                requestQueue.stop();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> obj = new HashMap<String, String>();

                Log.i("tag", "LP spinnerWisatawanTambahan =" + LP );
                Log.i("tag", "getDateTime spinnerWisatawanTambahan =" + Help.getDateTime() );

                obj.put("lokasi", LP.trim() );
                obj.put("tgl_kunjungan",Help.getDateTime().trim());
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }



    private void inputKarcisPetugas(String EP,
                                    String mode_bayar,
                                    String nama_lokasi,
                                    String jml_krcs_tmbhn,
                                    long selisih_day,
                                    String flag_print,
                                    String nama_bayar
    ){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://"+ Help.domain_api() +"/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(PesanKarcisPetugasActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                response -> {
                    Log.i("tag","response= " + response );
                    try {
                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        if( Help.isJSONValid(response) ) {

                            JSONObject jObj = new JSONObject(response);
                            String data = jObj.getString("data");
                            JSONObject jsonObject1 = new JSONObject(data);
                            boolean berhasil = jsonObject1.getBoolean("berhasil");

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
                                String _mode_pembayaran = jsonObject1.getString("mode_pembayaran");
                                String _nama_pengunjung = jsonObject1.getString("nama_pengunjung");
                                String _no_hp_pengunjung = jsonObject1.getString("no_hp_pengunjung");
                                String _email_pengunjung = jsonObject1.getString("email_pengunjung");


//                                    Intent i = new Intent(PesanKarcisPetugasActivity.this, SuccessRegistrasiWisatawanActivity.class);
//                                    i.putExtra("result_dt_ket","Pesanan Karcis berhasil, harap check email Anda");
//                                    i.putExtra("result_dt_email", _alamat_email);
//                                    i.putExtra("result_dt_berhasil", true);
//                                    i.putExtra("result_dt_flag", "flagPesanKarcisPetugas");
//                                    startActivity(i);


                                final String tgl_kunjungan_sd =  _tgl_kunjungan_ptgs_2.getText().toString().trim();

                                Intent i = new Intent(getApplicationContext(), NotifSuksesActivity.class);
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
                                i.putExtra("_jumlah_tambahan", jml_krcs_tmbhn);
                                i.putExtra("_nama_lokasi", nama_lokasi);
                                i.putExtra("_mode_pembayaran", _mode_pembayaran);
                                i.putExtra("_nama_pengunjung", _nama_pengunjung);
                                i.putExtra("_no_hp_pengunjung", _no_hp_pengunjung);
                                i.putExtra("_email_pengunjung", _email_pengunjung);
                                i.putExtra("_tgl_kunjungan_sd", tgl_kunjungan_sd);
                                i.putExtra("_selisih_hari", String.valueOf(selisih_day) );
                                i.putExtra("flag_print", "0");
                                i.putExtra("result_dt_berhasil", true);
                                i.putExtra("result_dt_flag", "flagPesanKarcisPetugas");
                                i.putExtra("_nama_pembayaran", nama_bayar);
                                startActivity(i);

                            }
                        }
                    } catch (JSONException e) {
                        Log.i("triono", "error ===" + e.toString() );
                        e.printStackTrace();
                    }
                    requestQueue.stop();
                }, error -> {
                    Log.i("tag", "response inputKarcisPetugas=" + error.toString());
                    error.printStackTrace();
                    requestQueue.stop();
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> obj = new HashMap<String, String>();

                String flag_pemesan = null;
//                final String key_kode_ksda = (String) sessionManager.getDaftarKarcisWisatawanUtama().get(SessionManager.key_kode_ksda).trim();
                final String key_name =  _nama_pengunjung_ptgs.getText().toString().trim().trim();
                final String key_email =  _email_pengunjung.getText().toString().trim().trim();
                final String key_hp =  _hp_pengunjung_ptgs.getText().toString().trim().trim();
                final String key_tgl_penjualan =  Help.getDateTime().trim();
                final String tgl_kunjungan =  _tgl_kunjungan_ptgs.getText().toString().trim();
                final String key_kode_lokasi =   _kode_lokasi_ku.getText().toString().trim().trim();
                final String key_id_utama =   _id_ku.getText().toString().trim().trim();
                final String key_id_tmbhn =   _id_kt.getText().toString().trim().trim();
                String jml_wisnu = _jml_krcs_wisnu_ptgs.getText().toString().trim().trim();
                String jml_wisman = _jml_krcs_wisman_ptgs.getText().toString().trim();
                String jml_tmhn = _jml_krcs_wisman_tmbhn_ptgs.getText().toString().trim();
                final String key_kode_lok_new = (String) sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi);
//                flag_pemesan = (key_kode_lok_new == null ? "1" : "2");

                if ( key_kode_lok_new.isEmpty() ){
                    flag_pemesan = "1";
                } else {
                    flag_pemesan= "2";
                }

                String jns_byr = "";
                final  String hp_pengunjung = _hp_pengunjung_ptgs.getText().toString().trim();
                final String email_pengunjung = _email_pengunjung.getText().toString().trim();
                final String nama_pengunjung = _nama_pengunjung_ptgs.getText().toString().trim();
                final String tgl_kunjungan_sd =  _tgl_kunjungan_ptgs_2.getText().toString();

                if(jml_wisnu.equals("")){
                    jml_wisnu = "0";
                }
                if(jml_wisman.equals("")){
                    jml_wisman = "0";
                }
                if(jml_tmhn.equals("")){
                    jml_tmhn = "0";
                }

                if( mode_bayar.equals("1") ){
                    jns_byr = "1";
//                    jns_byr = "2";
                } else{
                    jns_byr = "2";
                }

                Log.i("tag","mode_pembayaran= " + jns_byr );
                Log.i("tag","nama_bayar= " + nama_bayar );
                Log.i("tag","prefix_bank= " + mode_bayar);
                Log.i("tag","hp_pengunjung= " + hp_pengunjung );
                Log.i("tag","nama_pengunjung= " + nama_pengunjung );
                Log.i("tag","email_pengunjung= " + email_pengunjung );
                Log.i("tag","key_email= " + key_email );
                Log.i("tag","key_hp= " + key_hp );
                Log.i("tag","key_tgl_penjualan= " + key_tgl_penjualan );
                Log.i("tag","tgl_kunjungan= " + tgl_kunjungan );
                Log.i("tag","key_kode_lokasi= " + key_kode_lokasi );
                Log.i("tag","key_id_utama= " + key_id_utama );
                Log.i("tag","key_id_tmbhn= " + key_id_tmbhn );
                Log.i("tag","jml_wisnu= " + jml_wisnu );
                Log.i("tag","jml_wisman= " + jml_wisman );
                Log.i("tag","jml_tmhn= " + jml_tmhn );
                Log.i("tag","flag_pemesan= " + flag_pemesan );
                Log.i("tag","tgl_kunjungan_sd= " + tgl_kunjungan_sd );
                Log.i("tag","selisih_day= " + selisih_day);



                obj.put("registration_by", key_email);
                obj.put("flag_pemesan",flag_pemesan);
                obj.put("nama",key_name);
                obj.put("sellular_no",key_hp);
                obj.put("alamat_email",key_email);
                obj.put("tgl_penjualan",key_tgl_penjualan);
                obj.put("kode_lokasi",key_kode_lokasi);
                obj.put("id_karcis_utama",key_id_utama);
                obj.put("id_karcis_tambahan",key_id_tmbhn);
                obj.put("jumlah_wisnu",jml_wisnu);
                obj.put("jumlah_wisman",jml_wisman);
                obj.put("jumlah_tambahan",jml_tmhn);
                obj.put("mode_pembayaran", jns_byr);
                obj.put("no_hp_pengunjung", hp_pengunjung);
                obj.put("email_pengunjung", email_pengunjung);
                obj.put("nama_pengunjung", nama_pengunjung);
                obj.put("tgl_kunjungan",tgl_kunjungan);
                obj.put("tgl_kunjungan_sd",tgl_kunjungan_sd);
                obj.put("jumlah_hari", String.valueOf(selisih_day) );
                obj.put("prefix_bank", mode_bayar );

                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }


}




