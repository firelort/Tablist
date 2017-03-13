package de.firelort.tablist;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Tablist extends JavaPlugin implements Listener{
	
	Scoreboard sb;
	
	@Override
	public void onEnable() {
		loadConfig();
		sb = Bukkit.getScoreboardManager().getNewScoreboard();
		
		sb.registerNewTeam("00Rang1");
		sb.registerNewTeam("00Rang2");
		sb.registerNewTeam("00Rang3");
		sb.registerNewTeam("00Rang4");
		sb.registerNewTeam("00Rang5");
		sb.registerNewTeam("00Rang6");
		sb.registerNewTeam("00Rang7");
		sb.registerNewTeam("00Rang8");
		sb.registerNewTeam("00Rang9");
		sb.registerNewTeam("00Rang10");
		sb.registerNewTeam("00Rang11");
		sb.registerNewTeam("00Rang12");
		sb.registerNewTeam("00Rang13");
		sb.registerNewTeam("00Rang14");
		sb.registerNewTeam("00Rang15");
		sb.registerNewTeam("00Rang16");
		
		String prefix1 = getConfig().getString("Config.prefix1");
		String prefix2 = getConfig().getString("Config.prefix2");
		String prefix3 = getConfig().getString("Config.prefix3");
		String prefix4 = getConfig().getString("Config.prefix4");
		String prefix5 = getConfig().getString("Config.prefix5");
		String prefix6 = getConfig().getString("Config.prefix6");
		String prefix7 = getConfig().getString("Config.prefix7");
		String prefix8 = getConfig().getString("Config.prefix8");
		String prefix9 = getConfig().getString("Config.prefix9");
		String prefix10 = getConfig().getString("Config.prefix10");
		String prefix11 = getConfig().getString("Config.prefix11");
		String prefix12 = getConfig().getString("Config.prefix12");
		String prefix13 = getConfig().getString("Config.prefix13");
		String prefix14 = getConfig().getString("Config.prefix14");
		String prefix15 = getConfig().getString("Config.prefix15");
		String prefix16 = getConfig().getString("Config.prefix16");
		
		sb.getTeam("00Rang1").setPrefix(ChatColor.translateAlternateColorCodes('&', prefix1));
		sb.getTeam("00Rang2").setPrefix(ChatColor.translateAlternateColorCodes('&', prefix2));
		sb.getTeam("00Rang3").setPrefix(ChatColor.translateAlternateColorCodes('&', prefix3));
		sb.getTeam("00Rang4").setPrefix(ChatColor.translateAlternateColorCodes('&', prefix4));
		sb.getTeam("00Rang5").setPrefix(ChatColor.translateAlternateColorCodes('&', prefix5));
		sb.getTeam("00Rang6").setPrefix(ChatColor.translateAlternateColorCodes('&', prefix6));
		sb.getTeam("00Rang7").setPrefix(ChatColor.translateAlternateColorCodes('&', prefix7));
		sb.getTeam("00Rang8").setPrefix(ChatColor.translateAlternateColorCodes('&', prefix8));
		sb.getTeam("00Rang9").setPrefix(ChatColor.translateAlternateColorCodes('&', prefix9));
		sb.getTeam("00Rang10").setPrefix(ChatColor.translateAlternateColorCodes('&', prefix10));
		sb.getTeam("00Rang11").setPrefix(ChatColor.translateAlternateColorCodes('&', prefix11));
		sb.getTeam("00Rang12").setPrefix(ChatColor.translateAlternateColorCodes('&', prefix12));
		sb.getTeam("00Rang13").setPrefix(ChatColor.translateAlternateColorCodes('&', prefix13));
		sb.getTeam("00Rang14").setPrefix(ChatColor.translateAlternateColorCodes('&', prefix14));
		sb.getTeam("00Rang15").setPrefix(ChatColor.translateAlternateColorCodes('&', prefix15));
		sb.getTeam("00Rang16").setPrefix(ChatColor.translateAlternateColorCodes('&', prefix16));
		
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	public void onDissable() {
		
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		setPrefix(e.getPlayer());
	}
	@SuppressWarnings("deprecation")
	private void setPrefix(Player p){
		String team = "";
		
		if(p.hasPermission("tablist.rank1")){
			team = "00Rang1";
		}
		else if(p.hasPermission("tablist.rank2")){
			team = "00Rang2";
		}
		else if(p.hasPermission("tablist.rank3")){
			team = "00Rang3";
		}
		else if(p.hasPermission("tablist.rank4")){
			team = "00Rang4";
		}
		else if(p.hasPermission("tablist.rank5")){
			team = "00Rang5";
		}
		else if(p.hasPermission("tablist.rank6")){
			team = "00Rang6";
		}
		else if(p.hasPermission("tablist.rank7")){
			team = "00Rang7";
		}
		else if(p.hasPermission("tablist.rank8")){
			team = "00Rang8";
		}
		else if(p.hasPermission("tablist.rank9")){
			team = "00Rang9";
		}
		else if(p.hasPermission("tablist.rank10")){
			team = "00Rang10";
		}
		else if(p.hasPermission("tablist.rank11")){
			team = "00Rang11";
		}
		else if(p.hasPermission("tablist.rank12")){
			team = "00Rang12";
		}
		else if(p.hasPermission("tablist.rank13")){
			team = "00Rang13";
		}
		else if(p.hasPermission("tablist.rank14")){
			team = "00Rang14";
		}
		else if(p.hasPermission("tablist.rank15")){
			team = "00Rang15";
		}
		else {
			team = "00Rang16";
		}
		
		sb.getTeam(team).addPlayer(p);
		p.setDisplayName(sb.getTeam(team).getPrefix() + p.getDisplayName());
		
		for(Player all : Bukkit.getOnlinePlayers()){
			all.setScoreboard(sb);
		}
	}
	
	public void loadConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

}
