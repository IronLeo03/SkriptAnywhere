package me.ironleo03.skriptanywhere.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.ironleo03.skriptanywhere.SkriptAnywhere;
import me.ironleo03.skriptanywhere.network.client.AnywhereSocket;
import org.bukkit.event.Event;

import java.io.IOException;

public class EffConnectClient extends Effect {
    static {
        Skript.registerEffect(EffConnectClient.class, "[make] %anywheresocket% connect[s]");
    }

    private Expression<AnywhereSocket> client;

    @Override
    protected void execute(Event e) {
        AnywhereSocket socket = client.getSingle(e);
        try {
            socket.connectRegister(SkriptAnywhere.getInstance().getTickableNetworkManager().getSelector());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "Start connecting using socket";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.client = (Expression<AnywhereSocket>) exprs[0];
        return true;
    }
}
