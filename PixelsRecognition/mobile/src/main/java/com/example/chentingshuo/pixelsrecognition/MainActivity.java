package com.example.chentingshuo.pixelsrecognition;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView img_1, img_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img_1 = (ImageView) findViewById(R.id.img_1);
        img_2 = (ImageView) findViewById(R.id.img_2);
        img_1.setImageResource(R.drawable.ic_alipay);
        img_2.setImageDrawable(pixelsRecognition(R.drawable.ic_alipay));
    }

    private Drawable pixelsRecognition (int imgID) {

        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), imgID);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] pixels = new int[width * height];
        int pixelNum = 0;
        Matrix matrix = new Matrix();
        //BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inJustDecodeBounds = true;
        //options.inSampleSize = 1;

        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        for (int i = 0; i < pixels.length; i++) {
            int color = pixels[i];
            int alpha = Color.alpha(color);
            if (alpha != 0) {
                pixelNum++;
            }
        }
        if (pixelNum >(pixels.length)) {
            //options.inSampleSize = 2;
            matrix.postScale(0.7f, 0.7f);
        }
        //bitmap = BitmapFactory.decodeResource(this.getResources(), imgID, options);
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return new BitmapDrawable(newBitmap);
    }

}
