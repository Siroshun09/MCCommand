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

package com.github.siroshun09.mccommand.bukkit;

import com.github.siroshun09.mccommand.bukkit.sender.BukkitSender;
import com.github.siroshun09.mccommand.common.Command;
import com.github.siroshun09.mccommand.common.context.CommandContext;
import com.github.siroshun09.mccommand.common.context.SimpleCommandContext;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * The class for registering commands to Bukkit.
 */
public final class BukkitCommandFactory {

    private BukkitCommandFactory() {
        throw new UnsupportedOperationException();
    }

    /**
     * Registers the command.
     *
     * @param target  the {@link PluginCommand}
     * @param command the command to register
     */
    public static void register(@NotNull PluginCommand target, @NotNull Command command) {
        BukkitCommandImpl bukkitCommand = new BukkitCommandImpl(target.getPlugin(), command);

        target.setExecutor(bukkitCommand);
        target.setTabCompleter(bukkitCommand);
    }

    /**
     * Searches for {@link PluginCommand} that is named as {@link Command#getName()}
     * and register the command if it exists.
     *
     * @param sourcePlugin the plugin to search for the command
     * @param command      the command to register
     */
    public static void registerIfExists(@NotNull JavaPlugin sourcePlugin, @NotNull Command command) {
        PluginCommand pluginCommand = sourcePlugin.getCommand(command.getName());

        if (pluginCommand != null) {
            register(pluginCommand, command);
        }
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
     * @param target  the {@link PluginCommand}
     * @param command the command to register
     */
    public static void registerAsync(@NotNull PluginCommand target, @NotNull Command command) {
        registerAsync(target, command, Executors.newSingleThreadExecutor());
    }

    /**
     * Searches for {@link PluginCommand} that is named as {@link Command#getName()}
     * and register the command if it exists.
     *
     * @param sourcePlugin the plugin to search for the command
     * @param command      the command to register
     * @see BukkitCommandFactory#registerAsync(PluginCommand, Command)
     */
    public static void registerAsyncIfExists(@NotNull JavaPlugin sourcePlugin, @NotNull Command command) {
        PluginCommand pluginCommand = sourcePlugin.getCommand(command.getName());

        if (pluginCommand != null) {
            registerAsync(pluginCommand, command);
        }
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
     * @param target   the {@link PluginCommand}
     * @param command  the command to register
     * @param executor the executor to run command
     */
    public static void registerAsync(@NotNull PluginCommand target, @NotNull Command command, @NotNull Executor executor) {
        AsyncBukkitCommandImpl bukkitCommand = new AsyncBukkitCommandImpl(target.getPlugin(), command, executor);

        target.setExecutor(bukkitCommand);
        target.setTabCompleter(bukkitCommand);
    }

    /**
     * Searches for {@link PluginCommand} that is named as {@link Command#getName()}
     * and register the command if it exists.
     *
     * @param sourcePlugin the plugin to search for the command
     * @param command      the command to register
     * @param executor     the executor to run command
     * @see BukkitCommandFactory#registerAsync(PluginCommand, Command)
     */
    public static void registerAsyncIfExists(@NotNull JavaPlugin sourcePlugin, @NotNull Command command,
                                             @NotNull Executor executor) {
        PluginCommand pluginCommand = sourcePlugin.getCommand(command.getName());

        if (pluginCommand != null) {
            registerAsync(pluginCommand, command, executor);
        }
    }

    private static class BukkitCommandImpl implements CommandExecutor, TabCompleter {

        private final BukkitAudiences audiences;
        private final Command command;

        private BukkitCommandImpl(@NotNull Plugin plugin, @NotNull Command command) {
            this.audiences = BukkitAudiences.create(plugin);
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
                    .setSender(new BukkitSender(audiences, sender))
                    .setArguments(args)
                    .setLabel(label)
                    .build();
        }
    }

    private static class AsyncBukkitCommandImpl implements CommandExecutor, TabCompleter {

        private final BukkitAudiences audiences;
        private final Command command;
        private final Executor executor;

        private AsyncBukkitCommandImpl(@NotNull Plugin plugin, @NotNull Command command, @NotNull Executor executor) {
            this.audiences = BukkitAudiences.create(plugin);
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
                    .setSender(new BukkitSender(audiences, sender))
                    .setArguments(args)
                    .setLabel(label)
                    .build();
        }
    }
}
