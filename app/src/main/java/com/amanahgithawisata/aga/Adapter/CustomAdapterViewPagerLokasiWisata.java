package com.amanahgithawisata.aga.Adapter;

import android.annotation.SuppressLint;
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

import com.amanahgithawisata.aga.Model.ModelHorizontalScrollLokasiWisata;
import com.amanahgithawisata.aga.PopUpActivity;
import com.amanahgithawisata.aga.R;

import java.util.ArrayList;
import java.util.List;


public class CustomAdapterViewPagerLokasiWisata extends PagerAdapter implements SharedPreferences.OnSharedPreferenceChangeListener {

    public SessionManager sessionManager;

    public List<ModelHorizontalScrollLokasiWisata> modelHorizontalScrollLokasiWisatas;
    public LayoutInflater layoutInflater;

    public TextView a1, b1;
    public TextView a2, b2;
//    public ViewPager pagerViewLokasiPintu;


    public Context context;

    public CustomAdapterViewPagerLokasiWisata() {
        modelHorizontalScrollLokasiWisatas = new ArrayList<>();
    }

    public void addCardItem(ModelHorizontalScrollLokasiWisata item) {
        modelHorizontalScrollLokasiWisatas.add(item);
    }
    public void clearCardItem() {
        modelHorizontalScrollLokasiWisatas.clear();
    }

    @Override
    public int getCount() {

        return modelHorizontalScrollLokasiWisatas.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {


//        LayoutInflater li = LayoutInflater.from(view.getContext());
//        view = li.inflate(R.layout.card_lokasi_wisata,view,false);
//
//        pagerViewLokasiPintu = view.findViewById(R.id.pagerLokasiPintu);

        return view.equals(object);
    }

    @SuppressLint("ResourceType")
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup parent, int position) {
//        return super.instantiateItem(container, position);

        SessionManager sessionManager ;
        sessionManager = new SessionManager(context.getApplicationContext());

        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View view = li.inflate(R.layout.card_lokasi_wisata,parent,false);


        TextView judul = view.findViewById(R.id.tv_judul);
        TextView text = view.findViewById(R.id.tv_text);
        TextView kd_lok_old_lp = view.findViewById(R.id.tv_kd_lok_old_lp);
        Button btnH = view.findViewById(R.id.buttonH);

        judul.setText(modelHorizontalScrollLokasiWisatas.get(position).getJudul());
        text.setText(modelHorizontalScrollLokasiWisatas.get(position).getText());
//        kd_lok_old_lp.setText(modelHorizontalScrollLokasiWisatas.get(position).getKdLokOld());

        final String _judul = modelHorizontalScrollLokasiWisatas.get(position).getJudul();
        final String _text = modelHorizontalScrollLokasiWisatas.get(position).getJudul();
//        final String _kdLokOld = modelHorizontalScrollLokasiWisatas.get(position).getKdLokOld();

        btnH.setOnClickListener(v -> {

                    Toast.makeText(v.getContext(), "btnH CustomAdapter is clicked. " + _judul.toString(), Toast.LENGTH_LONG).show();
//                    Log.e("","_judul= "+_judul.toString());



                Intent i = new Intent(v.getContext(), PopUpActivity.class);
                i.putExtra("result_dt_judul", _judul);
                i.putExtra("result_dt_text",_text );
                i.putExtra("result_dt_berhasil", true);
                i.putExtra("result_dt_flag", "flagHorizontalLokWis");
                parent.getContext().startActivity(i);

//            PesanKarcisWisatawanActivity xx  = new PesanKarcisWisatawanActivity();
//            xx.horizontalLokasiPintu("daftar_lokasi_pintu",_judul,v.getContext());

                });



        parent.addView(view);
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
