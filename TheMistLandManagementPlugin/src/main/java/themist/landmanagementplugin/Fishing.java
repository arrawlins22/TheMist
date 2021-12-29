package themist.landmanagementplugin;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

public class Fishing implements Listener {

    @EventHandler
    public void onFish(PlayerFishEvent event) {
        if (event.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)) {
            ItemStack caught = (ItemStack) event.getCaught();
            if(caught.getType().equals(Material.LEATHER)) {
                caught.setType(Material.IRON_ORE);
            }
            if(!caught.getType().equals(Material.COD) && !caught.getType().equals(Material.SALMON)) {
                caught.setType(Material.COD);
                return;
            }
        }



    }


}
