package com.riseghost.nebulamobile;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class display_images extends AppCompatActivity {
    private ScaleGestureDetector scaleGestureDetector;
    private ImageView imageView;
    private float scaleFactor = 1.0f;
    private Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_display_images);
        String FileName = getIntent().getStringExtra("FileName");
        String path  = getIntent().getStringExtra("path");
        String NebulaURL = getIntent().getStringExtra("NebulaURL");
        String SessionCookie = getIntent().getStringExtra("SessionCookie");

        TextView Name = findViewById(R.id.FileName);
        this.imageView = findViewById(R.id.ImageView);

        Name.setText(FileName);
        NebulaRequestFile nebulaRequestFile = new NebulaRequestFile(NebulaURL,SessionCookie,path);
        try{
            nebulaRequestFile.join();
            byte[] data = nebulaRequestFile.getByteArray();
            bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
            imageView.setImageBitmap(bitmap);
        }   catch (Exception e){
            Log.e("NEBULABITMAP",e.getMessage());
        }
        this.scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListenner());

        ImageButton download = findViewById(R.id.download);
        download.setOnClickListener((e) -> {
            if (bitmap != null){
                saveImageToGallery(getApplicationContext(),bitmap);
                Toast.makeText(this,"Saving",Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void saveImageToGallery(Context context, Bitmap bitmap) {
        ContentResolver contentResolver = context.getContentResolver();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, "image_" + System.currentTimeMillis() + ".jpg");
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");

        // Insert image to the MediaStore
        android.net.Uri imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

        if (imageUri != null) {
            try (OutputStream outputStream = contentResolver.openOutputStream(imageUri)) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] bytes = byteArrayOutputStream.toByteArray();
                outputStream.write(bytes);

                // Add image to gallery
                MediaStore.Images.Media.insertImage(contentResolver, imageUri.toString(), "Image", "Image description");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.scaleGestureDetector.onTouchEvent(event);
        return true;
    }

    private class ScaleListenner extends ScaleGestureDetector.SimpleOnScaleGestureListener{
        @Override
        public boolean onScale(ScaleGestureDetector detector){
            scaleFactor *= detector.getScaleFactor();
            imageView.setScaleX(scaleFactor);
            imageView.setScaleY(scaleFactor);
            return true;
        }
    }
}