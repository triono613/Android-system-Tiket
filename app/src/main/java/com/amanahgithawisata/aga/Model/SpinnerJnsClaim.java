package com.amanahgithawisata.aga.Model;

import androidx.annotation.NonNull;

public class SpinnerJnsClaim {

    public String jenis_claim;
    public String  nama_claim;

    public SpinnerJnsClaim(String jenis_claim, String nama_claim) {
        this.jenis_claim = jenis_claim;
        this.nama_claim = nama_claim;
    }

    @NonNull
    @Override
    public String toString() {
        return nama_claim;
    }

    public String getJenis_claim() {
        return jenis_claim;
    }

    public String getNama_claim() {
        return nama_claim;
    }
}
