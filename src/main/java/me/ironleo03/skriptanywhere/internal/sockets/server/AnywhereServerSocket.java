package me.ironleo03.skriptanywhere.internal.sockets.server;

import lombok.Getter;
import me.ironleo03.skriptanywhere.SkriptAnywhere;
import me.ironleo03.skriptanywhere.internal.sockets.client.AnywhereSocket;
import me.ironleo03.skriptanywhere.spigot.events.ServerAcceptsConnection;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.io.IOException;
import java.nio.channels.SelectionKey;

public class AnywhereServerSocket implements ServerHandler {
    @Getter
    private int port;
    @Getter
    private String error;
    private BukkitTask task;
    private ServerAcceptRunnable runnable;

    public AnywhereServerSocket(int port) {
        this.port = port;
    }

    public void startThread() {
        runnable = new ServerAcceptRunnable(this, port);
        task = Bukkit.getScheduler().runTaskAsynchronously(SkriptAnywhere.getInstance(),runnable);
    }

    @Override
    public void connect(SelectionKey selectionKey) throws IOException {
        AnywhereSocket anywhereSocket = (AnywhereSocket) selectionKey.attachment();
        Bukkit.getServer().getPluginManager().callEvent(new ServerAcceptsConnection(this, (AnywhereSocket) selectionKey.attachment()));
        anywhereSocket.connect(selectionKey);
    }

    @Override
    public void read(SelectionKey selectionKey) throws IOException {
        AnywhereSocket anywhereSocket = (AnywhereSocket) selectionKey.attachment();
        anywhereSocket.read(selectionKey);
    }

    @Override
    public void write(SelectionKey selectionKey) throws IOException {
        AnywhereSocket anywhereSocket = (AnywhereSocket) selectionKey.attachment();
        anywhereSocket.write(selectionKey);

    }

    @Override
    public void logError(String string) {
        error+=string;
    }

    public void stop() {
        task.cancel();
        runnable.close();
    }
}
