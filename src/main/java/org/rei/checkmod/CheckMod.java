package org.rei.checkmod;

import com.google.inject.Inject;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.LinearComponents;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.Server;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.Command;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.parameter.Parameter;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.ConstructPluginEvent;
import org.spongepowered.api.event.lifecycle.RegisterCommandEvent;
import org.spongepowered.api.event.lifecycle.StartingEngineEvent;
import org.spongepowered.api.event.lifecycle.StoppingEngineEvent;
import org.spongepowered.api.event.network.ServerSideConnectionEvent;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;
import org.rei.checkmod.CheckModAPI;
import java.util.List;
import java.util.function.Consumer;

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
//                ctx.cause().audience().forEachAudience(consumer);
                ctx.sendMessage(Identity.nil(), LinearComponents.linear(
                    NamedTextColor.AQUA,
                    Component.text("Your mods are:\n")
//                    Component.text(mods)
                ));

                return CommandResult.success();
            })
            .build(), "checkmod", "cm");
    }
    @Listener
    public void onConnection(ServerSideConnectionEvent.Join event)
    {
        logger.info("OnConnection");
        List<IModData> mods = CheckModAPI.getMods(event.player());
        logger.info("Player joined {}", event.player().identity().uuid().toString());

        int i = 0;
        for (IModData mod: mods) {
            logger.info("{} = {}", ++i, mod.getCompleteData());
        }
//        Task task = Task.builder().execute(() -> {
//            List<IModData> mods = CheckModAPI.getMods(event.player());
//            logger.info("Player joined {}", event.player().identity().uuid().toString());
//
//            int i = 0;
//            for (IModData mod: mods) {
//                logger.info("{} = {}", ++i, mod.getCompleteData());
//            }
//        }).build();
//        Sponge.asyncScheduler().submit(task);
    }
}
