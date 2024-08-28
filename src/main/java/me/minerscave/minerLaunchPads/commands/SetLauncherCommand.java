package me.minerscave.minerLaunchPads.commands;

import me.minerscave.minerLaunchPads.MinerLaunchPads;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
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

public class SetLauncherCommand implements CommandExecutor {



    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        Configuration config = MinerLaunchPads.getPlugin().getConfig();
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

        if (commandSender instanceof Player player) {

            if (args.length < 2) {
                player.sendMessage(ChatColor.RED + "Usage: " + command.getUsage());
                return true;
            }

            if (!onlyDigits(args[0], args[0].length()) || !onlyDigits(args[1], args[1].length())) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("messages.error-only-ints-as-args")));
                return true;
            }

            int velocity = Integer.parseInt(args[0]);
            int height = Integer.parseInt(args[1]);

            Block targetBlock = player.getTargetBlock(null, 4);

            if(!targetBlock.getType().equals(Material.OAK_PRESSURE_PLATE)){
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("messages.error-not-oak-plate")));
                return true;
            }

            String world = player.getWorld().getName();
            int x = targetBlock.getLocation().getBlockX();
            int y = targetBlock.getLocation().getBlockY();
            int z = targetBlock.getLocation().getBlockZ();

            ConfigurationSection launchersSection = config.getConfigurationSection("launchers");
            if (launchersSection == null) {
                launchersSection = config.createSection("launchers");
            }
            Set<String> keys = launchersSection.getKeys(false);

            if(!keys.isEmpty()) {
                for (String key : keys) {
                    String configWorld = config.getString(key + ".world");
                    int configX = config.getInt(key + ".x");
                    int configY = config.getInt(key + ".y");
                    int configZ = config.getInt(key + ".z");

                    if (Objects.equals(configWorld, world) && configX == x && configY == y && configZ == z) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("messages.error-already-a-launch-pad")));
                        return true;  // Se il launcher esiste già, esci
                    }
                }

                // Se non c'è un launcher alla posizione specifica, crealo
                setLauncher(targetBlock, velocity, height, player);
            }else{
                setLauncher(targetBlock, velocity, height, player);
            }
        } else {
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', MinerLaunchPads.getPlugin().getConfig().getString("messages.error-you-are-not-a-player")));
        }
        return true;
    }

    private void setLauncher(Block targetBlock, int velocity, int height, Player player) {
        Configuration config = MinerLaunchPads.getPlugin().getConfig();
        int nextLauncherIndex = getNextAvailableLauncherIndex(config);

        targetBlock.setType(Material.LIGHT_WEIGHTED_PRESSURE_PLATE);
        String basePath = "launchers.launcher" + nextLauncherIndex;

        config.set(basePath + ".world", player.getWorld().getName());
        config.set(basePath + ".x", targetBlock.getLocation().getBlockX());
        config.set(basePath + ".y", targetBlock.getLocation().getBlockY());
        config.set(basePath + ".z", targetBlock.getLocation().getBlockZ());
        config.set(basePath + ".velocity", velocity);
        config.set(basePath + ".height", height);

        MinerLaunchPads.getPlugin().saveConfig();  // Salva la configurazione

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("messages.success-launcher-set").replace("%x%", String.valueOf(targetBlock.getLocation().getBlockX())).replace("%y%", String.valueOf(targetBlock.getLocation().getBlockY())).replace("%z%", String.valueOf(targetBlock.getLocation().getBlockZ()))));
        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 100, 1);
    }

    public static boolean onlyDigits(String str, int n) {
        for (int i = 0; i < n; i++) {

            if (str.charAt(i) < '0' || str.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }

    private int getNextAvailableLauncherIndex(Configuration config) {
        int index = 1;
        while (config.contains("launchers.launcher" + index)) {
            index++;
        }
        return index;
    }
}
