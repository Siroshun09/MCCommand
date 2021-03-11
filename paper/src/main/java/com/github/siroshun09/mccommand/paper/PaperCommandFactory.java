/*
 *     Copyright 2021 Siroshun09
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package com.github.siroshun09.mccommand.paper;

import com.github.siroshun09.mccommand.common.Command;
import com.github.siroshun09.mccommand.common.context.CommandContext;
import com.github.siroshun09.mccommand.common.context.SimpleCommandContext;
import com.github.siroshun09.mccommand.paper.sender.PaperSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class PaperCommandFactory {

    private PaperCommandFactory() {
        throw new UnsupportedOperationException();
    }

    /**
     * Registers the command.
     *
     * @param target  the {@link PluginCommand}
     * @param command the command to register
     */
    public static void register(@NotNull PluginCommand target, @NotNull Command command) {
        PaperCommandImpl paperCommand = new PaperCommandImpl(command);

        target.setExecutor(paperCommand);
        target.setTabCompleter(paperCommand);
    }

    /**
     * Registers the command.
     * <p>
     * If you register a command with this method, it will be executed asynchronously.
     * <p>
     * Note:
     * <p>
     * The tab completion will execute on main thread.
     * <p>
     * If you want the tab completion to be executed asynchronously, use
     * {@link com.github.siroshun09.mccommand.paper.listener.AsyncTabCompleteListener#register(Plugin, Command)}.
     *
     * @param target  the {@link PluginCommand}
     * @param command the command to register
     */
    public static void registerAsync(@NotNull PluginCommand target, @NotNull Command command) {
        registerAsync(target, command, Executors.newSingleThreadExecutor());
    }

    /**
     * Registers the command.
     * <p>
     * If you register a command with this method, it will be executed asynchronously.
     * <p>
     * Note:
     * <p>
     * The tab completion will execute on main thread.
     * <p>
     * If you want the tab completion to be executed asynchronously, use {@link com.github.siroshun09.mccommand.paper.listener.AsyncTabCompleteListener#register(Plugin, Command)}.
     *
     * @param target   the {@link PluginCommand}
     * @param command  the command to register
     * @param executor the executor to run command
     */
    public static void registerAsync(@NotNull PluginCommand target, @NotNull Command command, @NotNull Executor executor) {
        AsyncPaperCommandImpl paperCommand = new AsyncPaperCommandImpl(command, executor);

        target.setExecutor(paperCommand);
        target.setTabCompleter(paperCommand);
    }

    private static class PaperCommandImpl implements CommandExecutor, TabCompleter {

        private final Command command;

        private PaperCommandImpl(@NotNull Command command) {
            this.command = command;
        }

        @Override
        public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command cmd,
                                 @NotNull String label, @NotNull String[] args) {
            command.onExecution(
                    createContext(sender, label, args)
            );

            return true;
        }

        @Override
        public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command cmd,
                                                    @NotNull String label, @NotNull String[] args) {
            return command.onTabCompletion(
                    createContext(sender, label, args)
            );
        }

        private CommandContext createContext(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
            return SimpleCommandContext.newBuilder()
                    .setCommand(command)
                    .setSender(new PaperSender(sender))
                    .setArguments(args)
                    .setLabel(label)
                    .build();
        }
    }

    private static class AsyncPaperCommandImpl implements CommandExecutor, TabCompleter {

        private final Command command;
        private final Executor executor;

        private AsyncPaperCommandImpl(@NotNull Command command, @NotNull Executor executor) {
            this.command = command;
            this.executor = executor;
        }

        @Override
        public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command cmd,
                                 @NotNull String label, @NotNull String[] args) {
            executor.execute(() ->
                    command.onExecution(
                            createContext(sender, label, args)
                    ));

            return true;
        }

        @Override
        public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command cmd,
                                                    @NotNull String label, @NotNull String[] args) {
            return command.onTabCompletion(
                    createContext(sender, label, args)
            );
        }

        private CommandContext createContext(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
            return SimpleCommandContext.newBuilder()
                    .setCommand(command)
                    .setSender(new PaperSender(sender))
                    .setArguments(args)
                    .setLabel(label)
                    .build();
        }
    }
}
