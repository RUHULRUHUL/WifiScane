package com.bugbd.wifiscane.Fragment_Activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.bugbd.wifiscane.R;
import com.bugbd.wifiscane.Sqlitedatabase.SqlitedbHelper;
import com.bugbd.wifiscane.imagesavesdcard.Saveimage;

import net.glxn.qrgen.android.QRCode;
import net.glxn.qrgen.core.scheme.Wifi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
public class MakeqrcodeFragment extends Fragment {

    private Saveimage saveimage;

    private View view;
    private EditText network_name_ssid,newtork_password;
    private Spinner spinner;
    private ImageView imageView;
    private Bitmap bitmap;
    private String security_type;
    private Wifi wifi;

    private String type="wifi";
    private String qrcode_type="Qr_code";

    private Button backbutton;
    private AppCompatButton wifi_qr_generate_btn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_makeqrcode, container, false);


        saveimage=new Saveimage();


        network_name_ssid=view.findViewById(R.id.input_network_ssid_name_id);
        newtork_password=view.findViewById(R.id.network_password_id);
        spinner=view.findViewById(R.id.spinner_network_type_id);
        imageView=view.findViewById(R.id.wifi_qr_code_imageview_id);
        backbutton=view.findViewById(R.id.backpress_id);
        wifi_qr_generate_btn=view.findViewById(R.id.wifi_qr_code_gen_id);


        wifi=new Wifi();


        List<String> categories = new ArrayList<String>();
        categories.add("WPA/WPA2");
        categories.add("WEP");
        categories.add("NONE");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                security_type = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        wifi_qr_generate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String network_name=network_name_ssid.getText().toString();
                String network_password=newtork_password.getText().toString();
                if (TextUtils.isEmpty(network_name))
                {
                    network_name_ssid.setError("Input network name");

                }
                if (TextUtils.isEmpty(network_password))
                {
                    newtork_password.setError("Input input password");

                }

                else {

                    switch (security_type)
                    {
                        case "WPA/WPA2":
                        {

                            wifi.setSsid(network_name);
                            wifi.setPsk(network_password);
                            wifi.setAuthentication("WPA/WPA2");
                            wifi.setAuthentication(Wifi.Authentication.WPA);
                            wifi.setHidden(true);
                            bitmap= QRCode.from(wifi).bitmap();
                            imageView.setImageBitmap(bitmap);
                            byte[]imagearray=saveimage.imageviewtobyteconvert(imageView);


                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
                            String formattedDate = df.format(c.getTime());

                            //String type , String input_url , String Wifi_security_mod, String password, String phone, String text, String name, String email, String addrash, String qrcode_type,byte[]imagebyte
                            //String type , String input_url , String Wifi_security_mod, String password,String phone,String text,String name,String email,String addrash, String qrcode_type, byte[]image

                            SqlitedbHelper sqlitedbHelper=new SqlitedbHelper(getContext());
                            long  rowid= sqlitedbHelper.generate_insert_data(type,"",security_type,network_password," "," ",network_name," "," ",qrcode_type,imagearray,network_name,formattedDate);


                            if (rowid>0)
                            {
                                saveimage.imagesave(getContext(),bitmap);

                            }
                            else {
                                Toast.makeText(getContext(), "data not insert: "+String.valueOf(rowid), Toast.LENGTH_SHORT).show();
                            }
                            break;

                        }
                        case "WEP":
                        {
                            wifi.setSsid(network_name);
                            wifi.setPsk(network_password);
                            wifi.setAuthentication(Wifi.Authentication.WEP);
                            wifi.setHidden(true);
                            bitmap=QRCode.from(wifi).bitmap();
                            imageView.setImageBitmap(bitmap);
                            byte[]imagearray=saveimage.imageviewtobyteconvert(imageView);


                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
                            String formattedDate = df.format(c.getTime());

                            //String type , String input_url , String Wifi_security_mod, String password, String phone, String text, String name, String email, String addrash, String qrcode_type,byte[]imagebyte
                            //String type , String input_url , String Wifi_security_mod, String password,String phone,String text,String name,String email,String addrash, String qrcode_type, byte[]image

                            SqlitedbHelper sqlitedbHelper=new SqlitedbHelper(getContext());
                            long  rowid= sqlitedbHelper.generate_insert_data(type,"",security_type,network_password," "," ",network_name," "," ",qrcode_type,imagearray,network_name,formattedDate);


                            if (rowid>0)
                            {
                                //Toast.makeText(this, "data insert successfully..: "+String.valueOf(rowid), Toast.LENGTH_SHORT).show();
                                saveimage.imagesave(getContext(),bitmap);




                            }
                            else {
                                Toast.makeText(getContext(), "data not insert: "+String.valueOf(rowid), Toast.LENGTH_SHORT).show();
                            }
                            break;

                        }
                        case "NONE":
                        {
                            wifi.setSsid(network_name);
                            wifi.setPsk(network_password);
                            wifi.setHidden(true);
                            bitmap=QRCode.from(wifi).bitmap();
                            imageView.setImageBitmap(bitmap);
                            byte[]imagearray=saveimage.imageviewtobyteconvert(imageView);

                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
                            String formattedDate = df.format(c.getTime());

                            //String type , String input_url , String Wifi_security_mod, String password, String phone, String text, String name, String email, String addrash, String qrcode_type,byte[]imagebyte
                            //String type , String input_url , String Wifi_security_mod, String password,String phone,String text,String name,String email,String addrash, String qrcode_type, byte[]image

                            SqlitedbHelper sqlitedbHelper=new SqlitedbHelper(getContext());
                            long  rowid= sqlitedbHelper.generate_insert_data(type,"",security_type,network_password," "," ",network_name," "," ",qrcode_type,imagearray,network_name,formattedDate);


                            if (rowid>0)
                            {
                                //Toast.makeText(this, "data insert successfully..: "+String.valueOf(rowid), Toast.LENGTH_SHORT).show();
                                saveimage.imagesave(getContext(),bitmap);

                            }
                            else {
                                Toast.makeText(getContext(), "data not insert: "+String.valueOf(rowid), Toast.LENGTH_SHORT).show();
                            }
                            break;

                        }
                        default:{
                            Toast.makeText(getContext(), "no selected..", Toast.LENGTH_SHORT).show();
                        }
                    }





                }
            }
        });

        return view;
    }









}
