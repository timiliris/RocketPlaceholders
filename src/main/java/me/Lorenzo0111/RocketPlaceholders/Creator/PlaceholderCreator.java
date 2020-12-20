package me.Lorenzo0111.RocketPlaceholders.Creator;


import me.Lorenzo0111.RocketPlaceholders.RocketPlaceholders;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;


public class PlaceholderCreator extends PlaceholderExpansion {

    /*

    Plugin by Lorenzo0111

     */

    private final RocketPlaceholders plugin;

    public PlaceholderCreator(RocketPlaceholders plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "rp";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Lorenzo0111";
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String identifier) {


        ConfigurationSection config = plugin.getConfig().getConfigurationSection("placeholders");

        for (String key : config.getKeys(false)) {
            if (identifier.equals(config.getString(key + ".placeholder"))) {
                if (config.getString(key + ".permission") == null) {
                    return ChatColor.translateAlternateColorCodes('&', config.getString(key + ".text"));
                } else if (config.getString(key + ".text_with_permission") == null) {
                    return ChatColor.translateAlternateColorCodes('&', config.getString(key + ".text"));
                } else {
                    if (player.getPlayer().hasPermission(config.getString(key + ".permission"))) {
                        return ChatColor.translateAlternateColorCodes('&', config.getString(key + ".text_with_permission"));
                    }

                    return ChatColor.translateAlternateColorCodes('&', config.getString(key + ".text"));
                }
            }
        }
        return null;
    }
}