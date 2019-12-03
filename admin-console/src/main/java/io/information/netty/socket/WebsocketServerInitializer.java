package io.information.netty.socket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.CharsetUtil;

/**
 * 服务端 ChannelInitializer
 * 
 * @author LX
 * @date 2019-11-25
 */
public class WebsocketServerInitializer extends
		ChannelInitializer<Channel> {	//1

	@Override
    public void initChannel(Channel ch) throws Exception {//2
		 ChannelPipeline pipeline = ch.pipeline();
//		pipeline.addLast("logging",new LoggingHandler("DEBUG"));
        pipeline.addLast(new HttpServerCodec());
		pipeline.addLast(new HttpObjectAggregator(64*1024));
		pipeline.addLast(new ChunkedWriteHandler());
//		pipeline.addLast(new HttpRequestHandler());
		pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
		pipeline.addLast(new TextWebSocketFrameHandler());

    }
}
