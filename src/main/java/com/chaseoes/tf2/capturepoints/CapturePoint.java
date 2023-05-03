package com.chaseoes.tf2.capturepoints;

import com.chaseoes.tf2.localization.Localizers;
import com.chaseoes.tf2.utilities.GeneralUtilities;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.chaseoes.tf2.Game;
import com.chaseoes.tf2.GamePlayer;
import com.chaseoes.tf2.TF2;
import com.chaseoes.tf2.Team;

public class CapturePoint implements Comparable<CapturePoint> {

    String map;
    Integer id;
    Location location;
    Integer task = 0;
    Integer ptask = 0;
    CaptureStatus status;
    public GamePlayer capturing;

    public CapturePoint(String map, Integer i, Location loc) {
        capturing = null;

       // if (Bukkit.getServer().getWorld(TF2.getInstance().getConfig().getString("MapName1")).equals(capturing.getPlayer().getWorld())) { //bug
            setStatus(CaptureStatus.UNCAPTURED);
       // }
        //if (Bukkit.getServer().getWorld(TF2.getInstance().getConfig().getString("MapName2")).equals(capturing.getPlayer().getWorld())) { //bug
          //  setStatus(CaptureStatus.UNCAPTURED2); //2
        //}

        id = i;
        this.map = map;
        location = loc;
    }

    public Integer getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public CaptureStatus getStatus() {
        return status;
    }

    public void setStatus(CaptureStatus s) {
        status = s;
    }

    public void startCapturing(final GamePlayer player) {
        capturing = player;
        setStatus(CaptureStatus.CAPTURING);
        setStatus(CaptureStatus.CAPTURING2);
        Game game = capturing.getGame();
        game.broadcast(Localizers.getDefaultLoc().CP_BEING_CAPTURED.getPrefixedString(id));


        task = CapturePointUtilities.getUtilities().plugin.getServer().getScheduler().scheduleSyncRepeatingTask(CapturePointUtilities.getUtilities().plugin, new Runnable() {
            Integer timeRemaining = CapturePointUtilities.getUtilities().plugin.getConfig().getInt("capture-timer");
            Integer timeTotal = CapturePointUtilities.getUtilities().plugin.getConfig().getInt("capture-timer");
            Game game = capturing.getGame();
            double diff = 1.0d / (timeTotal * 20);
            int currentTick = 0;

            @Override
            public void run() {
                game.setExpOfPlayers(diff * currentTick);
                if (timeRemaining != 0 && currentTick % 20 == 0) {
                    if (TF2.getInstance().getConfig().getBoolean("lightning-while-capturing")) {
                        player.getPlayer().getWorld().strikeLightningEffect(player.getPlayer().getLocation());
                    }
                }

                if (timeRemaining == 0 && currentTick % 20 == 0) {
                    for (final String gp : game.getPlayersIngame()) {
                        final Player player = Bukkit.getPlayerExact(gp);
                        if (player == null) {
                            continue;
                        }
                        TF2.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(TF2.getInstance(), new Runnable() {
                            @Override
                            public void run() {
                                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 1);
                            }
                        }, 1L);
                    }
                    stopCapturing();


                    //if (Bukkit.getServer().getWorld(TF2.getInstance().getConfig().getString("MapName1")).equals(capturing.getPlayer().getWorld())) { //bug
                        setStatus(CaptureStatus.CAPTURED);
                   // }
                   // if (Bukkit.getServer().getWorld(TF2.getInstance().getConfig().getString("MapName2")).equals(capturing.getPlayer().getWorld())) { //bug
                     //   setStatus(CaptureStatus.CAPTURED2); //2
                   // }


                    player.setPointsCaptured(-1);
                    game.broadcast(Localizers.getDefaultLoc().CP_CAPTURED.getPrefixedString(id, player.getName()));
                    game.setExpOfPlayers(0);
                    
                    GeneralUtilities.runCommands("on-point-capture", player.getPlayer(), player.getPlayer(), game.getMap());

                    if (TF2.getInstance().getMap(map).allCaptured()) {
                        game.winMatch(Team.RED);
                        return;
                    }
                    if (TF2.getInstance().getMap(map).allCaptured2()) {
                        game.winMatch(Team.RED);
                        return;
                    }
                }
                currentTick++;
                if (currentTick % 20 == 0) {
                    timeRemaining--;
                    timeTotal++;
                }
            }
        }, 0L, 1L);

        ptask = CapturePointUtilities.getUtilities().plugin.getServer().getScheduler().scheduleSyncRepeatingTask(CapturePointUtilities.getUtilities().plugin, new Runnable() {
            @Override
            public void run() {
                GamePlayer p = capturing;
                if (p == null) {
                    stopCapturing();
                    return;
                }

                if (!CapturePointUtilities.getUtilities().locationIsCapturePoint(player.getPlayer().getLocation())) {
                    stopCapturing();
                    return;
                }
            }
        }, 0L, 1L);
    }
    public void startCapturing2(final GamePlayer player) { //2
        capturing = player;
        setStatus(CaptureStatus.CAPTURING2);
        Game game = capturing.getGame();
        game.broadcast(Localizers.getDefaultLoc().CP_BEING_CAPTURED.getPrefixedString(id));


        task = CapturePointUtilities.getUtilities().plugin.getServer().getScheduler().scheduleSyncRepeatingTask(CapturePointUtilities.getUtilities().plugin, new Runnable() {
            Integer timeRemaining = CapturePointUtilities.getUtilities().plugin.getConfig().getInt("capture-timer");
            Integer timeTotal = CapturePointUtilities.getUtilities().plugin.getConfig().getInt("capture-timer");
            Game game = capturing.getGame();
            double diff = 1.0d / (timeTotal * 20);
            int currentTick = 0;

            @Override
            public void run() {
                game.setExpOfPlayers(diff * currentTick);
                if (timeRemaining != 0 && currentTick % 20 == 0) {
                    if (TF2.getInstance().getConfig().getBoolean("lightning-while-capturing")) {
                        player.getPlayer().getWorld().strikeLightningEffect(player.getPlayer().getLocation());
                    }
                }

                if (timeRemaining == 0 && currentTick % 20 == 0) {
                    for (final String gp : game.getPlayersIngame()) {
                        final Player player = Bukkit.getPlayerExact(gp);
                        if (player == null) {
                            continue;
                        }
                        TF2.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(TF2.getInstance(), new Runnable() {
                            @Override
                            public void run() {
                                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 1);
                            }
                        }, 1L);
                    }
                    stopCapturing();

                    //if (Bukkit.getServer().getWorld(TF2.getInstance().getConfig().getString("MapName1")).equals(capturing.getPlayer().getWorld())) { //bug
                        setStatus(CaptureStatus.CAPTURED);
                    //}
                   // if (Bukkit.getServer().getWorld(TF2.getInstance().getConfig().getString("MapName2")).equals(capturing.getPlayer().getWorld())) { //bug
                    //    setStatus(CaptureStatus.CAPTURED2); //2
                    //}

                    player.setPointsCaptured(-1);
                    game.broadcast(Localizers.getDefaultLoc().CP_CAPTURED.getPrefixedString(id, player.getName()));
                    game.setExpOfPlayers(0);

                    GeneralUtilities.runCommands("on-point-capture", player.getPlayer(), player.getPlayer(), game.getMap());

                    if (TF2.getInstance().getMap(map).allCaptured()) {
                        game.winMatch(Team.RED);
                        return;
                    }
                    if (TF2.getInstance().getMap(map).allCaptured2()) {
                        game.winMatch(Team.RED);
                        return;
                    }
                }
                currentTick++;
                if (currentTick % 20 == 0) {
                    timeRemaining--;
                    timeTotal++;
                }
            }
        }, 0L, 1L);

        ptask = CapturePointUtilities.getUtilities().plugin.getServer().getScheduler().scheduleSyncRepeatingTask(CapturePointUtilities.getUtilities().plugin, new Runnable() {
            @Override
            public void run() {
                GamePlayer p = capturing;
                if (p == null) {
                    stopCapturing();
                    return;
                }

                if (!CapturePointUtilities.getUtilities().locationIsCapturePoint(player.getPlayer().getLocation())) {
                    stopCapturing();
                    return;
                }
            }
        }, 0L, 1L);
    }

    public void stopCapturing() {
        if (ptask != 0) {
            Bukkit.getScheduler().cancelTask(ptask);
            ptask = 0;
        }
        if (task != 0) {
            Bukkit.getScheduler().cancelTask(task);
            task = 0;
        }

        if (capturing != null && capturing.getGame() != null) {
            capturing.getGame().setExpOfPlayers(0d);
            capturing = null;
        }

        //if (Bukkit.getServer().getWorld(TF2.getInstance().getConfig().getString("MapName1")).equals(capturing.getPlayer().getWorld())) { //bug
            setStatus(CaptureStatus.UNCAPTURED);
        //}
        //if (Bukkit.getServer().getWorld(TF2.getInstance().getConfig().getString("MapName2")).equals(capturing.getPlayer().getWorld())) { //bug
        //   setStatus(CaptureStatus.UNCAPTURED2);
        //}


    }

    @Override
    public int compareTo(CapturePoint o) {
        return this.getId() - o.getId();
    }
}
