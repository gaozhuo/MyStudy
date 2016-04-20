package com.example.networktest.apiservice;

import com.example.networktest.entity.PhoneEntity;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by gzhuo on 2016/4/20.
 */
public interface PhoneService2 {

    @GET("/apistore/mobilenumber/mobilenumber")
    Observable<PhoneEntity> getResult(@Header("apikey") String apikey, @Query("phone") String phone);

}
