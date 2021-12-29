package themist.landmanagementplugin.Harvesting;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import themist.landmanagementplugin.LandManagementPlugin;

import java.util.Map;


public class Mining implements Listener {

    Location spawnLocation;
    Map<String, Location> safeSpaces;

    public Mining(Map<String, Location> safe) {
        safeSpaces = safe;
    }

    @EventHandler
    public void onMiningEvent(BlockBreakEvent event) {
        if(event.getPlayer().getInventory().contains(new ItemStack(Material.ACACIA_BUTTON, 64))) {
            event.setCancelled(false);
            return;
        }
        Material pick = event.getPlayer().getInventory().getItemInMainHand().getType();
        Material block = event.getBlock().getType();
        if(isInHolyLand(event.getBlock().getLocation()) && isSafe(event.getBlock().getLocation()).equals("") ) {
            event.setCancelled(false);
            return;
        }
        int MiningTime = pickMinables(pick, block);
        if(MiningTime == 1) {
            event.setCancelled(false);
            return;
        }
        if(MiningTime != 0) {
            Material mat = event.getBlock().getType();
            event.getPlayer().getWorld().dropItem(event.getBlock().getLocation(), (ItemStack) event.getBlock().getDrops().toArray()[0]);
            event.getBlock().setType(Material.GRAY_STAINED_GLASS);
            Bukkit.getScheduler().runTaskLater(LandManagementPlugin.getPlugin(LandManagementPlugin.class), () -> {
                event.getBlock().setType(mat);
            }, MiningTime);
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void teleportOut(PlayerMoveEvent event) {
        if(event.getPlayer().isInWater()) {
            if(event.getPlayer().getLocation().add(0,-1,0).getBlock().getType().equals(Material.EMERALD_BLOCK)) {
                event.getPlayer().teleport(new Location(event.getPlayer().getWorld(), -55, 108, -101, 0, 0));
                return;
            }
        }
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

    private int pickMinables(Material pick, Material block) {

        if(pick.toString().contains("PICKAXE")) {
            //Holy Mines
            if(block.equals(Material.STONE) || block.equals(Material.MOSSY_COBBLESTONE) || block.equals(Material.GLOWSTONE) ||block.equals(Material.BROWN_TERRACOTTA) || block.equals(Material.GRAY_TERRACOTTA)) {
                return 1200;
            }
            //Sticky Mines
            if(block.equals(Material.CALCITE) || block.equals(Material.PRISMARINE) || block.equals(Material.CYAN_TERRACOTTA) || block.equals(Material.LIGHT_GRAY_TERRACOTTA)) {
                return 1200;
            }
            //Sandy Mines
            if(block.equals(Material.TERRACOTTA) || block.equals(Material.ORANGE_TERRACOTTA) || block.equals(Material.YELLOW_TERRACOTTA)) {
                return 1200;
            }
            //Amethyst Mines
            if(block.equals(Material.AMETHYST_BLOCK) || block.equals(Material.DIORITE) || block.equals(Material.ANDESITE) || block.equals(Material.GRANITE)) {
                return 1200;
            }

        } else {
            return 0;
        }
        if(pick.equals(Material.STONE_PICKAXE)) {
            //Holy Mines
            if(block.equals(Material.DEEPSLATE_IRON_ORE)) {
                return 3600;
            }
        }
        if(pick.equals(Material.IRON_PICKAXE)) {
            //Holy Mines
            if(block.equals(Material.DEEPSLATE_IRON_ORE)) {
                return 3600;
            }
            //Sticky Mines
            if(block.equals(Material.DEEPSLATE_GOLD_ORE)) {
                return 4800;
            }
            //Sandy Mines
            if(block.equals(Material.DEEPSLATE_COAL_ORE) || block.equals(Material.DEEPSLATE_COPPER_ORE)) {
                return 4800;
            }
        }
        if(pick.equals(Material.GOLDEN_PICKAXE)) {
            //Holy Mines
            if(block.equals(Material.DEEPSLATE_IRON_ORE)) {
                return 3600;
            }
            //Sticky Mines
            if(block.equals(Material.DEEPSLATE_GOLD_ORE)) {
                return 4800;
            }
            //Sandy Mines
            if(block.equals(Material.DEEPSLATE_COAL_ORE) || block.equals(Material.DEEPSLATE_COPPER_ORE)) {
                return 4800;
            }
        }
        if(pick.equals(Material.DIAMOND_PICKAXE)) {
            //Holy Mines
            if(block.equals(Material.DEEPSLATE_IRON_ORE)) {
                return 3600;
            }
            //Sticky Mines
            if(block.equals(Material.DEEPSLATE_GOLD_ORE) || block.equals(Material.HONEY_BLOCK) || block.equals(Material.HONEYCOMB_BLOCK)) {
                return 4800;
            }
            //Sandy Mines
            if(block.equals(Material.DEEPSLATE_COAL_ORE) || block.equals(Material.DEEPSLATE_COPPER_ORE)) {
                return 4800;
            }
            //Amethyst Mines
            if(block.equals(Material.AMETHYST_CLUSTER)) {
                return 1;
            }
        }
        if(pick.equals(Material.NETHERITE_PICKAXE)) {
            //Holy Mines
            if(block.equals(Material.DEEPSLATE_IRON_ORE)) {
                return 3600;
            }
            //Sticky Mines
            if(block.equals(Material.DEEPSLATE_GOLD_ORE) || block.equals(Material.HONEY_BLOCK) || block.equals(Material.HONEYCOMB_BLOCK)) {
                return 4800;
            }
            //Sandy Mines
            if(block.equals(Material.DEEPSLATE_COAL_ORE) || block.equals(Material.DEEPSLATE_COPPER_ORE)) {
                return 4800;
            }
            //Amethyst Mines
            if(block.equals(Material.AMETHYST_CLUSTER)) {
                return 1;
            }
        }
        return 0;
    }
    private boolean shovelMinables(Material Shovel, Material block) {
        return false;
    }
}
