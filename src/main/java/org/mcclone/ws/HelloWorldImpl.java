package org.mcclone.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * @author McClone
 */
@WebService(targetNamespace = "http://example/", serviceName = "helloWorldService", portName = "helloWorldPort")
public class HelloWorldImpl implements HelloWorld {

    @WebMethod
    public String sayHelloWorldFrom(String from) {
        String result = "Hello, world, from " + from;
        System.out.println(result);
        return result;
    }
}
