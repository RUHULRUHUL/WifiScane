package com.bugbd.wifiscane.imagesave;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Savestorage {
    private Context context;
    private Bitmap bitmap;
    private File sdcard;
    private File derectory;
    private static String filename;
    private FileOutputStream fileOutputStream=null;
    private Bitmap outputbitmap;


    public void imagesave(final Context context, final Bitmap bitmapImage)
    {
        OutputStream fOut;
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), "Encoded" + ".PNG"); // the File to save ,
        try {
            fOut = new FileOutputStream(file);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fOut); // saving the Bitmap to a file
            fOut.flush(); // Not really required
            fOut.close(); // do not forget to close the stream
//            whether_encoded.post(new Runnable() {
//                @Override
//                public void run() {
//                    save.dismiss();
//                }
//            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


//        filename="img";
//
//        outputbitmap=bitmap;
//        sdcard= Environment.getExternalStorageDirectory().getAbsoluteFile();
//        derectory=new File(sdcard+"/Encrip_decrip");
//        if (!derectory.exists())
//        {
//
//            derectory.mkdir();
//
//            Toast.makeText(context, "image has been save", Toast.LENGTH_SHORT).show();
//
//            File outputfile=new File(derectory,filename+"_"+System.currentTimeMillis()+".jpg");
//
//            try {
//                fileOutputStream=new FileOutputStream(outputfile);
//                outputbitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
//                Toast.makeText(context, "image has been saved: ", Toast.LENGTH_SHORT).show();
//                try {
//                    fileOutputStream.flush();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    //progressDialog.dismiss();
//                }
//                try {
//                    fileOutputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    //progressDialog.dismiss();
//                }
//
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//                //progressDialog.dismiss();
//            }
//
//            Intent intent=new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//            intent.setData(Uri.fromFile(outputfile));
//            context.sendBroadcast(intent);
//            //progressDialog.dismiss();
//
//
//        }
//        else {
//            //progressDialog.show();
//
//            File outputfile=new File(derectory,filename+"_"+System.currentTimeMillis()+".jpg");
//
//            try {
//                fileOutputStream=new FileOutputStream(outputfile);
//                outputbitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
//                try {
//                    fileOutputStream.flush();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    //progressDialog.dismiss();
//                }
//                try {
//                    fileOutputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    //progressDialog.dismiss();
//                }
//
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//               // progressDialog.dismiss();
//            }
//
//            Intent intent=new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//            intent.setData(Uri.fromFile(outputfile));
//            context.sendBroadcast(intent);
//            //progressDialog.dismiss();
//
//        }
//
//
//        /*
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
//        final EditText inputedittext = new EditText(context);
//        alertDialogBuilder.setTitle("Qr_Bar code ");
//
//        final Bitmap finalBitmap = bitmap;
//        alertDialogBuilder
//                .setMessage("Are you sure you want to save phone memory :")
//                .setCancelable(true)
//                .setView(inputedittext)
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//
//
//                    }
//                })
//                .setNegativeButton("CANCEL",new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//
//                        dialog.cancel();
//                    }
//                });
//
//        // create alert dialog
//        AlertDialog alertDialog = alertDialogBuilder.create();
//
//        // show it
//        alertDialog.show();
//
//         */


    }

    public byte[] imageviewtobyteconvert(ImageView imageView)
    {
        BitmapDrawable bitmapDrawable= (BitmapDrawable)
                imageView.getDrawable();
        Bitmap bitmap=bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArrayOutputStream=new
                ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        byte[] byteArray=byteArrayOutputStream.toByteArray();
        return byteArray;

    }
    public byte[] bitmaptobyteconvert(Bitmap bitmap)
    {
//        BitmapDrawable bitmapDrawable= (BitmapDrawable)
//                imageView.getDrawable();
//        Bitmap bitmap1=bitmap;
        ByteArrayOutputStream byteArrayOutputStream=new
                ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        byte[] byteArray=byteArrayOutputStream.toByteArray();
        return byteArray;

    }
}
