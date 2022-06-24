package me.ironleo03.skriptanywhere.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.ironleo03.skriptanywhere.internal.sockets.client.AnywhereSocket;
import me.ironleo03.skriptanywhere.internal.sockets.server.AnywhereServerSocket;
import org.bukkit.event.Event;

public class EffClientConnect extends Effect {
    static {
        Skript.registerEffect(EffClientConnect.class, "[make] %anywheresocket% connect[s]");
    }

    private Expression<AnywhereSocket> client;

    @Override
    protected void execute(Event e) {
        AnywhereSocket socket = client.getSingle(e);
        socket.startThread();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "Start connecting using socket";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.client = (Expression<AnywhereSocket>) exprs[0];
        return true;
    }
}
