package com.amanahgithawisata.aga.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.amanahgithawisata.aga.Model.ModelHorizontalScrollLokasiPintu;
import com.amanahgithawisata.aga.PopUpPintuActivity;
import com.amanahgithawisata.aga.R;

import java.util.ArrayList;
import java.util.List;


public class CustomAdapterViewPagerLokasiPintu extends PagerAdapter implements SharedPreferences.OnSharedPreferenceChangeListener {

    SessionManager sessionManager;


    public List<ModelHorizontalScrollLokasiPintu> modelHorizontalScrollLokasiPintus;
    public LayoutInflater layoutInflater;

//    public CustomAdapterViewPagerLokasiWisata() {
//        this.spinnerListWisataKsdas = spinnerListWisataKsdas;
//        this.context = context;
//    }

    public Context context;


    public CustomAdapterViewPagerLokasiPintu() {
        modelHorizontalScrollLokasiPintus = new ArrayList<>();
    }

    public void addCardItem(ModelHorizontalScrollLokasiPintu item) {
        modelHorizontalScrollLokasiPintus.add(item);
    }
    public void clearCardItem() {
        modelHorizontalScrollLokasiPintus.clear();
    }

    @Override
    public int getCount() {

        return modelHorizontalScrollLokasiPintus.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
//        return super.instantiateItem(container, position);
//        layoutInflater=LayoutInflater.from(context);
//        View view= layoutInflater.inflate(R.layout.activity_pesan_karcis_wisatawan_old,container, false);
//        ImageView _imageView;
//        _imageView = view.findViewById(R.id.img_utama);
//        _imageView.setImageResource(spinnerListWisataKsdas.get(position).getImage());



        LayoutInflater li = LayoutInflater.from(container.getContext());
        View view = li.inflate(R.layout.card_lokasi_pintu,container,false);

        TextView judul = view.findViewById(R.id.tv_judul_lp);
        TextView text = view.findViewById(R.id.tv_text_lp);
        TextView kd_lok_old_lp = view.findViewById(R.id.tv_kd_lok_old_lp);

        Button btnH = view.findViewById(R.id.buttonH_lp);

        judul.setText(modelHorizontalScrollLokasiPintus.get(position).getJudul());
        text.setText(modelHorizontalScrollLokasiPintus.get(position).getText());
        kd_lok_old_lp.setText(modelHorizontalScrollLokasiPintus.get(position).getKdLokOld());

        final String _judulnew = modelHorizontalScrollLokasiPintus.get(position).getJudul();
        final String _text = modelHorizontalScrollLokasiPintus.get(position).getText();
        final String _kdLokOld = modelHorizontalScrollLokasiPintus.get(position).getKdLokOld();

        btnH.setOnClickListener(v -> {
//            sessionManager = new SessionManager(context.getApplicationContext());
            Toast.makeText(v.getContext(),"btnH is clicked. "+_judulnew.toString(), Toast.LENGTH_LONG).show();
                    Log.e("","_kdLokOld= "+ _kdLokOld);
            Log.e("","_judulnew= "+ _judulnew);
//            sessionManager.createSessionLokWisPesankarcisWisatawanKsda(_judulnew,_text);

//            sessionManager.createSessionForHorizontalPintu(_judulnew,_text);

            Intent i = new Intent(v.getContext(), PopUpPintuActivity.class);
            i.putExtra("result_dt_judul_pintu", _kdLokOld);
            i.putExtra("result_dt_judul", _judulnew);
            i.putExtra("result_dt_text_pintu",_text );
            i.putExtra("result_dt_berhasil", true);
            i.putExtra("result_dt_flag", "flag_kd_lok_old_lp");
            container.getContext().startActivity(i);

        });


        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.i("","keyx= "+key);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
//        return super.getItemPosition(object);
        return POSITION_NONE;
    }





}
