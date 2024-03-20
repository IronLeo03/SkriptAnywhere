package me.ironleo03.skriptanywhere;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import lombok.Getter;
import me.ironleo03.skriptanywhere.elements.ClassInfos;
import me.ironleo03.skriptanywhere.network.TickableNetworkManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class SkriptAnywhere extends JavaPlugin {
    /**
     * Instance of SkriptAnywhere
     * This singleton is required to access the plugin instance from the classes for effects, events and expressions (thank you Skript).
     */
    @Getter
    private static SkriptAnywhere instance;
    private TickableNetworkManager tickableNetworkManager;
    private SkriptAddon skriptAddon;

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        getLogger().info("Starting SkriptAnywhere");
        instance = this;
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