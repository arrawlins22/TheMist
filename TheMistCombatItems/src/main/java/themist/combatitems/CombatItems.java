package themist.combatitems;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import themist.combatitems.CustomWeaponEvents.*;


public final class CombatItems extends JavaPlugin implements Listener {
    private CustomWeapons CW;
    @Override
    public void onEnable() {
        // Plugin startup logic
        CW = new CustomWeapons();
        CW.init();
        this.getServer().getPluginManager().registerEvents(new SkullSwordEvents(CW), this);
        this.getServer().getPluginManager().registerEvents(new AvalancheSwordEvents(CW), this);
        this.getServer().getPluginManager().registerEvents(new CrushingAxeEvents(CW), this);
        this.getServer().getPluginManager().registerEvents(new SwordOfTheSeaEvents(CW), this);
        this.getServer().getPluginManager().registerEvents(new CapitalistRodEvents(CW), this);
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(label.equalsIgnoreCase("weapons")) {
            if(!(sender instanceof Player)) {
                sender.sendMessage("no");
                return true;
            }
            Player player = (Player) sender;
            player.getInventory().addItem(CW.RunningShoes);
        }
        return false;
    }

}
