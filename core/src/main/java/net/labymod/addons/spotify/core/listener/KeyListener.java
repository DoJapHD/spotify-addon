package net.labymod.addons.spotify.core.listener;

import de.labystudio.spotifyapi.SpotifyAPI;
import de.labystudio.spotifyapi.SpotifyAPIFactory;
import de.labystudio.spotifyapi.model.MediaKey;
import javax.inject.Inject;
import net.labymod.addons.spotify.core.SpotifyAddon;
import net.labymod.addons.spotify.core.SpotifyConfiguration;
import net.labymod.addons.spotify.core.notifications.NotificationHandler;
import net.labymod.addons.spotify.core.notifications.NotificationHandler.NotificationCase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.input.KeyEvent;
import net.labymod.api.event.client.input.KeyEvent.State;

public class KeyListener {

  private final SpotifyAddon addon;
  private final SpotifyAPI api;

  private final NotificationHandler notiHandler;

  @Inject
  private KeyListener(SpotifyAddon addon) {
    this.addon = addon;
    api = SpotifyAPIFactory.createInitialized();
    notiHandler = new NotificationHandler(addon);
  }

  //!Braucht noch Deaktivierung während Settings!
  @Subscribe
  public void keyPressed(KeyEvent event) {

    try {
      SpotifyConfiguration config = this.addon.configuration();

      if (!config.enabled().get() || !config.hotkeySubSettings().enabled().get()) {
        return;
      }
      /*if(event.key().equals(config.hotkeySubSettings().prevSongKey().get())) {
        System.out.println(Arrays.toString(this.addon.labyAPI().ingameOverlay().getActivities().toArray()));

      }*/

      if (event.state() == State.UNPRESSED) {
        this.addon.logger().info("connected:" + api.isConnected());
        if (event.key().equals(config.hotkeySubSettings().skipSongKey().get())) {
          this.addon.logger().info("Skip song");
          api.pressMediaKey(MediaKey.NEXT);

          if (config.hotkeySubSettings().showNotifications().get()) {
            new Thread(() -> {
              try {
                Thread.sleep(1500L);
                notiHandler.createNotification(NotificationCase.SKIP, 400 * 7L,
                    api.getTrack().getName());
              } catch (InterruptedException e) {
                notiHandler.createNotification(NotificationCase.SKIP, 400 * 7L,
                    api.getTrack().getName());
                throw new RuntimeException(e);
              }
            }).start();
          }
        } else if (event.key().equals(config.hotkeySubSettings().pauseSongKey().get())) {
          this.addon.logger().info("Pause song");
          api.pressMediaKey(MediaKey.PLAY_PAUSE);
        } else if (event.key().equals(config.hotkeySubSettings().prevSongKey().get())) {
          this.addon.logger().info("Prev song");
          api.pressMediaKey(MediaKey.PREV);
          api.pressMediaKey(MediaKey.PREV);

          if (config.hotkeySubSettings().showNotifications().get()) {
            new Thread(() -> {
              try {
                Thread.sleep(1500L);
                notiHandler.createNotification(NotificationCase.PREV, 400 * 7L,
                    api.getTrack().getName());
              } catch (InterruptedException e) {
                notiHandler.createNotification(NotificationCase.PREV, 400 * 7L,
                    api.getTrack().getName());
                throw new RuntimeException(e);
              }
            }).start();
          }
        }
      }
    } catch (Exception e) {
      //Fire notification to (re-)start Spotify as the API isn't connected
      if (!api.isConnected()) {
        notiHandler.createNotification(NotificationCase.ERROR, 400 * 7L, "N/A");
        this.addon.logger().error("" + e);
      }
    }
  }
}
