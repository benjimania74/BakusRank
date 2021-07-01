package fr.benjimania74.bakusrank.listeners;

import fr.benjimania74.bakusrank.GettingRank;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;


public class onChat implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e){
        String username = e.getPlayer().getDisplayName();
        String[] playerRankInfo = GettingRank.rankInfo(username);

        e.setFormat(playerRankInfo[0] + " " + username + " " + playerRankInfo[1] + playerRankInfo[2] + playerRankInfo[3] + e.getMessage());
    }
}
