package edu.sandau.chat.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WSServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        // websocket 基于http协议，所以要有 http 编解码器
        pipeline.addLast(new HttpServerCodec());
        // 对写大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());
        // 对httpMessage进行聚合，聚合成fullHttpRequest 或 FullHttpResponse
        // 几乎在netty中的编程，都会使用到handler
        pipeline.addLast(new HttpObjectAggregator(1024 * 64));

        // ==================== 以上是用于支持http协议 ===================

        /**
         * websocket 服务器处理的协议，用于指定给客户端连接访问的路由： /ws
         * 本 handler 会帮你处理一些繁重的复杂的事
         * 会帮你处理捂手动作：handshaking ( close, ping, pong ) ping+pong = 心跳
         * 对 websocket 来讲，都是以 frames 进行传输的，不同的数据类型对于的frames也不同
         */
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        // 自定义的handler
        pipeline.addLast(new ChatHandler());

    }
}
