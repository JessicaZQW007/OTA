package com.yhxc.Netty;

import com.yhxc.Netty.Contorller.sendAirController;
import com.yhxc.Netty.nettyServer.EchoServer;
import org.apache.http.HttpRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

/**
 * @Author: 张权威
 * @Date: 2019/6/4 16:59
 */
@Component
@Order(value = 1)
public class Test implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        EchoServer server = new EchoServer();
        server.start();
    }
}

