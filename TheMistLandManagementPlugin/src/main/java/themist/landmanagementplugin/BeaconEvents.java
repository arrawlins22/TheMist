package themist.landmanagementplugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class BeaconEvents implements Listener {
    ArrayList<Location> regenBeacons;
    ArrayList<Location> speedBeacons;
    public BeaconEvents() {
        regenBeacons = new ArrayList<>();
        speedBeacons = new ArrayList<>();
    }

    @EventHandler
    public void onPlaceEvent(BlockPlaceEvent event) {
        Location location = event.getBlock().getLocation();
        String string = event.getItemInHand().getItemMeta().getDisplayName();
        if(!event.getBlock().getType().equals(Material.SOUL_TORCH)) {
            return;
        }
        if(string.equals("Regen")) {
            regenBeacons.add(location);
            Bukkit.getScheduler().runTaskLater(LandManagementPlugin.getPlugin(LandManagementPlugin.class), () -> {
                event.getBlock().setType(Material.AIR);
                regenBeacons.remove(location);
            }, 6000);
            event.setCancelled(false);
            return;
        }
        if(string.equals("Speed")) {
            speedBeacons.add(location);
            Bukkit.getScheduler().runTaskLater(LandManagementPlugin.getPlugin(LandManagementPlugin.class), () -> {
                event.getBlock().setType(Material.AIR);
                speedBeacons.remove(location);
            }, 6000);
            event.setCancelled(false);
            return;
        }
    }
    @EventHandler
    public void moveInBeaconEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if(inSpeedBeaconRange(player)) {
            if(!player.hasPotionEffect(PotionEffectType.SPEED)) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 0));
            }
        }
        if(inRegenBeaconRange(player)) {
            if(!player.hasPotionEffect(PotionEffectType.REGENERATION)) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 0));
            }
        }

    }

    private boolean inSpeedBeaconRange(Player player) {
        Location location = player.getLocation();
        for(int i = 0; i < speedBeacons.size(); i++) {
                if ((location.getBlockX() < speedBeacons.get(i).getBlockX() + 50) && (location.getBlockX() > speedBeacons.get(i).getBlockX() - 50)) {
                    if ((location.getBlockZ() < speedBeacons.get(i).getBlockZ() + 50) && (location.getBlockZ() > speedBeacons.get(i).getBlockZ() - 50)) {
                        return true;
                    }
                }
        }
        return false;
    }
    private boolean inRegenBeaconRange(Player player) {
        Location location = player.getLocation();
        for(int i = 0; i < regenBeacons.size(); i++) {
            if ((location.getBlockX() < regenBeacons.get(i).getBlockX() + 50) && (location.getBlockX() > regenBeacons.get(i).getBlockX() - 50)) {
                if ((location.getBlockZ() < regenBeacons.get(i).getBlockZ() + 50) && (location.getBlockZ() > regenBeacons.get(i).getBlockZ() - 50)) {
                    return true;
                }
            }
        }
        return false;
    }









}
