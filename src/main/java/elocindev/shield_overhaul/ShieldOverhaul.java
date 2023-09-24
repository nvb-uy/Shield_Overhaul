package elocindev.shield_overhaul;

import elocindev.shield_overhaul.registry.EffectRegistry;
import elocindev.shield_overhaul.registry.EntityRegistry;
import elocindev.shield_overhaul.registry.ServerPacketRegistry;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import elocindev.shield_overhaul.config.ConfigBuilder;
import elocindev.shield_overhaul.config.ConfigEntries;

public class ShieldOverhaul implements ModInitializer {
	public static final String MODID = "shield_overhaul";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	public static ConfigEntries CONFIG;

	// 1.20.1 branch!
	@Override
	public void onInitialize() {
		CONFIG = ConfigBuilder.loadConfig();

		EffectRegistry.initEffects();
		EntityRegistry.reg();
		ServerPacketRegistry.registerC2SPackets();
	}
}
