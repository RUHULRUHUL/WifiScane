package com.bugbd.wifiscane.Fragment_Activity;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.bugbd.wifiscane.MainActivity.ScanResultActivity;
import com.bugbd.wifiscane.R;
import com.bugbd.wifiscane.Sqlitedatabase.SqlitedbHelper;
import com.bugbd.wifiscane.imagesavesdcard.Saveimage;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResultType;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.common.HybridBinarizer;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ScanFragment extends Fragment {
    private View view;
    public String content;
    private MaterialButton attachimage;
    private Result zxingresult;
    public SqlitedbHelper sqlitedbHelper;
    private Saveimage saveimage;
    private Bitmap imagebit;
    private Bitmap imagebitmap;
    private byte[] imagebytes;

    private CodeScanner mCodeScanner;
    private CodeScannerView scannerView;


    //for location service
    private LocationManager locationManager;
    private LocationRequest locationRequest;
    private static final int REQUEST_CHECK_SETTINGS = 10001;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_scan, container, false);
        initView(view);
        getUserCameraPermission();
        clickEvent();
        startScan();

        return view;

    }

    private void getUserCameraPermission() {

        Dexter.withContext(getContext())
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE

                ).withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        getUserGpsLocationPermission();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();

                    }
                }).check();
    }

    private void getUserGpsLocationPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            checkGpsEnable();
        } else {
            requestPermission();
        }

    }

    private void requestPermission() {

        Dexter.withContext(getContext())
                .withPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION

                ).withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            checkGpsEnable();

                        }

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();

                    }
                }).check();

    }

    private void checkGpsEnable() {
        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

        } else {
            locationRequest = LocationRequest.create();
            locationRequest = locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(3000);
            locationRequest.setFastestInterval(2000);

            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);
            builder.setAlwaysShow(true);

            Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getContext())
                    .checkLocationSettings(builder.build());

            result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
                @Override
                public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                    try {
                        LocationSettingsResponse response = task.getResult(ApiException.class);
                    } catch (ApiException e) {
                        switch (e.getStatusCode()) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                try {
                                    ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                    resolvableApiException.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException ex) {
                                    ex.printStackTrace();
                                }
                                break;

                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                //Device does not have location
                                break;
                        }
                    }
                }
            });
        }


    }

    private void startScan() {

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            openCamera();

        } else {
            Dexter.withContext(getContext())
                    .withPermissions(
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE

                    ).withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            if (report.areAllPermissionsGranted()) {
                                openCamera();

                            }

                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                            token.continuePermissionRequest();

                        }
                    }).check();
        }
    }

    private void openCamera() {
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Vibrator vibrator = (Vibrator) getActivity().getSystemService(android.content.Context.VIBRATOR_SERVICE);

                            if (TextUtils.isEmpty(result.getText().toString())) {

                                Toast.makeText(getContext(), "no image found camera............", Toast.LENGTH_LONG).show();
                            } else {

                                vibrator.vibrate(250);


                                if (ResultParser.parseResult(result).getType() == ParsedResultType.WIFI) {

                                    String Result = result.getText().toString();
                                    String Format = String.valueOf(result.getBarcodeFormat());

                                    //camera Scanner
                                    ScanWifiResult(Result, Format);

                                } else {
                                    Intent intent = new Intent(getContext(), ScanResultActivity.class);
                                    intent.putExtra("type", "notWifi");
                                    intent.putExtra("result", result.toString());
                                    startActivity(intent);

                                }


                            }


                        } catch (Exception e) {
                        }

                        Toast.makeText(getContext(), result.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void clickEvent() {
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                        && (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {

                    mCodeScanner.startPreview();


                }


            }
        });
        attachimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {

                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(intent, 10);

                } else {
                    Dexter.withContext(getContext())
                            .withPermissions(

                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE

                            ).withListener(new MultiplePermissionsListener() {
                                @Override
                                public void onPermissionsChecked(MultiplePermissionsReport report) {
                                    if (report.areAllPermissionsGranted()) {

                                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                        intent.setType("image/*");
                                        startActivityForResult(intent, 10);

                                    }

                                }

                                @Override
                                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                    token.continuePermissionRequest();

                                }
                            }).check();
                }


            }
        });
    }

    private void initView(View view) {
        attachimage = view.findViewById(R.id.attach_image_id);
        scannerView = view.findViewById(R.id.scanner_view_id);

        sqlitedbHelper = new SqlitedbHelper(getContext());
        saveimage = new Saveimage();
        mCodeScanner = new CodeScanner(getContext(), scannerView);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10 && resultCode == RESULT_OK && data != null) {


            CropImage.activity(data.getData())
                    .setAspectRatio(2, 2)
                    .start(getContext(), this);


        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK && result != null) {
                Uri resultUri = result.getUri();
                try {
                    imagebit = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), resultUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    InputStream inputStream = getActivity().getContentResolver().openInputStream(resultUri);
                    if (inputStream == null) {
                        Toast.makeText(getContext(), "Image  null.....", Toast.LENGTH_SHORT).show();
                    }
                    imagebitmap = BitmapFactory.decodeStream(inputStream);

                    int width = imagebitmap.getWidth();
                    int height = imagebitmap.getHeight();
                    int[] pixels = new int[width * height];
                    imagebitmap.getPixels(pixels, 0, width, 0, 0, width, height);
                    imagebitmap.recycle();
                    RGBLuminanceSource source = new RGBLuminanceSource(width, height, pixels);
                    BinaryBitmap bBitmap = new BinaryBitmap(new HybridBinarizer(source));
                    MultiFormatReader reader = new MultiFormatReader();
                    try {
                        Vibrator vibrator = (Vibrator) getActivity().getSystemService(android.content.Context.VIBRATOR_SERVICE);
                        zxingresult = reader.decode(bBitmap);
                        imagebytes = saveimage.bitmaptobyteconvert(imagebit);


                        try {
                            vibrator.vibrate(250);
                            if (ResultParser.parseResult(zxingresult).getType() == ParsedResultType.WIFI) {

                                //File qr code scan
                                ScanWifiResult(zxingresult.getText().toString(), zxingresult.getBarcodeFormat().toString());


                            } else {
                                String res = zxingresult.getText().toString();
                                String formate = String.valueOf(zxingresult.getBarcodeFormat());
                                String type = "no";

                                if (TextUtils.isEmpty(res)) {
                                    Toast.makeText(getContext(), "no scan text found", Toast.LENGTH_SHORT).show();
                                } else if (TextUtils.isEmpty(formate)) {
                                    Toast.makeText(getContext(), "no qr code formate", Toast.LENGTH_SHORT).show();
                                } else if (TextUtils.isEmpty(type)) {
                                    Toast.makeText(getContext(), "no qr code type that min : text,sms phone,email etc....", Toast.LENGTH_SHORT).show();
                                } else {

                                }


                            }

                        } catch (Exception e) {

                        }

                    } catch (NotFoundException e) {

                    }
                } catch (FileNotFoundException e) {

                }

            }

        }

    }

    private void ScanWifiResult(String res, String format) {
        String Format = format;
        String type = "wifi";

        String wifiname = "";
        String wifisecuretytype = "";
        String wifipassword = "";

        String[] splitArray = res.split(";");


        if (splitArray[0].contains("S:")) {
            wifiname = splitArray[0].substring(7);
        }
        if (splitArray[0].contains("T:")) {
            wifisecuretytype = splitArray[0].substring(7);
        }
        if (splitArray[0].contains("P:")) {
            wifipassword = splitArray[0].substring(7);
        }


        if (splitArray[1].contains("T:")) {
            wifisecuretytype = splitArray[1].substring(2);
        }
        if (splitArray[1].contains("S:")) {
            wifiname = splitArray[1].substring(2);
        }
        if (splitArray[1].contains("P:")) {
            wifipassword = splitArray[1].substring(2);
        }


        if (splitArray[2].contains("P:")) {
            wifipassword = splitArray[2].substring(2);
        }
        if (splitArray[2].contains("S:")) {
            wifiname = splitArray[2].substring(2);
        }
        if (splitArray[2].contains("T:")) {
            wifisecuretytype = splitArray[2].substring(2);
        }


        Log.d("Scan", "Scan Result:  " + res);
        Log.d("Scan", "String Value: wifi " + wifiname);
        Log.d("Scan", "String Value: security " + wifisecuretytype);
        Log.d("Scan", "String Value: wifi " + wifipassword);


        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
        String formattedDate = df.format(c.getTime());


        long id = sqlitedbHelper.scann_insert_data(type, "", wifisecuretytype.trim(), wifipassword.trim(),
                "", "", "", "", "", Format, imagebytes, wifiname.trim(), formattedDate);
        if (id > 0) {
            Intent intent = new Intent(getContext(), ScanResultActivity.class);
            intent.putExtra("WifiName", wifiname.trim());
            intent.putExtra("WifiSecurity", wifisecuretytype.trim());
            intent.putExtra("WifiPassword", wifipassword.trim());
            intent.putExtra("type", "Wifi");
            startActivity(intent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                && (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {

            try {
                mCodeScanner.startPreview();
            } catch (Exception e) {
                Toast.makeText(getContext(), "some things wrong", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }


}
