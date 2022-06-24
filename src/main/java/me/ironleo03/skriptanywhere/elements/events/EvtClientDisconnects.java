package me.ironleo03.skriptanywhere.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import me.ironleo03.skriptanywhere.internal.sockets.client.AnywhereSocket;
import me.ironleo03.skriptanywhere.spigot.events.ClientConnected;
import me.ironleo03.skriptanywhere.spigot.events.ClientDisconnected;
import org.bukkit.event.Event;

public class EvtClientDisconnects extends SkriptEvent {
    static {
        Skript.registerEvent("ClientDisconnects", EvtClientDisconnects.class, ClientDisconnected.class, "client disconnects");
        EventValues.registerEventValue(ClientDisconnected.class, AnywhereSocket.class, new Getter<AnywhereSocket, ClientDisconnected>() {
            @Override
            public AnywhereSocket get(ClientDisconnected arg) {
                return arg.getSocket();
            }
        },0);
    }

    @Override
    public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public boolean check(Event e) {
        return true;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "Client disconnects!";
    }
}
