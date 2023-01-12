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

package net.labymod.addons.spotify.core;

import com.google.inject.Singleton;
import de.labystudio.spotifyapi.SpotifyAPI;
import de.labystudio.spotifyapi.SpotifyAPIFactory;
import net.labymod.addons.spotify.core.hudwidgets.SpotifyHudWidget;
import net.labymod.addons.spotify.core.hudwidgets.SpotifyTextHudWidget;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonListener;

@Singleton
@AddonListener
public class SpotifyAddon extends LabyAddon<SpotifyConfiguration> {

  @Override
  protected void enable() {
    this.registerSettingCategory();

    SpotifyAPI spotifyAPI = SpotifyAPIFactory.create();
    SpotifyApiListener spotifyApiListener = new SpotifyApiListener();

    spotifyAPI.registerListener(spotifyApiListener);
    spotifyAPI.initializeAsync();

    this.labyAPI().hudWidgetRegistry().register(new SpotifyTextHudWidget("spotify_track", spotifyAPI));
    this.labyAPI().hudWidgetRegistry().register(new SpotifyHudWidget("spotify", spotifyAPI));
  }

  @Override
  protected Class<SpotifyConfiguration> configurationClass() {
    return SpotifyConfiguration.class;
  }
}
