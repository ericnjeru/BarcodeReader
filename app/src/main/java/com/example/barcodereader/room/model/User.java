package com.example.barcodereader.room.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;

@Entity(tableName = "users")
public class User {

    @PrimaryKey
    @NonNull
    private String id = "null";

    private String ip_address_source;

    private String ip_address_destination;

    private String password;

    public User() {
    }

    public User(String id, String ip_address_source, String ip_address_destination, String password) {
        this.id = id;
        this.ip_address_source = ip_address_source;
        this.ip_address_destination = ip_address_destination;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp_address_source() {
        return ip_address_source;
    }

    public void setIp_address_source(String ip_address_source) {
        this.ip_address_source = ip_address_source;
    }

    public String getIp_address_destination() {
        return ip_address_destination;
    }

    public void setIp_address_destination(String ip_address_destination) {
        this.ip_address_destination = ip_address_destination;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String serialize() {
        return new Gson().toJson(this);
    }

    public static User create(String fromJson) {
        return new Gson().fromJson(fromJson, User.class);
    }
}
