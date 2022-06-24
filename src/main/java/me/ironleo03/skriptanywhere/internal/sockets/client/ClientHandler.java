package me.ironleo03.skriptanywhere.internal.sockets.client;

import java.io.IOException;
import java.nio.channels.SelectionKey;

public interface ClientHandler {
    void connect(SelectionKey channel) throws IOException;
    void read(SelectionKey selectionKey) throws IOException;
    void write(SelectionKey selectionKey) throws IOException;
    void logError(String string);
}
