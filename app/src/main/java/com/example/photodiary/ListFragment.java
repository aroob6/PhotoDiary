package com.example.photodiary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


public class ListFragment extends Fragment {

    GridViewAdapter adapter;
    GridView gridView;
    String content, date;
    byte[] weather, img;
    TextView tv_year, tv_month;
    View dialog;
    ImageView img_cal;
    CalendarView dateCalendar;
    TextView textView;
    String sDate;
    String sMonth;
    String sDay;
    String sel_content;
    String sel_date;
    byte[] sel_img;
    byte[] sel_weather;
    Cursor cursor;
    MyDBHelper myDbHelper;
    SQLiteDatabase sqLiteOpenHelper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_list, container, false);
        tv_year = root.findViewById(R.id.tv_year);
        tv_month = root.findViewById(R.id.tv_month);
        img_cal = root.findViewById(R.id.img_cal);

        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        Log.e("mDate","" + mDate);
        SimpleDateFormat simpleDateYear = new SimpleDateFormat("yyyy");
        SimpleDateFormat simpleDateMonth = new SimpleDateFormat("MM");
        String getYear = simpleDateYear.format(mDate);
        String getMonth = simpleDateMonth.format(mDate);
        tv_year.setText(getYear);
        tv_month.setText(getMonth + "월");

        // Adapter 생성
        adapter = new GridViewAdapter();

        // 리스트뷰 참조 및 Adapter달기
        gridView = root.findViewById(R.id.gridview);
        gridView.setAdapter(adapter);

        myDbHelper = new MyDBHelper(getContext());
        sqLiteOpenHelper = myDbHelper.getReadableDatabase();

        cursor = sqLiteOpenHelper.rawQuery("select content,date, img, weather from myPhotoDiary;",null);

        while (cursor.moveToNext()){
            adapter.addItem(cursor.getString(0),cursor.getString(1),cursor.getBlob(2),cursor.getBlob(3));
        }

        sqLiteOpenHelper.close();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

                GridViewItem item = (GridViewItem) parent.getItemAtPosition(position);


                content = item.getContent();
                date = item.getDate();
                weather = item.getWeather();
                img = item.getImg();

                Intent intent = new Intent(getContext(),DetailActivity.class);
                intent.putExtra("content",content);
                intent.putExtra("date",date);
                intent.putExtra("weather",weather);
                intent.putExtra("img",img);
                startActivity(intent);
            }
        });

        img_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = (View) View.inflate(getContext(), R.layout.caldialog, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                textView = dialog.findViewById(R.id.textView);
                dateCalendar = dialog.findViewById(R.id.calendarView_visited);

                dateCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

                        if(i1 + 1 < 10) { sMonth = "0" + (i1 + 1) + ". "; }
                        else{ sMonth = (i1 + 1) + ". "; }

                        if(i2 + 1 < 11) { sDay = "0" + i2  + ""; }
                        else{ sDay = i2+""; }

                        sDate = i + ". " + sMonth + sDay ;
                        textView.setText(sDate);
                    }
                });
                builder.setView(dialog);

                builder.setPositiveButton("입력", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String sele =  textView.getText().toString();

                        myDbHelper = new MyDBHelper(getContext());
                        sqLiteOpenHelper = myDbHelper.getReadableDatabase();

                        cursor = sqLiteOpenHelper.rawQuery("select _id, content,date, img, weather from myPhotoDiary where date =='" + sele + "'", null);
                        Log.e("개수", "" + cursor.getCount());

                        if(cursor.getCount() == 1) {
                            cursor.moveToFirst();
                            Intent intent = new Intent(getContext(), DetailActivity.class);
                            intent.putExtra("content", cursor.getString(1));
                            intent.putExtra("date", cursor.getString(2));
                            intent.putExtra("img", cursor.getBlob(3));
                            intent.putExtra("weather", cursor.getBlob(4));
                            startActivity(intent);
                        }else{
                            dialogInterface.dismiss();
                            Toast.makeText(getActivity(),"해당하는 날짜에 일기가 없습니다", Toast.LENGTH_SHORT).show();
                        }



                    }
                });

                builder.show();
            }
        });


        return root;
    }
}