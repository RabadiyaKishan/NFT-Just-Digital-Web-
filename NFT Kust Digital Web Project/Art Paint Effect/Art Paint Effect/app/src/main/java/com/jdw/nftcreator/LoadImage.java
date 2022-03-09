package com.jdw.nftcreator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class LoadImage {
    public static int calculateInSampleSize(Options options, int reqWidth, int reqHeight) {
        Log.d("test", "calculateInSampleSize reqquired size " + reqWidth + " " + reqHeight);
        int height = options.outHeight;
        int width = options.outWidth;
        if (height <= reqHeight && width <= reqWidth) {
            return 1;
        }
        int inSampleSize = Math.min(Math.round(((float) height) / ((float) reqHeight)), Math.round(((float) width) / ((float) reqWidth)));
        Log.d("test", "calculateInSampleSize reqquired size " + reqWidth + " " + reqHeight + " insample " + inSampleSize);
        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Context context, Uri uri, int reqWidth, int reqHeight) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        InputStream is = null;
        try {
            is = context.getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        BitmapFactory.decodeStream(is, null, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        Log.d("test", "samplesize  " + options.inSampleSize);
        options.inJustDecodeBounds = false;
        InputStream inputs = null;
        try {
            inputs = context.getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeStream(inputs, null, options);
    }
}
