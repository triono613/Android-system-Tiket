package com.amanahgithawisata.aga.Model;

import androidx.annotation.NonNull;

public class SpinnerJnsByrEksp {

    public String mode_pembayaran_eksp;
    public String nama_pembayaraan_eksp;

    public SpinnerJnsByrEksp(String mode_pembayaran_eksp, String nama_pembayaraan_eksp) {
        this.mode_pembayaran_eksp = mode_pembayaran_eksp;
        this.nama_pembayaraan_eksp = nama_pembayaraan_eksp;
    }

    @NonNull
    @Override
    public String toString() {
        return nama_pembayaraan_eksp;
    }

    public String getMode_pembayaran_eksp() {
        return mode_pembayaran_eksp;
    }

    public String getNama_pembayaraan_eksp() {
        return nama_pembayaraan_eksp;
    }
}
