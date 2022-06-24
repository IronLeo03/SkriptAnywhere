package me.ironleo03.skriptanywhere.elements.expressions;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.ironleo03.skriptanywhere.internal.sockets.client.AnywhereSocket;
import me.ironleo03.skriptanywhere.internal.sockets.server.AnywhereServerSocket;

public class ExprClientGetFilter extends SimplePropertyExpression<AnywhereSocket, String> {
    static {
        register(ExprClientGetFilter.class, String.class, "filter", "anywhereserversocket");
    }

    @Override
    protected String getPropertyName() {
        return "filter";
    }

    @Override
    public String convert(AnywhereSocket anywhereSocket) {
        return anywhereSocket.getFilter();
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}
