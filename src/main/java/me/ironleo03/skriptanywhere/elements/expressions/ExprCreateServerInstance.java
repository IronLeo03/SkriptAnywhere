package me.ironleo03.skriptanywhere.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.ironleo03.skriptanywhere.network.server.AnywhereServerSocket;
import org.bukkit.event.Event;

import java.net.InetSocketAddress;

/**
 * Skript expression for creating a new instance of {@link AnywhereServerSocket}
 */
public class ExprCreateServerInstance extends SimpleExpression<AnywhereServerSocket> {
    static {
        Skript.registerExpression(
                ExprCreateServerInstance.class,
                AnywhereServerSocket.class,
                ExpressionType.COMBINED,
                "[a] [new] server listening on port %integer%");
    }

    private Expression<Number> port;

    @Override
    protected AnywhereServerSocket[] get(Event e) {
        return new AnywhereServerSocket[] {
                new AnywhereServerSocket(new InetSocketAddress((Integer) port.getSingle(e)))
        };
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends AnywhereServerSocket> getReturnType() {
        return AnywhereServerSocket.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "Server listening on port "+port.toString(e,debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        port = (Expression<Number>) exprs[0];
        return true;
    }
}
