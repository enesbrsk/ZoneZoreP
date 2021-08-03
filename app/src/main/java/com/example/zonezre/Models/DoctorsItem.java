package com.example.zonezre.Models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DoctorsItem{
    private static final String TAG = "DoctorsItem";
    private String user_status;
    private Image image;
    private String full_name;
    private String gender;
    private String mImageUrl;
    private Context mContext;


    public DoctorsItem(String full_name,String imageUrl, String user_status, String gender, Context context) {
        this.user_status = user_status;
        this.full_name = full_name;
        this.gender = gender;
        mImageUrl = imageUrl;
        this.mContext = context;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setUserStatus(String userStatus){
        this.user_status = userStatus;
    }

    public String getUserStatus(){
        return user_status;
    }

    public void setImage(Image image){
        this.image = image;
    }

    public Image getImage(){
        return image;
    }

    public void setFullName(String fullName){
        this.full_name = fullName;
    }

    public String getFullName(){
        return full_name;
    }

    public void setGender(String gender){
        this.gender = gender;
    }

    public String getGender(){
        return gender;
    }

    @Override
     public String toString(){
        return 
            "DoctorsItem{" + 
            "user_status = '" + user_status + '\'' +
            ",image = '" + image + '\'' + 
            ",full_name = '" + full_name + '\'' +
            ",gender = '" + gender + '\'' + 
            "}";
        }
}
