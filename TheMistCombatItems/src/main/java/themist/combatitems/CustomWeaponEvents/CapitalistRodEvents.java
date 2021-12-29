package themist.combatitems.CustomWeaponEvents;


import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;
import themist.combatitems.CustomWeapons;

public class CapitalistRodEvents implements Listener {

    public CustomWeapons CW;

    public CapitalistRodEvents(CustomWeapons C) {
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
        if(!event.getItem().getItemMeta().getDisplayName().equals("Capitalist Rod")) {
            return;
        }
        Player player = event.getPlayer();
        Vector vector = player.getLocation().getDirection().multiply(2);
        Vector vector1 = rotateZ(vector, Math.PI/16);
        player.launchProjectile(FishHook.class, vector1);
        Vector vector3 = rotateZ(vector, -Math.PI/8);
        player.launchProjectile(FishHook.class, vector3);
    }

    private Vector rotateZ(Vector vector, double angle) { // angle in radians
        float x1 = (float)(vector.getX() * Math.cos(angle) - vector.getZ() * Math.sin(angle));
        float y1 = (float)(vector.getX() * Math.sin(angle) + vector.getZ() * Math.cos(angle)) ;
        vector.setX(x1);
        vector.setZ(y1);
        return vector;

    }

}
