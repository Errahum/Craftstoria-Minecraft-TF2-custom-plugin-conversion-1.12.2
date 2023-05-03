package com.chaseoes.tf2.listeners;

import com.chaseoes.tf2.*;
import com.chaseoes.tf2.localization.Localizers;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.chaseoes.tf2.commands.SpectateCommand;

import static org.bukkit.Bukkit.getServer;

public class PlayerQuitListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent event) {
        GamePlayer gPlayer = GameUtilities.getUtilities().getGamePlayer(event.getPlayer());
        if (gPlayer.isIngame()) {
            Game game = gPlayer.getGame();
            //if (game != null) {
                game.leaveGame(gPlayer.getPlayer());
            //}
            game.leaveGame(gPlayer.getPlayer());
        }
        SpectateCommand.getCommand().stopSpectating(event.getPlayer());
        SpectateCommand.getCommand().playerLogout(event.getPlayer());
        GameUtilities.getUtilities().playerLeaveServer(event.getPlayer());
    }
}
