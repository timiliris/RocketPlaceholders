package me.Lorenzo0111.RocketPlaceholders;


import org.bukkit.OfflinePlayer;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;


public class PlaceholderCreator extends PlaceholderExpansion {

    /*

    Plugin by Lorenzo0111


     */

    private Placeholders plugin;

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
        return "1.0";
    }

    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public boolean persist(){
        return true;
    }

    public PlaceholderCreator(Placeholders plugin){
        this.plugin = plugin;
    }

    @Override
    public String onRequest(OfflinePlayer player, String identifier){


        ConfigurationSection config = plugin.getConfig().getConfigurationSection("placeholders");

        for (String key : config.getKeys(false)) {
            if(identifier.equals(config.getString(key + ".placeholder"))) {
                return config.getString(key + ".text");
            }
        }
        return null;
    }
}




