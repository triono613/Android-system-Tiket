package com.example.aga.Model;

import androidx.annotation.NonNull;

public class SpinnerListWisataKsda {

    public String kdksda;
    public String nmlok;

    public SpinnerListWisataKsda(String kdksda, String nmlok){
        this.kdksda = kdksda;
        this.nmlok = nmlok;
    }

    @NonNull
    @Override
    public String toString() {
        return nmlok;
    }

    public String getKdksda(){
        return this.kdksda;
    }

    public String getNmlok(){
        return this.nmlok;
    }

}
