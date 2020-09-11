package com.amanahgithawisata.aga.Model;

import androidx.annotation.NonNull;

public class SpinnerListWisataKsda {

    public String kdksda;
    public String nmlok;
    public String image;

    public SpinnerListWisataKsda(String kdksda, String nmlok,String image) {
        this.kdksda = kdksda;
        this.nmlok = nmlok;
        this.image = image;
    }

    @NonNull
    @Override
    public String toString() {
        return nmlok;
    }

    public String getKdksda() {
        return kdksda;
    }

    public void setKdksda(String kdksda) {
        this.kdksda = kdksda;
    }

    public String getNmlok() {
        return nmlok;
    }

    public void setNmlok(String nmlok) {
        this.nmlok = nmlok;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
