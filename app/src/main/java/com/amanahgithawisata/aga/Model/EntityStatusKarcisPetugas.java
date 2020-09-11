package com.amanahgithawisata.aga.Model;

public class EntityStatusKarcisPetugas {

    public String va;
    public String tgl;
    public String status;
    public String lokWis;
    public String email;


    public EntityStatusKarcisPetugas(String va, String tgl, String status, String lokWis, String email) {
        this.va = va;
        this.tgl = tgl;
        this.status = status;
        this.lokWis = lokWis;
        this.email = email;
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

    public String getEmail() {  return email; }

    public void setEmail(String email) {  this.email = email; }

}
