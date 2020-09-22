package com.amanahgithawisata.aga;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amanahgithawisata.aga.Adapter.SessionManager;
import com.amanahgithawisata.aga.Adapter.TableViewAdapter;
import com.amanahgithawisata.aga.Helper.Help;
import com.amanahgithawisata.aga.Model.ModelQuotaLokWis;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuotaActivity extends AppCompatActivity {

    String[] quota_lokwis_header={
            "id",
            "from_date",
            "thru_date",
            "quota",
            "quota_in",
            "quota_out"
    };

    SessionManager sessionManager;
    TableViewAdapter tableViewAdapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quota);

        recyclerView = findViewById(R.id.recyclerViewTableQuotaList);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        calculateData();





    }

    /*
    public List<ModelQuotaLokWis> getQuotalist(){
        List<ModelQuotaLokWis> modelQuotaLokWisList = new ArrayList<>();
        modelQuotaLokWisList.add(new ModelQuotaLokWis("1", "2020-02-12","2020-01-02","1300", "500", "50"));
        modelQuotaLokWisList.add(new ModelQuotaLokWis("1", "2020-02-12","2020-01-02","1300", "500", "50"));
        modelQuotaLokWisList.add(new ModelQuotaLokWis("1", "2020-02-12","2020-01-02","1300", "500", "50"));
        modelQuotaLokWisList.add(new ModelQuotaLokWis("1", "2020-02-12","2020-01-02","1300", "500", "50"));
        modelQuotaLokWisList.add(new ModelQuotaLokWis("1", "2020-02-12","2020-01-02","1300", "500", "50"));

        return modelQuotaLokWisList;
    } */

    public void calculateData(){
        List<ModelQuotaLokWis> modelQuotaLokWisList = new ArrayList<>();

        String EP ="get_quota_lokasi_wisata";
        String server_url = "http://kaffah.amanahgitha.com/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue( getApplicationContext() );
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                response -> {

                    try {
                        if( Help.isJSONValid(response) ){
                            JSONObject jsonObject = new JSONObject(response);

                            if( jsonObject.getBoolean("success") ) {

                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i <jsonArray.length();i++ ) {
                                    JSONObject jInnerObject = new JSONObject();

                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                    String id =  jsonObject1.getString("id");
                                    String from_date =  jsonObject1.getString("from_date");
                                    String thru_date =  jsonObject1.getString("thru_date");
                                    String quota =  jsonObject1.getString("quota");
                                    String quota_in =  jsonObject1.getString("quota_in");
                                    String quota_out =  jsonObject1.getString("quota_out");

                                    Log.i("tag","id= "+id);
                                    Log.i("tag","from_date= "+from_date);

                                    modelQuotaLokWisList.add(new ModelQuotaLokWis(id, from_date,thru_date,quota, quota_in, quota_out));

                                }
                                tableViewAdapter = new TableViewAdapter(modelQuotaLokWisList);
                                recyclerView.setAdapter(tableViewAdapter);


                            }
                        }


                    } catch (JSONException e) {
                        Log.i("", "error ===" + e.toString() );
                        e.printStackTrace();
                    }
                    requestQueue.stop();
                }, error -> {
                    Log.i("", "response spinnerLokPintuPtgs=" + error.toString());
                    error.printStackTrace();
                    requestQueue.stop();
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> obj = new HashMap<String, String>();
//                final String key_email =  Objects.requireNonNull(sessionManager.getUserDetail().get(SessionManager.key_email)).trim();
                obj.put("alamat_email", "petugas-1");
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }



}
