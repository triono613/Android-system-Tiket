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

        tv_va.setText(entityStatusKarcisPetugasArrayList.get(position).va);
        tv_tgl.setText(entityStatusKarcisPetugasArrayList.get(position).tgl);
        tv_status.setText(entityStatusKarcisPetugasArrayList.get(position).status);
        tv_lokWis.setText(entityStatusKarcisPetugasArrayList.get(position).lokWis);

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

//    public Filter getFilter() {
//        return exampleFilter;
//    }
//    private Filter exampleFilter = new Filter() {
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            List<EntityStatusKarcisPetugas> filteredList = new ArrayList<>();
//            if (constraint == null || constraint.length() == 0) {
//                filteredList.addAll(exampleListFull);
//            } else {
//                String filterPattern = constraint.toString().toLowerCase().trim();
//                for (EntityStatusKarcisPetugas item : exampleListFull) {
//                    if (item.getVa().toLowerCase().contains(filterPattern)) {
//                        filteredList.add(item);
//                    }
//                }
//            }
//            FilterResults results = new FilterResults();
//            results.values = filteredList;
//            return results;
//        }
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            exampleList.clear();
//            exampleList.addAll((List) results.values);
//            notifyDataSetChanged();
//        }
//    };
}
