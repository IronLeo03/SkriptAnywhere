package me.ironleo03.skriptanywhere.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import lombok.SneakyThrows;
import me.ironleo03.skriptanywhere.network.client.AnywhereSocket;
import me.ironleo03.skriptanywhere.network.server.AnywhereServerSocket;
import org.bukkit.event.Event;

import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * Skript expression for creating a new instance of {@link me.ironleo03.skriptanywhere.network.client.AnywhereSocket}
 */
public class ExprCreateSocket extends SimpleExpression<AnywhereSocket> {
    static {
        Skript.registerExpression(
                ExprCreateSocket.class,
                AnywhereSocket.class,
                ExpressionType.COMBINED,
                "[a] [new] client [connected] [to] %string% [on] [port] %integer%");
    }

    private Expression<String> host;
    private Expression<Number> port;

    @SneakyThrows
    @Override
    protected AnywhereSocket[] get(Event e) {
        return new AnywhereSocket[] {
                new AnywhereSocket(
                        new InetSocketAddress(InetAddress.getByName(host.getSingle(e)),(Integer) port.getSingle(e)))
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

    //todo
    @Override
    public String toString(Event e, boolean debug) {
        return "Client";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        host = (Expression<String>) exprs[0];
        port = (Expression<Number>) exprs[1];
        return true;
    }
}
