package me.ironleo03.skriptanywhere.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.ironleo03.skriptanywhere.internal.sockets.client.AnywhereSocket;
import me.ironleo03.skriptanywhere.spigot.events.ClientConnected;
import me.ironleo03.skriptanywhere.spigot.events.ClientDisconnected;
import me.ironleo03.skriptanywhere.spigot.events.ClientReceivesData;
import me.ironleo03.skriptanywhere.spigot.events.ServerAcceptsConnection;
import org.bukkit.event.Event;

public class ExprTheClient extends SimpleExpression<AnywhereSocket> {
    static {
        Skript.registerExpression(ExprTheClient.class, AnywhereSocket.class, ExpressionType.SIMPLE, "[the] client");
    }
    @Override
    protected AnywhereSocket[] get(Event e) {
        if (e instanceof ServerAcceptsConnection) {
            return new AnywhereSocket[] {((ServerAcceptsConnection) e).getSocket()};
        } else if (e instanceof ClientReceivesData) {
            return new AnywhereSocket[] {((ClientReceivesData) e).getSocket()};
        } else if (e instanceof ClientConnected) {
            return new AnywhereSocket[] {((ClientConnected) e).getSocket()};
        } else if (e instanceof ClientDisconnected) {
            return new AnywhereSocket[] {((ClientDisconnected) e).getSocket()};
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
        return getParser().isCurrentEvent(ServerAcceptsConnection.class, ClientReceivesData.class, ClientConnected.class, ClientDisconnected.class);
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "The client";
    }
}
