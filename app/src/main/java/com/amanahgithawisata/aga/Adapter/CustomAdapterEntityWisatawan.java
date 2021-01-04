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

import com.amanahgithawisata.aga.EditKarcisStatusWisatawanActivity;
import com.amanahgithawisata.aga.Helper.Help;
import com.amanahgithawisata.aga.Model.EntityStatusKarcisWisatawan;
import com.amanahgithawisata.aga.NotifSuksesActivity;
import com.amanahgithawisata.aga.R;
import com.amanahgithawisata.aga.R.layout;
import com.amanahgithawisata.aga.SuccessRegistrasiWisatawanActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        Button _btn_detail_tagihan;

        MyViewHolder( View itemView) {
            super(itemView);
            this.tv_va=(TextView) itemView.findViewById(R.id.tv_va);
            this.tv_tgl=(TextView) itemView.findViewById(R.id.tv_tgl);
            this.tv_status=(TextView) itemView.findViewById(R.id.tv_status);
            this.tv_lokWis=(TextView) itemView.findViewById(R.id.tv_lokWis);
//            this.img_more=(ImageView) itemView.findViewById(R.id.img_more);
            this._btn_entity_edit = (Button) itemView.findViewById(R.id.btn_entity_edit);
            this._btn_detail_tagihan = itemView.findViewById(R.id.btn_detail_tagihan);
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
        Button btn_detail_tagihan = holder._btn_detail_tagihan;


        SessionManager sessionManager ;
        sessionManager = new SessionManager(context.getApplicationContext());


        final String _va = entityStatusKarcisWisatawanArrayList.get(position).getVa();
        final String _status = entityStatusKarcisWisatawanArrayList.get(position).getStatus();

        tv_va.setText(entityStatusKarcisWisatawanArrayList.get(position).va);
        tv_tgl.setText(entityStatusKarcisWisatawanArrayList.get(position).tgl);
        tv_status.setText(entityStatusKarcisWisatawanArrayList.get(position).status);
        tv_lokWis.setText(entityStatusKarcisWisatawanArrayList.get(position).lokWis);

        btn_entity_edit.setOnClickListener(v -> {
//                Snackbar.make(v, "Clicked element ", Snackbar.LENGTH_LONG).show();

            final String _email =  sessionManager.getUserDetail().get(SessionManager.key_email);

            if(_status.trim().equals("Kadaluarsa")){
                Intent i = new Intent(v.getContext(), SuccessRegistrasiWisatawanActivity.class);
                i.putExtra("result_dt_ket", "Nomor Virtual Account "+_va+" telah Kadaluarsa");
                i.putExtra("result_dt_email",_email );
                i.putExtra("result_dt_berhasil", "false");
                i.putExtra("result_dt_flag", "flagPesanKarcisPetugas");
                context.startActivity(i);
            } else {
                Intent i = new Intent(v.getContext(), EditKarcisStatusWisatawanActivity.class);
                i.putExtra("result_va", _va );
                i.putExtra("result_dt_flag", "fromCustomAdapterEntityWisatawan" );
                context.startActivity(i);
                Log.i("","_va= "+_va);
            }

        });

        btn_detail_tagihan.setOnClickListener(v -> tagihan_by_no_va("tagihan_by_no_va", _va));

    }

    @Override
    public int getItemCount() {
        return entityStatusKarcisWisatawanArrayList.size();
    }

    private void tagihan_by_no_va(String EP, String VA){

        String server_url = "http://"+ Help.domain_api() +"/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                (Response.Listener<String>) response -> {
                    Log.i("tag","response= " + response );
                    try {

                        if( Help.isJSONValid(response) ){

                            JSONObject jObj = new JSONObject(response);
                            String data = jObj.getString("data");
                            JSONObject jsonObject1 = new JSONObject(data);
                            Boolean berhasil = jsonObject1.getBoolean("berhasil");

                            Log.i("tag","success= " + jObj.getBoolean("success") );
                            Log.i("tag","data= " + data );
                            Log.i("tag","berhasil= " + berhasil );

                            if( berhasil ) {
                                String _id = jsonObject1.getString("id");
                                String _va_no = jsonObject1.getString("va_no");
                                String _va_no_berlaku_sd = jsonObject1.getString("va_no_berlaku_sd");
                                String _vnama = jsonObject1.getString("nama");
                                String _alamat_email = jsonObject1.getString("alamat_email");
                                String _sellular_no = jsonObject1.getString("sellular_no");
                                String _jumlah_wisnu = jsonObject1.getString("jumlah_wisnu");
                                String _jumlah_wisman = jsonObject1.getString("jumlah_wisman");
                                String _jumlah_karcis = jsonObject1.getString("jumlah_karcis");
                                String _tgl_penjualan = jsonObject1.getString("tgl_penjualan");
                                String _tgl_kunjungan = jsonObject1.getString("tgl_kunjungan");
                                String _menit_valid = jsonObject1.getString("menit_valid");
                                String _tgl_valid = jsonObject1.getString("tgl_valid");
                                String _tagihan_total = jsonObject1.getString("tagihan_total");
                                String _nama_lokasi = jsonObject1.getString("nama_lokasi");
                                String _jumlah_tambahan = jsonObject1.getString("jumlah_tambahan");


                                Intent i = new Intent(context, NotifSuksesActivity.class);
                                i.putExtra("result_dt_ket", "Pemesanan Anda Berhasil Silahkan Cek email!");
                                i.putExtra("_id", _id);
                                i.putExtra("_va_no", _va_no);
                                i.putExtra("_va_no_berlaku_sd", _va_no_berlaku_sd);
                                i.putExtra("_vnama", _vnama);
                                i.putExtra("_alamat_email", _alamat_email);
                                i.putExtra("_sellular_no", _sellular_no);
                                i.putExtra("_jumlah_wisnu", _jumlah_wisnu);
                                i.putExtra("_jumlah_wisman", _jumlah_wisman);
                                i.putExtra("_jumlah_karcis", _jumlah_karcis);
                                i.putExtra("_tgl_penjualan", _tgl_penjualan);
                                i.putExtra("_tgl_kunjungan", _tgl_kunjungan);
                                i.putExtra("_menit_valid", _menit_valid);
                                i.putExtra("_tgl_valid", _tgl_valid);
                                i.putExtra("_tagihan_total", _tagihan_total);
                                i.putExtra("_jumlah_tambahan", _jumlah_tambahan);
                                i.putExtra("_nama_lokasi", _nama_lokasi);

                                i.putExtra("result_dt_berhasil", berhasil);
                                i.putExtra("result_dt_flag", "detailTagihan");
                                context.startActivity(i);
                            }
                        }
                    } catch (JSONException e) {
                        Log.i("", "error pesan karcis wisatawan" + e.toString() );
                        e.printStackTrace();
                    }
                    requestQueue.stop();
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("tag", "response =" + error.toString());
                error.printStackTrace();
                requestQueue.stop();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> obj = new HashMap<String, String>();

                obj.put("va_no", VA);

                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }
}
