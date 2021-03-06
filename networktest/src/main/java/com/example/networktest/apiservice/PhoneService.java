package com.example.networktest.apiservice;

import com.example.networktest.entity.PhoneEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by gzhuo on 2016/4/19.
 */
public interface PhoneService {
    @GET("/apistore/mobilenumber/mobilenumber")
    Call<PhoneEntity> getResult(@Header("apikey") String apikey, @Query("phone") String phone);
}
