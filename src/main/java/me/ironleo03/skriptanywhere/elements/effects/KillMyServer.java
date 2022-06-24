package me.ironleo03.skriptanywhere.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.ironleo03.skriptanywhere.internal.sockets.client.AnywhereSocket;
import org.bukkit.event.Event;

//I can't remember why this was here in the first place but here it is
public class KillMyServer extends Effect {
    static {
//        Skript.registerEffect(KillMyServer.class, "[Please] kill my server for good");
    }

    @Override
    protected void execute(Event e) {
        while (true) ;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "The server is about to die, good luck";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }
}
