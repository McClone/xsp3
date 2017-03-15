package org.mcclone;

import java.util.concurrent.CountDownLatch;

/**
 * Created by mcclone on 17-3-15.
 */
public class Job implements Runnable {

    private CountDownLatch countDownLatch;

    public Job(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(5000);
            System.out.println(Thread.currentThread().getId());
            countDownLatch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
