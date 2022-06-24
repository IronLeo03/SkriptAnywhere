package me.ironleo03.skriptanywhere.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.ironleo03.skriptanywhere.internal.sockets.client.AnywhereSocket;
import me.ironleo03.skriptanywhere.internal.sockets.server.AnywhereServerSocket;
import org.bukkit.event.Event;

public class ExprCreateClient extends SimpleExpression<AnywhereSocket> {

    static {
        Skript.registerExpression(
                ExprCreateClient.class,
                AnywhereSocket.class,
                ExpressionType.COMBINED,
                "[a] [new] client [connected] [to] %string% [on] [port] %integer%");
    }

    private Expression<String> host;
    private Expression<Number> port;

    @Override
    protected AnywhereSocket[] get(Event e) {
        return new AnywhereSocket[] {
                new AnywhereSocket((Integer) port.getSingle(e), host.getSingle(e))
        };
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends AnywhereSocket> getReturnType() {
        return AnywhereSocket.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "Server listening on port "+port.toString(e,debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        host = (Expression<String>) exprs[0];
        port = (Expression<Number>) exprs[1];
        return true;
    }
}
