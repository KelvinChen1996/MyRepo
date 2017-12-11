package com.example.asus.pikachise.presenter.session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.asus.pikachise.view.authentication.LoginUser;
import com.example.asus.pikachise.model.User;
import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by WilliamSumitro on 17/09/2017.
 */

public class SessionManagement {
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";



    private SharedPreferences pref;

    // Editor for Shared preferences
    private SharedPreferences.Editor editor;

    // Context
    private Context _context;

    // Shared pref mode
    private int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "SessionPref";

    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String USER_DATA = "userdata";


    public static final String USER_ID = "id";
    public static final String USER_NAME = "name";
    public static final String USER_EMAIL = "email";
    public static final String USER_PHONENUMBER = "password";
    public static final String USER_ADDRESS = "address";
    public static final String USER_IMAGE = "image";
    public static final String USER_TOKEN = "usertoken";


    // Constructor
    public SessionManagement(Context context){
        this._context = context;
        this.pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        this.editor = this.pref.edit();
    }


    public static SessionManagement with(Context context){
        return new SessionManagement(context);
    }
    /**
     * Create login session
     * */
    public void createLoginSession(int id, String name, String email, String image, String address, String phonenumber, String token){
        editor.putBoolean(IS_LOGIN, true);
        editor.putInt(USER_ID, id);
        editor.putString(USER_NAME, name);
        editor.putString(USER_EMAIL, email);
        editor.putString(USER_IMAGE, image);
        editor.putString(USER_ADDRESS, address);
        editor.putString(USER_PHONENUMBER, phonenumber);
        editor.putString(USER_TOKEN, token);
        editor.commit();
    }
    public void createLoginSession(User user){
        editor.putBoolean(IS_LOGIN,true);
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString(USER_DATA,json);
        editor.commit();
    }
    public User getUserSession(){
        Gson gson = new Gson();
        String json = pref.getString(USER_DATA, null);
        User user = gson.fromJson(json, User.class);
        return user;
    }
    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(USER_NAME, pref.getString(USER_NAME, null));
        user.put(USER_EMAIL, pref.getString(USER_EMAIL, null));
        user.put(USER_TOKEN, pref.getString(USER_TOKEN, null));
        user.put(USER_PHONENUMBER, pref.getString(USER_PHONENUMBER, null));
        user.put(USER_ADDRESS, pref.getString(USER_ADDRESS, null));
        user.put(USER_IMAGE, pref.getString(USER_IMAGE, null));
        return user;
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        if(!this.isLoggedIn()){
            Intent i = new Intent(_context, LoginUser.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            _context.startActivity(i);
        }

    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginUser.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        _context.startActivity(i);

    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    public User getuserloggedin() {
        return new Gson().fromJson(pref.getString(USER_DATA, ""), User.class);
    }

    /*
    * TO-DO
    * clear the shared preferences from the user data
    */
    public void clearsession() {
        editor.clear();
        editor.commit();
    }

    public void setIsFirstTimeLaunch(boolean isFirstTimeLaunch){
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTimeLaunch);
        editor.commit();
    }
    public boolean isFirstTimeLaunch(){
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }
}
