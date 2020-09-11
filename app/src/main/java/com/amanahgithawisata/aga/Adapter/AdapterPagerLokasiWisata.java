package com.amanahgithawisata.aga.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amanahgithawisata.aga.Model.ModelHorizontalScrollLokasiWisata;
import com.amanahgithawisata.aga.R;
import com.amanahgithawisata.aga.R.layout;

import java.util.ArrayList;

public class AdapterPagerLokasiWisata extends RecyclerView.Adapter<AdapterPagerLokasiWisata.MyViewHolder>  {

    ArrayList<ModelHorizontalScrollLokasiWisata> modelHorizontalScrollLokasiWisatas;
    Context context;

    public AdapterPagerLokasiWisata(ArrayList<ModelHorizontalScrollLokasiWisata> modelHorizontalScrollLokasiWisatas, Context context) {
        this.modelHorizontalScrollLokasiWisatas = modelHorizontalScrollLokasiWisatas;
        this.context = context;
    }



    public static  class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_jdl;
        TextView tv_text;
        ImageView img_more;
        Button _btnH;

        MyViewHolder( View itemView) {
            super(itemView);
            this.tv_jdl=(TextView) itemView.findViewById(R.id.tv_judul_scroll);
            this.tv_text=(TextView) itemView.findViewById(R.id.tv_text_scroll);
            this.img_more=(ImageView) itemView.findViewById(R.id.tv_img_scroll);
            this._btnH = (Button) itemView.findViewById(R.id.buttonH);
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View view = li.inflate(layout.card_lokasi_wisata_scroll,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder( MyViewHolder holder, int position) {
        TextView tv_jdl = holder.tv_jdl;
        TextView tv_text = holder.tv_text;
        ImageView img_more= holder.img_more;
        Button btnH = holder._btnH;


        SessionManager sessionManager ;
        sessionManager = new SessionManager(context.getApplicationContext());


        final String _jdl = modelHorizontalScrollLokasiWisatas.get(position).getJudul();
        final String _text = modelHorizontalScrollLokasiWisatas.get(position).getText();
        final String _img = modelHorizontalScrollLokasiWisatas.get(position).getUrl_gambar();

        tv_jdl.setText(modelHorizontalScrollLokasiWisatas.get(position).judul);
        tv_text.setText(modelHorizontalScrollLokasiWisatas.get(position).text);
//        img_more.setImageResource(modelHorizontalScrollLokasiWisatas.get(position).gambar);

        btnH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Snackbar.make(v, "Clicked element ", Snackbar.LENGTH_LONG).show();

                final String _email =  sessionManager.getUserDetail().get(SessionManager.key_email);

//                if(_status.trim().equals("Kadaluarsa")){
//                    Intent i = new Intent(v.getContext(), SuccessRegistrasiWisatawanActivity.class);
//                    i.putExtra("result_dt_ket", "Nomor Virtual Account "+_va+" telah Kadaluarsa");
//                    i.putExtra("result_dt_email",_email );
//                    i.putExtra("result_dt_berhasil", "false");
//                    i.putExtra("result_dt_flag", "flagPesanKarcisPetugas");
//                    context.startActivity(i);
//                } else {
//                    Intent i = new Intent(v.getContext(), EditKarcisStatusWisatawanActivity.class);
//                    i.putExtra("result_va", _va );
//                    i.putExtra("result_dt_flag", "fromCustomAdapterEntityWisatawan" );
//                    context.startActivity(i);
//                    Log.i("","_va= "+_va);
//                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return modelHorizontalScrollLokasiWisatas.size();
    }
}
