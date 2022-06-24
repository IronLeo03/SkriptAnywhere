package me.ironleo03.skriptanywhere.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.ironleo03.skriptanywhere.internal.sockets.server.AnywhereServerSocket;
import me.ironleo03.skriptanywhere.spigot.events.ServerAcceptsConnection;
import org.bukkit.event.Event;

public class ExprTheServer extends SimpleExpression<AnywhereServerSocket> {
    static {
        Skript.registerExpression(ExprTheServer.class, AnywhereServerSocket.class, ExpressionType.SIMPLE, "[the] server");
    }
    @Override
    protected AnywhereServerSocket[] get(Event e) {
        if (e instanceof ServerAcceptsConnection) {
            return new AnywhereServerSocket[] {((ServerAcceptsConnection) e).getServerSocket()};
        } else {
            return new AnywhereServerSocket[0];
        }
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
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return getParser().isCurrentEvent(ServerAcceptsConnection.class);
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "The server";
    }
}
