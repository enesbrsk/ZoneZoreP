package com.example.zonezre.RestApi;


import com.example.zonezre.Models.DoctorsItem;
import com.example.zonezre.Models.DoctorsModel;

import java.util.List;

import retrofit2.Call;

public class ManagerAll extends BaseManager{

    private  static ManagerAll ourInstance = new ManagerAll();

    public  static synchronized ManagerAll getInstance()
    {
        return  ourInstance;
    }


    public Call<DoctorsModel> getUser()
    {
        Call<DoctorsModel> x = getRestApi().getUser();
        return  x ;

    }

}
