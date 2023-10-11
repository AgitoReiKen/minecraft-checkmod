package org.rei.checkmod;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.LinearComponents;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fml.network.FMLConnectionData;
import net.minecraftforge.fml.network.NetworkHooks;
import org.apache.logging.log4j.Logger;
import org.spongepowered.api.Server;
import org.spongepowered.api.command.Command;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.ConstructPluginEvent;
import org.spongepowered.api.event.lifecycle.RegisterCommandEvent;
import org.spongepowered.api.event.lifecycle.StartingEngineEvent;
import org.spongepowered.api.event.lifecycle.StoppingEngineEvent;
import org.spongepowered.api.event.network.ServerSideConnectionEvent;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;

import java.util.ArrayList;
import java.util.List;

@Plugin("checkmod")
public class CheckMod {
    private final PluginContainer container;
    private final Logger logger;

    @Inject
    CheckMod(final PluginContainer container, final Logger logger) {
        this.container = container;
        this.logger = logger;
    }

    @Listener
    public void onConstructPlugin(final ConstructPluginEvent event) {
        // Perform any one-time setup
        this.logger.info("Constructing CheckMod");
    }

    @Listener
    public void onServerStarting(final StartingEngineEvent<Server> event) {
        // Any setup per-game instance. This can run multiple times when
        // using the integrated (singleplayer) server.
    }

    @Listener
    public void onServerStopping(final StoppingEngineEvent<Server> event) {
        // Any tear down per-game instance. This can run multiple times when
        // using the integrated (singleplayer) server.
    }

    @Listener
    public void onRegisterCommands(final RegisterCommandEvent<Command.Parameterized> event) {
        event.register(this.container, Command.builder()
                .executor(ctx -> {
                    ctx.sendMessage(Identity.nil(), LinearComponents.linear(
                            NamedTextColor.AQUA,
                            Component.text("Your mods are:\n")
                    ));

                    return CommandResult.success();
                })
                .build(), "checkmod", "cm");
    }
    @Listener
    public void onConnection(ServerSideConnectionEvent.Join event)
    {
        logger.info("Player joined {}", event.player().identity().uuid().toString());
        ServerPlayer serverPlayer = (ServerPlayer)event.player();
        FMLConnectionData connectionData = NetworkHooks.getConnectionData(serverPlayer.connection.connection);
        if (connectionData == null) return;
        ImmutableList<String> mods = connectionData.getModList();

        int i = 0;
        for (String mod: mods) {
            logger.info("{} = {}", ++i, mod);
        }
    }
}
