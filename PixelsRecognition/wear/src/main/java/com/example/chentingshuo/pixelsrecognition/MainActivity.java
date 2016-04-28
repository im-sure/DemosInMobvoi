package com.example.chentingshuo.pixelsrecognition;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class MainActivity extends Activity {

    private static final String TAG = "PixelsRecognition";
    private ImageView img_1, img_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img_1 = (ImageView) findViewById(R.id.img_1);
        img_2 = (ImageView) findViewById(R.id.img_2);
        img_1.setImageDrawable(pixelsRecognition(R.drawable.ic_app_timer));
        img_2.setImageDrawable(pixelsRecognition(R.drawable.ic_ganji_life));
    }

    private Drawable pixelsRecognition (int imgID) {

        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), imgID);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] pixels = new int[width * height];
        int pixelNum = 0;
        Matrix matrix = new Matrix();

        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        for (int i = 0; i < pixels.length; i++) {
            int color = pixels[i];
            int alpha = Color.alpha(color);

            if (i%100 == 0){
                Log.d(TAG, "color is " + color);}
            if (alpha != 0) {
                pixelNum++;
            }
        }
        Log.d(TAG, "pixels.length is " + pixels.length);
        Log.d(TAG, "pixelNum is " + pixelNum);
        if (pixelNum >(pixels.length * 0.8)) {
            matrix.postScale(0.8f, 0.8f);
            Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
            return new BitmapDrawable(newBitmap);
        }
        return new BitmapDrawable(bitmap);
    }

}
