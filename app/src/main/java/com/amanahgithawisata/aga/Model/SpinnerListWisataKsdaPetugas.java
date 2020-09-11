package com.amanahgithawisata.aga.Model;

import androidx.annotation.NonNull;

public class SpinnerListWisataKsdaPetugas {

    public String kdksda;
    public String nmlok;
    public String ttl_pengunjung;
    public String ttl_berkunjung;
    public String ttl_semua;

    public SpinnerListWisataKsdaPetugas(String kdksda, String nmlok, String ttl_pengunjung, String ttl_berkunjung, String ttl_semua) {
        this.kdksda = kdksda;
        this.nmlok = nmlok;
        this.ttl_pengunjung = ttl_pengunjung;
        this.ttl_berkunjung = ttl_berkunjung;
        this.ttl_semua = ttl_semua;
    }

    @NonNull
    @Override
    public String toString() {
        return nmlok;
    }

    public String getKdksda() {
        return kdksda;
    }

    public String getNmlok() {
        return nmlok;
    }

    public String getTtl_pengunjung() {
        return ttl_pengunjung;
    }

    public String getTtl_berkunjung() {
        return ttl_berkunjung;
    }

    public String getTtl_semua() {
        return ttl_semua;
    }
}
