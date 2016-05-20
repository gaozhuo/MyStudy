package com.example.networktest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RxJavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test2();
            }
        });
    }

    private void test1() {
        Subscriber<String> subscriber = new Subscriber<String>() {

            @Override
            public void onStart() {
                super.onStart();
                Log.d("gaozhuo", "onStart");
            }

            @Override
            public void onCompleted() {
                Log.d("gaozhuo", "onCompleted");

            }

            @Override
            public void onError(Throwable e) {
                Log.d("gaozhuo", "onError");

            }

            @Override
            public void onNext(String s) {
                Log.d("gaozhuo", "s=" + s);

            }
        };

        Action1<String> onNextAction = new Action1<String>() {
            // onNext()
            @Override
            public void call(String s) {
                Log.d("gaozhuo", "thread 2 id=" + Thread.currentThread().getId());
                Log.d("gaozhuo", s);
            }
        };


        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                Log.d("gaozhuo", "thread 1 id=" + Thread.currentThread().getId());
                subscriber.onNext("Hello");
                subscriber.onNext("Hi");
                subscriber.onNext("Aloha");
                subscriber.onCompleted();

            }
        });
        //Observable observable = Observable.just("you", "are", "best");

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(onNextAction);

    }


    private void test2() {
        Subscriber<Integer> subscriber = new Subscriber<Integer>() {

            @Override
            public void onStart() {
                super.onStart();
                Log.d("gaozhuo", "onStart");
            }

            @Override
            public void onCompleted() {
                Log.d("gaozhuo", "onCompleted");

            }

            @Override
            public void onError(Throwable e) {
                Log.d("gaozhuo", "onError");

            }

            @Override
            public void onNext(Integer s) {
                Log.d("gaozhuo", "s=" + s);

            }
        };

        Observable.just(1, 2, 3, 4) // IO 线程，由 subscribeOn() 指定
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .map(new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer) {
                        return integer + 10;
                    }
                }) // 新线程，由 observeOn() 指定
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);  // Android 主线程，由 observeOn() 指定
    }


}
