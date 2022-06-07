package pvptoggle.mixins;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.encryption.PlayerPublicKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pvptoggle.PvpWhitelist;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntity_PvpToggleMixin extends PlayerEntity {
    public ServerPlayerEntity_PvpToggleMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile, @Nullable PlayerPublicKey publicKey) {
        super(world, pos, yaw, gameProfile, publicKey);
    }

    @Inject(method = "shouldDamagePlayer", at = @At("HEAD"), cancellable = true)
    public void checkwhitelist(PlayerEntity targetPlayer, CallbackInfoReturnable<Boolean> cir) {
        // if either player is not on pvp whitelist, return false
        if(!PvpWhitelist.contains(this.getGameProfile()) || !PvpWhitelist.contains(targetPlayer.getGameProfile())) {
            cir.setReturnValue(false);
        }
    }
}
