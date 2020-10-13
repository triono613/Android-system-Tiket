package com.amanahgithawisata.aga.Model;

public class ModelReportKarcisLevel4 {

    public String id;
    public String nama_lokasi_regional;
    public String nama_lokasi_wisata;
    public String nama_lokasi_pintu;
    public String status_karcis;
    public String jumlah_transaksi;
    public String jumlah_wisnu;
    public String jumlah_tambahan;
    public String tagihan_wisata;
    public String tagihan_asuransi;

    public ModelReportKarcisLevel4(String id,String nama_lokasi_regional,String nama_lokasi_wisata, String nama_lokasi_pintu, String status_karcis, String jumlah_transaksi, String jumlah_wisnu, String jumlah_tambahan, String tagihan_wisata, String tagihan_asuransi) {

        this.id = id;
        this.nama_lokasi_regional = nama_lokasi_regional;
        this.nama_lokasi_wisata = nama_lokasi_wisata;
        this.nama_lokasi_pintu = nama_lokasi_pintu;
        this.status_karcis = status_karcis;
        this.jumlah_transaksi = jumlah_transaksi;
        this.jumlah_wisnu = jumlah_wisnu;
        this.jumlah_tambahan = jumlah_tambahan;
        this.tagihan_wisata = tagihan_wisata;
        this.tagihan_asuransi = tagihan_asuransi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama_lokasi_regional() {
        return nama_lokasi_regional;
    }

    public void setNama_lokasi_regional(String nama_lokasi_regional) {
        this.nama_lokasi_regional = nama_lokasi_regional;
    }

    public String getNama_lokasi_wisata() {
        return nama_lokasi_wisata;
    }

    public void setNama_lokasi_wisata(String nama_lokasi_wisata) {
        this.nama_lokasi_wisata = nama_lokasi_wisata;
    }

    public String getNama_lokasi_pintu() {
        return nama_lokasi_pintu;
    }

    public void setNama_lokasi_pintu(String nama_lokasi_pintu) {
        this.nama_lokasi_pintu = nama_lokasi_pintu;
    }

    public String getStatus_karcis() {
        return status_karcis;
    }

    public void setStatus_karcis(String status_karcis) {
        this.status_karcis = status_karcis;
    }

    public String getJumlah_transaksi() {
        return jumlah_transaksi;
    }

    public void setJumlah_transaksi(String jumlah_transaksi) {
        this.jumlah_transaksi = jumlah_transaksi;
    }

    public String getJumlah_wisnu() {
        return jumlah_wisnu;
    }

    public void setJumlah_wisnu(String jumlah_wisnu) {
        this.jumlah_wisnu = jumlah_wisnu;
    }

    public String getJumlah_tambahan() {
        return jumlah_tambahan;
    }

    public void setJumlah_tambahan(String jumlah_tambahan) {
        this.jumlah_tambahan = jumlah_tambahan;
    }

    public String getTagihan_wisata() {
        return tagihan_wisata;
    }

    public void setTagihan_wisata(String tagihan_wisata) {
        this.tagihan_wisata = tagihan_wisata;
    }

    public String getTagihan_asuransi() {
        return tagihan_asuransi;
    }

    public void setTagihan_asuransi(String tagihan_asuransi) {
        this.tagihan_asuransi = tagihan_asuransi;
    }
}
