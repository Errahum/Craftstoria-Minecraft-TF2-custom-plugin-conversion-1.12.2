package com.chaseoes.tf2.listeners;

import com.chaseoes.tf2.*;
import com.chaseoes.tf2.localization.Localizers;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.chaseoes.tf2.capturepoints.CapturePoint;
import com.chaseoes.tf2.capturepoints.CapturePointUtilities;

import static org.bukkit.Bukkit.getServer;

public class PlayerMoveListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMove(PlayerMoveEvent event) {
        if (!(event.getFrom().getBlockX() != event.getTo().getBlockX() || event.getFrom().getBlockY() != event.getTo().getBlockY() || event.getFrom().getBlockZ() != event.getTo().getBlockZ())) {
            return;
        }

        if (GameUtilities.getUtilities().getGamePlayer(event.getPlayer()).isInLobby() || !GameUtilities.getUtilities().getGamePlayer(event.getPlayer()).isIngame()) {
            return;
        }

        Player player = event.getPlayer();
        GamePlayer gp = GameUtilities.getUtilities().getGamePlayer(player);
        GamePlayer gp2 = GameUtilities.getUtilities().getGamePlayer2(player); //////////2

        Block b = event.getTo().getBlock();
        TF2 plugin = TF2.getInstance();

        Map map = plugin.getMap(CapturePointUtilities.getUtilities().getMapFromLocation(b.getLocation()));
        if (map == null) {
            return;
        }

        // Capture Points 1
        World MapName1 = Bukkit.getServer().getWorld(TF2.getInstance().getConfig().getString("MapName1"));

        //Initialisation
        String world = plugin.getConfig().getString("MapName1");
        double x = 0, y = 0, z = 0;
        float yRot = 0, xRot = 0;


      //On prend les données dans config
        world = plugin.getConfig().getString("MapName1");
        x = plugin.getConfig().getDouble("SpawnMaps1.RedSpawn1.x"); //CP
        y = plugin.getConfig().getDouble("SpawnMaps1.RedSpawn1.y"); //CP
        z = plugin.getConfig().getDouble("SpawnMaps1.RedSpawn1.z"); //CP

/*        customConfig.set("region.p1.w", p1.getWorld().getName());
        customConfig.set("region.p1.x", p1.getBlockX());
        customConfig.set("region.p1.y", p1.getBlockY());
        customConfig.set("region.p1.z", p1.getBlockZ());*/




        if (gp.getPlayer().getWorld().equals(MapName1)){ //test
        if (gp.isIngame() && CapturePointUtilities.getUtilities().locationIsCapturePoint(b.getLocation())) { //&& GameUtilities.getUtilities().games.get(map.getName()).getStatus() == GameStatus.INGAME
            Integer id = CapturePointUtilities.getUtilities().getIDFromLocation(b.getLocation());
            CapturePoint cp = map.getCapturePoint(id);
            if (gp.getTeam() == Team.RED) {

                if (cp.getStatus().string().equalsIgnoreCase("uncaptured")) {
                    if (TF2.getInstance().getConfig().getBoolean("capture-in-order")) { //order option
                        if (CapturePointUtilities.getUtilities().capturePointBeforeHasBeenCaptured(map, id)) {
                            if (cp.capturing == null) {
                                cp.startCapturing(gp);
                            }
                        } else {
                            Localizers.getDefaultLoc().CP_MUST_CAPTURE_PREVIOUS.sendPrefixed(event.getPlayer(), CapturePointUtilities.getUtilities().getFirstUncaptured(map).getId());
                        }
                    } else {
                        if (cp.capturing == null) { //il détetecte que dans la map c'est vide avec l'autre
                            cp.startCapturing(gp); ////////////////////  //getMap(map).getCapturePoint(id).getLocation(); //'1': castle2.51.65.81.94.08749.18.752876
                        }
                    }
                } else if (map.getCapturePoint(id).getStatus().string().equalsIgnoreCase("captured")) {
                    Localizers.getDefaultLoc().CP_ALREADY_CAPTURED_RED.sendPrefixed(event.getPlayer(), id + 1);
                } else if (map.getCapturePoint(id).getStatus().string().equalsIgnoreCase("capturing")) {
                    Localizers.getDefaultLoc().CP_ALREADY_CAPTURING.sendPrefixed(event.getPlayer(), cp.capturing.getName());
                }
            } else {
                if (!map.getCapturePoint(id).getStatus().string().equalsIgnoreCase("captured")) {
                    Localizers.getDefaultLoc().CP_WRONG_TEAM.sendPrefixed(event.getPlayer());
                } else {
                    Localizers.getDefaultLoc().CP_ALREADY_CAPTURED_BLUE.sendPrefixed(event.getPlayer());
                }
            }
        }
        }
        // Capture Points 2
        World MapName2 = Bukkit.getServer().getWorld(TF2.getInstance().getConfig().getString("MapName2"));
        if (gp2.getPlayer().getWorld().equals(MapName2)){ /////////2
            if (gp2.isIngame() && CapturePointUtilities.getUtilities().locationIsCapturePoint2(b.getLocation())) { //&& GameUtilities.getUtilities().games.get(map.getName()).getStatus() == GameStatus.INGAME
                Integer id2 = CapturePointUtilities.getUtilities().getIDFromLocation2(b.getLocation());
                CapturePoint cp2 = map.getCapturePoint2(id2);
                if (gp2.getTeam() == Team.RED) {

                    if (cp2.getStatus().string().equalsIgnoreCase("uncaptured2")) {
                        if (TF2.getInstance().getConfig().getBoolean("capture-in-order")) { //order option
                            if (CapturePointUtilities.getUtilities().capturePointBeforeHasBeenCaptured2(map, id2)) {
                                if (cp2.capturing == null) {
                                    cp2.startCapturing2(gp2);
                                }
                            } else {
                                Localizers.getDefaultLoc().CP_MUST_CAPTURE_PREVIOUS.sendPrefixed(event.getPlayer(), CapturePointUtilities.getUtilities().getFirstUncaptured2(map).getId());
                            }
                        } else {
                            if (cp2.capturing == null) {
                                cp2.startCapturing(gp2); ////////////////////  //getMap(map).getCapturePoint(id).getLocation(); //'1': castle2.51.65.81.94.08749.18.752876
                            }
                        }
                    } else if (map.getCapturePoint2(id2).getStatus().string().equalsIgnoreCase("captured2")) {
                        Localizers.getDefaultLoc().CP_ALREADY_CAPTURED_RED.sendPrefixed(event.getPlayer(), id2 + 1);
                    } else if (map.getCapturePoint2(id2).getStatus().string().equalsIgnoreCase("capturing2")) {
                        Localizers.getDefaultLoc().CP_ALREADY_CAPTURING.sendPrefixed(event.getPlayer(), cp2.capturing.getName());
                    }
                } else {
                    if (!map.getCapturePoint2(id2).getStatus().string().equalsIgnoreCase("captured2")) {
                        Localizers.getDefaultLoc().CP_WRONG_TEAM.sendPrefixed(event.getPlayer());
                    } else {
                        Localizers.getDefaultLoc().CP_ALREADY_CAPTURED_BLUE.sendPrefixed(event.getPlayer());
                    }
                }
            }
        }
    }

}
