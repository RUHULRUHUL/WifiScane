package com.bugbd.wifiscane.model;

public class Item_model {

    //String type , String input_url , String Wifi_security_mod, String password, String phone, String text, String name, String email, String addrash, String qrcode_type,byte[]imagebyte

    private String type;
    private String input_url;
    private  String wifi_security_mod;
    private String password;
    private String phone;
    private String text;
    private String name;
    private String email;
    private String addrash;
    private String qrcode_type;
    private byte[] imagebyte;
    private String wifi_name;
    private String time_date;

    public String getTime_date() {
        return time_date;
    }

    public Item_model(String type, String input_url, String wifi_security_mod, String password, String phone, String text, String name, String email, String addrash, String qrcode_type, byte[] imagebyte, String wifi_name, String time_date) {
        this.type = type;
        this.input_url = input_url;
        this.wifi_security_mod = wifi_security_mod;
        this.password = password;
        this.phone = phone;
        this.text = text;
        this.name = name;
        this.email = email;
        this.addrash = addrash;
        this.qrcode_type = qrcode_type;
        this.imagebyte = imagebyte;
        this.wifi_name = wifi_name;
        this.time_date=time_date;
    }



    public String getType() {
        return type;
    }

    public String getWifi_security_mod() {
        return wifi_security_mod;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getText() {
        return text;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddrash() {
        return addrash;
    }

    public String getQrcode_type() {
        return qrcode_type;
    }

    public byte[] getImagebyte() {
        return imagebyte;
    }

    public String getInput_url() {
        return input_url;
    }

    public String getWifi_name() {
        return wifi_name;
    }







    public Item_model() {
    }







}
