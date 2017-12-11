package com.example.asus.pikachise.model;

import com.example.asus.pikachise.presenter.api.apiUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by WilliamSumitro on 02/12/2017.
 */

public class DocumentStatus {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("franchise_id")
    @Expose
    private String franchiseId;
    @SerializedName("tdp")
    @Expose
    private String tdp;
    @SerializedName("siup")
    @Expose
    private String siup;
    @SerializedName("suratperjanjian")
    @Expose
    private String suratperjanjian;
    @SerializedName("stpw")
    @Expose
    private String stpw;
    @SerializedName("ktpfranchisor")
    @Expose
    private String ktpfranchisor;
    @SerializedName("companyprofile")
    @Expose
    private String companyprofile;
    @SerializedName("laporankeuangan2tahunterakhir")
    @Expose
    private String laporankeuangan2tahunterakhir;
    @SerializedName("suratizinteknis")
    @Expose
    private String suratizinteknis;
    @SerializedName("tandabuktipendaftaran")
    @Expose
    private String tandabuktipendaftaran;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFranchiseId() {
        return franchiseId;
    }

    public void setFranchiseId(String franchiseId) {
        this.franchiseId = franchiseId;
    }

    public String getTdp() {
        return apiUtils.getUrlImage() + tdp;
    }

    public void setTdp(String tdp) {
        this.tdp = tdp;
    }

    public String getSiup() {
        return apiUtils.getUrlImage() + siup;
    }

    public void setSiup(String siup) {
        this.siup = siup;
    }

    public String getSuratperjanjian() {
        return apiUtils.getUrlImage() + suratperjanjian;
    }

    public void setSuratperjanjian(String suratperjanjian) {
        this.suratperjanjian = suratperjanjian;
    }

    public String getStpw() {
        return apiUtils.getUrlImage() + stpw;
    }

    public void setStpw(String stpw) {
        this.stpw = stpw;
    }

    public String getKtpfranchisor() {
        return apiUtils.getUrlImage() + ktpfranchisor;
    }

    public void setKtpfranchisor(String ktpfranchisor) {
        this.ktpfranchisor = ktpfranchisor;
    }

    public String getCompanyprofile() {
        return apiUtils.getUrlImage() + companyprofile;
    }

    public void setCompanyprofile(String companyprofile) {
        this.companyprofile = companyprofile;
    }

    public String getLaporankeuangan2tahunterakhir() {
        return apiUtils.getUrlImage() + laporankeuangan2tahunterakhir;
    }

    public void setLaporankeuangan2tahunterakhir(String laporankeuangan2tahunterakhir) {
        this.laporankeuangan2tahunterakhir = laporankeuangan2tahunterakhir;
    }

    public String getSuratizinteknis() {
        return apiUtils.getUrlImage() + suratizinteknis;
    }

    public void setSuratizinteknis(String suratizinteknis) {
        this.suratizinteknis = suratizinteknis;
    }

    public String getTandabuktipendaftaran() {
        return apiUtils.getUrlImage() + tandabuktipendaftaran;
    }

    public void setTandabuktipendaftaran(String tandabuktipendaftaran) {
        this.tandabuktipendaftaran = tandabuktipendaftaran;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
