package org.mcclone;

import com.google.common.base.Strings;
import com.google.common.primitives.Bytes;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.ArrayUtils;

/**
 * Created by Administrator on 2017/10/4.
 */
public class Main {

    public static void main(String[] args) {
        byte[] bytes = Integer.toBinaryString(11).getBytes();
        String s = Strings.repeat("0", 8 - bytes.length);
        byte[] result = Bytes.concat(s.getBytes(), bytes, RandomStringUtils.randomAlphabetic(10).getBytes());
        System.out.println(new String(result));
        byte[] header = ArrayUtils.subarray(result, 0, 8);
        System.out.println(new String(header));
    }
}
