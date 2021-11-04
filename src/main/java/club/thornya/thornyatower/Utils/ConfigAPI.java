package club.thornya.thornyatower.Utils;

import club.thornya.thornyatower.ThornyaTower;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigAPI {

    private static File file = null;
    private static FileConfiguration fileC = null;

    public static void criarConfig(String nomedoarquivo){
        File fileVerifica = new File(ThornyaTower.TT.getDataFolder(), nomedoarquivo + ".yml");
        if(!fileVerifica.exists()){ ThornyaTower.TT.saveResource(nomedoarquivo + ".yml", false);}
    }

    public static FileConfiguration getFile(String nomedoarquivo){
        file = new File(ThornyaTower.TT.getDataFolder(), nomedoarquivo + ".yml");
        fileC = YamlConfiguration.loadConfiguration(file);

        return fileC;

    }

    public static void saveConfig(String nomedoarquivo){
        try {
            getFile(nomedoarquivo).save(file);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void reloadConfig(String nomedoarquivo){
        //this.tax.taxValue = this.getFile("configuration").getDouble("tax-prefeitura");
        //this.tax.taxClan = this.getFile("configuration").getDouble("tax-supremacia");
        //this.tax.taxTotal = this.getFile("configuration").getDouble("tax-prefeitura") + this.getFile("configuration").getDouble("tax-supremacia");
        if(file == null){
            file = new File(ThornyaTower.TT.getDataFolder(), nomedoarquivo);
        }
        fileC = YamlConfiguration.loadConfiguration(file);
        if(fileC != null){
            YamlConfiguration defaults = YamlConfiguration.loadConfiguration(file);
            fileC.setDefaults(defaults);
        }
    }

}
