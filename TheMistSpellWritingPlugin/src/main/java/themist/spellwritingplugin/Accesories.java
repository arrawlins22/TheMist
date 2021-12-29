package themist.spellwritingplugin;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Accesories {

    public ItemStack createAccessory(String name) {
        ItemStack Accessory = new ItemStack(Material.TOTEM_OF_UNDYING, 1);
        ItemMeta meta = Accessory.getItemMeta();
        meta.setDisplayName(name);
        Accessory.setItemMeta(meta);
        Accessory.addUnsafeEnchantment(Enchantment.LUCK, 1);
        return Accessory;
    }

}
