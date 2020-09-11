package com.amanahgithawisata.aga.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Petugas {
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTgl_lahir() {
        return tgl_lahir;
    }

    public void setTgl_lahir(String tgl_lahir) {
        this.tgl_lahir = tgl_lahir;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getSellular_no() {
        return sellular_no;
    }

    public void setSellular_no(String sellular_no) {
        this.sellular_no = sellular_no;
    }

    public String getAlamat_email() {
        return alamat_email;
    }

    public void setAlamat_email(String alamat_email) {
        this.alamat_email = alamat_email;
    }

    public String getKata_kunci() {
        return kata_kunci;
    }

    public void setKata_kunci(String kata_kunci) {
        this.kata_kunci = kata_kunci;
    }



/*
    @SerializedName("nama")
    @SerializedName("tgl_lahir");
    @SerializedName("jenis_kelamin");
    @SerializedName("sellular_no");
    @SerializedName("kata_kunci");
    @SerializedName("alamat_email");
    */

    private  String nama;
    private  String tgl_lahir;
    private  String jenis_kelamin;
    private  String sellular_no;
    private  String alamat_email;
    private  String kata_kunci;

    public Petugas(){}

    public Petugas( String nama, String tgl_lahir, String jenis_kelamin, String hp, String alamat_email, String kata_kunci  ) {
        this.nama = nama;
        this.tgl_lahir = tgl_lahir;
        this.jenis_kelamin = jenis_kelamin;
        this.sellular_no = sellular_no;
        this.alamat_email = alamat_email;
        this.kata_kunci = kata_kunci;
    }



}
