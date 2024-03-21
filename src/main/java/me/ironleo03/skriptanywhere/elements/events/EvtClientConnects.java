package me.ironleo03.skriptanywhere.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import me.ironleo03.skriptanywhere.events.AnywhereClientConnectsEvent;
import me.ironleo03.skriptanywhere.network.client.AnywhereSocket;
import org.bukkit.event.Event;

/**
 * Relays {@link AnywhereClientConnectsEvent} to Skript.
 * Thank you Skript API.
 */
public class EvtClientConnects extends SkriptEvent {
    static {
        Skript.registerEvent("ClientConnects", EvtClientConnects.class, AnywhereClientConnectsEvent.class, "client connects");
        EventValues.registerEventValue(AnywhereClientConnectsEvent.class, AnywhereSocket.class, new Getter<AnywhereSocket, AnywhereClientConnectsEvent>() {
            @Override
            public AnywhereSocket get(AnywhereClientConnectsEvent arg) {
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
        return "Client connects!";
    }
}
