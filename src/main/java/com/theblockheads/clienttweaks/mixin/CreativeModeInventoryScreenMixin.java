package com.theblockheads.clienttweaks.mixin;

import com.theblockheads.clienttweaks.ClientTweaks;
import com.theblockheads.clienttweaks.ClientTweaksConfig;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeModeInventoryScreen.class)
public abstract class CreativeModeInventoryScreenMixin {

	// Search slot coordinates inside tab_item_search.png. The EditBox starts two pixels inside it.
	private static final float SLOT_TOP_V      = 4f;
	private static final float SLOT_INTERIOR_U = 81f;
	private static final float SLOT_BORDER_U   = 169f;
	private static final float SLOT_FRAME_U    = 170f;
	private static final int   SLOT_HEIGHT     = 12;
	private static final int   SLOT_END        = 87;
	private static final int   TEX_SIZE        = 256;

	@Shadow private EditBox searchBox;
	@Shadow private static CreativeModeTab selectedTab;

	// EditBox caches text clipping at construction, so changing width afterwards is too late.
	@ModifyArg(
		method = "init",
		at = @At(value = "INVOKE",
			target = "Lnet/minecraft/client/gui/components/EditBox;<init>(Lnet/minecraft/client/gui/Font;IIIILnet/minecraft/network/chat/Component;)V"),
		index = 3
	)
	private int blockheads$applySearchBoxWidth(int width) {
		return ClientTweaks.CONFIG.creativeSearchWidth;
	}

	@Inject(method = "extractBackground", at = @At("RETURN"))
	private void blockheads$adjustVisual(GuiGraphicsExtractor g, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
		if (selectedTab == null || !this.searchBox.isVisible()) return;

		int width = ClientTweaks.CONFIG.creativeSearchWidth;
		if (width == ClientTweaksConfig.VANILLA_WIDTH) return;

		int x = this.searchBox.getX();
		int y = this.searchBox.getY() - 2;
		Identifier texture = selectedTab.getBackgroundTexture();

		if (width < ClientTweaksConfig.VANILLA_WIDTH) {
			this.blockheads$blitSlotPatch(g, texture, x + width + 1, y, SLOT_FRAME_U, SLOT_END - width);
		} else {
			this.blockheads$blitSlotPatch(g, texture, x + SLOT_END, y, SLOT_INTERIOR_U, width - SLOT_END + 1);
		}

		g.blit(RenderPipelines.GUI_TEXTURED, texture, x + width, y, SLOT_BORDER_U, SLOT_TOP_V, 1, SLOT_HEIGHT, TEX_SIZE, TEX_SIZE);
	}

	private void blockheads$blitSlotPatch(GuiGraphicsExtractor g, Identifier texture, int x, int y, float u, int width) {
		// A one-pixel source column tiles cleanly without sampling outside the vanilla panel.
		g.blit(RenderPipelines.GUI_TEXTURED, texture, x, y, u, SLOT_TOP_V, width, SLOT_HEIGHT, 1, SLOT_HEIGHT, TEX_SIZE, TEX_SIZE);
	}
}
