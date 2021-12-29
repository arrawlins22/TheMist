package themist.miningandlogging;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class MiningEvents implements Listener {

    @EventHandler
    public void WoodPickBreakables(BlockBreakEvent event) {
        if(!event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.WOODEN_PICKAXE)) {
            return;
        }

    }
    @EventHandler
    public void stonePickBreakables(BlockBreakEvent event) {
        if(!event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.STONE_PICKAXE)) {
            return;
        }

    }
    @EventHandler
    public void goldPickBreakables(BlockBreakEvent event) {
        if(!event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.GOLDEN_PICKAXE)) {
            return;
        }

    }
    @EventHandler
    public void ironPickBreakables(BlockBreakEvent event) {
        if(!event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.IRON_PICKAXE)) {
            return;
        }

    }
    @EventHandler
    public void diamondPickBreakables(BlockBreakEvent event) {
        if(!event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_PICKAXE)) {
            return;
        }

    }
    @EventHandler
    public void netheritePickBreakables(BlockBreakEvent event) {
        if(!event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.NETHERITE_PICKAXE)) {
            return;
        }
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if(!block.getType().equals(Material.STONE) && !block.getType().equals(Material.DEEPSLATE_IRON_ORE)
                && !block.getType().equals(Material.DEEPSLATE_COAL_ORE) && !block.getType().equals(Material.DEEPSLATE_COPPER_ORE)
                && !block.getType().equals(Material.DEEPSLATE_GOLD_ORE) && !block.getType().equals(Material.DEEPSLATE_REDSTONE_ORE)
                && !block.getType().equals(Material.DEEPSLATE_LAPIS_ORE) && !block.getType().equals(Material.DEEPSLATE_DIAMOND_ORE)
                && !block.getType().equals(Material.DEEPSLATE_EMERALD_ORE))
        {
            event.setCancelled(true);
            return;
        }

    }



}
