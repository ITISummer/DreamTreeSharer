package com.ITIS.DreamTreeSharer.config;

import com.ITIS.DreamTreeSharer.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * WebSocket配置类
 * <p>
 * [Using WebSocket to build an interactive web application](https://spring.io/guides/gs/messaging-stomp-websocket/)
 * [websocket 流程讲解](https://segmentfault.com/a/1190000016309473)
 *
 * @author LCX
 * @since 1.0.0
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsService userDetailsService;


    /**
     * 配置消息代理
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //配置代理域，可以配置多个，配置代理目的地前缀为/queue,可以在配置域上向客户端推送消息
        // 发送消息队列，可在其后配置多个路径，如：/queue/chat, /queue/weather
        registry.enableSimpleBroker("/queue");
    }

    /**
     * 添加这个Endpoint，这样在网页可以通过websocket连接上服务
     * 也就是我们配置websocket的服务地址，并且可以指定是否使用socketJS
     * STOMP是一个在底层WebSocket上运行的子协议。
     * [vue、springboot集成websocket跨域问题解决](https://blog.csdn.net/xiaoxiangzi520/article/details/106628992)
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        /*
         * 1.将ws/ep路径注册为stomp的端点，用户连接了这个端点就可以进行websocket通讯，支持socketJS
         * 2.setAllowedOrigins("*")：允许跨域
         * 3.withSockJS():支持socketJS访问
         */
        // 以下方式不可以
//        registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();
        // 以下具体指明可访问的域名也可以
//        registry.addEndpoint("/ws/ep").setAllowedOrigins("http://localhost:8080").withSockJS();
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
    }


    /**
     * 输入通道参数配置
     * 防止 jwt 拦截器拦截所需配置
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                //判断是否为连接，如果是，需要获取token，并且设置用户对象
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    // Auto-Token 为前端发送的请求头，需与前端一致
                    String token = accessor.getFirstNativeHeader("Auth-Token");
                    if (!StringUtils.isEmpty(token)) {
                        String authToken = token.substring(tokenHead.length());
                        String username = jwtTokenUtil.getUserNameFromToken(authToken);
                        //token中存在用户名
                        if (!StringUtils.isEmpty(username)) {
                            //登录
                            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                            //验证token是否有效，重新设置用户对象
                            if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                                UsernamePasswordAuthenticationToken authenticationToken =
                                        new UsernamePasswordAuthenticationToken(userDetails, null,
                                                userDetails.getAuthorities());
                                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                                accessor.setUser(authenticationToken);
                            }
                        }
                    }
                }
                return message;
            }
        });
    }
}