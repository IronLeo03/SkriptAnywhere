package me.ironleo03.skriptanywhere.elements.expressions;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.ironleo03.skriptanywhere.network.client.AnywhereSocket;

public class ExprIsClientActive extends SimplePropertyExpression<AnywhereSocket, Boolean> {
    static {
        register(ExprIsClientActive.class, Boolean.class, "active", "anywheresocket");
    }

    @Override
    protected String getPropertyName() {
        return "active";
    }

    @Override
    public Boolean convert(AnywhereSocket anywhereSocket) {
        return anywhereSocket.getSocketChannel().isConnected();
    }

    @Override
    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }
}
