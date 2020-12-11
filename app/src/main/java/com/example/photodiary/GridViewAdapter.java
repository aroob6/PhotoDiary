package com.example.photodiary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class GridViewAdapter extends BaseAdapter {

    private ImageView imageView;
    Context context;

    private ArrayList<GridViewItem> listViewItemList = new ArrayList<>();

    public GridViewAdapter() { this.context = context; }

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }


    public void addItem(String content, String date, byte[] img, byte[] weather) {
        GridViewItem item = new GridViewItem();

        item.setContent(content);
        item.setDate(date);
        item.setWeather(weather);
        item.setImg(img);

        listViewItemList.add(item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();


        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.gridview_item, parent, false);
        }

        GridViewItem listViewItem = listViewItemList.get(pos);

        ImageView ImageView = (ImageView) convertView.findViewById(R.id.imageView);
        ImageView.setImageBitmap(getimg(listViewItem.getWeather()));

        return convertView;
    }
    public Bitmap getimg(byte[] b){
        Bitmap bitmap = BitmapFactory.decodeByteArray(b,0,b.length);
        return bitmap;
    }


}
