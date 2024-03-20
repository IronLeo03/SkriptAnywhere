package me.ironleo03.skriptanywhere;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import me.ironleo03.skriptanywhere.elements.ClassInfos;
import me.ironleo03.skriptanywhere.network.TickableNetworkManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class SkriptAnywhere extends JavaPlugin {
    private TickableNetworkManager tickableNetworkManager;
    private SkriptAddon skriptAddon;

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        getLogger().info("Starting SkriptAnywhere");
        getLogger().info("Now starting TickableNetworkManager");
        try {
            tickableNetworkManager = new TickableNetworkManager();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        getLogger().info("Now registering as a Skript addon");
        try {
            skriptAddon = Skript.registerAddon(this);
            skriptAddon.loadClasses("me.ironleo03.skriptanywhere.elements", "expressions", "effects", "events");
            new ClassInfos();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        getLogger().info("SkriptAnywhere has finished starting");
    }
}