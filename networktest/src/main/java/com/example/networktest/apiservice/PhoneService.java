package com.example.networktest.apiservice;

import com.example.networktest.entity.PhoneEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * Created by gzhuo on 2016/4/19.
 */
public interface PhoneService {
//    @GET("/apistore/mobilenumber/mobilenumber")
//    Call<PhoneEntity> getResult(@Header("apikey") String apikey, @Query("phone") String phone);

    @GET("/apistore/mobilenumber/mobilenumber?phone={phone}")
    Call<PhoneEntity> getResult(@Header("apikey") String apikey, @Path("phone") String phone);
}
