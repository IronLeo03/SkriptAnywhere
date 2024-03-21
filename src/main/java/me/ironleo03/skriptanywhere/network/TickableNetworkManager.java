package me.ironleo03.skriptanywhere.network;

import lombok.Getter;
import me.ironleo03.skriptanywhere.network.client.AnywhereSocket;
import me.ironleo03.skriptanywhere.network.server.AnywhereServerSocket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class TickableNetworkManager {
    @Getter
    private final Selector selector;
    /**
     * Fire when AnywhereSocket receives a connection
     */
    @Getter
    private BiConsumer<AnywhereServerSocket, AnywhereSocket> fireServerAcceptsConnectionEvent;
    @Getter
    private BiConsumer<AnywhereSocket, ByteBuffer> fireClientReceivesDataEvent;
    private Consumer<AnywhereSocket> fireClientConnectsEvent;

    /**
     * Tickable Network Manager tries to be platform independent.
     * Implementations of how to properly fire events is needed.
     * Events are only fired if the instances are registered to {@link #getSelector()}.
     *
     * @param fireServerAcceptsConnectionEvent Called when {@link AnywhereServerSocket#acceptRegister(SelectionKey)} is called (a new client has connected)
     * @param fireClientReceivesDataEvent Called when {@link AnywhereSocket#callbackRead(SelectionKey)} is called (client has received data contained in a byte buffer)
     * @param fireClientConnectsEvent Called when {@link AnywhereSocket#callbackConnect(SelectionKey)} is called (client finishes connecting)
     * @throws IOException
     */
    public TickableNetworkManager(BiConsumer<AnywhereServerSocket, AnywhereSocket> fireServerAcceptsConnectionEvent, BiConsumer<AnywhereSocket, ByteBuffer> fireClientReceivesDataEvent, Consumer<AnywhereSocket> fireClientConnectsEvent) throws IOException {
        selector = Selector.open();
        this.fireServerAcceptsConnectionEvent = fireServerAcceptsConnectionEvent;
        this.fireClientReceivesDataEvent = fireClientReceivesDataEvent;
        this.fireClientConnectsEvent = fireClientConnectsEvent;
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
                AnywhereServerSocket anywhereServerSocket = (AnywhereServerSocket) key.attachment();
                try {
                    AnywhereSocket anywhereSocket = anywhereServerSocket.acceptRegister(key);
                    fireServerAcceptsConnectionEvent.accept(anywhereServerSocket,anywhereSocket);
                } catch (IOException e) {
                    //todo fire event
                }
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
                //todo keep-alive
                AnywhereSocket anywhereSocket = (AnywhereSocket) key.attachment();
                try {
                    ByteBuffer byteBuffer = anywhereSocket.callbackRead(key);
                    if (byteBuffer == null) {
                        //todo fire event
                        //todo make a method for disconnection
                        key.cancel();
                        anywhereSocket.getSocketChannel().close();
                        continue;
                    } else {
                        fireClientReceivesDataEvent.accept(anywhereSocket,byteBuffer);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    //todo fire event
                }
            }
            /*
             * SocketChannel connected
             */
            if (key.isConnectable()) {
                AnywhereSocket anywhereSocket = (AnywhereSocket) key.attachment();
                try {
                    anywhereSocket.callbackConnect(key);
                    fireClientConnectsEvent.accept(anywhereSocket);
                } catch (IOException exception) {
                    //todo make a method for disconnection
                    key.cancel();
                    anywhereSocket.getSocketChannel().close();
                    //todo fire event
                }
            }
        }
    }
}