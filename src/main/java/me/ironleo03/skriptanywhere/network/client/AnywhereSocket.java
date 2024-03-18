package me.ironleo03.skriptanywhere.network.client;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class AnywhereSocket {
    private InetSocketAddress address;
    /**
     * SocketChannel if the socket is connected or null otherwise
     */
    @Getter
    @Setter(AccessLevel.PROTECTED)
    private SocketChannel socketChannel;

    public AnywhereSocket(InetSocketAddress address) {
        this.address = address;
    }

    /**
     * Create instance of SocketChannel if it doesn't already exist
     * Register new instance of SocketChannel to selector.
     * Expect tickable manager to use my callbacks on IO events
     * If instance of SocketChannel already exists, do nothing and return false
     *
     * @param selector Register SocketChannel to this selector
     * @return True if a new instance of SocketChannel was created
     * @throws IOException On SocketChannel's exceptions.
     */
    public boolean connectRegister(Selector selector) throws IOException {
        if (socketChannel != null)
            return false;
        socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_CONNECT).attach(this);
        socketChannel.connect(address);
        return true;
    }

    /**
     * Callback for OP_CONNECT interest.
     * Finish connecting socket.
     * Cancel selection key if fails.
     * @param selectionKey key of selector throwing the callback
     * @return True
     */
    public boolean callbackConnect(SelectionKey selectionKey) {
        try {
            /**
             * finishConnect should throw an exception if the socket failed to connect.
             * This assert should then always be true if no exception is thrown by NIO.
             */
            assert (socketChannel.finishConnect());
            selectionKey.interestOps(SelectionKey.OP_READ);
            //todo fire event
            return true;
        } catch (Exception e) {
            //todo handle
            selectionKey.cancel();
            try {
                socketChannel.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            return false;
        }
    }
}
