package me.ironleo03.skriptanywhere.spigot.events;

import lombok.Getter;
import me.ironleo03.skriptanywhere.internal.sockets.client.AnywhereSocket;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ClientConnected extends Event {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Getter
    private AnywhereSocket socket;

    public ClientConnected(AnywhereSocket socket) {
        this.socket = socket;
    }
}
