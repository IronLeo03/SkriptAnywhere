package me.ironleo03.skriptanywhere.internal.sockets.server;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public interface ServerHandler {
    void connect(SelectionKey channel) throws IOException;
    void read(SelectionKey selectionKey) throws IOException;
    void write(SelectionKey selectionKey) throws IOException;
    void logError(String string);
}
