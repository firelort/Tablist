package de.hartlab.tablist;

import de.hartlab.tablist.listener.ServerListener;
import jdk.internal.jline.internal.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.IllegalPluginAccessException;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Set;

public class Tablist extends JavaPlugin {
    private Scoreboard scoreBoard;
    private ConfigurationSection cs;
    private Server server;

    @Override
    public void onEnable() {
        server = getServer();
        saveDefaultConfig();
        FileConfiguration config = getConfig();
        cs = config.getConfigurationSection("teams");
        startupRoutine();
    }

    private void startupRoutine() {
        if (setupScoreboard()) {
            try {
                Bukkit.getServer().getPluginManager().registerEvents(new ServerListener(this, scoreBoard, cs), this);
            } catch (IllegalPluginAccessException e) {
                server.getLogger().warning("[Tablist] Their is an error in your configuration.");
                server.getPluginManager().disablePlugin(this);
                return;
            }
            PluginDescriptionFile pluginDescriptionFile = this.getDescription();
            server.getLogger().info(String.format("[Tablist] Version %s enabled.", pluginDescriptionFile.getVersion()));
        } else {
            server.getLogger().warning("Due to an initialization error the plugin does not work and has been deactivated.");
            server.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        PluginDescriptionFile pluginDescriptionFile = this.getDescription();
        server.getLogger().info(String.format("[Tablist] Version %s disabled.", pluginDescriptionFile.getVersion()));
    }

    private boolean setupScoreboard() {
        try {
            scoreBoard = Bukkit.getScoreboardManager().getNewScoreboard();
            setupTeams();
        } catch (Exception e) {
            getLogger().warning(e.toString());
            return false;
        }
        return true;
    }

    private void setupTeams() throws Exception {
        Set<String> configKeys = cs.getKeys(false);
        String prefix, priority;
        Integer intPriority;
        for (String key : configKeys) {
            prefix = cs.getString(key + ".prefix");
            intPriority = cs.getInt(key + ".priority");
            if (intPriority <= 9999) {
                priority = String.format("%04d", intPriority);
            } else { // Max accepted value is 9999, set all values greater to 9999.
                priority = "9999";
            }
            createNewTeam(key + priority, prefix);
        }
    }

    private void createNewTeam(String teamName, String prefix) throws Exception {
        Team team = scoreBoard.registerNewTeam(teamName);
        team.setPrefix(Color(prefix));
        team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
    }

    private String Color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}