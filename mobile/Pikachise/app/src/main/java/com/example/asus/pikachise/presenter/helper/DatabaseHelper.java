//package com.example.asus.pikachise.presenter.helper;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.database.sqlite.SQLiteStatement;
//import android.util.Log;
//
//import com.example.asus.pikachise.model.ChatMessage;
//import com.example.asus.pikachise.model.Contract;
//import com.example.asus.pikachise.model.Event;
//import com.example.asus.pikachise.model.Franchise;
//import com.example.asus.pikachise.model.News;
//import com.example.asus.pikachise.model.User;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by Asus on 18/09/2017.
// */
//
//public class DatabaseHelper extends SQLiteOpenHelper{
//    private static final int DATABASE_VERSION = 1;
//    private static final String DATABASE_NAME = "UserManager.db";
//
//    private static final String CREATE_USER_TABLE = "CREATE TABLE " +
//            Contract.NewUserInfo.TABLE_USER + "(" +
//            Contract.NewUserInfo.USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
//            Contract.NewUserInfo.USER_NAME + " TEXT," +
//            Contract.NewUserInfo.USER_EMAIL + " TEXT," +
//            Contract.NewUserInfo.USER_PASSWORD + " TEXT" +
//            ")";
//
//    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + Contract.NewUserInfo.TABLE_USER;
//    private String DROP_DAFTARFRANCHISE_TABLE = "DROP TABLE IF EXISTS " + Contract.NewDaftarFranchiseInfo.TABLE_DAFTARFRANCHISE;
//    private String DROP_EVENT_TABLE = "DROP TABLE IF EXISTS " + Contract.NewEventInfo.TABLE_EVENT;
//    private String DROP_NEWS_TABLE = "DROP TABLE IF EXISTS " + Contract.NewNewsInfo.TABLE_BERITA;
//    private String DROP_OUTLET_TABLE = "DROP TABLE IF EXISTS " + Contract.NewOutletInfo.TABLE_OUTLET;
//    private String DROP_BOOKMARK_TABBLE = "DROP TABLE IF EXISTS " + Contract.NewBookMark.TABLE_BOOKMARK;
//    private String DROP_MESSAGE_TABBLE = "DROP TABLE IF EXISTS " + Contract.NewMessage.TABLE_MESSAGE;
//
//
//    private static final String CREATE_DAFTARFRANCHISE_TABLE = "CREATE TABLE " +
//            Contract.NewDaftarFranchiseInfo.TABLE_DAFTARFRANCHISE + "(" +
//            Contract.NewDaftarFranchiseInfo.ID_FRANCHISE + " INTEGER PRIMARY KEY AUTOINCREMENT," +
//            Contract.NewDaftarFranchiseInfo.NAMA_FRANCHISE + " TEXT ," +
//            Contract.NewDaftarFranchiseInfo.NAMA_PT + " TEXT ," +
//            Contract.NewDaftarFranchiseInfo.KETERANGAN_FRANCHISE + " TEXT ," +
//            Contract.NewDaftarFranchiseInfo.JENIS_FRANCHISE + " TEXT ," +
//            Contract.NewDaftarFranchiseInfo.KATEGORI_FRANCHISE + " TEXT ," +
//            Contract.NewDaftarFranchiseInfo.BERDIRI_SEJAK + " TEXT ," +
//            Contract.NewDaftarFranchiseInfo.INVESTASI + " LONG ," +
//            Contract.NewDaftarFranchiseInfo.WEBSITE + " TEXT ," +
//            Contract.NewDaftarFranchiseInfo.ALAMAT + " TEXT ," +
//            Contract.NewDaftarFranchiseInfo.LOKASI + " TEXT ," +
//            Contract.NewDaftarFranchiseInfo.TELEPON + " TEXT ," +
//            Contract.NewDaftarFranchiseInfo.EMAIL + " TEXT ," +
//            Contract.NewDaftarFranchiseInfo.GAMBAR_FRANCHISE + " TEXT " +
//            ")";
//
//    private String CREATE_DAFTARLISTEVENT_TABLE = "CREATE TABLE " +
//            Contract.NewEventInfo.TABLE_EVENT + "(" +
//            Contract.NewEventInfo.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
//            Contract.NewEventInfo.JUDUL_EVENT + " TEXT ," +
//            Contract.NewEventInfo.ISI_EVENT + " TEXT ," +
//            Contract.NewEventInfo.GAMBAR_EVENT + " TEXT ," +
//            Contract.NewEventInfo.ALAMAT_EVENT + " TEXT ," +
//            Contract.NewEventInfo.WAKTU_EVENT + " TEXT " +
//            ")";
//    private String CREATE_DAFTARLISTNEWS_TABLE = "CREATE TABLE " +
//            Contract.NewNewsInfo.TABLE_BERITA + " (" +
//            Contract.NewNewsInfo.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
//            Contract.NewNewsInfo.JUDUL_BERITA + " TEXT ," +
//            Contract.NewNewsInfo.ISI_BERITA + " TEXT ," +
//            Contract.NewNewsInfo.GAMBAR_BERITA + " TEXT ," +
//            Contract.NewNewsInfo.WAKTU_BERITA + " TEXT " +
//            ")";
//
//    private String CREATE_DAFTAROUTLET_TABLE = "CREATE TABLE " +
//            Contract.NewOutletInfo.TABLE_OUTLET + " (" +
//            Contract.NewOutletInfo.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
//            Contract.NewOutletInfo.ID_FRANCHISE + " INTEGER ," +
//            Contract.NewOutletInfo.namaPTFranchisee + " TEXT ," +
//            Contract.NewOutletInfo.alamatFranchisee + " TEXT ," +
//            Contract.NewOutletInfo.contactFranchisee + " TEXT ," +
//            Contract.NewOutletInfo.Owner_Franchisee + " TEXT ," +
//            Contract.NewOutletInfo.tahun_berdiri_Franchisee + " TEXT " +
//            ")";
//
//    private String CREATE_BOOKMARK_TABLE = "CREATE TABLE " +
//            Contract.NewBookMark.TABLE_BOOKMARK + " (" +
//            Contract.NewBookMark.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
//            Contract.NewBookMark.EMAIL_USER + " TEXT ," +
//            Contract.NewBookMark.ID_FRANCHISE + " INTEGER " +
//            ")";
//    private String CREATE_MESSAGE_TABLE = "CREATE TABLE " +
//            Contract.NewMessage.TABLE_MESSAGE + " (" +
//            Contract.NewMessage.ID_PESAN + " INTEGER PRIMARY KEY AUTOINCREMENT," +
//            Contract.NewMessage.USER_EMAIL + " TEXT ," +
//            Contract.NewMessage.ID_FRANCHISE + " INTEGER ," +
//            Contract.NewMessage.MESSAGE + " TEXT " +
//            ")";
//    public DatabaseHelper(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(CREATE_USER_TABLE);
//        db.execSQL(CREATE_DAFTARFRANCHISE_TABLE);
//        db.execSQL(CREATE_DAFTARLISTEVENT_TABLE);
//        db.execSQL(CREATE_DAFTARLISTNEWS_TABLE);
//        db.execSQL(CREATE_DAFTAROUTLET_TABLE);
//        db.execSQL(CREATE_BOOKMARK_TABLE);
//        db.execSQL(CREATE_MESSAGE_TABLE);
//        Log.e("DATABASE OPERATIONS", "Table Created");
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL(DROP_USER_TABLE);
//        db.execSQL(DROP_DAFTARFRANCHISE_TABLE);
//        db.execSQL(DROP_EVENT_TABLE);
//        db.execSQL(DROP_OUTLET_TABLE);
//        db.execSQL(DROP_NEWS_TABLE);
//        db.execSQL(DROP_BOOKMARK_TABBLE);
//        db.execSQL(DROP_MESSAGE_TABBLE);
//        Log.e("DATABASE OPERATIONS", "Table Droped");
//        onCreate(db);
//    }
//    public void addUser(User user) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//
//        values.put(Contract.NewUserInfo.USER_NAME, user.getName());
//        values.put(Contract.NewUserInfo.USER_EMAIL, user.getEmail());
//        values.put(Contract.NewUserInfo.USER_PASSWORD, user.getPassword());
//        db.insert(Contract.NewUserInfo.TABLE_USER, null, values);
//        db.close();
//    }
//
//
//
//    public void updateUser(User user) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(Contract.NewUserInfo.USER_NAME, user.getName());
//        values.put(Contract.NewUserInfo.USER_EMAIL, user.getEmail());
//        values.put(Contract.NewUserInfo.USER_PASSWORD, user.getPassword());
//        db.update(Contract.NewUserInfo.TABLE_USER, values, Contract.NewUserInfo.USER_EMAIL + " = ?",
//                new String[]{String.valueOf(user.getEmail())});
//        db.close();
//    }
//
//
//    public boolean checkUser(String email) {
//
//        String[] columns = {
//                Contract.NewUserInfo.USER_ID
//        };
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        String selection = Contract.NewUserInfo.USER_EMAIL + " = ?";
//
//        String[] selectionArgs = {email};
//
//        Cursor cursor = db.query(Contract.NewUserInfo.TABLE_USER,
//                columns,
//                selection,
//                selectionArgs,
//                null,
//                null,
//                null);
//        int cursorCount = cursor.getCount();
//        cursor.close();
//        db.close();
//
//        if (cursorCount > 0) {
//            return true;
//        }
//
//        return false;
//    }
//
//
//    public boolean checkUser(String email, String password) {
//
//        String[] columns = {
//                Contract.NewUserInfo.USER_ID
//        };
//        SQLiteDatabase db = this.getReadableDatabase();
//        String selection = Contract.NewUserInfo.USER_EMAIL + " = ?" + " AND " + Contract.NewUserInfo.USER_PASSWORD + " = ?";
//
//        String[] selectionArgs = {email, password};
//
//        Cursor cursor = db.query(Contract.NewUserInfo.TABLE_USER,
//                columns,
//                selection,
//                selectionArgs,
//                null,
//                null,
//                null);
//
//        int cursorCount = cursor.getCount();
//
//        cursor.close();
//        db.close();
//        if (cursorCount > 0) {
//            return true;
//        }
//
//        return false;
//    }
//    public void addFranchise(Franchise franchise) {
//        if (!checkFranchise(franchise.getId())) {
//            SQLiteDatabase db = this.getWritableDatabase();
//            ContentValues values = new ContentValues();
//            values.put(Contract.NewDaftarFranchiseInfo.ID_FRANCHISE, franchise.getId());
//            values.put(Contract.NewDaftarFranchiseInfo.NAMA_FRANCHISE, franchise.getNama_franchise());
//            values.put(Contract.NewDaftarFranchiseInfo.NAMA_PT, franchise.getNama_pt_franchisor());
//            values.put(Contract.NewDaftarFranchiseInfo.KETERANGAN_FRANCHISE, franchise.getKeterangan());
//            values.put(Contract.NewDaftarFranchiseInfo.JENIS_FRANCHISE, franchise.getJenis());
//            values.put(Contract.NewDaftarFranchiseInfo.KATEGORI_FRANCHISE, franchise.getKategori());
//            values.put(Contract.NewDaftarFranchiseInfo.BERDIRI_SEJAK, franchise.getBerdiri_sejak());
//            values.put(Contract.NewDaftarFranchiseInfo.INVESTASI, franchise.getInvestasi());
//            values.put(Contract.NewDaftarFranchiseInfo.WEBSITE, franchise.getWebsite());
//            values.put(Contract.NewDaftarFranchiseInfo.ALAMAT, franchise.getAlamat());
//            values.put(Contract.NewDaftarFranchiseInfo.LOKASI, franchise.getLokasi());
//            values.put(Contract.NewDaftarFranchiseInfo.TELEPON, franchise.getTelepon());
//            values.put(Contract.NewDaftarFranchiseInfo.EMAIL, franchise.getEmail());
//            values.put(Contract.NewDaftarFranchiseInfo.GAMBAR_FRANCHISE, franchise.getGambar_franchise());
//            db.insert(Contract.NewDaftarFranchiseInfo.TABLE_DAFTARFRANCHISE, null, values);
//            db.close();
//        }
//    }
//
//    public boolean checkFranchise(int id) {
//        String[] columns = {Contract.NewDaftarFranchiseInfo.ID_FRANCHISE};
//        SQLiteDatabase db = this.getReadableDatabase();
//        String selection = Contract.NewDaftarFranchiseInfo.ID_FRANCHISE + " = ?";
//
//        String idstr = String.valueOf(id);
//        String[] selectionArgs = {idstr};
//
//        Cursor cursor = db.query(Contract.NewDaftarFranchiseInfo.TABLE_DAFTARFRANCHISE, columns, selection, selectionArgs, null, null, null);
//        int cursorCount = cursor.getCount();
//        cursor.close();
//        db.close();
//
//        if (cursorCount > 0) {
//            return true;
//        }
//        return false;
//    }
//
//
//    public List<Franchise> getAllDaftarFrancise() {
//        String[] columns = {
//                Contract.NewDaftarFranchiseInfo.ID_FRANCHISE,
//                Contract.NewDaftarFranchiseInfo.NAMA_FRANCHISE,
//                Contract.NewDaftarFranchiseInfo.NAMA_PT,
//                Contract.NewDaftarFranchiseInfo.KETERANGAN_FRANCHISE,
//                Contract.NewDaftarFranchiseInfo.JENIS_FRANCHISE,
//                Contract.NewDaftarFranchiseInfo.KATEGORI_FRANCHISE,
//                Contract.NewDaftarFranchiseInfo.BERDIRI_SEJAK,
//                Contract.NewDaftarFranchiseInfo.INVESTASI,
//                Contract.NewDaftarFranchiseInfo.WEBSITE,
//                Contract.NewDaftarFranchiseInfo.ALAMAT,
//                Contract.NewDaftarFranchiseInfo.LOKASI,
//                Contract.NewDaftarFranchiseInfo.TELEPON,
//                Contract.NewDaftarFranchiseInfo.EMAIL,
//                Contract.NewDaftarFranchiseInfo.GAMBAR_FRANCHISE
//        };
//
//        String sortOrder =
//                Contract.NewDaftarFranchiseInfo.NAMA_FRANCHISE + " ASC";
//
//        List<Franchise> daftarFranchiseList = new ArrayList<Franchise>();
//
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query(
//                Contract.NewDaftarFranchiseInfo.TABLE_DAFTARFRANCHISE,
//                columns, null, null, null, null, sortOrder);
//
//        if (cursor.moveToFirst()) {
//            do {
//
//                Franchise daftarFranchise = new Franchise();
//
//                daftarFranchise.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.ID_FRANCHISE))));
//                daftarFranchise.setNama_franchise(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.NAMA_FRANCHISE)));
//                daftarFranchise.setNama_pt_franchisor(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.NAMA_PT)));
//                daftarFranchise.setKeterangan(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.KETERANGAN_FRANCHISE)));
//                daftarFranchise.setJenis(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.JENIS_FRANCHISE)));
//                daftarFranchise.setKategori(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.KATEGORI_FRANCHISE)));
//                daftarFranchise.setBerdiri_sejak(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.BERDIRI_SEJAK)));
//                daftarFranchise.setInvestasi(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.INVESTASI))));
//                daftarFranchise.setWebsite(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.WEBSITE)));
//                daftarFranchise.setAlamat(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.ALAMAT)));
//                daftarFranchise.setLokasi(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.LOKASI)));
//                daftarFranchise.setTelepon(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.TELEPON)));
//                daftarFranchise.setEmail(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.EMAIL)));
//                daftarFranchise.setGambar_franchise(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.GAMBAR_FRANCHISE)));
//
//                daftarFranchiseList.add(daftarFranchise);
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();
//        return daftarFranchiseList;
//    }
//
//
//
//    //EVENT FRANCHISE
//
////    public void addEvent(Event Event) {
////        if (!checkEvent(Event.getId())) {
////            SQLiteDatabase db = this.getWritableDatabase();
////            ContentValues values = new ContentValues();
////            values.put(Contract.NewEventInfo.ID, Event.getId());
////            values.put(Contract.NewEventInfo.JUDUL_EVENT, Event.getJudulEvent());
////            values.put(Contract.NewEventInfo.ISI_EVENT, Event.getIsiEvent());
////            values.put(Contract.NewEventInfo.GAMBAR_EVENT, Event.getGambarEvent());
////            values.put(Contract.NewEventInfo.ALAMAT_EVENT, Event.getALAMATEVENT());
////            values.put(Contract.NewEventInfo.WAKTU_EVENT, Event.getWAKTUEVENT());
////            db.insert(Contract.NewEventInfo.TABLE_EVENT, null, values);
////            db.close();
////        }
////    }
////
////    public boolean checkEvent(int id) {
////        String[] columns = {Contract.NewEventInfo.ID};
////        SQLiteDatabase db = this.getReadableDatabase();
////        String selection = Contract.NewEventInfo.ID + " = ?";
////
////        String idstr = String.valueOf(id);
////        String[] selectionArgs = {idstr};
////
////        Cursor cursor = db.query(Contract.NewEventInfo.TABLE_EVENT, columns, selection, selectionArgs, null, null, null);
////        int cursorCount = cursor.getCount();
////        cursor.close();
////        db.close();
////
////        if (cursorCount > 0) {
////            return true;
////        }
////        return false;
////    }
////
////
////
////    public List<Event> getAllEvent() {
////        String[] columns = {
////                Contract.NewEventInfo.ID,
////                Contract.NewEventInfo.JUDUL_EVENT,
////                Contract.NewEventInfo.ISI_EVENT,
////                Contract.NewEventInfo.GAMBAR_EVENT,
////                Contract.NewEventInfo.ALAMAT_EVENT,
////                Contract.NewEventInfo.WAKTU_EVENT
////        };
////
////        String sortOrder =
////                Contract.NewEventInfo.JUDUL_EVENT + " ASC";
////
////        List<Event> daftarListEvent = new ArrayList<Event>();
////
////        SQLiteDatabase db = this.getReadableDatabase();
////
////        Cursor cursor = db.query(
////                Contract.NewEventInfo.TABLE_EVENT,
////                columns, null, null, null, null, sortOrder);
////
////        if (cursor.moveToFirst()) {
////            do {
////
////                Event event = new Event();
////                event.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Contract.NewEventInfo.ID))));
////                event.setJudulEvent(cursor.getString(cursor.getColumnIndex(Contract.NewEventInfo.JUDUL_EVENT)));
////                event.setIsiEvent(cursor.getString(cursor.getColumnIndex(Contract.NewEventInfo.ISI_EVENT)));
////                event.setGambarEvent(cursor.getString(cursor.getColumnIndex(Contract.NewEventInfo.GAMBAR_EVENT)));
////                event.setALAMATEVENT(cursor.getString(cursor.getColumnIndex(Contract.NewEventInfo.ALAMAT_EVENT)));
////                event.setWAKTUEVENT(cursor.getString(cursor.getColumnIndex(Contract.NewEventInfo.WAKTU_EVENT)));
////
////
////                daftarListEvent.add(event);
////            } while (cursor.moveToNext());
////        }
////        cursor.close();
////        db.close();
////        return daftarListEvent;
////    }
////
////
////    //News Franchise
////
////    public void addNews(News news) {
////        if (!checkNews(news.getId_berita())) {
////            SQLiteDatabase db = this.getWritableDatabase();
////            ContentValues values = new ContentValues();
////            values.put(Contract.NewNewsInfo.ID, news.getId_berita());
////            values.put(Contract.NewNewsInfo.JUDUL_BERITA, news.getJudul_berita());
////            values.put(Contract.NewNewsInfo.ISI_BERITA, news.getIsi_berita());
////            values.put(Contract.NewNewsInfo.GAMBAR_BERITA, news.getGambar_berita());
////            values.put(Contract.NewNewsInfo.WAKTU_BERITA, news.getWaktu_berita());
////            db.insert(Contract.NewNewsInfo.TABLE_BERITA, null, values);
////            db.close();
////        }
////    }
////
////    public boolean checkNews(int id) {
////        String[] columns = {Contract.NewNewsInfo.ID};
////        SQLiteDatabase db = this.getReadableDatabase();
////        String selection = Contract.NewNewsInfo.ID + " = ?";
////
////        String idstr = String.valueOf(id);
////        String[] selectionArgs = {idstr};
////
////        Cursor cursor = db.query(Contract.NewNewsInfo.TABLE_BERITA, columns, selection, selectionArgs, null, null, null);
////        int cursorCount = cursor.getCount();
////        cursor.close();
////        db.close();
////
////        if (cursorCount > 0) {
////            return true;
////        }
////        return false;
////
////
////    }
////
////
////
////    public List<News> getAllBerita() {
////        String[] columns = {
////                Contract.NewNewsInfo.ID,
////                Contract.NewNewsInfo.JUDUL_BERITA,
////                Contract.NewNewsInfo.ISI_BERITA,
////                Contract.NewNewsInfo.GAMBAR_BERITA,
////                Contract.NewNewsInfo.WAKTU_BERITA
////        };
////
////        String sortOrder =
////                Contract.NewNewsInfo.JUDUL_BERITA + " ASC";
////
////        List<News> daftarListBerita = new ArrayList<News>();
////
////        SQLiteDatabase db = this.getReadableDatabase();
////
////        Cursor cursor = db.query(
////                Contract.NewNewsInfo.TABLE_BERITA,
////                columns, null, null, null, null, sortOrder);
////
////        if (cursor.moveToFirst()) {
////            do {
////                News berita = new News();
////                berita.setId_berita(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Contract.NewNewsInfo.ID))));
////                berita.setJudul_berita(cursor.getString(cursor.getColumnIndex(Contract.NewNewsInfo.JUDUL_BERITA)));
////                berita.setIsi_berita(cursor.getString(cursor.getColumnIndex(Contract.NewNewsInfo.ISI_BERITA)));
////                berita.setGambar_berita(cursor.getString(cursor.getColumnIndex(Contract.NewNewsInfo.GAMBAR_BERITA)));
////                berita.setWaktu_berita(cursor.getString(cursor.getColumnIndex(Contract.NewNewsInfo.WAKTU_BERITA)));
////                daftarListBerita.add(berita);
////            } while (cursor.moveToNext());
////        }
////        cursor.close();
////        db.close();
////        return daftarListBerita;
////    }
//    //Outlet Franchise
//
////    public void addOutlet(Franchisee outlet) {
////        if (!checkOutlet(outlet.getId_Outlet())) {
////            SQLiteDatabase db = this.getWritableDatabase();
////            ContentValues values = new ContentValues();
////            values.put(Contract.NewOutletInfo.ID, outlet.getId_Outlet());
////            values.put(Contract.NewOutletInfo.ID_FRANCHISE, outlet.getId_franchise());
////            values.put(Contract.NewOutletInfo.namaPTFranchisee, outlet.getNamaPTFranchisee());
////            values.put(Contract.NewOutletInfo.alamatFranchisee, outlet.getAlamatFranchisee());
////            values.put(Contract.NewOutletInfo.contactFranchisee, outlet.getContactFranchisee());
////            values.put(Contract.NewOutletInfo.Owner_Franchisee, outlet.getOwner_Franchisee());
////            values.put(Contract.NewOutletInfo.tahun_berdiri_Franchisee, outlet.getTahun_berdiri_Franchisee());
////            db.insert(Contract.NewOutletInfo.TABLE_OUTLET, null, values);
////            db.close();
////        }
////    }
//
//    public boolean checkOutlet(int id) {
//        String[] columns = {Contract.NewOutletInfo.ID};
//        SQLiteDatabase db = this.getReadableDatabase();
//        String selection = Contract.NewOutletInfo.ID + " = ?";
//
//        String idstr = String.valueOf(id);
//        String[] selectionArgs = {idstr};
//
//        Cursor cursor = db.query(Contract.NewOutletInfo.TABLE_OUTLET, columns, selection, selectionArgs, null, null, null);
//        int cursorCount = cursor.getCount();
//        cursor.close();
//        db.close();
//
//        if (cursorCount > 0) {
//            return true;
//        }
//        return false;
//
//    }
//
//
//
//
//
//    public List<Franchise> getBookmark(String email) {
//        List<Franchise> listdata = new ArrayList<Franchise>();
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        String MY_QUERY = "SELECT * FROM daftar_bookmark db INNER JOIN daftar_franchise df ON db.id_franchise=df.id WHERE db.user_email=?";
//        Cursor cursor = db.rawQuery(MY_QUERY, new String[]{email});
//
//        if (cursor.moveToFirst()) {
//            do {
//
//                Franchise daftarFranchise = new Franchise();
//
//                daftarFranchise.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.ID_FRANCHISE))));
//                daftarFranchise.setNama_franchise(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.NAMA_FRANCHISE)));
//                daftarFranchise.setNama_pt_franchisor(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.NAMA_PT)));
//                daftarFranchise.setKeterangan(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.KETERANGAN_FRANCHISE)));
//                daftarFranchise.setJenis(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.JENIS_FRANCHISE)));
//                daftarFranchise.setKategori(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.KATEGORI_FRANCHISE)));
//                daftarFranchise.setBerdiri_sejak(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.BERDIRI_SEJAK)));
//                daftarFranchise.setInvestasi(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.INVESTASI))));
//                daftarFranchise.setWebsite(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.WEBSITE)));
//                daftarFranchise.setAlamat(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.ALAMAT)));
//                daftarFranchise.setLokasi(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.LOKASI)));
//                daftarFranchise.setTelepon(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.TELEPON)));
//                daftarFranchise.setEmail(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.EMAIL)));
//                daftarFranchise.setGambar_franchise(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.GAMBAR_FRANCHISE)));
//
//                listdata.add(daftarFranchise);
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();
//        return listdata;
//    }
//
//    public void addBookmark(String email, String id_franchise) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(Contract.NewBookMark.EMAIL_USER, email);
//        values.put(Contract.NewBookMark.ID_FRANCHISE, id_franchise);
//        db.insert(Contract.NewBookMark.TABLE_BOOKMARK, null, values);
//        db.close();
//    }
//
//    public void deleteBookmark(String email, String id_franchise) {
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(Contract.NewBookMark.TABLE_BOOKMARK, Contract.NewBookMark.EMAIL_USER + " = ? AND " + Contract.NewBookMark.ID_FRANCHISE + " = ?",
//                new String[]{email, id_franchise});
//        db.close();
//    }
//
//    public boolean checkBookmark(String email, String id_franchise) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        String MY_QUERY = "SELECT * FROM daftar_bookmark WHERE user_email = ? AND id_franchise = ?";
//        Cursor cursor = db.rawQuery(MY_QUERY, new String[]{email, id_franchise});
//
//        int cursorCount = cursor.getCount();
//        cursor.close();
//        db.close();
//
//        if (cursorCount > 0) {
//            return true;
//        }
//        return false;
//    }
//
//    public void addMessage(ChatMessage message) {
//        if (!checkMessage(message.getId())) {
//            SQLiteDatabase db = this.getWritableDatabase();
//            ContentValues values = new ContentValues();
//            values.put(Contract.NewMessage.ID_PESAN, message.getId());
//            values.put(Contract.NewMessage.ID_FRANCHISE, message.getId_franchise());
//            values.put(Contract.NewMessage.USER_EMAIL, message.getEmail());
//            values.put(Contract.NewMessage.MESSAGE, message.getMessage());
//            db.insert(Contract.NewMessage.TABLE_MESSAGE, null, values);
//            db.close();
//        }
//    }
//
//    public boolean checkMessage(int id) {
//        String[] columns = {Contract.NewMessage.ID_PESAN};
//        SQLiteDatabase db = this.getReadableDatabase();
//        String selection = Contract.NewMessage.ID_PESAN + " = ?";
//
//        String idstr = String.valueOf(id);
//        String[] selectionArgs = {idstr};
//
//        Cursor cursor = db.query(Contract.NewMessage.TABLE_MESSAGE, columns, selection, selectionArgs, null, null, null);
//        int cursorCount = cursor.getCount();
//        cursor.close();
//        db.close();
//
//        if (cursorCount > 0) {
//            return true;
//        }
//        return false;
//
//    }
//
//    public List<ChatMessage> getChat(String id_franchise) {
//        List<ChatMessage> listdata = new ArrayList<ChatMessage>();
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        String MY_QUERY = "Select * from daftar_message WHERE id=?";
//        Cursor cursor = db.rawQuery(MY_QUERY, new String[]{id_franchise});
//
//        if (cursor.moveToFirst()) {
//            do {
//
//                ChatMessage chatMessage = new ChatMessage();
//                chatMessage.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Contract.NewMessage.ID_PESAN))));
//                chatMessage.setId_franchise(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Contract.NewMessage.ID_FRANCHISE))));
//                chatMessage.setEmail(cursor.getString(cursor.getColumnIndex(Contract.NewMessage.USER_EMAIL)));
//                chatMessage.setMessage(cursor.getString(cursor.getColumnIndex(Contract.NewMessage.MESSAGE)));
//
//                listdata.add(chatMessage);
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();
//        return listdata;
//    }
//
//
//    public void addChat(String email, String id_franchise, String message) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(Contract.NewMessage.USER_EMAIL, email);
//        values.put(Contract.NewMessage.ID_FRANCHISE, id_franchise);
//        values.put(Contract.NewMessage.MESSAGE, message);
//        db.insert(Contract.NewMessage.TABLE_MESSAGE, null, values);
//        db.close();
//    }
//
//    //ADa Outlet
//    public long getSameDFinOutlet(String id_franchise) {
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        SQLiteStatement s = db.compileStatement("Select COUNT (*) from daftar_outlet do inner join daftar_franchise df ON do.id = " + id_franchise + " AND  df.id =" + id_franchise + "");
//        long count = s.simpleQueryForLong();
//        return count;
//
//    }
//
////
////    public List<Franchisee> getOutlet_DF(String id_franchise) {
////        List<Franchisee> listdata = new ArrayList<Franchisee>();
////
////        SQLiteDatabase db = this.getReadableDatabase();
////        String MY_QUERY = "Select * from daftar_outlet inner join daftar_franchise ON daftar_outlet.id = ? AND daftar_franchise.id = ?";
////
////
////        Cursor cursor = db.rawQuery(MY_QUERY, new String[]{id_franchise, id_franchise});
////
////        if (cursor.moveToFirst()) {
////            do {
////
////                Franchisee outlet = new Franchisee();
////                outlet.setId_Outlet(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Contract.NewOutletInfo.ID))));
////                outlet.setId_franchise(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Contract.NewOutletInfo.ID_FRANCHISE))));
////                outlet.setNamaPTFranchisee(cursor.getString(cursor.getColumnIndex(Contract.NewOutletInfo.namaPTFranchisee)));
////                outlet.setAlamatFranchisee(cursor.getString(cursor.getColumnIndex(Contract.NewOutletInfo.alamatFranchisee)));
////                outlet.setContactFranchisee(cursor.getString(cursor.getColumnIndex(Contract.NewOutletInfo.contactFranchisee)));
////                outlet.setOwner_Franchisee(cursor.getString(cursor.getColumnIndex(Contract.NewOutletInfo.Owner_Franchisee)));
////                outlet.setTahun_berdiri_Franchisee(cursor.getString(cursor.getColumnIndex(Contract.NewOutletInfo.tahun_berdiri_Franchisee)));
////
////                listdata.add(outlet);
////            } while (cursor.moveToNext());
////        }
////        cursor.close();
////        db.close();
////        return listdata;
////    }
//
//
//    public List<Franchise> getHomeFranchise() {
//        List<Franchise> listdata = new ArrayList<Franchise>();
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        String MY_QUERY = "Select * From daftar_franchise df inner join daftar_message dm ON df.id  = dm.id GROUP BY dm.id HAVING COUNT(dm.id) >0 ORDER BY dm.id_pesan";
//        Cursor cursor = db.rawQuery(MY_QUERY, new String[]{});
//
//        if (cursor.moveToFirst()) {
//            do {
//
//                Franchise daftarFranchise = new Franchise();
//
//                daftarFranchise.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.ID_FRANCHISE))));
//                daftarFranchise.setNama_franchise(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.NAMA_FRANCHISE)));
//                daftarFranchise.setNama_pt_franchisor(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.NAMA_PT)));
//                daftarFranchise.setKeterangan(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.KETERANGAN_FRANCHISE)));
//                daftarFranchise.setJenis(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.JENIS_FRANCHISE)));
//                daftarFranchise.setKategori(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.KATEGORI_FRANCHISE)));
//                daftarFranchise.setBerdiri_sejak(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.BERDIRI_SEJAK)));
//                daftarFranchise.setInvestasi(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.INVESTASI))));
//                daftarFranchise.setWebsite(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.WEBSITE)));
//                daftarFranchise.setAlamat(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.ALAMAT)));
//                daftarFranchise.setLokasi(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.LOKASI)));
//                daftarFranchise.setTelepon(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.TELEPON)));
//                daftarFranchise.setEmail(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.EMAIL)));
//                daftarFranchise.setGambar_franchise(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.GAMBAR_FRANCHISE)));
//
//                listdata.add(daftarFranchise);
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();
//        return listdata;
//    }
//
//    public List<Franchise> getBestReviewFranchise() {
//        List<Franchise> listdata = new ArrayList<Franchise>();
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        String MY_QUERY = "Select * From daftar_franchise df inner join daftar_outlet do ON df.id  = do.id GROUP BY df.id HAVING COUNT(do.id) >0";
//
//        Cursor cursor = db.rawQuery(MY_QUERY, new String[]{});
//
//        if (cursor.moveToFirst()) {
//            do {
//
//                Franchise daftarFranchise = new Franchise();
//
//                daftarFranchise.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.ID_FRANCHISE))));
//                daftarFranchise.setNama_franchise(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.NAMA_FRANCHISE)));
//                daftarFranchise.setNama_pt_franchisor(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.NAMA_PT)));
//                daftarFranchise.setKeterangan(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.KETERANGAN_FRANCHISE)));
//                daftarFranchise.setJenis(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.JENIS_FRANCHISE)));
//                daftarFranchise.setKategori(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.KATEGORI_FRANCHISE)));
//                daftarFranchise.setBerdiri_sejak(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.BERDIRI_SEJAK)));
//                daftarFranchise.setInvestasi(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.INVESTASI))));
//                daftarFranchise.setWebsite(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.WEBSITE)));
//                daftarFranchise.setAlamat(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.ALAMAT)));
//                daftarFranchise.setLokasi(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.LOKASI)));
//                daftarFranchise.setTelepon(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.TELEPON)));
//                daftarFranchise.setEmail(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.EMAIL)));
//                daftarFranchise.setGambar_franchise(cursor.getString(cursor.getColumnIndex(Contract.NewDaftarFranchiseInfo.GAMBAR_FRANCHISE)));
//
//                listdata.add(daftarFranchise);
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();
//        return listdata;
//    }
//
//    public User getUser(String email) {
//        User user = new User();
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        String MY_QUERY = "select * from user where user_email = ?";
//        Cursor cursor = db.rawQuery(MY_QUERY, new String[]{email});
//
//        if (cursor.moveToFirst()) {
//            do {
//                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Contract.NewUserInfo.USER_ID))));
//                user.setEmail(cursor.getString(cursor.getColumnIndex(Contract.NewUserInfo.USER_EMAIL)));
//                user.setName(cursor.getString(cursor.getColumnIndex(Contract.NewUserInfo.USER_NAME)));
//                user.setPassword(cursor.getString(cursor.getColumnIndex(Contract.NewUserInfo.USER_PASSWORD)));
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();
//        return user;
//    }
//}
//
