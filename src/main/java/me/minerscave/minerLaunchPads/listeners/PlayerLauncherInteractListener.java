package me.minerscave.minerLaunchPads.listeners;

import me.minerscave.minerLaunchPads.MinerLaunchPads;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import java.time.Duration;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

public class PlayerLauncherInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteractWithLauncher(PlayerInteractEvent event) {
        Configuration config = MinerLaunchPads.getPlugin().getConfig();
        if (event.getAction().equals(Action.PHYSICAL)) {
            Player player = event.getPlayer();

            LuckPerms api = LuckPermsProvider.get();
            User user = api.getPlayerAdapter(Player.class).getUser(player);
            String group = user.getPrimaryGroup();
            Block block = event.getClickedBlock();

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
                    double configVelocity = section.getDouble("velocity");
                    double configHeight = section.getDouble("height");

                    if (Objects.equals(configWorld, world) && configX == x && configY == y && configZ == z) {
                        if (block == null || !block.getType().equals(Material.LIGHT_WEIGHTED_PRESSURE_PLATE)) {
                            return; // Esci se il blocco non è una pedana leggera
                        }

                        if(!hasPermission(api.getGroupManager().getGroup(group), "minerlauncher.uselaunchers")){
                            if(!hasPermission(user, "minerlauncher.uselaunchers")){
                                return;
                            }
                        }

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
    public boolean hasPermission(Group group, String permission) {
        return group.getCachedData().getPermissionData().checkPermission(permission).asBoolean();
    }

    public boolean hasPermission(User user, String permission) {
        return user.getCachedData().getPermissionData().checkPermission(permission).asBoolean();
    }






}
