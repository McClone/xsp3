package org.mcclone.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * @author McClone
 */
@WebService(targetNamespace = "http://example/", serviceName = "helloWorldService", portName = "helloWorldPort")
public interface HelloWorld {

    @WebMethod
    String sayHelloWorldFrom(String from);

}
