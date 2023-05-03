package com.chaseoes.tf2.listeners;

import com.chaseoes.tf2.*;
import com.chaseoes.tf2.localization.Localizers;
import com.chaseoes.tf2.utilities.GeneralUtilities;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

//import com.chaseoes.tf2.classes.TF2Class;
import com.chaseoes.tf2.events.TF2DeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static org.bukkit.Bukkit.getServer;


public class TF2DeathListener implements Listener {

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onDeath(final TF2DeathEvent event) {
        TF2.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(TF2.getInstance(), new Runnable() {
            @Override
            public void run() {
                final Player player = event.getPlayer();
                final GamePlayer playerg = GameUtilities.getUtilities().getGamePlayer(player);
                final Player killer = event.getKiller();
                GamePlayer killerg = GameUtilities.getUtilities().getGamePlayer(killer);

                Game game = playerg.getGame();
                if (game == null) {
                    return;
                }
                Map map = game.getMap();

                killer.playSound(killer.getLocation(), Sound.valueOf(TF2.getInstance().getConfig().getString("killsound.sound")), TF2.getInstance().getConfig().getInt("killsound.volume"), TF2.getInstance().getConfig().getInt("killsound.pitch"));

                TF2.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(TF2.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        playerg.setJustSpawned(false);
                    }
                }, 40L);


                // Run Commands + modification suicide
                if (player.getPlayer() != killer.getPlayer()){
                    GeneralUtilities.runCommands("on-kill", killer.getPlayer(), player.getPlayer(), game.getMap());
                }


                // Reset the kills of the player who died.
/*                playerg.addKillstreak(playerg.getKills());
                playerg.setKills(0);
                playerg.setDeaths(-1);
                playerg.settotalDeaths(-1);*/

                 //Add one kill to the kills the killer has made.
/*                if (!playerg.getName().equalsIgnoreCase(killerg.getName())) {
                    killerg.setTotalKills(-1);
                    killer.setLevel(killerg.getTotalKills());
                    killerg.setKills(-1);

                    int kills = killerg.getKills();
                    if (kills % TF2.getInstance().getConfig().getInt("kill streaks") == 0) {
                        game.broadcast(ChatColor.YELLOW + "[TeamFortress] " + killerg.getTeamColor() + killer.getName() + " " + ChatColor.RESET + ChatColor.YELLOW + "is on a " + ChatColor.DARK_RED + ChatColor.BOLD + "" + kills + " " + ChatColor.RESET + ChatColor.YELLOW + "kill streak!");
                    }
                }*/


                //player.teleport(MapUtilities.getUtilities().loadTeamSpawn(map.getName(), playerg.getTeam())); //teleport
                player.setFireTicks(0);
                //TF2Class c = playerg.getCurrentClass();
                //c.apply(playerg);

                playerg.setJustSpawned(true);
                playerg.setIsDead(false);
                playerg.setPlayerLastDamagedBy(null);
                //game.getScoreboard().updateBoard();

            }
        }, 1l);
    }

    /////////////////////////////////////////Sahurows////////////////////
    World CreationSpawn = Bukkit.getServer().getWorld(TF2.getInstance().getConfig().getString("LobbyName"));
    double x = TF2.getInstance().getConfig().getDouble("LobbyCoord.x");
    double y = TF2.getInstance().getConfig().getDouble("LobbyCoord.y");
    double z = TF2.getInstance().getConfig().getDouble("LobbyCoord.z");
    Location NewSpawn = new Location(CreationSpawn, x, y, z);

    @EventHandler (priority = EventPriority.HIGHEST)
    public void PlayerRespawnEvent(final PlayerRespawnEvent event) {
                final Player player = event.getPlayer();
                final GamePlayer playerg = GameUtilities.getUtilities().getGamePlayer(player);


                Game game = playerg.getGame();
                if (game == null) {
                    return;
                }
                Map map = game.getMap();

                if((GameUtilities.getUtilities().getGame(map).getStatus() != GameStatus.DISABLED)){
                    player.sendMessage(ChatColor.RED + "You revived!");
                    player.sendMessage(ChatColor.RED + "Pick Your class!");

                    player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0));


                    //Custom World spawn Set
                    if (playerg.getTeam() == Team.BLUE){
                        //World Castle = getServer().getWorld("castle1");


                        //////////////////////Sahurows//////////////////////
                        //////////////////////Le spawn pour l'équipe avec un config//////////////////////

                        //Initialisation
                        String world = TF2.getInstance().getConfig().getString("MapName1");
                        double x = 0, y = 0, z = 0;
                        float yRot = 0, xRot = 0;
                        //map Condition Pour plus de maps
                        if (player.getWorld() == Bukkit.getServer().getWorld(TF2.getInstance().getConfig().getString("MapName1"))){ //Map1
                            //On prend les données dans config
                            world = TF2.getInstance().getConfig().getString("MapName1");
                            x = TF2.getInstance().getConfig().getDouble("SpawnMaps1.BlueSpawn1.x");
                            y = TF2.getInstance().getConfig().getDouble("SpawnMaps1.BlueSpawn1.y");
                            z = TF2.getInstance().getConfig().getDouble("SpawnMaps1.BlueSpawn1.z");
                            yRot = (float) TF2.getInstance().getConfig().getDouble("SpawnMaps1.BlueSpawn1.yRot");
                            xRot = (float) TF2.getInstance().getConfig().getDouble("SpawnMaps1.BlueSpawn1.xRot");
                        }
                        if (player.getWorld() == Bukkit.getServer().getWorld(TF2.getInstance().getConfig().getString("MapName2"))){ //Map2
                            //On prend les données dans config
                            world = TF2.getInstance().getConfig().getString("MapName2");
                            x = TF2.getInstance().getConfig().getDouble("SpawnMaps2.BlueSpawn2.x");
                            y = TF2.getInstance().getConfig().getDouble("SpawnMaps2.BlueSpawn2.y");
                            z = TF2.getInstance().getConfig().getDouble("SpawnMaps2.BlueSpawn2.z");
                            yRot = (float) TF2.getInstance().getConfig().getDouble("SpawnMaps2.BlueSpawn2.yRot");
                            xRot = (float) TF2.getInstance().getConfig().getDouble("SpawnMaps2.BlueSpawn2.xRot");
                        }
                        /////////////////////////////////////////////////////

                        //Nouvelle localisation
                        //Le spawn pour l'equipe'
                        Location BlueSpawn = new Location(Bukkit.getWorld(world), x, y, z, xRot, yRot);


                        /////////////////////////////////////////////////////

                        event.setRespawnLocation(BlueSpawn);
                    }
                    if (playerg.getTeam() == Team.RED){


                        //////////////////////Sahurows//////////////////////
                        //////////////////////Le spawn pour l'équipe avec un config//////////////////////

                        //Initialisation
                        String world = TF2.getInstance().getConfig().getString("MapName1");
                        double x = 0, y = 0, z = 0;
                        float yRot = 0, xRot = 0;
                        //map Condition Pour plus de maps
                        if (player.getWorld() == Bukkit.getServer().getWorld(TF2.getInstance().getConfig().getString("MapName1"))){ //Map1
                            //On prend les données dans config
                            world = TF2.getInstance().getConfig().getString("MapName1");
                            x = TF2.getInstance().getConfig().getDouble("SpawnMaps1.RedSpawn1.x");
                            y = TF2.getInstance().getConfig().getDouble("SpawnMaps1.RedSpawn1.y");
                            z = TF2.getInstance().getConfig().getDouble("SpawnMaps1.RedSpawn1.z");
                            yRot = (float) TF2.getInstance().getConfig().getDouble("SpawnMaps1.RedSpawn1.yRot");
                            xRot = (float) TF2.getInstance().getConfig().getDouble("SpawnMaps1.RedSpawn1.xRot");
                        }
                        if (player.getWorld() == Bukkit.getServer().getWorld(TF2.getInstance().getConfig().getString("MapName2"))){ //Map2
                            //On prend les données dans config
                            world = TF2.getInstance().getConfig().getString("MapName2");
                            x = TF2.getInstance().getConfig().getDouble("SpawnMaps2.RedSpawn2.x");
                            y = TF2.getInstance().getConfig().getDouble("SpawnMaps2.RedSpawn2.y");
                            z = TF2.getInstance().getConfig().getDouble("SpawnMaps2.RedSpawn2.z");
                            yRot = (float) TF2.getInstance().getConfig().getDouble("SpawnMaps2.RedSpawn2.yRot");
                            xRot = (float) TF2.getInstance().getConfig().getDouble("SpawnMaps2.RedSpawn2.xRot");
                        }
                        /////////////////////////////////////////////////////

                        //Nouvelle localisation
                        //Le spawn pour l'equipe'
                        Location RedSpawn = new Location(Bukkit.getWorld(world), x, y, z, xRot, yRot);


                        /////////////////////////////////////////////////////
                        event.setRespawnLocation(RedSpawn);
                    }
                    //event.setRespawnLocation(MapUtilities.getUtilities().loadTeamSpawn(map.getName(), playerg.getTeam()));

                    //test
/*                    player.sendMessage(ChatColor.RED + "You quit the game!");
                    playerg.getGame().leaveGame(playerg.getPlayer());
                    Localizers.getDefaultLoc().PLAYER_LEAVE_GAME.sendPrefixed(player);*/


                    if(player.getWorld().equals(CreationSpawn)) {
                        player.teleport(MapUtilities.getUtilities().loadTeamSpawn(map.getName(), playerg.getTeam())); //teleport
                    }
                }
                else{
                    event.setRespawnLocation(NewSpawn);
                    player.teleport(NewSpawn);
                }
    }
}
