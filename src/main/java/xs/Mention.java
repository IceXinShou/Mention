package xs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;


public class Mention implements Listener {

    @EventHandler()
    public void Chat(AsyncPlayerChatEvent event) {
        if (!event.getMessage().contains("@")) return;

        Player player = event.getPlayer();
        String message = event.getMessage();

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            int lastPlayerNameIndex = 0;
            int playerNameIndex;
            while ((playerNameIndex = message.indexOf(onlinePlayer.getName(), lastPlayerNameIndex)) > -1) {
                lastPlayerNameIndex = playerNameIndex + onlinePlayer.getName().length();
                //只有名字沒有tag
                int atIndex = message.lastIndexOf("@", playerNameIndex + 1);
                if (atIndex < 0 || playerNameIndex - atIndex > 2) continue;

                int spaceIndex = playerNameIndex + onlinePlayer.getName().length();
                String endSpace = spaceIndex >= message.length() || message.charAt(playerNameIndex + onlinePlayer.getName().length()) == ' ' ? "" : " ";

                message = message.substring(0, atIndex) +
                        (ChatColor.RED + "@" + onlinePlayer.getName() + endSpace + ChatColor.RESET) +
                        message.substring(playerNameIndex + onlinePlayer.getName().length());

//                player.sendMessage(message);
                onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);

            }


//            if (event.getMessage().contains("@ " + onlinePlayer.getName() + " ")) {
//                event.setMessage(event.getMessage().replace("@ " + onlinePlayer.getName(),
//                        (ChatColor.RED + "@" + onlinePlayer.getName() + ChatColor.RESET)));e s
//                onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
//            } if (event.getMessage().contains("@ " + onlinePlayer.getName())) {
//                event.setMessage(event.getMessage().replace("@ " + onlinePlayer.getName(),
//                        (ChatColor.RED + "@" + onlinePlayer.getName() + ChatColor.RESET + " ")));
//                onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
//            } if (event.getMessage().contains("@" + onlinePlayer.getName() + " ")) {
//                event.setMessage(event.getMessage().replace("@" + onlinePlayer.getName(),
//                        (ChatColor.RED + "@" + onlinePlayer.getName() + ChatColor.RESET)));
//                onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
//
//            } if (event.getMessage().contains("@" + onlinePlayer.getName())) {
//                event.setMessage(event.getMessage().replace("@" + onlinePlayer.getName(),
//                        (ChatColor.RED + "@" + onlinePlayer.getName() + ChatColor.RESET + " ")));
//                onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
//            }
        }
        event.setMessage(message);
    }
}

