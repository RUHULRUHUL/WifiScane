package com.bugbd.wifiscane.MainActivity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bugbd.wifiscane.Fragment_Activity.ListFragment;
import com.bugbd.wifiscane.Fragment_Activity.MakeqrcodeFragment;
import com.bugbd.wifiscane.Fragment_Activity.ScanFragment;
import com.bugbd.wifiscane.R;
import com.bugbd.wifiscane.Sqlitedatabase.SqlitedbHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private Fragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.botom_nav_id);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_view_id, new ScanFragment()).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            fragment = null;

            switch (item.getItemId()) {
                case R.id.scan_qr_id: {
                    fragment = new ScanFragment();
                    break;

                }
                case R.id.make_qr_code_id: {
                    fragment = new MakeqrcodeFragment();
                    break;

                }
                case R.id.list_id: {
                    fragment = new ListFragment();
                    break;

                }
                default: {
                    return false;

                }


            }
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_view_id, fragment).commit();


            return true;


        });


    }

}
