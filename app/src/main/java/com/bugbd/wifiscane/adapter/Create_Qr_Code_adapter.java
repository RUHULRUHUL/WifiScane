package com.bugbd.wifiscane.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bugbd.wifiscane.R;
import com.bugbd.wifiscane.model.Item_model;

import java.util.ArrayList;

public class Create_Qr_Code_adapter extends RecyclerView.Adapter<Create_Qr_Code_adapter.ViewHolder> {
    private Context context;
    private ArrayList<Item_model> arrayList;
    private Bitmap bitmap;
    public Contact_Adapter contact_adapter;


    public Create_Qr_Code_adapter(Context context, ArrayList<Item_model> arrayList, Contact_Adapter contact_adapter) {
        this.context = context;
        this.arrayList = arrayList;
        this.contact_adapter = contact_adapter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_qrcode_sample_design, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Item_model item_model = arrayList.get(position);

        String type = item_model.getType();
        byte[] byte_image_array = item_model.getImagebyte();

        if (byte_image_array != null) {
            bitmap = BitmapFactory.decodeByteArray(byte_image_array, 0, byte_image_array.length);
        }

        if (type.equalsIgnoreCase("wifi")) {
            holder.title_messege.setText(item_model.getWifi_name());
            holder.qrcode_type.setText(item_model.getQrcode_type() + ": " + item_model.getType());
            holder.timedate.setText(item_model.getTime_date());
            try {
                holder.imageView.setImageBitmap(bitmap);
            } catch (Exception e) {

            }
        }

    }

    @Override
    public int getItemCount() {
        if (arrayList != null) {
            return arrayList.size();
        } else {
            return 0;
        }

    }

    public interface Contact_Adapter {
        void Sellectitem_create(Item_model item_model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView title_messege;
        private TextView qrcode_type;
        private TextView timedate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            imageView = itemView.findViewById(R.id.qrcode_type_image_id);
            title_messege = itemView.findViewById(R.id.Qrcode_type_messege_id);
            qrcode_type = itemView.findViewById(R.id.qrcode_type_id);
            timedate = itemView.findViewById(R.id.date_time_id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    contact_adapter.Sellectitem_create(arrayList.get(getAdapterPosition()));
                }
            });

        }
    }
}
