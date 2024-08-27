package me.minerscave.minerLaunchPads;

import me.minerscave.minerLaunchPads.commands.EditLauncherCommand;
import me.minerscave.minerLaunchPads.commands.LauncherInfoCommand;
import me.minerscave.minerLaunchPads.commands.RemoveLauncherCommand;
import me.minerscave.minerLaunchPads.commands.SetLauncherCommand;
import me.minerscave.minerLaunchPads.listeners.PlayerLauncherBlockBreakListener;
import me.minerscave.minerLaunchPads.listeners.PlayerLauncherInteractListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinerLaunchPads extends JavaPlugin {

    private static MinerLaunchPads plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[MinerLaunchPads] Plugin started!"));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[MinerLaunchPads] Plugin made by &lMiner's Cave!"));
        plugin = this;

        this.getConfig().options().copyDefaults(true);
        this.saveDefaultConfig();

        //Register Events
        getServer().getPluginManager().registerEvents(new PlayerLauncherInteractListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerLauncherBlockBreakListener(), this);

        //Register Commands
        getCommand("setlauncher").setExecutor(new SetLauncherCommand());
        getCommand("removelauncher").setExecutor(new RemoveLauncherCommand());
        getCommand("editlauncher").setExecutor(new EditLauncherCommand());
        getCommand("launcherinfo").setExecutor(new LauncherInfoCommand());
    }

    @Override
    public void onDisable() {
        plugin.reloadConfig();
    }

    public static MinerLaunchPads getPlugin() {
        return plugin;
    }
}
