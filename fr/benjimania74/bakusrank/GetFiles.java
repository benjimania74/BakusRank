package fr.benjimania74.bakusrank;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class GetFiles {
    private final String pluginPath = "plugins/BakusRank";
    private final File pRank = new File(pluginPath, "playerRank.yml");
    private final FileConfiguration playerRank = YamlConfiguration.loadConfiguration(pRank);
    private final File conf = new File(pluginPath, "config.yml");
    private final FileConfiguration config = YamlConfiguration.loadConfiguration(conf);

    public String getRank(String username){
        String rank = playerRank.getString(username);
        return rank;
    }

    public String getPrefix(String username){
        String prefix = config.getString("Groups." + getRank(username) + ".prefix").replace("&", "§");
        return prefix;
    }

    public String getSuffix(String username){
        String suffix = config.getString("Groups." + getRank(username) + ".suffix").replace("&", "§");
        return suffix;
    }

    public String getColor(String username){
        String color = config.getString("Groups." + getRank(username) + ".color").replace("&", "§");
        return color;
    }

    public String getChatSeparator(){
        String chatSepa = config.getString("chatseparator").replace("&", "§");
        return chatSepa;
    }
}
