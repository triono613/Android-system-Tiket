package com.amanahgithawisata.aga.Model;

import androidx.annotation.NonNull;

public class SpinnerKarcisUtama {

    public String kode_ksda;
    public String kode_lokasi;
    public String kode_karcis;
    public String nama_karcis;
    public String kode_libur;
    public String harga_karcis_wisata_wisnu;
    public String harga_karcis_wisata_wisman;
    public String harga_karcis_asuransi_wisnu;
    public String harga_karcis_asuransi_wisman;
    public String id;

    public SpinnerKarcisUtama(String kode_ksda, String kode_lokasi, String kode_karcis, String nama_karcis, String kode_libur, String harga_karcis_wisata_wisnu, String harga_karcis_wisata_wisman, String harga_karcis_asuransi_wisnu, String harga_karcis_asuransi_wisman, String id) {
        this.kode_ksda = kode_ksda;
        this.kode_lokasi = kode_lokasi;
        this.kode_karcis = kode_karcis;
        this.nama_karcis = nama_karcis;
        this.kode_libur = kode_libur;
        this.harga_karcis_wisata_wisnu = harga_karcis_wisata_wisnu;
        this.harga_karcis_wisata_wisman = harga_karcis_wisata_wisman;
        this.harga_karcis_asuransi_wisnu = harga_karcis_asuransi_wisnu;
        this.harga_karcis_asuransi_wisman = harga_karcis_asuransi_wisman;
        this.id = id;
    }

    @NonNull
    @Override
    public String toString() {
        return nama_karcis;
    }

    public String getKode_ksda() {
        return kode_ksda;
    }

    public String getKode_lokasi() {
        return kode_lokasi;
    }

    public String getKode_karcis() {
        return kode_karcis;
    }

    public String getNama_karcis() {
        return nama_karcis;
    }

    public String getKode_libur() {
        return kode_libur;
    }

    public String getHarga_karcis_wisata_wisnu() {
        return harga_karcis_wisata_wisnu;
    }

    public String getHarga_karcis_wisata_wisman() {
        return harga_karcis_wisata_wisman;
    }

    public String getHarga_karcis_asuransi_wisnu() {
        return harga_karcis_asuransi_wisnu;
    }

    public String getHarga_karcis_asuransi_wisman() {
        return harga_karcis_asuransi_wisman;
    }

    public String getId() {
        return id;
    }
}
