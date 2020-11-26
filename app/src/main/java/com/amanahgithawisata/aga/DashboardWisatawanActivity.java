package com.amanahgithawisata.aga;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.airbnb.lottie.LottieAnimationView;
import com.amanahgithawisata.aga.Adapter.CustomAdapterFeatured;
import com.amanahgithawisata.aga.Adapter.SessionManager;
import com.amanahgithawisata.aga.Helper.Help;
import com.amanahgithawisata.aga.Model.ModelFeatured;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.gauravk.bubblenavigation.BubbleNavigationLinearView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DashboardWisatawanActivity extends AppCompatActivity implements LocationListener {

    TextView txt_jljh_wis;
    TextView txt_jljh_wis_dec;
    ShimmerRecyclerView  featuredRecycle,recyclerViewWebView;
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

    @Override
    public void onBackPressed() {

        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_wisatawan);
        SessionManager sessionManager;

        TextView txt_jljh_wis = (TextView) findViewById(R.id.txt_jljh_wis);
        TextView txt_jljh_wis_desc = (TextView) findViewById(R.id.txt_jljh_wis_dec);
//        featuredRecycle = findViewById(R.id.featuredRecycle);
//        featuredRecycle = (ShimmerRecyclerView) findViewById(R.id.featuredRecycle);


        SpannableString content = new SpannableString("Jelajah Wisata");
        SpannableString content_desc = new SpannableString("Jelajah Wisata adalah aplikasi karya anak bangsa yang memuat Lokasi Objek-Objek Wisata resmi yang berada dalam naungan Taman Nasional Indonesia ");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);

        txt_jljh_wis.setText(content);
        txt_jljh_wis_desc.setText(content_desc);


        
        featuredRecycleLokasiWisata();
        recyclerViewWebView();
        getLatlong();




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
                /*
                case 1:
                    Intent a = new Intent(DashboardWisatawanActivity.this, PesanKarcisWisatawanActivity.class);
                    startActivity(a);
                    break;
                case 1:
                    Intent b = new Intent(DashboardWisatawanActivity.this, StatusKarcisWisatawanActivity.class);
                    startActivity(b);
                    break;
                case 3:
                    Intent c = new Intent(DashboardWisatawanActivity.this, EditPasswordWisatawanActivity.class);
                    startActivity(c);
                    break;
                    */
                case 1:
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
                case 2:
                    Intent d = new Intent(DashboardWisatawanActivity.this,ProfileActivity.class);
                    startActivity(d);
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



    private void apiWeather(double lati, double longi, long strDate ) {

        String server_url = "https://api.openweathermap.org/data/2.5/weather?lat="+lati+"&lon="+longi+"&units=metric&appid=8ef3b88b48346e6c2d46df2ce0aea96f";
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        @SuppressLint("SetTextI18n") StringRequest stringRequest = new StringRequest(Request.Method.GET, server_url,
                response -> {
                    Log.i("triono", "response apiWeather= " + response );
                    try {

                        if( Help.isJSONValid(response) ){
                            ArrayList dx = new ArrayList<>();
                            JSONObject JO = new JSONObject(response);


                                JSONArray jsonArrayWeather = JO.getJSONArray("weather");
                                JSONObject jo_main = JO.getJSONObject("main");
                                JSONObject jo_wind = JO.getJSONObject("wind");
                                String coord = JO.getString("coord");
                                String strNamaKota = JO.getString("name");
                                String strKelembaban  = jo_main.getString("humidity");
                                String strTemperatur = jo_main.getString("temp");
                                String strKecepatanAngin = jo_wind.getString("speed");

                                Log.i("", "response jsonArrayWeather= " + jsonArrayWeather );
                                Log.i("", "response coord= " + coord );
                                Log.i("", "strNamaKota= " + strNamaKota );
                                Log.i("", "strKelembaban= " + strKelembaban );
                                Log.i("", "strTemperatur= " + strTemperatur );
                                Log.i("", "strKecepatanAngin= " + strKecepatanAngin );

                                String strMainWeather=null;
                                String strDescWeather=null;
                                String strIconWeather=null;
                                for (int i = 0; i <jsonArrayWeather.length();i++ ) {
                                    JSONObject jsonObject1 = jsonArrayWeather.getJSONObject(i);
                                    strMainWeather = jsonObject1.getString("main");
                                    strDescWeather = jsonObject1.getString("description");
                                    strIconWeather = jsonObject1.getString("icon");
                                }



//                             strDescWeather = "heavy intensity rain";
                            Log.i("","strDescWeather= "+strDescWeather);
                            LottieAnimationView iconTemp ;
                            TextView tvDate, tvWeather,tvNamaKota,tvTempeatur,tvKecepatanAngin,tvKelembaban;

                            iconTemp = findViewById(R.id.iconTemp);
                            tvWeather = findViewById(R.id.tvWeather);
                            tvNamaKota = findViewById(R.id.tvNamaKota);
                            tvTempeatur = findViewById(R.id.tvTempeatur);
                            tvKecepatanAngin = findViewById(R.id.tvKecepatanAngin);
                            tvKelembaban = findViewById(R.id.tvKelembaban);
                            tvDate = findViewById(R.id.tvDate);


//                            String strNamaKota ="Jakarta";
//                            String dblTemperatur ="32";
//                            String strKecepatanAngin = "1.61";
//                            String strKelembaban = "66";

                            if (strDescWeather.equals("broken clouds")) {
                                iconTemp.setAnimation(R.raw.broken_clouds);
                                tvWeather.setText("Awan Tersebar");
                            } else if (strDescWeather.equals("light rain")) {
                                iconTemp.setAnimation(R.raw.light_rain);
                                tvWeather.setText( "Gerimis");
                            } else if (strDescWeather.equals("haze")) {
                                iconTemp.setAnimation(R.raw.broken_clouds);
                                tvWeather.setText("Berkabut");
                            } else if (strDescWeather.equals("overcast clouds")) {
                                iconTemp.setAnimation(R.raw.overcast_clouds);
                                tvWeather.setText( "Awan Mendung");
                            } else if (strDescWeather.equals("moderate rain")) {
                                iconTemp.setAnimation(R.raw.moderate_rain);
                                tvWeather.setText("Hujan Ringan");
                            } else if (strDescWeather.equals("few clouds")) {
                                iconTemp.setAnimation(R.raw.few_clouds);
                                tvWeather.setText( "Berawan");
                            } else if (strDescWeather.equals("heavy intensity rain")) {
                                iconTemp.setAnimation(R.raw.heavy_intentsity);
                                tvWeather.setText( "Hujan Lebat" );
                            } else if (strDescWeather.equals("clear sky")) {
                                iconTemp.setAnimation(R.raw.clear_sky);
                                tvWeather.setText( "Cerah");
                            } else if (strDescWeather.equals("scattered clouds")) {
                                iconTemp.setAnimation(R.raw.scattered_clouds);
                                tvWeather.setText( "Awan Tersebar");
                            } else {
                                iconTemp.setAnimation(R.raw.unknown);
                                tvWeather.setText( "else");
                            }

                            tvNamaKota.setText( strNamaKota);
                            tvTempeatur.setText(strTemperatur+ " \u2103 ");
                            tvKecepatanAngin.setText("Kecepatan Angin "+ strKecepatanAngin + "km/j" );
                            tvKelembaban.setText( "Kelembaban "+ strKelembaban+ "%" );
                            Date tgl = new Date(strDate);
//                            tvDate.setText(DateFormat.getDateInstance(DateFormat.FULL).format(tgl));
                            tvDate.setText(getToday());




//                                    CustomAdapterEntityWisatawan customAdapter = new CustomAdapterEntityWisatawan( entityStatusKarcisArrayList, DashboardWisatawanOLdActivity.this);
//                                    recyclerView.setAdapter(customAdapter);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    requestQueue.stop();
                }, error -> {
            Log.i("", "response =" + error.toString());
            error.printStackTrace();
            requestQueue.stop();

        }
        ) ;
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private String getToday() {
        Date date = Calendar.getInstance().getTime();
        return new SimpleDateFormat("EEEE, dd MMMM yyyy",new java.util.Locale("id")).format(date);
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



//        webView.requestFocus();
//        webView.getSettings().setJavaScriptEnabled(true);
//        String url = "http://www.amanahgitha.com/jenis-jenis-wisata-berdasarkan-tujuan-wisatanya";
//        webView.loadUrl(url);
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//        });
//        webView.setWebChromeClient(new WebChromeClient() {
//            public void onProgressChanged(WebView view, int progress) {
//                if (progress < 100) {
//                    pDialog.show();
//                }
//                if (progress == 100) {
//                    pDialog.dismiss();
//                }
//            }
//        });


    }

    private void featuredRecycleLokasiWisata() {
        featuredRecycle = (ShimmerRecyclerView) findViewById(R.id.featuredRecycle);
        featuredRecycle.setHasFixedSize(true);
        featuredRecycle.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));

//        ArrayList<ModelFeatured> modelFeaturedArrayList = new ArrayList<>();
        ArrayList<ModelFeatured> modelFeaturedArrayList = new ArrayList<>();

        modelFeaturedArrayList.add(new ModelFeatured(R.drawable.gede,"Gunung Gede Pangrango","Gunung Gede merupakan sebuah gunung api bertipe stratovolcano yang berada di Pulau Jawa, Indonesia. Gunung Gede berada dalam ruang lingkup Taman Nasional Gede Pangrango, yang merupakan salah satu dari lima taman nasional yang pertama kali diumumkan di Indonesia pada tahun 1980. Gunung ini berada di dua wilayah kabupaten yaitu Kabupaten Cianjur dan Sukabumi, dengan ketinggian 1.000 - 2.958 m. dpl. "));
        modelFeaturedArrayList.add(new ModelFeatured(R.drawable.papandayan,"Gunung Papandayan","Gunung Papandayan adalah gunung api strato yang terletak di Kabupaten Garut, Jawa Barat tepatnya di Kecamatan Cisurupan. Gunung dengan ketinggian 2665 meter di atas permukaan laut itu terletak sekitar 70 km sebelah tenggara Kota Bandung"));
        modelFeaturedArrayList.add(new ModelFeatured(R.drawable.gede,"Gunung Gede Pangrango","Gunung Gede merupakan sebuah gunung api bertipe stratovolcano yang berada di Pulau Jawa, Indonesia. Gunung Gede berada dalam ruang lingkup Taman Nasional Gede Pangrango, yang merupakan salah satu dari lima taman nasional yang pertama kali diumumkan di Indonesia pada tahun 1980. Gunung ini berada di dua wilayah kabupaten yaitu Kabupaten Cianjur dan Sukabumi, dengan ketinggian 1.000 - 2.958 m. dpl, dan berada pada lintang 106°51' - 107°02' BT dan 64°1' - 65°1 LS. Suhu rata-rata di puncak gunung Gede 18 °C dan di malam hari suhu puncak berkisar 5 °C, dengan curah hujan rata-rata 3.600 mm/tahun. Gerbang utama menuju gunung ini adalah dari Cibodas dan Cipanas."));

        customAdapterFeatured = new CustomAdapterFeatured(modelFeaturedArrayList,getApplicationContext());

//        featuredRecycle.showShimmerAdapter();
        featuredRecycle.setAdapter(customAdapterFeatured);
    }


    private void getLatlong() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{(Manifest.permission.ACCESS_FINE_LOCATION)}, 115);
            return;
        }
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(provider);

        Log.i("","location ="+location);

        if (location != null) {
            onLocationChanged(location);
        } else {
            locationManager.requestLocationUpdates(provider, 20000, 0f, this);
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        double lati = location.getLatitude();
        double longi = location.getLongitude();


        Log.i("","lati= "+ lati);
        Log.i("","longi= "+ longi);

        apiWeather(lati,longi,location.getTime());
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
