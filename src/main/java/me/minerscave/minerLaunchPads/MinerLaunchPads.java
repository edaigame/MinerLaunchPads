package me.minerscave.minerLaunchPads;

import me.minerscave.minerLaunchPads.commands.EditLauncherCommand;
import me.minerscave.minerLaunchPads.commands.LauncherInfoCommand;
import me.minerscave.minerLaunchPads.commands.RemoveLauncherCommand;
import me.minerscave.minerLaunchPads.commands.SetLauncherCommand;
import me.minerscave.minerLaunchPads.listeners.PlayerLauncherBlockBreakListener;
import me.minerscave.minerLaunchPads.listeners.PlayerLauncherInteractListener;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinerLaunchPads extends JavaPlugin {

    private static MinerLaunchPads plugin;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        plugin = this;
        saveConfig();
        // Plugin startup logic
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[MinerLaunchPads] Plugin started!"));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[MinerLaunchPads] Plugin made by &lMiner's Cave!"));




        //Register Events
        getServer().getPluginManager().registerEvents(new PlayerLauncherInteractListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerLauncherBlockBreakListener(), this);

        //Register Commands
        getCommand("setlauncher").setExecutor(new SetLauncherCommand());
        getCommand("removelauncher").setExecutor(new RemoveLauncherCommand());
        getCommand("editlauncher").setExecutor(new EditLauncherCommand());
        getCommand("launcherinfo").setExecutor(new LauncherInfoCommand());


    }

    public static MinerLaunchPads getPlugin() {
        return plugin;
    }

}
