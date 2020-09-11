package com.amanahgithawisata.aga.Model;

import androidx.annotation.NonNull;

public class SpinnerKarcisTambahan {

    public String kode_karcis;
    public String nama_karcis;
    public String harga_karcis_wisata;
    public String id_tmbhn;




    public SpinnerKarcisTambahan(String kode_karcis, String nama_karcis,
                                 String harga_karcis_wisata, String id_tmbhn)
    {
        this.kode_karcis = kode_karcis;
        this.nama_karcis = nama_karcis;
        this.harga_karcis_wisata = harga_karcis_wisata;
        this.id_tmbhn = id_tmbhn;
    }

    @NonNull
    @Override
    public String toString() {
        return nama_karcis;
    }

    public String getKode_karcis() {
        return kode_karcis;
    }
    public String getNama_karcis() {
        return nama_karcis;
    }
    public String getHarga_karcis_wisata() {
        return harga_karcis_wisata;
    }
    public  String getId_tmbhn(){
        return id_tmbhn;
    }

}

