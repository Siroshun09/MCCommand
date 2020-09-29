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

package com.github.siroshun09.mccommand.bukkit;

import com.github.siroshun09.mccommand.bukkit.sender.BukkitSender;
import com.github.siroshun09.mccommand.common.Command;
import com.github.siroshun09.mccommand.common.context.SimpleCommandContext;
import com.github.siroshun09.mccommand.common.context.CommandContext;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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
     * @param target the {@link PluginCommand}
     * @param command the command to register
     */
    public static void register(@NotNull PluginCommand target, @NotNull Command command) {
        BukkitCommandImpl bukkitCommand = new BukkitCommandImpl(command);

        target.setExecutor(bukkitCommand);
        target.setTabCompleter(bukkitCommand);
    }

    private static class BukkitCommandImpl implements CommandExecutor, TabCompleter {

        private final Command command;

        private BukkitCommandImpl(@NotNull Command command) {
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
                    .setSender(new BukkitSender(sender))
                    .setArguments(args)
                    .setLabel(label)
                    .build();
        }
    }
}
