package me.ironleo03.skriptanywhere.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.ironleo03.skriptanywhere.events.AnywhereServerAcceptsConnectionEvent;
import me.ironleo03.skriptanywhere.network.server.AnywhereServerSocket;
import org.bukkit.event.Event;

/**
 * Let user get {@link AnywhereServerSocket} from {@link me.ironleo03.skriptanywhere.elements.events.EvtServerAcceptsConnection}
 */
public class ExprTheServer extends SimpleExpression<AnywhereServerSocket> {
    static {
        Skript.registerExpression(ExprTheServer.class, AnywhereServerSocket.class, ExpressionType.SIMPLE, "[the] server");
    }
    @Override
    protected AnywhereServerSocket[] get(Event e) {
        if (e instanceof AnywhereServerAcceptsConnectionEvent) {
            return new AnywhereServerSocket[] {((AnywhereServerAcceptsConnectionEvent) e).getServerSocket()};
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
        return getParser().isCurrentEvent(AnywhereServerAcceptsConnectionEvent.class);
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "The server";
    }
}
