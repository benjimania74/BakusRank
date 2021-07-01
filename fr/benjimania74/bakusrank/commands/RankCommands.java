package fr.benjimania74.bakusrank.commands;

import fr.benjimania74.bakusrank.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class RankCommands implements CommandExecutor, TabCompleter {
    private final File pRank = new File("plugins/BakusRank", "playerRank.yml");
    private final FileConfiguration playerRank = YamlConfiguration.loadConfiguration(pRank);
    private final FileConfiguration config = Main.getInstance().getConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] arg) {
        if(sender.hasPermission("BakusRank.*")){
            if(arg.length != 0) {
                switch (arg[0]) {
                    case "viewgperms":
                        if(arg.length == 2){
                            if(config.isSet("Groups." + arg[1])){
                                String perms = Objects.requireNonNull(config.get("Groups." + arg[1] + ".permissions")).toString().replace("-", ",");
                                sender.sendMessage("§aPermission(s) du groupe §e" + arg[1] + "§a: §e" + perms);
                            }else{
                                sender.sendMessage("§cLe groupe §e" + arg[1] + " §cn'existe pas");
                            }
                        }else{
                            sender.sendMessage("§cUsage: §e/rank viewgperms [Group]");
                        }
                        break;
                    case "addgperm":
                        if(arg.length == 3){
                            if(config.isSet("Groups." + arg[1])){
                                List<String> perms = (List<String>) config.getList("Groups." + arg[1] + ".permissions");
                                assert perms != null;
                                perms.add(arg[2]);

                                try {
                                    config.set("Groups."+arg[1]+".permissions", perms);
                                    config.save("plugins/BakusRank/config.yml");
                                    sender.sendMessage("§aLa permission §e" + arg[2] + " §aa été ajoutée au groupe §e" + arg[1]);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }else{
                                sender.sendMessage("§cLe groupe §e" + arg[1] + " §cn'existe pas");
                            }
                        }else{
                            sender.sendMessage("§cUsage: §e/rank addgperm [Group] [permision]");
                        }
                        break;
                    case "delgperm":
                        if(arg.length == 3){
                            if(config.isSet("Groups." + arg[1])){
                                List<String> perms = (List<String>) config.getList("Groups." + arg[1] + ".permissions");
                                assert perms != null;
                                if(perms.contains(arg[2])){
                                    perms.remove(arg[2]);
                                    try {
                                        config.set("Groups."+arg[1]+".permissions", perms);
                                        config.save("plugins/BakusRank/config.yml");
                                        sender.sendMessage("§aLa permission §e" + arg[2] + " §aa été supprimée au groupe §e" + arg[1]);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }else{
                                    sender.sendMessage("§cLe groupe §e" + arg[1] + " §cn'a pas la permission §e" + arg[2]);
                                }
                            }else{
                                sender.sendMessage("§cLe groupe §e" + arg[1] + " §cn'existe pas");
                            }
                        }else{
                            sender.sendMessage("§cUsage: §e/rank delgperm [Group] [permision]");
                        }
                        break;
                    case "setgcolor":
                        if(arg.length == 3){
                            if(config.isSet("Groups."+arg[1])){
                                if(arg[2].startsWith("&")){
                                    config.set("Groups."+arg[2]+".color", arg[2]);
                                    sender.sendMessage("§aLe prefix du groupe §e" + arg[1] + " §aest maintenant §e" + arg[2] + " §a(" + arg[2].replace("&", "§") + "couleur§a)");
                                }else{
                                    config.set("Groups."+arg[2]+".color", "&" + arg[2]);
                                    sender.sendMessage("§aLe prefix du groupe §e" + arg[1] + " §aest maintenant §e" + arg[2] + " §a(§" + arg[2] + "couleur§a)");
                                }

                                try {
                                    config.save("plugins/BakusRank/config.yml");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }else{
                                sender.sendMessage("§cLe groupe spécifié n'existe pas.");
                            }
                        }else{
                            sender.sendMessage("§cUsage: §e/rank setgcolor [Group] [&color]");
                        }
                        break;
                    case "addp":
                        if(arg.length >= 2) {
                            if(arg.length == 3) {
                                if (config.isSet("Groups." + arg[2])) {
                                    sender.sendMessage("§aLe joueur §e" + arg[1] + " §aest entré dans le groupe §e" + arg[2] + "§a!");
                                    playerRank.set(arg[1], arg[2]);
                                    try {
                                        playerRank.save(pRank);
                                    } catch (IOException IOe) {
                                        IOe.printStackTrace();
                                    }
                                } else {
                                    sender.sendMessage("§cLe groupe spécifié n'existe pas.");
                                }
                            } else {
                                sender.sendMessage("§cUsage: /rank addp [Player] [Group]");
                            }
                        }else{
                            sender.sendMessage("§cUsage: §e/rank addp [Player] [Group]");
                        }
                        break;
                    case "getpgroup":
                        if(arg.length == 2){
                            if(playerRank.isSet(arg[1])){
                                sender.sendMessage("§e" + arg[1] + " §aest dans le groupe: §e" + playerRank.getString(arg[1]));
                            }else{
                                sender.sendMessage("§cLe joueur entré ne s'est jamais connecté au serveur.");
                            }
                        }else{
                            sender.sendMessage("§cUtilisation: §e/rank getpgroup [Joueur]");
                        }
                        break;
                    case "createg":
                        if(arg.length == 2){
                            if(!config.isSet("Groups." + arg[1])){
                                List<String> perm = new ArrayList();
                                perm.add("none");
                                config.set("Groups." + arg[1] + ".prefix", arg[1] + " ");
                                config.set("Groups." + arg[1] + ".suffix", "");
                                config.set("Groups." + arg[1] + ".color", "&f");
                                config.set("Groups." + arg[1] + ".permissions", perm);
                                try {
                                    config.save("plugins/BakusRank/config.yml");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                sender.sendMessage("§aLe groupe §e" + arg[1] + " §aa été créé avec succès");
                            }else{
                                sender.sendMessage("§cLe groupe §e" + arg[1] + " §cest déjà existant");
                            }
                        }else{
                            sender.sendMessage("§cUsage: /rank createg [Groupe]");
                        }
                        break;
                    case "deleteg":
                        if(arg.length == 2){
                            if(config.isSet("Groups." + arg[1])) {
                                if(!arg[1].equals("Default")){
                                    config.set("Groups." + arg[1], null);
                                    try {
                                        config.save("plugins/BakusRank/config.yml");
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    sender.sendMessage("§aLe groupe §e" + arg[1] + " §aa été supprimé avec succès");
                                    for(String po : playerRank.getStringList("player")){
                                        if(Objects.equals(playerRank.getString("player." + po), arg[1])){
                                            playerRank.set("player" + po, "Default");
                                        }
                                    }
                                }else{
                                    sender.sendMessage("§cIl est impossible de supprimer le groupe §eDefault");
                                }
                            }else{
                                sender.sendMessage("§cLe groupe §e" + arg[1] + " §cn'est pas existant");
                            }
                        }else{
                            sender.sendMessage("§cUsage: /rank deleteg [Groupe]");
                        }
                        break;
                    case "setgprefix":
                        if(arg.length == 3){
                            if(config.isSet("Groups." + arg[1])){
                                if(!arg[2].equals("empty")){
                                    config.set("Groups." + arg[1] + ".prefix", arg[2]);
                                }else{
                                    config.set("Groups." + arg[1] + ".prefix", "");
                                }

                                try {
                                    config.save("plugins/BakusRank/config.yml");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }else{
                                sender.sendMessage("§cLe groupe §e" + arg[1] + " n'existe pas");
                            }
                        }else{
                            sender.sendMessage("§cUsage: §e/rank setgprefix [Groupe] [Prefix]");
                        }
                        break;
                    case "setgsuffix":
                        if(arg.length == 3){
                            if(config.isSet("Groups." + arg[1])){
                                if(!arg[2].equals("empty")){
                                    config.set("Groups." + arg[1] + ".suffix", arg[2]);
                                }else {
                                    config.set("Groups." + arg[1] + ".suffix", "");
                                }

                                try {
                                    config.save("plugins/BakusRank/config.yml");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }else{
                                sender.sendMessage("§cLe groupe §e" + arg[1] + " n'existe pas");
                            }
                        }else{
                            sender.sendMessage("§cUsage: §e/rank setgprefix [Groupe] [Suffix]");
                        }
                        break;
                    case "getprefix":
                        if(arg.length == 2){
                            if(config.isSet("Groups." + arg[1])){
                                sender.sendMessage("§aLe prefix du groupe §e" + arg[1] + " §aest§e: " + Main.getInstance().getConfig().getString("Groups." + arg[1] + ".prefix"));
                            }else {
                                sender.sendMessage("§cLe groupe entré n'existe pas");
                            }
                        }else{
                            sender.sendMessage("§cUtilisation: §e/rank getprefix [Groupe]");
                        }
                        break;
                    case "getsuffix":
                        if(arg.length == 2){
                            if(config.isSet("Groups." + arg[1])){
                                sender.sendMessage("§aLe suffix du groupe §e" + arg[1] + " §aest§e: " + Main.getInstance().getConfig().getString("Groups." + arg[1] + ".suffix"));
                            }else {
                                sender.sendMessage("§cLe groupe entré n'existe pas");
                            }
                        }else{
                            sender.sendMessage("§cUtilisation: §e/rank getsuffix [Groupe]");
                        }
                        break;
                    case "help":
                        sender.sendMessage("§1/rank addp [joueur] [groupe]: §aPermet d'affecter un joueur à un groupe spécifique");
                        sender.sendMessage("§1/rank createg [groupe]: §aPermet de créer un groupe");
                        sender.sendMessage("§1/rank deleteg [groupe]: §aPermet de supprimer un groupe. Il ne peut sagire de Default");
                        sender.sendMessage("§1/rank getpgroup [joueur]: §aPermet d'obtenire le groupe d'un joueur spécifique");
                        sender.sendMessage("§1/rank getprefix [groupe]: §aPermet d'obtenire le prefix d'un groupe spécifique");
                        sender.sendMessage("§1/rank getsuffix [groupe]: §aPermet d'obtenire le suffix d'un groupe spécifique");
                        sender.sendMessage("§1/rank help: §aPermet d'afficher cette page");
                        sender.sendMessage("§1/rank viewgperms [groupe]: §aPermet d'afficher les permissions d'un groupe spécifique");
                        sender.sendMessage("§1/rank setprefix [groupe] [prefix]: §aPermet de changer le prefix d'un groupe spécifique");
                        sender.sendMessage("§1/rank setsuffix [groupe] [suffix]: §aPermet de changer le suffix d'un groupe spécifique");
                        sender.sendMessage("§1/rank setgcolor [groupe] [&couleur]: §aPermet de changer la couleur d'un groupe spécifique");
                        sender.sendMessage("§1/rank addgperm [groupe] [permission]: §aPermet d'ajouter une ou plusieurs permission à un groupe spécifique");
                        break;
                    default:
                        sender.sendMessage("§cUsage: §e/rank §a[option] §f| §e/rank §ahelp");
                        break;
                }
            }else{
                sender.sendMessage("§cUsage: §e/rank §a[option] §f| §e/rank §ahelp");
            }
        }else{
            sender.sendMessage("§cVous devez avoir la permission §eBakusRank.* §cpour utiliser cette commande!");
        }
        return false;
    }

    private final List<String> arguments = new ArrayList<>();

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String msg, String[] arg3) {
        if (arguments.isEmpty()) {
            arguments.add("addp");
            arguments.add("getpgroup");
            arguments.add("createg");
            arguments.add("deleteg");
            arguments.add("setgprefix");
            arguments.add("setgsuffix");
            arguments.add("getprefix");
            arguments.add("getsuffix");
            arguments.add("help");
            arguments.add("setgcolor");
            arguments.add("setgcolor");
            arguments.add("addgperm");
            arguments.add("viewgperms");
        }

        List<String> resultat = new ArrayList<>();

        if (arg3.length == 1) {
            for (String str : arguments) {
                if (str.toLowerCase().startsWith(arg3[0].toLowerCase())) {
                    resultat.add(str);
                }
            }
            return resultat;
        }
        return null;
    }
}

