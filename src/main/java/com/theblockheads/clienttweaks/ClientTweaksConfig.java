package com.theblockheads.clienttweaks;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "blockheads_client_tweaks")
public class ClientTweaksConfig implements ConfigData {

	/** Vanilla creative search box width (from MC source). */
	public static final int VANILLA_WIDTH = 80;
	/** Default width — narrows the box to avoid overlapping IPN sort buttons. */
	public static final int DEFAULT_WIDTH = 70;

	public static final int MIN_WIDTH = 40;
	public static final int MAX_WIDTH = 200;

	public int creativeSearchWidth = DEFAULT_WIDTH;

	@Override
	public void validatePostLoad() {
		if (creativeSearchWidth < MIN_WIDTH || creativeSearchWidth > MAX_WIDTH) {
			creativeSearchWidth = DEFAULT_WIDTH;
		}
	}
}
