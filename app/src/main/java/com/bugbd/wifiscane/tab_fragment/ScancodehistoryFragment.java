package com.bugbd.wifiscane.tab_fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bugbd.wifiscane.R;
import com.bugbd.wifiscane.Scan_Result.ShowItemResult;
import com.bugbd.wifiscane.Sqlitedatabase.SqlitedbHelper;
import com.bugbd.wifiscane.adapter.Scan_qr_code_Adapter;
import com.bugbd.wifiscane.model.Item_model;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScancodehistoryFragment extends Fragment implements Scan_qr_code_Adapter.Contact_Adapter {

    private View view;
    private ArrayList<Item_model> arrayList;
    private RecyclerView recyclerView;
    private SqlitedbHelper sqlitedbHelper;
    private Scan_qr_code_Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_scancodehistory, container, false);

        arrayList = new ArrayList<>();
        sqlitedbHelper = new SqlitedbHelper(getContext());

        recyclerView = view.findViewById(R.id.recycular_view_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new Scan_qr_code_Adapter(getContext(), arrayList, this::Onitemsellect);

        recyclerView.setAdapter(adapter);

        Cursor cursor = sqlitedbHelper.scanquerydata();

        while (cursor.moveToNext()) {
            String type = cursor.getString(1);
            String wifi_security_mod = cursor.getString(2);
            String password = cursor.getString(3);
            String phone = cursor.getString(4);
            String text = cursor.getString(5);
            String name = cursor.getString(6);
            String email = cursor.getString(7);
            String addrash = cursor.getString(8);
            String qrcode_type = cursor.getString(9);
            byte[] imagebyte = cursor.getBlob(10);
            String input_url = cursor.getString(11);
            String wifi_name = cursor.getString(12);
            String time_date = cursor.getString(13);
            // String type, String input_url, String wifi_security_mod, String password, String phone, String text, String name, String email, String addrash, String qrcode_type, byte[] imagebyte, String wifi_name
            arrayList.add(new Item_model(type, input_url, wifi_security_mod, password, phone, text, name, email, addrash, qrcode_type, imagebyte, wifi_name, time_date));

        }
        return view;
    }

    @Override
    public void Onitemsellect(Item_model item_model) {

        Intent intent = new Intent(getContext(), ShowItemResult.class);
        intent.putExtra("imagebyte", item_model.getImagebyte());
        intent.putExtra("wifi_name", item_model.getWifi_name());
        intent.putExtra("wifi_security_mod", item_model.getWifi_security_mod());
        intent.putExtra("password", item_model.getPassword());
        intent.putExtra("time_date", item_model.getTime_date());
        startActivity(intent);

    }
}
