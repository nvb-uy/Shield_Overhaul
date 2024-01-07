package elocindev.shield_overhaul.config;

import java.util.ArrayList;
import java.util.List;

public class ConfigEntries {
  public boolean enable_instant_shield_use = true;
  public List<String> blacklisted_shields = new ArrayList<>();
  public boolean bosses_immune_to_stun = true;
  public boolean bash_only_on_ground = true;
  public float bash_damage = 0.0F;
  public float bash_cooldown_secs = 3.0F;
  public float bash_stun_duration_secs = 1.0F;
  public boolean add_stun_immunity = true;
  public boolean enable_parrying = true;
  public boolean enable_parry_abuse_prevention = true;
  public float parry_abuse_cooldown_secs = 0.5F;
  public float parry_window_secs = 1F;
}
