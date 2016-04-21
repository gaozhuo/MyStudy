package com.example.networktest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.networktest.apiservice.PhoneService;
import com.example.networktest.apiservice.PhoneService2;
import com.example.networktest.entity.PhoneEntity;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RetroActivity extends AppCompatActivity {
    //百度电话号码查询api
    private static final String BASE_URL = "http://apis.baidu.com";
    private static final String API_KEY = "8e13586b86e4b7f3758ba3bd6c9c9135";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retro);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testRetrofit();
            }
        });
    }

    private void testRetrofit() {
        OkHttpClient client = new OkHttpClient.Builder().addNetworkInterceptor(new LoggingInterceptor()).build();

        Retrofit retrofit = new Retrofit.Builder().client(client).baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        PhoneService service = retrofit.create(PhoneService.class);
        Call<PhoneEntity> call = service.getResult(API_KEY, "18819012482");
        call.enqueue(new Callback<PhoneEntity>() {
            @Override
            public void onResponse(Call<PhoneEntity> call, Response<PhoneEntity> response) {
                if (response.isSuccessful()) {
                    PhoneEntity phoneEntity = response.body();
                    if (phoneEntity != null) {
                        Log.d("gaozhuo", phoneEntity.getRetData().toString());

                    }
                }

            }

            @Override
            public void onFailure(Call<PhoneEntity> call, Throwable t) {

            }
        });

    }

    private void testRetrofit2() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        PhoneService2 service = retrofit.create(PhoneService2.class);
        Observable<PhoneEntity> observable = service.getResult(API_KEY, "18819012482");
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<PhoneEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(PhoneEntity phoneEntity) {
                if (phoneEntity != null) {
                    Log.d("gaozhuo", phoneEntity.getRetData().toString());
                }
            }
        });

    }

    class LoggingInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            Log.d("gaozhuo", String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));

            okhttp3.Response response = chain.proceed(request);

            long t2 = System.nanoTime();
            Log.d("gaozhuo", String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));

            return response;
        }
    }
}
