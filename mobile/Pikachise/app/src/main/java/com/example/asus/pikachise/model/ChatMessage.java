package com.example.asus.pikachise.model;

/**
 * Created by Asus on 18/09/2017.
 */

public class ChatMessage {
    private int id;
    private int id_franchise;
    private String email;
    public String message;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_franchise() {
        return id_franchise;
    }

    public void setId_franchise(int id_franchise) {
        this.id_franchise = id_franchise;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ChatMessage() {

    }
}
