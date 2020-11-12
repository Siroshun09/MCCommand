/*
 *     Copyright 2020 Siroshun09
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

package com.github.siroshun09.mccommand.bungee;


import com.github.siroshun09.mccommand.bungee.sender.BungeeSender;
import com.github.siroshun09.mccommand.common.Command;
import com.github.siroshun09.mccommand.common.context.CommandContext;
import com.github.siroshun09.mccommand.common.context.SimpleCommandContext;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.TabExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * The class for registering commands to BungeeCord.
 */
public final class BungeeCommandFactory {

    private BungeeCommandFactory() {
        throw new UnsupportedOperationException();
    }

    /**
     * Registers the command.
     *
     * @param plugin  the BungeeCord plugin
     * @param command the command to register
     */
    public static void register(@NotNull Plugin plugin, @NotNull Command command) {
        ProxyServer.getInstance().getPluginManager().registerCommand(plugin, new BungeeCommandImpl(command));
    }

    /**
     * Registers the command.
     * <p>
     * If you register a command with this method, it will be executed asynchronously.
     * <p>
     * Note:
     * <p>
     * The tab completion will execute on main thread.
     *
     * @param plugin  the BungeeCord plugin
     * @param command the command to register
     */
    public static void registerAsync(@NotNull Plugin plugin, @NotNull Command command) {
        registerAsync(plugin, command, Executors.newSingleThreadExecutor());
    }

    /**
     * Registers the command.
     * <p>
     * If you register a command with this method, it will be executed asynchronously.
     * <p>
     * Note:
     * <p>
     * The tab completion will execute on main thread.
     *
     * @param plugin   the BungeeCord plugin
     * @param command  the command to register
     * @param executor the executor to run command
     */
    public static void registerAsync(@NotNull Plugin plugin, @NotNull Command command, @NotNull Executor executor) {
        ProxyServer.getInstance().getPluginManager().registerCommand(plugin, new AsyncBungeeCommandImpl(command, executor));
    }

    private static class BungeeCommandImpl extends net.md_5.bungee.api.plugin.Command implements TabExecutor {

        private final Command command;

        private BungeeCommandImpl(@NotNull Command command) {
            super(command.getName(), null, command.getAliases().toArray(new String[0]));
            this.command = command;
        }

        @Override
        public void execute(CommandSender sender, String[] args) {
            command.onExecution(
                    createContext(sender, args)
            );
        }

        @Override
        public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
            return command.onTabCompletion(
                    createContext(sender, args)
            );
        }

        private CommandContext createContext(CommandSender sender, String[] args) {
            return SimpleCommandContext.newBuilder()
                    .setCommand(command)
                    .setSender(new BungeeSender(sender))
                    .setArguments(args)
                    .setLabel(command.getName())
                    .build();
        }
    }

    private static class AsyncBungeeCommandImpl extends net.md_5.bungee.api.plugin.Command implements TabExecutor {

        private final Command command;
        private final Executor executor;

        private AsyncBungeeCommandImpl(@NotNull Command command, @NotNull Executor executor) {
            super(command.getName(), null, command.getAliases().toArray(new String[0]));
            this.command = command;
            this.executor = executor;
        }

        @Override
        public void execute(CommandSender sender, String[] args) {
            executor.execute(() ->
                    command.onExecution(
                            createContext(sender, args)
                    )
            );
        }

        @Override
        public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
            return command.onTabCompletion(
                    createContext(sender, args)
            );
        }

        private CommandContext createContext(CommandSender sender, String[] args) {
            return SimpleCommandContext.newBuilder()
                    .setCommand(command)
                    .setSender(new BungeeSender(sender))
                    .setArguments(args)
                    .setLabel(command.getName())
                    .build();
        }
    }
}
