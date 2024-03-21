package me.ironleo03.skriptanywhere.network.client;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
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
     * Create AnywhereSocket with an existing socket channel.
     * Register socketchannel to selector.
     * @param socketChannel SocketChannel to register. Must already be in non-blocking mode.
     * @param selector Register SocketChannel to this selector.
     * @throws ClosedChannelException On Selector exception.
     */
    public AnywhereSocket(SocketChannel socketChannel, Selector selector) throws ClosedChannelException {
        this.socketChannel = socketChannel;
        this.address = (InetSocketAddress) socketChannel.socket().getRemoteSocketAddress();
        socketChannel.register(selector, SelectionKey.OP_READ).attach(this);
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
     * Detect client disconnecting (https://stackoverflow.com/questions/12243765/java-handling-socket-disconnection/12244232#12244232)
     *
     * @param selectionKey key within the selector throwing the callback
     * @return Client's input or null if the client has disconnected
     * @throws IOException
     */
    public ByteBuffer callbackRead(SelectionKey selectionKey) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int received = socketChannel.read(buffer);
        return received!=-1 ? buffer : null;
    }

    public int write(String string) throws IOException {
        return socketChannel.write(ByteBuffer.wrap(string.getBytes()));
    }
}
