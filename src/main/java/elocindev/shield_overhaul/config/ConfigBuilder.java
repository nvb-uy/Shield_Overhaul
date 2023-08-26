package elocindev.shield_overhaul.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import elocindev.shield_overhaul.ShieldOverhaul;
import net.fabricmc.loader.api.FabricLoader;

public class ConfigBuilder {
    public static final Gson BUILDER = (new GsonBuilder()).setPrettyPrinting().create();

    public static final Path file = FabricLoader.getInstance().getConfigDir()
            .resolve(ShieldOverhaul.MODID+".json");

    public static ConfigEntries loadConfig() {
        try {
            if (Files.notExists(file)) {
                ConfigEntries exampleConfig = new ConfigEntries();

                exampleConfig.blacklisted_shields.add("// Items here will not use Shield Overhaul's mechanics");
                exampleConfig.blacklisted_shields.add("examplemod:epic_shield");
                exampleConfig.bosses_immune_to_stun = true;
                exampleConfig.bash_only_on_ground = true;
                exampleConfig.bash_damage = 0.0F;
                exampleConfig.bash_cooldown_secs = 2;
                exampleConfig.bash_stun_duration_secs = 1;
                exampleConfig.add_stun_immunity = true;

                String defaultJson = BUILDER.toJson(exampleConfig);
                Files.writeString(file, defaultJson);
            } else {
                String json = Files.readString(file);
                ConfigEntries configEntries = BUILDER.fromJson(json, ConfigEntries.class);

                configEntries.blacklisted_shields = (configEntries.blacklisted_shields == null) ? new ArrayList<>() : configEntries.blacklisted_shields;            

                String updatedJson = BUILDER.toJson(configEntries);
                Files.writeString(file, updatedJson);
                return configEntries;
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        return new ConfigEntries();
    }
}
