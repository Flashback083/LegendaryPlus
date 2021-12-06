package fr.pokepixel.legendaryplus.commands;

import com.google.common.collect.Lists;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import fr.pokepixel.legendaryplus.Legendaryplus;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.config.ConfigCategory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static fr.pokepixel.legendaryplus.Legendaryplus.legendaries;
import static fr.pokepixel.legendaryplus.utils.ChatColor.translateAlternateColorCodes;

public class LegendInfos extends CommandBase implements ICommand
{
	private final List<String> aliases;

	public LegendInfos(){
        aliases = Lists.newArrayList("lg");
    }
	
	
	@Override
	@Nonnull
	public String getName() {
		return "legend"; 
	}

	@Override
	@Nonnull
	public String getUsage(@Nonnull ICommandSender sender) {
		return "/legend [Legendary name]";
	}


	@Override
	@Nonnull
	public List<String> getAliases()
	{
		return aliases;
	}

	@Override
	public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        if (server.getPlayerList().getPlayers().size()==0){
            return;
        }
        String search = "";
        if (args.length == 1){
            search = args[0];
        }
        AtomicBoolean found = new AtomicBoolean(false);
        for (WorldServer world : server.worlds) {
            List<Entity> entityList = world.loadedEntityList;
            String finalSearch = search;
            entityList.forEach(entity -> {
                if (entity instanceof EntityPixelmon){
                    EntityPixelmon pixelmon = (EntityPixelmon) entity;
                    if (pixelmon.isLegendary() && !pixelmon.hasOwner() && !pixelmon.isBossPokemon()){
                        boolean cont = finalSearch.length() == 0 || (pixelmon.getPokemonName().equalsIgnoreCase(finalSearch));
                        if (cont){
                            EntityPlayer player;
                            if (pixelmon.getEntityWorld().getClosestPlayerToEntity(pixelmon,100) != null){
                                player = pixelmon.getEntityWorld().getClosestPlayerToEntity(pixelmon, 100.0);
                                assert player != null;
                                float distance = pixelmon.getDistance(player);
                                found.set(true);
                                ConfigCategory cfglang = Legendaryplus.lang.getCategory("lang");
                                Biome b = pixelmon.getEntityWorld().getBiome(pixelmon.getPosition());
                                Field field = com.pixelmonmod.pixelmon.util.helpers.ReflectionHelper.findField(Biome.class, "biomeName","field_76791_y");
                                String biomename = "";
                                try {
                                    biomename = String.valueOf(field.get(b));
                                } catch (IllegalArgumentException | IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                                String keytransform = cfglang.get("legendlist").getString()
                                        .replace("{legendname}", pixelmon.getLocalizedName())
                                        .replace("{player}", player.getName())
                                        .replace("{distance}",String.valueOf(distance))
                                        .replace("{biome}", biomename);
                                ITextComponent msgleg = new TextComponentString(translateAlternateColorCodes('&',keytransform));
                                sender.sendMessage(msgleg);
                            }
                           
                        }
                        
                    }
                }
            });
        }
        if (!found.get()){
            sender.sendMessage(new TextComponentString(TextFormatting.RED + "No legendary found !"));
        }
    }
       

	@Override
	public int getRequiredPermissionLevel() {
        return 2;
    }

	@Override
	@Nonnull
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
        if (args.length == 1)
        {
            return getListOfStringsMatchingLastWord(args, legendaries);
        } 
        else
        {
            return Collections.emptyList();
        }
    }

	@Override
	public boolean isUsernameIndex(String[] astring, int i)
	{
		return false;
	}
	
	



	
}