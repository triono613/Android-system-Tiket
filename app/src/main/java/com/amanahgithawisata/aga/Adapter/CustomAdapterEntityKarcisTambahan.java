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

import com.amanahgithawisata.aga.Model.ModelHorizontalScrollKarcisTambahan;
import com.amanahgithawisata.aga.PesanKarcisWisatawanActivity;
import com.amanahgithawisata.aga.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapterEntityKarcisTambahan extends RecyclerView.Adapter<CustomAdapterEntityKarcisTambahan.MyViewHolder> {
    ArrayList<ModelHorizontalScrollKarcisTambahan> modelHorizontalScrollKarcisTambahans;
    Context context;

    public CustomAdapterEntityKarcisTambahan(ArrayList<ModelHorizontalScrollKarcisTambahan> modelHorizontalScrollKarcisTambahans, Context context) {
        this.modelHorizontalScrollKarcisTambahans = modelHorizontalScrollKarcisTambahans;
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
        TextView tv_harga_karcis_wisata;
        TextView tv_harga_karcis_asuransi;
        TextView tv_url_img_lokWis;
        TextView tv_url_img_pintu;
        TextView tv_url_img_kt;
        TextView tv_id_kt;

        TextView tv_dt_jml_krcs_wisnu;
        TextView tv_dt_jml_krcs_wisman;
        TextView tv_dt_ttl_jml_krcs_wisnu_wisman;

        TextView tv_dt_jml_krcs_tmbhn;
        TextView tv_dt_ttl_krcs_tmbhn;
        TextView tv_dt_grand_ttl;

        ImageView img_kt;
        Button _buttonH_card_karcis_tmbhn;


        MyViewHolder( View itemView) {
            super(itemView);


            this.tv_dt_jml_krcs_tmbhn=(TextView) itemView.findViewById(R.id.tv_dt_jml_krcs_tmbhn);
            this.tv_dt_ttl_krcs_tmbhn=(TextView) itemView.findViewById(R.id.tv_dt_ttl_krcs_tmbhn);
            this.tv_dt_grand_ttl=(TextView) itemView.findViewById(R.id.tv_dt_grand_ttl);

            this.tv_dt_jml_krcs_wisnu=(TextView) itemView.findViewById(R.id.tv_dt_jml_krcs_wisnu);
            this.tv_dt_jml_krcs_wisman=(TextView) itemView.findViewById(R.id.tv_dt_jml_krcs_wisman);
            this.tv_dt_ttl_jml_krcs_wisnu_wisman=(TextView) itemView.findViewById(R.id.tv_dt_ttl_jml_krcs_wisnu_wisman);

            this.tv_tgl_kunj=(TextView) itemView.findViewById(R.id.tv_tgl_kunj_kt);
            this.tv_kode_ksda=(TextView) itemView.findViewById(R.id.tv_kodeKsda_kt);
            this.tv_nm_ksda=(TextView) itemView.findViewById(R.id.tv_nmKsda_kt);
            this.tv_kode_lokasi=(TextView) itemView.findViewById(R.id.tv_kodeLokasi_kt);
            this.tv_nm_lokasi=(TextView) itemView.findViewById(R.id.tv_nmLokasi_kt);
            this.tv_kode_karcis=(TextView) itemView.findViewById(R.id.tv_kodeKarcis_kt);
            this.tv_nama_karcis=(TextView) itemView.findViewById(R.id.tv_namaKarcis_kt);
            this.tv_kode_libur=(TextView) itemView.findViewById(R.id.tv_kodeLibur_kt);
            this.tv_harga_karcis_wisata=(TextView) itemView.findViewById(R.id.tv_harga_karcis_wisata_kt);
            this.tv_harga_karcis_asuransi=(TextView) itemView.findViewById(R.id.tv_harga_karcis_asuransi_kt);
            this.tv_url_img_lokWis=(TextView) itemView.findViewById(R.id.tv_url_img_lokWis_kt);
            this.tv_url_img_pintu=(TextView) itemView.findViewById(R.id.tv_url_img_pintu_kt);
            this.tv_url_img_kt=(TextView) itemView.findViewById(R.id.tv_url_img_kt);
            this.img_kt=(ImageView) itemView.findViewById(R.id._tv_img_card_karcis_tmbhn);
            this._buttonH_card_karcis_tmbhn = (Button) itemView.findViewById(R.id.buttonH_card_karcis_tmbhn);
            this.tv_id_kt=(TextView) itemView.findViewById(R.id.tv_id_kt);
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View view = li.inflate(R.layout.entity_card_karcis_tambahan,parent,false);
        MyViewHolder myViewHolder = new CustomAdapterEntityKarcisTambahan.MyViewHolder(view);
        return myViewHolder;
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        TextView tv_dt_jml_krcs_tmbhnx = holder.tv_dt_jml_krcs_tmbhn;
        TextView tv_dt_ttl_krcs_tmbhnx = holder.tv_dt_ttl_krcs_tmbhn;
        TextView tv_dt_grand_ttlx = holder.tv_dt_grand_ttl;

        TextView tv_dt_jml_krcs_wisnux = holder.tv_dt_jml_krcs_wisnu;
        TextView tv_dt_jml_krcs_wismanx = holder.tv_dt_jml_krcs_wisman;
        TextView tv_dt_ttl_jml_krcs_wisnu_wismanx = holder.tv_dt_ttl_jml_krcs_wisnu_wisman;

        TextView tv_tgl_kunjx = holder.tv_tgl_kunj;
        TextView tv_kode_ksdax = holder.tv_kode_ksda;
        TextView tv_nm_ksdax = holder.tv_nm_ksda;
        TextView tv_kode_lokasix = holder.tv_kode_lokasi;
        TextView tv_nm_lokasix = holder.tv_nm_lokasi;
        TextView tv_kode_karcisx = holder.tv_kode_karcis;
        TextView tv_nama_karcisx = holder.tv_nama_karcis;
        TextView tv_kode_liburx = holder.tv_kode_libur;
        TextView tv_harga_karcis_wisatax = holder.tv_harga_karcis_wisata;
        TextView tv_harga_karcis_asuransix = holder.tv_harga_karcis_asuransi;
        TextView tv_url_img_lokWisx = holder.tv_url_img_lokWis;
        TextView tv_url_img_pintux = holder.tv_url_img_pintu;
        TextView tv_url_img_ktx = holder.tv_url_img_kt;
        TextView tv_id_ktx = holder.tv_id_kt;
        ImageView tv_img_ktx = holder.img_kt;
        Button btn_entity_edit = holder._buttonH_card_karcis_tmbhn;

        SessionManager sessionManager ;
        sessionManager = new SessionManager(context.getApplicationContext());

        tv_id_ktx.setVisibility(View.GONE);
        tv_tgl_kunjx.setVisibility(View.GONE);
        tv_kode_ksdax.setVisibility(View.GONE);
        tv_nm_ksdax.setVisibility(View.GONE);
        tv_kode_lokasix.setVisibility(View.GONE);
        tv_nm_lokasix.setVisibility(View.GONE);
        tv_kode_karcisx.setVisibility(View.GONE);
        tv_kode_liburx.setVisibility(View.GONE);
        tv_harga_karcis_wisatax.setVisibility(View.GONE);
        tv_harga_karcis_asuransix.setVisibility(View.GONE);
        tv_url_img_lokWisx.setVisibility(View.GONE);
        tv_url_img_pintux.setVisibility(View.GONE);
        tv_url_img_ktx.setVisibility(View.GONE);


        final String _jml_krcs_tmbhn = modelHorizontalScrollKarcisTambahans.get(position).getResult_dt_jml_krcs_tmbhn();
        final String _ttl_krcs_tmbhn = modelHorizontalScrollKarcisTambahans.get(position).getResult_dt_ttl_krcs_tmbhn();
        final String _grand_ttl = modelHorizontalScrollKarcisTambahans.get(position).getResult_dt_grand_ttl();

        final String _jml_krcs_wisnu = modelHorizontalScrollKarcisTambahans.get(position).getResult_dt_jml_krcs_wisnu();
        final String _jml_krcs_wisman = modelHorizontalScrollKarcisTambahans.get(position).getResult_dt_jml_krcs_wisman();
        final String _jml_krcs_wisnu_wisman = modelHorizontalScrollKarcisTambahans.get(position).getResult_dt_ttl_jml_krcs_wisnu_wisman();

        final String _tgl_kunj = modelHorizontalScrollKarcisTambahans.get(position).getTgl_kunj();
        final String _kodeKsda = modelHorizontalScrollKarcisTambahans.get(position).getKode_ksda();
        final String _nm_ksda = modelHorizontalScrollKarcisTambahans.get(position).getNm_ksda();
        final String _kodeLokasi = modelHorizontalScrollKarcisTambahans.get(position).getKode_lokasi();
        final String _nm_pintu = modelHorizontalScrollKarcisTambahans.get(position).getNm_pintu();

        final String _kodeKarcis = modelHorizontalScrollKarcisTambahans.get(position).getKode_karcis();
        final String _namaKarcis = modelHorizontalScrollKarcisTambahans.get(position).getNama_karcis();
        final String _kodeLibur = modelHorizontalScrollKarcisTambahans.get(position).getKode_libur();
        final String _harga_karcis_wisata = modelHorizontalScrollKarcisTambahans.get(position).getHarga_karcis_wisata();
        final String _harga_karcis_asuransi = modelHorizontalScrollKarcisTambahans.get(position).getHarga_karcis_asuransi();

        final String _url_img_lokWis = modelHorizontalScrollKarcisTambahans.get(position).getUrl_lokWis();
        final String _url_img_pintu = modelHorizontalScrollKarcisTambahans.get(position).getUrl_pintu();
        final String _url_img_kt= modelHorizontalScrollKarcisTambahans.get(position).getUrl_img_kt();
        final String _id_kt= modelHorizontalScrollKarcisTambahans.get(position).getId();

        final String _result_dt_id_karcis_utama= modelHorizontalScrollKarcisTambahans.get(position).getResult_dt_id_karcis_utama();
        final String _result_dt_id_karcis_tmbhn= modelHorizontalScrollKarcisTambahans.get(position).getResult_dt_id_karcis_tmbhn();

        final String harga_karcis_wisata_wisnu = modelHorizontalScrollKarcisTambahans.get(position).getHarga_karcis_wisata_wisnu();
        final String harga_karcis_wisata_wisman = modelHorizontalScrollKarcisTambahans.get(position).getHarga_karcis_wisata_wisman();
        final String harga_karcis_wisata_tmbhn = modelHorizontalScrollKarcisTambahans.get(position).getHarga_karcis_wisata_tmbhn();
        final String harga_karcis_asuransi_wisnu = modelHorizontalScrollKarcisTambahans.get(position).getHarga_karcis_asuransi_wisnu();
        final String harga_karcis_asuransi_wisman = modelHorizontalScrollKarcisTambahans.get(position).getHarga_karcis_asuransi_wisman();



        Log.i("","_url_img_lokWis tambahan adapter"+_url_img_lokWis);
        Log.i("","_url_img_pintu tambahan adapter"+_url_img_pintu);
        Log.i("","_namaKarcis tambahan adapter"+_namaKarcis);


        tv_dt_jml_krcs_tmbhnx.setText(modelHorizontalScrollKarcisTambahans.get(position).result_dt_jml_krcs_tmbhn);
        tv_dt_ttl_krcs_tmbhnx.setText(modelHorizontalScrollKarcisTambahans.get(position).result_dt_ttl_krcs_tmbhn);
        tv_dt_grand_ttlx.setText(modelHorizontalScrollKarcisTambahans.get(position).result_dt_grand_ttl);

        tv_dt_jml_krcs_wisnux.setText(modelHorizontalScrollKarcisTambahans.get(position).result_dt_jml_krcs_wisnu);
        tv_dt_jml_krcs_wismanx.setText(modelHorizontalScrollKarcisTambahans.get(position).result_dt_jml_krcs_wisman);
        tv_dt_ttl_jml_krcs_wisnu_wismanx.setText(modelHorizontalScrollKarcisTambahans.get(position).result_dt_ttl_jml_krcs_wisnu_wisman);

        tv_tgl_kunjx.setText(modelHorizontalScrollKarcisTambahans.get(position).tgl_kunj);
        tv_kode_ksdax.setText(modelHorizontalScrollKarcisTambahans.get(position).kode_ksda);
        tv_nm_ksdax.setText(modelHorizontalScrollKarcisTambahans.get(position).nm_ksda);
        tv_kode_lokasix.setText(modelHorizontalScrollKarcisTambahans.get(position).kode_lokasi);
        tv_nm_lokasix.setText(modelHorizontalScrollKarcisTambahans.get(position).nm_lokasi_pintu);
        tv_kode_karcisx.setText(modelHorizontalScrollKarcisTambahans.get(position).kode_karcis);
        tv_nama_karcisx.setText(modelHorizontalScrollKarcisTambahans.get(position).nama_karcis);
        tv_kode_liburx.setText(modelHorizontalScrollKarcisTambahans.get(position).kode_libur);
        tv_harga_karcis_wisatax.setText(modelHorizontalScrollKarcisTambahans.get(position).harga_karcis_wisata);
        tv_harga_karcis_asuransix.setText(modelHorizontalScrollKarcisTambahans.get(position).harga_karcis_asuransi);
        tv_url_img_lokWisx.setText(modelHorizontalScrollKarcisTambahans.get(position).url_lokWis);
        tv_url_img_pintux.setText(modelHorizontalScrollKarcisTambahans.get(position).url_pintu);
        tv_url_img_ktx.setText(modelHorizontalScrollKarcisTambahans.get(position).url_img_kt);
        tv_id_ktx.setText(modelHorizontalScrollKarcisTambahans.get(position).id);


        Picasso.with( context )
                .load( _url_img_kt )
                .error(R.mipmap.ic_launcher)
                .resize(1900,600)
                .centerCrop()
                .into(tv_img_ktx, new Callback() {
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
            Log.i("","_namaKarcis "+_namaKarcis);

            Log.i("","_id_kt "+_id_kt);

            Intent i = new Intent(v.getContext(), PesanKarcisWisatawanActivity.class);
            i.putExtra("result_dt_id_kt", _id_kt);
            i.putExtra("result_dt_kodeKarcis_kt", _kodeKarcis);
            i.putExtra("result_dt_namaKarcis_kt",_namaKarcis );
            i.putExtra("result_dt_harga_karcis_wisata_kt",_harga_karcis_wisata );
            i.putExtra("result_dt_harga_karcis_asuransi_kt",_harga_karcis_asuransi );
            i.putExtra("result_dt_kdlokWis_kt", _kodeKsda);
            i.putExtra("result_dt_nmlokWis_kt", _nm_ksda);
            i.putExtra("result_dt_kdlokPintu_kt",_kodeLokasi);
            i.putExtra("result_dt_nmlokPintu_kt",_nm_pintu);
            i.putExtra("result_dt_tgl_kunj_kt",_tgl_kunj);
            i.putExtra("result_dt_url_img_lokWisOld_kt",_url_img_lokWis);
            i.putExtra("result_dt_url_img_lokPintuOld_kt",_url_img_pintu);

            i.putExtra("result_dt_jml_krcs_wisnu_kt",_jml_krcs_wisnu);
            i.putExtra("result_dt_jml_krcs_wisman_kt",_jml_krcs_wisman);
            i.putExtra("result_dt_jml_krcs_wisnu_wisman_kt",_jml_krcs_wisnu_wisman);

            i.putExtra("result_dt_jml_krcs_tmbhn_kt",_jml_krcs_tmbhn);
            i.putExtra("result_dt_ttl_krcs_tmbhn_kt",_ttl_krcs_tmbhn);
            i.putExtra("result_dt_grand_ttl",_grand_ttl);

            i.putExtra("result_dt_id_karcis_utama",_result_dt_id_karcis_utama);
            i.putExtra("result_dt_id_karcis_tmbhn",_result_dt_id_karcis_tmbhn);

            i.putExtra("harga_karcis_wisata_wisnu_kt",harga_karcis_wisata_wisnu);
            i.putExtra("harga_karcis_wisata_wisman_kt",harga_karcis_wisata_wisman);
            i.putExtra("harga_karcis_wisata_tmbhn_kt",harga_karcis_wisata_tmbhn);
            i.putExtra("harga_karcis_asuransi_wisnu_kt",harga_karcis_asuransi_wisnu);
            i.putExtra("harga_karcis_asuransi_wisman_kt",harga_karcis_asuransi_wisman);


            sessionManager.createSessionWisTmbhn(   _kodeKarcis,
                                                    _namaKarcis,
                                                    _harga_karcis_wisata,
                                                    _id_kt,
                                                    _url_img_kt );

//                    i.putExtra("result_dt_berhasil", "false");
//                    i.putExtra("result_dt_flag", "flagPesanKarcisPetugas");
            v.getContext().startActivity(i);
        });
    }



    @Override
    public int getItemCount() {
        return modelHorizontalScrollKarcisTambahans.size();
    }
}


