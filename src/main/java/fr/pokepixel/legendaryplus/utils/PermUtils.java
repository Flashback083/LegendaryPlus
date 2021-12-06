package fr.pokepixel.legendaryplus.utils;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.permission.PermissionAPI;

public class PermUtils {
    public static boolean isOP(EntityPlayerMP player) {
            MinecraftServer server = player.getServer();
            if (server.getPlayerList() != null)
                return (server.getPlayerList().getOppedPlayers().getEntry(player.getGameProfile()) != null);
            return false;
        }

        public static boolean hasPermission(String permission, EntityPlayerMP player) {
            return (PermissionAPI.hasPermission(player, permission) || player.canUseCommand(4, permission) || isOP(player));
        }

        public static boolean canUse(String permission, ICommandSender sender) {
            return (sender instanceof  MinecraftServer || (sender instanceof EntityPlayerMP && hasPermission(permission, (EntityPlayerMP) sender)));
        }
}
