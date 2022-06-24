package me.ironleo03.skriptanywhere.elements.expressions;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.ironleo03.skriptanywhere.internal.sockets.client.AnywhereSocket;

public class ExprClientIsActive extends SimplePropertyExpression<AnywhereSocket, Boolean> {
    static {
        register(ExprClientIsActive.class, Boolean.class, "active", "anywheresocket");
    }

    @Override
    protected String getPropertyName() {
        return "message";
    }

    @Override
    public Boolean convert(AnywhereSocket anywhereSocket) {
        return anywhereSocket.isRunning();
    }

    @Override
    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }
}
