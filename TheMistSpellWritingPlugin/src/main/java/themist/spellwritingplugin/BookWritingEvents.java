package themist.spellwritingplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import java.util.Collections;

public class BookWritingEvents implements Listener {
    //REMOVE LATER
    @EventHandler
    public void noReadingInAlpha(PlayerInteractEvent event) {
        if(event.getItem().getType().equals(Material.WRITTEN_BOOK)) {
            if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                event.setCancelled(true);
                event.getPlayer().sendMessage("No looking at the Sacred Text in Development");
                return;
            }
        }
    }
    private Plugin plugin;
    public BookWritingEvents(Plugin plug) {
        plugin = plug;
    }

    //Spell Mana cost
    private static int JumpSpellCost = 10;
    private static int LightningSpellCost = 20;
    private static int TeleportSpellCost = 30;
    private static int FireballSpellCost = 20;
    private static int DashSpellCost = 10;
    private static int SpeedSpellCost = 10;
    private static int TurtleSpellCost = 30;
    private static int VisionSpellCost = 20;
    private static int RegenSpellCost = 20;
    private static int ArrowSpellCost = 5;


    private boolean hasEnoughMana(Player player, int levels) {
        if(player.getLevel() < levels) {
            player.sendMessage("not enough mana");
            return false;
        } else {
            player.setLevel(player.getLevel() - levels);
         return true;
        }
    }
    private boolean spellCheck(Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();

        if (!item.getType().equals(Material.AIR)) {
            if (item.getType().equals(Material.WRITTEN_BOOK)) {
                return true;
            }
        }
        return false;
    }

    @EventHandler
    public void JumpSpell(PlayerInteractEvent event) {
        if(!event.getAction().equals(Action.LEFT_CLICK_AIR) && !event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            return;
        }
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if(!spellCheck(player)) {
            return;
        }
        BookMeta meta = (BookMeta) item.getItemMeta();
        if(!meta.getAuthor().equals("God")) {
            player.sendMessage("This spell is a fraud");
            return;
        }
            //spell
        if(meta.getTitle().equals("Hops Tier 1")) {
            if(!hasEnoughMana(player, JumpSpellCost)) {
                return;
            }
            player.setVelocity(player.getVelocity().setY(1));
        }
        if(meta.getTitle().equals("Hops Tier 2")) {
            if(!hasEnoughMana(player, JumpSpellCost)) {
                return;
            }
            player.setVelocity(player.getVelocity().setY(2));
        }
        if(meta.getTitle().equals("Hops Tier 3")) {
            if(!hasEnoughMana(player, JumpSpellCost-5)) {
                return;
            }
            player.setVelocity(player.getVelocity().setY(3));
        }
    }
    @EventHandler
    public void DashSpell(PlayerInteractEvent event) {
        if(!event.getAction().equals(Action.LEFT_CLICK_AIR) && !event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            return;
        }
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if(!spellCheck(player)) {
            return;
        }
        BookMeta meta = (BookMeta) item.getItemMeta();
        if(!meta.getAuthor().equals("God")) {
            player.sendMessage("This spell is a fraud");
            return;
        }
            //spell
        if(meta.getTitle().equals("Dash Tier 1")) {
            if(!hasEnoughMana(player, DashSpellCost)) {
                return;
            }
            Location loc = player.getLocation().subtract(player.getLineOfSight(Collections.singleton(Material.AIR), 20).get(20).getLocation());
            loc.setY(-.1);
            player.setVelocity(loc.toVector().normalize().multiply(-2));
        }
        if(meta.getTitle().equals("Dash Tier 2")) {
            if(!hasEnoughMana(player, DashSpellCost)) {
                return;
            }
            Location loc = player.getLocation().subtract(player.getLineOfSight(Collections.singleton(Material.AIR), 20).get(20).getLocation());
            loc.setY(-.1);
            player.setVelocity(loc.toVector().normalize().multiply(-3));
        }
        if(meta.getTitle().equals("Dash Tier 3")) {
            if(!hasEnoughMana(player, DashSpellCost)) {
                return;
            }
            Location loc = player.getLocation().subtract(player.getLineOfSight(Collections.singleton(Material.AIR), 20).get(20).getLocation());
            loc.setY(-.1);
            player.setVelocity(loc.toVector().normalize().multiply(-4));
        }
    }
    @EventHandler
    public void LightningSpell(PlayerInteractEvent event) {
        if(!event.getAction().equals(Action.LEFT_CLICK_AIR) && !event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            return;
        }
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if(!spellCheck(player)) {
            return;
        }
        BookMeta meta = (BookMeta) item.getItemMeta();
        if(!meta.getAuthor().equals("God")) {
            player.sendMessage("This spell is a fraud");
            return;
        }
        //Spells
        if(meta.getTitle().equals("Lightning Tier 1")) {
            if(!hasEnoughMana(player, LightningSpellCost)) {
                return;
            }
            player.getWorld().strikeLightning(player.getTargetBlockExact(100).getLocation());
        }
        if(meta.getTitle().equals("Lightning Tier 2")) {
            if(!hasEnoughMana(player, LightningSpellCost)) {
                return;
            }
            Location loc = player.getTargetBlockExact(100).getLocation();
            player.getWorld().strikeLightning(loc);
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                player.getWorld().strikeLightning(loc);
            },20);

        }
        if(meta.getTitle().equals("Lightning Tier 3")) {
            if (!hasEnoughMana(player, LightningSpellCost)) {
                return;
            }
            Location loc = player.getTargetBlockExact(100).getLocation();
            player.getWorld().strikeLightning(loc);
            Location loc1 = loc.add(3, 0, 3);
            player.getWorld().strikeLightning(loc1);
            Location loc2 = loc.add(-6, 0, 0);
            player.getWorld().strikeLightning(loc2);
            Location loc3 = loc.add(0, 0, -6);
            player.getWorld().strikeLightning(loc3);
            Location loc4 = loc.add(0, 0, 6);
            player.getWorld().strikeLightning(loc4);
        }

    }
    @EventHandler
    public void FireballSpell(PlayerInteractEvent event) {
        if(!event.getAction().equals(Action.LEFT_CLICK_AIR) && !event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            return;
        }
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if(!spellCheck(player)) {
            return;
        }
        BookMeta meta = (BookMeta) item.getItemMeta();
        if(!meta.getAuthor().equals("God")) {
            player.sendMessage("This spell is a fraud");
            return;
        }

        //SPELL
        if(meta.getTitle().equals("Fireball Tier 1")) {
            if(!hasEnoughMana(player, FireballSpellCost)) {
                return;
            }
            player.launchProjectile(Fireball.class, player.getLocation().getDirection().multiply(4));

        }
        if(meta.getTitle().equals("Fireball Tier 2")) {
            if(!hasEnoughMana(player, FireballSpellCost)) {
                return;
            }
            player.launchProjectile(Fireball.class, player.getLocation().getDirection().multiply(4));
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                player.launchProjectile(Fireball.class, player.getLocation().getDirection().multiply(4));
            },10);
        }
        if(meta.getTitle().equals("Fireball Tier 3")) {
            if(!hasEnoughMana(player, FireballSpellCost)) {
                return;
            }
            player.launchProjectile(Fireball.class, player.getLocation().getDirection().multiply(4));
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                player.launchProjectile(Fireball.class, player.getLocation().getDirection().multiply(4));
            },10);
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                player.launchProjectile(Fireball.class, player.getLocation().getDirection().multiply(4));
            },20);
        }
    }
    @EventHandler
    public void TeleportSpell(PlayerInteractEvent event) {
        if(!event.getAction().equals(Action.LEFT_CLICK_AIR) && !event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            return;
        }
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if(!spellCheck(player)) {
            return;
        }
        BookMeta meta = (BookMeta) item.getItemMeta();
        if(!meta.getAuthor().equals("God")) {
            player.sendMessage("This spell is a fraud");
            return;
        }

        //SPELL
        if(meta.getTitle().equals("Teleport Tier 1")) {
            if(!hasEnoughMana(player, TeleportSpellCost)) {
                return;
            }
            Location loc = player.getLocation();
            player.teleport(player.getTargetBlockExact(25).getLocation().add(0, 1, 0));
            player.teleport(player.getLocation().setDirection(loc.getDirection()));
        }
        if(meta.getTitle().equals("Teleport Tier 2")) {
            if(!hasEnoughMana(player, TeleportSpellCost)) {
                return;
            }
            Location loc = player.getLocation();
            player.teleport(player.getTargetBlockExact(50).getLocation().add(0, 1, 0));
            player.teleport(player.getLocation().setDirection(loc.getDirection()));
        }
        if(meta.getTitle().equals("Teleport Tier 3")) {
            if(!hasEnoughMana(player, TeleportSpellCost)) {
                return;
            }
            Location loc = player.getLocation();
            player.teleport(player.getTargetBlockExact(75).getLocation().add(0, 1, 0));
            player.teleport(player.getLocation().setDirection(loc.getDirection()));
        }
    }

    @EventHandler
    public void SpeedSpell(PlayerInteractEvent event) {
        if(!event.getAction().equals(Action.LEFT_CLICK_AIR) && !event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            return;
        }
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if(!spellCheck(player)) {
            return;
        }
        BookMeta meta = (BookMeta) item.getItemMeta();
        if(!meta.getAuthor().equals("God")) {
            player.sendMessage("This spell is a fraud");
            return;
        }

        //SPELL
        if(meta.getTitle().equals("Speed Tier 1")) {
            if(!hasEnoughMana(player, SpeedSpellCost)) {
                return;
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 1));
        }
        if(meta.getTitle().equals("Speed Tier 2")) {
            if(!hasEnoughMana(player, SpeedSpellCost)) {
                return;
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 300, 2));
        }
        if(meta.getTitle().equals("Speed Tier 3")) {
            if(!hasEnoughMana(player, SpeedSpellCost)) {
                return;
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 300, 2));
            for (int i = 0; i < player.getServer().getOnlinePlayers().size(); i++) {
                Location loc = player.getLocation();
                Player player2 = ((Player) player.getServer().getOnlinePlayers().toArray()[i]);
                Location match = player2.getLocation();
                if ((loc.getBlockX() < match.getBlockX() + 5) && (loc.getBlockX() > match.getBlockX() - 5)) {
                    if ((loc.getBlockZ() < match.getBlockZ() + 40) && (loc.getBlockZ() > match.getBlockZ() - 40)) {
                        player2.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 300, 2));
                    }
                }
            }
        }
    }

    @EventHandler
    public void TurtleSpell(PlayerInteractEvent event) {
        if(!event.getAction().equals(Action.LEFT_CLICK_AIR) && !event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            return;
        }
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if(!spellCheck(player)) {
            return;
        }
        BookMeta meta = (BookMeta) item.getItemMeta();
        if(!meta.getAuthor().equals("God")) {
            player.sendMessage("This spell is a fraud");
            return;
        }

        //SPELL
        if(meta.getTitle().equals("Shield Shell Tier 1")) {
            if(!hasEnoughMana(player, TurtleSpellCost)) {
                return;
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 10));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 10));
        }
        if(meta.getTitle().equals("Shield Shell Tier 2")) {
            if(!hasEnoughMana(player, TurtleSpellCost)) {
                return;
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 10));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 5));
        }
        if(meta.getTitle().equals("Shield Shell Tier 3")) {
            if(!hasEnoughMana(player, TurtleSpellCost)) {
                return;
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 10));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 1));
        }
    }
    @EventHandler
    public void VisionSpell(PlayerInteractEvent event) {
        if(!event.getAction().equals(Action.LEFT_CLICK_AIR) && !event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            return;
        }
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if(!spellCheck(player)) {
            return;
        }
        BookMeta meta = (BookMeta) item.getItemMeta();
        if(!meta.getAuthor().equals("God")) {
            player.sendMessage("This spell is a fraud");
            return;
        }

        //SPELL
        if(meta.getTitle().equals("Night Vision Tier 1")) {
            if(!hasEnoughMana(player, VisionSpellCost)) {
                return;
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1000, 0));
        }
        if(meta.getTitle().equals("Night Vision Tier 2")) {
            if(!hasEnoughMana(player, VisionSpellCost)) {
                return;
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 2000, 0));
        }
        if(meta.getTitle().equals("Night Vision Tier 3")) {
            if(!hasEnoughMana(player, VisionSpellCost)) {
                return;
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 2000, 0));
            for (int i = 0; i < player.getServer().getOnlinePlayers().size(); i++) {
                Location loc = player.getLocation();
                Player player2 = ((Player) player.getServer().getOnlinePlayers().toArray()[i]);
                Location match = player2.getLocation();
                if ((loc.getBlockX() < match.getBlockX() + 5) && (loc.getBlockX() > match.getBlockX() - 5)) {
                    if ((loc.getBlockZ() < match.getBlockZ() + 40) && (loc.getBlockZ() > match.getBlockZ() - 40)) {
                        player2.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 2000, 0));
                    }
                }
            }
        }
    }
    @EventHandler
    public void RegenSpell(PlayerInteractEvent event) {
        if(!event.getAction().equals(Action.LEFT_CLICK_AIR) && !event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            return;
        }
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if(!spellCheck(player)) {
            return;
        }
        BookMeta meta = (BookMeta) item.getItemMeta();
        if(!meta.getAuthor().equals("God")) {
            player.sendMessage("This spell is a fraud");
            return;
        }

        //SPELL
        if(meta.getTitle().equals("Regeneration Tier 1")) {
            if(!hasEnoughMana(player, RegenSpellCost)) {
                return;
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 1));
        }
        if(meta.getTitle().equals("Regeneration Tier 2")) {
            if(!hasEnoughMana(player, RegenSpellCost)) {
                return;
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 1));
        }
        if(meta.getTitle().equals("Regeneration Tier 3")) {
            if(!hasEnoughMana(player, RegenSpellCost)) {
                return;
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 1));
            for (int i = 0; i < player.getServer().getOnlinePlayers().size(); i++) {
                Location loc = player.getLocation();
                Player player2 = ((Player) player.getServer().getOnlinePlayers().toArray()[i]);
                Location match = player2.getLocation();
                if ((loc.getBlockX() < match.getBlockX() + 5) && (loc.getBlockX() > match.getBlockX() - 5)) {
                    if ((loc.getBlockZ() < match.getBlockZ() + 40) && (loc.getBlockZ() > match.getBlockZ() - 40)) {
                            player2.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 1));
                    }
                }
            }

        }
    }
    @EventHandler
    public void ArrowSpell(PlayerInteractEvent event) {
        if(!event.getAction().equals(Action.LEFT_CLICK_AIR) && !event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            return;
        }
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if(!spellCheck(player)) {
            return;
        }
        BookMeta meta = (BookMeta) item.getItemMeta();
        if(!meta.getAuthor().equals("God")) {
            player.sendMessage("This spell is a fraud");
            return;
        }

        //SPELL
        if(meta.getTitle().equals("Baby's First Spell")) {
            if(!hasEnoughMana(player, ArrowSpellCost*4)) {
                return;
            }
            player.launchProjectile(Arrow.class, player.getLocation().getDirection().multiply(2));
        }
        if(meta.getTitle().equals("Arrow Tier 1")) {
            if(!hasEnoughMana(player, ArrowSpellCost)) {
                return;
            }
            player.launchProjectile(Arrow.class, player.getLocation().getDirection().multiply(2));
        }
        if(meta.getTitle().equals("Arrow Tier 2")) {
            if(!hasEnoughMana(player, ArrowSpellCost)) {
                return;
            }
            player.launchProjectile(Arrow.class, player.getLocation().getDirection().multiply(3));
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                player.launchProjectile(Arrow.class, player.getLocation().getDirection().multiply(3));
            },5);
        }
        if(meta.getTitle().equals("Arrow Tier 3")) {
            if(!hasEnoughMana(player, ArrowSpellCost)) {
                return;
            }
            player.launchProjectile(Arrow.class, player.getLocation().getDirection().multiply(4));
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                player.launchProjectile(Arrow.class, player.getLocation().getDirection().multiply(4));
            },5);
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                player.launchProjectile(Arrow.class, player.getLocation().getDirection().multiply(4));
            },10);
        }
    }
}
