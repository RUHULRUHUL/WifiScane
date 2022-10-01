package com.bugbd.wifiscane.Sqlitedatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class SqlitedbHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String Database_name = "Qr_Bar_code_generate.db";
    private static final int database_version_number = 5;
    private static final String GENERATE_CODE_TABLE_NAME = "GENERATE_QR_CODE";
    private static final String SCAN_CODE_TABLE_NAME = "SCAN_QR_CODE";
    private static final String ID_TOTAL = "id";
    private static final String TYPE = "type";
    private static final String INPUT_URL = "INPUT_URL";
    private static final String SECURITY_MOD = "WIFI_SECURITY_MODE";
    private static final String PASSWORD = "PASSWORD";
    private static final String PHONE = "PHONE";
    private static final String TEXT = "TEXT";
    private static final String NAME = "NAME";
    private static final String EMAIL = "EMAIL";
    private static final String ADDRASH = "ADDRASH";
    private static final String Qrcode_type = "qr_code_type";
    private static final String QR_BR_CODE_IMAGE = "QR_BR_CODE_IMAGE";
    private static final String WIFI_NAME = "WIFI_NAME";


    private static final String TIME_DATE = "time";


    private static final String create_table = "test_table";
    private static final String test_username = "username";
    private static final String test_years = "years";

    private SQLiteDatabase sqLiteDatabase;

    private final String GENERATE_TABLE_CREATE_COMMAND = "CREATE TABLE " + GENERATE_CODE_TABLE_NAME + "( " + ID_TOTAL + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TYPE + " TEXT, " + SECURITY_MOD + " TEXT, " + PASSWORD + " TEXT," + PHONE + " TEXT, " + TEXT + " TEXT," + NAME + " TEXT," + EMAIL + " TEXT," + ADDRASH + " TEXT," + Qrcode_type + " TEXT," + QR_BR_CODE_IMAGE + " BLOB," + INPUT_URL + " TEXT," + WIFI_NAME + " TEXT," + TIME_DATE + " TEXT)";

    private final String SCANNER_TABLE_CREATE_COMMAND = "CREATE TABLE " + SCAN_CODE_TABLE_NAME + "( " + ID_TOTAL + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TYPE + " TEXT, " + SECURITY_MOD + " TEXT, " + PASSWORD + " TEXT," + PHONE + " TEXT, " + TEXT + " TEXT," + NAME + " TEXT," + EMAIL + " TEXT," + ADDRASH + " TEXT," + Qrcode_type + " TEXT," + QR_BR_CODE_IMAGE + " BLOB , " + INPUT_URL + " TEXT," + WIFI_NAME + " TEXT," + TIME_DATE + " TEXT)";


    private static final String GENERATE_DROP_TABLE = "DROP TABLE IF EXISTS " + GENERATE_CODE_TABLE_NAME;
    private static final String SCAN_DROP_TABLE = "DROP TABLE IF EXISTS " + SCAN_CODE_TABLE_NAME;


    private static final String GENERATE_QUERY_DATABASE = "SELECT * FROM " + GENERATE_CODE_TABLE_NAME;
    private static final String SCAN_QUERY_DATABASE = "SELECT * FROM " + SCAN_CODE_TABLE_NAME;

    public SqlitedbHelper(@Nullable Context context) {
        super(context, Database_name, null, database_version_number);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(GENERATE_TABLE_CREATE_COMMAND);
            db.execSQL(SCANNER_TABLE_CREATE_COMMAND);
        } catch (Exception e) {
            // Toast.makeText(context, "database problemes " + e.getMessage().toString(), Toast.LENGTH_LONG).show();


        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Toast.makeText(context, "onupgrade is called...", Toast.LENGTH_SHORT).show();
        try {
            db.execSQL(SCAN_DROP_TABLE);
            db.execSQL(GENERATE_DROP_TABLE);
            onCreate(db);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long generate_insert_data(String type, String input_url, String Wifi_security_mod, String password, String phone, String text, String name, String email, String addrash, String qrcode_type, byte[] imagebyte, String wifi_name, String time) {
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(TYPE, type);
        contentValues.put(SECURITY_MOD, Wifi_security_mod);
        contentValues.put(PASSWORD, password);
        contentValues.put(PHONE, phone);
        contentValues.put(TEXT, text);
        contentValues.put(NAME, name);
        contentValues.put(EMAIL, email);
        contentValues.put(ADDRASH, addrash);
        contentValues.put(INPUT_URL, input_url);
        contentValues.put(Qrcode_type, qrcode_type);
        contentValues.put(QR_BR_CODE_IMAGE, imagebyte);
        contentValues.put(WIFI_NAME, wifi_name);
        contentValues.put(TIME_DATE, time);

        long result = sqLiteDatabase.insert(GENERATE_CODE_TABLE_NAME, null, contentValues);

        return result;


    }

    public long scann_insert_data(String type, String input_url, String Wifi_security_mod, String password, String phone, String text, String name, String email, String addrash, String qrcode_type, byte[] imagebyte, String wifi_name, String time) {
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(TYPE, type);
        contentValues.put(SECURITY_MOD, Wifi_security_mod);
        contentValues.put(PASSWORD, password);
        contentValues.put(PHONE, phone);
        contentValues.put(TEXT, text);
        contentValues.put(NAME, name);
        contentValues.put(EMAIL, email);
        contentValues.put(ADDRASH, addrash);
        contentValues.put(INPUT_URL, input_url);
        contentValues.put(Qrcode_type, qrcode_type);
        contentValues.put(QR_BR_CODE_IMAGE, imagebyte);
        contentValues.put(WIFI_NAME, wifi_name);
        contentValues.put(TIME_DATE, time);

        long result = sqLiteDatabase.insert(SCAN_CODE_TABLE_NAME, null, contentValues);

        return result;


    }

    public Cursor querydata() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(GENERATE_QUERY_DATABASE, null);
        return cursor;
    }

    public Cursor scanquerydata() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(SCAN_QUERY_DATABASE, null);
        return cursor;
    }


}



