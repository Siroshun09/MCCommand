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

package com.github.siroshun09.mccommand.common.argument.parser;

import com.github.siroshun09.mccommand.common.argument.Argument;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * A class that collect basic parsers.
 */
public final class BasicParser {

    private BasicParser() {
        throw new UnsupportedOperationException();
    }

    /**
     * An instance of {@link ArgumentParser} that parses an {@link Argument} to {@link Boolean}
     */
    public static final ArgumentParser<Boolean> BOOLEAN = argument -> {
        if (argument.get().equalsIgnoreCase("true")) {
            return true;
        }

        if (argument.get().equalsIgnoreCase("false")) {
            return false;
        }

        return null;
    };

    /**
     * An instance of {@link ArgumentParser} that parses an {@link Argument} to {@link Short}
     */
    public static final ArgumentParser<Short> SHORT = new ArgumentParser<>() {
        @Override
        public @Nullable Short parse(@NotNull Argument argument) {
            try {
                return Short.parseShort(argument.get());
            } catch (NumberFormatException e) {
                return null;
            }
        }

        @Override
        public @NotNull Short parseOrThrow(@NotNull Argument argument) throws IllegalArgumentException {
            try {
                return Short.parseShort(argument.get());
            } catch (NumberFormatException e) {
                throw generateException(argument, e);
            }
        }
    };

    /**
     * An instance of {@link ArgumentParser} that parses an {@link Argument} to {@link Integer}
     */
    public static final ArgumentParser<Integer> INTEGER = new ArgumentParser<>() {
        @Override
        public @Nullable Integer parse(@NotNull Argument argument) {
            try {
                return Integer.parseInt(argument.get());
            } catch (NumberFormatException e) {
                return null;
            }
        }

        @Override
        public @NotNull Integer parseOrThrow(@NotNull Argument argument) throws IllegalArgumentException {
            try {
                return Integer.parseInt(argument.get());
            } catch (NumberFormatException e) {
                throw generateException(argument, e);
            }
        }
    };

    /**
     * An instance of {@link ArgumentParser} that parses an {@link Argument} to {@link Long}
     */
    public static final ArgumentParser<Long> LONG = new ArgumentParser<>() {
        @Override
        public @Nullable Long parse(@NotNull Argument argument) {
            try {
                return Long.parseLong(argument.get());
            } catch (NumberFormatException e) {
                return null;
            }
        }

        @Override
        public @NotNull Long parseOrThrow(@NotNull Argument argument) throws IllegalArgumentException {
            try {
                return Long.parseLong(argument.get());
            } catch (NumberFormatException e) {
                throw generateException(argument, e);
            }
        }
    };

    /**
     * An instance of {@link ArgumentParser} that parses an {@link Argument} to {@link Float}
     */
    public static final ArgumentParser<Float> FLOAT = new ArgumentParser<>() {
        @Override
        public @Nullable Float parse(@NotNull Argument argument) {
            try {
                return Float.parseFloat(argument.get());
            } catch (NumberFormatException e) {
                return null;
            }
        }

        @Override
        public @NotNull Float parseOrThrow(@NotNull Argument argument) throws IllegalArgumentException {
            try {
                return Float.parseFloat(argument.get());
            } catch (NumberFormatException e) {
                throw generateException(argument, e);
            }
        }
    };

    /**
     * An instance of {@link ArgumentParser} that parses an {@link Argument} to {@link Double}
     */
    public static final ArgumentParser<Double> DOUBLE = new ArgumentParser<>() {
        @Override
        public @Nullable Double parse(@NotNull Argument argument) {
            try {
                return Double.parseDouble(argument.get());
            } catch (NumberFormatException e) {
                return null;
            }
        }

        @Override
        @NotNull
        public Double parseOrThrow(@NotNull Argument argument) throws IllegalArgumentException {
            try {
                return Double.parseDouble(argument.get());
            } catch (NumberFormatException e) {
                throw generateException(argument, e);
            }
        }
    };

    /**
     * An instance of {@link ArgumentParser} that parses an {@link Argument} to {@link UUID}
     */
    public static final ArgumentParser<UUID> UUID = new UUIDParser();
}
