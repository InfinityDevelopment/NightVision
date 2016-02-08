package com.infinity.night_vision;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class NightVision extends JavaPlugin{
	
	public final HashMap<Player, ArrayList<PotionEffect>> hm = new HashMap<Player, ArrayList<PotionEffect>>();
	
	public void onEnable() {
		Bukkit.getServer().getLogger().info("[NightVision] NightVision plugin by xXInfinityXx has been enabled.");
	}
	
	public void onDisable() {
		Bukkit.getServer().getLogger().info("[NightVision] NightVision plugin by xXInfinityXx has been disabled.");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	  {
		if(!(sender instanceof Player)){
			sender.sendMessage(ChatColor.DARK_RED + "You must be a player to toggle your night vision.");
			return true;
		}
		Player p = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("nv")){
			if(args.length == 0){
				p.sendMessage(ChatColor.DARK_RED + "Usage: /nv toggle, /nv check {playername}, /nv give {playername}, /nv take {playername}.");
				return true;
			}
			if(args[0].equalsIgnoreCase("toggle")){
				if(p.hasPermission("NightVision.toggle")){
					if(!hm.containsKey(p)){
						hm.put((Player) p, null);
						p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 5));
						p.sendMessage(ChatColor.GREEN + "Night Vision Enabled!");
						return true;
					}else if(hm.containsKey(p)){
						hm.remove(p);
						p.removePotionEffect(PotionEffectType.NIGHT_VISION);
						p.sendMessage(ChatColor.DARK_RED + "Night Vision Disabled!");
						return true;
					}
			 }else if(!p.hasPermission("NightVision.toggle")){
				 p.sendMessage(ChatColor.DARK_RED + "You do not have permission to use this command!");
				 return true;
			 }
			}if(args[0].equalsIgnoreCase("check")){
				if(p.hasPermission("NightVision.check")){
					Player target = Bukkit.getServer().getPlayer(args[1]);
					if (target == null) {
						sender.sendMessage(ChatColor.DARK_RED + "Could not check if that player has night vision, because they are not online.");
						return true;
					}
					if(!hm.containsKey(target)){
						p.sendMessage("" + ChatColor.DARK_RED + target + " does not have Night Vision.");
						return true;
					}else if(hm.containsKey(target)){
						p.sendMessage("" + ChatColor.GREEN + target + " has Night Vision!");
						return true;
					}
				}else if(!p.hasPermission("NightVision.check")){
					p.sendMessage(ChatColor.DARK_RED + "You do not have permission to use this command!");
					 return true;
				}
			}if(args[0].equalsIgnoreCase("give")){
				if(p.hasPermission("NightVision.give")){
					Player target = Bukkit.getServer().getPlayer(args[1]);
					if (target == null) {
						sender.sendMessage(ChatColor.DARK_RED + "Could not give " + target + " night vision, because they are not online.");
						return true;
					}
					if(!hm.containsKey(target)){
						hm.put((Player) target, null);
						p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 5));
						p.sendMessage("" + ChatColor.GREEN + target + " has been given Night Vision!");
						return true;
					}else if(hm.containsKey(target)){
						p.sendMessage("" + ChatColor.DARK_RED + target + " already has Night Vision!");
						return true;
					}	
				}else if(!p.hasPermission("NightVision.give")){
					p.sendMessage(ChatColor.DARK_RED + "You do not have permission to use this command!");
					return true;
				}
			}if(args[0].equalsIgnoreCase("take")){
				if(p.hasPermission("NightVision.take")){
					Player target = Bukkit.getServer().getPlayer(args[1]);
					if (target == null) {
						sender.sendMessage(ChatColor.DARK_RED + "Could not take away " + args[1] + "'s night vision, because they are not online.");
						return true;
					}
					if(!hm.containsKey(target)){
						p.sendMessage("" + ChatColor.DARK_RED + target + " does not have Night Vision to take away!");
						return true;
					}else if(hm.containsKey(target)){
						hm.remove(target);
						p.removePotionEffect(PotionEffectType.NIGHT_VISION);
						p.sendMessage("" + ChatColor.GREEN + target + "'s Night Vision has been taken away.");
						return true;
					}	
				}else if(!p.hasPermission("NightVision.take")){
					p.sendMessage(ChatColor.DARK_RED + "You do not have permission to use this command!");
					return true;
				}
			}if(!args[0].isEmpty()){
				sender.sendMessage(ChatColor.DARK_RED + "Undefined Usage. Please type /nv for usages.");
			}
		}
		return false;
	  }
 
}
