package themist.combatitems.CustomWeaponEvents;

import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import themist.combatitems.CustomWeapons;

public class SwordOfTheSeaEvents implements Listener {
    public CustomWeapons CW;

    public SwordOfTheSeaEvents(CustomWeapons C) {
        CW = C;
    }

    @EventHandler
    public void onSwing(PlayerInteractEvent event) {
        if(event.getPlayer().getAttackCooldown() != 1.0) {
            return;
        }
        if(!event.getAction().equals(Action.LEFT_CLICK_BLOCK) && !event.getAction().equals(Action.LEFT_CLICK_AIR)) {
            return;
        }
        if(!event.getItem().getItemMeta().getDisplayName().equals("Sword of the Sea")) {
            return;
        }
        Player player = event.getPlayer();
        player.launchProjectile(Trident.class, player.getLocation().getDirection().multiply(1.25));

    }

}
