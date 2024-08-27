package me.minerscave.minerLauncher;

import me.minerscave.minerLauncher.commands.EditLauncherCommand;
import me.minerscave.minerLauncher.commands.LauncherInfoCommand;
import me.minerscave.minerLauncher.commands.RemoveLauncherCommand;
import me.minerscave.minerLauncher.commands.SetLauncherCommand;
import me.minerscave.minerLauncher.listeners.PlayerLauncherBlockBreakListener;
import me.minerscave.minerLauncher.listeners.PlayerLauncherInteractListener;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinerLauncher extends JavaPlugin {

    private static MinerLauncher plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("MinerLauncher plugin started!");
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

    public static MinerLauncher getPlugin() {
        return plugin;
    }
}
