package com.example.codefestmay32024.models;

public class AccountModel
{
    private  int id;
    private  String name;
    private  String address;
    private  String bdate;
    private  String contact;
    private  String username;
    private  String password;
    private  String pin;

    public AccountModel(int id, String name, String address, String bdate, String contact, String username, String password, String pin) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.bdate = bdate;
        this.contact = contact;
        this.username = username;
        this.password = password;
        this.pin = pin;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getBdate() {
        return bdate;
    }

    public String getContact() {
        return contact;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPin() {
        return pin;
    }
}
