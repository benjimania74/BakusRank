package fr.benjimania74.bakusrank;

import fr.benjimania74.bakusrank.commands.CommandPerm;
import fr.benjimania74.bakusrank.commands.RankCommands;
import fr.benjimania74.bakusrank.listeners.onChat;
import fr.benjimania74.bakusrank.listeners.onJoin;
import fr.benjimania74.bakusrank.listeners.onQuit;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        setupPandF(1);

        File pRank = new File("plugins/BakusRank", "playerRank.yml");
        if(!pRank.exists()) {
            try {
                pRank.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("BakusRank: On");
        Bukkit.getPluginManager().registerEvents(new onJoin(), this);
        Bukkit.getPluginManager().registerEvents(new onQuit(), this);
        Bukkit.getPluginManager().registerEvents(new onChat(), this);
        getCommand("perm").setExecutor(new CommandPerm());
        getCommand("rank").setExecutor(new RankCommands());
    }

    @Override
    public void onDisable() {
        System.out.println("BakusRank: On");
    }

    public void setupPandF(final int i){
        new BukkitRunnable(){
            @Override
            public void run() {
                int time = i;
                if (time != 0) {
                    time--;
                    setupPandF(time);
                    if (time == 0) {
                        time--;
                    }
                }
                if (time == 0){
                    for(Player player : Bukkit.getOnlinePlayers()){
                        Perm perm = new Perm();
                        String pluginPath = "plugins/BakusRank";
                        File pRank = new File(pluginPath, "playerRank.yml");
                        FileConfiguration playerRank = YamlConfiguration.loadConfiguration(pRank);

                        reloadConfig();
                        String rank = playerRank.getString(player.getDisplayName());

                        if(getConfig().getBoolean("suffixInList")) {
                            String prefix = Objects.requireNonNull(getConfig().getString("Groups." + rank + ".prefix")).replace("&", "§");
                            String suffix = Objects.requireNonNull(getConfig().getString("Groups." + rank + ".suffix")).replace("&", "§");
                            player.setPlayerListName(prefix + " " + player.getDisplayName() + " " + suffix);
                            player.setCustomName(prefix + " " + player.getDisplayName() + " " + suffix);
                            player.setCustomNameVisible(true);
                            perm.addDefaultPerm(player, rank);
                        }else {
                            String prefix = Objects.requireNonNull(getConfig().getString("Groups." + rank + ".prefix")).replace("&", "§");
                            player.setPlayerListName(prefix + " " + player.getDisplayName());
                            player.setCustomName(prefix + " " + player.getDisplayName());
                            player.setCustomNameVisible(true);
                            perm.addDefaultPerm(player, rank);
                        }
                    }
                    setupPandF(1);
                }
            }
        }.runTaskLater(this, 20L);
    };

    public static Main getInstance() {
        return instance;
    }
}