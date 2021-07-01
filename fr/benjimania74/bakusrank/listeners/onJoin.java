package fr.benjimania74.bakusrank.listeners;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;
public class onJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        String pluginPath = "plugins/BakusRank";

        File pRank = new File(pluginPath, "playerRank.yml");
        FileConfiguration playerRank = YamlConfiguration.loadConfiguration(pRank);

        Player player = e.getPlayer();

        if(!playerRank.isSet(player.getDisplayName())){
            playerRank.set(player.getDisplayName(), "Default");
            try {
                playerRank.save(pRank);
            } catch (IOException IOe) {
                IOe.printStackTrace();
            }
        }
    }
}
