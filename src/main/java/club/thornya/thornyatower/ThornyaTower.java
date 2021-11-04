package club.thornya.thornyatower;


import club.thornya.thornyatower.Database.SQLite;
import club.thornya.thornyatower.Listeners.TowerTexturePack;
import club.thornya.thornyatower.Utils.ConfigAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;


import java.io.IOException;
import java.util.Objects;

public final class ThornyaTower extends JavaPlugin {

    public static ThornyaTower TT;

    @Override
    public void onEnable() {
        TT = this;
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            carregarconfigs();
            registrarComandos();
            registrarListener(new TowerTexturePack());
            new SQLite();
        } else {
            Bukkit.getConsoleSender().sendMessage("PlaceholderAPI falhou.");
            Bukkit.getPluginManager().disablePlugin(this);
        }

    }
    @Override
    public void onLoad() {
    }

    @Override
    public void onDisable() {
    }

    public void carregarconfigs(){
        ConfigAPI.criarConfig("cache");
    }
    public void salvarConfig(){
        ConfigAPI.saveConfig("cache");
    }
    public void reloadAll(){
        salvarConfig();
        ConfigAPI.reloadConfig("cache");
    }
    public void registrarComandos(){
        registrarComando("torre", new MenuTower());
        //Objects.requireNonNull(getCommand("template")).setTabCompleter(new TabPrefeitura(this));
    }

    public void registrarComando(String nome, CommandExecutor comando) {
        Objects.requireNonNull(this.getCommand(nome)).setExecutor(comando);
    }

    public void registrarListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, this);
    }

}