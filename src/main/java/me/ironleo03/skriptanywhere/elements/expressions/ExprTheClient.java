package me.ironleo03.skriptanywhere.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.ironleo03.skriptanywhere.events.AnywhereClientConnectsEvent;
import me.ironleo03.skriptanywhere.events.AnywhereClientReceivesDataEvent;
import me.ironleo03.skriptanywhere.events.AnywhereServerAcceptsConnectionEvent;
import me.ironleo03.skriptanywhere.network.client.AnywhereSocket;
import me.ironleo03.skriptanywhere.network.server.AnywhereServerSocket;
import org.bukkit.event.Event;

/**
 * Let user get {@link AnywhereSocket} from
 * - {@link me.ironleo03.skriptanywhere.elements.events.EvtServerAcceptsConnection}
 * - {@link me.ironleo03.skriptanywhere.elements.events.EvtClientConnects}
 * - {@link me.ironleo03.skriptanywhere.elements.events.EvtClientReceivesData}
 */
public class ExprTheClient extends SimpleExpression<AnywhereSocket> {
    static {
        Skript.registerExpression(ExprTheClient.class, AnywhereSocket.class, ExpressionType.SIMPLE, "[the] client");
    }
    @Override
    protected AnywhereSocket[] get(Event e) {
        if (e instanceof AnywhereServerAcceptsConnectionEvent) {
            return new AnywhereSocket[]{((AnywhereServerAcceptsConnectionEvent) e).getSocket()};
        } else if (e instanceof AnywhereClientReceivesDataEvent) {
            return new AnywhereSocket[]{((AnywhereClientReceivesDataEvent) e).getSocket()};
        } else if (e instanceof AnywhereClientConnectsEvent) {
            return new AnywhereSocket[]{((AnywhereClientConnectsEvent) e).getSocket()};
        } else {
            return new AnywhereSocket[0];
        }
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
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return getParser().isCurrentEvent(AnywhereServerAcceptsConnectionEvent.class, AnywhereClientReceivesDataEvent.class, AnywhereClientConnectsEvent.class);
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "The client";
    }
}
