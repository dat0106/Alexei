package com.kimo.lib.alexei.helpers;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

/**
 * Created by Kimo on 8/15/14.
 */
public class PixelUtils {

    public static int [][] getPixelsMatrixFromBitmap(Bitmap bitmap) {

        int [][] pixelMatrix = new int[bitmap.getWidth()][bitmap.getHeight()];

        for(int i = 0; i < bitmap.getWidth(); i++)
            for(int j = 0; j < bitmap.getHeight(); j++)
                pixelMatrix[i][j] = bitmap.getPixel(i,j);

        return pixelMatrix;

    }

    public static Bitmap getBitmapFromImageView(ImageView theImage) {
        return ((BitmapDrawable)theImage.getDrawable()).getBitmap();
    }
}
