package org.rei.checkmod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fml.network.FMLConnectionData;
import net.minecraftforge.fml.network.NetworkHooks;
import org.spongepowered.api.entity.living.player.Player;
class ModData implements IModData {
    private String Name;
    private String Version;

    public ModData(String it)
    {
        Name = it;
        Version = "0";
    }
    @Override
    public String getName() {
        return Name;
    }

    @Override
    public String getVersion() {
        return Version;
    }
}
public class CheckModAPI {

    public static List<IModData> getMods(Player player)
    {
        List<IModData> mods = new ArrayList<>();
        ServerPlayer serverPlayer = (ServerPlayer)player;
        FMLConnectionData connectionData = NetworkHooks.getConnectionData(serverPlayer.connection.connection);
        if (connectionData == null) return mods;
        ImmutableList<String> modList = connectionData.getModList();
        for (String mod: modList) {
            mods.add(new ModData(mod));
        }
        return mods;
    }
}
