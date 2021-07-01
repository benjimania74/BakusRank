package fr.benjimania74.bakusrank.listeners;

import fr.benjimania74.bakusrank.Perm;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;

public class onQuit implements Listener {
    private Perm perm = new Perm();

    @SuppressWarnings("unused")
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        String pluginPath = "plugins/BakusRank";
        File pRank = new File(pluginPath, "playerRank.yml");
        FileConfiguration playerRank = YamlConfiguration.loadConfiguration(pRank);
        File conf = new File(pluginPath, "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(conf);

        perm.delPermAll(e.getPlayer());
    }
}
