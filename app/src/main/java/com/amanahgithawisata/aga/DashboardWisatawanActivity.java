package com.amanahgithawisata.aga;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.amanahgithawisata.aga.Adapter.CustomAdapterFeatured;
import com.amanahgithawisata.aga.Adapter.SessionManager;
import com.amanahgithawisata.aga.Model.ModelFeatured;
import com.gauravk.bubblenavigation.BubbleNavigationLinearView;

import java.util.ArrayList;

public class DashboardWisatawanActivity extends AppCompatActivity implements LocationListener {

    TextView txt_jljh_wis;
    TextView txt_jljh_wis_dec;
    RecyclerView featuredRecycle,recyclerViewWebView;
    CustomAdapterFeatured customAdapterFeatured;
    LinearLayoutManager linearLayoutManager;
    WebView webView;
    WebSettings webSettings;
    ProgressDialog pDialog;

    Button btn_logout_wstn;
    LinearLayout _card_pemesanan_karcis_wstn;
    LinearLayout _card_status_karcis_wstn;
    LinearLayout _card_ganti_password_wstwn;
    BubbleNavigationLinearView navigationBar;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_wisatawan);
        SessionManager sessionManager;

        TextView txt_jljh_wis = (TextView) findViewById(R.id.txt_jljh_wis);
        TextView txt_jljh_wis_desc = (TextView) findViewById(R.id.txt_jljh_wis_dec);
        featuredRecycle = findViewById(R.id.featuredRecycle);
//        recyclerViewWebView = findViewById(R.id.RecyclerViewWebView);

        SpannableString content = new SpannableString("Jelajah Wisata");
        SpannableString content_desc = new SpannableString("Jelajah Wisata adalah aplikasi karya anak bangsa yang memuat Lokasi Objek-Objek Wisata resmi yang berada dalam naungan Taman Nasional Indonesia ");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);

        txt_jljh_wis.setText(content);
        txt_jljh_wis_desc.setText(content_desc);


        
        featuredRecycle();
        recyclerViewWebView();

        weather();




//        btn_logout_wstn =(Button) findViewById(R.id.btn_logout_wstn);
        _card_pemesanan_karcis_wstn = (LinearLayout) findViewById(R.id.card_pemesanan_karcis_wstn);
        _card_status_karcis_wstn = (LinearLayout) findViewById(R.id.card_status_karcis_wstn);
        _card_ganti_password_wstwn = (LinearLayout) findViewById(R.id.card_ganti_password_wstwn);

        sessionManager = new SessionManager(getApplicationContext());



//        Toast.makeText(DashboardWisatawanOLdActivity.this,"check session : "+sessionManager.isLoggedIn()+" - "+sessionManager.getFlag()+" - "+(sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi)), Toast.LENGTH_LONG).show();


        _card_pemesanan_karcis_wstn.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardWisatawanActivity.this, PesanKarcisWisatawanActivity.class);
//                Intent intent = new Intent(DashboardWisatawanOLdActivity.this, LokasiWisataScrollViewActivity.class);

            String result_flag_pesan_karcis_sukses = getIntent().getStringExtra("result_flag_pesan_karcis_sukses");
            if( result_flag_pesan_karcis_sukses != null ){
                Log.i("","dashboard sukses");
                intent.putExtra("result_flag_pesan_karcis_sukses", "sukses");
            }else {
                Log.i("","dashboard not sukses");
                intent.putExtra("result_flag_pesan_karcis_sukses", "");
            }

            startActivity(intent);
        });



         navigationBar = findViewById(R.id.navigationBarx);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigationBar.getLayoutParams();
//        layoutParams.setBehavior(new BottomAppBar.Behavior());

        navigationBar.setNavigationChangeListener((view, position) -> {
            Log.i("","position= "+position);
            switch (position){
                case 1:
                    Intent a = new Intent(DashboardWisatawanActivity.this, PesanKarcisWisatawanActivity.class);
                    startActivity(a);
                    break;
                case 2:
                    Intent b = new Intent(DashboardWisatawanActivity.this, StatusKarcisWisatawanActivity.class);
                    startActivity(b);
                    break;
                case 3:
                    Intent c = new Intent(DashboardWisatawanActivity.this, EditPasswordWisatawanActivity.class);
                    startActivity(c);
                    break;
                case 4:
                    AlertDialog.Builder builder = new AlertDialog.Builder(DashboardWisatawanActivity.this);
                    builder.setMessage("Anda Yakin Akan Keluar ?")
                            .setCancelable(false)
                            .setPositiveButton("Ya", (dialog, id) -> {
                                sessionManager.logout();
                            })
                            .setNegativeButton("Tidak", (dialog, id) -> dialog.cancel());
                    AlertDialog alert = builder.create();
                    alert.show();
                    break;
            }
        });

        _card_status_karcis_wstn.setOnClickListener(v -> {
            Intent i = new Intent(DashboardWisatawanActivity.this, StatusKarcisWisatawanActivity.class);
            startActivity(i);
//            overridePendingTransition(R.anim.app_getstarted,R.anim.btt);
        });

        _card_ganti_password_wstwn.setOnClickListener(v -> {
            Intent x = new Intent(DashboardWisatawanActivity.this, EditPasswordWisatawanActivity.class);
            startActivity(x);
        });


        String key_name_a = sessionManager.getUserDetail().get(SessionManager.key_name);
        String key_email_a = sessionManager.getUserDetail().get(SessionManager.key_email);
        String is_login_a = sessionManager.getUserDetail().get(SessionManager.is_login);
        String is_flag_a = sessionManager.getUserDetail().get(SessionManager.key_flag);


//        informasiStatusKarcis("informasi_status_karcis");


    }

    @SuppressLint("SetTextI18n")
    private void weather() {

        String strDescWeather = "heavy intensity rain";
        Log.i("","strDescWeather= "+strDescWeather);
        LottieAnimationView iconTemp ;
        TextView tvWeather,tvNamaKota,tvTempeatur,tvKecepatanAngin,tvKelembaban;

        iconTemp = findViewById(R.id.iconTemp);
        tvWeather = findViewById(R.id.tvWeather);
        tvNamaKota = findViewById(R.id.tvNamaKota);
        tvTempeatur = findViewById(R.id.tvTempeatur);
        tvKecepatanAngin = findViewById(R.id.tvKecepatanAngin);
        tvKelembaban = findViewById(R.id.tvKelembaban);


        String strNamaKota ="Jakarta";
        String dblTemperatur ="32";
        String strKecepatanAngin = "1.61";
        String strKelembaban = "66";

        if (strDescWeather == "broken clouds") {
            iconTemp.setAnimation(R.raw.broken_clouds);
            tvWeather.setText("Awan Tersebar");
        } else if (strDescWeather == "light rain") {
            iconTemp.setAnimation(R.raw.light_rain);
            tvWeather.setText( "Gerimis");
        } else if (strDescWeather == "haze") {
            iconTemp.setAnimation(R.raw.broken_clouds);
            tvWeather.setText("Berkabut");
        } else if (strDescWeather == "overcast clouds") {
            iconTemp.setAnimation(R.raw.overcast_clouds);
            tvWeather.setText( "Awan Mendung");
        } else if (strDescWeather == "moderate rain") {
            iconTemp.setAnimation(R.raw.moderate_rain);
            tvWeather.setText("Hujan Ringan");
        } else if (strDescWeather == "few clouds") {
            iconTemp.setAnimation(R.raw.few_clouds);
            tvWeather.setText( "Berawan");
        } else if (strDescWeather == "heavy intensity rain") {
            iconTemp.setAnimation(R.raw.heavy_intentsity);
            tvWeather.setText( "Hujan Lebat" );
        } else if (strDescWeather == "clear sky") {
            iconTemp.setAnimation(R.raw.clear_sky);
            tvWeather.setText( "Cerah");
        } else if (strDescWeather == "scattered clouds") {
            iconTemp.setAnimation(R.raw.scattered_clouds);
            tvWeather.setText( "Awan Tersebar");
        } else {
            iconTemp.setAnimation(R.raw.unknown);
            tvWeather.setText( "");
        }

        tvNamaKota.setText( strNamaKota);
        tvTempeatur.setText(dblTemperatur + " \u2103 ");
        tvKecepatanAngin.setText("Kecepatan Angin "+ strKecepatanAngin + "km/j" );
        tvKelembaban.setText( "Kelembaban $strKelembaban %" );

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void recyclerViewWebView() {


        webView = findViewById(R.id.webViewEvent);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings();
        pDialog = new ProgressDialog(DashboardWisatawanActivity.this);
        pDialog.setTitle("Load Event");
        pDialog.setMessage("Loading...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);



        webView.requestFocus();
        webView.getSettings().setJavaScriptEnabled(true);
//        String myPdfUrl = "http://157.245.199.148/~kaffahdev2/event_opening_gunung-dikonversi.pdf";
        String url = "http://www.amanahgitha.com/jenis-jenis-wisata-berdasarkan-tujuan-wisatanya";
//        String myPdfUrl = "https://mindorks.s3.ap-south-1.amazonaws.com/courses/MindOrks_Android_Online_Professional_Course-Syllabus.pdf";
//        String myPdfUrl ="https://yukcoding.blogspot.com";
//        String url = "https://docs.google.com/viewer?embedded=true&url="+myPdfUrl;

//        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(myPdfUrl));
//        startActivity(browserIntent);

        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100) {
                    pDialog.show();
                }
                if (progress == 100) {
                    pDialog.dismiss();
                }
            }
        });


    }

    private void featuredRecycle() {

        featuredRecycle.setHasFixedSize(true);
        featuredRecycle.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));

//        ArrayList<ModelFeatured> modelFeaturedArrayList = new ArrayList<>();
        ArrayList<ModelFeatured> modelFeaturedArrayList = new ArrayList<>();

        modelFeaturedArrayList.add(new ModelFeatured(R.drawable.gede,"Gunung Gede Pangrango","Gunung Gede merupakan sebuah gunung api bertipe stratovolcano yang berada di Pulau Jawa, Indonesia. Gunung Gede berada dalam ruang lingkup Taman Nasional Gede Pangrango, yang merupakan salah satu dari lima taman nasional yang pertama kali diumumkan di Indonesia pada tahun 1980. Gunung ini berada di dua wilayah kabupaten yaitu Kabupaten Cianjur dan Sukabumi, dengan ketinggian 1.000 - 2.958 m. dpl. "));
        modelFeaturedArrayList.add(new ModelFeatured(R.drawable.papandayan,"Gunung Papandayan","Gunung Papandayan adalah gunung api strato yang terletak di Kabupaten Garut, Jawa Barat tepatnya di Kecamatan Cisurupan. Gunung dengan ketinggian 2665 meter di atas permukaan laut itu terletak sekitar 70 km sebelah tenggara Kota Bandung"));
        modelFeaturedArrayList.add(new ModelFeatured(R.drawable.gede,"Gunung Gede Pangrango","Gunung Gede merupakan sebuah gunung api bertipe stratovolcano yang berada di Pulau Jawa, Indonesia. Gunung Gede berada dalam ruang lingkup Taman Nasional Gede Pangrango, yang merupakan salah satu dari lima taman nasional yang pertama kali diumumkan di Indonesia pada tahun 1980. Gunung ini berada di dua wilayah kabupaten yaitu Kabupaten Cianjur dan Sukabumi, dengan ketinggian 1.000 - 2.958 m. dpl, dan berada pada lintang 106°51' - 107°02' BT dan 64°1' - 65°1 LS. Suhu rata-rata di puncak gunung Gede 18 °C dan di malam hari suhu puncak berkisar 5 °C, dengan curah hujan rata-rata 3.600 mm/tahun. Gerbang utama menuju gunung ini adalah dari Cibodas dan Cipanas."));

        customAdapterFeatured = new CustomAdapterFeatured(modelFeaturedArrayList,getApplicationContext());
        featuredRecycle.setAdapter(customAdapterFeatured);


    }


    @Override
    public void onLocationChanged(Location location) {
        double lati = location.getLatitude();
        double longi = location.getLongitude();

        Log.i("","lati= "+ lati);
        Log.i("","longi= "+ longi);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
