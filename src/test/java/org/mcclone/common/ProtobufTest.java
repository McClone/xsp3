package org.mcclone.common;

import org.mcclone.proto.Response;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by mcclone on 17-7-23.
 */
public class ProtobufTest {

    public static void main(String[] args) throws IOException {

        Response.Resp resp = Response.Resp
                .newBuilder()
                .setCode(200)
                .setMessage("test")
                .build();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        resp.writeTo(output);

        byte[] byteArray = output.toByteArray();

        ByteArrayInputStream input = new ByteArrayInputStream(byteArray);

        Response.Resp resp_ = Response.Resp.parseFrom(input);
        System.out.println(resp_);
    }
}
