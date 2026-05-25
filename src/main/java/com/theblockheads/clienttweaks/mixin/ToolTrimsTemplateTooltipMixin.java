package com.theblockheads.clienttweaks.mixin;

import java.util.ArrayList;
import java.util.List;

import com.theblockheads.clienttweaks.ToolTrimTooltipUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ToolTrimsTemplateTooltipMixin {

	@Inject(method = "getTooltipLines", at = @At("RETURN"), cancellable = true)
	private void blockheads$replaceToolTrimsBookTooltip(Item.TooltipContext context, Player player, TooltipFlag tooltipFlag, CallbackInfoReturnable<List<Component>> cir) {
		ItemStack stack = (ItemStack)(Object)this;
		if (!stack.is(Items.ENCHANTED_BOOK)) return;

		String titleKey = ToolTrimTooltipUtil.getTitleKey(stack);
		if (titleKey == null) return;

		// Tool Trims stores templates as hidden enchantments; show them like real smithing templates.
		List<Component> lines = new ArrayList<>();
		lines.add(Component.translatable(titleKey).withStyle(style -> style.withItalic(false).withColor(ChatFormatting.YELLOW)));
		lines.add(gray("item.minecraft.smithing_template"));
		lines.add(CommonComponents.EMPTY);
		lines.add(gray("item.minecraft.smithing_template.applies_to"));
		lines.add(indentedBlue("template.tooltrims.applies_to"));
		lines.add(gray("item.minecraft.smithing_template.ingredients"));
		lines.add(indentedBlue("item.minecraft.smithing_template.armor_trim.ingredients"));
		cir.setReturnValue(lines);
	}

	private static Component gray(String key) {
		return Component.translatable(key).withStyle(style -> style.withItalic(false).withColor(ChatFormatting.GRAY));
	}

	private static Component indentedBlue(String key) {
		return Component.literal(" ").append(Component.translatable(key).withStyle(style -> style.withItalic(false).withColor(ChatFormatting.BLUE)));
	}
}
