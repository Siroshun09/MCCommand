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

import java.util.Optional;
import java.util.function.Function;

/**
 * Parses an argument from a String
 *
 * @param <T> the value type
 */
@FunctionalInterface
public interface ArgumentParser<T> {

    /**
     * An instance of {@link ArgumentParser} that parses an {@link Argument} to {@link Boolean}
     */
    ArgumentParser<Boolean> BOOLEAN = argument -> {
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
    ArgumentParser<Short> SHORT = new ArgumentParser<>() {
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
    ArgumentParser<Integer> INTEGER = new ArgumentParser<>() {
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
    ArgumentParser<Long> LONG = new ArgumentParser<>() {
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
    ArgumentParser<Float> FLOAT = new ArgumentParser<>() {
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
    ArgumentParser<Double> DOUBLE = new ArgumentParser<>() {
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
     * Creates an instance of the parser from {@link Function}.
     *
     * @param function the {@link Function} to parse {@link Argument} to a specific type.
     * @param <R> the value type
     * @return the {@link ArgumentParser} instance
     */
    static <R> ArgumentParser<R> of(@NotNull Function<Argument, R> function) {
        return function::apply;
    }

    /**
     * Parses an {@link Argument} to a specified type and returns it.
     *
     * @param argument the argument to be parsed
     * @return the value to be parsed, or {@code null} if the parsing fails
     */
    @Nullable
    T parse(@NotNull Argument argument);

    /**
     * Parses an {@link Argument} to a specified type and returns it.
     *
     * @param argument the argument to be parsed
     * @return the {@link Optional} value
     */
    default Optional<T> parseOptional(@NotNull Argument argument) {
        return Optional.ofNullable(parse(argument));
    }

    /**
     * Parses an {@link Argument} to a specified type and returns it.
     *
     * @param argument the argument to be parsed
     * @param def      the default value to be returned if the parsing fails
     * @return the value to be parsed, or default value if the parsing fails
     */
    @NotNull
    default T parseOrDefault(@NotNull Argument argument, @NotNull T def) {
        T value = parse(argument);

        return value != null ? value : def;
    }

    /**
     * Parses an {@link Argument} to a specified type and returns it.
     * <p>
     * Depending on the implementation, it is possible to use {@link IllegalArgumentException#getCause()}
     * to get the exception that caused the conversion to fail.
     *
     * @param argument the argument to be parsed
     * @return the value to be parsed, or {@code null} if the parsing fails
     * @throws IllegalArgumentException if the parsing fails
     */
    @NotNull
    default T parseOrThrow(@NotNull Argument argument) throws IllegalArgumentException {
        T value = parse(argument);

        if (value != null) {
            return value;
        } else {
            throw generateException(argument);
        }
    }

    /**
     * Generates an exception that throws when the parsing fails.
     *
     * @param argument the argument that failed to be parsed
     * @return the generated exception
     */
    default IllegalArgumentException generateException(@NotNull Argument argument) {
        return generateException(argument, null);
    }

    /**
     * Generates an exception that throws when the parsing fails.
     *
     * @param argument  the argument that failed to be parsed
     * @param exception the exception that caused the parsing to fail
     * @return the generated exception
     */
    default IllegalArgumentException generateException(@NotNull Argument argument, @Nullable Throwable exception) {
        return new IllegalArgumentException(
                "Could not parse argument '" + argument.get() + "' (index: " + argument.getIndex() + ")", exception
        );
    }
}
