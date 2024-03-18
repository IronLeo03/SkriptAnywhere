package me.ironleo03.skriptanywhere.network;

import lombok.Getter;
import me.ironleo03.skriptanywhere.network.client.AnywhereSocket;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

public class TickableNetworkManager {
    @Getter
    private final Selector selector;

    public TickableNetworkManager() throws IOException {
        selector = Selector.open();
    }

    /**
     * Delegate ticking to the server that creates an instance of TickableNetworkManager.
     *
     * @throws IOException
     */
    public void tick() throws IOException {
        selector.selectNow();
        Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
        while (keys.hasNext()) {
            SelectionKey key = keys.next();
            keys.remove();

            // key could be invalid if for example, the client closed the connection.
            if (!key.isValid()) {
                continue;
            }
            /*
             * Sever can accept a connection
             */
            if (key.isAcceptable()) {
                System.out.println("Accepting connection");
            }
            /*
             * Channel is available for writing
             */
            if (key.isWritable()) {
                System.out.println("Writing...");
            }
            /*
             * Channel is available for reading
             */
            if (key.isReadable()) {
                AnywhereSocket anywhereSocket = (AnywhereSocket) key.attachment();
                anywhereSocket.callbackRead(key);
            }
            /*
             * SocketChannel connected
             */
            if (key.isConnectable()) {
                AnywhereSocket anywhereSocket = (AnywhereSocket) key.attachment();
                try {
                    anywhereSocket.callbackConnect(key);
                    //todo fire event
                } catch (IOException exception) {
                    key.cancel();
                    anywhereSocket.getSocketChannel().close();
                    //todo fire event
                }
            }
        }
    }
}