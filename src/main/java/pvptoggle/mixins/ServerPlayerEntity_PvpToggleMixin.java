package pvptoggle.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import pvptoggle.PvpWhitelist;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntity_PvpToggleMixin {
    @Redirect(method = "shouldDamagePlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;isPvpEnabled()Z"))
    public boolean checkPvpWhitelist(ServerPlayerEntity thisPlayer, PlayerEntity targetPlayer) {
        // check if pvp is enabled, and check if both players are in the whitelist
        return thisPlayer.server.isPvpEnabled() && PvpWhitelist.contains(thisPlayer.getGameProfile()) && PvpWhitelist.contains(targetPlayer.getGameProfile());
    }
}
