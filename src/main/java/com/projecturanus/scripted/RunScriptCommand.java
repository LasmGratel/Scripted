package com.projecturanus.scripted;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;

public class RunScriptCommand implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Context polyglot = Context.newBuilder().
                allowAllAccess(true).build();
        args.getOne("script").map(script -> ((Text) script).toPlain()).ifPresent(script -> {
            Value context = polyglot.getBindings("js");
            context.putMember("src", src);
            Source source = Source.newBuilder("js", script, args.getOne("name").orElse("").toString()).buildLiteral();
            polyglot.asValue(src);
            Value ret = polyglot.eval(source);
            src.sendMessage(Text.of(ret.toString()));
        });
        return CommandResult.success();
    }
}
