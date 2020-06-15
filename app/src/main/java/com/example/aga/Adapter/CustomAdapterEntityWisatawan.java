package com.example.aga.Adapter;

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

import com.example.aga.EditKarcisStatusWisatawanActivity;
import com.example.aga.Model.EntityStatusKarcisWisatawan;
import com.example.aga.R;
import com.example.aga.R.layout;

import java.util.ArrayList;

public class CustomAdapterEntityWisatawan extends RecyclerView.Adapter<CustomAdapterEntityWisatawan.MyViewHolder>  {

    ArrayList<EntityStatusKarcisWisatawan> entityStatusKarcisWisatawanArrayList;
    Context context;

    public CustomAdapterEntityWisatawan(ArrayList<EntityStatusKarcisWisatawan> entityStatusKarcisWisatawanArrayList, Context context) {
        this.entityStatusKarcisWisatawanArrayList = entityStatusKarcisWisatawanArrayList;
        this.context = context;
    }



    public static  class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_va;
        TextView tv_tgl;
        TextView tv_status;
        TextView tv_lokWis;
        ImageView img_more;
        Button _btn_entity_edit;

        MyViewHolder( View itemView) {
            super(itemView);
            this.tv_va=(TextView) itemView.findViewById(R.id.tv_va);
            this.tv_tgl=(TextView) itemView.findViewById(R.id.tv_tgl);
            this.tv_status=(TextView) itemView.findViewById(R.id.tv_status);
            this.tv_lokWis=(TextView) itemView.findViewById(R.id.tv_lokWis);
//            this.img_more=(ImageView) itemView.findViewById(R.id.img_more);
            this._btn_entity_edit = (Button) itemView.findViewById(R.id.btn_entity_edit);
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View view = li.inflate(layout.entity_status_karcis_wisatawan,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder( MyViewHolder holder, int position) {
        TextView tv_va = holder.tv_va;
        TextView tv_tgl= holder.tv_tgl;
        TextView tv_status= holder.tv_status;
        TextView tv_lokWis= holder.tv_lokWis;
        Button btn_entity_edit = holder._btn_entity_edit;

        final String _va = entityStatusKarcisWisatawanArrayList.get(position).getVa();
        tv_va.setText(entityStatusKarcisWisatawanArrayList.get(position).va);
        tv_tgl.setText(entityStatusKarcisWisatawanArrayList.get(position).tgl);
        tv_status.setText(entityStatusKarcisWisatawanArrayList.get(position).status);
        tv_lokWis.setText(entityStatusKarcisWisatawanArrayList.get(position).lokWis);

        btn_entity_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Snackbar.make(v, "Clicked element ", Snackbar.LENGTH_LONG).show();

                Intent i = new Intent(v.getContext(), EditKarcisStatusWisatawanActivity.class);
                i.putExtra("result_va", _va );
                i.putExtra("result_dt_flag", "fromCustomAdapterEntityWisatawan" );
                context.startActivity(i);
                Log.i("","_va= "+_va);

            }
        });

    }

    @Override
    public int getItemCount() {
        return entityStatusKarcisWisatawanArrayList.size();
    }
}
