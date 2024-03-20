package me.ironleo03.skriptanywhere.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.ironleo03.skriptanywhere.SkriptAnywhere;
import me.ironleo03.skriptanywhere.network.server.AnywhereServerSocket;
import org.bukkit.event.Event;

import java.io.IOException;
import java.nio.channels.Selector;

/**
 * Skript effect for calling the {@link AnywhereServerSocket#listenRegister(Selector)} method of an existing AnywhereServerSocket
 * Read {@link me.ironleo03.skriptanywhere.elements.expressions.ExprCreateServerInstance} for creating instances of AnywhereServerSocket
 */
public class EffServerStartAccept extends Effect {
    static {
        Skript.registerEffect(EffServerStartAccept.class, "(accept|listen) [on] %anywhereserversocket%");
    }

    private Expression<AnywhereServerSocket> server;

    @Override
    protected void execute(Event e) {
        AnywhereServerSocket anywhereServerSockets = server.getSingle(e);
        try {
            anywhereServerSockets.listenRegister(SkriptAnywhere.getInstance().getTickableNetworkManager().getSelector());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
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
