package com.example.photodiary;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {

    Intent intent;
    String content, date;
    byte[] weather, img;

    ImageView iv_weather, iv_img;
    TextView tv_date, tv_content;
    ImageView btn_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tv_date = findViewById(R.id.tv_date);
        tv_content = findViewById(R.id.tv_content);
        iv_weather = findViewById(R.id.iv_weather);
        iv_img = findViewById(R.id.iv_img);
        btn_cancel = findViewById(R.id.btn_cancel);

        intent = getIntent();
        content = intent.getExtras().getString("content");
        date = intent.getExtras().getString("date");
        weather = intent.getExtras().getByteArray("weather");
        img = intent.getExtras().getByteArray("img");

        tv_date.setText(date);
        tv_content.setText(content);
        if(img != null) {
            iv_img.setImageBitmap(getimg(img));
        }
        iv_weather.setImageBitmap(getimg(weather));

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    public Bitmap getimg(byte[] b){
        Bitmap bitmap = BitmapFactory.decodeByteArray(b,0,b.length);
        return bitmap;
    }

}