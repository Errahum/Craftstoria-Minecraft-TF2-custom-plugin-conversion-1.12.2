package com.chaseoes.tf2.listeners;

import com.chaseoes.tf2.*;
import com.chaseoes.tf2.localization.Localizers;
import com.chaseoes.tf2.utilities.GeneralUtilities;
import com.sun.org.apache.xpath.internal.objects.XNull;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import com.chaseoes.tf2.utilities.WorldEditUtilities;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static org.bukkit.Bukkit.getServer;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onLogin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        for (Map m : TF2.getInstance().getMaps()) {
            //if (m.getP1() != null && m.getP2() != null && WorldEditUtilities.getWEUtilities().isInMap(player.getLocation(), m)) {
            if(GameUtilities.getUtilities().getGame(m).getStatus() == GameStatus.WAITING) { //Test
                player.teleport(MapUtilities.getUtilities().loadLobby());
            }
            //}
        }

        GameUtilities.getUtilities().playerJoinServer(player);
        if (TF2.getInstance().getConfig().getBoolean("dedicated-join")) {
            player.performCommand("tf2 join " + MapUtilities.getUtilities().getRandomMap().getName());
        }
    }

    /////////////////////////////////////////Sahurows/////////////////////////////////////////
    // Spawn in game
    @EventHandler
    public void PlayerLevelChangeEvent(final PlayerChangedWorldEvent event) {
        TF2.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(TF2.getInstance(), new Runnable() {
            @Override
            public void run() {
                //Spawn world
                World CreationSpawn = Bukkit.getServer().getWorld(TF2.getInstance().getConfig().getString("LobbyName"));
                final Player player = event.getPlayer();


                //player.sendMessage(ChatColor.RED + "Change of world!");
                final GamePlayer playerg = GameUtilities.getUtilities().getGamePlayer(player);

                for(PotionEffect effect : player.getActivePotionEffects())
                {
                    player.removePotionEffect(effect.getType());
                }
                player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0));

                if ((player.getWorld().equals(CreationSpawn)) && (playerg.isIngame()) && (!(event.isAsynchronous()))) {
                    Game game = playerg.getGame();
                    Map map = game.getMap();

                    if ((map != null) && ((GameUtilities.getUtilities().getGame(map).getStatus() == GameStatus.INGAME) || (GameUtilities.getUtilities().getGame(map).getStatus() == GameStatus.STARTING) || (GameUtilities.getUtilities().getGame(map).getStatus() == GameStatus.WAITING))) {
                        GeneralUtilities.runCommands("on-Change-World", player.getPlayer(), player.getPlayer(), game.getMap());
                        //player.sendMessage(ChatColor.RED + "You quit the game!");
                        playerg.getGame().leaveGame(playerg.getPlayer());
                        Localizers.getDefaultLoc().PLAYER_LEAVE_GAME.sendPrefixed(player);

                    }
                }
            }
        }, 20l);
    }
}