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

package com.github.siroshun09.mccommand.paper.parser;

import com.github.siroshun09.mccommand.common.argument.Argument;
import com.github.siroshun09.mccommand.common.argument.parser.ArgumentParser;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A class that parses an {@link Argument} to an {@link Material}.
 */
public class MaterialParser implements ArgumentParser<Material> {

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable Material parse(@NotNull Argument argument) {
        try {
            return Material.valueOf(argument.get().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Material parseOrThrow(@NotNull Argument argument) throws IllegalArgumentException {
        try {
            return Material.valueOf(argument.get().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw generateException(argument, e);
        }
    }
}
