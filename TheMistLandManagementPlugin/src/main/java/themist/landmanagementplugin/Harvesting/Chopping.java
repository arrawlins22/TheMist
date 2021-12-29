package themist.landmanagementplugin.Harvesting;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import themist.landmanagementplugin.LandManagementPlugin;

import java.util.Map;

public class Chopping implements Listener {

    Location spawnLocation;
    Map<String, Location> safeSpaces;

    public Chopping(Map<String, Location> safe) {
        safeSpaces = safe;
    }

    @EventHandler
    public void onChop(BlockBreakEvent event) {
        if(!event.getBlock().getType().toString().contains("WOOD")) {
            return;
        }
        if((isInHolyLand(event.getBlock().getLocation())) || !isSafe(event.getBlock().getLocation()).equals("")) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("No harvesting here");
            return;
        }
        if(event.getBlock().getType().toString().contains("FENCE")){
            event.setCancelled(true);
            event.getPlayer().getWorld().dropItem(event.getBlock().getLocation(), new ItemStack(Material.STICK, 1));
            return;
        }
        Material mat = event.getBlock().getType();
        event.getPlayer().getWorld().dropItem(event.getBlock().getLocation(), (ItemStack) event.getBlock().getDrops().toArray()[0]);
        event.getBlock().setType(Material.BROWN_STAINED_GLASS);
        Bukkit.getScheduler().runTaskLater(LandManagementPlugin.getPlugin(LandManagementPlugin.class), () -> {
            event.getBlock().setType(mat);
        }, 200);
    }

    private boolean isInHolyLand(Location location) {
        spawnLocation = new Location(location.getWorld(),-9, 63, 7,0,0);
        if ((location.getBlockX() < spawnLocation.getBlockX() + 40) && (location.getBlockX() > spawnLocation.getBlockX() - 40)) {
            if ((location.getBlockZ() < spawnLocation.getBlockZ() + 40) && (location.getBlockZ() > spawnLocation.getBlockZ() - 40)) {
                return true;
            }
        }
        return false;
    }

    public String isSafe(Location location) {

        for (Map.Entry<String, Location> entry : safeSpaces.entrySet()) {
            if(entry.getValue() != null) {
                if ((location.getBlockX() < entry.getValue().getBlockX() + 25) && (location.getBlockX() > entry.getValue().getBlockX() - 25)) {
                    if ((location.getBlockZ() < entry.getValue().getBlockZ() + 25) && (location.getBlockZ() > entry.getValue().getBlockZ() - 25)) {
                        return entry.getKey();
                    }
                }
            }
        }
        return "";
    }
}
