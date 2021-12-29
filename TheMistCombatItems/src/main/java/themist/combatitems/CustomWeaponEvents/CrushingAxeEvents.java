package themist.combatitems.CustomWeaponEvents;

import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import themist.combatitems.CustomWeapons;

public class CrushingAxeEvents implements Listener {

    public CustomWeapons CW;

    public CrushingAxeEvents(CustomWeapons C) {
        CW = C;
    }

    @EventHandler
    public void onSwing(EntityDamageByEntityEvent event) {
        if(!(event.getEntity() instanceof Player)) {
            return;
        }

        if(!(event.getDamager() instanceof Player)) {
            return;
        }
        Player attacker = (Player) event.getDamager();
        if(!attacker.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("Crushing Axe")) {
            return;
        }
        Player player = (Player) event.getEntity();
        if(player.isBlocking()) {
            if(player.getInventory().getItemInOffHand().getType().equals(Material.SHIELD)) {
                player.getWorld().dropItem(player.getLocation(), player.getInventory().getItemInOffHand());
                player.getInventory().setItemInOffHand(new ItemStack(Material.AIR, 1));
            } else if(player.getInventory().getItemInMainHand().getType().equals(Material.SHIELD)) {
                player.getWorld().dropItem(player.getLocation(), player.getInventory().getItemInMainHand());
                player.getInventory().setItemInMainHand(new ItemStack(Material.AIR, 1));
            }
        }

    }
}
