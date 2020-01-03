package io.information.netty.socket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * Websocket
 *
 * @author LX
 * @date 2019-11-25
 */
public class WebsocketServer {
    protected Logger LOG = LoggerFactory.getLogger(getClass());

    private int port;

    public WebsocketServer(int port) {
        this.port = port;
    }


    public void run() throws Exception {

        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // (3)
                    .childHandler(new WebsocketServerInitializer())  //(4)
                    .option(ChannelOption.SO_BACKLOG, 1024)          // (5)
                    .localAddress(new InetSocketAddress(port))
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)
            LOG.info("---------------WebsocketChatServer 启动了---------------" + port);
            // 绑定端口，开始接收进来的连接
            ChannelFuture f = b.bind(port)/*.sync()*/; // (7)
//             f.channel().closeFuture().sync();
        } catch (Exception e) {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            LOG.info("---------------WebsocketChatServer--------------- 关闭了" + e.getMessage());
        }
    }

}