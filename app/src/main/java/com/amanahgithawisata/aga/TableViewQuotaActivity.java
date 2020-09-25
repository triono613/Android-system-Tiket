package com.amanahgithawisata.aga;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amanahgithawisata.aga.Adapter.SessionManager;
import com.amanahgithawisata.aga.Adapter.TableViewAdapter;
import com.amanahgithawisata.aga.Helper.Help;
import com.amanahgithawisata.aga.Model.ModelQuotaExample;
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


public class TableQuotaExampleActivity extends AppCompatActivity {
    SessionManager sessionManager;
    TableViewAdapter tableViewAdapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_quota_example);
        sessionManager = new SessionManager(getApplicationContext());
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);

        recyclerView = findViewById(R.id.recyclerViewDeliveryProductList);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        calculateData();

//        TableViewAdapter adapter = new TableViewAdapter(getQuotalist());
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setAdapter(adapter);

    }

    public List<ModelQuotaLokWis> getQuotalist(){
        List<ModelQuotaLokWis> modelQuotaLokWisList = new ArrayList<>();

        int a;
        for (a=0; a<20; a++)
              {
                  modelQuotaLokWisList.add(new ModelQuotaLokWis("1", "2020-02-12","2020-01-02","1300", "500", String.valueOf(a)));
              }
//        modelQuotaLokWisList.add(new ModelQuotaLokWis("1", "2020-02-12","2020-01-02","1300", "500", "50"));
//        modelQuotaLokWisList.add(new ModelQuotaLokWis("1", "2020-02-12","2020-01-02","1300", "500", "50"));
//        modelQuotaLokWisList.add(new ModelQuotaLokWis("1", "2020-02-12","2020-01-02","1300", "500", "50"));
//        modelQuotaLokWisList.add(new ModelQuotaLokWis("1", "2020-02-12","2020-01-02","1300", "500", "50"));
//        modelQuotaLokWisList.add(new ModelQuotaLokWis("1", "2020-02-12","2020-01-02","1300", "500", "50"));

        return modelQuotaLokWisList;
    }

    private List<ModelQuotaExample> getMovieList() {
        List<ModelQuotaExample> movieList = new ArrayList<>();
        // src Wikipedia
        movieList.add(new ModelQuotaExample(1, "Pirates of the Caribbean: On Stranger Tides", 2011, 378));
        movieList.add(new ModelQuotaExample(2, "Avengers: Age of Ultron", 2015, 365));
        movieList.add(new ModelQuotaExample(3, "Avengers: Infinity War", 2018, 316));
        movieList.add(new ModelQuotaExample(4, "Pirates of the Caribbean: At World's End", 2007, 300));
        movieList.add(new ModelQuotaExample(5, "Justice League", 2017, 300));
        movieList.add(new ModelQuotaExample(6, "Solo: A Star Wars Story", 2018, 275));
        movieList.add(new ModelQuotaExample(7, "John Carter", 2012, 264));
        movieList.add(new ModelQuotaExample(8, "Batman v Superman: Dawn of Justice", 2016, 263));
        movieList.add(new ModelQuotaExample(9, "Star Wars: The Last Jedi", 2017, 263));
        movieList.add(new ModelQuotaExample(9, "Star Wars: The Last Jedi", 2017, 263));
        movieList.add(new ModelQuotaExample(9, "Star Wars: The Last Jedi", 2017, 263));
        movieList.add(new ModelQuotaExample(9, "Star Wars: The Last Jedi", 2017, 263));
        movieList.add(new ModelQuotaExample(9, "Star Wars: The Last Jedi", 2017, 263));
        movieList.add(new ModelQuotaExample(9, "Star Wars: The Last Jedi", 2017, 263));
        movieList.add(new ModelQuotaExample(9, "Star Wars: The Last Jedi", 2017, 263));
        movieList.add(new ModelQuotaExample(9, "Star Wars: The Last Jedi", 2017, 263));
        movieList.add(new ModelQuotaExample(9, "Star Wars: The Last Jedi", 2017, 263));
        movieList.add(new ModelQuotaExample(9, "Star Wars: The Last Jedi", 2017, 263));
        movieList.add(new ModelQuotaExample(10, "Tangled", 2010, 260));
        movieList.add(new ModelQuotaExample(10, "Tangled", 2010, 260));
        movieList.add(new ModelQuotaExample(10, "Tangled", 2010, 260));
        movieList.add(new ModelQuotaExample(10, "Tangled", 2010, 260));
        movieList.add(new ModelQuotaExample(10, "Tangled", 2010, 260));
        movieList.add(new ModelQuotaExample(10, "Tangled", 2010, 260));
        movieList.add(new ModelQuotaExample(10, "Tangled", 2010, 260));
        movieList.add(new ModelQuotaExample(10, "Tangled", 2010, 260));
        movieList.add(new ModelQuotaExample(10, "Tangled", 2010, 260));

        return movieList;
    }


    public void calculateData(){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
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
                                findViewById(R.id.loadingPanel).setVisibility(View.GONE);

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

                final String key_email =  sessionManager.getUserDetail().get(SessionManager.key_email).trim();
                Log.i("","key_email "+key_email);

                obj.put("alamat_email", key_email);
//                obj.put("alamat_email", "petugas-1");
                return obj;
            }
        };
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }



}

