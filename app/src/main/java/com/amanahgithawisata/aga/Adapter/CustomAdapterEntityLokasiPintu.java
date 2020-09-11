package com.amanahgithawisata.aga.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amanahgithawisata.aga.Model.ModelHorizontalScrollLokasiPintu;
import com.amanahgithawisata.aga.PesanKarcisWisatawanActivity;
import com.amanahgithawisata.aga.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapterEntityLokasiPintu extends RecyclerView.Adapter<CustomAdapterEntityLokasiPintu.MyViewHolder> {
    ArrayList<ModelHorizontalScrollLokasiPintu> modelHorizontalScrollLokasiPintus;
    Context context;

    public CustomAdapterEntityLokasiPintu(ArrayList<ModelHorizontalScrollLokasiPintu> modelHorizontalScrollLokasiPintus, Context context) {
        this.modelHorizontalScrollLokasiPintus = modelHorizontalScrollLokasiPintus;
        this.context = context;
    }



    public static  class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_judul;
        TextView tv_text;
        TextView tv_almt;
        TextView tv_kota;
        TextView tv_kd_old;
        TextView tv_nm_old;
        TextView tv_url_img_pintu;
        TextView tv_url_img_wisataOld;
        ImageView tv_img_pintu;
        ImageView tv_img_lokWisOld;
        Button _btn_entity_edit;
        TextView tv_tgl_kunj_pintu;
        TextView url_img1;

        MyViewHolder( View itemView) {
            super(itemView);
            this.tv_judul=(TextView) itemView.findViewById(R.id.tv_judul_card_lokasi_pintu);
            this.tv_text=(TextView) itemView.findViewById(R.id.tv_text_card_lokasi_pintu);
            this.tv_almt=(TextView) itemView.findViewById(R.id.tv_almt_card_lokasi_pintu);
            this.tv_kota=(TextView) itemView.findViewById(R.id.tv_kota_card_lokasi_pintu);
            this.tv_kd_old = (TextView) itemView.findViewById(R.id.tv_kdLokOld_card_lokasi_pintu);
            this.tv_nm_old = (TextView) itemView.findViewById(R.id.tv_nmLokOld_card_lokasi_wisata);
            this.tv_tgl_kunj_pintu = (TextView) itemView.findViewById(R.id.tv_tgl_kunj_pintu);


            this.tv_url_img_pintu  =(TextView) itemView.findViewById(R.id.tv_url_card_lokasi_pintu);
            this.tv_url_img_wisataOld  =(TextView) itemView.findViewById(R.id.tv_url_card_lokasi_wisata_old);
            this.tv_img_pintu=(ImageView) itemView.findViewById(R.id._tv_img_card_lokasi_pintu);

            this._btn_entity_edit = (Button) itemView.findViewById(R.id.buttonH_card_lokasi_pintu);
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View view = li.inflate(R.layout.entity_card_lokasi_pintu,parent,false);
        MyViewHolder myViewHolder = new CustomAdapterEntityLokasiPintu.MyViewHolder(view);
        return myViewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        TextView tv_judulx = holder.tv_judul;
        TextView tv_textx= holder.tv_text;
        TextView tv_almtx= holder.tv_almt;
        TextView tv_kotax= holder.tv_kota;
        TextView tv_kdOld = holder.tv_kd_old;
        TextView tv_nmOld = holder.tv_nm_old;
        TextView tv_url_img_pintux = holder.tv_url_img_pintu;
        TextView tv_url_img_wisataOldx = holder.tv_url_img_wisataOld;
        ImageView tv_img_pintux = holder.tv_img_pintu;
        Button btn_entity_edit = holder._btn_entity_edit;
        TextView tv_tgl_kunj_pintux = holder.tv_tgl_kunj_pintu;


        SessionManager sessionManager ;
        sessionManager = new SessionManager(context.getApplicationContext());

        tv_almtx.setVisibility(View.GONE);
        tv_kotax.setVisibility(View.GONE);
        tv_kdOld.setVisibility(View.GONE);
        tv_nmOld.setVisibility(View.GONE);
        tv_url_img_pintux.setVisibility(View.GONE);
        tv_url_img_wisataOldx.setVisibility(View.GONE);
        tv_tgl_kunj_pintux.setVisibility(View.GONE);


        final String _judul = modelHorizontalScrollLokasiPintus.get(position).getJudul();
        final String _text = modelHorizontalScrollLokasiPintus.get(position).getText();
        final String _kdOld = modelHorizontalScrollLokasiPintus.get(position).getKdLokOld();
        final String _NmOld = modelHorizontalScrollLokasiPintus.get(position).getNmLokOld();
        final String _url_img_pintu = modelHorizontalScrollLokasiPintus.get(position).getUrl_img_pintu();
        final String _url_img_lokPintu = modelHorizontalScrollLokasiPintus.get(position).getResult_dt_url_img_lokPintu();
        final String _tgl_kunj_pintux = modelHorizontalScrollLokasiPintus.get(position).getTgl_kunj_pintu();
        final String _url_img_lokWis = modelHorizontalScrollLokasiPintus.get(position).getResult_dt_url_img_lokwis();

        final String result_jml_karcis_wisnu = modelHorizontalScrollLokasiPintus.get(position).getResult_jml_karcis_wisnu();
        final String result_jml_karcis_wisman = modelHorizontalScrollLokasiPintus.get(position).getResult_jml_karcis_wisman();
        final String result_jml_karcis_tmbhn = modelHorizontalScrollLokasiPintus.get(position).getResult_jml_karcis_tmbhn();
        final String result_ttl_wisnu_wisman = modelHorizontalScrollLokasiPintus.get(position).getResult_ttl_wisnu_wisman();
        final String result_ttl_karcis_tmbhn = modelHorizontalScrollLokasiPintus.get(position).getResult_ttl_karcis_tmbhn();
        final String result_grand_ttl = modelHorizontalScrollLokasiPintus.get(position).getResult_grand_ttl();
        final String txt_harga_karcis_wisata_tmbhn = modelHorizontalScrollLokasiPintus.get(position).getTxt_harga_karcis_wisata_tmbhn();


        tv_judulx.setText(modelHorizontalScrollLokasiPintus.get(position).judul);
        tv_textx.setText(modelHorizontalScrollLokasiPintus.get(position).text);
        tv_kdOld.setText(modelHorizontalScrollLokasiPintus.get(position).KdLokOld);;
        tv_nmOld.setText(modelHorizontalScrollLokasiPintus.get(position).NmLokOld);;
        tv_url_img_pintux.setText(modelHorizontalScrollLokasiPintus.get(position).url_img_pintu);
//        tv_url_img_wisataOldx.setText(modelHorizontalScrollLokasiPintus.get(position).url_img_lokWisOld);
        tv_tgl_kunj_pintux.setText(modelHorizontalScrollLokasiPintus.get(position).tgl_kunj_pintu);


        Picasso.with( context )
                .load( _url_img_pintu )
                .error(R.mipmap.ic_launcher)
                .resize(1900,600)
                .centerCrop()
                .into(tv_img_pintux);




        Log.i("","_url_img_lokWis "+_url_img_lokWis);



        btn_entity_edit.setOnClickListener(v -> {
//                Snackbar.make(v, "Clicked element ", Snackbar.LENGTH_LONG).show();

            final String _email =  sessionManager.getUserDetail().get(SessionManager.key_email);

            Log.i("","_judulx pintu"+_judul);


            Intent i = new Intent(v.getContext(), PesanKarcisWisatawanActivity.class);
            i.putExtra("result_dt_kdlokPintu2", _judul);
            i.putExtra("result_dt_textlokPintu2",_text );
            i.putExtra("result_dt_kdlokOldPintu2",_kdOld);
            i.putExtra("result_dt_nmlokOldPintu2",_NmOld);
            i.putExtra("result_dt_url_img_pintu_fromAdapterLokPintu",_url_img_pintu);
            i.putExtra("result_dt_url_img_lokWisOld_fromAdapterLokPintu",_url_img_lokWis);
            i.putExtra("result_dt_tgl_kunj_pintux",_tgl_kunj_pintux);

            i.putExtra("result_jml_karcis_wisnu_lp",result_jml_karcis_wisnu);
            i.putExtra("result_jml_karcis_wisman_lp",result_jml_karcis_wisman);
            i.putExtra("result_jml_karcis_tmbhn_lp",result_jml_karcis_tmbhn);

            i.putExtra("result_ttl_wisnu_wisman_lp",result_ttl_wisnu_wisman);
            i.putExtra("result_ttl_karcis_tmbhn_lp",result_ttl_karcis_tmbhn);
            i.putExtra("result_grand_ttl_lp",result_grand_ttl);
            i.putExtra("txt_harga_karcis_wisata_tmbhn_lp",txt_harga_karcis_wisata_tmbhn);


            sessionManager.createSessionLokPintuPesankarcisWisatawan(_judul,_text,_url_img_pintu);

            v.getContext().startActivity(i);


        });
    }



    @Override
    public int getItemCount() {
        return modelHorizontalScrollLokasiPintus.size();
    }
}


