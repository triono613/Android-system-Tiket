package com.example.aga.Model;

import androidx.annotation.NonNull;

public class SpinnerJnsByr {

    public String mode_pembayaran;
    public String nama_pembayaraan;

    public SpinnerJnsByr(String mode_pembayaran, String nama_pembayaraan) {
        this.mode_pembayaran = mode_pembayaran;
        this.nama_pembayaraan = nama_pembayaraan;
    }

    @NonNull
    @Override
    public String toString() {
        return nama_pembayaraan;
    }

    public String getMode_pembayaran() {
        return mode_pembayaran;
    }

    public String getNama_pembayaraan() {
        return nama_pembayaraan;
    }
}
