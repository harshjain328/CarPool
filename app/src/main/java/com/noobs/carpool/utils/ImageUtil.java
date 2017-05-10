package com.noobs.carpool.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by deepak on 10/5/17.
 */

public class ImageUtil {

    /**
     * It takes an image path and convert the image to base64
     * @param path of an image
     * @return Base64 encoded string
     */
    public static String convertToBase64(String path){
        return convertToBase64(BitmapFactory.decodeFile(path));
    }


    /**
     Encodes a Bitmap image to base64 String
     @param Bitmap image
     @return Base64 encoded string
     */
    public static String convertToBase64(Bitmap bm){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        return Base64.encodeToString(bos.toByteArray(), Base64.DEFAULT);
    }


    /**
     Decodes base64 encoded string to Bitmap image
     @param Base64 encoded string
     @return Bitmap Instance
     */
    public static Bitmap convertFromBase64ToBitmap(String encodedString){
        byte[] imageByteArray = Base64.decode(encodedString, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
    }

}
