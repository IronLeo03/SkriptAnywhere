package me.ironleo03.skriptanywhere;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import lombok.Getter;
import me.ironleo03.skriptanywhere.elements.ClassInfos;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class SkriptAnywhere extends JavaPlugin {
    @Getter
    private static SkriptAnywhere instance;
    private SkriptAddon skriptAddon;


    @Override
    public void onEnable() {
        instance = this;
        try {
            skriptAddon = Skript.registerAddon(this);
            skriptAddon.loadClasses("me.ironleo03.skriptanywhere.elements", "expressions", "effects", "events");
            new ClassInfos();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        //Handled by skript
    }
}
