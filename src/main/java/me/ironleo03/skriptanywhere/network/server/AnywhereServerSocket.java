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
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT).attach(this);
        return true;
    }
}
