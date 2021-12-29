package themist.miningandlogging;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class MiningAndLogging extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getServer().getPluginManager().registerEvents(new MiningEvents(), this);
        this.getServer().getPluginManager().registerEvents(new LoggingEvents(), this);
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(label.equalsIgnoreCase("mine")) {
            if(!(sender instanceof Player)) {
                sender.sendMessage("no");
                return true;
            }
            Player player = (Player) sender;
        }
        return false;
    }

}

