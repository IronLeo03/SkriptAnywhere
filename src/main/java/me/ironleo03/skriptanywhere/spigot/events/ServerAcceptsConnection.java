package me.ironleo03.skriptanywhere.spigot.events;

import lombok.Getter;
import me.ironleo03.skriptanywhere.internal.sockets.client.AnywhereSocket;
import me.ironleo03.skriptanywhere.internal.sockets.server.AnywhereServerSocket;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ServerAcceptsConnection extends Event {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Getter
    private AnywhereServerSocket serverSocket;
    @Getter
    private AnywhereSocket socket;

    public ServerAcceptsConnection(AnywhereServerSocket serverSocket, AnywhereSocket socket) {
        this.serverSocket = serverSocket;
        this.socket = socket;
    }
}
