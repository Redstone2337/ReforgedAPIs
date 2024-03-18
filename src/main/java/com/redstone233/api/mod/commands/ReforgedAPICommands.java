package com.redstone233.api.mod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.redstone233.api.mod.ApiCommandsText;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.ServerTickManager;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class ReforgedAPICommands {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("api").requires((src) -> {
            return src.hasPermissionLevel(2);
        }))).then(((LiteralArgumentBuilder)CommandManager.literal("help").executes((a) -> {
            return executeStopStep((ServerCommandSource)a.getSource());
        })).then(CommandManager.argument("page", IntegerArgumentType.integer()).executes((b) -> {
            return executeStopPage((ServerCommandSource)b.getSource(),IntegerArgumentType.getInteger(b, "'page"));
        }))).then(((LiteralArgumentBuilder)CommandManager.literal("info").executes((c) -> {
            return executeStopStep((ServerCommandSource)c.getSource());
        })).then(CommandManager.argument("page", IntegerArgumentType.integer())).executes((d)->{
            return executeStopInfo((ServerCommandSource)d.getSource(), IntegerArgumentType.getInteger(d, "page"));
        })));

        dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("more").requires((sec) -> {
            return sec.hasPermissionLevel(2);
        }))).then(((LiteralArgumentBuilder)CommandManager.literal("book").executes((a)-> {
            return executeStopStep((ServerCommandSource)a.getSource());
        })).then(((LiteralArgumentBuilder)CommandManager.literal("all").executes((b) -> {
            return executeStopBookAll((ServerCommandSource)b.getSource(), b.getSource().getPlayer());
        }))).then(((LiteralArgumentBuilder)CommandManager.literal("any").executes((c) -> {
            return executeStopBookAny((ServerCommandSource)c.getSource(), c.getSource().getPlayer());
        })))).then(((LiteralArgumentBuilder)CommandManager.literal("info").executes((d)-> {
            return executeStopStep((ServerCommandSource)d.getSource());
        })).then(((LiteralArgumentBuilder)CommandManager.literal("all").executes((e) -> {
            return executeStopInfoAll((ServerCommandSource)e.getSource(), e.getSource().getPlayer());
        }))).then(((LiteralArgumentBuilder)CommandManager.literal("any").executes((f) -> {
            return executeStopInfoAny((ServerCommandSource)f.getSource(),f.getSource().getPlayer());
        })))));
    }

    private static int executeStopStep(ServerCommandSource source) {
      ServerTickManager serverTickManager = source.getServer().getTickManager();
      boolean bl = serverTickManager.stopStepping();
      if (bl) {
         source.sendFeedback(() -> {
            return Text.translatable("commands.api.help.success");
         }, true);
         return 1;
      } else {
         source.sendError(Text.translatable("commands.api.help.fail"));
         return 0;
      }
   }

   private static int executeStopPage(ServerCommandSource source, int page) {
    PlayerEntity playerEntity = source.getPlayer();
    ServerTickManager serverTickManager = source.getServer().getTickManager();
    boolean bl = serverTickManager.stopStepping();
    if (bl) {
       source.sendFeedback(() -> {
          return Text.translatable("commands.api.help.page.success");
       }, true);
       if (page == 1) {
        playerEntity.sendMessage((Text) new LiteralMessage(ApiCommandsText.CommandsText.Bookarray[0]),false);
       } else if (page == 2) {
        playerEntity.sendMessage((Text) new LiteralMessage(ApiCommandsText.CommandsText.Bookarray[1]),false);
       } else if (page == 3) {
        playerEntity.sendMessage((Text) new LiteralMessage(ApiCommandsText.CommandsText.Bookarray[2]),false);
       } else if (page == 4) {
        playerEntity.sendMessage((Text) new LiteralMessage(ApiCommandsText.CommandsText.Bookarray[3]),false);
       }
       return 1;
    } else {
       source.sendError(Text.translatable("commands.api.help.page.fail"));
       return 0;
    }
 }

 private static int executeStopInfo(ServerCommandSource source, int page) {
    ServerTickManager serverTickManager = source.getServer().getTickManager();
    boolean bl = serverTickManager.stopStepping();
    if (bl) {
       source.sendFeedback(() -> {
          return Text.translatable("commands.api.info.page.success");
       }, true);
       return 1;
    } else {
       source.sendError(Text.translatable("commands.api.info.page.fail"));
       return 0;
    }
 }

 private static int executeStopBookAll(ServerCommandSource source,PlayerEntity player) {
    ServerTickManager serverTickManager = source.getServer().getTickManager();
    boolean bl = serverTickManager.stopStepping();
    if (bl) {
       source.sendFeedback(() -> {
          return Text.translatable("commands.more.book.all.success");
       }, true);
       for (String books : ApiCommandsText.CommandsText.Bookarray) {
        player.sendMessage((Text) new LiteralMessage(books), false);
       }
       return 1;
    } else {
       source.sendError(Text.translatable("commands.more.book.all.fail"));
       return 0;
    }
 }

 private static int executeStopInfoAll(ServerCommandSource source,PlayerEntity player) {
    ServerTickManager serverTickManager = source.getServer().getTickManager();
    boolean bl = serverTickManager.stopStepping();
    if (bl) {
       source.sendFeedback(() -> {
          return Text.translatable("commands.more.info.all.success");
       }, true);
       for (String infos : ApiCommandsText.CommandsText.Infoarray) {
        player.sendMessage((Text) new LiteralMessage(infos), false);
       }
       return 1;
    } else {
       source.sendError(Text.translatable("commands.more.info.all.fail"));
       return 0;
    }
 }

 private static int executeStopBookAny(ServerCommandSource source,PlayerEntity player) {
    ServerTickManager serverTickManager = source.getServer().getTickManager();
    boolean bl = serverTickManager.stopStepping();
    if (bl) {
       source.sendFeedback(() -> {
          return Text.translatable("commands.more.book.any.success");
       }, true);
        player.sendMessage((Text) new LiteralMessage(ApiCommandsText.CommandsText.Bookarray[2]), false);
       return 1;
    } else {
       source.sendError(Text.translatable("commands.more.book.any.fail"));
       return 0;
    }
 }

 private static int executeStopInfoAny(ServerCommandSource source,PlayerEntity player) {
    ServerTickManager serverTickManager = source.getServer().getTickManager();
    boolean bl = serverTickManager.stopStepping();
    if (bl) {
       source.sendFeedback(() -> {
          return Text.translatable("commands.more.info.any.success");
       }, true);
        player.sendMessage((Text) new LiteralMessage(ApiCommandsText.CommandsText.Infoarray[0]), false);
       return 1;
    } else {
       source.sendError(Text.translatable("commands.more.info.any.fail"));
       return 0;
    }
 }

}
