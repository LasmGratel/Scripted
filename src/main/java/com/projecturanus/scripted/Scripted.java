package com.projecturanus.scripted;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

@Plugin(id = "scripted")
public class Scripted {
    @Inject
    private Logger logger;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        logger.info("Scripted launched");
        CommandSpec myCommandSpec = CommandSpec.builder()
                .description(Text.of("Run script"))
                .arguments(
                        GenericArguments.string(Text.of("name")),
                        GenericArguments.text(Text.of("script"), TextSerializers.PLAIN, true)
                )
                .permission("scripted.run")
                .executor(new RunScriptCommand())
                .build();

        Sponge.getCommandManager().register(this, myCommandSpec, "runscript", "js");
    }
}
