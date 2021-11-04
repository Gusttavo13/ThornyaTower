package club.thornya.thornyatower.Listeners;

import club.thornya.thornyatower.MenuTower;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

public class TowerTexturePack implements Listener {

    public static ArrayList<String> playerWTexture = new ArrayList<>();

    @EventHandler
    public void onCloseInventoryMenuTower(InventoryCloseEvent event){
        Player p = (Player) event.getPlayer();
        MenuTower.playerIsOpenInv.remove(p.getName());
    }

    @EventHandler
    public void onResourcepackStatusEvent(PlayerResourcePackStatusEvent event){
        if(event.getStatus() == PlayerResourcePackStatusEvent.Status.DECLINED || event.getStatus() == PlayerResourcePackStatusEvent.Status.FAILED_DOWNLOAD) {
            playerWTexture.remove(event.getPlayer().getName());
            //Bukkit.broadcastMessage("A porra do " + event.getPlayer().getName() + " entrou sem textura!");
        }
        if(event.getStatus() == PlayerResourcePackStatusEvent.Status.ACCEPTED){
            playerWTexture.add(event.getPlayer().getName());
            //Bukkit.broadcastMessage("O lindo do " + event.getPlayer().getName() + " entrou com textura!");
        }

    }

}
