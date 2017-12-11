package com.example.asus.pikachise.model;

/**
 * Created by Asus on 18/09/2017.
 */

public class Franchise {
    private int id;
    private String nama_franchise;
    private String keterangan;
    private String jenis;
    private String kategori;
    private String berdiri_sejak;
    private long investasi;
    private String website;
    private String alamat;
    private String lokasi;
    private String telepon;
    private String email;
    private String gambar_franchise;
    private String nama_pt_franchisor;

    public Franchise() {

    }

    public String getGambar_franchise() {
        return gambar_franchise;
    }

    public void setGambar_franchise(String gambar_franchise) {
        this.gambar_franchise = gambar_franchise;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama_franchise() {
        return nama_franchise;
    }

    public void setNama_franchise(String nama_franchise) {
        this.nama_franchise = nama_franchise;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getBerdiri_sejak() {
        return berdiri_sejak;
    }

    public void setBerdiri_sejak(String berdiri_sejak) {
        this.berdiri_sejak = berdiri_sejak;
    }

    public long getInvestasi() {
        return investasi;
    }

    public void setInvestasi(long investasi) {
        this.investasi = investasi;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNama_pt_franchisor() {
        return nama_pt_franchisor;
    }

    public void setNama_pt_franchisor(String nama_pt_franchisor) {
        this.nama_pt_franchisor = nama_pt_franchisor;
    }
}
