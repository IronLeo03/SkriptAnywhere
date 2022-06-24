package me.ironleo03.skriptanywhere.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.ironleo03.skriptanywhere.internal.sockets.client.AnywhereSocket;
import org.bukkit.event.Event;

public class EffClientFilter extends Effect {
    static {
        Skript.registerEffect(EffClientFilter.class, "filter %anywheresocket% by %string%");
    }

    private Expression<AnywhereSocket> client;
    private Expression<String> filter;

    @Override
    protected void execute(Event e) {
        AnywhereSocket socket = client.getSingle(e);
        socket.setFilter(filter.getSingle(e));
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "Filter socket";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.client = (Expression<AnywhereSocket>) exprs[0];
        this.filter = (Expression<String>) exprs[1];
        return true;
    }
}
