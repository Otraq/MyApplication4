package com.example.amin.criminalintent.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

/**
 * Created by Amin on 7/11/2018.
 */

public class PictureUtils {
    public static Bitmap getScaledBitmap(String path, int desWidth, int desHeight) {
        BitmapFactory.Options readOnlyOption = new BitmapFactory.Options();
        readOnlyOption.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, readOnlyOption);

        int srcWidth = readOnlyOption.outWidth;
        int srcHeight = readOnlyOption.outHeight;

        int inSampleSize = 1;
        if (srcWidth > desWidth || srcHeight > desHeight) {
            if (srcWidth > srcHeight) {
                inSampleSize = Math.round(srcHeight / desHeight);
            } else {
                inSampleSize = Math.round(srcWidth / desWidth);
            }
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);

        return bitmap;
    }

    public static Bitmap getScaledBitmap(String path, Activity activity) {
        Point point = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(point);
        return getScaledBitmap(path, point.x, point.y);
    }
}
