package me.minerscave.minerLaunchPads.commands;

import me.minerscave.minerLaunchPads.MinerLaunchPads;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.Set;

public class RemoveLauncherCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

        if (commandSender instanceof Player player) {
            Configuration config = MinerLaunchPads.getPlugin().getConfig();
            Block targetBlock = player.getTargetBlock(null, 4);
            ConfigurationSection launchersSection = config.getConfigurationSection("launchers");
            if (launchersSection == null) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("messages.error-no-launchers-set")));
                return true;
            }
            Set<String> keys = launchersSection.getKeys(false);

            String world = player.getWorld().getName();
            int x = targetBlock.getLocation().getBlockX();
            int y = targetBlock.getLocation().getBlockY();
            int z = targetBlock.getLocation().getBlockZ();

            if (!keys.isEmpty()) {
                boolean launcherFound = false;

                for (String key : keys) {
                    ConfigurationSection section = config.getConfigurationSection("launchers."+key);

                    if (section != null) {
                        String configWorld = section.getString("world");
                        int configX = section.getInt("x");
                        int configY = section.getInt("y");
                        int configZ = section.getInt("z");

                        if (configWorld == world && configX == x && configY == y && configZ == z) {
                            // Segna il launcher come rimosso
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("messages.success-launcher-remove").replace("%x%", String.valueOf(targetBlock.getLocation().getBlockX())).replace("%y%", String.valueOf(targetBlock.getLocation().getBlockY())).replace("%z%", String.valueOf(targetBlock.getLocation().getBlockZ()))));

                            targetBlock.setType(Material.AIR);  // Imposta il blocco come aria (rimuovendo il launcher)

                            config.set("launchers."+key, null);
                            MinerLaunchPads.getPlugin().saveConfig();  // Salva la configurazione
                            launcherFound = true;
                            break;
                        }
                    }
                }

                if (!launcherFound) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("messages.error-not-a-launch-pad")));
                }
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("messages.error-no-launchers-set")));
            }
        } else {
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', MinerLaunchPads.getPlugin().getConfig().getString("messages.error-you-are-not-a-player")));
        }

        return true;
    }
}
