package net.labymod.addons.spotify.core.notifications;

import net.kyori.adventure.text.Component;
import net.labymod.addons.spotify.core.SpotifyAddon;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.notification.Notification;
import net.labymod.api.notification.Notification.Type;

public class NotificationHandler {

  private final SpotifyAddon addon;
  private final Icon hudIcon;

  public NotificationHandler(SpotifyAddon addon) {
    this.addon = addon;
    hudIcon = this.addon.getIcon();

  }

  public void createNotification(NotificationCase notiCase, long duration, String trackName) {
    if (notiCase.equals(NotificationCase.ERROR)) {
      this.addon.labyAPI().notificationController().push(
          Notification.builder()
              .title(Component.text("Spotify Addon"))
              .text(Component.text("Please (re-)start Spotify and wait a few seconds"))
              .duration(duration)
              .icon(hudIcon)
              .type(Type.SYSTEM).build());
    } else if (notiCase.equals(NotificationCase.SKIP)) {
      this.addon.labyAPI().notificationController().push(
          Notification.builder()
              .title(Component.text("Spotify Addon"))
              .text(Component.text("Playing next track: "+ trackName))
              .duration(duration)
              .icon(hudIcon)
              .type(Type.SYSTEM).build());
    } else if (notiCase.equals(NotificationCase.PREV)) {
      this.addon.labyAPI().notificationController().push(
          Notification.builder()
              .title(Component.text("Spotify Addon"))
              .text(Component.text("Playing previous track: "+ trackName))
              .duration(duration)
              .icon(hudIcon)
              .type(Type.SYSTEM).build());
    }
  }

  public enum NotificationCase {
    ERROR,
    RESTART,
    SKIP,
    PREV
  }
}
