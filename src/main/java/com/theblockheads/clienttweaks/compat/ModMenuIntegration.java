package com.theblockheads.clienttweaks.compat;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import com.theblockheads.clienttweaks.ClientTweaks;
import com.theblockheads.clienttweaks.ClientTweaksConfig;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import net.minecraft.network.chat.Component;

public class ModMenuIntegration implements ModMenuApi {

	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return parent -> {
			ConfigBuilder builder = ConfigBuilder.create()
				.setParentScreen(parent)
				.setTitle(Component.literal("Blockheads Client Tweaks"))
				.setSavingRunnable(ClientTweaks::saveConfig);

			builder.getOrCreateCategory(Component.literal("Creative Inventory"))
				.addEntry(builder.entryBuilder()
					.startIntSlider(
						Component.literal("Creative Search Box Width"),
						ClientTweaks.CONFIG.creativeSearchWidth,
						ClientTweaksConfig.MIN_WIDTH,
						ClientTweaksConfig.MAX_WIDTH)
					.setDefaultValue(ClientTweaksConfig.DEFAULT_WIDTH)
					.setTextGetter(val -> val == ClientTweaksConfig.VANILLA_WIDTH
						? Component.literal("Vanilla (" + val + " px)")
						: Component.literal(val + " px"))
					.setTooltip(Component.literal(
						"Width of the creative search box. Vanilla = " + ClientTweaksConfig.VANILLA_WIDTH + " px."))
					.setSaveConsumer(val -> ClientTweaks.CONFIG.creativeSearchWidth = val)
					.build());

			return builder.build();
		};
	}
}
