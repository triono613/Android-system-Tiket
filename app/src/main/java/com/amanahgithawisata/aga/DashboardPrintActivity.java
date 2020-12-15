package com.amanahgithawisata.aga;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.amanahgithawisata.aga.Adapter.SessionManager;
import com.amanahgithawisata.aga.Helper.Help;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mazenrashed.printooth.Printooth;
import com.mazenrashed.printooth.data.printable.ImagePrintable;
import com.mazenrashed.printooth.data.printable.Printable;
import com.mazenrashed.printooth.data.printable.RawPrintable;
import com.mazenrashed.printooth.data.printable.TextPrintable;
import com.mazenrashed.printooth.data.printer.DefaultPrinter;
import com.mazenrashed.printooth.ui.ScanningActivity;
import com.mazenrashed.printooth.utilities.Printing;
import com.mazenrashed.printooth.utilities.PrintingCallback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DashboardPrintActivity extends AppCompatActivity {
    private Printing printing_text = null;
    private Printing printing_qrcode = null;
    PrintingCallback printingCallback = null;
    Button btnPairUnpair, btnPrintStruk;

    TextView textview_nmr_va;
    TextView textview_nm_ket_kelompok;
    TextView textview_no_hp;
    TextView textview_email;
    TextView textview_jml_psrt_wisnu;
    TextView textview_jml_psrt_wisman;
    TextView textview_jml_psrt;
    TextView textview_jml_krcs_tmbhn;
    TextView textview_tgl_beli;
    TextView textview_tgl_kunj;
    TextView textview_tgl_plg;
    TextView textview_tgl_red;
    TextView textview_va_red;
    TextView textview_nm_red;
    TextView textview_bank_red;
    TextView textview_grand_ttl_red;
    TextView textview_jam_red;
    TextView textview_nm_lok_wis;
    TextView textview_tgl_kunj_sd;

    TextView textview_mode_pembayaran;
    TextView textview_nama_pengunjung;
    TextView textview_no_hp_pengunjung;
    TextView textview_email_pengunjung;
    TextView textview_jml_hari;

    Button btn_continue;
    ImageView btn_back_regis;
    SessionManager sessionManager;
    LinearLayout linearMohonWisatawan;
    LinearLayout linearMohonPetugas;
    LinearLayout linear_hp_red;
    LinearLayout linear_email_red;
    LinearLayout linear_bukti_bayar_red;
    LinearLayout linear_nm_bank_red;
    LinearLayout linear_red_nominal_red;
    LinearLayout linear_red_cash_ptgs_red;
    LinearLayout linear_va_red;
    LinearLayout linear_cash_red;
    LinearLayout linear_cash_bottom_red;
    LinearLayout linear_va_bottom_red;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_print);
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        Printooth.INSTANCE.init(this);
        sessionManager = new SessionManager(getApplicationContext());

        textview_nmr_va =  findViewById(R.id.textview_nmr_va);
        textview_nm_ket_kelompok = findViewById(R.id.textview_nm_ket_kelompok);
        textview_no_hp = findViewById(R.id.textview_no_hp);
        textview_email = findViewById(R.id.textview_email);
        textview_jml_psrt_wisnu = findViewById(R.id.textview_jml_psrt_wisnu);
        textview_jml_psrt_wisman = findViewById(R.id.textview_jml_psrt_wisman);
        textview_jml_psrt = findViewById(R.id.textview_jml_psrt);
        textview_jml_krcs_tmbhn = findViewById(R.id.textview_jml_krcs_tmbhn);
        textview_tgl_beli = findViewById(R.id.textview_tgl_beli);
        textview_tgl_kunj = findViewById(R.id.textview_tgl_kunj);
        textview_tgl_plg = findViewById(R.id.textview_tgl_plg);
        btn_continue = findViewById(R.id.btn_continue);
        btn_back_regis =  findViewById(R.id.btn_back_regis);;
        textview_tgl_red =  findViewById(R.id.textview_tgl_red);
        textview_va_red =  findViewById(R.id.textview_va_red);
        textview_nm_red =  findViewById(R.id.textview_nm_red);
        textview_bank_red =  findViewById(R.id.textview_bank_red);
        textview_grand_ttl_red =  findViewById(R.id.textview_grand_ttl_red);
        textview_jam_red =  findViewById(R.id.textview_jam_red);
//        textview_nm_lok_wis =  findViewById(R.id.textview_nm_lok_wis);
        textview_tgl_kunj_sd =  findViewById(R.id.textview_tgl_kunj_sd);
        textview_jml_hari =  findViewById(R.id.textview_jml_hari);
        textview_no_hp_pengunjung = findViewById(R.id.textview_hp_red);
        textview_email_pengunjung = findViewById(R.id.textview_email_red);
        linearMohonWisatawan = findViewById(R.id.linearMohonWisatawan);
        linearMohonPetugas = findViewById(R.id.linearMohonPetugas);
        linear_email_red = findViewById(R.id.linear_email_red);
        linear_hp_red = findViewById(R.id.linear_hp_red);
        linear_bukti_bayar_red = findViewById(R.id.linear_bukti_bayar_red);
        linear_nm_bank_red = findViewById(R.id.linear_nm_bank_red);
        linear_red_nominal_red = findViewById(R.id.linear_red_nominal_red);
        linear_red_cash_ptgs_red = findViewById(R.id.linear_red_cash_ptgs_red);
        linear_va_red = findViewById(R.id.linear_va_red);
        linear_cash_red = findViewById(R.id.linear_cash_red);
        linear_cash_bottom_red = findViewById(R.id.linear_cash_bottom_red);
        linear_va_bottom_red = findViewById(R.id.linear_va_bottom_red);

        btnPairUnpair = findViewById(R.id.btnPairUnpair);
        btnPrintStruk = findViewById(R.id.btnPrint);


        String _vnama = getIntent().getStringExtra("nama");
        String _alamat_email = getIntent().getStringExtra("registration_by");
        String _sellular_no = getIntent().getStringExtra("sellular_no");
        String _jumlah_wisnu = getIntent().getStringExtra("jumlah_wisnu");
        String _jumlah_wisman = getIntent().getStringExtra("jumlah_wisman");
        String _jumlah_tambahan = getIntent().getStringExtra("jumlah_tambahan");
//        String _jumlah_karcis = getIntent().getStringExtra("0");
        String _tgl_penjualan = getIntent().getStringExtra("tgl_penjualan");
        String _tgl_kunjungan = getIntent().getStringExtra("tgl_kunjungan");
        String _tgl_kunj_sd = getIntent().getStringExtra("tgl_kunjungan_sd");
        String _nama_lokasi = getIntent().getStringExtra("nama_lokasi");
        String _nama_pintu = getIntent().getStringExtra("nama_pintu");
        String _kode_lokasi = getIntent().getStringExtra("kode_lokasi");
        String _id_karcis_utama = getIntent().getStringExtra("id_karcis_utama");
        String _id_karcis_tambahan = getIntent().getStringExtra("id_karcis_tambahan");

//        String _menit_valid = getIntent().getStringExtra("_menit_valid");
//        String _tgl_valid = getIntent().getStringExtra("_tgl_valid");
        String _tagihan_total = getIntent().getStringExtra("_tagihan_total");
        String _berhasil = getIntent().getStringExtra("berhasil");
        String result_dt_flag = getIntent().getStringExtra("result_dt_flag");

        String _nama_pengunjung = getIntent().getStringExtra("nama_pengunjung");
        String _no_hp_pengunjung = getIntent().getStringExtra("no_hp_pengunjung");
        String _email_pengunjung = getIntent().getStringExtra("email_pengunjung");


        String _selisih_hari = getIntent().getStringExtra("jumlah_hari");
        String _flag_print = getIntent().getStringExtra("flag_print");
        String _grand_ttl_ptgs = getIntent().getStringExtra("grand_ttl_ptgs");

        Log.i("","_grand_ttl_ptgs dashboard print "+ _grand_ttl_ptgs);


/*
                                     i.putExtra("nama_lokasi",_nama_lokasi);
                                     i.putExtra("registration_by", key_email);
                                     i.putExtra("flag_pemesan", flag_pemesan);
                                     i.putExtra("nama", key_name);
                                     i.putExtra("nama", key_name);
                                     i.putExtra("sellular_no",key_hp);
                                     i.putExtra("alamat_email",key_email);
                                     i.putExtra("tgl_penjualan",key_tgl_penjualan);
                                     i.putExtra("tgl_kunjungan",tgl_kunjungan);
                                     i.putExtra("kode_lokasi",key_kode_lokasi);
                                     i.putExtra("id_karcis_utama",key_id_utama);
                                     i.putExtra("id_karcis_tambahan",key_id_tmbhn);
                                     i.putExtra("jumlah_wisnu",jml_wisnu);
                                     i.putExtra("jumlah_wisman",jml_wisman);
                                     i.putExtra("jumlah_tambahan",jml_tmhn);
                                     i.putExtra("mode_pembayaran", jns_byr);
                                     i.putExtra("no_hp_pengunjung", hp_pengunjung);
                                     i.putExtra("email_pengunjung", email_pengunjung);
                                     i.putExtra("nama_pengunjung", nama_pengunjung);
                                     i.putExtra("tgl_kunjungan",tgl_kunjungan);
                                     i.putExtra("tgl_kunjungan_sd",tgl_kunjungan_sd);
                                     i.putExtra("grand_ttl_ptgs", txt_grand_ttl_ptgs);
                                     try {
                                         i.putExtra("jumlah_hari", String.valueOf(get_selisih_day()) );
                                     } catch (ParseException e) {
                                         e.printStackTrace();
                                     }
*/


        if (Printooth.INSTANCE.hasPairedPrinter()) {
            printing_text = Printooth.INSTANCE.printer();
            printing_qrcode = Printooth.INSTANCE.printer();
        }

        initViews();
        initListeners();

        linear_red_nominal_red.setVisibility(View.GONE);
        linear_red_cash_ptgs_red.setVisibility(View.VISIBLE);
        linear_cash_red.setVisibility(View.VISIBLE);
        linear_va_bottom_red.setVisibility(View.GONE);
        linear_cash_bottom_red.setVisibility(View.VISIBLE);

        textview_nm_ket_kelompok.setText(_vnama);
        textview_no_hp.setText(_sellular_no);
        textview_email.setText(_alamat_email);
        textview_email_pengunjung.setText(_alamat_email);
        textview_no_hp_pengunjung.setText(_sellular_no);
        textview_jml_psrt_wisnu.setText(_jumlah_wisnu);
        textview_jml_psrt_wisman.setText(_jumlah_wisman);
        textview_jml_krcs_tmbhn.setText( _jumlah_tambahan );
        textview_grand_ttl_red.setText( _grand_ttl_ptgs );

//        textview_nm_lok_wis.setText(_nama_lokasi);

        String  new_key_tgl_penjualan ;
        String  new_tgl_kunjungan ;

        if( _tgl_penjualan == null ){
            new_key_tgl_penjualan  = "";
        } else {
            new_key_tgl_penjualan  = Help.formatTgl(_tgl_penjualan) ;
        }
        textview_tgl_beli.setText( new_key_tgl_penjualan );
        textview_tgl_kunj_sd.setText(Help.formatTgl(_tgl_kunj_sd));
        textview_jml_hari.setText(_selisih_hari);
        textview_tgl_red.setText(Help.getToday());


        if( _tgl_kunjungan == null ){
            new_tgl_kunjungan  = "";
        } else {
            new_tgl_kunjungan  = Help.formatTgl(_tgl_kunjungan) ;
        }
        textview_tgl_kunj.setText(new_tgl_kunjungan );
        textview_nm_red.setText(_vnama);
        textview_bank_red.setText("PT Bank BRIsyariah Tbk ");
//        textview_grand_ttl_red.setText(_tagihan_total);




        btnPairUnpair.setOnClickListener(v -> {
            if (Printooth.INSTANCE.hasPairedPrinter()) {
                Printooth.INSTANCE.removeCurrentPrinter();
            } else {
                startActivityForResult(new Intent(this, ScanningActivity.class), ScanningActivity.SCANNING_FOR_PRINTER);
                initViews();
            }
        });

        btnPrintStruk.setOnClickListener(v -> {
            if (!Printooth.INSTANCE.hasPairedPrinter()) {
                startActivityForResult(new Intent(this, ScanningActivity.class), ScanningActivity.SCANNING_FOR_PRINTER);
            } else {

                        getSomePrintables(
                                "new_input_petugas",
                                _nama_lokasi,
                                _nama_pintu,
                                "",
                                "",
                                _nama_pengunjung,
                                _email_pengunjung,
                                _no_hp_pengunjung,
                                _jumlah_wisnu,
                                _jumlah_wisman,
                                "0",
                                _jumlah_tambahan,
                                _tgl_penjualan,
                                _tgl_kunjungan,
                                _tgl_kunj_sd,
                                _tgl_kunj_sd,
                                _kode_lokasi,
                                _id_karcis_utama,
                                _id_karcis_tambahan,
                                _selisih_hari
                                );

             /*
                getSomePrintables(
                        "new_input_petugas",
                        "Kedu Utara",
                        "Gunung Kembang via Blembem",
                        "0000220000000165",
                        "Wonosobo",
                        "triono",
                        "triono.triono1@gmail.com",
                        "089508611088",
                        "1",
                        "0",
                        "0",
                        "1",
                        "03-12-2020",
                        "08-12-2020",
                        "09-12-2020",
                        "10-12-2020",
                        "00022",
                        "a47f34bcd49d444ea2e880a9c54d9f17",
                        "a769040fa7734356a54bfbd364fe862c",
                        "2"
                );
                */

            }
        });

    }



    //    public ArrayList<Printable> getSomePrintables(
    public void getSomePrintables(
            String EP,
            String kph,
            String nm_objek_wisata,
            String va,
            String nm_kota,
            String nama,
            String email,
            String hp,
            String jml_psrt_wisnu,
            String jml_psrt_wisman,
            String jml_psrt,
            String jml_krcs_tmbhn,
            String tgl_beli,
            String tgl_kunj,
            String tgl_kunj_sd,
            String tgl_plg,
            String kode_lokasi,
            String id_karcis_utama,
            String id_karcis_tambahan,
            String selisih_hari
    ) {

            findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
            String server_url = "http://kaffah.amanahgitha.com/~androidwisata/?data="+ EP;
            final RequestQueue requestQueue = Volley.newRequestQueue(DashboardPrintActivity.this);
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
                                    String __id = jsonObject1.getString("id");
                                    String __va_no = jsonObject1.getString("va_no");
                                    String __va_no_berlaku_sd = jsonObject1.getString("va_no_berlaku_sd");
                                    String __vnama = jsonObject1.getString("nama");
                                    String __alamat_email = jsonObject1.getString("alamat_email");
                                    String __sellular_no = jsonObject1.getString("sellular_no");
                                    String __jumlah_wisnu = jsonObject1.getString("jumlah_wisnu");
                                    String __jumlah_wisman = jsonObject1.getString("jumlah_wisman");
                                    String __jumlah_karcis = jsonObject1.getString("jumlah_karcis");
                                    String __tgl_penjualan = jsonObject1.getString("tgl_penjualan");
                                    String __tgl_kunjungan = jsonObject1.getString("tgl_kunjungan");
                                    String __menit_valid = jsonObject1.getString("menit_valid");
                                    String __tgl_valid = jsonObject1.getString("tgl_valid");
                                    String __tagihan_total = jsonObject1.getString("tagihan_total");
                                    String __mode_pembayaran = jsonObject1.getString("mode_pembayaran");
                                    String __nama_pengunjung = jsonObject1.getString("nama_pengunjung");
                                    String __no_hp_pengunjung = jsonObject1.getString("no_hp_pengunjung");
                                    String __email_pengunjung = jsonObject1.getString("email_pengunjung");
                                    String __qrcode = jsonObject1.getString("qrcode");

                                    ArrayList<Printable> p = new ArrayList<>();
                                    p.add(new RawPrintable.Builder(new byte[]{27, 100, 4}).build());
//                p.add(new RawPrintable.Builder(new byte[]{29, 33, 35 }).build());
                                    p.add((new TextPrintable.Builder())
                                            .setText("Assalamualaikum")
                                            .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                                            .setAlignment(DefaultPrinter.Companion.getALIGNMENT_CENTER())
                                            .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                                            .setNewLinesAfter(1)
                                            .build());

                                    p.add((new TextPrintable.Builder())
                                            .setText("Warahmatullahi Wabarakatuh")
                                            .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                                            .setAlignment(DefaultPrinter.Companion.getALIGNMENT_CENTER())
                                            .setNewLinesAfter(1)
                                            .setLineSpacing(DefaultPrinter.Companion.getLINE_SPACING_60())
                                            .build());

                                    p.add((new TextPrintable.Builder())
                                            .setText(kph)
                                            .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                                            .setAlignment(DefaultPrinter.Companion.getALIGNMENT_CENTER())
                                            .setNewLinesAfter(1)
                                            .build());
                                    p.add((new TextPrintable.Builder())
                                            .setText(nm_objek_wisata)
                                            .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                                            .setAlignment(DefaultPrinter.Companion.getALIGNMENT_CENTER())
                                            .setNewLinesAfter(1)
                                            .build());

                                    p.add((new TextPrintable.Builder())
                                            .setText("Nomor VA : " + __va_no)
                                            .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                                            .setAlignment(DefaultPrinter.Companion.getALIGNMENT_LEFT())
                                            .setNewLinesAfter(1)
                                            .build()
                                    );

                                    p.add((new TextPrintable.Builder())
                                            .setText("Nama Ketua Kelompok : " + nama)
                                            .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                                            .setAlignment(DefaultPrinter.Companion.getALIGNMENT_LEFT())
                                            .setNewLinesAfter(1)
                                            .build());

                                    p.add((new TextPrintable.Builder())
                                            .setText("No HP : " + __no_hp_pengunjung)
                                            .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                                            .setAlignment(DefaultPrinter.Companion.getALIGNMENT_LEFT())
                                            .setNewLinesAfter(1)
                                            .build());

                                    p.add((new TextPrintable.Builder())
                                            .setText("Email Ketua Kelompok : " + __email_pengunjung)
                                            .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                                            .setAlignment(DefaultPrinter.Companion.getALIGNMENT_LEFT())
                                            .setNewLinesAfter(1)
                                            .build());


                                    p.add((new TextPrintable.Builder())
                                            .setText("Jml peserta Wisnu : " + __jumlah_wisnu)
                                            .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                                            .setAlignment(DefaultPrinter.Companion.getALIGNMENT_LEFT())
                                            .setNewLinesAfter(1)
                                            .build());

                                    p.add((new TextPrintable.Builder())
                                            .setText("Jml peserta Wisman : " + __jumlah_wisman)
                                            .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                                            .setAlignment(DefaultPrinter.Companion.getALIGNMENT_LEFT())
                                            .setNewLinesAfter(1)
                                            .build());

                                    p.add((new TextPrintable.Builder())
                                            .setText("Jumlah Karcis Tambahan : " + jml_krcs_tmbhn)
                                            .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                                            .setAlignment(DefaultPrinter.Companion.getALIGNMENT_LEFT())
                                            .setNewLinesAfter(1)
                                            .build());


                                    p.add((new TextPrintable.Builder())
                                            .setText("Tgl. Pembelian : " + __tgl_penjualan)
                                            .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                                            .setNewLinesAfter(1)
                                            .setAlignment(DefaultPrinter.Companion.getALIGNMENT_LEFT())
                                            .build());

                                    p.add((new TextPrintable.Builder())
                                            .setText("Tgl. Kunjungan : " + __tgl_kunjungan)
                                            .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                                            .setAlignment(DefaultPrinter.Companion.getALIGNMENT_LEFT())
                                            .setNewLinesAfter(1)
                                            .build());

                                    p.add((new TextPrintable.Builder())
                                            .setText("Tgl. Pulang : " + tgl_plg)
                                            .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                                            .setNewLinesAfter(1)
                                            .setAlignment(DefaultPrinter.Companion.getALIGNMENT_LEFT())
                                            .build());

                                    p.add((new TextPrintable.Builder())
                                            .setText("Jml Hari : " + selisih_hari)
                                            .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                                            .setNewLinesAfter(1)
                                            .setAlignment(DefaultPrinter.Companion.getALIGNMENT_LEFT())
                                            .build());

                                    p.add((new TextPrintable.Builder())
                                            .setText("Jml Yang Dibayar Rp. : " + __tagihan_total)
                                            .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                                            .setNewLinesAfter(1)
                                            .setLineSpacing(DefaultPrinter.Companion.getLINE_SPACING_60())
                                            .setAlignment(DefaultPrinter.Companion.getALIGNMENT_LEFT())
                                            .build());


                                    p.add((new TextPrintable.Builder())
                                            .setText("Terimakasih telah melakukan")
                                            .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                                            .setNewLinesAfter(1)
                                            .setAlignment(DefaultPrinter.Companion.getALIGNMENT_CENTER())
                                            .build());
                                    p.add((new TextPrintable.Builder())
                                            .setText("Pembayaran secara CASH.")
                                            .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                                            .setNewLinesAfter(1)
                                            .setAlignment(DefaultPrinter.Companion.getALIGNMENT_CENTER())
                                            .build());

                                    p.add((new TextPrintable.Builder())
                                            .setText(tgl_beli + " WIB")
                                            .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                                            .setNewLinesAfter(1)
                                            .setLineSpacing(DefaultPrinter.Companion.getLINE_SPACING_60())
                                            .setAlignment(DefaultPrinter.Companion.getALIGNMENT_CENTER())
                                            .build());

                                    Log.i("","__qrcode="+__qrcode);

                                    Picasso.with(getApplicationContext())
                                            .load(__qrcode)
                                            .into(new Target() {
                                                @Override
                                                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                                    p.add(new ImagePrintable.Builder(bitmap)
                                                            .setAlignment(DefaultPrinter.Companion.getALIGNMENT_CENTER())
                                                            .setNewLinesAfter(1)
                                                            .build());
                                                    printing_text.print(p);
                                                }

                                                @Override
                                                public void onBitmapFailed(Drawable errorDrawable) {

                                                }

                                                @Override
                                                public void onPrepareLoad(Drawable placeHolderDrawable) {

                                                }
                                            });



                                    Intent intent;
                                    intent = new Intent(getApplicationContext(), DashboardPetugasActivity.class);
                                    intent.putExtra("result_flag_notif", "sukses");
                                    startActivity(intent);

                                }
                            }
                        } catch (JSONException e) {
                            Log.i("", "error print struk=" + e.toString() );
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

                    String flag_pemesan = null;
                    final String key_kode_lok_new = (String) sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi);
                    final String key_email = (String) sessionManager.getUserDetail().get(SessionManager.key_email);

                    if ( key_kode_lok_new.isEmpty() ){ flag_pemesan = "1"; }
                    else { flag_pemesan= "2"; }


                    Log.i("","registration_by : "+ key_email );
                    Log.i("","flag_pemesan : "+flag_pemesan);
                    Log.i("","nama : "+nama);
                    Log.i("","sellular_no : "+hp);
                    Log.i("","alamat_email : "+ key_email);
                    Log.i("","tgl_penjualan : "+tgl_beli);
                    Log.i("","tgl_kunjungan : "+tgl_kunj);
                    Log.i("","kode_lokasi : "+kode_lokasi);
                    Log.i("","id_karcis_utama : "+id_karcis_utama);
                    Log.i("","id_karcis_tambahan : "+id_karcis_tambahan);
                    Log.i("","jumlah_wisnu : "+jml_psrt_wisnu);
                    Log.i("","jumlah_wisman : "+jml_psrt_wisman);
                    Log.i("","jumlah_tambahan : "+jml_krcs_tmbhn);
                    Log.i("","mode_pembayaran : "+ "1");
                    Log.i("","no_hp_pengunjung : "+ hp);
                    Log.i("","email_pengunjung : "+ email);
                    Log.i("","nama_pengunjung : "+ nama);
                    Log.i("","tgl_kunjungan_sd : "+tgl_kunj_sd);
                    Log.i("","jumlah_hari : "+ selisih_hari);


                    obj.put("registration_by", key_email );
                    obj.put("flag_pemesan",flag_pemesan);
                    obj.put("nama",nama);
                    obj.put("sellular_no",hp);
                    obj.put("alamat_email", key_email);
                    obj.put("tgl_penjualan",tgl_beli);
                    obj.put("tgl_kunjungan",tgl_kunj);
                    obj.put("kode_lokasi",kode_lokasi);
                    obj.put("id_karcis_utama",id_karcis_utama);
                    obj.put("id_karcis_tambahan",id_karcis_tambahan);
                    obj.put("jumlah_wisnu",jml_psrt_wisnu);
                    obj.put("jumlah_wisman",jml_psrt_wisman);
                    obj.put("jumlah_tambahan",jml_krcs_tmbhn);
                    obj.put("mode_pembayaran", "1");
                    obj.put("no_hp_pengunjung", hp);
                    obj.put("email_pengunjung", email);
                    obj.put("nama_pengunjung", nama);
                    obj.put("tgl_kunjungan_sd",tgl_kunj_sd);
                    obj.put("jumlah_hari", String.valueOf(selisih_hari) );



                    return obj;
                }
            };
            int socketTimeout = 0;
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(policy);
            requestQueue.add(stringRequest);






    }

    private void printSomeImages() {
        if (printing_qrcode != null) {
            Log.d("xxx", "printSomeImages ");
            ArrayList<Printable> printImg = new ArrayList<>();

            Resources resources = getResources();
            String uri = "http://kaffah.amanahgitha.com/~androidwisata/va.bmp";
            Picasso.with(getApplicationContext())
                    .load(uri)
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            printImg.add(new ImagePrintable.Builder(bitmap)
                                    .setAlignment(DefaultPrinter.Companion.getALIGNMENT_CENTER())
                                    .setNewLinesAfter(1)
                                    .build());
                            printing_qrcode.print(printImg);
                            printing_qrcode.print(printImg);
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });

        }
    }

/*
    Assalamualaikum warahmatullahi wabarakatuh
    Terima kasih atas Booking Online KPH Kedu Utara dari Petugas yang telah dilakukan. Bersama ini konfirmasi data Booking Online sebagai berikut :

    Nomor Karcis Virtual	:	0000220000000165
    Nama Ketua Kelompok	:	gg
    Email Ketua Kelompok	:	triono.triono1@gmail.com
    No HP	:	88888
    Jumlah peserta Wisnu	:	1
    Jumlah peserta Wisman	:	0
    Jumlah Peserta	:	1
    Jumlah Karcis Tambahan	:	1
    Jumlah Hari	:	2
    Tgl. Pembelian	:	02-12-2020
    Tgl. Kunjungan	:	02-12-2020
    Tgl. Pulang	:	03-12-2020
    Terimakasih telah melakukan pembayaran secara CASH :

    Tanggal : 02-12-2020 WIB
    Nomor Karcis Virtual : 0000220000000165
    Atas Nama gg
    Alamat e-Mail triono.triono1@gmail.com
    No. Sellular 88888
    BUKTI PEMBAYARAN
    Jumlah Yang telah Dibayar Rp. 12.000,00

    Mohon tidak meng-hapus notifikasi e-mail ini, notifikasi email ini sebagai bukti bahwa sudah membayar dipintu masuk
    PEMBAYARAN KARCIS VIRTUAL INI DILAKUKAN SECARA TUNAI/CASH.
    Terima kasih telah melakukan pembayaran secara TUNAI, lain waktu boleh dicoba dengan VIRTUAL ACCOUNT.

    Wassalamualaikum warahmatullahi wabarakatuh


    KPH Kedu Utara
    KPH Kedu Utara
    Jawa Tengah
*/

    private void initListeners() {
        if (printing_text != null && printingCallback == null) {
            Log.d("xxx", "initListeners ");
            printingCallback = new PrintingCallback() {

                public void connectingWithPrinter() {
                    Toast.makeText(getApplicationContext(), "Proses Mencetak Struk..", Toast.LENGTH_LONG).show();
                    Log.d("xxx", "Connecting");
                }

                public void printingOrderSentSuccessfully() {
                    Toast.makeText(getApplicationContext(), "Sukses Mencetak Struk", Toast.LENGTH_SHORT).show();
                    Log.d("xxx", "Sukses Mencetak Struk");
                }

                public void connectionFailed(@NonNull String error) {
                    Toast.makeText(getApplicationContext(), "connectionFailed :" + error, Toast.LENGTH_SHORT).show();
                    Log.d("xxx", "connectionFailed : " + error);
                }

                public void onError(@NonNull String error) {
                    Toast.makeText(getApplicationContext(), "onError :" + error, Toast.LENGTH_SHORT).show();
                    Log.d("xxx", "onError : " + error);
                }

                public void onMessage(@NonNull String message) {
                    Toast.makeText(getApplicationContext(), "onMessage :" + message, Toast.LENGTH_SHORT).show();
                    Log.d("xxx", "onMessage : " + message);
                }
            };

            Printooth.INSTANCE.printer().setPrintingCallback(printingCallback);
        }
    }

    private void initViews() {
        Log.i("", "Printooth.INSTANCE.getPairedPrinter() = " + (Printooth.INSTANCE.getPairedPrinter()));
//        (Printooth.INSTANCE.hasPairedPrinter())?("Un-pair "+ Printooth.INSTANCE.getPairedPrinter().getName()):"Pair with printer");
        String has = null;
        if (Printooth.INSTANCE.getPairedPrinter() != null) {
            has = "Printer Terhubung "+ Printooth.INSTANCE.getPairedPrinter().getName();
        } else {
            has = "Cari Printer..";
        }
        ((Button) findViewById(R.id.btnPairUnpair)).setText(has);
    }

}