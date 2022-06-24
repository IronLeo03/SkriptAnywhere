package me.ironleo03.skriptanywhere.internal.sockets.client;

import lombok.Getter;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class SocketRunnable implements Runnable {
    @Getter
    private boolean running;
    private InetSocketAddress socketAddress;
    private ClientHandler handler;

    private Selector selector;
    private SocketChannel channel;

    public SocketRunnable(InetSocketAddress socketAddress, ClientHandler handler) {
        this.socketAddress = socketAddress;
        this.handler = handler;
    }

    //TODO TEST
    @Override
    public void run() {
        running = true;
        try {
            selector = Selector.open();
            channel = SocketChannel.open();
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_CONNECT |
                    SelectionKey.OP_READ | SelectionKey.OP_WRITE);
            channel.connect(socketAddress);
            SelectionKey key = null;
            Iterator iterator = null;
            while (true) {
                if (selector.select() > 0) {
                    Set readySet = (selector.selectedKeys());
                    iterator = readySet.iterator();
                    while (iterator.hasNext()) {
                        key = (SelectionKey) iterator.next();
                        iterator.remove();
                    }
                    if (key.isConnectable()) {
                        SocketChannel sc = (SocketChannel) key.channel();
                        while (sc.isConnectionPending()) {
                            sc.finishConnect();
                        }
                        handler.connect(key);
                    }
                    if (key.isWritable()) {
                        handler.write(key);
                    }
                    if (key.isReadable()) {
                        handler.read(key);
                    }
                }
            }
        } catch (IOException e) {
            handler.logError(e.getMessage());
            running = false;
            throw new RuntimeException(e);
        }
    }

    public void close() {
        running = false;
        try {
            channel.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            selector.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
