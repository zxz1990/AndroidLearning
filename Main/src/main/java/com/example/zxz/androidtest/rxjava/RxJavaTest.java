package com.example.zxz.androidtest.rxjava;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhuxuezhi on 2019/1/16.
 */
public class RxJavaTest {
    private static final String TAG = "RxJavaTest";
    
    void print(Object o) {
        Log.i(TAG, "" + o + "");
    }

    public void test() {
        Log.d(TAG, "test() called");

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                print(0);

                emitter.onNext(1);
                print(1);

                emitter.onNext(2);
                print(2);

                emitter.onNext(3);
                print(3);
                emitter.onComplete();
                print("complete");

                emitter.onNext(4);
                print(4);

            }
        })
                .subscribeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        print("accept 1 " + integer);
                    }
                })
                .observeOn(Schedulers.io())
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        print("accept 2 " + integer);
//                    }
//                });
 .subscribe(new Observer<Integer>() {

            private int i;
            private Disposable mDisposable;

            @Override
            public void onSubscribe(Disposable d) {
                mDisposable = d;
                print("onSubscribe");

            }

            @Override
            public void onNext(Integer integer) {
                i++;
//                if (i == 2) {
//                    mDisposable.dispose();
//                }
                print("onNext:" + integer + " i=" + i);
            }

            @Override
            public void onError(Throwable e) {
//                e.printStackTrace();
                print("error");
            }

            @Override
            public void onComplete() {
                print("onComplete");
            }
        });
    }

    public static void main(String[] args) {
        new RxJavaTest().test();
    }
}
