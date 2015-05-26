package me.Dustin.com;

import java.util.HashMap;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class VoteMain extends JavaPlugin implements Listener {

	public static Economy economy = null;
	String Prefix = ChatColor.translateAlternateColorCodes('&', "&2&lVirus Kits");
	private VoteShopGUI menu;
	public static Permission permission = null;

	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&m----------------------------"));
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&a[VirusKits] has been enabled!"));
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&m----------------------------"));
		setupPermissions();
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		this.menu = new VoteShopGUI(this);
		if (!setupEconomy()) {
			getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", new Object[] { getDescription().getName() }));
			getServer().getPluginManager().disablePlugin(this);
		}
	}
	
	private boolean setupEconomy()
	{
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		economy = (Economy)rsp.getProvider();
		return economy != null;
	}

	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&m----------------------------"));
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c[VirusKits] has been disabled!"));
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&m----------------------------"));
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		saveConfig();
	}

	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(Permission.class);
		if (permissionProvider != null) {
			permission = (Permission)permissionProvider.getProvider();
		}
		return permission != null;
	}

	public void addPerm(String perm, Player player) {
		permission.playerAdd(player, perm);
	}


	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player p = (Player) sender;
		if ((cmd.getName().equalsIgnoreCase("kit")) || (cmd.getName().equalsIgnoreCase("kits"))) {
			menu.show(p);
		}
		return false;
	}
}
