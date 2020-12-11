package com.example.photodiary;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ReadDBActivity extends AppCompatActivity {

    LinearLayout linearLayout;
    Intent intent;
    ImageView imageView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_db);

        MyDBHelper myDbHelper = new MyDBHelper(getApplicationContext());
        SQLiteDatabase sqLiteOpenHelper = myDbHelper.getReadableDatabase();

        linearLayout = findViewById(R.id.liinearlayout);
        imageView3 = findViewById(R.id.imageView3);

        Cursor cursor = sqLiteOpenHelper.rawQuery("select content, img,weather ,date from myPhotoDiary;",null);

        while (cursor.moveToNext()){
            TextView textView = new TextView(getApplicationContext());
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setMaxWidth(100);
            imageView.setMaxHeight(300);

            textView.setTextSize(20);
            textView.setText("내용" + cursor.getString(0) + "\nimg값" + cursor.getBlob(1) + "\n날짜" + cursor.getString(3));
            imageView.setImageBitmap(getimg(cursor.getBlob(2)));
            linearLayout.addView(textView);
            linearLayout.addView(imageView);
        }
        sqLiteOpenHelper.close();
    }

    public Bitmap getimg(byte[] b){
        Bitmap bitmap = BitmapFactory.decodeByteArray(b,0,b.length);
        return bitmap;
    }

}