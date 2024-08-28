package me.minerscave.minerLaunchPads.listeners;

import me.minerscave.minerLaunchPads.MinerLaunchPads;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Objects;
import java.util.Set;

public class PlayerLauncherBlockBreakListener implements Listener {

    @EventHandler
    public void onPlayerLauncherBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        Configuration config = MinerLaunchPads.getPlugin().getConfig();
        ConfigurationSection launchersSection = config.getConfigurationSection("launchers");
        if (launchersSection == null) {
            return;
        }
        Set<String> keys = launchersSection.getKeys(false);

        String world = player.getWorld().getName();
        int x = block.getLocation().getBlockX();
        int y = block.getLocation().getBlockY();
        int z = block.getLocation().getBlockZ();

        for (String key : keys) {
            ConfigurationSection section = config.getConfigurationSection("launchers."+key);

            if (section != null) {
                String configWorld = section.getString("world");
                int configX = section.getInt("x");
                int configY = section.getInt("y");
                int configZ = section.getInt("z");

                if (Objects.equals(configWorld, world) && configX == x && configY == y && configZ == z) {

                    if (block == null || !block.getType().equals(Material.LIGHT_WEIGHTED_PRESSURE_PLATE)) {
                        return; // Esci se il blocco non è una pedana leggera
                    }

                    // Blocca la rottura del launcher
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("messages.cant-break")));
                    event.setCancelled(true);
                    return; // Esci dopo aver trovato il launcher corrispondente
                }
            }
        }
    }
}
