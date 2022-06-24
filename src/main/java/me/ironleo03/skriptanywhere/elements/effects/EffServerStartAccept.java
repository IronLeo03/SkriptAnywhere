package me.ironleo03.skriptanywhere.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.ironleo03.skriptanywhere.internal.sockets.server.AnywhereServerSocket;
import org.bukkit.event.Event;

public class EffServerStartAccept extends Effect {
    static {
        Skript.registerEffect(EffServerStartAccept.class, "(accept|listen) [on] %anywhereserversocket%");
    }

    private Expression<AnywhereServerSocket> server;

    @Override
    protected void execute(Event e) {
        AnywhereServerSocket anywhereServerSockets = server.getSingle(e);
        anywhereServerSockets.startThread();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "Start accepting on server socket";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.server = (Expression<AnywhereServerSocket>) exprs[0];
        return true;
    }
}
