package com.chaseoes.tf2.listeners;

import com.chaseoes.tf2.GamePlayer;
import com.chaseoes.tf2.GameUtilities;
import com.chaseoes.tf2.TF2;
import com.chaseoes.tf2.Team;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
//import com.chaseoes.tf2.classes.TF2Class;

public class PlayerRespawnListener implements Listener {

    @EventHandler
    public void onPlayerRespawn(final PlayerRespawnEvent event) {


        final GamePlayer gp = GameUtilities.getUtilities().getGamePlayer(event.getPlayer());
        final Player player = event.getPlayer();
/*        Game game = gp.getGame();
        if (game == null) {
            return;
        }*/
        //final Map map = game.getMap();

        //Custom World spawn Set
        if (gp.getTeam() == Team.BLUE){


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
        if (gp.getTeam() == Team.RED){


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
        //MapUtilities.getUtilities().loadTeamSpawn(map.getName(), gp.getTeam())
    }
}
