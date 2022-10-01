package com.bugbd.wifiscane.MainActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;

import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bugbd.wifiscane.R;
import com.bugbd.wifiscane.databinding.ActivityScanResultBinding;
import com.google.android.material.button.MaterialButton;
import com.thanosfisherman.wifiutils.WifiUtils;
import com.thanosfisherman.wifiutils.wifiConnect.ConnectionErrorCode;
import com.thanosfisherman.wifiutils.wifiConnect.ConnectionSuccessListener;

import java.util.List;

public class ScanResultActivity extends AppCompatActivity {

    private ActivityScanResultBinding binding;
    private AppCompatTextView name, password, type, WifiLabelName, WifiLabelPassword;
    private MaterialButton connect;
    private String wifiname, wifipass, wifitype, WifiSecurity;
    //  private WifiConfiguration conf;
    private AppCompatImageView backpress;
    private LottieAnimationView lottieAnimationView;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_result);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_scan_result);

        initView();
        getIntentData();
        clickEvent();
    }

    private void initView() {
        name = findViewById(R.id.name_id);
        password = findViewById(R.id.password_id);
        type = findViewById(R.id.type_id);
        connect = findViewById(R.id.wifi_connect_id);

        WifiLabelName = findViewById(R.id.WifiLabelName);
        WifiLabelPassword = findViewById(R.id.WifiLabelPassword);

        backpress = findViewById(R.id.backpress_id);
        lottieAnimationView = findViewById(R.id.animationView);

        progressDialog = new ProgressDialog(ScanResultActivity.this);
        progressDialog.setMessage("Please Wait For Wifi Connect");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCanceledOnTouchOutside(false);

    }


    private void clickEvent() {
        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //connectWifiNetwork();
     /*           boolean isConnect = WifiConnect();
                System.out.println("connectivity : "+isConnect);*/
                //ConnectWifi(wifiname, wifiname, WifiSecurity, wifipass);
                //connectNetwork();
                //connectNetwork1();
                anotherOption();
            }

        });
    }

    private void anotherOption() {
        progressDialog.show();

        WifiUtils.withContext(getApplicationContext()).enableWifi();
        WifiUtils.withContext(getApplicationContext()).scanWifi(this::getScanResults).start();

        WifiUtils.withContext(getApplicationContext())
                .connectWith(wifiname, wifipass)
                .setTimeout(6000)
                .onConnectionResult(new ConnectionSuccessListener() {
                    @Override
                    public void success() {
                        Toast.makeText(ScanResultActivity.this, "SUCCESS!", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        connect.setVisibility(View.GONE);
                        lottieAnimationView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void failed(@NonNull ConnectionErrorCode errorCode) {
                        progressDialog.dismiss();
                        Toast.makeText(ScanResultActivity.this, "EPIC FAIL! Please Enable Your Device Location and turn on Wifi" + errorCode.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
                .start();


    }

    private void getScanResults(@NonNull final List<ScanResult> results) {
        if (results.isEmpty()) {
            Toast.makeText(this, "Scan Result Empty", Toast.LENGTH_SHORT).show();
            // Log.i("Wifi"", "SCAN RESULTS IT'S EMPTY");
            return;
        } else {
            Toast.makeText(this, "Scan Result Not Empty", Toast.LENGTH_SHORT).show();
        }
        //Log.i(TAG, "GOT SCAN RESULTS " + results);
    }

/*    private void connectNetwork1() {

        WifiUtils.withContext(getApplicationContext())
                .connectWith(wifiname, getMacAddress(this), wifipass)
                .onConnectionResult(successListener)
                .start();


        WifiUtils.withContext(getApplicationContext())
                .connectWithScanResult(wifipass, scanResults -> scanResults.get(0))
                .onConnectionResult(successListener)
                .start();


    }*/


    private ConnectionSuccessListener successListener = new ConnectionSuccessListener() {
        @Override
        public void success() {
            Toast.makeText(ScanResultActivity.this, "SUCCESS!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void failed(@NonNull ConnectionErrorCode errorCode) {
            Toast.makeText(ScanResultActivity.this, "EPIC FAIL!" + errorCode.toString(), Toast.LENGTH_SHORT).show();
        }
    };


/*    public String getMacAddress(Context context) {
        WifiManager wimanager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        @SuppressLint("HardwareIds") String macAddress = wimanager.getConnectionInfo().getMacAddress();
        if (macAddress == null) {
            macAddress = "Device don't have mac address or wi-fi is disabled";
        }
        return macAddress;
    }*/

 /*   private void connectNetwork() {
        //WifiUtils.withContext(getApplicationContext()).enableWifi(this::checkResult);
        WifiUtils.withContext(getApplicationContext()).enableWifi();

        WifiUtils.withContext(getApplicationContext())
                .connectWith(wifiname, wifipass)
                .setTimeout(40000)
                .onConnectionResult(new ConnectionSuccessListener() {
                    @Override
                    public void success() {
                        Toast.makeText(ScanResultActivity.this, "SUCCESS!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failed(@NonNull ConnectionErrorCode errorCode) {
                        Toast.makeText(ScanResultActivity.this, "EPIC FAIL!" + errorCode.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
                .start();


    }*/

/*    private void checkResult(boolean isSuccess) {
        if (isSuccess)
            Toast.makeText(ScanResultActivity.this, "WIFI ENABLED", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(ScanResultActivity.this, "COULDN'T ENABLE WIFI", Toast.LENGTH_SHORT).show();
    }*/

/*    private void ConnectWifi(String ssid, String bssid, String security, String password) {

        WifiConnector connector = new WifiConnector(this, ssid, bssid, security, password);

        connector.enableWifi();

        connector.connectToWifi(new ConnectionResultListener() {
            @Override
            public void successfulConnect(String SSID) {

                Toast.makeText(ScanResultActivity.this, "Connect Successfully..", Toast.LENGTH_SHORT).show();
                Log.d("WifiLog", "errorConnect: " + SSID);
            }

            @Override
            public void errorConnect(int codeReason) {
                Log.d("WifiLog", "errorConnect: " + codeReason);
            }

            @Override
            public void onStateChange(SupplicantState supplicantState) {
                Log.d("WifiLog", "onStateChange: " + supplicantState.name());

            }
        });

    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

/*    private boolean WifiConnect() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiConfiguration wifiConfig = new WifiConfiguration();
        wifiConfig.SSID = String.format("\"%s\"", wifiname);

        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }

        if (WifiSecurity.equalsIgnoreCase("WEP")) { // WEP Network.
            Toast.makeText(this, "WEP Network", Toast.LENGTH_SHORT).show();

            wifiConfig.wepKeys[0] = String.format("\"%s\"", wifipass);
            wifiConfig.wepTxKeyIndex = 0;
            wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
        } else if (WifiSecurity.equalsIgnoreCase("WPA")) { // WPA Network
            Toast.makeText(this, "WPA Network", Toast.LENGTH_SHORT).show();
            wifiConfig.preSharedKey = String.format("\"%s\"", wifipass);

        } else { // OPEN Network.
            Toast.makeText(this, "OPEN Network", Toast.LENGTH_SHORT).show();
            wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        }


        int netId = wifiManager.addNetwork(wifiConfig);//
        wifiManager.disconnect();
        wifiManager.enableNetwork(netId, true);
        return wifiManager.reconnect();

    }*/

/*    private void connectWifiNetwork() {

        conf = new WifiConfiguration();
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }


        if (WifiSecurity.equalsIgnoreCase("wep")) {
            conf.SSID = "\"" + wifiname + "\"";
            conf.wepKeys[0] = "\"" + wifipass + "\"";
            conf.wepTxKeyIndex = 0;
            conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);

        } else if (WifiSecurity.equalsIgnoreCase("WPA")) {
            conf.preSharedKey = "\"" + wifipass + "\"";
            conf.SSID = "\"" + wifiname + "\"";

        } else {
            conf.preSharedKey = "\"" + wifipass + "\"";
            conf.SSID = "\"" + wifiname + "\"";
            conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);

        }

        wifiManager.addNetwork(conf);
        @SuppressLint("MissingPermission") List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
        for (WifiConfiguration i : list) {
            if (i.SSID != null && i.SSID.equalsIgnoreCase("\"" + wifiname + "\"")) {
                wifiManager.disconnect();
                wifiManager.enableNetwork(i.networkId, true);
                wifiManager.reconnect();
                break;
            }
        }
*//*        if (isNetworkAvailable(ScanResultActivity.this)) {
            progressDialog.dismiss();
            Toast.makeText(ScanResultActivity.this, "Connection successfully..", Toast.LENGTH_SHORT).show();
            connect.setVisibility(View.GONE);
            lottieAnimationView.setVisibility(View.VISIBLE);

        } else {
            Toast.makeText(ScanResultActivity.this, "Connection Problem..", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }*//*
    }*/

    private void getIntentData() {
        Intent intent = getIntent();
        wifitype = intent.getStringExtra("type");

        if (wifitype.equalsIgnoreCase("notWifi")) {
            String scanResult = intent.getStringExtra("result");

            connect.setVisibility(View.GONE);

            name.setVisibility(View.GONE);
            password.setVisibility(View.GONE);

            WifiLabelName.setVisibility(View.GONE);
            WifiLabelPassword.setVisibility(View.GONE);

            if (scanResult != null) {
                type.setText(scanResult);
            }

        } else {
            wifiname = intent.getStringExtra("WifiName");
            wifipass = intent.getStringExtra("WifiPassword");
            WifiSecurity = intent.getStringExtra("WifiSecurity");

            name.setVisibility(View.VISIBLE);
            password.setVisibility(View.VISIBLE);
            connect.setVisibility(View.VISIBLE);

            WifiLabelName.setVisibility(View.VISIBLE);
            WifiLabelPassword.setVisibility(View.VISIBLE);

            name.setText(wifiname);
            password.setText(wifipass);
            type.setText(WifiSecurity);

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}