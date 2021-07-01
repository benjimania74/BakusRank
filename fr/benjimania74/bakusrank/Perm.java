package fr.benjimania74.bakusrank;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.HashMap;
import java.util.UUID;

public class Perm {
    HashMap<UUID, PermissionAttachment> perms = new HashMap<>();

    public void addDefaultPerm(Player player, String group) {
        PermissionAttachment attachment = player.addAttachment(Main.getInstance());
        perms.put(player.getUniqueId(), attachment);
        permissionSetter(player.getUniqueId(), group);
    }

    public void delPermAll(Player player){
        perms.remove(player.getUniqueId());
    }

    private void permissionSetter(UUID playerUniqueId, String groupToGetPerm) {
        PermissionAttachment attachment = perms.get(playerUniqueId);

        for(String group : Main.getInstance().getConfig().getConfigurationSection("Groups").getKeys(false)){
            for (String permissions : Main.getInstance().getConfig().getStringList("Groups." + groupToGetPerm + ".permissions")) {
                attachment.setPermission(permissions, true);
            }
        }
    }
}
