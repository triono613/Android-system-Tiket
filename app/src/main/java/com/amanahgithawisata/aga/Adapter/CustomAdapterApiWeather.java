package com.amanahgithawisata.aga.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.amanahgithawisata.aga.Helper.Help;
import com.amanahgithawisata.aga.Model.ModelApiWeather;
import com.amanahgithawisata.aga.R;

import java.util.ArrayList;

public class CustomAdapterApiWeather extends RecyclerView.Adapter<CustomAdapterApiWeather.MyViewHolder> {
    ArrayList<ModelApiWeather> modelApiWeatherArrayList;
    Context context;

    public CustomAdapterApiWeather(ArrayList<ModelApiWeather> modelApiWeatherArrayList, Context context) {
        this.modelApiWeatherArrayList = modelApiWeatherArrayList;
        this.context = context;
    }


    public static  class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_Weather,tv_NamaKota,tv_Tempeatur,tv_KecepatanAngin,tv_Kelembaban,tv_Date,tv_txt_lbl_cuaca;
        LottieAnimationView tv_iconTemp ;

        @SuppressLint("WrongViewCast")
        MyViewHolder(View itemView) {
            super(itemView);

            this.tv_iconTemp= (LottieAnimationView) itemView.findViewById(R.id.iconTemp);
            this.tv_Weather=(TextView) itemView.findViewById(R.id.tvWeather);
            this.tv_NamaKota=(TextView) itemView.findViewById(R.id.tvNamaKota);
            this.tv_Tempeatur=(TextView) itemView.findViewById(R.id.tvTempeatur);
            this.tv_KecepatanAngin=(TextView) itemView.findViewById(R.id.tvKecepatanAngin);
            this.tv_Kelembaban=(TextView) itemView.findViewById(R.id.tvKelembaban);
            this.tv_Date=(TextView) itemView.findViewById(R.id.tvDate);
            this.tv_txt_lbl_cuaca=(TextView) itemView.findViewById(R.id.txt_lbl_cuaca);


//            TextView tv = (TextView) itemView.findViewById(R.id.featured_title);
//            SpannableString content = new SpannableString("Content");
//            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
//            tv.setText(content);
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View view = li.inflate(R.layout.featured_api_weather,parent,false);
        MyViewHolder myViewHolder = new CustomAdapterApiWeather.MyViewHolder(view);
        return myViewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        LottieAnimationView tv_iconTemp = holder.tv_iconTemp;
        TextView tv_Weather= holder.tv_Weather;
        TextView tv_NamaKota= holder.tv_NamaKota;
        TextView tv_Tempeatur= holder.tv_Tempeatur;
        TextView tv_KecepatanAngin= holder.tv_KecepatanAngin;
        TextView tv_Kelembaban= holder.tv_Kelembaban;
        TextView tv_Date= holder.tv_Date;
        TextView tv_txt_lbl_cuaca= holder.tv_txt_lbl_cuaca;

        SessionManager sessionManager ;
        sessionManager = new SessionManager(context.getApplicationContext());

//        SpannableString content = new SpannableString(modelApiWeatherArrayList.get(position).getTitle());
//        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
//        tv_titlex.setText(content);

        final String strDescWeather = modelApiWeatherArrayList.get(position).weather;


        Log.i("","strDescWeather adapter"+strDescWeather);
        Log.i("","strDescWeather adapter get"+modelApiWeatherArrayList.get(position).getWeather());

                            switch (strDescWeather) {
                                case "broken clouds":
                                    tv_iconTemp.setAnimation(R.raw.broken_clouds);
                                    tv_Weather.setText("Awan Tersebar");
                                    break;
                                case "light rain":
                                    tv_iconTemp.setAnimation(R.raw.light_rain);
                                    tv_Weather.setText("Gerimis");
                                    break;
                                case "haze":
                                    tv_iconTemp.setAnimation(R.raw.broken_clouds);
                                    tv_Weather.setText("Berkabut");
                                    break;
                                case "overcast clouds":
                                    tv_iconTemp.setAnimation(R.raw.overcast_clouds);
                                    tv_Weather.setText("Awan Mendung");
                                    break;
                                case "moderate rain":
                                    tv_iconTemp.setAnimation(R.raw.moderate_rain);
                                    tv_Weather.setText("Hujan Ringan");
                                    break;
                                case "few clouds":
                                    tv_iconTemp.setAnimation(R.raw.few_clouds);
                                    tv_Weather.setText("Berawan");
                                    break;
                                case "heavy intensity rain":
                                    tv_iconTemp.setAnimation(R.raw.heavy_intentsity);
                                    tv_Weather.setText("Hujan Lebat");
                                    break;
                                case "clear sky":
                                    tv_iconTemp.setAnimation(R.raw.clear_sky);
                                    tv_Weather.setText("Cerah");
                                    break;
                                case "scattered clouds":
                                    tv_iconTemp.setAnimation(R.raw.scattered_clouds);
                                    tv_Weather.setText("Awan Tersebar");
                                    break;
                                default:
                                    tv_iconTemp.setAnimation(R.raw.unknown);
                                    tv_Weather.setText("else");
                                    break;
                            }

                        tv_txt_lbl_cuaca.setText(modelApiWeatherArrayList.get(position).getCoord());
                        tv_Weather.setText(modelApiWeatherArrayList.get(position).getWeather());
                        tv_NamaKota.setText(modelApiWeatherArrayList.get(position).getNamaKota());
                        tv_Tempeatur.setText(modelApiWeatherArrayList.get(position).getTemperatur() + " \u2103 ");
                        tv_KecepatanAngin.setText("Kecepatan Angin "+ modelApiWeatherArrayList.get(position).getKecepatanAngin() + "km/j" );
                        tv_Kelembaban.setText( "Kelembaban "+ modelApiWeatherArrayList.get(position).getKelembaban() + "%" );
                        tv_Date.setText(Help.getToday());

    }



    @Override
    public int getItemCount() {
        return modelApiWeatherArrayList.size();
    }


}


