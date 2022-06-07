package pvptoggle;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.Whitelist;
import net.minecraft.server.WhitelistEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class PvpWhitelist {
    private static final Logger LOGGER = LogManager.getLogger();
    private static Whitelist pvpWhitelist;

    // create and load whitelist
    public static void create(File file) {
        // uses vanilla whitelist class for handling pvp whitelisting
        pvpWhitelist = new Whitelist(file);
        load();
    }

    // load data from whitelist file
    public static void load() {
        try {
            pvpWhitelist.load();
        }
        catch(Exception error) {
            LOGGER.error("Failed to load pvp whitelist: ", error);
        }
    }

    // check if player is in whitelist
    public static boolean contains(GameProfile player) {
        return pvpWhitelist.isAllowed(player);
    }

    // add player to whitelist
    public static void addPlayer(GameProfile player) {
        pvpWhitelist.add(new WhitelistEntry(player));
    }

    // remove player from whitelist
    public static void removePlayer(GameProfile player) {
        pvpWhitelist.remove(player);
    }

    // get list of players in whitelist
    public static String[] getPlayers() {
        return pvpWhitelist.getNames();
    }
}
