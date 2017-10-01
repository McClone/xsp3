package org.mcclone.web;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.File;

/**
 * Created by Administrator on 2017/10/1.
 */
public class WebApp {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        WebAppContext webAppContext = new WebAppContext();
        File file = new File("src/main/webapp");
        webAppContext.setContextPath("/");
        webAppContext.setWar(file.getAbsolutePath());
        Configuration.ClassList classlist = Configuration.ClassList
                .setServerDefault(server);
        classlist.addBefore(
                "org.eclipse.jetty.webapp.JettyWebXmlConfiguration",
                "org.eclipse.jetty.annotations.AnnotationConfiguration");

        webAppContext.setAttribute(
                "org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",
                ".*/[^/]*servlet-api-[^/]*\\.jar$|.*/javax.servlet.jsp.jstl-.*\\.jar$|.*/[^/]*taglibs.*\\.jar$");
        server.setHandler(webAppContext);
        server.start();
        server.dumpStdErr();
        server.join();
    }
}
