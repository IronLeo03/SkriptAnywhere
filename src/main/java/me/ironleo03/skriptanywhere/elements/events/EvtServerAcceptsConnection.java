package me.ironleo03.skriptanywhere.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import me.ironleo03.skriptanywhere.internal.sockets.client.AnywhereSocket;
import me.ironleo03.skriptanywhere.internal.sockets.server.AnywhereServerSocket;
import me.ironleo03.skriptanywhere.spigot.events.ServerAcceptsConnection;
import org.bukkit.event.Event;

public class EvtServerAcceptsConnection extends SkriptEvent {
    static {
        Skript.registerEvent("ServerAcceptsClient", EvtServerAcceptsConnection.class, ServerAcceptsConnection.class, "(new connection|receive connection) [(on|at|to) [port] %-integer%] [[and] client filter [of] %-string%]");
        EventValues.registerEventValue(ServerAcceptsConnection.class, AnywhereServerSocket.class, new Getter<AnywhereServerSocket, ServerAcceptsConnection>() {
            @Override
            public AnywhereServerSocket get(ServerAcceptsConnection arg) {
                return arg.getServerSocket();
            }
        },0);
        EventValues.registerEventValue(ServerAcceptsConnection.class, AnywhereSocket.class, new Getter<AnywhereSocket, ServerAcceptsConnection>() {
            @Override
            public AnywhereSocket get(ServerAcceptsConnection arg) {
                return arg.getSocket();
            }
        },0);
    }

    private Expression<Integer> server;
    private Expression<String> client;

    @Override
    public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
        this.server = (Expression<Integer>) args[0];
        this.client = (Expression<String>) args[1];
        return true;
    }

    @Override
    public boolean check(Event e) {
        ServerAcceptsConnection serverAcceptsConnection = (ServerAcceptsConnection) e;
        if (server != null && server.getSingle(e) != serverAcceptsConnection.getServerSocket().getPort())
            return false;
        if (client != null && client.getSingle(e).equals(serverAcceptsConnection.getSocket().getFilter()))
            return false;
        return true;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "Client connected to server!";
    }
}
