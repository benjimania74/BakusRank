package fr.benjimania74.bakusrank.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class CommandPerm implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] arg3) {
        String pluginPath = "plugins/BakusRank";
        File pRank = new File(pluginPath, "playerRank.yml");
        FileConfiguration playerRank = YamlConfiguration.loadConfiguration(pRank);
        File conf = new File(pluginPath, "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(conf);

        if(sender instanceof Player){
            if(sender.hasPermission("BakusRank.seePerms")){
                if(arg3.length == 0) {
                    sender.sendMessage("§aVous avez les permissions: §e" + config.getStringList("Groups." + playerRank.getString(((Player) sender).getDisplayName()) + ".permissions"));
                }else{
                    String player = arg3[0];
                    if(playerRank.isSet(player)){
                        sender.sendMessage("§aLe joueur §e" + player +" §aa les permissions: §e" + config.getStringList("Groups." + playerRank.getString(arg3[0]) + ".permissions"));
                    }else{
                        sender.sendMessage("§aLe joueur n'est pas enregistré sur le serveur");
                    }
                }
            }else{
                sender.sendMessage("§cTu n'as pas la permission pour utiliser cette command");
            }
        }else{
            sender.sendMessage("§cCommande uniquement pour les joueurs");
        }
        return false;
    }
}
