package com.riseghost.nebulamobile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class display_images extends AppCompatActivity {
    private ScaleGestureDetector scaleGestureDetector;
    private ImageView imageView;
    private float scaleFactor = 1.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_display_images);
        String FileName = getIntent().getStringExtra("FileName");
        byte[] data = getIntent().getByteArrayExtra("data");

        TextView Name = findViewById(R.id.FileName);
        this.imageView = findViewById(R.id.ImageView);

        Name.setText(FileName);

        Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
        imageView.setImageBitmap(bitmap);
       this.scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListenner());
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