package com.example.photodiary;

import android.app.Activity;

public class GridViewItem extends Activity {

    private String scontent;
    private byte[] bimg;
    private byte[] bweather;
    private String sdate;

    public void setContent(String content) { scontent = content; }
    public String getContent() { return this.scontent; }

    public void setDate(String date) { sdate = date; }
    public String getDate() { return this.sdate; }

    public void setImg(byte[] img) { bimg = img; }
    public byte[] getImg() { return this.bimg; }

    public void setWeather(byte[] weather) { bweather = weather; }
    public byte[] getWeather() { return this.bweather; }
}
