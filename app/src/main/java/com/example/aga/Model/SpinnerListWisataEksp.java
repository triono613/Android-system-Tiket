package com.example.aga.Model;

import androidx.annotation.NonNull;

public class SpinnerListWisataEksp {


    public String kdlok_eksp;
    public String nmlok_eksp;

    public SpinnerListWisataEksp(String kdlok_eksp, String nmlok_eksp) {
        this.kdlok_eksp = kdlok_eksp;
        this.nmlok_eksp = nmlok_eksp;
    }

    @NonNull
    @Override
    public String toString() {
        return nmlok_eksp;
    }

    public String getKdlok_eksp() {
        return kdlok_eksp;
    }

    public String getNmlok_eksp() {
        return nmlok_eksp;
    }
}
