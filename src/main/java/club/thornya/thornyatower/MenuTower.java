package club.thornya.thornyatower;

import club.thornya.thornyatower.Listeners.TowerTexturePack;
import club.thornya.thornyatower.Utils.ConfigAPI;
import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.FontImages.FontImageWrapper;
import me.clip.placeholderapi.PlaceholderAPI;
import me.mattstudios.mfgui.gui.components.util.ItemBuilder;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import me.mattstudios.mfgui.gui.guis.PaginatedGui;
import me.mattstudios.mfgui.gui.guis.ScrollingGui;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class MenuTower implements CommandExecutor {

    private int numPage = 1;
    public static ArrayList<String> playerIsOpenInv = new ArrayList<String>();

    @Override
    public boolean onCommand(@NotNull CommandSender snd, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {

        if(!(snd instanceof Player)){
            Bukkit.getConsoleSender().sendMessage("§4Comandos somente para jogadores!");
        }else {
            Player p = (Player) snd;
            if (cmd.getName().equalsIgnoreCase("torre")) {
                //if (TowerTexturePack.playerWTexture.contains(p.getName())) {
                    p.sendMessage("Parabéns usando textura puto");
                    numPage = 1;
                    PaginatedGui gui = new PaginatedGui(6, ":offset_-8::tower" + numPage + ":");
                    gui.setDefaultClickAction(event -> {
                        event.setCancelled(true);
                        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR)
                            return;
                        if (event.isShiftClick() && event.isLeftClick() || event.isLeftClick() || event.isRightClick()) {
                            event.setCancelled(true);
                        }
                        if (event.getCursor() != null) {
                            event.setCancelled(true);
                        }
                    });
                    updatePage(numPage, gui, p);
                    gui.open(p);
                    if(!playerIsOpenInv.contains(p.getName())){
                        playerIsOpenInv.add(p.getName());
                    }
                }else {
                    p.sendMessage("A torre só funciona com textura, sorry!");
                }
            //}
        }

        return false;
    }

    private void updatePage(Integer page, PaginatedGui gui, Player p){
        List<String> loreItens = new ArrayList<String>();
        CustomStack towers = CustomStack.getInstance("empty_tower");
        List<Object> itemReverse = Arrays.asList(ConfigAPI.getFile("cache").getStringList("towers.menu_" + page).toArray());
        Collections.reverse(itemReverse);

        int menuID = ConfigAPI.getFile("cache").getStringList("towers.menu_" + page).size();
        if(!(numPage == 17)) {
            if(!(numPage == 5)) {
                gui.setItem(2, 8, ItemBuilder.from(towers.getItemStack()).setName(PlaceholderAPI.setPlaceholders(p, "§f%img_up% Subir")).asGuiItem(event -> {
                    numPage++;
                    updatePage(numPage, gui, p);
                    p.sendMessage("Página Atual: " + numPage);
                }));
            }else{
                gui.setItem(2, 8, ItemBuilder.from(towers.getItemStack()).setName(PlaceholderAPI.setPlaceholders(p, "§c%img_lock% Novidades em Breve %img_lock%")).asGuiItem(event -> {
                    event.setCancelled(true);
                }));
            }
        }else gui.removeItem(2, 8);
        if(!(numPage == 1)) {
            gui.setItem(3, 8, ItemBuilder.from(towers.getItemStack()).setName("Descer").asGuiItem(event -> {
                numPage--;
                updatePage(numPage, gui, p);
                p.sendMessage("Página Atual: " + numPage);
            }));
        }else gui.removeItem(3, 8);

        int i = 1;

        while (i <= menuID){
            int itemID = Integer.parseInt(itemReverse.get(i-1).toString());
            loreItens.clear();

            if(itemID == 1){
                loreItens.add(PlaceholderAPI.setPlaceholders(p, "§f%img_offset_-25%%img_towerlore%%img_offset_-20%"));
                loreItens.add(" ");
                //loreItens.add("§a█ &eSala " + itemID + " &f- &4&lBoss Level");
                loreItens.add("§a█ §eSalão Principal");
                loreItens.add("§2Sala livre para batalhar");
                //loreItens.add("&cBatalha em andamento");
                if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".drops.rare")
                || ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".drops.legendary")
                || ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".drops.common")) {
                    loreItens.add(" ");
                    loreItens.add("§fPossíveis drops:");
                    if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".drops.common")){
                        loreItens.add(PlaceholderAPI.setPlaceholders(p, "§f%img_common%"));
                    }if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".drops.rare")){
                        loreItens.add(PlaceholderAPI.setPlaceholders(p, "§f%img_rare%"));
                    }if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".drops.legendary")){
                        loreItens.add(PlaceholderAPI.setPlaceholders(p, "§f%img_legendary%"));
                    }
                }
                if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".tools.shovel")
                        || ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".tools.axe")
                        || ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".tools.pickaxe")) {
                    loreItens.add(" ");
                    loreItens.add("§6§lEssa sala requer:");
                    if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".tools.pickaxe")) {
                        loreItens.add("§f× §dPicareta");
                    }
                    if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".tools.shovel")){
                        loreItens.add("§f× §dPá");
                    }
                    if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".tools.axe")){
                        loreItens.add("§f× §dMachado");
                    }


                }
                GuiItem base = ItemBuilder.from(towers.getItemStack()).setName("§cSalão Principal").setLore(loreItens).asGuiItem(event -> {
                    p.sendMessage("Você clicou na base");
					gui.close(p);
                    playerIsOpenInv.remove(p.getName());
                });
                gui.setItem(i, 5, base);
            }
            //else if(itemID == 20){
            //    towers.getItemStack().getItemMeta().setLore(loreMiddle);
            //    GuiItem middleUsed = ItemBuilder.from(towerUsed.getItemStack()).setName("Sala " + itemID).setLore(" ", "Batalha em Andamento").asGuiItem(event -> {
            //        if(event.getCurrentItem().getItemMeta().getLore().get(1).equalsIgnoreCase("Batalha em Andamento")){
            //            p.sendMessage("§cOutro clan já está dominando essa sala!");
            //            p.sendMessage("§cEspera sua vez morô?!");
            //            gui.close(p);
            //        }
            //    });
            //    gui.setItem(i, 5, middleUsed);
            //}
            else if(itemID == 100){
                loreItens.add(PlaceholderAPI.setPlaceholders(p, "§f%img_offset_-25%%img_towerlore%%img_offset_-20%"));
                loreItens.add(" ");
                loreItens.add("§a█ §eSalão Final");
                loreItens.add("§2Sala livre para batalhar");
                //loreItens.add("&cBatalha em andamento");
                if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".drops.rare")
                        || ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".drops.legendary")
                        || ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".drops.common")) {
                    loreItens.add(" ");
                    loreItens.add("§fPossíveis drops:");
                    if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".drops.common")){
                        loreItens.add(PlaceholderAPI.setPlaceholders(p, "§f%img_common%"));
                    }if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".drops.rare")){
                        loreItens.add(PlaceholderAPI.setPlaceholders(p, "§f%img_rare%"));
                    }if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".drops.legendary")){
                        loreItens.add(PlaceholderAPI.setPlaceholders(p, "§f%img_legendary%"));
                    }
                }
                if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".tools.shovel")
                        || ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".tools.axe")
                        || ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".tools.pickaxe")) {
                    loreItens.add(" ");
                    loreItens.add("§6§lEssa sala requer:");
                    if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".tools.pickaxe")) {
                        loreItens.add("§f× §dPicareta");
                    }
                    if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".tools.shovel")){
                        loreItens.add("§f× §dPá");
                    }
                    if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".tools.axe")){
                        loreItens.add("§f× §dMachado");
                    }


                }
                GuiItem top = ItemBuilder.from(towers.getItemStack()).setName("§4Batalha Final").setLore(loreItens).asGuiItem(event -> {
                    p.sendMessage("Você clicou no top - Bem vindo ao inferno");
					gui.close(p);
                    playerIsOpenInv.remove(p.getName());
                });
                gui.setItem(2, 5, top);
                gui.removeItem(6, 5);
                gui.removeItem(1, 5);
            }else if(itemID == 99){
                loreItens.add(PlaceholderAPI.setPlaceholders(p, "§f%img_offset_-25%%img_towerlore%%img_offset_-20%"));
                loreItens.add(" ");
                //loreItens.add("§a█ &eSala " + itemID + " &f- &4&lBoss Level");
                loreItens.add("§a█ §eSala " + itemID);
                loreItens.add("§2Sala livre para batalhar");
                //loreItens.add("&cBatalha em andamento");
                if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".drops.rare")
                        || ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".drops.legendary")
                        || ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".drops.common")) {
                    loreItens.add(" ");
                    loreItens.add("§fPossíveis drops:");
                    if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".drops.common")){
                        loreItens.add(PlaceholderAPI.setPlaceholders(p, "§f%img_common%"));
                    }if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".drops.rare")){
                        loreItens.add(PlaceholderAPI.setPlaceholders(p, "§f%img_rare%"));
                    }if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".drops.legendary")){
                        loreItens.add(PlaceholderAPI.setPlaceholders(p, "§f%img_legendary%"));
                    }
                }
                if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".tools.shovel")
                        || ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".tools.axe")
                        || ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".tools.pickaxe")) {
                    loreItens.add(" ");
                    loreItens.add("§6§lEssa sala requer:");
                    if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".tools.pickaxe")) {
                        loreItens.add("§f× §dPicareta");
                    }
                    if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".tools.shovel")){
                        loreItens.add("§f× §dPá");
                    }
                    if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".tools.axe")){
                        loreItens.add("§f× §dMachado");
                    }


                }
                GuiItem middle99 = ItemBuilder.from(towers.getItemStack()).setName("§fSala " + 99).setLore(loreItens).asGuiItem(event -> {
                    p.sendMessage("Você clicou no meio - " + 99);
                    gui.close(p);
                    playerIsOpenInv.remove(p.getName());
                });
                gui.setItem(3, 5, middle99);
            }else if(itemID == 98){
                loreItens.add(PlaceholderAPI.setPlaceholders(p, "§f%img_offset_-25%%img_towerlore%%img_offset_-20%"));
                loreItens.add(" ");
                //loreItens.add("§a█ &eSala " + itemID + " &f- &4&lBoss Level");
                loreItens.add("§a█ §eSala " + itemID);
                loreItens.add("§2Sala livre para batalhar");
                //loreItens.add("&cBatalha em andamento");
                if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".drops.rare")
                        || ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".drops.legendary")
                        || ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".drops.common")) {
                    loreItens.add(" ");
                    loreItens.add("§fPossíveis drops:");
                    if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".drops.common")){
                        loreItens.add(PlaceholderAPI.setPlaceholders(p, "§f%img_common%"));
                    }if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".drops.rare")){
                        loreItens.add(PlaceholderAPI.setPlaceholders(p, "§f%img_rare%"));
                    }if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".drops.legendary")){
                        loreItens.add(PlaceholderAPI.setPlaceholders(p, "§f%img_legendary%"));
                    }
                }
                if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".tools.shovel")
                        || ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".tools.axe")
                        || ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".tools.pickaxe")) {
                    loreItens.add(" ");
                    loreItens.add("§6§lEssa sala requer:");
                    if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".tools.pickaxe")) {
                        loreItens.add("§f× §dPicareta");
                    }
                    if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".tools.shovel")){
                        loreItens.add("§f× §dPá");
                    }
                    if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".tools.axe")){
                        loreItens.add("§f× §dMachado");
                    }


                }
                GuiItem middle98 = ItemBuilder.from(towers.getItemStack()).setName("§fSala " + 98).setLore(loreItens).asGuiItem(event -> {
                    p.sendMessage("Você clicou no meio - " + 98);
                    gui.close(p);
                    playerIsOpenInv.remove(p.getName());
                });
                gui.setItem(4, 5, middle98);
            }else if(itemID == 97){
                loreItens.add(PlaceholderAPI.setPlaceholders(p, "§f%img_offset_-25%%img_towerlore%%img_offset_-20%"));
                loreItens.add(" ");
                //loreItens.add("§a█ &eSala " + itemID + " &f- &4&lBoss Level");
                loreItens.add("§a█ §eSala " + itemID);
                loreItens.add("§2Sala livre para batalhar");
                //loreItens.add("&cBatalha em andamento");
                if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".drops.rare")
                        || ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".drops.legendary")
                        || ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".drops.common")) {
                    loreItens.add(" ");
                    loreItens.add("§fPossíveis drops:");
                    if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".drops.common")){
                        loreItens.add(PlaceholderAPI.setPlaceholders(p, "§f%img_common%"));
                    }if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".drops.rare")){
                        loreItens.add(PlaceholderAPI.setPlaceholders(p, "§f%img_rare%"));
                    }if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".drops.legendary")){
                        loreItens.add(PlaceholderAPI.setPlaceholders(p, "§f%img_legendary%"));
                    }
                }
                if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".tools.shovel")
                        || ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".tools.axe")
                        || ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".tools.pickaxe")) {
                    loreItens.add(" ");
                    loreItens.add("§6§lEssa sala requer:");
                    if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".tools.pickaxe")) {
                        loreItens.add("§f× §dPicareta");
                    }
                    if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".tools.shovel")){
                        loreItens.add("§f× §dPá");
                    }
                    if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".tools.axe")){
                        loreItens.add("§f× §dMachado");
                    }


                }
                GuiItem middle97 = ItemBuilder.from(towers.getItemStack()).setName("§fSala " + 97).setLore(loreItens).asGuiItem(event -> {
                    p.sendMessage("Você clicou no meio - " + 97);
                    gui.close(p);
                    playerIsOpenInv.remove(p.getName());
                });
                gui.setItem(5, 5, middle97);
            }else if(itemID == 96){
                loreItens.add(PlaceholderAPI.setPlaceholders(p, "§f%img_offset_-25%%img_towerlore%%img_offset_-20%"));
                loreItens.add(" ");
                //loreItens.add("§a█ &eSala " + itemID + " &f- &4&lBoss Level");
                loreItens.add("§a█ §eSala " + itemID);
                loreItens.add("§2Sala livre para batalhar");
                //loreItens.add("&cBatalha em andamento");
                if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".drops.rare")
                        || ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".drops.legendary")
                        || ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".drops.common")) {
                    loreItens.add(" ");
                    loreItens.add("§fPossíveis drops:");
                    if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".drops.common")){
                        loreItens.add(PlaceholderAPI.setPlaceholders(p, "§f%img_common%"));
                    }if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".drops.rare")){
                        loreItens.add(PlaceholderAPI.setPlaceholders(p, "§f%img_rare%"));
                    }if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".drops.legendary")){
                        loreItens.add(PlaceholderAPI.setPlaceholders(p, "§f%img_legendary%"));
                    }
                }
                if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".tools.shovel")
                        || ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".tools.axe")
                        || ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".tools.pickaxe")) {
                    loreItens.add(" ");
                    loreItens.add("§6§lEssa sala requer:");
                    if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".tools.pickaxe")) {
                        loreItens.add("§f× §dPicareta");
                    }
                    if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".tools.shovel")){
                        loreItens.add("§f× §dPá");
                    }
                    if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".tools.axe")){
                        loreItens.add("§f× §dMachado");
                    }


                }
                GuiItem middle96 = ItemBuilder.from(towers.getItemStack()).setName("§fSala " + 96).setLore(loreItens).asGuiItem(event -> {
                    p.sendMessage("Você clicou no meio - " + 96);
                    gui.close(p);
                    playerIsOpenInv.remove(p.getName());
                });
                gui.setItem(6, 5, middle96);
            }else {
                loreItens.add(PlaceholderAPI.setPlaceholders(p, "§f%img_offset_-25%%img_towerlore%%img_offset_-20%"));
                loreItens.add(" ");
                //loreItens.add("§a█ &eSala " + itemID + " &f- &4&lBoss Level");
                loreItens.add("§a█ §eSala " + itemID);
                loreItens.add("§2Sala livre para batalhar");
                //loreItens.add("&cBatalha em andamento");
                if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".drops.rare")
                        || ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".drops.legendary")
                        || ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".drops.common")) {
                    loreItens.add(" ");
                    loreItens.add("§fPossíveis drops:");
                    if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".drops.common")){
                        loreItens.add(PlaceholderAPI.setPlaceholders(p, "§f%img_common%"));
                    }if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".drops.rare")){
                        loreItens.add(PlaceholderAPI.setPlaceholders(p, "§f%img_rare%"));
                    }if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".drops.legendary")){
                        loreItens.add(PlaceholderAPI.setPlaceholders(p, "§f%img_legendary%"));
                    }
                }
                if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".tools.shovel")
                        || ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".tools.axe")
                        || ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".tools.pickaxe")) {
                    loreItens.add(" ");
                    loreItens.add("§6§lEssa sala requer:");
                    if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".tools.pickaxe")) {
                        loreItens.add("§f× §dPicareta");
                    }
                    if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".tools.shovel")){
                        loreItens.add("§f× §dPá");
                    }
                    if(ConfigAPI.getFile("cache").getBoolean("items.room" + itemID + ".tools.axe")){
                        loreItens.add("§f× §dMachado");
                    }


                }
                GuiItem middle = ItemBuilder.from(towers.getItemStack()).setName("§fSala " + itemID).setLore(loreItens).asGuiItem(event -> {
                    p.sendMessage("Você clicou no meio - " + itemID);
                    gui.close(p);
                    playerIsOpenInv.remove(p.getName());
                });
                gui.setItem(i, 5, middle);
            }
            i++;
        }
        gui.updateTitle(":offset_-8::tower" + numPage + ":");
        gui.update();
        if(!playerIsOpenInv.contains(p.getName())){
            playerIsOpenInv.add(p.getName());
        }

    }

}
