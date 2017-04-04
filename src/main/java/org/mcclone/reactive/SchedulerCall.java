package org.mcclone.reactive;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by mcclone on 17-4-4.
 */
public class SchedulerCall {

    public static void main(String[] args) throws InterruptedException {
        Flowable.fromCallable(() -> {
            Thread.sleep(1000);
            return "Done";
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe(System.out::println, Throwable::printStackTrace);
        Thread.sleep(2000);
    }
}
