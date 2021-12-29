package themist.landmanagementplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import themist.landmanagementplugin.Harvesting.Chopping;
import themist.landmanagementplugin.Harvesting.Mining;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class LandManagementPlugin extends JavaPlugin implements Listener {

    SafeSpaceEvents sse = new SafeSpaceEvents(new HashMap<String, Location>(),  new HashMap<String, ItemStack[]>());

    @Override
    public void onEnable() {
        // Plugin startup logic

        this.saveDefaultConfig();

        if(this.getConfig().contains("data")) {
            this.restoreLand();
        }
        if(this.getConfig().contains("spaces")) {
            this.restoreSpaces();
        }
        this.getServer().getPluginManager().registerEvents(sse, this);
        this.getServer().getPluginManager().registerEvents(this,this);
        this.getServer().getPluginManager().registerEvents(new Chopping(sse.safeSpaces),this);
        this.getServer().getPluginManager().registerEvents(new Mining(sse.safeSpaces),this);
        this.getServer().getPluginManager().registerEvents(new Fishing(), this);
        this.getServer().getPluginManager().registerEvents(new BeaconEvents(), this);
        initiateMisting();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if(!sse.landOwnership.isEmpty()) {
            this.saveLand();
        }
        if(!sse.safeSpaces.isEmpty()) {
            this.saveSpaces();
        }
    }

    public void saveLand() {
        for (Map.Entry<String, ItemStack[]> entry : sse.landOwnership.entrySet()) {
            this.getConfig().set("data." + entry.getKey(), entry.getValue());
        }
        this.saveConfig();
    }

    public void restoreLand() {
        this.getConfig().getConfigurationSection("data").getKeys(false).forEach(key -> {
            @SuppressWarnings("unchecked")
            ItemStack[] content = ((List<ItemStack>) this.getConfig().get("data." + key)).toArray(new ItemStack[27]);
            sse.landOwnership.put(key, content);
        });
    }

    public void saveSpaces() {
        for (Map.Entry<String, Location> entry : sse.safeSpaces.entrySet()) {
            this.getConfig().set("spaces." + entry.getKey(), entry.getValue());
        }
        this.saveConfig();
    }

    public void restoreSpaces() {
        this.getConfig().getConfigurationSection("spaces").getKeys(false).forEach(key -> {
        String name = key;
        Location location = (Location) this.getConfig().get("spaces." + key);
        sse.safeSpaces.put(name, location);
        });

    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(label.equalsIgnoreCase("land")) {
            if(!(sender instanceof Player)) {
                sender.sendMessage("no");
                return true;
            }
            Player player = (Player) sender;
            Inventory inventory = Bukkit.createInventory(player, 27, player.getName() + "'s Land");
            if(sse.landOwnership.containsKey(player.getUniqueId().toString())) {
                inventory.setContents(sse.landOwnership.get(player.getUniqueId().toString()));
            }
            player.openInventory(inventory);
            return true;
        }
        return false;
    }

    @EventHandler
    public void onGUIClose(InventoryCloseEvent event) {
        if(event.getView().getTitle().contains(event.getPlayer().getName() + "'s Land")) {
            sse.landOwnership.put(event.getPlayer().getUniqueId().toString(), event.getInventory().getContents());
        }
    }

    public void initiateMisting() {
        Bukkit.getScheduler().runTaskTimer(this,() -> {
            for(int i = 0; i < this.getServer().getOnlinePlayers().size(); i++) {
                if(((Player) this.getServer().getOnlinePlayers().toArray()[i]).hasPotionEffect(PotionEffectType.SLOW)) {
                    ((Player) this.getServer().getOnlinePlayers().toArray()[i]).damage(1);
                }

            }
        }, 1, 80);
    }
}
