package com.example;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class ServerSocketChannelMain {
    private static final int PORT = 8080;
    private static final int BUF_SIZE = 1000;

    public static void main(String[] args) {
        Selector selector;
        ServerSocketChannel serverChannel;
        try {
            selector = Selector.open();
            serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);
            serverChannel.socket().bind(new InetSocketAddress(PORT));
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (selector.select() > 0) {
                Set<SelectionKey> keys = selector.selectedKeys();
                for (Iterator<SelectionKey> it = keys.iterator(); it.hasNext(); ) {
                    SelectionKey key = it.next();
                    it.remove();

                    if (key.isAcceptable()) {
                        accept(selector, (ServerSocketChannel) key.channel());
                    } else if (key.isReadable()) {
                        read((SocketChannel) key.channel());
                    }
                }
            }

            if (serverChannel.isOpen()) {
                serverChannel.close();
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static void accept(Selector selector, ServerSocketChannel serverSocketChannel) {
        try {
            SocketChannel channel = serverSocketChannel.accept();
            String remoteAddress = channel.socket()
                                          .getRemoteSocketAddress()
                                          .toString();
            System.out.println("accept: " + remoteAddress);

            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void read(SocketChannel channel) {
        ByteBuffer buf = ByteBuffer.allocate(BUF_SIZE);
        String remoteAddress = channel.socket()
                                      .getRemoteSocketAddress()
                                      .toString();
        try {
            if (channel.read(buf) < 0) {
                channel.close();
                return;
            }

            buf.flip();
            System.out.println("remote address: " + remoteAddress);
            System.out.print(StandardCharsets.UTF_8.decode(buf));
            buf.flip();

            write(channel);

            channel.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void write(SocketChannel channel) {
        ByteBuffer buf = ByteBuffer.allocate(BUF_SIZE);
        String response = "HTTP/1.0 200 OK\n"
                          + "Content-Type: text/html\n"
                          + '\n'
                          + "<H1>Hello!</H1>";
        buf.put(response.getBytes());
        buf.flip();

        try {
            channel.write(buf);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
