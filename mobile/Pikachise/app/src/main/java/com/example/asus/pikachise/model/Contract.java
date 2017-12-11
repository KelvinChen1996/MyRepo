package com.example.asus.pikachise.model;

/**
 * Created by Asus on 18/09/2017.
 */

public class Contract {
    public static abstract class NewUserInfo {
        public static final String USER_ID = "user_id";
        public static final String USER_NAME = "user_name";
        public static final String USER_EMAIL = "user_email";
        public static final String USER_PASSWORD = "user_password";
        public static final String TABLE_USER = "user";
    }


    public static abstract class NewDaftarFranchiseInfo {
        public static final String ID_FRANCHISE = "id";
        public static final String NAMA_FRANCHISE = "nama_franchise";
        public static final String NAMA_PT = "nama_pt";
        public static final String KETERANGAN_FRANCHISE = "keterangan_franchise";
        public static final String JENIS_FRANCHISE = "jenis_franchise";
        public static final String KATEGORI_FRANCHISE ="kategori_franchise";
        public static final String BERDIRI_SEJAK ="berdiri_sejak";
        public static final String INVESTASI ="investasi";
        public static final String WEBSITE ="website";
        public static final String ALAMAT ="alamat";
        public static final String LOKASI ="lokasi";
        public static final String TELEPON ="telepon";
        public static final String EMAIL ="email";
        public static final String GAMBAR_FRANCHISE ="gambar_franchise";

        public static final String TABLE_DAFTARFRANCHISE = "daftar_franchise";
    }

    public static abstract class NewEventInfo {
        public static final String ID = "id";
        public static final String JUDUL_EVENT = "Judul_Event";
        public static final String ISI_EVENT = "Isi_Event";
        public static final String GAMBAR_EVENT = "Gambar_Event";
        public static final String ALAMAT_EVENT = "Alamat_Event";
        public static final String WAKTU_EVENT = "Waktu_Event";

        public static final String TABLE_EVENT = "daftar_list_event";
    }

    public static abstract class NewNewsInfo {
        public static final String ID = "id";
        public static final String JUDUL_BERITA = "Judul_Berita";
        public static final String ISI_BERITA = "Isi_Berita";
        public static final String GAMBAR_BERITA = "Gambar_Berita";
        public static final String WAKTU_BERITA= "Waktu_Berita";

        public static final String TABLE_BERITA = "daftar_list_news";
    }

    public static abstract class NewOutletInfo {
        public static final String ID = "id_outlet";
        public static final String ID_FRANCHISE = "id";
        public static final String namaPTFranchisee = "namaPTFranchisee";
        public static final String alamatFranchisee = "alamatFranchisee";
        public static final String contactFranchisee = "contactFranchisee";
        public static final String Owner_Franchisee= "Owner_Franchisee";
        public static final String tahun_berdiri_Franchisee= "tahun_berdiri_Franchisee";

        public static final String TABLE_OUTLET = "daftar_outlet";
    }

    public static abstract class NewBookMark{
        public static final String ID ="id_bookmark";
        public static final String EMAIL_USER = "user_email";
        public static final String ID_FRANCHISE = "id_franchise";
        public static final String TABLE_BOOKMARK = "daftar_bookmark";
    }

    public static abstract class NewMessage{
        public static final String ID_PESAN ="id_pesan";
        public static final String ID_FRANCHISE = "id";
        public static final String USER_EMAIL = "user_email";
        public static final String MESSAGE = "message";
        public static final String TABLE_MESSAGE= "daftar_message";
    }
}
