package org.mcclone.netty;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ClassResolvers;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;

import java.net.InetSocketAddress;

/**
 * Created by mcclone on 17-6-28.
 */
public class NettyServer {

    public static void main(String[] args) {
        ServerBootstrap bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory());
        bootstrap.setPipeline(Channels.pipeline(
                new ObjectDecoder(ClassResolvers.cacheDisabled(NettyServer.class.getClassLoader())),
                new HelloServerHandler()));
        bootstrap.bind(new InetSocketAddress(8000));
    }

    private static class HelloServerHandler extends
            SimpleChannelHandler {

        @Override
        public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
            System.out.println("Hello, I'm server.");
        }

        @Override
        public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
            System.out.println((String) e.getMessage());
        }
    }
}
