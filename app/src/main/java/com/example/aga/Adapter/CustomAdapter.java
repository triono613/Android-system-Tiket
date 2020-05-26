package com.example.aga.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aga.Model.EntityStatusKarcis;
import com.example.aga.R;
import com.example.aga.R.layout;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>  {

    ArrayList<EntityStatusKarcis> entityStatusKarcisArrayList ;
    Context context;

    public CustomAdapter(ArrayList<EntityStatusKarcis> entityStatusKarcisArrayList, Context context) {
        this.entityStatusKarcisArrayList = entityStatusKarcisArrayList;
        this.context = context;
    }



    public static  class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_va;
        TextView tv_tgl;
        TextView tv_status;
        TextView tv_lokWis;
        ImageView img_more;

        MyViewHolder( View itemView) {
            super(itemView);
            this.tv_va=(TextView) itemView.findViewById(R.id.tv_va);
            this.tv_tgl=(TextView) itemView.findViewById(R.id.tv_tgl);
            this.tv_status=(TextView) itemView.findViewById(R.id.tv_status);
            this.tv_lokWis=(TextView) itemView.findViewById(R.id.tv_lokWis);
//            this.img_more=(ImageView) itemView.findViewById(R.id.img_more);
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View view = li.inflate(layout.entity_status_karcis,parent,false);
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
//        ImageView img_more= holder.img_more;

        tv_va.setText(entityStatusKarcisArrayList.get(position).va);
        tv_tgl.setText(entityStatusKarcisArrayList.get(position).tgl);
        tv_status.setText(entityStatusKarcisArrayList.get(position).status);
        tv_lokWis.setText(entityStatusKarcisArrayList.get(position).lokWis);

    }

    @Override
    public int getItemCount() {
        return entityStatusKarcisArrayList.size();
    }
}
