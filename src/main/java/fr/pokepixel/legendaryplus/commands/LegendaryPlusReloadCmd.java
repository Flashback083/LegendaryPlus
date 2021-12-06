package fr.pokepixel.legendaryplus.commands;

import com.google.common.collect.Lists;
import fr.pokepixel.legendaryplus.Legendaryplus;
import fr.pokepixel.legendaryplus.config.Config;
import fr.pokepixel.legendaryplus.config.Lang;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.config.Configuration;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class LegendaryPlusReloadCmd extends CommandBase implements ICommand
{
	private final List<String> aliases;
	
	public LegendaryPlusReloadCmd(){
        aliases = Lists.newArrayList("lpreload");
    }
	
	
	@Override
	@Nonnull
	public String getName() {
		return "lpreload"; 
	}

	@Override
	@Nonnull
	public String getUsage(@Nonnull ICommandSender sender) {
		return 	TextFormatting.LIGHT_PURPLE+"/lpreload";
				
	}


	@Override
	@Nonnull
	public List<String> getAliases()
	{
		return aliases;
	}

	@Override
	public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) throws CommandException {
				Configuration cfglang = Legendaryplus.lang;
				Configuration cfg = Legendaryplus.config;
            	Lang.readConfig();
				Config.readConfig();
                if (cfglang.hasChanged()) {
                	cfglang.save();
                }
                if (cfg.hasChanged()){
                	cfg.save();
				}
                cfglang.load();
                cfg.load();
                sender.sendMessage(new TextComponentString(TextFormatting.GREEN+"LegendaryPlus's lang and config reloaded!"));
        }
	



	@Override
	public int getRequiredPermissionLevel() {
        return 2;
    }

	@Override
	@Nonnull
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
    	  return Collections.emptyList();
    }

	@Override
	public boolean isUsernameIndex(String[] astring, int i)
	{
		return false;
	}
	
	



	
}