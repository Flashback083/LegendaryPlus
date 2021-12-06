package fr.pokepixel.legendaryplus;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import fr.pokepixel.legendaryplus.commands.*;
import fr.pokepixel.legendaryplus.config.Config;
import fr.pokepixel.legendaryplus.config.Lang;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Mod(
        modid = Legendaryplus.MOD_ID,
        name = Legendaryplus.MOD_NAME,
        version = Legendaryplus.VERSION,
        serverSideOnly = true,
        dependencies = "required-after:pixelmon",
        acceptableRemoteVersions = "*"
)
public class Legendaryplus {

    public static final String MOD_ID = "legendaryplus";
    public static final String MOD_NAME = "Legendaryplus";
    public static final String VERSION = "7.0.2";

    public static Logger logger;
    public static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static File directory;
    public static Configuration config;
    public static Configuration lang;

    public static File lastlegendary;
    public static File lastultrabeast;
    public static File lastshiny;
    public static File lastboss;

    public static List<String> legendaries = Lists.newArrayList();


    /**
     * This is the instance of your mod as created by Forge. It will never be null.
     */
    @Mod.Instance(MOD_ID)
    public static Legendaryplus INSTANCE;

    /**
     * This is the first initialization event. Register tile entities here.
     * The registry events below will have fired prior to entry to this method.
     */
    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        directory = new File(event.getModConfigurationDirectory(), MOD_NAME);
        directory.mkdir();
        config = new Configuration(new File(directory.getPath(), "legendaryplus.cfg"));
        lang = new Configuration(new File(directory.getPath(), "lang.cfg"));
        Config.readConfig();
        Lang.readConfig();
        lastlegendary = new File(directory.getPath(), "lastlegendary.json");
        boolean check = lastlegendary.exists();
        if (!check) {
            PrintWriter start;
            try {
                start = new PrintWriter(lastlegendary, "UTF-8");
                start.write(gson.toJson(new PokemonInfo(Lists.newArrayList())));
                start.close();
            } catch (FileNotFoundException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        lastultrabeast = new File(directory.getPath(), "lastultrabeast.json");
        check = lastultrabeast.exists();
        if (!check) {
            PrintWriter start;
            try {
                start = new PrintWriter(lastultrabeast, "UTF-8");
                start.write(gson.toJson(new PokemonInfo(Lists.newArrayList())));
                start.close();
            } catch (FileNotFoundException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        lastshiny = new File(directory.getPath(), "lastshiny.json");
        check = lastshiny.exists();
        if (!check) {
            PrintWriter start;
            try {
                start = new PrintWriter(lastshiny, "UTF-8");
                start.write(gson.toJson(new PokemonInfo(Lists.newArrayList())));
                start.close();
            } catch (FileNotFoundException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        lastboss = new File(directory.getPath(), "lastboss.json");
        check = lastboss.exists();
        if (!check) {
            PrintWriter start;
            try {
                start = new PrintWriter(lastboss, "UTF-8");
                start.write(gson.toJson(new PokemonInfo(Lists.newArrayList())));
                start.close();
            } catch (FileNotFoundException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        Pixelmon.EVENT_BUS.register(new PixelmonEvents());
        EnumSpecies.legendaries.forEach(species -> {
            legendaries.add(species.getPokemonName());
        });
    }

    @Mod.EventHandler
    public void onServerStart(FMLServerStartingEvent event){
        event.registerServerCommand(new LastLegendCmd());
        event.registerServerCommand(new LegendInfos());
        event.registerServerCommand(new LegendaryPlusReloadCmd());
        event.registerServerCommand(new LastUBCmd());
        event.registerServerCommand(new UltrabeastInfos());
        event.registerServerCommand(new LastShinyCmd());
        event.registerServerCommand(new LastBossCmd());
    }

}
