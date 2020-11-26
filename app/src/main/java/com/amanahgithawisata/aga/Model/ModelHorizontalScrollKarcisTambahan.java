package com.amanahgithawisata.aga.Model;

public class ModelHorizontalScrollKarcisTambahan {

    public String url_lokWis;
    public String url_pintu;
    public String kode_ksda;
    public String nm_ksda;
    public String kode_lokasi;
    public String nm_lokasi_pintu;
    public String kode_karcis;
    public String nama_karcis;
    public String kode_libur;
    public String harga_karcis_wisata;
    public String harga_karcis_asuransi;
    public String tgl_kunj;
    public String id;
    public String url_img_kt;

    public String result_dt_jml_krcs_wisnu;
    public String result_dt_jml_krcs_wisman;
    public String result_dt_ttl_jml_krcs_wisnu_wisman;

    public String result_dt_jml_krcs_tmbhn;
    public String result_dt_ttl_krcs_tmbhn;
    public String result_dt_grand_ttl;

    public String result_dt_id_karcis_utama;
    public String result_dt_id_karcis_tmbhn;

    public String harga_karcis_wisata_wisnu;
    public String harga_karcis_wisata_wisman;
    public String harga_karcis_wisata_tmbhn;
    public String harga_karcis_asuransi_wisnu;
    public String harga_karcis_asuransi_wisman;

    public String  nama_pengunjung;
    public String  hp_pengunjung;
    public String  email_pengunjung;
    public String  mode_pembayaran;
    public String tgl_kujungan_2_val;


    public ModelHorizontalScrollKarcisTambahan(String url_lokWis,
                                            String url_pintu,
                                            String kode_ksda,
                                            String nm_ksda,
                                            String kode_lokasi,
                                            String nm_pintu,
                                            String kode_karcis,
                                            String nama_karcis,
                                            String kode_libur,
                                            String harga_karcis_wisata,
                                            String harga_karcis_asuransi,
                                            String tgl_kunj,
                                            String id,
                                            String url_img_kt,
                                            String result_dt_jml_krcs_wisnu,
                                            String result_dt_jml_krcs_wisman,
                                            String result_dt_ttl_jml_krcs_wisnu_wisman,
                                            String result_dt_jml_krcs_tmbhn,
                                            String result_dt_ttl_krcs_tmbhn,
                                            String result_dt_grand_ttl,
                                            String result_dt_id_karcis_utama,
                                            String result_dt_id_karcis_tmbhn,

                                            String harga_karcis_wisata_wisnu,
                                            String harga_karcis_wisata_wisman,
                                            String harga_karcis_wisata_tmbhn,
                                            String harga_karcis_asuransi_wisnu,
                                            String harga_karcis_asuransi_wisman,

                                           String nama_pengunjung,
                                           String hp_pengunjung,
                                           String email_pengunjung,
                                           String mode_pembayaran,
                                           String tgl_kujungan_2_val
                                   )
    {
        this.url_lokWis = url_lokWis;
        this.url_pintu = url_pintu;
        this.kode_ksda = kode_ksda;
        this.nm_ksda = nm_ksda;
        this.kode_lokasi = kode_lokasi;
        this.nm_lokasi_pintu = nm_pintu;
        this.kode_karcis = kode_karcis;
        this.nama_karcis = nama_karcis;
        this.kode_libur = kode_libur;
        this.harga_karcis_wisata = harga_karcis_wisata;
        this.harga_karcis_asuransi = harga_karcis_asuransi;
        this.tgl_kunj = tgl_kunj;
        this.id = id;
        this.url_img_kt = url_img_kt;

        this.result_dt_jml_krcs_wisnu = result_dt_jml_krcs_wisnu;
        this.result_dt_jml_krcs_wisman = result_dt_jml_krcs_wisman;
        this.result_dt_ttl_jml_krcs_wisnu_wisman = result_dt_ttl_jml_krcs_wisnu_wisman;
        this.result_dt_jml_krcs_tmbhn = result_dt_jml_krcs_tmbhn;
        this.result_dt_ttl_krcs_tmbhn = result_dt_ttl_krcs_tmbhn;
        this.result_dt_grand_ttl = result_dt_grand_ttl;

        this.result_dt_id_karcis_utama = result_dt_id_karcis_utama;
        this.result_dt_id_karcis_tmbhn = result_dt_id_karcis_tmbhn;

        this.harga_karcis_wisata_wisnu = harga_karcis_wisata_wisnu;
        this.harga_karcis_wisata_wisman = harga_karcis_wisata_wisman;
        this.harga_karcis_wisata_tmbhn = harga_karcis_wisata_tmbhn;
        this.harga_karcis_asuransi_wisnu = harga_karcis_asuransi_wisnu;
        this.harga_karcis_asuransi_wisman = harga_karcis_asuransi_wisman;

        this.nama_pengunjung = nama_pengunjung;
        this.hp_pengunjung = hp_pengunjung;
        this.email_pengunjung = email_pengunjung;
        this.mode_pembayaran =  mode_pembayaran;
        this.tgl_kujungan_2_val = tgl_kujungan_2_val;
    }


    public String getTgl_kujungan_2_val() {
        return tgl_kujungan_2_val;
    }

    public void setTgl_kujungan_2_val(String tgl_kujungan_2_val) {
        this.tgl_kujungan_2_val = tgl_kujungan_2_val;
    }

    public String getMode_pembayaran() {
        return mode_pembayaran;
    }

    public void setMode_pembayaran(String mode_pembayaran) {
        this.mode_pembayaran = mode_pembayaran;
    }

    public String getNama_pengunjung() {
        return nama_pengunjung;
    }

    public void setNama_pengunjung(String nama_pengunjung) {
        this.nama_pengunjung = nama_pengunjung;
    }

    public String getHp_pengunjung() {
        return hp_pengunjung;
    }

    public void setHp_pengunjung(String hp_pengunjung) {
        this.hp_pengunjung = hp_pengunjung;
    }

    public String getEmail_pengunjung() {
        return email_pengunjung;
    }

    public void setEmail_pengunjung(String email_pengunjung) {
        this.email_pengunjung = email_pengunjung;
    }

    public String getHarga_karcis_wisata_wisnu() {
        return harga_karcis_wisata_wisnu;
    }

    public void setHarga_karcis_wisata_wisnu(String harga_karcis_wisata_wisnu) {
        this.harga_karcis_wisata_wisnu = harga_karcis_wisata_wisnu;
    }

    public String getHarga_karcis_wisata_wisman() {
        return harga_karcis_wisata_wisman;
    }

    public void setHarga_karcis_wisata_wisman(String harga_karcis_wisata_wisman) {
        this.harga_karcis_wisata_wisman = harga_karcis_wisata_wisman;
    }

    public String getHarga_karcis_wisata_tmbhn() {
        return harga_karcis_wisata_tmbhn;
    }

    public void setHarga_karcis_wisata_tmbhn(String harga_karcis_wisata_tmbhn) {
        this.harga_karcis_wisata_tmbhn = harga_karcis_wisata_tmbhn;
    }

    public String getHarga_karcis_asuransi_wisnu() {
        return harga_karcis_asuransi_wisnu;
    }

    public void setHarga_karcis_asuransi_wisnu(String harga_karcis_asuransi_wisnu) {
        this.harga_karcis_asuransi_wisnu = harga_karcis_asuransi_wisnu;
    }

    public String getHarga_karcis_asuransi_wisman() {
        return harga_karcis_asuransi_wisman;
    }

    public void setHarga_karcis_asuransi_wisman(String harga_karcis_asuransi_wisman) {
        this.harga_karcis_asuransi_wisman = harga_karcis_asuransi_wisman;
    }

    public String getResult_dt_id_karcis_utama() {
        return result_dt_id_karcis_utama;
    }

    public void setResult_dt_id_karcis_utama(String result_dt_id_karcis_utama) {
        this.result_dt_id_karcis_utama = result_dt_id_karcis_utama;
    }

    public String getResult_dt_id_karcis_tmbhn() {
        return result_dt_id_karcis_tmbhn;
    }

    public void setResult_dt_id_karcis_tmbhn(String result_dt_id_karcis_tmbhn) {
        this.result_dt_id_karcis_tmbhn = result_dt_id_karcis_tmbhn;
    }

    public String getResult_dt_jml_krcs_tmbhn() {
        return result_dt_jml_krcs_tmbhn;
    }

    public void setResult_dt_jml_krcs_tmbhn(String result_dt_jml_krcs_tmbhn) {
        this.result_dt_jml_krcs_tmbhn = result_dt_jml_krcs_tmbhn;
    }

    public String getResult_dt_ttl_krcs_tmbhn() {
        return result_dt_ttl_krcs_tmbhn;
    }

    public void setResult_dt_ttl_krcs_tmbhn(String result_dt_ttl_krcs_tmbhn) {
        this.result_dt_ttl_krcs_tmbhn = result_dt_ttl_krcs_tmbhn;
    }

    public String getResult_dt_grand_ttl() {
        return result_dt_grand_ttl;
    }

    public void setResult_dt_grand_ttl(String result_dt_grand_ttl) {
        this.result_dt_grand_ttl = result_dt_grand_ttl;
    }

    public String getResult_dt_jml_krcs_wisnu() {
        return result_dt_jml_krcs_wisnu;
    }

    public void setResult_dt_jml_krcs_wisnu(String result_dt_jml_krcs_wisnu) {
        this.result_dt_jml_krcs_wisnu = result_dt_jml_krcs_wisnu;
    }

    public String getResult_dt_jml_krcs_wisman() {
        return result_dt_jml_krcs_wisman;
    }

    public void setResult_dt_jml_krcs_wisman(String result_dt_jml_krcs_wisman) {
        this.result_dt_jml_krcs_wisman = result_dt_jml_krcs_wisman;
    }

    public String getResult_dt_ttl_jml_krcs_wisnu_wisman() {
        return result_dt_ttl_jml_krcs_wisnu_wisman;
    }

    public void setResult_dt_ttl_jml_krcs_wisnu_wisman(String result_dt_ttl_jml_krcs_wisnu_wisman) {
        this.result_dt_ttl_jml_krcs_wisnu_wisman = result_dt_ttl_jml_krcs_wisnu_wisman;
    }

    public String getUrl_lokWis() {
        return url_lokWis;
    }

    public void setUrl_lokWis(String url_lokWis) {
        this.url_lokWis = url_lokWis;
    }

    public String getUrl_pintu() {
        return url_pintu;
    }

    public void setUrl_pintu(String url_pintu) {
        this.url_pintu = url_pintu;
    }

    public String getNm_lokasi_pintu() {
        return nm_lokasi_pintu;
    }

    public void setNm_lokasi_pintu(String nm_lokasi_pintu) {
        this.nm_lokasi_pintu = nm_lokasi_pintu;
    }

    public String getTgl_kunj() {
        return tgl_kunj;
    }

    public void setTgl_kunj(String tgl_kunj) {
        this.tgl_kunj = tgl_kunj;
    }

    public String getNm_ksda() {
        return nm_ksda;
    }

    public void setNm_ksda(String nm_ksda) {
        this.nm_ksda = nm_ksda;
    }

    public String getNm_pintu() {
        return nm_lokasi_pintu;
    }

    public void setNm_pintu(String nm_pintu) {
        this.nm_lokasi_pintu = nm_pintu;
    }

    public String getKode_ksda() {
        return kode_ksda;
    }

    public void setKode_ksda(String kode_ksda) {
        this.kode_ksda = kode_ksda;
    }

    public String getKode_lokasi() {
        return kode_lokasi;
    }

    public void setKode_lokasi(String kode_lokasi) {
        this.kode_lokasi = kode_lokasi;
    }

    public String getKode_karcis() {
        return kode_karcis;
    }

    public void setKode_karcis(String kode_karcis) {
        this.kode_karcis = kode_karcis;
    }

    public String getNama_karcis() {
        return nama_karcis;
    }

    public void setNama_karcis(String nama_karcis) {
        this.nama_karcis = nama_karcis;
    }

    public String getKode_libur() {
        return kode_libur;
    }

    public void setKode_libur(String kode_libur) {
        this.kode_libur = kode_libur;
    }

    public String getHarga_karcis_wisata() {
        return harga_karcis_wisata;
    }

    public void setHarga_karcis_wisata(String harga_karcis_wisata) {
        this.harga_karcis_wisata = harga_karcis_wisata;
    }


    public String getHarga_karcis_asuransi() {
        return harga_karcis_asuransi;
    }

    public void setHarga_karcis_asuransi(String harga_karcis_asuransi) {
        this.harga_karcis_asuransi = harga_karcis_asuransi;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl_img_kt() {
        return url_img_kt;
    }

    public void setUrl_img_kt(String url_img_kt) {
        this.url_img_kt = url_img_kt;
    }
}

