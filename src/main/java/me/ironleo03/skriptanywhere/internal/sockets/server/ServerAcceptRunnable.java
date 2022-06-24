package me.ironleo03.skriptanywhere.internal.sockets.server;

import lombok.Getter;
import me.ironleo03.skriptanywhere.internal.sockets.client.AnywhereSocket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class ServerAcceptRunnable implements Runnable{
    private ServerHandler handler;
    private int port;
    @Getter
    private boolean running;
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;

    public ServerAcceptRunnable(ServerHandler handler, int port) {
        this.handler = handler;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            running = true;
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress((InetAddress) null,port));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            SelectionKey key= null;
            while (running) {
                if (selector.selectNow() <= 0) {
                    continue;
                }
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectedKeys.iterator();
                while (iterator.hasNext()) {
                    key = iterator.next();

                    if (key.isAcceptable()) {
                        SocketChannel sc = serverSocketChannel.accept();
                        sc.configureBlocking(false);
                        if (sc.finishConnect()) {
                            sc.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                            SelectionKey clientKey = sc.keyFor(selector);
                            AnywhereSocket anywhereSocket = new AnywhereSocket();
                            clientKey.attach(anywhereSocket);
                            handler.connect(clientKey);
                        }
                    }
                    else if (key.isReadable()) {
                        handler.read(key);
                    }
                    else if (key.isWritable()) {
                        handler.write(key);
                    }
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            handler.logError(e.getMessage());
            running = false;
            throw new RuntimeException(e);
        } catch (ClosedSelectorException e) {
            //Not my problem
        }
    }

    public void close() {
        running = false;
        try {
            serverSocketChannel.close();
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
