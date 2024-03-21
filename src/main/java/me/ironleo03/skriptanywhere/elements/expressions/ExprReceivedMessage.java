package me.ironleo03.skriptanywhere.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.ironleo03.skriptanywhere.events.AnywhereClientReceivesDataEvent;
import me.ironleo03.skriptanywhere.network.client.AnywhereSocket;
import org.bukkit.event.Event;

/**
 * Let user get the data from {@link AnywhereClientReceivesDataEvent#getByteBuffer()} as a string
 */
public class ExprReceivedMessage extends SimpleExpression<String> {
    static {
        Skript.registerExpression(ExprReceivedMessage.class, String.class, ExpressionType.SIMPLE, "[the] [client['s]] data");

    }

    @Override
    protected String[] get(Event event) {
        if (event instanceof AnywhereClientReceivesDataEvent) {
            return new String[] {
                    new String(((AnywhereClientReceivesDataEvent) event).getByteBuffer().array())
            };
        } else {
            return new String[0];
        }
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "Client received data";
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        return getParser().isCurrentEvent(AnywhereClientReceivesDataEvent.class);
    }
}
