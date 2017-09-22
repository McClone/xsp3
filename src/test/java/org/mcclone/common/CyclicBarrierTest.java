package org.mcclone.common;

import org.apache.commons.lang.RandomStringUtils;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by mcclone on 17-9-22.
 */
public class CyclicBarrierTest {

    public static void main(String[] args) {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    System.out.println(finalI);
                    Thread.sleep(Long.parseLong(RandomStringUtils.randomNumeric(3)));
                    cyclicBarrier.await();
                    System.out.println(System.currentTimeMillis() + ":" + finalI);
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
