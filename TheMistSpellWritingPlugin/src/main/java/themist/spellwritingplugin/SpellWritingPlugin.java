package themist.spellwritingplugin;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SpellWritingPlugin extends JavaPlugin implements Listener {

    private Map<String, ItemStack[]> Accessories;
    private Accesories AccessoryItems = new Accesories();

    @Override
    public void onEnable() {
        // Plugin startup logic
        Accessories = new HashMap<>();;
        this.saveDefaultConfig();
        if(this.getConfig().contains("objects")) {
            restoreAccesories();
        }
        this.getServer().getPluginManager().registerEvents( new BookWritingEvents(this), this);
        this.getServer().getPluginManager().registerEvents( new SpellCreation(), this);
        this.getServer().getPluginManager().registerEvents(this,this);
        startMana();


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if(!Accessories.isEmpty()) {
            this.saveAccesories();
        }

    }

    private void startMana() {
        Bukkit.getScheduler().runTaskTimer(this,() -> {
            for(int i = 0; i < this.getServer().getOnlinePlayers().size(); i++) {
                Player player = (Player) this.getServer().getOnlinePlayers().toArray()[i];
                int maxMana = 100;
                if(hasAccessory(player, "Tear of the Goddess", Material.TOTEM_OF_UNDYING)) {
                    maxMana += 50;
                }
                if(hasAccessory(player, "Warmogs", Material.TOTEM_OF_UNDYING)) {
                    player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(30.0);
                } else {
                    player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20.0);
                }
                int levels = 1;
                if(player.getLevel() < maxMana) {
                    if(hasAccessory(player, "Lost Chapter", Material.TOTEM_OF_UNDYING)) {
                        levels++;
                    }
                    player.giveExpLevels(levels);
                }
            }
        },20,20);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(label.equalsIgnoreCase("accessories")) {
            if(!(sender instanceof Player)) {
                sender.sendMessage("no");
                return true;
            }
            Player player = (Player) sender;
            Inventory inventory = Bukkit.createInventory(player, 9, player.getName() + "'s Accessories");
            if(Accessories.containsKey(player.getUniqueId().toString())) {
                inventory.setContents(Accessories.get(player.getUniqueId().toString()));
            }
            player.openInventory(inventory);
            return true;
        }
        return false;
    }

    @EventHandler
    public void onGUIClose(InventoryCloseEvent event) {
        if(event.getView().getTitle().contains(event.getPlayer().getName() + "'s Accessories")) {
            Accessories.put(event.getPlayer().getUniqueId().toString(), event.getInventory().getContents());
        }
    }

    public void saveAccesories() {
        for (Map.Entry<String, ItemStack[]> entry : Accessories.entrySet()) {
            this.getConfig().set("objects." + entry.getKey(), entry.getValue());
        }
        this.saveConfig();
    }

    public void restoreAccesories() {
        this.getConfig().getConfigurationSection("objects").getKeys(false).forEach(key -> {
            @SuppressWarnings("unchecked")
            ItemStack[] content = ((List<ItemStack>) this.getConfig().get("objects." + key)).toArray(new ItemStack[9]);
            Accessories.put(key, content);
        });
    }
    private boolean hasAccessory(Player player, String name, Material mat) {
        ItemStack[] inventory = Accessories.get(player.getUniqueId().toString());
        for(int i = 0; i < inventory.length; i++) {
            if(inventory[i] == null) {

            } else
            if(inventory[i].getItemMeta().getDisplayName().equals(name) && inventory[i].getType().equals(mat) && inventory[i].containsEnchantment(Enchantment.LUCK)) {
                return true;
            }
        }
        return false;
    }
}
