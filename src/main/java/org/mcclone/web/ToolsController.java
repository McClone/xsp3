package org.mcclone.web;

import com.google.common.base.Joiner;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.mcclone.context.PropertyContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by mcclone on 17-6-24.
 */
@RequestMapping("/tools")
@Controller
public class ToolsController {

    private static Logger logger = LoggerFactory.getLogger(ToolsController.class);

    @Autowired
    private PropertyContextHolder propertyContextHolder;

    @Autowired
    private ZooKeeper zooKeeper;

    @Value("${zk.service.register}")
    private String serviceRootPath;

    @RequestMapping("/properties/reload")
    @ResponseBody
    public String reloadProperties() {
        propertyContextHolder.reload();
        return PropertyContextHolder.getProperty("application.name");
    }

    @RequestMapping("/services")
    @ResponseBody
    public String getAllService() {
        try {
            List<String> childrens = zooKeeper.getChildren(serviceRootPath, false);
            return Joiner.on(",").skipNulls().join(childrens);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return "";
    }

}
