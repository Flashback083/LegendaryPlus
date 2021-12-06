package fr.pokepixel.legendaryplus.commands;

import com.google.common.collect.Lists;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

import static fr.pokepixel.legendaryplus.utils.Utils.sendMessage;

public class LastUBCmd extends CommandBase implements ICommand
{
	private final List<String> aliases;


	public LastUBCmd(){
        aliases = Lists.newArrayList("lu","lastub");
    }
	
	
	@Override
	@Nonnull
	public String getName() {
		return "lastultrabeast";
	}

	@Override
	@Nonnull
	public String getUsage(@Nonnull ICommandSender sender) {
		return "/lastub";
	}


	@Override
	@Nonnull
	public List<String> getAliases()
	{
		return aliases;
	}

	@Override
	public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        if (args.length == 0) {
            sendMessage(getName(), sender, server);
        } else {
            sender.sendMessage(new TextComponentString(TextFormatting.RED + "Error in the command ! Use : " + TextFormatting.AQUA + getUsage(sender)));
        }
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