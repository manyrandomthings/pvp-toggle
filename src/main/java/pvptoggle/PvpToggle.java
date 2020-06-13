package pvptoggle;

import java.io.File;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;

public class PvpToggle implements DedicatedServerModInitializer {
  @Override
  public void onInitializeServer() {
    // set whitelist file to pvp_whitelist.json
    PvpWhitelist.create(new File("pvp_whitelist.json"));
    // register /pvp command
    CommandRegistrationCallback.EVENT.register(PvpCommand::register);
  }
}
