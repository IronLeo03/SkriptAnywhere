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
     * Finish connecting socket and change interests on selector.
     *
     * @throws IOException if connection fails
     * @param selectionKey key of selector throwing the callback
     * @return False is the client was already connected, true otherwise
     */
    public boolean callbackConnect(SelectionKey selectionKey) throws IOException {
        if (socketChannel.isConnected())
            return false;

        if (!socketChannel.finishConnect())
            throw new IOException("connection failed");
        selectionKey.interestOps(SelectionKey.OP_READ);
        return true;
    }

    /**
     * Read from client
     *
     * @param selectionKey key within the selector throwing the callback
     * @return Client's input
     * @throws IOException
     */
    public ByteBuffer callbackRead(SelectionKey selectionKey) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        socketChannel.read(buffer);
        return buffer;
    }
}
