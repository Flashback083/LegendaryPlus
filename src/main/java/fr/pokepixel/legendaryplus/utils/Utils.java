package fr.pokepixel.legendaryplus.utils;

import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import fr.pokepixel.legendaryplus.Legendaryplus;
import fr.pokepixel.legendaryplus.PokemonInfo;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.config.ConfigCategory;

import java.util.Date;
import java.util.List;

import static fr.pokepixel.legendaryplus.utils.ChatColor.translateAlternateColorCodes;
import static fr.pokepixel.legendaryplus.utils.GsonUtils.listPoke;

public class Utils {

    private static long elapsedDays,elapsedHours,elapsedMinutes;


    /*public static String getBiomeNameWithEntity(Entity entity){
        Biome biome = entity.getEntityWorld().getBiome(entity.getPosition());
        Field field = ReflectionHelper.findField(Biome.class, "biomeName","field_76791_y");
        String biomename = "";
        try {
            biomename = String.valueOf(field.get(biome));
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return biomename;
    }*/

    public static void sendMessage(String name, ICommandSender sender, MinecraftServer server){
        ConfigCategory cfglang = Legendaryplus.lang.getCategory("lang");
        List<PokemonInfo.Info> leglist = listPoke(name);
        int i = 1;
        for (PokemonInfo.Info info : leglist) {
            String state = info.getState();
            String playerph = "";
            if (server.getEntityFromUuid(info.getUuid()) != null && !state.equalsIgnoreCase(cfglang.get("captured").getString())) {
                if (server.getEntityFromUuid(info.getUuid()) instanceof EntityPixelmon) {
                    state = cfglang.get("alive").getString();
                }
            } else {
                if (!state.equalsIgnoreCase(cfglang.get("defeated").getString()) && !state.equalsIgnoreCase(cfglang.get("captured").getString())) {
                    state = cfglang.get("despawned").getString();
                } else {
                    playerph = cfglang.get("playerph").getString().replace("{player}", info.getPlayer());
                }
            }
            Date datetoday = new Date();
            Date datediff = new Date();
            datediff.setTime(info.getMs());
            printDifference(datediff, datetoday);
            String keytransform = cfglang.get("lastpoke").getString()
                    .replace("{x}",String.valueOf(i))
                    .replace("{pokemon}", info.getName())
                    .replace("{state}", state)
                    .replace("{playerph}", playerph)
                    .replace("{days}", String.valueOf(elapsedDays))
                    .replace("{hours}", String.valueOf(elapsedHours))
                    .replace("{minutes}", String.valueOf(elapsedMinutes));
            sender.sendMessage(new TextComponentString(translateAlternateColorCodes('&',keytransform)));
            i = i+1;
        }
    }

    public static void printDifference(Date startDate, Date endDate){

        //milliseconds
        long different = endDate.getTime() - startDate.getTime();
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        elapsedDays = different / daysInMilli;
        different = different % daysInMilli;
        elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;
        elapsedMinutes = different / minutesInMilli;
        //different = different % minutesInMilli;
    }

}
