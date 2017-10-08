package org.mcclone.netty;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;

import java.io.File;
import java.net.InetSocketAddress;

/**
 * @author Administrator
 */
public class NettyClient {

    public static void main(String[] args) throws InterruptedException {
        ClientBootstrap bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory());
        bootstrap.setPipeline(Channels.pipeline(new ObjectEncoder()));
        ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress("127.0.0.1", 8000));
        channelFuture.getChannel().write(new File("d:\\report_data2.txt"));
    }

    private static class HelloClientHandler extends SimpleChannelHandler {
        @Override
        public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
            System.out.println("Hello, I'm client.");
            e.getChannel().write("Hello, I'm client.");
        }
    }
}
