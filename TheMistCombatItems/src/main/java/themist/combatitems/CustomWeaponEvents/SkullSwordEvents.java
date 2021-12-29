package themist.combatitems.CustomWeaponEvents;

import org.bukkit.entity.DragonFireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.ShulkerBullet;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import themist.combatitems.CustomWeapons;

public class SkullSwordEvents implements Listener {

    public CustomWeapons CW;

    public SkullSwordEvents(CustomWeapons C) {
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
        if(!event.getItem().getItemMeta().getDisplayName().equals("Skull Sword")) {
            return;
        }
        Player player = event.getPlayer();
        player.launchProjectile(WitherSkull.class, player.getLocation().getDirection().multiply(2));

    }

}
