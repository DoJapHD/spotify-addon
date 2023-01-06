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
import net.labymod.addons.spotify.core.hudwidgets.SpotifyTrackHudWidget;
import net.labymod.addons.spotify.core.listener.KeyListener;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.models.addon.annotation.AddonListener;

@Singleton
@AddonListener
public class SpotifyAddon extends LabyAddon<SpotifyConfiguration> {

  private final Icon hudIcon = Icon.texture(
          ResourceLocation.create("spotify", "themes/vanilla/textures/settings/hud/spotify.png"))
      .resolution(64, 64);

  @Override
  protected void enable() {
    this.registerSettingCategory();

    this.registerListener(KeyListener.class);
    //this.registerListener(MultiKeyListener.class);

    this.labyAPI().hudWidgetRegistry().register(new SpotifyTrackHudWidget("spotify_track"));
  }

  @Override
  protected Class<SpotifyConfiguration> configurationClass() {
    return SpotifyConfiguration.class;
  }

  public Icon getIcon() {
    return hudIcon;
  }

}
