package me.ironleo03.skriptanywhere.internal.sockets.client;

import lombok.Getter;
import lombok.Setter;
import me.ironleo03.skriptanywhere.SkriptAnywhere;
import me.ironleo03.skriptanywhere.internal.sockets.server.ServerAcceptRunnable;
import me.ironleo03.skriptanywhere.spigot.events.ClientConnected;
import me.ironleo03.skriptanywhere.spigot.events.ClientDisconnected;
import me.ironleo03.skriptanywhere.spigot.events.ClientReceivesData;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class AnywhereSocket implements ClientHandler {
    private int port;
    private String host;

    @Getter
    private String error;
    @Getter
    private boolean running;
    @Getter
    @Setter
    private String filter="";
    @Getter
    private String in;
    @Getter
    private String out;
    private SocketRunnable runnable;
    private BukkitTask task;

    public AnywhereSocket() {
        this.port = -1;
        this.host = "";
        in = "";
        out = "";
    }

    public AnywhereSocket(int port, String host) {
        this.port = port;
        this.host = host;
        in = "";
        out = "";
    }
    @Override
    public void write(SelectionKey key) {
        if (!out.isEmpty()) {
            SocketChannel socketChannel = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.wrap(out.getBytes());
            try {
                socketChannel.write(buffer);
            } catch (IOException e) {
                error += e.getMessage();
                throw new RuntimeException(e);
            }
            out = "";
        }
    }

    public void queueWrite(String string) {
        out += string;
    }

    public void handleDisconnect(SelectionKey selectionKey) {
        running = false;
        selectionKey.cancel();
        Bukkit.getServer().getPluginManager().callEvent(new ClientDisconnected(this));
    }

    @Override
    public void connect(SelectionKey selectionKey) throws IOException {
        Bukkit.getServer().getPluginManager().callEvent(new ClientConnected(this));
    }

    @Override
    public void read(SelectionKey selectionKey) {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
            if (selectionKey.isReadable()) {
                int size = socketChannel.read(buffer);
                if (size == -1) {
                    handleDisconnect(selectionKey);
                } else {
                    buffer.flip();
                    in = new String(buffer.array());
                    buffer.flip();
                    Bukkit.getServer().getPluginManager().callEvent(new ClientReceivesData(this));
                }
            }
        } catch (IOException e) {
            logError(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void logError(String string) {
        error += string;
    }

    public void startThread() {
        try {
            if (!running) {
                runnable = new SocketRunnable(new InetSocketAddress(InetAddress.getByName(host), port), this);
                task = Bukkit.getScheduler().runTaskAsynchronously(SkriptAnywhere.getInstance(), runnable);
                running = true;
            }
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        task.cancel();
        runnable.close();
    }
}
