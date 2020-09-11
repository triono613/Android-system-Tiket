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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amanahgithawisata.aga.Model.ModelHorizontalScrollKarcisUtama;
import com.amanahgithawisata.aga.PesanKarcisWisatawanActivity;
import com.amanahgithawisata.aga.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapterEntityKarcisUtama extends RecyclerView.Adapter<CustomAdapterEntityKarcisUtama.MyViewHolder> {
    ArrayList<ModelHorizontalScrollKarcisUtama> modelHorizontalScrollKarcisUtamas;
    Context context;

    public CustomAdapterEntityKarcisUtama(ArrayList<ModelHorizontalScrollKarcisUtama> modelHorizontalScrollKarcisUtamas, Context context) {
        this.modelHorizontalScrollKarcisUtamas = modelHorizontalScrollKarcisUtamas;
        this.context = context;
    }



    public static  class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_tgl_kunj;
        TextView tv_kode_ksda;
        TextView tv_nm_ksda;
        TextView tv_kode_lokasi;
        TextView tv_nm_lokasi;
        TextView tv_kode_karcis;
        TextView tv_nama_karcis;
        TextView tv_kode_libur;
        TextView tv_harga_karcis_wisata_wisnu;
        TextView tv_harga_karcis_wisata_wisman;
        TextView tv_harga_karcis_asuransi_wisnu;
        TextView tv_harga_karcis_asuransi_wisman;
        TextView tv_url_img_lokWis;
        TextView tv_url_img_pintu;
        TextView tv_url_img_ku;
        TextView tv_id_ku;




        ImageView img_ku;
        Button _buttonH_card_karcis_utama;


        MyViewHolder( View itemView) {
            super(itemView);
            this.tv_tgl_kunj=(TextView) itemView.findViewById(R.id.tv_tgl_kunj_ku);
            this.tv_kode_ksda=(TextView) itemView.findViewById(R.id.tv_kodeKsda_ku);
            this.tv_nm_ksda=(TextView) itemView.findViewById(R.id.tv_nmKsda_ku);
            this.tv_kode_lokasi=(TextView) itemView.findViewById(R.id.tv_kodeLokasi_ku);
            this.tv_nm_lokasi=(TextView) itemView.findViewById(R.id.tv_nmLokasi_ku);
            this.tv_kode_karcis=(TextView) itemView.findViewById(R.id.tv_kodeKarcis_ku);
            this.tv_nama_karcis=(TextView) itemView.findViewById(R.id.tv_namaKarcis_ku);
            this.tv_kode_libur=(TextView) itemView.findViewById(R.id.tv_kodeLibur_ku);
            this.tv_harga_karcis_wisata_wisnu=(TextView) itemView.findViewById(R.id.tv_harga_karcis_wisata_wisnu_ku);
            this.tv_harga_karcis_wisata_wisman=(TextView) itemView.findViewById(R.id.tv_harga_karcis_wisata_wisman_ku);
            this.tv_harga_karcis_asuransi_wisnu=(TextView) itemView.findViewById(R.id.tv_harga_karcis_asuransi_wisnu_ku);
            this.tv_harga_karcis_asuransi_wisman=(TextView) itemView.findViewById(R.id.tv_harga_karcis_asuransi_wisman_ku);
            this.tv_url_img_lokWis=(TextView) itemView.findViewById(R.id.tv_url_img_lokWis);
            this.tv_url_img_pintu=(TextView) itemView.findViewById(R.id.tv_url_img_pintu);
            this.tv_url_img_ku=(TextView) itemView.findViewById(R.id.tv_url_img_ku);
            this.img_ku=(ImageView) itemView.findViewById(R.id._tv_img_card_karcis_utama);
            this.tv_id_ku=(TextView) itemView.findViewById(R.id.tv_id_ku);


//            this.img_more=(ImageView) itemView.findViewById(R.id.img_more);
            this._buttonH_card_karcis_utama = (Button) itemView.findViewById(R.id.buttonH_card_karcis_utama);
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View view = li.inflate(R.layout.entity_card_karcis_utama,parent,false);
        MyViewHolder myViewHolder = new CustomAdapterEntityKarcisUtama.MyViewHolder(view);
        return myViewHolder;
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TextView tv_tgl_kunjx = holder.tv_tgl_kunj;
        TextView tv_kode_ksdax = holder.tv_kode_ksda;
        TextView tv_nm_ksdax = holder.tv_nm_ksda;
        TextView tv_kode_lokasix = holder.tv_kode_lokasi;
        TextView tv_nm_lokasix = holder.tv_nm_lokasi;
        TextView tv_kode_karcisx = holder.tv_kode_karcis;
        TextView tv_nama_karcisx = holder.tv_nama_karcis;
        TextView tv_kode_liburx = holder.tv_kode_libur;
        TextView tv_harga_karcis_wisata_wisnux = holder.tv_harga_karcis_wisata_wisnu;
        TextView tv_harga_karcis_wisata_wismanx = holder.tv_harga_karcis_wisata_wisman;
        TextView tv_harga_karcis_asuransi_wisnux = holder.tv_harga_karcis_asuransi_wisnu;
        TextView tv_harga_karcis_asuransi_wismanx = holder.tv_harga_karcis_asuransi_wisman;
        TextView tv_url_img_lokWisx = holder.tv_url_img_lokWis;
        TextView tv_url_img_pintux = holder.tv_url_img_pintu;
        TextView tv_url_img_kux = holder.tv_url_img_ku;
        TextView tv_id_kux = holder.tv_id_ku;
        ImageView img_kux = holder.img_ku;
        Button btn_entity_edit = holder._buttonH_card_karcis_utama;

        SessionManager sessionManager ;
        sessionManager = new SessionManager(context.getApplicationContext());

        tv_id_kux.setVisibility(View.GONE);
        tv_tgl_kunjx.setVisibility(View.GONE);
        tv_kode_ksdax.setVisibility(View.GONE);
        tv_nm_ksdax.setVisibility(View.GONE);
        tv_kode_lokasix.setVisibility(View.GONE);
        tv_nm_lokasix.setVisibility(View.GONE);
        tv_kode_karcisx.setVisibility(View.GONE);
        tv_kode_liburx.setVisibility(View.GONE);
        tv_harga_karcis_wisata_wisnux.setVisibility(View.GONE);
        tv_harga_karcis_wisata_wismanx.setVisibility(View.GONE);
        tv_harga_karcis_asuransi_wisnux.setVisibility(View.GONE);
        tv_harga_karcis_asuransi_wismanx.setVisibility(View.GONE);
        tv_url_img_lokWisx.setVisibility(View.GONE);
        tv_url_img_kux.setVisibility(View.GONE);

        final String _id_ku = modelHorizontalScrollKarcisUtamas.get(position).getId();
        final String _tgl_kunj = modelHorizontalScrollKarcisUtamas.get(position).getTgl_kunj();
        final String _kodeKsda = modelHorizontalScrollKarcisUtamas.get(position).getKode_ksda();
        final String _nm_ksda = modelHorizontalScrollKarcisUtamas.get(position).getNm_ksda();
        final String _kodeLokasi = modelHorizontalScrollKarcisUtamas.get(position).getKode_lokasi();
        final String _nm_pintu = modelHorizontalScrollKarcisUtamas.get(position).getNm_pintu();

        final String _kodeKarcis = modelHorizontalScrollKarcisUtamas.get(position).getKode_karcis();
        final String _namaKarcis = modelHorizontalScrollKarcisUtamas.get(position).getNama_karcis();
        final String _kodeLibur = modelHorizontalScrollKarcisUtamas.get(position).getKode_libur();
        final String _harga_karcis_wisata_wisnu = modelHorizontalScrollKarcisUtamas.get(position).getHarga_karcis_wisata_wisnu();
        final String _harga_karcis_wisata_wisman = modelHorizontalScrollKarcisUtamas.get(position).getHarga_karcis_wisata_wisman();
        final String _harga_karcis_asuransi_wisnu = modelHorizontalScrollKarcisUtamas.get(position).getHarga_karcis_asuransi_wisnu();
        final String _harga_karcis_asuransi_wisman = modelHorizontalScrollKarcisUtamas.get(position).getHarga_karcis_asuransi_wisman();
        final String _harga_karcis_wisata_tmbhn = modelHorizontalScrollKarcisUtamas.get(position).getResult_dt_harga_karcis_wisata_tmbhn();

        final String _url_img_lokWis = modelHorizontalScrollKarcisUtamas.get(position).getUrl_lokWis();
        final String _url_img_pintu = modelHorizontalScrollKarcisUtamas.get(position).getUrl_pintu();
        final String _url_img_ku = modelHorizontalScrollKarcisUtamas.get(position).getUrl_ku();

        final String _jml_karcis_wisnu = modelHorizontalScrollKarcisUtamas.get(position).getResult_dt_jml_karcis_wisnu();
        final String _jml_karcis_wisman = modelHorizontalScrollKarcisUtamas.get(position).getResult_dt_jml_karcis_wisman();
        final String _ttl_wisnu_wisman = modelHorizontalScrollKarcisUtamas.get(position).getResult_dt_ttl_wisnu_wisman();
        final String _jml_karcis_tmbhn = modelHorizontalScrollKarcisUtamas.get(position).getResult_dt_jml_karcis_tmbhn();
        final String _ttl_karcis_tmbhn = modelHorizontalScrollKarcisUtamas.get(position).getResult_dt_ttl_karcis_tmbhn();
        final String _grand_ttl = modelHorizontalScrollKarcisUtamas.get(position).getResult_dt_grand_ttl();

        final String _id_karcis_utama = modelHorizontalScrollKarcisUtamas.get(position).getResult_dt_id_karcis_utama();
        final String _id_karcis_tmbhn = modelHorizontalScrollKarcisUtamas.get(position).getResult_dt_id_karcis_tmbhn();


        Log.i("","_namaKarcis adapter"+_namaKarcis);
        Log.i("","_url_img_lokWis adapter"+_url_img_lokWis);
        Log.i("","_url_img_pintu adapter"+_url_img_pintu);
        Log.i("","_url_img_ku adapter"+_url_img_ku);
        Log.i("","_id_ku adapter"+_id_ku);
        Log.i("","_jml_karcis_wisnu adapter"+_jml_karcis_wisnu);




        tv_tgl_kunjx.setText(modelHorizontalScrollKarcisUtamas.get(position).tgl_kunj);
        tv_kode_ksdax.setText(modelHorizontalScrollKarcisUtamas.get(position).kode_ksda);
        tv_nm_ksdax.setText(modelHorizontalScrollKarcisUtamas.get(position).nm_ksda);
        tv_kode_lokasix.setText(modelHorizontalScrollKarcisUtamas.get(position).kode_lokasi);
        tv_nm_lokasix.setText(modelHorizontalScrollKarcisUtamas.get(position).nm_lokasi_pintu);
        tv_kode_karcisx.setText(modelHorizontalScrollKarcisUtamas.get(position).kode_karcis);
        tv_nama_karcisx.setText(modelHorizontalScrollKarcisUtamas.get(position).nama_karcis);
        tv_kode_liburx.setText(modelHorizontalScrollKarcisUtamas.get(position).kode_libur);
        tv_harga_karcis_wisata_wisnux.setText(modelHorizontalScrollKarcisUtamas.get(position).harga_karcis_wisata_wisnu);
        tv_harga_karcis_wisata_wismanx.setText(modelHorizontalScrollKarcisUtamas.get(position).harga_karcis_wisata_wisman);
        tv_harga_karcis_asuransi_wisnux.setText(modelHorizontalScrollKarcisUtamas.get(position).harga_karcis_asuransi_wisnu);
        tv_harga_karcis_asuransi_wismanx.setText(modelHorizontalScrollKarcisUtamas.get(position).harga_karcis_asuransi_wisman);
        tv_url_img_lokWisx.setText(modelHorizontalScrollKarcisUtamas.get(position).url_lokWis);
        tv_url_img_pintux.setText(modelHorizontalScrollKarcisUtamas.get(position).url_pintu);
        tv_url_img_kux.setText(modelHorizontalScrollKarcisUtamas.get(position).url_ku);
        tv_id_kux.setText(modelHorizontalScrollKarcisUtamas.get(position).id);





        Picasso.with( context )
                .load( _url_img_ku )
                .error(R.mipmap.ic_launcher)
                .resize(1900,600)
                .centerCrop()
                .into(img_kux, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d("TAG", "onSuccess");
                    }
                    @Override
                    public void onError() {
                        Toast.makeText(context, "An error occurred", Toast.LENGTH_SHORT).show();
                    }
                });



        btn_entity_edit.setOnClickListener(v -> {
//                Snackbar.make(v, "Clicked element ", Snackbar.LENGTH_LONG).show();

//                final String _email =  sessionManager.getUserDetail().get(SessionManager.key_email);

            Log.i("","tv_kode_karcisx "+_kodeKarcis);
            Log.i("","_ttl_karcis_tmbhn "+_ttl_karcis_tmbhn);
            Log.i("","_harga_karcis_wisata_tmbhn "+_harga_karcis_wisata_tmbhn);


            Intent i = new Intent(v.getContext(), PesanKarcisWisatawanActivity.class);
            i.putExtra("result_dt_kodeKarcis", _kodeKarcis);
            i.putExtra("result_dt_namaKarcis",_namaKarcis );
            i.putExtra("result_dt_harga_karcis_wisata_wisnu",_harga_karcis_wisata_wisnu );
            i.putExtra("result_dt_harga_karcis_wisata_wisman",_harga_karcis_wisata_wisman );
            i.putExtra("result_dt_harga_karcis_asuransi_wisnu",_harga_karcis_asuransi_wisnu );
            i.putExtra("result_dt_harga_karcis_asuransi_wisman",_harga_karcis_asuransi_wisman );
            i.putExtra("result_dt_kdlokWis", _kodeKsda);
            i.putExtra("result_dt_nmlokWis", _nm_ksda);
            i.putExtra("result_dt_kdlokPintu",_kodeLokasi);
            i.putExtra("result_dt_nmlokPintu",_nm_pintu);
            i.putExtra("result_dt_tgl_kunj",_tgl_kunj);
            i.putExtra("result_dt_url_img_lokWisOld",_url_img_lokWis);
            i.putExtra("result_dt_url_img_lokPintuOld",_url_img_pintu);
            i.putExtra("result_dt_url_img_ku",_url_img_ku);
            i.putExtra("result_dt_id_ku",_id_ku);

            i.putExtra("result_jml_karcis_wisnu",_jml_karcis_wisnu);
            i.putExtra("result_jml_karcis_wisman",_jml_karcis_wisman);
            i.putExtra("result_ttl_wisnu_wisman",_ttl_wisnu_wisman);
            i.putExtra("result_jml_karcis_tmbhn",_jml_karcis_tmbhn);
            i.putExtra("result_ttl_karcis_tmbhn",_ttl_karcis_tmbhn);
            i.putExtra("result_grand_ttl",_grand_ttl);
            i.putExtra("result_dt_harga_karcis_wisata_tmbhn",_harga_karcis_wisata_tmbhn);

            i.putExtra("result_dt_id_karcis_utama",_id_karcis_utama);
            i.putExtra("result_dt_id_karcis_tmbhn",_id_karcis_tmbhn);




            sessionManager.createSessionWisUtm(
                    _kodeKsda,
                    _kodeLokasi,
                    _kodeKarcis,
                    _namaKarcis,
                    _kodeLibur,
                    _harga_karcis_wisata_wisnu,
                    _harga_karcis_wisata_wisman,
                    _harga_karcis_asuransi_wisnu,
                    _harga_karcis_asuransi_wisman,
                    _id_ku,
                    _url_img_ku );


            v.getContext().startActivity(i);

        });
    }



    @Override
    public int getItemCount() {
        return modelHorizontalScrollKarcisUtamas.size();
    }
}


