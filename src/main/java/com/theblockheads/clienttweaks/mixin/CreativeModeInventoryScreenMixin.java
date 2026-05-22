package com.theblockheads.clienttweaks.mixin;

import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeModeInventoryScreen.class)
public abstract class CreativeModeInventoryScreenMixin {

    @Shadow
    private EditBox searchBox;

    @Inject(method = "init", at = @At("TAIL"))
    private void blockheads$shrinkSearchBox(CallbackInfo ci) {
        if (searchBox != null) {
            searchBox.setWidth(70);
        }
    }
}
