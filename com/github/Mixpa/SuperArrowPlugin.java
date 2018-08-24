package com.github.Mixpa;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;


public class SuperArrowPlugin extends JavaPlugin{
	public HashMap<String, Boolean> isEnable;
	File config = new File(getDataFolder(), "config.yml");
	File message = new File(getDataFolder(), "message.yml");
	public YamlConfiguration Config;
	public YamlConfiguration Message;
	@Override
	public void onEnable() {
		isEnable = new HashMap<String, Boolean>();
	
			loadConfig();
		
		this.Config = YamlConfiguration.loadConfiguration(config);
		this.Message = YamlConfiguration.loadConfiguration(message);
		
		CommandExecutor superArrowCommand = new SuperArrowCommand(this);
		getCommand("SuperArrow").setExecutor(superArrowCommand);
		
		getServer().getPluginManager().registerEvents(new ArrowShootListener(this), this);
		
		getWorldGuard();
		
		getLogger().info("SuperArrow已经加载!");
		getLogger().info("欢迎使用本插件!本插件作者为"+this.getDescription().getAuthors()+"版本号为"+this.getDescription().getVersion());
	}
	public void onDisable(){
		getLogger().info("SuperArrow已经关闭!");
	}
	public void loadConfig(){
		if(!getDataFolder().exists()){
			getDataFolder().mkdir();
		}
		if(!config.exists()){
			saveDefaultConfig();
		}
		if(!message.exists()){
			try {
				message.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			saveResource("message.yml", true);
		}
	}
	public boolean isWorldGuardEnable(){
		if(this.getServer().getPluginManager().getPlugin("WorldGuard") != null){
			return true;
		}else return false;
	}
	public WorldGuardPlugin getWorldGuard(){
		if(isWorldGuardEnable()){
			Plugin wgp = this.getServer().getPluginManager().getPlugin("WorldGuard");
			if((wgp instanceof WorldGuardPlugin)) {
				return (WorldGuardPlugin)wgp;
			}
			else return null;
		}
		return null;
	}
	public void sendMessage(Player p, String path){
		String s = ChatColor.translateAlternateColorCodes('&', this.Message.getString(path));
		p.sendMessage(s);
	}
}
