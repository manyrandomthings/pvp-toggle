package pvptoggle;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;

import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

public class PvpCommand {
  public static LiteralCommandNode<ServerCommandSource> register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
    return dispatcher.register(
      CommandManager.literal("pvp")
      .then(
        CommandManager.literal("on")
        .executes(PvpCommand::enablePvp) // "/pvp on"
      )
      .then(
        CommandManager.literal("off")
        .executes(PvpCommand::disablePvp) // "/pvp off"
      )
      .then(
        CommandManager.literal("list")
        .executes(PvpCommand::listPlayers) // "/pvp list"
      )
      .executes(PvpCommand::pvpStatus) // "/pvp"
    );
  }

  // enables pvp for player
  private static int enablePvp(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
    ServerCommandSource source = ctx.getSource();
    GameProfile player = source.getPlayer().getGameProfile();

    // if not in list, add to list
    if(!PvpWhitelist.contains(player)) {
      PvpWhitelist.addPlayer(player);
      source.sendFeedback(new LiteralText("PvP for you is now on"), false);
      return 1;
    }

    // if already in list, tell player
    source.sendFeedback(new LiteralText("You already have PvP on!"), false);
    return 0;
  }

  // disables pvp for player
  private static int disablePvp(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
    ServerCommandSource source = ctx.getSource();
    GameProfile player = source.getPlayer().getGameProfile();

    // if in list, remove from list
    if(PvpWhitelist.contains(player)) {
      PvpWhitelist.removePlayer(player);
      source.sendFeedback(new LiteralText("PvP for you is now off"), false);
      return 1;
    }

    // if player isn't in list, tell player
    source.sendFeedback(new LiteralText("You already have PvP off!"), false);
    return 0;
  }

  // send player their current pvp status
  private static int pvpStatus(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
    ServerCommandSource source = ctx.getSource();
    GameProfile player = source.getPlayer().getGameProfile();

    source.sendFeedback(new LiteralText("PvP for you is " + (PvpWhitelist.contains(player) ? "on" : "off")), false);
    return 1;
  }

  // sends player list of players who are on pvp whitelist
  private static int listPlayers(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
    ServerCommandSource source = ctx.getSource();

    source.sendFeedback(new LiteralText("Players with PvP on: " + String.join(", ", PvpWhitelist.getPlayers())), false);
    return 1;
  }
}
