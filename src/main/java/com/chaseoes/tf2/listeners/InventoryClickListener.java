package com.chaseoes.tf2.listeners;

import com.chaseoes.tf2.GamePlayer;
import com.chaseoes.tf2.GameUtilities;
import com.chaseoes.tf2.TF2;
import com.chaseoes.tf2.localization.Localizers;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class InventoryClickListener implements Listener {

    private TF2 pl;

    public InventoryClickListener(TF2 tf2) {
        pl = tf2;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        //Sahurows// Armor Only
        if (event.getWhoClicked() != null && event.getWhoClicked() instanceof Player && event.getSlotType() == InventoryType.SlotType.ARMOR) {
            GamePlayer gp = GameUtilities.getUtilities().getGamePlayer((Player) event.getWhoClicked());

            if (gp.isIngame() && pl.getConfig().getBoolean("prevent-inventory-moving")){
                    event.setCancelled(true);
                    Localizers.getDefaultLoc().PLAYER_INVENTORY_MOVING_BLOCKED.sendPrefixed(gp.getPlayer());
            }
        }
    }
}
