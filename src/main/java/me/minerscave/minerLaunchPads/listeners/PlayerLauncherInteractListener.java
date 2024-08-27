package me.minerscave.minerLaunchPads.listeners;

import me.minerscave.minerLaunchPads.MinerLaunchPads;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.Objects;
import java.util.Set;

public class PlayerLauncherInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteractWithLauncher(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.PHYSICAL)) {
            Player player = event.getPlayer();
            Block block = event.getClickedBlock();

            if (block == null || !block.getType().equals(Material.LIGHT_WEIGHTED_PRESSURE_PLATE)) {
                return; // Esci se il blocco non è una pedana leggera
            }

            Configuration config = MinerLaunchPads.getPlugin().getConfig();
            ConfigurationSection launchersSection = config.getConfigurationSection("launchers");
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
                    double configVelocity = section.getDouble("velocity");
                    double configHeight = section.getDouble("height");

                    if (configWorld == world && configX == x && configY == y && configZ == z) {
                        // Lancia il giocatore
                        Vector launchVector = player.getLocation().getDirection().multiply(configVelocity).setY(configHeight);
                        player.setVelocity(launchVector);
                        player.playSound(player.getLocation(), Sound.ENTITY_WITHER_SHOOT, 100, -5);
                        break; // Esci dal loop se trovi il launcher corrispondente
                    }
                }
            }
        }
    }
}
