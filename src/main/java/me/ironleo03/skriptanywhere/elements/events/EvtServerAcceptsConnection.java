package me.ironleo03.skriptanywhere.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import me.ironleo03.skriptanywhere.events.AnywhereClientConnectsEvent;
import me.ironleo03.skriptanywhere.events.AnywhereServerAcceptsConnectionEvent;
import me.ironleo03.skriptanywhere.network.client.AnywhereSocket;
import me.ironleo03.skriptanywhere.network.server.AnywhereServerSocket;
import org.bukkit.event.Event;

/**
 * Relays {@link AnywhereServerAcceptsConnectionEvent} to Skript.
 * Thank you Skript API.
 */
public class EvtServerAcceptsConnection extends SkriptEvent {
    static {
        Skript.registerEvent("ServerAcceptsClient", EvtServerAcceptsConnection.class, AnywhereServerAcceptsConnectionEvent.class, "(new connection|receive connection) [(on|at|to) [port] %-integer%]");
        EventValues.registerEventValue(AnywhereServerAcceptsConnectionEvent.class, AnywhereServerSocket.class, new Getter<AnywhereServerSocket, AnywhereServerAcceptsConnectionEvent>() {
            @Override
            public AnywhereServerSocket get(AnywhereServerAcceptsConnectionEvent arg) {
                return arg.getServerSocket();
            }
        },0);
        EventValues.registerEventValue(AnywhereServerAcceptsConnectionEvent.class, AnywhereSocket.class, new Getter<AnywhereSocket, AnywhereServerAcceptsConnectionEvent>() {
            @Override
            public AnywhereSocket get(AnywhereServerAcceptsConnectionEvent arg) {
                return arg.getSocket();
            }
        },0);
    }

    private Expression<Integer> server;

    @Override
    public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
        this.server = (Expression<Integer>) args[0];
        return true;
    }

    @Override
    public boolean check(Event e) {
        AnywhereServerAcceptsConnectionEvent serverAcceptsConnection = (AnywhereServerAcceptsConnectionEvent) e;
        if (server != null && server.getSingle(e) != serverAcceptsConnection.getServerSocket().getAddress().getPort())
            return false;
        return true;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "Client connected to server";
    }
}
