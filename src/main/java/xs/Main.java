package xs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;


public final class Main extends JavaPlugin {
    public static Plugin plugin;
    final static ConsoleCommandSender console = Bukkit.getConsoleSender();
    final static String PluginPrefix = "[" + ChatColor.AQUA + "Mention" + ChatColor.RESET + "]";
    final static String ErrorPluginPrefix = "[" + ChatColor.RED + "Mention" + ChatColor.RESET + "]";
    private ConfigManager cfgm;

    @Override
    public void onEnable() {
        plugin = getPlugin(Main.class);
        //setup config
        cfgm = new ConfigManager();
        cfgm.setup();

        this.getServer().getPluginManager().registerEvents(new Mention(), this);
        getConfig().options().copyDefaults(true);

        console.sendMessage(cfgm.language.getString("onEnable").replace("&", "§").replace("%pluginPrefix%", PluginPrefix));
    }

    @Override
    public void onDisable() {
        cfgm.saveConfig();
        console.sendMessage(cfgm.language.getString("onDisable").replace("&", "§").replace("%pluginPrefix%", PluginPrefix));
    }


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("mention.admin")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    this.getConfig().getString("No-Permissions")));
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage("指令: /mention reload");
            return true;
        }

        switch (args[0]) {
            case "reload":
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        this.getConfig().getString("Reload-Message")));
                cfgm.reloadConfig();
                break;
            case "toggle":
                if (sender instanceof Player)
                    toggleMention((Player) sender);
                break;
            default:
                sender.sendMessage("wrong argument");
                break;
        }
//    } else { /mention Reload
//        if (args[0].equalsIgnoreCase("reload")) {
//            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
//                    this.getConfig().getString("Reload-Message")));
//            cfgm.reloadConfig();
//        }else if (){
//
//        }else {
//
//        }
//    }

        return false;
    }

    private void toggleMention(Player player) {
        FileConfiguration pData = cfgm.playerData;

        String uuid = player.getUniqueId().toString().replace("-", "");

        pData.createSection("Data");
        ConfigurationSection section = pData.createSection("Data." + uuid);
        if (pData.contains("Data." + uuid)) {
            section.set("state", !section.getBoolean("state"));
        } else {
            section.set("state", false);
        }
    }
}
