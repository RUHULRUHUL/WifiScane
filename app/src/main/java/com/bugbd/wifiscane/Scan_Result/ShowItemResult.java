package com.bugbd.wifiscane.Scan_Result;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;

import com.bugbd.wifiscane.R;

public class ShowItemResult extends AppCompatActivity {

    private ImageView imageView;
    private TextView typetext, text1, text2, text3;
    private String wifi_security_mod;
    private String password;
    private byte[] byte_image_array;
    private String wifi_name;
    private String time_date;
    private Bitmap bitmap;
    private AppCompatImageView BackPressImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_item_result);

        initView();

        Intent intent = getIntent();
        byte_image_array = intent.getByteArrayExtra("imagebyte");
        wifi_name = intent.getStringExtra("wifi_name");
        time_date = intent.getStringExtra("time_date");
        wifi_security_mod = intent.getStringExtra("wifi_security_mod");
        password = intent.getStringExtra("password");

        if (byte_image_array != null) {
            try {
                bitmap = BitmapFactory.decodeByteArray(byte_image_array, 0, byte_image_array.length);
                imageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        if (wifi_security_mod != null && wifi_name != null && password != null) {
            typetext.setText("Time : " + time_date);
            text1.setText("wifi name: " + wifi_name);
            text2.setText("wifi password: " + password);
            text3.setText("mode: " + wifi_security_mod);

        }

    }

    private void initView() {

        imageView = findViewById(R.id.imageview_id);
        typetext = findViewById(R.id.type_id);
        text1 = findViewById(R.id.text1_id);
        text2 = findViewById(R.id.text2_id);
        text3 = findViewById(R.id.text3_id);


        BackPressImg = findViewById(R.id.BackPress);

        BackPressImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}