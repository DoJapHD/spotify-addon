package net.labymod.addons.spotify.core.listener;

import de.labystudio.spotifyapi.SpotifyAPI;
import de.labystudio.spotifyapi.SpotifyAPIFactory;
import javax.inject.Inject;
import net.labymod.addons.spotify.core.SpotifyAddon;
import net.labymod.addons.spotify.core.SpotifyConfiguration;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.input.KeyEvent;
import net.labymod.api.event.client.input.KeyEvent.State;

public class MultiKeyListener {

  private final SpotifyAddon addon;
  private final SpotifyAPI api;


  @Inject
  private MultiKeyListener(SpotifyAddon addon) {
    this.addon = addon;
    api = SpotifyAPIFactory.createInitialized();
  }

  @Subscribe
  public void keyPressed(KeyEvent event) {

    try {
      SpotifyConfiguration config = this.addon.configuration();

      if (!config.enabled().get() || !config.hotkeySubSettings().enabled().get()) {
        return;
      }

      if (event.state() == State.UNPRESSED) {
        //DOESN'T WORK CORRECTLY, DO NOT USE

        /*
        for(Key requiredKey : config.hotkeySubSettings().skipSongKey().get()) {
          if (this.addon.labyAPI().minecraft().isKeyPressed(requiredKey)) {
            this.addon.logger().info("Skip song");
            api.pressMediaKey(MediaKey.NEXT);
          }
        }

        for(Key requiredKey : config.hotkeySubSettings().pauseSongKey().get()) {
          if (this.addon.labyAPI().minecraft().isKeyPressed(requiredKey)) {
            this.addon.logger().info("Pause song");
            api.pressMediaKey(MediaKey.PLAY_PAUSE);
          }
        }*/
      }
    } catch (Exception e) {

      this.addon.logger().error("" + e);
    }
  }
}