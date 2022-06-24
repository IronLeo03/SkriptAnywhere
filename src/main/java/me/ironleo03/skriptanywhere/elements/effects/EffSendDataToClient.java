package me.ironleo03.skriptanywhere.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.ironleo03.skriptanywhere.internal.sockets.client.AnywhereSocket;
import me.ironleo03.skriptanywhere.internal.sockets.server.AnywhereServerSocket;
import org.bukkit.event.Event;

import java.nio.ByteBuffer;

public class EffSendDataToClient extends Effect {
    static {
        Skript.registerEffect(EffSendDataToClient.class, "send %string% (to [the] socket of|using [[the] client['s socket]]) %anywheresocket%");
    }

    private Expression<String> data;
    private Expression<AnywhereSocket> socket;

    @Override
    protected void execute(Event e) {
        AnywhereSocket anywhereSocket = socket.getSingle(e);
        String text = data.getSingle(e);
        anywhereSocket.queueWrite(text);
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "Send data";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.data = (Expression<String>) exprs[0];
        this.socket = (Expression<AnywhereSocket>) exprs[1];
        return true;
    }
}
