package de.hartlab.tablist.listener;

import de.hartlab.tablist.Tablist;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.*;

public class ServerListener implements Listener {
    private Tablist plugin;
    private Scoreboard scoreboard;
    private ConfigurationSection configurationSection;
    private Map<String, String> permissions;

    public ServerListener(Tablist plugin, Scoreboard scoreboard, ConfigurationSection cs) {
        this.plugin = plugin;
        this.scoreboard = scoreboard;
        this.configurationSection = cs;
        this.permissions = new LinkedHashMap<String, String>();
        getPermission();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        for(Map.Entry<String, String> entry: permissions.entrySet()) {
            if (p.hasPermission(entry.getKey())) {
                scoreboard.getTeam(entry.getValue()).addEntry(p.getDisplayName());
                break;
            }
        }

        p.setScoreboard(scoreboard);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        Set<Team> teams = scoreboard.getTeams();

        for (Team team: teams) {
            if (scoreboard.getTeam(team.getDisplayName()).hasEntry(p.getDisplayName())) {
                scoreboard.getTeam(team.getDisplayName()).removeEntry(p.getDisplayName());
                break;
            }
        }
    }

    private void getPermission() {
        Set<String> configKeys = configurationSection.getKeys(false);
        Map<Integer, Map.Entry<String, String>> myPermissionMap = new TreeMap<Integer, Map.Entry<String, String>>();
        String permission;
        Integer priority;
        for(String key : configKeys) {
            permission = configurationSection.getString(key + ".permission");
            priority = configurationSection.getInt(key + ".priority");
            //Test if permission and priority is set
            if (permission == null || priority  == null) {
                this.plugin.getServer().getLogger().warning("[Tablist] Skipping team " + key + ", because permission or priority are missing. " + priority);

            } else {
               if(myPermissionMap.containsKey(priority)) {
                   this.plugin.getServer().getLogger().warning("[Tablist] Disabling Plugin due to an configuration error. Two teams can't have the same priority.");
                   this.plugin.getServer().getPluginManager().disablePlugin(plugin);
                   return;
               }

               String stringPriority;
               if (priority <= 9999) {
                   stringPriority = String.format("%04d", priority);
               } else {
                   stringPriority = "9999";
               }

               myPermissionMap.put(priority, new AbstractMap.SimpleEntry<String, String>(permission, key + stringPriority));
            }
        }

        for (Map.Entry<String, String> entry : myPermissionMap.values()) {
            String value = entry.getKey();
            if (permissions.containsKey(value)) {
                this.plugin.getServer().getLogger().warning("[Tablist] Disabling Plugin due to an configuration error. Two teams can't have the same permission.");
                this.plugin.getServer().getPluginManager().disablePlugin(plugin);
                return;
            } else {
                permissions.put(value, entry.getValue());
            }
        }
    }

}
