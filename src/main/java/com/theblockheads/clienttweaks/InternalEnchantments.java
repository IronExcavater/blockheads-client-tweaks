package com.theblockheads.clienttweaks;

import java.util.Set;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;

public final class InternalEnchantments {

	private static final Set<Identifier> HIDDEN = Set.of(
		// Nova Structures: internal boss/level-checker enchantments (never player-facing)
		Identifier.parse("nova_structures:boss_behaviour"),
		Identifier.parse("nova_structures:boss_nether"),
		Identifier.parse("nova_structures:librarian_level_checker"),
		Identifier.parse("nova_structures:shulker_boss"),
		Identifier.parse("nova_structures:shulker_miniboss"),
		Identifier.parse("nova_structures:tavern_level_checker"),
		// Nova Structures: jockey AI enchantments (never player-facing)
		Identifier.parse("nova_structures:jockey/make_drowned_into_jockey"),
		Identifier.parse("nova_structures:jockey/spawn_bogged_horseman"),
		Identifier.parse("nova_structures:jockey/spawn_camel_husk_jockey"),
		Identifier.parse("nova_structures:jockey/spawn_chicken_jockey"),
		Identifier.parse("nova_structures:jockey/spawn_hoglin_jockey"),
		Identifier.parse("nova_structures:jockey/spawn_ravager_jockey"),
		Identifier.parse("nova_structures:jockey/spawn_skeleton_horseman"),
		Identifier.parse("nova_structures:jockey/spawn_stray_horseman"),
		Identifier.parse("nova_structures:jockey/spawn_zombie_horseman"),
		Identifier.parse("nova_structures:jockey/spawn_zautilus_jockey"),
		// D&T enchantments intentionally withheld from players
		Identifier.parse("nova_structures:power"),
		Identifier.parse("nova_structures:piercing")
	);

	private InternalEnchantments() {
	}

	public static boolean shouldHide(ItemStack stack) {
		return containsHidden(stack.get(DataComponents.STORED_ENCHANTMENTS)) || containsHidden(stack.get(DataComponents.ENCHANTMENTS));
	}

	private static boolean containsHidden(ItemEnchantments enchantments) {
		if (enchantments == null || enchantments.isEmpty()) return false;

		for (Object2IntMap.Entry<Holder<Enchantment>> entry : enchantments.entrySet()) {
			ResourceKey<Enchantment> key = entry.getKey().unwrapKey().orElse(null);
			if (key != null && HIDDEN.contains(key.identifier())) return true;
		}

		return false;
	}
}
