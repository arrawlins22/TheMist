package themist.landmanagementplugin;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LandTokenItems {


    public ItemStack createLandDeed(String city, String Location) {
        ItemStack landDeed = new ItemStack(Material.HEART_OF_THE_SEA, 1);
        ItemMeta meta = landDeed.getItemMeta();
        meta.setDisplayName(city + " " + Location);
        landDeed.setItemMeta(meta);
        return landDeed;
    }

}
