package me.ironleo03.skriptanywhere.elements.expressions;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.ironleo03.skriptanywhere.internal.sockets.server.AnywhereServerSocket;

public class ExprServerGetPort extends SimplePropertyExpression<AnywhereServerSocket, Number> {
    static {
        register(ExprServerGetPort.class, Number.class, "port", "anywhereserversocket");
    }

    @Override
    protected String getPropertyName() {
        return "port";
    }

    @Override
    public Number convert(AnywhereServerSocket anywhereServerSocket) {
        return anywhereServerSocket.getPort();
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }
}
