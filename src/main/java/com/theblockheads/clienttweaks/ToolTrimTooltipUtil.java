package com.theblockheads.clienttweaks;

import java.util.Map;
import java.util.Optional;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;

public final class ToolTrimTooltipUtil {

	private static final Map<Identifier, String> TITLES = Map.of(
		Identifier.parse("tooltrims:_a_linear_template"), "trim_pattern.tooltrims.linear",
		Identifier.parse("tooltrims:_b_tracks_template"), "trim_pattern.tooltrims.tracks",
		Identifier.parse("tooltrims:_c_charge_template"), "trim_pattern.tooltrims.charge",
		Identifier.parse("tooltrims:_d_frost_template"), "trim_pattern.tooltrims.frost"
	);

	private ToolTrimTooltipUtil() {
	}

	public static String getTitleKey(ItemStack stack) {
		if (!stack.is(Items.ENCHANTED_BOOK)) return null;

		ItemEnchantments enchantments = stack.get(DataComponents.STORED_ENCHANTMENTS);
		if (enchantments == null || enchantments.isEmpty()) return null;

		for (Object2IntMap.Entry<Holder<Enchantment>> entry : enchantments.entrySet()) {
			Optional<ResourceKey<Enchantment>> key = entry.getKey().unwrapKey();
			if (key.isEmpty()) continue;

			String title = TITLES.get(key.get().identifier());
			if (title != null) return title;
		}

		return null;
	}

	public static boolean isTemplateBook(ItemStack stack) {
		return getTitleKey(stack) != null;
	}
}
