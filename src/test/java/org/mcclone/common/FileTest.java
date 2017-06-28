package org.mcclone.common;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * Created by mcclone on 17-6-28.
 */
public class FileTest {

    public static void main(String[] args) {
        Resource resource = new ClassPathResource("application.properties");
        try {
            Charset charset = Charset.forName("UTF-8");
            RandomAccessFile file = new RandomAccessFile(resource.getURL().getPath(), "r");
            FileChannel fileChannel = file.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(40);
            while (fileChannel.read(byteBuffer) != -1) {
                byteBuffer.flip();
                System.out.print(charset.decode(byteBuffer).toString());
                byteBuffer.clear();
            }
            fileChannel.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}