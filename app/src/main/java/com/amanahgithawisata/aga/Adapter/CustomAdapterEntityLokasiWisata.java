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

import com.amanahgithawisata.aga.Model.ModelHorizontalScrollLokasiWisata;
import com.amanahgithawisata.aga.PesanKarcisWisatawanActivity;
import com.amanahgithawisata.aga.R;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

public class CustomAdapterEntityLokasiWisata extends RecyclerView.Adapter<CustomAdapterEntityLokasiWisata.MyViewHolder> {
    ArrayList<ModelHorizontalScrollLokasiWisata> modelHorizontalScrollLokasiWisatas;
    Context context;

    public CustomAdapterEntityLokasiWisata(ArrayList<ModelHorizontalScrollLokasiWisata> modelHorizontalScrollLokasiWisatas, Context context) {
        this.modelHorizontalScrollLokasiWisatas = modelHorizontalScrollLokasiWisatas;
        this.context = context;

    }



    public static  class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_judul;
        TextView tv_text;
        TextView tv_almt;
        TextView tv_kota;
        TextView tv_url_img;
        ImageView tv_img;
        Button _btn_entity_edit;
        TextView tv_tgl_kunj_lokwis;



        @SuppressLint("WrongViewCast")
        MyViewHolder(View itemView) {
            super(itemView);
            this.tv_judul=(TextView) itemView.findViewById(R.id.tv_judul_card_lokasi_wisata);
            this.tv_text=(TextView) itemView.findViewById(R.id.tv_text_card_lokasi_wisata);
            this.tv_almt=(TextView) itemView.findViewById(R.id.tv_almt_card_lokasi_wisata);
            this.tv_kota=(TextView) itemView.findViewById(R.id.tv_kota_card_lokasi_wisata);

            this.tv_url_img  =(TextView) itemView.findViewById(R.id.tv_url_card_lokasi_wisata);
            this.tv_img=(ImageView) itemView.findViewById(R.id._tv_img_card_lokasi_wisata);
            this._btn_entity_edit = (Button) itemView.findViewById(R.id.buttonH_card_lokasi_wisata);
            this.tv_tgl_kunj_lokwis =  itemView.findViewById(R.id.tv_tgl_kunj_lokwis);

        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View view = li.inflate(R.layout.entity_card_lokasi_wisata,parent,false);
        MyViewHolder myViewHolder = new CustomAdapterEntityLokasiWisata.MyViewHolder(view);
        return myViewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        TextView tv_judulx = holder.tv_judul;
        TextView tv_textx= holder.tv_text;
        TextView tv_almtx= holder.tv_almt;
        TextView tv_kotax= holder.tv_kota;
        TextView tv_url_imgx= holder.tv_url_img;
        ImageView tv_imgx= holder.tv_img;
        Button btn_entity_edit = holder._btn_entity_edit;
        TextView tv_tgl_kunj_lokwisx= holder.tv_tgl_kunj_lokwis;

        SessionManager sessionManager ;
        sessionManager = new SessionManager(context.getApplicationContext());

        tv_tgl_kunj_lokwisx.setVisibility(View.GONE);

//        tv_url_img_kux.setVisibility(View.GONE);

        final String _judul = modelHorizontalScrollLokasiWisatas.get(position).getJudul();
        final String _text = modelHorizontalScrollLokasiWisatas.get(position).getText();
        final String _almt = modelHorizontalScrollLokasiWisatas.get(position).getAlmt();
        final String _kota = modelHorizontalScrollLokasiWisatas.get(position).getKota();
        final String _url_img = modelHorizontalScrollLokasiWisatas.get(position).getUrl_gambar();
        final String _tgl_kunj_lokwis = modelHorizontalScrollLokasiWisatas.get(position).getTgl_kunj_lokwis();

        final String txt_kdlokPintu = modelHorizontalScrollLokasiWisatas.get(position).getTxt_kdlokPintu();
        final String txt_kdlokWis = modelHorizontalScrollLokasiWisatas.get(position).getTxt_kdlokWis();
        final String txt_nmlokPintux = modelHorizontalScrollLokasiWisatas.get(position).getTxt_nmlokPintux();
        final String txt_url_img_lokwisOld = modelHorizontalScrollLokasiWisatas.get(position).getTxt_url_img_lokwisOld();
        final String txt_url_img_lokPintu = modelHorizontalScrollLokasiWisatas.get(position).getTxt_url_img_lokPintu();
        final String result_dt_jml_karcis_wisnu = modelHorizontalScrollLokasiWisatas.get(position).getResult_dt_jml_karcis_wisnu();
        final String result_dt_jml_karcis_wisman = modelHorizontalScrollLokasiWisatas.get(position).getResult_dt_jml_karcis_wisman();
        final String result_dt_jml_karcis_tmbhn = modelHorizontalScrollLokasiWisatas.get(position).getResult_dt_jml_karcis_tmbhn();
        final String result_dt_ttl_karcis_tmbhn = modelHorizontalScrollLokasiWisatas.get(position).getResult_dt_ttl_karcis_tmbhn();
        final String result_dt_grand_ttl = modelHorizontalScrollLokasiWisatas.get(position).getResult_dt_grand_ttl();
        final String txt_harga_karcis_wisata_tmbhn = modelHorizontalScrollLokasiWisatas.get(position).getTxt_harga_karcis_wisata_tmbhn();
        final String txt_id_karcis_utama = modelHorizontalScrollLokasiWisatas.get(position).getTxt_id_karcis_utama();
        final String txt_id_karcis_tmbhn = modelHorizontalScrollLokasiWisatas.get(position).getTxt_id_karcis_tmbhn();


        tv_judulx.setText(modelHorizontalScrollLokasiWisatas.get(position).judul);
        tv_textx.setText(modelHorizontalScrollLokasiWisatas.get(position).text);
        tv_url_imgx.setText(modelHorizontalScrollLokasiWisatas.get(position).url_gambar);
        tv_tgl_kunj_lokwisx.setText(modelHorizontalScrollLokasiWisatas.get(position).tgl_kunj_lokwis);
        tv_almtx.setText(modelHorizontalScrollLokasiWisatas.get(position).almt);
        tv_kotax.setText(modelHorizontalScrollLokasiWisatas.get(position).kota);






        Transformation transformation = new RoundedTransformationBuilder()
//                                        .borderColor()
//                                        .borderWidthDp(3)
                                        .oval(false)
                                        .build();

        Picasso.with(context.getApplicationContext())
                .load(_url_img)
                .fit()
                .placeholder(R.drawable.ic_image)
                .transform(transformation)
                .into(tv_imgx);


        btn_entity_edit.setOnClickListener(v -> {


            Log.i("","_url_img adpter="+_url_img);
            Log.i("","_kota adpter="+_kota);
            Log.i("","_judul adpter="+_judul);
            Log.i("","_text adpter="+_text);

            Log.i("","result_dt_jml_karcis_wisnu adpter="+result_dt_jml_karcis_wisnu);

            final String _email =  sessionManager.getUserDetail().get(SessionManager.key_email);

            Intent i = new Intent(v.getContext(), PesanKarcisWisatawanActivity.class);
            i.putExtra("result_dt_kd_lokwis_adapter", _judul);
            i.putExtra("result_dt_nm_lokwis_adapter",_text );
            i.putExtra("result_dt_almt_adapter",_almt );
            i.putExtra("result_dt_kota_adapter",_kota );
            i.putExtra("result_dt_url_img_lokwis_adapter", _url_img );
            i.putExtra("result_dt_tgl_kunj_lokwis_adapter", _tgl_kunj_lokwis );


            i.putExtra("txt_kdlokPintu",txt_kdlokPintu);
            i.putExtra("txt_kdlokWis",txt_kdlokWis);
            i.putExtra("txt_nmlokPintux",txt_nmlokPintux);
            i.putExtra("txt_url_img_lokwisOld",txt_url_img_lokwisOld);
            i.putExtra("txt_url_img_lokPintu",txt_url_img_lokPintu);
            i.putExtra("result_dt_jml_karcis_wisnu",result_dt_jml_karcis_wisnu);
            i.putExtra("result_dt_jml_karcis_wisman",result_dt_jml_karcis_wisman);
            i.putExtra("result_dt_jml_karcis_tmbhn",result_dt_jml_karcis_tmbhn);
            i.putExtra("result_dt_ttl_karcis_tmbhn",result_dt_ttl_karcis_tmbhn);
            i.putExtra("result_dt_grand_ttl",result_dt_grand_ttl);
            i.putExtra("txt_harga_karcis_wisata_tmbhn",txt_harga_karcis_wisata_tmbhn);
            i.putExtra("txt_id_karcis_utama",txt_id_karcis_utama);
            i.putExtra("txt_id_karcis_tmbhn",txt_id_karcis_tmbhn);

            sessionManager.createSessionLokWisPesankarcisWisatawan(_judul,_text,_almt,_kota,_url_img);

            v.getContext().startActivity(i);

        });




    }



    @Override
    public int getItemCount() {
        return modelHorizontalScrollLokasiWisatas.size();
    }


    public void setFilter(ArrayList<ModelHorizontalScrollLokasiWisata> filterList){
        modelHorizontalScrollLokasiWisatas = new ArrayList<>();
        modelHorizontalScrollLokasiWisatas.addAll(filterList);
        notifyDataSetChanged();
    }
}


