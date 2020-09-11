package com.amanahgithawisata.aga.Model;

import androidx.annotation.NonNull;

public class SpinnerListWisata {

    public String kdlok;
    public String nmlok;
    public String almtlok;
    public String kotalok;
    public String url_img_lok;

    public SpinnerListWisata(String kdlok, String nmlok, String almtlok, String kotalok, String url_img_lok){
        this.kdlok = kdlok;
        this.nmlok = nmlok;
        this.almtlok = almtlok;
        this.kotalok = kotalok;
        this.url_img_lok = url_img_lok;
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

    public String getAlmtlok() {
        return almtlok;
    }

    public void setAlmtlok(String almtlok) {
        this.almtlok = almtlok;
    }

    public String getKotalok() {
        return kotalok;
    }

    public void setKotalok(String kotalok) {
        this.kotalok = kotalok;
    }

    public String getUrl_img_lok() {
        return url_img_lok;
    }

    public void setUrl_img_lok(String url_img_lok) {
        this.url_img_lok = url_img_lok;
    }
}
