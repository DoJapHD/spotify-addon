/*
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package net.labymod.addons.spotify.core.hudwidgets;

import de.labystudio.spotifyapi.SpotifyAPI;
import de.labystudio.spotifyapi.model.Track;
import net.labymod.addons.spotify.core.events.SpotifyConnectEvent;
import net.labymod.addons.spotify.core.events.SpotifyTrackChangedEvent;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.event.Subscribe;


public class SpotifyTextHudWidget extends TextHudWidget<TextHudWidgetConfig> {

  private TextLine trackLine;
  private TextLine artistLine;

  private final SpotifyAPI spotifyAPI;

  public SpotifyTextHudWidget(String id, SpotifyAPI spotifyAPI) {
    super(id);

    this.spotifyAPI = spotifyAPI;
  }

  @Override
  public void load(TextHudWidgetConfig config) {
    super.load(config);

    this.trackLine = super.createLine("Track", "Loading...");
    this.artistLine = super.createLine("Artist", "Loading...");
  }

  @Override
  public void initialize(AbstractWidget<Widget> widget) {
    super.initialize(widget);
  }

  @Override
  public boolean isVisibleInGame() {
    return spotifyAPI.isConnected() && spotifyAPI.isPlaying();
  }

  @Subscribe
  public void onSpotifyConnectEvent(SpotifyConnectEvent event) {
    this.onPlayBackChanged(this.spotifyAPI.isPlaying());
  }

  @Subscribe
  public void onSpotifyTrackChangedEvent(SpotifyTrackChangedEvent event) {
    this.onTrackChanged(event.getTrack());
  }

  public void onTrackChanged(Track track) {
    if (track == null) {
      this.onPlayBackChanged(false);
    } else {
      this.trackLine.update(track.getName());
      this.artistLine.update(track.getArtist());
    }

    this.flushAll();
  }

  public void onPlayBackChanged(boolean isPlaying) {
    if (!isPlaying) {
      this.trackLine.update("Not playing");
      this.artistLine.update("Not playing");
    } else {
      this.onTrackChanged(this.spotifyAPI.getTrack());
    }
  }

}