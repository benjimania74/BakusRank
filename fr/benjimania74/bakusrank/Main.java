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
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main extends JavaPlugin {
    public static List<Team> teamList = new ArrayList<>();
    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        loop();

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

    public void loop(){
        new BukkitRunnable(){
            @Override
            public void run() {
                ScoreboardManager sm = Bukkit.getScoreboardManager();
                for(String key : getConfig().getConfigurationSection("Groups").getKeys(false)){
                    String name = getConfig().getInt("Groups." + key + ".tabPriority") + "";
                    boolean unique = true;
                    for(Team t : sm.getMainScoreboard().getTeams()){
                        if(t.getName().equals(name)){
                            unique = false;
                        }
                    }
                    if(unique) {
                        Team team = sm.getMainScoreboard().registerNewTeam(name);
                        if (!teamList.contains(sm.getMainScoreboard().getTeam(getConfig().getInt("Groups." + key + ".tabPriority") + ""))) {
                            teamList.add(team);
                        }
                    }
                }

                for(Player player : Bukkit.getOnlinePlayers()){
                    String pluginPath = "plugins/BakusRank";
                    File pRank = new File(pluginPath, "playerRank.yml");
                    FileConfiguration playerRank = YamlConfiguration.loadConfiguration(pRank);

                    for(Team t : teamList){
                        if(t.getName().equals(getConfig().getInt("Groups." + playerRank.getString(player.getName()) + ".tabPriority") + "")){
                            t.addEntry(player.getName());
                        }
                    }

                    Perm perm = new Perm();

                    String rank = playerRank.getString(player.getName());

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
                loop();
            }
        }.runTaskLater(this,  5L);
    }

    public static Main getInstance() {
        return instance;
    }
}