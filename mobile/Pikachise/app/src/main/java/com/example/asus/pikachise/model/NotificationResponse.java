package com.example.asus.pikachise.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Kelvin on 07/12/2017.
 */

public class NotificationResponse {
    @SerializedName("notifications")
    @Expose
    private List<Notification> notifications = null;

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
}
