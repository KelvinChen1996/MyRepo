package com.example.asus.pikachise.model;

/**
 * Created by Asus on 18/09/2017.
 */

public class News {
    private Integer id_berita;
    private String Judul_berita;
    private String Isi_berita;
    private String Gambar_berita;
    private String Waktu_berita;

    public Integer getId_berita() {
        return id_berita;
    }

    public void setId_berita(Integer id_berita) {
        this.id_berita = id_berita;
    }

    public String getJudul_berita() {
        return Judul_berita;
    }

    public void setJudul_berita(String judul_berita) {
        Judul_berita = judul_berita;
    }

    public String getIsi_berita() {
        return Isi_berita;
    }

    public void setIsi_berita(String isi_berita) {
        Isi_berita = isi_berita;
    }

    public String getGambar_berita() {
        return Gambar_berita;
    }

    public void setGambar_berita(String gambar_berita) {
        Gambar_berita = gambar_berita;
    }

    public String getWaktu_berita() {
        return Waktu_berita;
    }

    public void setWaktu_berita(String waktu_berita) {
        Waktu_berita = waktu_berita;
    }
}
