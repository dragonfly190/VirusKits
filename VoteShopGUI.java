package me.Dustin.com;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class VoteShopGUI
implements Listener
{
	HashMap<String, Integer> Starter = new HashMap<String, Integer>();
	private static Inventory inv;
	VoteMain plugin;
	String Price = ChatColor.GREEN + "" + ChatColor.BOLD + "Price: ";
	String Buy = ChatColor.RED + " -";
	String Prefix = ChatColor.translateAlternateColorCodes('&', "&2&lVirus Kits");

	public VoteShopGUI(VoteMain instance)
	{
		this.plugin = instance;
		setup(instance);
	}
	
	public static VoteMain plugin1;

	public void VoteShopGUI1(VoteMain plugin) {
		plugin = this.plugin;
	}

	int task;

	public void setCooldownLength(Player player, int time,
			HashMap<String, Integer> hashmap) {
		hashmap.put(player.getName(), time);
	}

	public int getTimeLeft(Player player, HashMap<String, Integer> hashmap) {
		int time = hashmap.get(player.getName());
		return time;
	}

	@SuppressWarnings("deprecation")
	public void startCooldown(final Player player,
			final HashMap<String, Integer> hashmap) {
		task = Bukkit.getServer().getScheduler()
				.scheduleSyncRepeatingTask(plugin, new BukkitRunnable() {
					public void run() {
						int time = hashmap.get(player.getName());
						if (time != 0) {
							hashmap.put(player.getName(), time - 1);
						} else {
							hashmap.remove(player.getName());
							Bukkit.getServer().getScheduler().cancelTask(task);
						}
					}
				}, 0L, 20L);
	}

	public void setup(VoteMain instance)
	{
		inv = Bukkit.getServer().createInventory(null, 18, ChatColor.translateAlternateColorCodes('&', "&2&lVirus Kits"));
		//ItemStacks
		ItemStack town = new ItemStack(Material.IRON_SWORD);
		ItemMeta townmeta = town.getItemMeta();
		townmeta.setDisplayName(ChatColor.GREEN + "Starter");
		townmeta.setLore(Arrays.asList(new String[] { ChatColor.YELLOW + "Member Kit",
				ChatColor.AQUA + "Iron Helmet",
				ChatColor.AQUA + "Iron Chestplate",
				ChatColor.AQUA + "Iron Leggings",
				ChatColor.AQUA + "Iron Boots",
				ChatColor.AQUA + "Diamond Sword",
				ChatColor.AQUA + "Creeper Egg",
				ChatColor.AQUA + "Steak (x16)",
				ChatColor.AQUA + "Obsidian (x32)",
				ChatColor.AQUA + "Oak Wood (x16)" }));
		town.setItemMeta(townmeta);

		//Builders
		ItemStack builder = new ItemStack(Material.DIAMOND_CHESTPLATE);
		ItemMeta buildermeta = builder.getItemMeta();
		buildermeta.setDisplayName(ChatColor.GREEN + "Infected");
		buildermeta.setLore(Arrays.asList(new String[] { ChatColor.YELLOW + "Infected Rank",
				ChatColor.AQUA + "Diamond Helmet",
				ChatColor.AQUA + "Diamond Chestplate",
				ChatColor.AQUA + "Diamond Leggings",
				ChatColor.AQUA + "Diamond Boots",
				ChatColor.AQUA + "Diamond Sword (Sharpness 1, Unbreaking 1)",
				ChatColor.AQUA + "Diamond Pickaxe (Efficiency 1, Unbreaking 1)",
				ChatColor.AQUA + "Steak (x64)",
				ChatColor.AQUA + "Diamond (x5)",
				ChatColor.AQUA + "Golden Apple (x5)",
				ChatColor.AQUA + "Glowstone (x16)",
				ChatColor.AQUA + "Strength Potion (3:00) (x2)",
				ChatColor.AQUA + "Swiftness Potion (3:00 (x2))",
				ChatColor.AQUA + "Creeper Egg"}));
		town.setItemMeta(townmeta);
		builder.setItemMeta(buildermeta);

		inv.setItem(1, town);
		inv.setItem(2, builder);

		Bukkit.getServer().getPluginManager().registerEvents(this, instance);
	}

	public static ItemStack createItem1(Material material, String displayname) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(displayname);
		item.setItemMeta(meta);
		return item;
	}

	public void show(Player p) {
		p.openInventory(inv);
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (!e.getInventory().getName().equalsIgnoreCase(inv.getName())) return;
		if (e.getCurrentItem().getItemMeta() == inv) return;

		Player p = (Player)e.getWhoClicked();
		if ((e.getCurrentItem().equals(null)) ||
				(e.getCurrentItem().getType().equals(Material.AIR)) ||
				(!e.getCurrentItem().hasItemMeta())) {
			p.closeInventory();
			return;
		}
		if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Starter")) {
			if (Starter.containsKey(p.getName())) {
                p.sendMessage(ChatColor.RED
                        + "You are still on cooldown for another "
                        + getTimeLeft(p, Starter) + " seconds!");
                p.closeInventory();
            } else {
			p.getInventory().addItem(new ItemStack(Material.IRON_HELMET));
			p.getInventory().addItem(new ItemStack(Material.IRON_CHESTPLATE));
			p.getInventory().addItem(new ItemStack(Material.IRON_LEGGINGS));
			p.getInventory().addItem(new ItemStack(Material.IRON_BOOTS));
			p.getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD));
			p.getInventory().addItem(new ItemStack(Material.MONSTER_EGG, 1,(byte)50));
			p.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 16));
			p.getInventory().addItem(new ItemStack(Material.OBSIDIAN, 32));
			p.getInventory().addItem(new ItemStack(Material.WOOD, 16));

			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&m----------------------------"));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aYou have selected the Starter Kit!"));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&m----------------------------"));
			p.closeInventory();
			setCooldownLength(p, 86400, Starter);
			startCooldown(p, Starter);
		}

		if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Infected")) {
			//Helmet
			ItemStack np = new ItemStack(Material.DIAMOND_HELMET);
			ItemMeta npm = np.getItemMeta();
			npm.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
			npm.addEnchant(Enchantment.DURABILITY, 1, true);
			np.setItemMeta(npm);

			//ChestPlate
			ItemStack dc = new ItemStack(Material.DIAMOND_CHESTPLATE);
			ItemMeta dcm = dc.getItemMeta();
			dcm.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
			dcm.addEnchant(Enchantment.DURABILITY, 1, true);
			dc.setItemMeta(dcm);
			//Leggings
			ItemStack dl = new ItemStack(Material.DIAMOND_LEGGINGS);
			ItemMeta dlm = dl.getItemMeta();
			dlm.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
			dlm.addEnchant(Enchantment.DURABILITY, 1, true);
			dl.setItemMeta(dlm);
			//Boots
			ItemStack db = new ItemStack(Material.DIAMOND_BOOTS);
			ItemMeta dbm = db.getItemMeta();
			dbm.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
			dbm.addEnchant(Enchantment.DURABILITY, 1, true);
			db.setItemMeta(dbm);

			//Sword
			ItemStack ds = new ItemStack(Material.DIAMOND_SWORD);
			ItemMeta dsm = ds.getItemMeta();
			dsm.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
			dsm.addEnchant(Enchantment.DURABILITY, 1, true);
			dsm.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&5&oInfect Players"));
			ds.setItemMeta(dsm);

			p.getInventory().addItem(np);
			p.getInventory().addItem(dc);
			p.getInventory().addItem(dl);
			p.getInventory().addItem(db);
			p.getInventory().addItem(ds);
			p.getInventory().addItem(new ItemStack(Material.MONSTER_EGG, 1,(byte)50));
			p.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 16));
			p.getInventory().addItem(new ItemStack(Material.OBSIDIAN, 32));
			p.getInventory().addItem(new ItemStack(Material.WOOD, 16));

			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&m----------------------------"));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aYou have selected the Infected Kit!"));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&m----------------------------"));
			p.closeInventory();
		}
	}
}
}
