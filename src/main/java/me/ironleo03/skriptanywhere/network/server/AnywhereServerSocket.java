package me.ironleo03.skriptanywhere.network.server;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import me.ironleo03.skriptanywhere.network.client.AnywhereSocket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

@Getter
public class AnywhereServerSocket {
    private InetSocketAddress address;
    /**
     * ServerSocketChannel if the server is accepting or null otherwise
     */
    @Getter
    @Setter(AccessLevel.PROTECTED)
    private ServerSocketChannel serverSocketChannel;

    public AnywhereServerSocket(String host, int port) throws IOException {
        address = new InetSocketAddress(InetAddress.getByName(host),port);
    }
    public AnywhereServerSocket(InetSocketAddress address) {
        this.address = address;
    }

    /**
     * Create instance of ServerSocketChannel if it doesn't already exist
     * Register new instance of ServerSocketChannel to selector.
     * Expect tickable manager to use my callbacks on IO events
     * If instance of ServerSocketChannel already exists, do nothing and return false
     *
     * @param selector Register ServerSocketChannel to this selector
     * @return True if a new instance of ServerSocketChannel was created
     * @throws IOException On ServerSocketChannel's exceptions.
     */
    public boolean listenRegister(Selector selector) throws IOException {
        if (serverSocketChannel != null)
            return false;
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(address);
        //todo store this instead of asking it as an argument
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT).attach(this);
        return true;
    }

    public AnywhereSocket acceptRegister(SelectionKey selectionKey) throws IOException {
        SocketChannel client = serverSocketChannel.accept();
        client.configureBlocking(false);
        return new AnywhereSocket(client,selectionKey.selector());
    }

    /**
     * Attempts to close server socket
     * If selectionKey is not null, attempt to cancel selection key
     * If another selection key is passed, throw runtime exception
     *
     * @param selectionKey Server's selection key
     * @throws IOException
     */
    public void stop(SelectionKey selectionKey) throws IOException {
        serverSocketChannel.close();
        if (selectionKey != null) {
            if (selectionKey.attachment() == this) {
                selectionKey.cancel();
            } else {
                throw new RuntimeException("Wrong selection key passed");
            }
        }
    }
}
