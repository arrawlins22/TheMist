package themist.combatitems.CustomWeaponEvents;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;
import themist.combatitems.CustomWeapons;

public class AvalancheSwordEvents implements Listener {

    public CustomWeapons CW;

    public AvalancheSwordEvents(CustomWeapons C) {
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
        if(!event.getItem().getItemMeta().getDisplayName().equals("Avalanche Sword")) {
            return;
        }
        Player player = event.getPlayer();
        Vector vector = player.getLocation().getDirection().multiply(2);
        player.launchProjectile(Snowball.class, vector);
        Vector vector1 = rotateZ(vector, Math.PI/32);
        player.launchProjectile(Snowball.class, vector1);
        Vector vector2 = rotateZ(vector,  Math.PI/32);
        player.launchProjectile(Snowball.class, vector2);
        Vector vector3 = rotateZ(vector, -Math.PI/16);
        vector3 = rotateZ(vector, -Math.PI/32);
        player.launchProjectile(Snowball.class, vector3);
        Vector vector4 = rotateZ(vector, -Math.PI/32);
        player.launchProjectile(Snowball.class, vector4);
    }

    @EventHandler
    public void onSnowballDamage(ProjectileHitEvent event) {
        if(!(event.getEntity() instanceof Snowball)) {
            return;
        }
        if(!(event.getHitEntity() instanceof LivingEntity)) {
            return;
        }
        LivingEntity LE = (LivingEntity) event.getHitEntity();
        LE.damage(2);
    }

    @EventHandler
    public void onProjectileland(ProjectileHitEvent event) {
        if(!(event.getHitEntity() instanceof LivingEntity)) {
            event.getEntity().remove();
        }
    }

    private Vector rotateZ(Vector vector,double angle) { // angle in radians
        float x1 = (float)(vector.getX() * Math.cos(angle) - vector.getZ() * Math.sin(angle));
        float y1 = (float)(vector.getX() * Math.sin(angle) + vector.getZ() * Math.cos(angle)) ;
        vector.setX(x1);
        vector.setZ(y1);
        return vector;

    }
}
