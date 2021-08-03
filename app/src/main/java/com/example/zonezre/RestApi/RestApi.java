package com.example.zonezre.RestApi;

import com.example.zonezre.Models.DoctorsModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RestApi {

    @GET("android/doctors.json")
    Call<DoctorsModel> getUser();


}
