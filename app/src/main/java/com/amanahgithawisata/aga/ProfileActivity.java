package com.amanahgithawisata.aga;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.amanahgithawisata.aga.Adapter.TcAdapter;
import com.amanahgithawisata.aga.Model.ModelTC;

import java.util.ArrayList;


public class ProfileActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<ModelTC> modelTCList;
    TcAdapter tcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        recyclerView = findViewById(R.id.recyclerViewTC);

        initData();
        initRecyclerView();

    }

    private void initRecyclerView() {
        tcAdapter = new TcAdapter(modelTCList,getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(tcAdapter);
    }

    private void initData() {


        modelTCList = new ArrayList<>();
        modelTCList.add(new ModelTC(R.drawable.ic_menu_black_24dp,"http://kaffah.amanahgitha.com/~androidwisata/syarat_ketentuan.html","Syarat & Ketentuan", "", "", ""));
        modelTCList.add(new ModelTC(R.drawable.ic_baseline_record_voice_over_24,"http://kaffah.amanahgitha.com/~androidwisata/informasi.html","Informasi", "", "", ""));
        modelTCList.add(new ModelTC(R.drawable.ic_baseline_phone_24,"http://kaffah.amanahgitha.com/~androidwisata/call_centre_email.html","Call Center", "", "", ""));
//        modelTCList.add(new ModelTC(R.drawable.ic_baseline_info_24,"http://kaffah.amanahgitha.com/~androidwisata/tentang.html","Tentang", "", "", ""));


    }

}