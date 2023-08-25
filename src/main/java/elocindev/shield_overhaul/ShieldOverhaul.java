package elocindev.shield_overhaul;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import software.bernie.geckolib3.GeckoLib;

public class ShieldOverhaul implements ModInitializer {
	public static final String MODID = "shield_overhaul";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitialize() {
		GeckoLib.initialize();
	}
}
