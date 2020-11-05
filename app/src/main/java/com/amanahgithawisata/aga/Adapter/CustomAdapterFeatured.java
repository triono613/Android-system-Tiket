package com.amanahgithawisata.aga.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amanahgithawisata.aga.Model.ModelFeatured;
import com.amanahgithawisata.aga.R;

import java.util.ArrayList;

public class CustomAdapterFeatured extends RecyclerView.Adapter<CustomAdapterFeatured.MyViewHolder> {
    ArrayList<ModelFeatured> modelFeaturedArrayList;
    Context context;

    public CustomAdapterFeatured(ArrayList<ModelFeatured> modelFeaturedArrayList, Context context) {
        this.modelFeaturedArrayList = modelFeaturedArrayList;
        this.context = context;

    }


    public static  class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_title,tv_desc;
        ImageView tv_img;
        Button _btn_entity_edit;

        @SuppressLint("WrongViewCast")
        MyViewHolder(View itemView) {
            super(itemView);

            this.tv_img=(ImageView) itemView.findViewById(R.id.featured_img);
            this.tv_title=(TextView) itemView.findViewById(R.id.featured_title);
            this.tv_desc=(TextView) itemView.findViewById(R.id.featured_desc);

            TextView tv = (TextView) itemView.findViewById(R.id.featured_title);
            SpannableString content = new SpannableString("Content");
            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            tv.setText(content);

        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View view = li.inflate(R.layout.featured_card_design,parent,false);
        MyViewHolder myViewHolder = new CustomAdapterFeatured.MyViewHolder(view);
        return myViewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        TextView tv_titlex = holder.tv_title;
        TextView tv_descx= holder.tv_desc;
        ImageView tv_imgx= holder.tv_img;
        Button btn_entity_edit = holder._btn_entity_edit;

        SessionManager sessionManager ;
        sessionManager = new SessionManager(context.getApplicationContext());

        tv_imgx.setImageResource(modelFeaturedArrayList.get(position).getImg());
//        tv_titlex.setText(modelFeaturedArrayList.get(position).getTitle());
        tv_descx.setText(modelFeaturedArrayList.get(position).getDesc());


        SpannableString content = new SpannableString(modelFeaturedArrayList.get(position).getTitle());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tv_titlex.setText(content);


//        btn_entity_edit.setOnClickListener(v -> {
//            final String _email =  sessionManager.getUserDetail().get(SessionManager.key_email);
//            Intent i = new Intent(v.getContext(), PesanKarcisWisatawanActivity.class);
//            i.putExtra("result_dt_kd_lokwis_adapter", _judul);
//            v.getContext().startActivity(i);
//        });




    }



    @Override
    public int getItemCount() {
        return modelFeaturedArrayList.size();
    }


}


