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

package com.github.siroshun09.mccommand.common.sender;

import com.github.siroshun09.adventureextender.MessageReceivable;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Interface that wraps a CommandSender that is different on different platforms.
 */
public interface Sender extends MessageReceivable {

    /**
     * Gets the {@link UUID} of the command sender.
     * <p>
     * If it is internal sender, returns {@link ConsoleSender#CONSOLE_UUID}.
     *
     * @return sender's {@link UUID}.
     */
    @NotNull
    UUID getUUID();

    /**
     * Gets the name of the command sender.
     * <p>
     * If it is internal sender, returns {@link ConsoleSender#CONSOLE_NAME}.
     *
     * @return sender's name.
     */
    @NotNull
    String getName();

    /**
     * Checks if the {@link Sender} has the requested permission.
     *
     * @param perm a permission.
     * @return {@code true} if the sender have permission, {@code false} otherwise
     */
    boolean hasPermission(@NotNull String perm);

    /**
     * Checks if the sender is online.
     *
     * @return {@code true} if the sender is online, {@code false} otherwise.
     */
    boolean isOnline();

    /**
     * Checks if the sender is console.
     *
     * @return {@code true} if the sender is console, {@code false} otherwise.
     */
    default boolean isConsole() {
        return getUUID().equals(ConsoleSender.CONSOLE_UUID);
    }

    /**
     * Gets the command sender that the instance is wrapped in.
     *
     * @return the original command sender
     */
    @NotNull Object getOriginalSender();
}
