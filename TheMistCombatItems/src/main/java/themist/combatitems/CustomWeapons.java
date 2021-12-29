package themist.combatitems;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class CustomWeapons {

    public ItemStack SkullSword;
    public ItemStack AvalancheSword;
    public ItemStack CrushingAxe;
    public ItemStack SwordOfTheSea;
    public ItemStack CapitalistRod;
    public ItemStack RunningShoes;


    public void init() {
        createSkullSword();
        createAvalanche();
        createCrushingAxe();
        createSwordOfTheSea();
        createCapitalistRod();
        createRunningShoes();
    }

    private ItemStack createSkullSword() {
        ItemStack Accessory = new ItemStack(Material.NETHERITE_SWORD, 1);
        ItemMeta meta = Accessory.getItemMeta();
        meta.setDisplayName("Skull Sword");
        meta.setUnbreakable(true);
        Accessory.setItemMeta(meta);
        Accessory.addUnsafeEnchantment(Enchantment.LOYALTY, 20);
        SkullSword = Accessory;
        return Accessory;
    }

    private ItemStack createAvalanche() {
        ItemStack Accessory = new ItemStack(Material.DIAMOND_SWORD, 1);
        ItemMeta meta = Accessory.getItemMeta();
        meta.setDisplayName("Avalanche Sword");
        meta.setUnbreakable(true);
        Accessory.setItemMeta(meta);
        Accessory.addUnsafeEnchantment(Enchantment.FROST_WALKER, 20);
        AvalancheSword = Accessory;
        return Accessory;
    }

    private ItemStack createCrushingAxe() {
        ItemStack Accessory = new ItemStack(Material.DIAMOND_AXE, 1);
        ItemMeta meta = Accessory.getItemMeta();
        meta.setDisplayName("Crushing Axe");
        meta.setUnbreakable(true);
        Accessory.setItemMeta(meta);
        Accessory.addUnsafeEnchantment(Enchantment.DURABILITY, 20);
        CrushingAxe = Accessory;
        return Accessory;
    }

    private ItemStack createSwordOfTheSea() {
        ItemStack Accessory = new ItemStack(Material.IRON_SWORD, 1);
        ItemMeta meta = Accessory.getItemMeta();
        meta.setDisplayName("Sword of the Sea");
        meta.setUnbreakable(true);
        Accessory.setItemMeta(meta);
        Accessory.addUnsafeEnchantment(Enchantment.CHANNELING, 20);
        SwordOfTheSea = Accessory;
        return Accessory;
    }

    private ItemStack createCapitalistRod() {
        ItemStack Accessory = new ItemStack(Material.FISHING_ROD, 1);
        ItemMeta meta = Accessory.getItemMeta();
        meta.setDisplayName("Capitalist Rod");
        meta.setUnbreakable(true);
        Accessory.setItemMeta(meta);
        CapitalistRod = Accessory;
        return Accessory;
    }

    private ItemStack createRunningShoes() {
        ItemStack Accessory = new ItemStack(Material.LEATHER_BOOTS, 1);
        ItemMeta meta = Accessory.getItemMeta();
        meta.setDisplayName("Running Shoes");
        meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(UUID.randomUUID(), "Speed", .05, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
        Accessory.setItemMeta(meta);
        RunningShoes = Accessory;
        return Accessory;
    }

}
