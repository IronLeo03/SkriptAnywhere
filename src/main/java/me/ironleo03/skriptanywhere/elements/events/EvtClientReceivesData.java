package me.ironleo03.skriptanywhere.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import me.ironleo03.skriptanywhere.internal.sockets.client.AnywhereSocket;
import me.ironleo03.skriptanywhere.spigot.events.ClientReceivesData;
import org.bukkit.event.Event;

public class EvtClientReceivesData extends SkriptEvent {
    static {
        Skript.registerEvent("ClientReceivesData", EvtClientReceivesData.class, ClientReceivesData.class, "client [[with] filter %-string%] receive[s]");
        EventValues.registerEventValue(ClientReceivesData.class, AnywhereSocket.class, new Getter<AnywhereSocket, ClientReceivesData>() {
            @Override
            public AnywhereSocket get(ClientReceivesData arg) {
                return arg.getSocket();
            }
        },0);
    }

    private Expression<String> client;

    @Override
    public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
        this.client = (Expression<String>) args[0];
        return true;
    }

    @Override
    public boolean check(Event e) {
        ClientReceivesData clientReceivesData = (ClientReceivesData) e;
        return client == null || !client.getSingle(e).equals(clientReceivesData.getSocket().getFilter());
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "Client receives!";
    }
}
