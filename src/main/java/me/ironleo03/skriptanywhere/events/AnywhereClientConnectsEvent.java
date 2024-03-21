package me.ironleo03.skriptanywhere.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.ironleo03.skriptanywhere.network.client.AnywhereSocket;
import me.ironleo03.skriptanywhere.network.server.AnywhereServerSocket;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
@AllArgsConstructor
public class AnywhereClientConnectsEvent extends Event {
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
}
