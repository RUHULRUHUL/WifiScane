package com.bugbd.wifiscane.model;

public class Scancode_item_model {
    private String type;
    private  String wifi_security_mod;
    private String password;
    private String phone;
    private String text;
    private String name;
    private String email;
    private String addrash;
    private String qrcode_type;
    private byte[] imagebyte;
    private String input_url;
    private String wifiname;

    public Scancode_item_model(String type, String wifi_security_mod, String password, String phone, String text, String name, String email, String addrash, String qrcode_type, byte[] imagebyte, String input_url, String wifiname) {
        this.type = type;
        this.wifi_security_mod = wifi_security_mod;
        this.password = password;
        this.phone = phone;
        this.text = text;
        this.name = name;
        this.email = email;
        this.addrash = addrash;
        this.qrcode_type = qrcode_type;
        this.imagebyte = imagebyte;
        this.input_url = input_url;
        this.wifiname = wifiname;
    }


}
