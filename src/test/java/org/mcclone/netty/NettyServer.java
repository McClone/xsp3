package org.mcclone.netty;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ClassResolvers;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;

import java.io.File;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * @author Administrator
 */
public class NettyServer {

    public static void main(String[] args) {
        ServerBootstrap bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory());
        bootstrap.setPipeline(Channels.pipeline(
                new ObjectDecoder(ClassResolvers.cacheDisabled(NettyServer.class.getClassLoader())),
                new HelloServerHandler()));
        bootstrap.bind(new InetSocketAddress(8000));
    }

    private static class HelloServerHandler extends SimpleChannelHandler {

        @Override
        public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
            SocketAddress socketAddress = e.getChannel().getRemoteAddress();
            System.out.println("Hello, I'm server." + socketAddress);
        }

        @Override
        public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
            System.out.println("channelClosed");
        }

        @Override
        public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
            Object message = e.getMessage();
            if (message instanceof File) {
                System.out.println(((File) message).getAbsolutePath());
            }
        }
    }
}
