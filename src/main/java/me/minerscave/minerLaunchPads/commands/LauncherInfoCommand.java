package me.minerscave.minerLaunchPads.commands;

import me.minerscave.minerLaunchPads.MinerLaunchPads;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.Set;

public class LauncherInfoCommand implements CommandExecutor {

    Configuration config = MinerLaunchPads.getPlugin().getConfig();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {


        if(commandSender instanceof Player player){

            Block block = player.getTargetBlock(null, 4);
            ConfigurationSection launchersSection = config.getConfigurationSection("launchers");

            if(launchersSection == null) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("messages.error-no-launchers-set")));
                return true;
            }
            Set<String> keys = launchersSection.getKeys(false);



            String world = player.getWorld().getName();
            int x = block.getLocation().getBlockX();
            int y = block.getLocation().getBlockY();
            int z = block.getLocation().getBlockZ();

            if (!keys.isEmpty()) {
                boolean launcherFound = false;

                for (String key : keys) {
                    ConfigurationSection section = config.getConfigurationSection("launchers."+key);

                    if (section != null) {
                        String configWorld = section.getString("world");
                        int configX = section.getInt("x");
                        int configY = section.getInt("y");
                        int configZ = section.getInt("z");
                        int velocity = section.getInt("velocity");
                        int height = section.getInt("height");

                        if (configWorld == world && configX == x && configY == y && configZ == z) {
                            player.sendMessage(ChatColor.GOLD + "-- Launcher Info --------------------------------");
                            player.sendMessage(" ");
                            player.sendMessage(ChatColor.GOLD + "Name: " + ChatColor.RESET + key);
                            player.sendMessage(ChatColor.GOLD + "World: " + ChatColor.RESET + configWorld);
                            player.sendMessage(ChatColor.GOLD + "x: " + ChatColor.RESET + configX);
                            player.sendMessage(ChatColor.GOLD + "y: " + ChatColor.RESET + configY);
                            player.sendMessage(ChatColor.GOLD + "z: " + ChatColor.RESET + configZ);
                            player.sendMessage(ChatColor.GOLD + "Velocity: " + ChatColor.RESET + velocity);
                            player.sendMessage(ChatColor.GOLD + "Height: " + ChatColor.RESET + height);
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

        }else{
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', MinerLaunchPads.getPlugin().getConfig().getString("messages.error-you-are-not-a-player")));
        }

        return true;
    }
}


