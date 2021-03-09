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

package com.github.siroshun09.mccommand.bukkit.paper;

import com.destroystokyo.paper.event.server.AsyncTabCompleteEvent;
import com.github.siroshun09.mccommand.bukkit.sender.BukkitSender;
import com.github.siroshun09.mccommand.common.Command;
import com.github.siroshun09.mccommand.common.argument.Argument;
import com.github.siroshun09.mccommand.common.context.SimpleCommandContext;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * A class that enables asynchronous tab completion.
 */
public class AsyncTabCompleteListener implements Listener {

    private final BukkitAudiences audiences;
    private final Command command;

    private AsyncTabCompleteListener(@NotNull Plugin plugin, @NotNull Command command) {
        this.audiences = BukkitAudiences.create(plugin);
        this.command = command;
    }

    /**
     * Enables asynchronous tab completion.
     *
     * @param plugin  the plugin to register the listener
     * @param command the {@link Command} for asynchronous tab completion
     * @return the listener of {@link AsyncTabCompleteEvent}
     */
    @NotNull
    public static AsyncTabCompleteListener register(@NotNull Plugin plugin, @NotNull Command command) {
        AsyncTabCompleteListener listener = new AsyncTabCompleteListener(plugin, command);

        plugin.getServer().getPluginManager().registerEvents(listener, plugin);

        return listener;
    }

    /**
     * Unregisters this listener.
     */
    public void unregister() {
        HandlerList.unregisterAll(this);
    }

    /**
     * The method called when the {@link AsyncTabCompleteEvent} is fired.
     *
     * @param event the {@link AsyncTabCompleteEvent}
     */
    @EventHandler
    public void onTabCompletion(@NotNull AsyncTabCompleteEvent event) {
        Objects.requireNonNull(event, "event");

        if (!event.isCommand()) {
            return;
        }

        String buffer = event.getBuffer();
        if (buffer.isEmpty()) {
            return;
        }

        if (buffer.charAt(0) == '/') {
            buffer = buffer.substring(1);
        }

        int firstSpace = buffer.indexOf(' ');
        if (firstSpace < 0) {
            return;
        }

        String alias = buffer.substring(0, firstSpace).toLowerCase();

        if (!command.getName().equals(alias) || !command.getAliases().contains(alias)) {
            return;
        }

        String[] args = buffer.substring(firstSpace + 1).split(" ");
        List<Argument> arguments;

        if (args.length == 0) {
            arguments = List.of(Argument.of(0, ""));
        } else if (!args[0].equals("") && buffer.endsWith(" ")) {
            List<Argument> temp = createArguments(new LinkedList<>(), args);

            temp.add(Argument.of(args.length, ""));

            arguments = List.copyOf(temp);
        } else {
            arguments = List.copyOf(createArguments(new LinkedList<>(), args));
        }

        event.setCompletions(command.onTabCompletion(
                SimpleCommandContext.newBuilder()
                        .setCommand(command)
                        .setSender(new BukkitSender(audiences, event.getSender()))
                        .setArguments(arguments)
                        .setLabel(alias)
                        .build()
        ));

        event.setHandled(true);
    }

    @NotNull
    private List<Argument> createArguments(@NotNull List<Argument> toCollect, @NotNull String[] args) {
        for (int i = 0; i < args.length; i++) {
            toCollect.add(Argument.of(i, args[i]));
        }

        return toCollect;
    }
}
