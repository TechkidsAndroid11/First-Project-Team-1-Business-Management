package com.example.haihoang.managemaster.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by haihoang on 10/23/17.
 */

public class ImageUtils {
    private static File tempFile;
    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex=image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);

       Log.e("base64   ", imageEncoded);
        return imageEncoded;
    }

    public static void saveImage(Bitmap bitmap, Context context) throws FileNotFoundException {
        String root = Environment.getExternalStorageDirectory().toString();
        File myFolder = new File(root + "/DrawingNotes");
        myFolder.mkdirs();

        String imageName = Calendar.getInstance().getTimeInMillis() + ".png";
        Log.e("fileLocation", "saveImage: " + imageName);

        File imageFile = new File(myFolder, imageName);

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

            MediaScannerConnection.scanFile(context, new String[]{imageFile.getAbsolutePath()}, null, null);
            Toast.makeText(context, "saved!", Toast.LENGTH_SHORT).show();

        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Uri getUriFromImage(Context context){
        //create temp file
        tempFile = null;

        try {
            tempFile = File.createTempFile(
                    Calendar.getInstance().getTime().toString(), ".jpg", context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

            );

            Log.e("Uri", "GetUriFromImage: " + tempFile.getPath());
            tempFile.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //get uri

        Uri uri = null;
        if(tempFile != null){
            uri = FileProvider.getUriForFile(
                    context,
                    context.getPackageName() + ".provider",
                    tempFile
            );
        }
        Log.e("Uri", "Get Uri from Image: " + uri);
        return uri;
    }

    public static Bitmap getBitmap(Context context ){
        Bitmap bitmap = BitmapFactory.decodeFile(tempFile.getPath());
        //scale
        int screenWith = context.getResources().getDisplayMetrics().widthPixels;
        double ratio = (double) bitmap.getWidth() / bitmap.getHeight();

        Bitmap scaleBitmap = Bitmap.createScaledBitmap(bitmap, screenWith, (int) (screenWith/ratio), false);
        return scaleBitmap;
    }

    public static String resizeBase64Image(String base64image){
        byte [] encodeByte=Base64.decode(base64image.getBytes(),Base64.DEFAULT);
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inPurgeable = true;
        Bitmap image = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length,options);


        if(image.getHeight() <= 400 && image.getWidth() <= 400){
            return base64image;
        }
        image = Bitmap.createScaledBitmap(image, 250, 400, false);

        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG,100, baos);

        byte [] b=baos.toByteArray();
        System.gc();
        return Base64.encodeToString(b, Base64.NO_WRAP);

    }

}
