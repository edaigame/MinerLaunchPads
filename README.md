# LaunchPads Plugin by Miner

### Description

The **LaunchPads Plugin** is a Minecraft Spigot plugin that allows players to create customizable launchpads. These launchpads can propel players into the air with controlled jumps, allowing for the adjustment of both velocity and height. The plugin also supports multi-world servers, offering flexible and configurable experiences for different scenarios.

### Features

- **LaunchPad Creation**: Players can create launchpads by placing specific blocks and configuring their launch properties.
- **LaunchPad Modification**: Players can edit launchpads by using commands, modifying velocity and height values.
- **Customizable Velocity**: You can adjust the launch velocity, customizing the intensity of the forward boost.
- **Customizable Height**: Configure the height of the jump.
- **Multi-World Support**: Full functionality for multi-world servers, allowing you to define different launchpads in each world.
- **Ease of Use**: Intuitive commands and simple configuration make it easy to integrate the plugin into any Minecraft server.

### Commands

- `/setlaunchpad <velocity> <height>`: Create a new launchpad.
- `/editlaunchpad <velocity> <height>`: Edit the launchpad's values.
- `/removelaunchpad`: Removes the launchpad.
- `/launchpadinfo`: Retreive information about a launchpad.

### Permissions

- `minerlauncher.setlauncher`: Allows players to set launchpads.
- `minerlauncher.removelauncher`: Allows players to remove launchpads.
- `minerlauncher.editlauncher`: Allows players to edit launchpads.
- `minerlauncher.launcherinfo`: Allows players to retreive information about launchpads.
- `minerlauncher.uselaunchers`: Allows players to use launchers.

### Installation

1. Download the `.jar` file from the [SpigotMC page](https://www.spigotmc.org/resources/miner-launchpads.119192/)
2. Place the file into your server's `plugins` folder.
3. Restart your server or use the `/reload` command to load the plugin.

### Configuration

The configuration file can be found in the `plugins/LaunchPads/config.yml` directory. You can customize the default settings for velocity, height, and other even customize the chat messages.
