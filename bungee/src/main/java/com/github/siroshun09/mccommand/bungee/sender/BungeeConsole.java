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

package com.github.siroshun09.mccommand.bungee.sender;

import com.github.siroshun09.mccommand.common.sender.ConsoleSender;
import net.md_5.bungee.api.ProxyServer;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Implementation class of {@link ConsoleSender} on BungeeCord.
 */
public class BungeeConsole extends BungeeSender implements ConsoleSender {

    /**
     * Creates a {@link BungeeConsole}.
     */
    public BungeeConsole() {
        super(ProxyServer.getInstance().getConsole());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public UUID getUUID() {
        return CONSOLE_UUID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public String getName() {
        return CONSOLE_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOnline() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isConsole() {
        return true;
    }
}
