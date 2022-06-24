package me.ironleo03.skriptanywhere.elements.expressions;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.ironleo03.skriptanywhere.internal.sockets.server.AnywhereServerSocket;

public class ExprServerGetError extends SimplePropertyExpression<AnywhereServerSocket, String> {
    //TODO reset
    static {
        register(ExprServerGetError.class, String.class, "error", "anywhereserversocket");
    }

    @Override
    protected String getPropertyName() {
        return "error";
    }

    @Override
    public String convert(AnywhereServerSocket anywhereServerSocket) {
        return anywhereServerSocket.getError();
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}
