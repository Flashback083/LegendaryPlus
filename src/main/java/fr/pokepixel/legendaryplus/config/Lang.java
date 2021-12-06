package fr.pokepixel.legendaryplus.config;

import fr.pokepixel.legendaryplus.Legendaryplus;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;

public class Lang {

    private static final String CATEGORY_LANG = "lang";
    private static String legendlist = "The legendary is {legendname}, and the nearest playerg is {player} in the biome {biome}.";
    private static String ultrabeastlist = "The Ultrabeast is {ubname}, and the nearest player is {player} in the biome {biome}.";
    private static String lastpoke = "{x}) Name : {pokemon} - Date : {days} day(s), {hours} hour(s), {minutes} minute(s) ago. [{state}] {playerph}";
    private static String titletrad = "A {legendname} has spawned around you {player}!";
    private static String messagetotheplayer = "A {legendname} has spawned around you {player}!";
    private static String messagetoallplayers = "A {legendname} has spawned around {player}!";

    //State
    private static String alive = "Alive";
    private static String captured = "Captured";
    private static String defeated = "Defeated";
    private static String despawned = "Despawned";

    //Player placeholder
    private static String playerph = "- {player}";

    //-----------------------------------------------------//
  
    
    public static void readConfig() {
        Configuration cfg = Legendaryplus.lang;
        try {
            cfg.load();
            initGeneralConfig(cfg);    
        } catch (Exception e1) {
            Legendaryplus.logger.log(Level.ERROR, "Problem loading config file!", e1);
        } finally {
            if (cfg.hasChanged()) {
                cfg.save();
            }
        }
       
    }

    private static void initGeneralConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_LANG, "Language configuration");
        legendlist = cfg.getString("legendlist", CATEGORY_LANG, legendlist, "/legend command, {legendname} will be replaced by the legendary name and {player} by the nearest player");
        ultrabeastlist = cfg.getString("ultrabeastlist", CATEGORY_LANG, ultrabeastlist, "/ultrabeast command, {ubname} will be replaced by the ultrabeast name and {player} by the nearest player");
        lastpoke = cfg.getString("lastpoke", CATEGORY_LANG, lastpoke, "/lastlegendary, /lastultrabeast & /lastshiny command, {pokemon} will be replaced by the legendary/UB/Shiny name and {days},{hours},{minutes} by the time elapsed");
        titletrad = cfg.getString("titletrad", CATEGORY_LANG, titletrad, "Which message displayed when titlemsg set to true for subtitle");
        messagetotheplayer = cfg.getString("messagetotheplayer", CATEGORY_LANG, messagetotheplayer, "Which message displayed when msgplayer set to true");
        messagetoallplayers = cfg.getString("messagetoallplayers", CATEGORY_LANG, messagetoallplayers, "Which message displayed when allplayers set to true");

        alive = cfg.getString("alive", CATEGORY_LANG, alive, "Placeholder for {state}");
        captured = cfg.getString("captured", CATEGORY_LANG, captured, "Placeholder for {state}");
        defeated = cfg.getString("defeated", CATEGORY_LANG, defeated, "Placeholder for {state}");
        despawned = cfg.getString("despawned", CATEGORY_LANG, despawned, "Placeholder for {state}");

        playerph = cfg.getString("playerph", CATEGORY_LANG, playerph, "Placeholder for player which catch or defeat the pokemon use {playerph} in your lastlegend line");
    }
    

    

}
