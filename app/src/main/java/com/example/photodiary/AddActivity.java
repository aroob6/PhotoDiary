package com.example.photodiary;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddActivity extends AppCompatActivity {

    ImageView camera;
    ImageView btn_cancel, btn_finish;
    TextView tv_date;
    EditText et_content;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    byte[] imgbyte, capturebyte, weatherbyte;

    ConstraintLayout capture;
    ImageView sun,cloud,rain,snow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        camera = findViewById(R.id.iv_camera);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_finish = findViewById(R.id.btn_finish);
        tv_date = findViewById(R.id.tv_date);
        et_content = findViewById(R.id.et_content);
        capture = findViewById(R.id.capture);
        sun = findViewById(R.id.sun);
        cloud = findViewById(R.id.cloud);
        rain = findViewById(R.id.rain);
        snow = findViewById(R.id.snow);

        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy. MM. dd");
        String getTime = simpleDate.format(mDate);
        tv_date.setText(getTime);


        MyDBHelper helper = new MyDBHelper(this);
        final SQLiteDatabase db = helper.getWritableDatabase();



        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        clickLis(sun,"맑음");
        clickLis(cloud,"구름많음");
        clickLis(rain,"비");
        clickLis(snow,"눈");

        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BitmapDrawable drawable = (BitmapDrawable) camera.getDrawable();
                if(drawable != null) {
                    Bitmap imgbitmap = drawable.getBitmap();
                    imgbyte = getByteArrayFromBitmap(imgbitmap);
                }else{
                    imgbyte = null;
                }


                String content = et_content.getText().toString();
                String date = tv_date.getText().toString();

                ContentValues values = new ContentValues();
                values.put("content", content);
                values.put("img", imgbyte);
                values.put("weather", weatherbyte);
                //values.put("capture", capturebyte);
                values.put("date", date);
                db.insert("myPhotoDiary", null, values);
                //Log.e("capturebyte",capturebyte.toString());

               /* String query = "insert into myRestaurant (content,img,date) values('"
                        +content + "', " + imgbyte + ", '"+ date+"')";*/
                db.close();
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("restart",10);
                finish();
                startActivity(intent);
            }

        });


    }

    byte[] clickLis(ImageView imageView, String name){

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                weatherbyte = getByteArrayFromDrawable(imageView.getDrawable());
                Toast.makeText(AddActivity.this,  name + " 선택", Toast.LENGTH_SHORT).show();
            }
        });
        return weatherbyte;
    }

    public byte[] getByteArrayFromDrawable(Drawable d){

        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] data = stream.toByteArray();

        return data;
    }

    public byte[] getByteArrayFromBitmap(Bitmap d){

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        d.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] data = stream.toByteArray();

        return data;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            camera.setImageBitmap(imageBitmap);
            Log.e("imageBitmap",imageBitmap.toString());

        }
    }
}