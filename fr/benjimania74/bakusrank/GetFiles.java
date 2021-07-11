package fr.benjimania74.bakusrank;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class GetFiles {
    private final String pluginPath = "plugins/BakusRank";
    private final File pRank = new File(pluginPath, "playerRank.yml");
    private final FileConfiguration playerRank = YamlConfiguration.loadConfiguration(pRank);
    private final File conf = new File(pluginPath, "config.yml");
    private final FileConfiguration config = YamlConfiguration.loadConfiguration(conf);

    public String getRank(String username){
        return playerRank.getString(username);
    }

    public String getPrefix(String username){
        return config.getString("Groups." + getRank(username) + ".prefix").replace("&", "§");
    }

    public String getSuffix(String username){
        return config.getString("Groups." + getRank(username) + ".suffix").replace("&", "§");
    }

    public String getColor(String username){
        return config.getString("Groups." + getRank(username) + ".color").replace("&", "§");
    }

    public String getChatSeparator(){
        return config.getString("chatseparator").replace("&", "§");
    }
}
