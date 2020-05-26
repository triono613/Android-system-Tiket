package com.example.aga.Model;

import androidx.annotation.NonNull;

public class SpinnerListWisata {

    public String kdlok;
    public String nmlok;

    public SpinnerListWisata(String kdlok, String nmlok){
        this.kdlok = kdlok;
        this.nmlok = nmlok;
    }

    @NonNull
    @Override
    public String toString() {
        return nmlok;
    }

    public String getKdlok(){
        return this.kdlok;
    }

    public String getNmlok(){
        return this.nmlok;
    }

}
