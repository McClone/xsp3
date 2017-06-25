package org.mcclone.common.guava;

import com.google.common.math.LongMath;

import java.math.RoundingMode;

/**
 * Created by mcclone on 17-6-16.
 */
public class MathExample {

    public static void main(String[] args) {
        long l = 1231232312312300000L;
        System.out.println(LongMath.divide(l, 1024 * 1024 * 1024 * 1024L, RoundingMode.CEILING));
    }
}
