package me.ironleo03.skriptanywhere.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import lombok.SneakyThrows;
import me.ironleo03.skriptanywhere.network.client.AnywhereSocket;
import org.bukkit.event.Event;

public class EffSendDataToClient extends Effect {
    static {
        Skript.registerEffect(EffSendDataToClient.class, "send (packet|data|network reply) %string% [to [the] socket of|using [[the] client['s socket]]|to] %anywheresocket%");
    }

    private Expression<String> data;
    private Expression<AnywhereSocket> socket;

    @SneakyThrows
    @Override
    protected void execute(Event e) {
        AnywhereSocket anywhereSocket = socket.getSingle(e);
        String text = data.getSingle(e);
        //todo review
        anywhereSocket.write(text);
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
