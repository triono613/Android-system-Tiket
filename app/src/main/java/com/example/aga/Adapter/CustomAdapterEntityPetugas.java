package com.example.aga.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aga.ClaimPetugasActivity;
import com.example.aga.EditKarcisStatusPetugasActivity;
import com.example.aga.Model.EntityStatusKarcisPetugas;
import com.example.aga.R;
import com.example.aga.R.layout;

import java.util.ArrayList;

public class CustomAdapterEntityPetugas extends RecyclerView.Adapter<CustomAdapterEntityPetugas.MyViewHolder>  {

    ArrayList<EntityStatusKarcisPetugas> entityStatusKarcisPetugasArrayList ;
    Context context;

//    ArrayList<EntityStatusKarcisPetugas> exampleList;
//    ArrayList<EntityStatusKarcisPetugas> exampleListFull;

    public CustomAdapterEntityPetugas(ArrayList<EntityStatusKarcisPetugas> entityStatusKarcisPetugasArrayList, Context context) {
        this.entityStatusKarcisPetugasArrayList = entityStatusKarcisPetugasArrayList;
        this.context = context;
    }



    public static  class MyViewHolder extends RecyclerView.ViewHolder{
        TextView _tv_va;
        TextView _tv_tgl;
        TextView _tv_status;
        TextView _tv_lokWis;
//        ImageView img_more;
        Button _btn_entity_edit;
        Button _btn_entity_view_ptgs;

        MyViewHolder( View itemView) {
            super(itemView);
            this._tv_va = (TextView) itemView.findViewById(R.id.tv_va_ptgs);
            this._tv_tgl = (TextView) itemView.findViewById(R.id.tv_tgl_ptgs);
            this._tv_status = (TextView) itemView.findViewById(R.id.tv_status_ptgs);
            this._tv_lokWis = (TextView) itemView.findViewById(R.id.tv_lokWis_ptgs);
//            this.img_more=(ImageView) itemView.findViewById(R.id.img_more);
            this._btn_entity_edit = (Button) itemView.findViewById(R.id.btn_entity_edit_ptgs);
            this._btn_entity_view_ptgs = (Button) itemView.findViewById(R.id.btn_entity_view_ptgs);
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View view = li.inflate(layout.entity_status_karcis_petugas,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder( MyViewHolder holder, int position) {
        TextView tv_va = holder._tv_va;
        TextView tv_tgl = holder._tv_tgl;
        TextView tv_status = holder._tv_status;
        TextView tv_lokWis = holder._tv_lokWis;
        Button  btn_entity_edit = holder._btn_entity_edit;
        Button btn_entity_view_ptgs = holder._btn_entity_view_ptgs;


        final String _va = entityStatusKarcisPetugasArrayList.get(position).getVa();
        tv_va.setText(entityStatusKarcisPetugasArrayList.get(position).va);
        tv_tgl.setText(entityStatusKarcisPetugasArrayList.get(position).tgl);
        tv_status.setText(entityStatusKarcisPetugasArrayList.get(position).status);
        tv_lokWis.setText(entityStatusKarcisPetugasArrayList.get(position).lokWis);


        btn_entity_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Snackbar.make(v, "Clicked element "+ _va , Snackbar.LENGTH_LONG).show();

                Intent i = new Intent(v.getContext(), EditKarcisStatusPetugasActivity.class);
                i.putExtra("result_va", _va );
                i.putExtra("result_dt_flag", "fromCustomAdapterEntityPetugas" );
                context.startActivity(i);
                Log.i("","_va Entity "+_va);


            }
        });

        btn_entity_view_ptgs.setOnClickListener(v -> {
            Intent x = new Intent(v.getContext(), ClaimPetugasActivity.class);
            x.putExtra("result_va", _va);
            x.putExtra("result_dt_flag", "fromCustomAdapterEntityPetugas" );
            context.startActivity(x);


        });

    }

    @Override
    public int getItemCount() {
        return entityStatusKarcisPetugasArrayList.size();
    }

//    ArrayList<EntityStatusKarcisPetugas> entityStatusKarcisPetugasArrayList ;
public void setFilter(ArrayList<EntityStatusKarcisPetugas> filterList){
    entityStatusKarcisPetugasArrayList = new ArrayList<>();
    entityStatusKarcisPetugasArrayList.addAll(filterList);
    notifyDataSetChanged();
}

}
