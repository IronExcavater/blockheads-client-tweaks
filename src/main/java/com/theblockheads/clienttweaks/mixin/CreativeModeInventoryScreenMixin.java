package com.theblockheads.clienttweaks.mixin;

import com.theblockheads.clienttweaks.ClientTweaks;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(CreativeModeInventoryScreen.class)
public abstract class CreativeModeInventoryScreenMixin {

	@ModifyArg(
		method = "init",
		at = @At(value = "INVOKE",
			target = "Lnet/minecraft/client/gui/components/EditBox;<init>(Lnet/minecraft/client/gui/Font;IIIILnet/minecraft/network/chat/Component;)V"),
		index = 3
	)
	private int blockheads$applySearchBoxWidth(int vanillaWidth) {
		return ClientTweaks.CONFIG.creativeSearchWidth;
	}
}
