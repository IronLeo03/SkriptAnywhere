package me.ironleo03.skriptanywhere.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.ironleo03.skriptanywhere.internal.sockets.client.AnywhereSocket;
import me.ironleo03.skriptanywhere.internal.sockets.server.AnywhereServerSocket;
import org.bukkit.event.Event;

public class EffClientStop extends Effect {
    static {
        Skript.registerEffect(EffClientStop.class, "stop [the] (connection|socket) %anywheresocket%");
    }

    private Expression<AnywhereSocket> client;

    @Override
    protected void execute(Event e) {
        AnywhereSocket anywhereSocket = client.getSingle(e);
        anywhereSocket.stop();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "Stop accepting on server socket";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.client = (Expression<AnywhereSocket>) exprs[0];
        return true;
    }
}
