package com.bugbd.wifiscane.imagesavesdcard;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Saveimage {
    private Context context;
    private Bitmap bitmap;
    private File sdcard;
    private File derectory;
    private static String filename;
    private FileOutputStream fileOutputStream=null;



    public void imagesave(final Context context, final Bitmap bitmap)
    {

        this.context=context;


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        final EditText inputedittext = new EditText(context);
        inputedittext.setHint("input image name..");
        alertDialogBuilder.setTitle("Qr_Bar code ");

        final Bitmap finalBitmap = bitmap;
        alertDialogBuilder
                .setMessage("Are you sure you want to save phone memory :")
                .setCancelable(true)
                .setView(inputedittext)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        filename = inputedittext.getText().toString();

                        sdcard= Environment.getExternalStorageDirectory().getAbsoluteFile();
                        derectory=new File(sdcard+"/QR_BAR_CODE_FOLDER");
                        if (!derectory.exists())
                        {
                            derectory.mkdir();


                            File outputfile=new File(derectory,filename+"_"+System.currentTimeMillis()+".jpg");

                            try {
                                fileOutputStream=new FileOutputStream(outputfile);
                                finalBitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
                                try {
                                    fileOutputStream.flush();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    fileOutputStream.close();
                                    Toast.makeText(context, "image saved gallery", Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                            Intent intent=new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                            intent.setData(Uri.fromFile(outputfile));
                            context.sendBroadcast(intent);

//                            try {
//                                addshow();
//                            }catch (Exception e)
//                            {
//
//                            }




                        }
                        else {

                            File outputfile=new File(derectory,filename+"_"+System.currentTimeMillis()+".jpg");

                            try {
                                fileOutputStream=new FileOutputStream(outputfile);
                                finalBitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
                                try {
                                    fileOutputStream.flush();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    fileOutputStream.close();
                                    Toast.makeText(context, "image saved gallery", Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                            Intent intent=new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                            intent.setData(Uri.fromFile(outputfile));
                            context.sendBroadcast(intent);


//                            try {
//                                addshow();
//                            }catch (Exception e)
//                            {
//
//                            }

                        }


                    }
                })
                .setNegativeButton("CANCEL",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();


    }

    public byte[] imageviewtobyteconvert(ImageView imageView)
    {
        BitmapDrawable bitmapDrawable= (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap=bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG,90,byteArrayOutputStream);
        byte[] byteArray=byteArrayOutputStream.toByteArray();
        return byteArray;

    }
    public byte[] bitmaptobyteconvert(Bitmap bitmap)
    {

        ByteArrayOutputStream byteArrayOutputStream=new
                ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG,90,byteArrayOutputStream);
        byte[] byteArray=byteArrayOutputStream.toByteArray();
        return byteArray;

    }


}
