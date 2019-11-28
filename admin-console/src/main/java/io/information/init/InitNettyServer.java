package io.information.init;

import io.information.netty.socket.WebsocketServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class InitNettyServer implements CommandLineRunner {
    @Value("${netty.socket.port}")
    public Integer port;

    @Override
    public void run(String... args) throws Exception {
        new WebsocketServer(port).run();
    }
}
