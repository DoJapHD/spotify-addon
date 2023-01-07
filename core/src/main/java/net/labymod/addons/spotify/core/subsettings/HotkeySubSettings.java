package net.labymod.addons.spotify.core.subsettings;

import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.widget.widgets.input.KeybindWidget.KeyBindSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.ParentSwitch;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingSection;
import net.labymod.api.util.MethodOrder;

public class HotkeySubSettings extends Config {

  @ParentSwitch
  @SwitchSetting
  private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(false);

  @SettingSection("info")

  @MethodOrder(after = "enabled")
  @KeyBindSetting
  private final ConfigProperty<Key> prevSong = new ConfigProperty<>(Key.NONE);


  @MethodOrder(after = "prevSong")
  @KeyBindSetting
  private final ConfigProperty<Key> pauseSong = new ConfigProperty<>(Key.NONE);

  @MethodOrder(after = "pauseSong")
  @KeyBindSetting
  private final ConfigProperty<Key> skipSong = new ConfigProperty<>(Key.NONE);

  @SwitchSetting
  private final ConfigProperty<Boolean> showNotifications = new ConfigProperty<>(true);

  /*
  @MultiKeyBindSetting
  private final ConfigProperty<Key[]> skipSong = new ConfigProperty<>(new Key[0]);

  @MultiKeyBindSetting
  private final ConfigProperty<Key[]> pauseSong = new ConfigProperty<>(new Key[0]);*/

  public ConfigProperty<Boolean> enabled() {
    return this.enabled;
  }

  public ConfigProperty<Key> skipSongKey() {
    return this.skipSong;
  }

  public ConfigProperty<Key> pauseSongKey() {
    return this.pauseSong;
  }

  public ConfigProperty<Key> prevSongKey() {
    return this.prevSong;
  }

  public ConfigProperty<Boolean> showNotifications() {
    return this.showNotifications;
  }

  /* MultiKeyBind
  public ConfigProperty<Key[]> skipSongKey() {
    return this.skipSong;
  }

  public ConfigProperty<Key[]> pauseSongKey() {
    return this.pauseSong;
  }*/
}
