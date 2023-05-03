package com.chaseoes.tf2.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import com.chaseoes.tf2.GamePlayer;
import com.chaseoes.tf2.GameStatus;
import com.chaseoes.tf2.GameUtilities;
import com.chaseoes.tf2.TF2;
import com.chaseoes.tf2.events.TF2DeathEvent;

public class EntityDamageListener implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (event.getEntity() instanceof Player) {
            GamePlayer gp = GameUtilities.getUtilities().getGamePlayer((Player) event.getEntity());
            //Ajout sahurows --> != EntityDamageEvent.DamageCause.MAGIC && event.getCause() != EntityDamageEvent.DamageCause.FALL
            if (gp.isIngame() && !gp.isDead() && event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK && event.getCause() != EntityDamageEvent.DamageCause.PROJECTILE && event.getCause() != EntityDamageEvent.DamageCause.MAGIC && event.getCause() != EntityDamageEvent.DamageCause.FALL) {
                if (gp.getGame().getStatus() == GameStatus.INGAME && !gp.isInLobby()) {
                    if (gp.getPlayer().getHealth() - event.getDamage() <= 0) {
                        if (gp.getPlayerLastDamagedBy() == null) {
                            TF2.getInstance().getServer().getPluginManager().callEvent(new TF2DeathEvent(gp.getPlayer(), gp.getPlayer()));
                        } else {
                            TF2.getInstance().getServer().getPluginManager().callEvent(new TF2DeathEvent(gp.getPlayer(), gp.getPlayerLastDamagedBy().getPlayer()));
                        }
                        gp.setIsDead(true);
                        event.setCancelled(true);
                        return;
                    }
                } else {
                    event.setCancelled(true);
                }
            }
        }
    }
}
