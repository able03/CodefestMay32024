package com.example.codefestmay32024.models;

public class AccountStaticModel
{
    private static int id;
    private static String name;
    private static String address;
    private  static String bdate;
    private static String contact;
    private static String username;
    private static String password;
    private static String pin;

    public AccountStaticModel(int id, String name, String address, String bdate, String contact, String username, String password, String pin) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.bdate = bdate;
        this.contact = contact;
        this.username = username;
        this.password = password;
        this.pin = pin;
    }

    public static int getId() {
        return id;
    }

    public static String getName() {
        return name;
    }

    public static String getAddress() {
        return address;
    }

    public static String getBdate() {
        return bdate;
    }

    public static String getContact() {
        return contact;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    public static String getPin() {
        return pin;
    }
}
