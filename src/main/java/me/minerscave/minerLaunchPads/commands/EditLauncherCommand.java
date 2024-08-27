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

import java.util.Set;

public class EditLauncherCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {


        if(commandSender instanceof Player player){

            Configuration config = MinerLaunchPads.getPlugin().getConfig();
            Block block = player.getTargetBlock(null, 4);
            ConfigurationSection launchersSection = config.getConfigurationSection("launchers");

            if (launchersSection == null) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("messages.error-no-launchers-set")));
                return true;
            }

            Set<String> keys = launchersSection.getKeys(false);

            String world = player.getWorld().getName();
            int x = block.getLocation().getBlockX();
            int y = block.getLocation().getBlockY();
            int z = block.getLocation().getBlockZ();



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



            if (!keys.isEmpty()) {
                boolean launcherFound = false;

                for (String key : keys) {
                    ConfigurationSection section = config.getConfigurationSection("launchers."+key);

                    if (section != null) {
                        String configWorld = config.getString(key + ".world");
                        int configX = section.getInt("x");
                        int configY = section.getInt("y");
                        int configZ = section.getInt("z");

                        if (configWorld == world && configX == x && configY == y && configZ == z) {
                            editLauncher(block, velocity, height, player, key);
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

    private void editLauncher(Block targetBlock, int velocity, int height, Player player, String key) {
        Configuration config = MinerLaunchPads.getPlugin().getConfig();

        config.set("launchers."+key + ".velocity", velocity);
        config.set("launchers."+key + ".height", height);

        MinerLaunchPads.getPlugin().saveConfig();  // Salva la configurazione

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("messages.success-launcher-edit").replace("%x%", String.valueOf(targetBlock.getLocation().getBlockX())).replace("%y%", String.valueOf(targetBlock.getLocation().getBlockY())).replace("%z%", String.valueOf(targetBlock.getLocation().getBlockZ()))));
    }

    public static boolean onlyDigits(String str, int n) {
        for (int i = 0; i < n; i++) {

            if (str.charAt(i) < '0' || str.charAt(i) > '9') {
                return false;
            }
        }

        return true;
    }
}
