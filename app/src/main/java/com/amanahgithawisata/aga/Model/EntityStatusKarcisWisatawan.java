package com.amanahgithawisata.aga.Model;

public class EntityStatusKarcisWisatawan {

    public String va;
    public String tgl;
    public String status;
    public String lokWis;
    public String _tgl_kunjungan_sd;
    public String _jumlah_hari;
    public String _bank_name;

    public EntityStatusKarcisWisatawan(String va, String tgl, String status, String lokWis, String _tgl_kunjungan_sd, String _jumlah_hari, String _bank_name ) {
        this.va = va;
        this.tgl = tgl;
        this.status = status;
        this.lokWis = lokWis;

        this._tgl_kunjungan_sd = _tgl_kunjungan_sd;
        this._jumlah_hari = _jumlah_hari;
        this._bank_name = _bank_name;
    }

    public String get_tgl_kunjungan_sd() {
        return _tgl_kunjungan_sd;
    }

    public void set_tgl_kunjungan_sd(String _tgl_kunjungan_sd) {
        this._tgl_kunjungan_sd = _tgl_kunjungan_sd;
    }

    public String get_jumlah_hari() {
        return _jumlah_hari;
    }

    public void set_jumlah_hari(String _jumlah_hari) {
        this._jumlah_hari = _jumlah_hari;
    }

    public String get_bank_name() {
        return _bank_name;
    }

    public void set_bank_name(String _bank_name) {
        this._bank_name = _bank_name;
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
