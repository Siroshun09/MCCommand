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

package com.github.siroshun09.mccommand.bungee.sender;

import com.github.siroshun09.mccommand.common.sender.ConsoleSender;
import com.github.siroshun09.mccommand.common.sender.Sender;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

/**
 * A class that wraps {@link CommandSender}.
 */
public class BungeeSender implements Sender {

    private final Audience audience;
    private final CommandSender sender;

    /**
     * Create {@link Sender} to use in the library with a {@link CommandSender}.
     *
     * @param audiences the {@link BungeeAudiences} to get {@link Audience}
     * @param sender    {@link CommandSender} to wrap
     */
    public BungeeSender(@NotNull BungeeAudiences audiences, @NotNull CommandSender sender) {
        this.audience = audiences.sender(sender);
        this.sender = sender;
    }

    @NotNull
    @Override
    public Audience getAudience() {
        return audience;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public UUID getUUID() {
        if (sender instanceof ProxiedPlayer) {
            return ((ProxiedPlayer) sender).getUniqueId();
        } else {
            return ConsoleSender.CONSOLE_UUID;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public String getName() {
        return sender.getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasPermission(@NotNull String perm) {
        return sender.hasPermission(perm);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOnline() {
        if (sender instanceof ProxiedPlayer) {
            return ((ProxiedPlayer) sender).isConnected();
        } else {
            return true;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isConsole() {
        return sender.equals(ProxyServer.getInstance().getConsole());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Object getOriginalSender() {
        return sender;
    }

    /**
     * Gets {@link CommandSender}.
     *
     * @return the wrapped {@link CommandSender}
     */
    public @NotNull CommandSender getCommandSender() {
        return sender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sender);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o instanceof BungeeSender) {
            BungeeSender that = (BungeeSender) o;
            return sender.equals(that.sender);
        } else {
            return false;
        }
    }
}
