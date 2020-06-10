package com.example.aga.Model;

public class EntityStatusKarcisWisatawan {

    public String va;
    public String tgl;
    public String status;
    public String lokWis;

    public EntityStatusKarcisWisatawan(String va, String tgl, String status, String lokWis) {
        this.va = va;
        this.tgl = tgl;
        this.status = status;
        this.lokWis = lokWis;
    }

    public String getVa() {
        return va;
    }

    public void setVa(String va) {
        this.va = va;
    }

    public String getTgl() {
        return tgl;
    }

    public void setTgl(String tgl) {
        this.tgl = tgl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLokWis() {
        return lokWis;
    }

    public void setLokWis(String lokWis) {
        this.lokWis = lokWis;
    }
}
