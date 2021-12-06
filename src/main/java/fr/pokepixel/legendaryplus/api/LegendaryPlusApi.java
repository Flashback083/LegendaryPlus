package fr.pokepixel.legendaryplus.api;

import fr.pokepixel.legendaryplus.Legendaryplus;
import fr.pokepixel.legendaryplus.PokemonInfo;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;

import java.util.UUID;

import static fr.pokepixel.legendaryplus.utils.GsonUtils.replaceLatest;

public class LegendaryPlusApi {

    public static void addLegendary(String name, long millis, UUID uuid) {
        Configuration cfg = Legendaryplus.config;
        ConfigCategory category = cfg.getCategory("general");
        ConfigCategory cfglang = Legendaryplus.lang.getCategory("lang");
        int limit = category.get("limitleg").getInt();
        String state = cfglang.get("alive").getString();
        replaceLatest("lastlegendary",new PokemonInfo.Info(name,millis,uuid,state,""),limit);
    }

    public static void addUB(String name, long millis, UUID uuid) {
        Configuration cfg = Legendaryplus.config;
        ConfigCategory category = cfg.getCategory("general");
        ConfigCategory cfglang = Legendaryplus.lang.getCategory("lang");
        int limit = category.get("limitub").getInt();
        String state = cfglang.get("alive").getString();
        replaceLatest("lastultrabeast",new PokemonInfo.Info(name,millis,uuid,state,""),limit);
    }

    public static void addShiny(String name, long millis, UUID uuid) {
        Configuration cfg = Legendaryplus.config;
        ConfigCategory category = cfg.getCategory("general");
        ConfigCategory cfglang = Legendaryplus.lang.getCategory("lang");
        int limit = category.get("limitshiny").getInt();
        String state = cfglang.get("alive").getString();
        replaceLatest("lastshiny",new PokemonInfo.Info(name,millis,uuid,state,""),limit);
    }

    public static void addBoss(String name, long millis, UUID uuid) {
        Configuration cfg = Legendaryplus.config;
        ConfigCategory category = cfg.getCategory("general");
        ConfigCategory cfglang = Legendaryplus.lang.getCategory("lang");
        int limit = category.get("limitboss").getInt();
        String state = cfglang.get("alive").getString();
        replaceLatest("lastboss",new PokemonInfo.Info(name,millis,uuid,state,""),limit);
    }
}
