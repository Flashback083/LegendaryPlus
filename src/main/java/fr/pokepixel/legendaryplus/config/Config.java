package fr.pokepixel.legendaryplus.config;

import fr.pokepixel.legendaryplus.Legendaryplus;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;

public class Config {

   
    private static final String CATEGORY = "general";
 
    private static String[] blacklist = new String[]{"empty"};
    private static boolean titlemsg = false;
    private static boolean msgplayer = false;
    private static boolean allplayers = false;
    private static int limitleg = 3;
    private static int limitub = 3;
    private static int limitshiny = 3;
    private static int limitboss = 3;

    public static void readConfig() {
        Configuration cfg = Legendaryplus.config;
        try {
            cfg.load();   
            initWhitelistConfig(cfg);
        } catch (Exception e1) {
            Legendaryplus.logger.log(Level.ERROR, "Problem loading config file!", e1);
        } finally {
            if (cfg.hasChanged()) {
                cfg.save();
            }
        }    
    }
  
    private static void initWhitelistConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY, CATEGORY);
        blacklist = cfg.getStringList("blacklist", CATEGORY, blacklist, "Add each legendary or UB you dont want to see appear in /lastlegend & /lastub");
        titlemsg = cfg.getBoolean("titlemsg", CATEGORY, titlemsg, "Set to true to display a title & subtitle on the player which is the nearest of a legendary");
        msgplayer = cfg.getBoolean("msgplayer", CATEGORY, msgplayer, "Set to true to send a message in tchat to the player which is the nearest of the legendary");
        allplayers = cfg.getBoolean("allplayers", CATEGORY, allplayers, "Set to true to send the msgplayer to all players instead of only which is the nearest of the legendary");
        limitleg = cfg.getInt("limitleg",CATEGORY,limitleg,1, 10,"Limit of legendary that will be displayed in /lastlegend");
        limitshiny = cfg.getInt("limitshiny",CATEGORY,limitshiny,1, 10,"Limit of shiny that will be displayed in /lastshiny");
        limitub = cfg.getInt("limitub",CATEGORY,limitub,1, 10,"Limit of ultrabeast that will be displayed in /lastultrabeast");
        limitboss = cfg.getInt("limitboss",CATEGORY,limitboss,1, 10,"Limit of boss that will be displayed in /lastboss");
    }
}
