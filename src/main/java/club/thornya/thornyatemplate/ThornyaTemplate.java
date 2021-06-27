package club.thornya.thornyatemplate;


import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ThornyaTemplate extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("Ligando");
    }

    @Override
    public void onLoad() {
        Bukkit.getConsoleSender().sendMessage("Carregando");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("Desligando");
    }


}