package com.amanahgithawisata.aga.Model;

public class ModelApiWeather {

    public int ico;
    public String weather;
    public String coord;
    public String namaKota;
    public String kelembaban;
    public String temperatur;
    public String kecepatanAngin;

    public ModelApiWeather(int ico,String weather, String coord, String namaKota, String kelembaban, String temperatur, String kecepatanAngin) {
        this.ico = ico;
        this.weather = weather;
        this.coord = coord;
        this.namaKota = namaKota;
        this.kelembaban = kelembaban;
        this.temperatur = temperatur;
        this.kecepatanAngin = kecepatanAngin;
    }

    public int getIco() {
        return ico;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public void setIco(int ico) {
        this.ico = ico;
    }

    public String getCoord() {
        return coord;
    }

    public void setCoord(String coord) {
        this.coord = coord;
    }

    public String getNamaKota() {
        return namaKota;
    }

    public void setNamaKota(String namaKota) {
        this.namaKota = namaKota;
    }

    public String getKelembaban() {
        return kelembaban;
    }

    public void setKelembaban(String kelembaban) {
        this.kelembaban = kelembaban;
    }

    public String getTemperatur() {
        return temperatur;
    }

    public void setTemperatur(String temperatur) {
        this.temperatur = temperatur;
    }

    public String getKecepatanAngin() {
        return kecepatanAngin;
    }

    public void setKecepatanAngin(String kecepatanAngin) {
        this.kecepatanAngin = kecepatanAngin;
    }
}
