package me.ironleo03.skriptanywhere.elements.expressions;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.ironleo03.skriptanywhere.internal.sockets.client.AnywhereSocket;

public class ExprClientGetCurrentData extends SimplePropertyExpression<AnywhereSocket, String> {
    static {
        register(ExprClientGetCurrentData.class, String.class, "message", "anywheresocket");
    }

    @Override
    protected String getPropertyName() {
        return "message";
    }

    @Override
    public String convert(AnywhereSocket anywhereSocket) {
        return anywhereSocket.getIn();
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}
