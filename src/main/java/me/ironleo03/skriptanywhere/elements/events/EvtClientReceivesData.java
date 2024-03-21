package me.ironleo03.skriptanywhere.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import me.ironleo03.skriptanywhere.events.AnywhereClientReceivesDataEvent;
import me.ironleo03.skriptanywhere.network.client.AnywhereSocket;
import org.bukkit.event.Event;

public class EvtClientReceivesData extends SkriptEvent {
    static {
        Skript.registerEvent("ClientReceivesData", EvtClientReceivesData.class, AnywhereClientReceivesDataEvent.class, "client receive[s]");
        EventValues.registerEventValue(AnywhereClientReceivesDataEvent.class, AnywhereSocket.class, new Getter<AnywhereSocket, AnywhereClientReceivesDataEvent>() {
            @Override
            public AnywhereSocket get(AnywhereClientReceivesDataEvent arg) {
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
        return "Client receives data";
    }
}
