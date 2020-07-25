package edu.sandau.chat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component("nettyProperties")
@ConfigurationProperties(prefix = "netty")
public class NettyProperties {

    /***
     * netty 绑定端口
     */
    private Integer port = 8088;

    /***
     * websocket 路径
     */
    private String websocketPath = "ws";
}
