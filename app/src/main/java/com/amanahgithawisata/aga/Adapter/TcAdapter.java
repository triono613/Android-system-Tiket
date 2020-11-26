package com.amanahgithawisata.aga.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.amanahgithawisata.aga.Model.ModelTC;
import com.amanahgithawisata.aga.R;
import com.amanahgithawisata.aga.R.layout;

import java.util.ArrayList;

public class TcAdapter extends RecyclerView.Adapter<TcAdapter.MyViewHolder>  {

    ArrayList<ModelTC> modelTCArrayList ;
    Context context;

    public TcAdapter(ArrayList<ModelTC> modelTCArrayList, Context context) {
        this.modelTCArrayList = modelTCArrayList;
        this.context = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout expandableLayout;
        TextView titleTextView, yearTextView, ratingTextView, plotTextView;
        WebView webView;


        @SuppressLint("SetJavaScriptEnabled")
        public MyViewHolder(View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.titleTextView);
            yearTextView = itemView.findViewById(R.id.yearTextView);
            ratingTextView = itemView.findViewById(R.id.ratingTextView);
            plotTextView = itemView.findViewById(R.id.plotTextView);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
            webView = itemView.findViewById(R.id.webView);

            titleTextView.setOnClickListener((View.OnClickListener) view -> {

                ModelTC modelTC1 = modelTCArrayList.get(getAdapterPosition());
                modelTC1.setExpanded(!modelTC1.isExpanded());
                notifyItemChanged(getAdapterPosition());

            });
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View view = li.inflate(layout.tc_list_row,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;

    }

    @SuppressLint({"SetTextI18n", "SetJavaScriptEnabled"})
    @Override
    public void onBindViewHolder( MyViewHolder holder, int position) {

        ModelTC modelTC = modelTCArrayList.get(position);
//        int icon = R.drawable.ic_person_black_24dp;
        holder.titleTextView.setCompoundDrawablesWithIntrinsicBounds(modelTC.getIcon(),0,0,0);

        holder.titleTextView.setText(modelTC.getTitle());
        holder.yearTextView.setText(modelTC.getYear());
        holder.ratingTextView.setText(modelTC.getRating());
        holder.plotTextView.setText(modelTC.getPlot());

        boolean isExpanded = modelTCArrayList.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        holder.webView.getSettings().setJavaScriptEnabled(true);
        holder.webView.getSettings();
        holder.webView.requestFocus();
        holder.webView.getSettings().setJavaScriptEnabled(true);

        Log.i("","modelTC.getUrl()= "+modelTC.getUrl());
        Log.i("","modelTC.getTitle()= "+modelTC.getTitle());

        String url = modelTC.getUrl();
        holder.webView.loadUrl(url);
        holder.webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        holder.webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
            }
        });


    }

    @Override
    public int getItemCount() {
        return modelTCArrayList.size();
    }



}
