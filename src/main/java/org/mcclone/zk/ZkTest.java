package org.mcclone.zk;

/**
 * Created by mcclone on 17-6-11.
 */
public class ZkTest {

    public static void main(String[] args) {
        DistributedLock lock = new DistributedLock("localhost:2181", "lock");
        lock.lock();
        //共享资源
        if (lock != null)
            lock.unlock();
    }


}
