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

package com.github.siroshun09.mccommand.paper.sender;

import com.github.siroshun09.mccommand.common.sender.ConsoleSender;
import com.github.siroshun09.mccommand.common.sender.Sender;
import net.kyori.adventure.audience.Audience;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class PaperSender implements Sender {

    private final CommandSender sender;

    public PaperSender(@NotNull CommandSender sender) {
        this.sender = sender;
    }

    @Override
    public @NotNull UUID getUUID() {
        if (sender instanceof Player) {
            return ((Player) sender).getUniqueId();
        } else {
            return ConsoleSender.CONSOLE_UUID;
        }
    }

    @Override
    public @NotNull String getName() {
        return sender.getName();
    }

    @Override
    public boolean hasPermission(@NotNull String perm) {
        return sender.hasPermission(perm);
    }

    @Override
    public boolean isOnline() {
        if (sender instanceof Player) {
            return ((Player) sender).isOnline();
        } else {
            return true;
        }
    }

    @Override
    public @NotNull Object getOriginalSender() {
        return sender;
    }

    @Override
    public @NotNull Audience getAudience() {
        return sender;
    }
}
