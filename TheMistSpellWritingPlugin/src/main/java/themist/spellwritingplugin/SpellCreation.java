package themist.spellwritingplugin;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class SpellCreation implements Listener {

    @EventHandler
    public void giveBasicSpell(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(player.getInventory().contains(new ItemStack(Material.WRITTEN_BOOK, 1))) {
           return;
        }
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
        BookMeta meta = (BookMeta) book.getItemMeta();
        meta.setTitle("Baby's First Spell");
        meta.setAuthor("God");
        book.setItemMeta(meta);
        player.getInventory().addItem(book);
    }

    @EventHandler
    public void makeSpellTier1(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if(!event.getItem().getType().equals(Material.WRITABLE_BOOK) || !(event.getClickedBlock().getType().equals(Material.ENCHANTING_TABLE)) ||
            !event.getClickedBlock().getLocation().add(0,-1,0).getBlock().getType().equals(Material.IRON_BLOCK)) {
            return;
        }
        BookMeta meta = (BookMeta) item.getItemMeta();
        if(CorrectBookTier1(meta, "Jett", "Malphite", "Maokai")) {
            item.setType(Material.WRITTEN_BOOK);
            meta.setTitle("Dash Tier 1");
            meta.setAuthor("God");
            item.setItemMeta(meta);
        }
        if(CorrectBookTier1(meta, "Peter Rabbit", "Roger Rabbit", "Bugs Bunny")) {
            item.setType(Material.WRITTEN_BOOK);
            meta.setTitle("Hops Tier 1");
            meta.setAuthor("God");
            item.setItemMeta(meta);
        }
        if(CorrectBookTier1(meta, "Sonic", "Usane Bolt", "Road Runner")) {
            item.setType(Material.WRITTEN_BOOK);
            meta.setTitle("Speed Tier 1");
            meta.setAuthor("God");
            item.setItemMeta(meta);
        }
        if(CorrectBookTier1(meta, "Leonardo", "Donatello", "Raphael")) {
            item.setType(Material.WRITTEN_BOOK);
            meta.setTitle("Shield Shell Tier 1");
            meta.setAuthor("God");
            item.setItemMeta(meta);
        }
        if(CorrectBookTier1(meta, "20/20", "Bat", "Goggles")) {
            item.setType(Material.WRITTEN_BOOK);
            meta.setTitle("Night Vision Tier 1");
            meta.setAuthor("God");
            item.setItemMeta(meta);
        }
        if(CorrectBookTier1(meta, "He needs some milk!", "Nurse Joy", "Chug Jug")) {
            item.setType(Material.WRITTEN_BOOK);
            meta.setTitle("Regeneration Tier 1");
            meta.setAuthor("God");
            item.setItemMeta(meta);
        }
        if(CorrectBookTier1(meta, "Ghast", "Mario", "Pitbull")) {
            item.setType(Material.WRITTEN_BOOK);
            meta.setTitle("Fireball Tier 1");
            meta.setAuthor("God");
            item.setItemMeta(meta);
        }
        if(CorrectBookTier1(meta, "Abra", "Ralts", "Natu")) {
            item.setType(Material.WRITTEN_BOOK);
            meta.setTitle("Teleport Tier 1");
            meta.setAuthor("God");
            item.setItemMeta(meta);
        }
        if(CorrectBookTier1(meta, "Zeus", "Thunder", "Lightning")) {
            item.setType(Material.WRITTEN_BOOK);
            meta.setTitle("Lightning Tier 1");
            meta.setAuthor("God");
            item.setItemMeta(meta);
        }
        if(CorrectBookTier1(meta, "Hawk-Eye", "Legolas", "Green Arrow")) {
            item.setType(Material.WRITTEN_BOOK);
            meta.setTitle("Arrow Tier 1");
            meta.setAuthor("God");
            item.setItemMeta(meta);
        }
    }
    @EventHandler
    public void makeSpellTier2(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if(!event.getItem().getType().equals(Material.WRITABLE_BOOK) || !(event.getClickedBlock().getType().equals(Material.ENCHANTING_TABLE)) ||
                !event.getClickedBlock().getLocation().add(0,-1,0).getBlock().getType().equals(Material.DIAMOND_BLOCK)) {
            return;
        }
        BookMeta meta = (BookMeta) item.getItemMeta();
        if(CorrectBookTier2(meta, "Jett", "Malphite", "Maokai")) {
            item.setType(Material.WRITTEN_BOOK);
            meta.setTitle("Dash Tier 2");
            meta.setAuthor("God");
            item.setItemMeta(meta);
        }
        if(CorrectBookTier2(meta, "Peter Rabbit", "Roger Rabbit", "Bugs Bunny")) {
            item.setType(Material.WRITTEN_BOOK);
            meta.setTitle("Hops Tier 2");
            meta.setAuthor("God");
            item.setItemMeta(meta);
        }
        if(CorrectBookTier2(meta, "Sonic", "Usane Bolt", "Road Runner")) {
            item.setType(Material.WRITTEN_BOOK);
            meta.setTitle("Speed Tier 2");
            meta.setAuthor("God");
            item.setItemMeta(meta);
        }
        if(CorrectBookTier2(meta, "Leonardo", "Donatello", "Raphael")) {
            item.setType(Material.WRITTEN_BOOK);
            meta.setTitle("Shield Shell Tier 2");
            meta.setAuthor("God");
            item.setItemMeta(meta);
        }
        if(CorrectBookTier2(meta, "20/20", "Bat", "Goggles")) {
            item.setType(Material.WRITTEN_BOOK);
            meta.setTitle("Night Vision Tier 2");
            meta.setAuthor("God");
            item.setItemMeta(meta);
        }
        if(CorrectBookTier2(meta, "He needs some milk!", "Nurse Joy", "Chug Jug")) {
            item.setType(Material.WRITTEN_BOOK);
            meta.setTitle("Regeneration Tier 2");
            meta.setAuthor("God");
            item.setItemMeta(meta);
        }
        if(CorrectBookTier2(meta, "Ghast", "Mario", "Pitbull")) {
            item.setType(Material.WRITTEN_BOOK);
            meta.setTitle("Fireball Tier 2");
            meta.setAuthor("God");
            item.setItemMeta(meta);
        }
        if(CorrectBookTier2(meta, "Abra", "Ralts", "Natu")) {
            item.setType(Material.WRITTEN_BOOK);
            meta.setTitle("Teleport Tier 2");
            meta.setAuthor("God");
            item.setItemMeta(meta);
        }
        if(CorrectBookTier2(meta, "Zeus", "Thunder", "Lightning")) {
            item.setType(Material.WRITTEN_BOOK);
            meta.setTitle("Lightning Tier 2");
            meta.setAuthor("God");
            item.setItemMeta(meta);
        }
        if(CorrectBookTier2(meta, "Hawk-Eye", "Legolas", "Green Arrow")) {
            item.setType(Material.WRITTEN_BOOK);
            meta.setTitle("Arrow Tier 2");
            meta.setAuthor("God");
            item.setItemMeta(meta);
        }

    }
    @EventHandler
    public void makeSpellTier3(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if(!event.getItem().getType().equals(Material.WRITABLE_BOOK) || !(event.getClickedBlock().getType().equals(Material.ENCHANTING_TABLE)) ||
                !event.getClickedBlock().getLocation().add(0,-1,0).getBlock().getType().equals(Material.EMERALD_BLOCK)) {
            return;
        }
        BookMeta meta = (BookMeta) item.getItemMeta();
        if(CorrectBookTier3(meta, "Jett", "Malphite", "Maokai")) {
            item.setType(Material.WRITTEN_BOOK);
            meta.setTitle("Dash Tier 3");
            meta.setAuthor("God");
            item.setItemMeta(meta);
        }
        if(CorrectBookTier3(meta, "Peter Rabbit", "Roger Rabbit", "Bugs Bunny")) {
            item.setType(Material.WRITTEN_BOOK);
            meta.setTitle("Hops Tier 3");
            meta.setAuthor("God");
            item.setItemMeta(meta);
        }
        if(CorrectBookTier3(meta, "Sonic", "Usane Bolt", "Road Runner")) {
            item.setType(Material.WRITTEN_BOOK);
            meta.setTitle("Speed Tier 3");
            meta.setAuthor("God");
            item.setItemMeta(meta);
        }
        if(CorrectBookTier3(meta, "Leonardo", "Donatello", "Raphael")) {
            item.setType(Material.WRITTEN_BOOK);
            meta.setTitle("Shield Shell Tier 3");
            meta.setAuthor("God");
            item.setItemMeta(meta);
        }
        if(CorrectBookTier3(meta, "20/20", "Bat", "Goggles")) {
            item.setType(Material.WRITTEN_BOOK);
            meta.setTitle("Night Vision Tier 3");
            meta.setAuthor("God");
            item.setItemMeta(meta);
        }
        if(CorrectBookTier3(meta, "He needs some milk!", "Nurse Joy", "Chug Jug")) {
            item.setType(Material.WRITTEN_BOOK);
            meta.setTitle("Regeneration Tier 3");
            meta.setAuthor("God");
            item.setItemMeta(meta);
        }
        if(CorrectBookTier3(meta, "Ghast", "Mario", "Pitbull")) {
            item.setType(Material.WRITTEN_BOOK);
            meta.setTitle("Fireball Tier 3");
            meta.setAuthor("God");
            item.setItemMeta(meta);
        }
        if(CorrectBookTier3(meta, "Abra", "Ralts", "Natu")) {
            item.setType(Material.WRITTEN_BOOK);
            meta.setTitle("Teleport Tier 3");
            meta.setAuthor("God");
            item.setItemMeta(meta);
        }
        if(CorrectBookTier3(meta, "Zeus", "Thunder", "Lightning")) {
            item.setType(Material.WRITTEN_BOOK);
            meta.setTitle("Lightning Tier 3");
            meta.setAuthor("God");
            item.setItemMeta(meta);
        }
        if(CorrectBookTier3(meta, "Hawk-Eye", "Legolas", "Green Arrow")) {
            item.setType(Material.WRITTEN_BOOK);
            meta.setTitle("Arrow Tier 3");
            meta.setAuthor("God");
            item.setItemMeta(meta);
        }
    }

    private boolean CorrectBookTier1(BookMeta meta, String s1, String s2, String s3) {
        String page1 = meta.getPage(1);
        if(page1.equals(s1) || page1.equals(s2) || page1.equals(s3)) {
            return true;
        }
        return false;
    }
    private boolean CorrectBookTier2(BookMeta meta, String s1, String s2, String s3) {
        String page1 = meta.getPage(1);
        String page2 = meta.getPage(2);
        if(page1.equals(s1) && (page2.equals(s2) || page2.equals(s3))) {
            return true;
        }
        if(page1.equals(s2) && (page2.equals(s1) || page2.equals(s3))) {
            return true;
        }
        if(page1.equals(s3) && (page2.equals(s2) || page2.equals(s1))) {
            return true;
        }
        return false;
    }
    private boolean CorrectBookTier3(BookMeta meta, String s1, String s2, String s3) {
        String page1 = meta.getPage(1);
        String page2 = meta.getPage(2);
        String page3 = meta.getPage(3);
        if(page1.equals(s1) && page2.equals(s2) && page3.equals(s3)) {
            return true;
        }
        if(page1.equals(s1) && page2.equals(s3) && page3.equals(s2)) {
            return true;
        }
        if(page1.equals(s2) && page2.equals(s1) && page3.equals(s3)) {
            return true;
        }
        if(page1.equals(s2) && page2.equals(s3) && page3.equals(s1)) {
            return true;
        }
        if(page1.equals(s3) && page2.equals(s2) && page3.equals(s1)) {
            return true;
        }
        if(page1.equals(s3) && page2.equals(s1) && page3.equals(s2)) {
            return true;
        }
        return false;
    }
}
