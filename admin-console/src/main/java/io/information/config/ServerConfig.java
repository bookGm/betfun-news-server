package io.information.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class ServerConfig implements ApplicationListener<WebServerInitializedEvent> {
    private int serverPort;
    @Value("${server.servlet.context-path}")
    String contextPath;

    public String getUrl() {
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
//        return "http://"+address.getHostAddress() +":"+this.serverPort+contextPath;
//        return "http://192.168.1.6:" + this.serverPort + contextPath;
        return "http://guansuo.info" + contextPath;
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        this.serverPort = event.getWebServer().getPort();
    }

}

