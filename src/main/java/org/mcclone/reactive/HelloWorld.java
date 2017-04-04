package org.mcclone.reactive;

import io.reactivex.Flowable;

/**
 * Created by mcclone on 17-4-4.
 */
public class HelloWorld {

    public static void main(String[] args) {
        Flowable.just("Hello world").subscribe(System.out::println);
    }
}
