package fr.pokepixel.legendaryplus;

import com.google.common.collect.Lists;
import com.pixelmonmod.pixelmon.api.events.BeatWildPixelmonEvent;
import com.pixelmonmod.pixelmon.api.events.CaptureEvent;
import com.pixelmonmod.pixelmon.api.events.spawning.SpawnEvent;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static fr.pokepixel.legendaryplus.utils.ChatColor.translateAlternateColorCodes;
import static fr.pokepixel.legendaryplus.utils.GsonUtils.replaceLatest;
import static fr.pokepixel.legendaryplus.utils.GsonUtils.replaceOne;

public class PixelmonEvents {

    @SubscribeEvent
    public void onPixelmonSpawn(SpawnEvent event) {
        final Entity entity = event.action.getOrCreateEntity();
        if (entity instanceof EntityPixelmon) {
            final Entity causeEntity = event.action.spawnLocation.cause;
            if (!(causeEntity instanceof EntityPlayerMP)) {
                return;
            }
            EntityPlayerMP playermp = (EntityPlayerMP) causeEntity;
            EntityPixelmon pixelmon = (EntityPixelmon) entity;
            boolean blacklist = true;
            Configuration cfg = Legendaryplus.config;
            ConfigCategory category = cfg.getCategory("general");
            String[] list = category.get("blacklist").getStringList();
            List<String> list2 = Lists.newArrayList(list);
            for (String s : list2) {
                if (s.equalsIgnoreCase(pixelmon.getName())) {
                    blacklist = false;
                }
            }
            if (blacklist && pixelmon.isLegendary() && !pixelmon.hasOwner() && !pixelmon.isBossPokemon()) {
                Date date = new Date();
                ConfigCategory cfglang = Legendaryplus.lang.getCategory("lang");
                String name = pixelmon.getLocalizedName();
                long ms = date.getTime();
                UUID uuid = pixelmon.getUniqueID();
                String state = cfglang.get("alive").getString();
                int limit = category.get("limitleg").getInt();
                PokemonInfo.Info info = new PokemonInfo.Info(name, ms, uuid, state, "");
                replaceLatest("lastlegendary", info, limit);
                if (cfg.getCategory("general").get("allplayers").getBoolean()) {
                    if (pixelmon.getEntityWorld().getClosestPlayerToEntity(pixelmon, 500) != null) {
                        String keytransform1 = translateAlternateColorCodes('&', cfglang.get("messagetoallplayers").getString())
                                .replace("{legendname}", pixelmon.getLocalizedName())
                                .replace("{player}", playermp.getName());
                        playermp.server.getPlayerList().getPlayers().forEach(entityPlayerMP -> entityPlayerMP.sendMessage(new TextComponentString(keytransform1)));
                    }
                }
                if (cfg.getCategory("general").get("msgplayer").getBoolean()) {
                    if (pixelmon.getEntityWorld().getClosestPlayerToEntity(pixelmon, 500) != null) {
                        String keytransform1 = cfglang.get("messagetotheplayer").getString()
                                .replace("{legendname}", pixelmon.getLocalizedName())
                                .replace("{player}", playermp.getName());
                        playermp.sendMessage(new TextComponentString(translateAlternateColorCodes('&', keytransform1)));
                    }
                }
            }
            if (blacklist && EnumSpecies.ultrabeasts.contains(pixelmon.getSpecies()) && !pixelmon.hasOwner() && !pixelmon.isBossPokemon()) {
                Date date = new Date();
                ConfigCategory cfglang = Legendaryplus.lang.getCategory("lang");
                String name = pixelmon.getLocalizedName();
                long ms = date.getTime();
                UUID uuid = pixelmon.getUniqueID();
                String state = cfglang.get("alive").getString();
                int limit = category.get("limitub").getInt();
                PokemonInfo.Info info = new PokemonInfo.Info(name, ms, uuid, state, "");
                replaceLatest("lastultrabeast", info, limit);
            }
            if (pixelmon.getPokemonData().isShiny() && !EnumSpecies.legendaries.contains(pixelmon.getSpecies()) && !EnumSpecies.ultrabeasts.contains(pixelmon.getSpecies()) && !pixelmon.isBossPokemon()) {
                Date date = new Date();
                ConfigCategory cfglang = Legendaryplus.lang.getCategory("lang");
                String name = pixelmon.getLocalizedName();
                long ms = date.getTime();
                UUID uuid = pixelmon.getUniqueID();
                String state = cfglang.get("alive").getString();
                int limit = category.get("limitshiny").getInt();
                PokemonInfo.Info info = new PokemonInfo.Info(name, ms, uuid, state, "");
                replaceLatest("lastshiny", info, limit);
            }
            if (pixelmon.isBossPokemon()){
                Date date = new Date();
                ConfigCategory cfglang = Legendaryplus.lang.getCategory("lang");
                String name = pixelmon.getLocalizedName();
                long ms = date.getTime();
                UUID uuid = pixelmon.getUniqueID();
                String state = cfglang.get("alive").getString();
                int limit = category.get("limitboss").getInt();
                PokemonInfo.Info info = new PokemonInfo.Info(name, ms, uuid, state, "");
                replaceLatest("lastboss", info, limit);
            }
        }
    }

    @SubscribeEvent
    public void onCapture(CaptureEvent.SuccessfulCapture event) {
        EntityPixelmon pokemon = event.getPokemon();
        //System.out.println("[onCaptureLP] uuid = " + pokemon.getUniqueID() );
        if (EnumSpecies.legendaries.contains(pokemon.getSpecies())){
            ConfigCategory cfglang = Legendaryplus.lang.getCategory("lang");
            String state = cfglang.get("captured").getString();
            replaceOne("lastlegendary",event.player.getName(),state,event.getPokemon().getUniqueID());
        }else if (EnumSpecies.ultrabeasts.contains(pokemon.getSpecies())){
            ConfigCategory cfglang = Legendaryplus.lang.getCategory("lang");
            String state = cfglang.get("captured").getString();
            replaceOne("lastultrabeast",event.player.getName(),state,pokemon.getUniqueID());
        }else if (event.getPokemon().getPokemonData().isShiny()){
            ConfigCategory cfglang = Legendaryplus.lang.getCategory("lang");
            String state = cfglang.get("captured").getString();
            replaceOne("lastshiny",event.player.getName(),state,pokemon.getUniqueID());
        }
    }

    @SubscribeEvent
    public void onKill(BeatWildPixelmonEvent event) {
        EntityPixelmon pokemon = (EntityPixelmon) event.wpp.getEntity();
        if (EnumSpecies.legendaries.contains(pokemon.getSpecies())){
            ConfigCategory cfglang = Legendaryplus.lang.getCategory("lang");
            String state = cfglang.get("defeated").getString();
            replaceOne("lastlegendary",event.player.getName(),state,pokemon.getUniqueID());
        }else if (EnumSpecies.ultrabeasts.contains(pokemon.getSpecies())){
            ConfigCategory cfglang = Legendaryplus.lang.getCategory("lang");
            String state = cfglang.get("defeated").getString();
            replaceOne("lastultrabeast",event.player.getName(),state,pokemon.getUniqueID());
        }else if (pokemon.getPokemonData().isShiny()){
            ConfigCategory cfglang = Legendaryplus.lang.getCategory("lang");
            String state = cfglang.get("defeated").getString();
            replaceOne("lastshiny",event.player.getName(),state,pokemon.getUniqueID());
        }else if (pokemon.isBossPokemon()){
            ConfigCategory cfglang = Legendaryplus.lang.getCategory("lang");
            String state = cfglang.get("defeated").getString();
            replaceOne("lastboss",event.player.getName(),state,pokemon.getUniqueID());
        }
    }

}
