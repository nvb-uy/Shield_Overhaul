package elocindev.shield_overhaul;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import elocindev.shield_overhaul.config.ConfigBuilder;
import elocindev.shield_overhaul.config.ConfigEntries;
import software.bernie.geckolib3.GeckoLib;

public class ShieldOverhaul implements ModInitializer {
	public static final String MODID = "shield_overhaul";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	public static ConfigEntries CONFIG;

	@Override
	public void onInitialize() {
		CONFIG = ConfigBuilder.loadConfig();

		GeckoLib.initialize();
	}
}
