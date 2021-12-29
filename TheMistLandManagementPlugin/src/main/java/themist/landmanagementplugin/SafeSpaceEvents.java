package themist.landmanagementplugin;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;

public class SafeSpaceEvents implements Listener {

    LandTokenItems LTI = new LandTokenItems();
    Map<String, ItemStack[]> landOwnership;
    Map<String, Location> safeSpaces;
    Map<String, Boolean> inNout;
    Location spawnLocation;

    public SafeSpaceEvents(Map<String, Location> safe, Map<String, ItemStack[]> land) {
        safeSpaces = safe;
        landOwnership = land;
        inNout = new HashMap<String, Boolean>();
    }

    @EventHandler
    private void placeCampFireEvent(BlockPlaceEvent event) {
        if(event.getBlock().getType() != Material.SOUL_CAMPFIRE) {
            return;
        }
        if(istooclose(event.getBlock().getLocation())) {
            event.getPlayer().sendMessage("too close bud");
            event.setCancelled(true);
            return;
        }
        if(!canHoldLand(event.getPlayer())) {
            event.getPlayer().sendMessage("You need 9 slots available to create more claims");
            return;
        }
        if(safeSpaces.containsKey(event.getItemInHand().getItemMeta().getDisplayName())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("That names taken you unoriginal loser");
            return;
        }
        Chunk chunk = event.getBlock().getLocation().getChunk();
        Location loc1 = chunk.getBlock(7,0,7).getLocation();
        Location loc2 = chunk.getBlock(8,0,8).getLocation();
        Location loc3 = chunk.getBlock(7,0,8).getLocation();
        Location loc4 = chunk.getBlock(8,0,7).getLocation();
        Location compare = event.getBlock().getLocation();
        if((compare.getBlockX() == loc1.getBlockX() && compare.getBlockZ() == loc1.getBlockZ()) ||
                (compare.getBlockX() == loc2.getBlockX() && compare.getBlockZ() == loc2.getBlockZ()) ||
                (compare.getBlockX() == loc3.getBlockX() && compare.getBlockZ() == loc3.getBlockZ()) ||
                (compare.getBlockX() == loc4.getBlockX() && compare.getBlockZ() == loc4.getBlockZ())) {
            safeSpaces.put(event.getItemInHand().getItemMeta().getDisplayName(), event.getBlock().getLocation());
            giveNewLandDeeds(event.getPlayer(), event.getItemInHand().getItemMeta().getDisplayName());
        } else {
            event.setCancelled(true);
            event.getPlayer().sendMessage("must place in the middle of a chunk, try:" + loc1.getBlockX() + " " + loc1.getBlockZ());
        }
    }

    @EventHandler
    private void breakCampFireEvent(BlockBreakEvent event) {
        if(event.getBlock().getType() != Material.SOUL_CAMPFIRE) {
            return;
        }
        if(event.getPlayer().getInventory().contains(new ItemStack(Material.ACACIA_BUTTON, 64))) {
            return;
        }
        for (Map.Entry<String, Location> entry : safeSpaces.entrySet()) {
            if(event.getBlock().getLocation().equals(entry.getValue())) {
                safeSpaces.remove(entry.getKey());
                safeSpaces.put(entry.getKey(), null);
            }
        }
    }

    @EventHandler
    private void playerMovingInEnd(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if(event.getPlayer().getInventory().contains(new ItemStack(Material.ACACIA_BUTTON, 64))) {
            return;
        }
        if(player.getLocation().getBlock().getBiome() == Biome.THE_END) {
            if(!player.hasPotionEffect(PotionEffectType.BLINDNESS)) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 0));
            }
        }
    }

    @EventHandler
    private void playerMovingInSafeSpace(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        String City = isSafe(player.getLocation());
        if(!City.equals("")) {
            if(!inNout.containsKey(player.getName())) {
                inNout.put(player.getDisplayName(), true);
                player.sendTitle(City, "wow pog", 10, 20, 10);
            }
            if(!inNout.get(player.getName())) {
                inNout.replace(player.getDisplayName(),false, true);
                player.sendTitle(City, "wow pog", 10, 20, 10);
            }
            if(!player.hasPotionEffect(PotionEffectType.GLOWING)) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 200, 0));
            }
            if(!player.hasPotionEffect(PotionEffectType.REGENERATION)) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 0));
            }
            return;
        }
        if(isInHolyLand(player.getLocation())) {
            if(!player.hasPotionEffect(PotionEffectType.GLOWING)) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 200, 0));
            }
            if(!player.hasPotionEffect(PotionEffectType.REGENERATION)) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 0));
            }
            return;
        }
        if(!player.hasPotionEffect(PotionEffectType.SLOW)) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 1));
            if(inNout.containsKey(player.getName()) && inNout.get(player.getName())) {
                inNout.replace(player.getDisplayName(), true, false);
            }
        }
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

    private void giveNewLandDeeds(Player player, String city) {
        ItemStack[] inventory = landOwnership.get(player.getUniqueId().toString());
        ItemStack deed0 = LTI.createLandDeed(city, "NW");
        ItemStack deed1 = LTI.createLandDeed(city, "N");
        ItemStack deed2 = LTI.createLandDeed(city, "NE");
        ItemStack deed3 = LTI.createLandDeed(city, "W");
        ItemStack deed4 = LTI.createLandDeed(city, "C");
        ItemStack deed5 = LTI.createLandDeed(city, "E");
        ItemStack deed6 = LTI.createLandDeed(city, "SW");
        ItemStack deed7 = LTI.createLandDeed(city, "S");
        ItemStack deed8 = LTI.createLandDeed(city, "SE");
        int count = 0;
        for(int i = 0; i < inventory.length; i++) {
            if(inventory[i] == null) {
                if(count == 0) {
                    inventory[i] = deed0;
                    count++;
                } else
                if(count == 1) {
                    inventory[i] = deed1;
                    count++;
                } else
                if(count == 2) {
                    inventory[i] = deed2;
                    count++;
                } else
                if(count == 3) {
                    inventory[i] = deed3;
                    count++;
                } else
                if(count == 4) {
                    inventory[i] = deed4;
                    count++;
                } else
                if(count == 5) {
                    inventory[i] = deed5;
                    count++;
                } else
                if(count == 6) {
                    inventory[i] = deed6;
                    count++;
                } else
                if(count == 7) {
                    inventory[i] = deed7;
                    count++;
                } else
                if(count == 8) {
                    inventory[i] = deed8;
                    landOwnership.put(player.getUniqueId().toString(), inventory);
                    return;
                }
            }
        }
    }
    private boolean canHoldLand(Player player) {
        ItemStack[] inventory = landOwnership.get(player.getUniqueId().toString());
        int emptyslots = 0;
        for(int i = 0; i < inventory.length; i++) {
            if(inventory[i] == null) {
                emptyslots++;
            }
        }
        if(emptyslots >= 9) {
            return true;
        }
        return false;

    }

    @EventHandler
    public void canBreak(BlockBreakEvent event) {
        if(event.getPlayer().getInventory().contains(new ItemStack(Material.ACACIA_BUTTON, 64))) {
            return;
        }
        if(event.getBlock().getType().toString().contains("GLASS") && !event.getBlock().getType().equals(Material.GLASS)) {
            event.getPlayer().sendMessage("No breaking this");
            event.setCancelled(true);
            return;
        }
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if(isSafe(event.getBlock().getLocation()).equals("")) {
            if(isInHolyLand(event.getBlock().getLocation())) {
                event.setCancelled(false);
                return;
            }
            event.setCancelled(true);
            return;
        }
        String city = "";
        for (Map.Entry<String, Location> entry : safeSpaces.entrySet()) {
            if(entry.getValue() != null) {
                if ((block.getLocation().getBlockX() < entry.getValue().getBlockX() + 25) && (block.getLocation().getBlockX() > entry.getValue().getBlockX() - 25)) {
                    if ((block.getLocation().getBlockZ() < entry.getValue().getBlockZ() + 25) && (block.getLocation().getBlockZ() > entry.getValue().getBlockZ() - 25)) {
                        city = entry.getKey();
                        break;
                    }
                }
            }
        }
        if(city.equals("")) {
            event.setCancelled(true);
            return;
        }
        Location match = block.getChunk().getBlock(0,0,0).getLocation();
        Location check = safeSpaces.get(city);
        for(int i = 0; i < 9; i++) {
            if(i == 0) {
                Location matchToo = new Location(player.getWorld(),check.getBlockX() -10, 0, check.getBlockZ() -10, 0 , 0);
                if(matchToo.getChunk().getBlock(0,0,0).getLocation().equals(match)) {
                    if(hasLandDeed(player, city, "NW") || hasLandDeed(player, city, "C")) {
                        event.setCancelled(false);
                        return;
                    } else {
                        player.sendMessage("you have no deed");
                        event.setCancelled(true);
                        return;
                    }
                }
            }
            if(i == 1) {
                Location matchToo = new Location(player.getWorld(),check.getBlockX(), 0, check.getBlockZ() -10, 0 , 0);
                if(matchToo.getChunk().getBlock(0,0,0).getLocation().equals(match)) {
                    if(hasLandDeed(player, city, "N") || hasLandDeed(player, city, "C")) {
                        event.setCancelled(false);
                        return;
                    } else {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
            if(i == 2) {
                Location matchToo = new Location(player.getWorld(),check.getBlockX() + 10, 0, check.getBlockZ() -10, 0 , 0);
                if(matchToo.getChunk().getBlock(0,0,0).getLocation().equals(match)) {
                    if(hasLandDeed(player, city, "NE") || hasLandDeed(player, city, "C")) {
                        event.setCancelled(false);
                        return;
                    } else {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
            if(i == 3) {
                Location matchToo = new Location(player.getWorld(),check.getBlockX() - 10, 0, check.getBlockZ(), 0 , 0);
                if(matchToo.getChunk().getBlock(0,0,0).getLocation().equals(match)) {
                    if(hasLandDeed(player, city, "W") || hasLandDeed(player, city, "C")) {
                        event.setCancelled(false);
                        return;
                    } else {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
            check = safeSpaces.get(city);
            if(i == 4) {
                Location matchToo = new Location(player.getWorld(),check.getBlockX(), 0, check.getBlockZ(), 0 , 0);
                if(matchToo.getChunk().getBlock(0,0,0).getLocation().equals(match)) {
                    if(hasLandDeed(player, city, "C")) {
                        event.setCancelled(false);
                        return;
                    } else {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
            check = safeSpaces.get(city);
            if(i == 5) {
                Location matchToo = new Location(player.getWorld(),check.getBlockX() +10, 0, check.getBlockZ(), 0 , 0);
                if(matchToo.getChunk().getBlock(0,0,0).getLocation().equals(match)) {
                    if(hasLandDeed(player, city, "E") || hasLandDeed(player, city, "C")) {
                        event.setCancelled(false);
                        return;
                    } else {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
            check = safeSpaces.get(city);
            if(i == 6) {
                Location matchToo = new Location(player.getWorld(),check.getBlockX() - 10, 0, check.getBlockZ() + 10, 0 , 0);
                if(matchToo.getChunk().getBlock(0,0,0).getLocation().equals(match)) {
                    if(hasLandDeed(player, city, "SW") || hasLandDeed(player, city, "C")) {
                        event.setCancelled(false);
                        return;
                    } else {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
            check = safeSpaces.get(city);
            if(i == 7) {
                Location matchToo = new Location(player.getWorld(),check.getBlockX(), 0, check.getBlockZ() + 10, 0 , 0);
                if(matchToo.getChunk().getBlock(0,0,0).getLocation().equals(match)) {
                    if(hasLandDeed(player, city, "S") || hasLandDeed(player, city, "C")) {
                        event.setCancelled(false);
                        return;
                    } else {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
            check = safeSpaces.get(city);
            if(i == 8) {
                Location matchToo = new Location(player.getWorld(),check.getBlockX() + 10, 0, check.getBlockZ() + 10, 0 , 0);
                if(matchToo.getChunk().getBlock(0,0,0).getLocation().equals(match)) {
                    if(hasLandDeed(player, city, "SE") || hasLandDeed(player, city, "C")) {
                        event.setCancelled(false);
                        return;
                    } else {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
        }
        event.setCancelled(true);
        player.sendMessage("no breaking here");
    }
    @EventHandler
    public void canPlace(BlockPlaceEvent event) {
        if(event.getBlock().getType() == Material.SOUL_CAMPFIRE) {
            return;
        }
        if(event.getPlayer().getInventory().contains(new ItemStack(Material.ACACIA_BUTTON, 64))) {
            return;
        }
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if(isSafe(event.getBlock().getLocation()).equals("")) {
            if(isInHolyLand(event.getBlock().getLocation())) {
                event.setCancelled(false);
                return;
            }
            event.setCancelled(true);
            return;
        }
        String city = "";
        for (Map.Entry<String, Location> entry : safeSpaces.entrySet()) {
            if(entry.getValue() != null) {
                if ((block.getLocation().getBlockX() < entry.getValue().getBlockX() + 25) && (block.getLocation().getBlockX() > entry.getValue().getBlockX() - 25)) {
                    if ((block.getLocation().getBlockZ() < entry.getValue().getBlockZ() + 25) && (block.getLocation().getBlockZ() > entry.getValue().getBlockZ() - 25)) {
                        city = entry.getKey();
                        break;
                    }
                }
            }
        }
        if(city.equals("")) {
            event.setCancelled(true);
            return;
        }
        Location match = block.getChunk().getBlock(0,0,0).getLocation();
        Location check = safeSpaces.get(city);
        for(int i = 0; i < 9; i++) {
            if(i == 0) {
                Location matchToo = new Location(player.getWorld(),check.getBlockX() -10, 0, check.getBlockZ() -10, 0 , 0);
                if(matchToo.getChunk().getBlock(0,0,0).getLocation().equals(match)) {
                    if(hasLandDeed(player, city, "NW") || hasLandDeed(player, city, "C")) {
                        event.setCancelled(false);
                        return;
                    } else {
                        player.sendMessage("you have no deed");
                        event.setCancelled(true);
                        return;
                    }
                }
            }
            if(i == 1) {
                Location matchToo = new Location(player.getWorld(),check.getBlockX(), 0, check.getBlockZ() -10, 0 , 0);
                if(matchToo.getChunk().getBlock(0,0,0).getLocation().equals(match)) {
                    if(hasLandDeed(player, city, "N") || hasLandDeed(player, city, "C")) {
                        event.setCancelled(false);
                        return;
                    } else {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
            if(i == 2) {
                Location matchToo = new Location(player.getWorld(),check.getBlockX() + 10, 0, check.getBlockZ() -10, 0 , 0);
                if(matchToo.getChunk().getBlock(0,0,0).getLocation().equals(match)) {
                    if(hasLandDeed(player, city, "NE") || hasLandDeed(player, city, "C")) {
                        event.setCancelled(false);
                        return;
                    } else {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
            if(i == 3) {
                Location matchToo = new Location(player.getWorld(),check.getBlockX() - 10, 0, check.getBlockZ(), 0 , 0);
                if(matchToo.getChunk().getBlock(0,0,0).getLocation().equals(match)) {
                    if(hasLandDeed(player, city, "W") || hasLandDeed(player, city, "C")) {
                        event.setCancelled(false);
                        return;
                    } else {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
            check = safeSpaces.get(city);
            if(i == 4) {
                Location matchToo = new Location(player.getWorld(),check.getBlockX(), 0, check.getBlockZ(), 0 , 0);
                if(matchToo.getChunk().getBlock(0,0,0).getLocation().equals(match)) {
                    if(hasLandDeed(player, city, "C")) {
                        event.setCancelled(false);
                        return;
                    } else {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
            check = safeSpaces.get(city);
            if(i == 5) {
                Location matchToo = new Location(player.getWorld(),check.getBlockX() +10, 0, check.getBlockZ(), 0 , 0);
                if(matchToo.getChunk().getBlock(0,0,0).getLocation().equals(match)) {
                    if(hasLandDeed(player, city, "E") || hasLandDeed(player, city, "C")) {
                        event.setCancelled(false);
                        return;
                    } else {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
            check = safeSpaces.get(city);
            if(i == 6) {
                Location matchToo = new Location(player.getWorld(),check.getBlockX() - 10, 0, check.getBlockZ() + 10, 0 , 0);
                if(matchToo.getChunk().getBlock(0,0,0).getLocation().equals(match)) {
                    if(hasLandDeed(player, city, "SW") || hasLandDeed(player, city, "C")) {
                        event.setCancelled(false);
                        return;
                    } else {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
            check = safeSpaces.get(city);
            if(i == 7) {
                Location matchToo = new Location(player.getWorld(),check.getBlockX(), 0, check.getBlockZ() + 10, 0 , 0);
                if(matchToo.getChunk().getBlock(0,0,0).getLocation().equals(match)) {
                    if(hasLandDeed(player, city, "S") || hasLandDeed(player, city, "C")) {
                        event.setCancelled(false);
                        return;
                    } else {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
            check = safeSpaces.get(city);
            if(i == 8) {
                Location matchToo = new Location(player.getWorld(),check.getBlockX() + 10, 0, check.getBlockZ() + 10, 0 , 0);
                if(matchToo.getChunk().getBlock(0,0,0).getLocation().equals(match)) {
                    if(hasLandDeed(player, city, "SE") || hasLandDeed(player, city, "C")) {
                        event.setCancelled(false);
                        return;
                    } else {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
        }

        event.setCancelled(true);
        player.sendMessage("no placing here");
    }

    private boolean hasLandDeed(Player player, String city, String Location) {
        ItemStack[] inventory = landOwnership.get(player.getUniqueId().toString());
        for(int i = 0; i < inventory.length; i++) {
            if(inventory[i] == null) {

            } else
            if(inventory[i].getItemMeta().getDisplayName().equals(city + " " + Location)) {
                return true;
            }
        }
        return false;
    }

    private boolean istooclose(Location location) {
        for (Map.Entry<String, Location> entry : safeSpaces.entrySet()) {
            if(entry.getValue() != null) {
                if ((location.getBlockX() < entry.getValue().getBlockX() + 40) && (location.getBlockX() > entry.getValue().getBlockX() - 40)) {
                    if ((location.getBlockZ() < entry.getValue().getBlockZ() + 40) && (location.getBlockZ() > entry.getValue().getBlockZ() - 40)) {
                        return true;
                    }
                }
            }
        }
        return false;
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

}
