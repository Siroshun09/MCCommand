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

package com.github.siroshun09.mccommand.common.filter;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.Locale;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * A class that implements {@link Filter} for the {@link String}.
 */
public final class StringFilter extends AbstractFilter<String> {

    /**
     * The filter if the {@link String} is empty.
     */
    public static final StringFilter IS_EMPTY = create(str -> str == null || str.isEmpty());

    /**
     * The filter if the {@link String} is not empty.
     */
    public static final StringFilter IS_NOT_EMPTY = create(str -> str != null && !str.isEmpty());

    private StringFilter(@NotNull Predicate<String> predicate) {
        super(predicate);
    }

    /**
     * Creates the {@link StringFilter}.
     *
     * @param predicate the predicate to test the {@link String}
     * @return the {@link StringFilter}
     */
    @Contract(value = "_ -> new", pure = true)
    public static @NotNull StringFilter create(@NotNull Predicate<String> predicate) {
        return new StringFilter(predicate);
    }

    /**
     * Creates a filter to check if the {@link String} starts with the specified {@link String}.
     *
     * @param prefix the prefix
     * @return the {@link StringFilter}
     */
    @Contract(value = "_ -> new", pure = true)
    public static @NotNull StringFilter startsWith(@NotNull String prefix) {
        Objects.requireNonNull(prefix);
        return create(str -> str != null && str.startsWith(prefix));
    }

    /**
     * Creates a filter to check if the {@link String} starts with the specified {@link String}.
     * <p>
     * This filter is not case-sensitive.
     *
     * @param prefix the prefix
     * @return the {@link StringFilter}
     */
    @Contract(value = "_ -> new", pure = true)
    public static @NotNull StringFilter startsWithIgnoreCase(@NotNull String prefix) {
        Objects.requireNonNull(prefix);
        var lowerCase = prefix.toLowerCase(Locale.ROOT);
        return create(str -> str != null && str.toLowerCase(Locale.ROOT).startsWith(lowerCase));
    }

    /**
     * Creates a filter to check if the {@link String} ends with the specified {@link String}.
     *
     * @param suffix the suffix
     * @return the {@link StringFilter}
     */
    @Contract(value = "_ -> new", pure = true)
    public static @NotNull StringFilter endsWith(@NotNull String suffix) {
        Objects.requireNonNull(suffix);
        return create(str -> str != null && str.endsWith(suffix));
    }

    /**
     * Creates a filter to check if the {@link String} ends with the specified {@link String}.
     * <p>
     * This filter is not case-sensitive.
     *
     * @param suffix the suffix
     * @return the {@link StringFilter}
     */
    @Contract(value = "_ -> new", pure = true)
    public static @NotNull StringFilter endsWithIgnoreCase(@NotNull String suffix) {
        Objects.requireNonNull(suffix);
        var lowerCase = suffix.toLowerCase(Locale.ROOT);
        return create(str -> str != null && str.toLowerCase(Locale.ROOT).endsWith(lowerCase));
    }

    /**
     * Creates a filter to check the length of {@link String}.
     *
     * @param min minimum length
     * @param max maximum length
     * @return the {@link StringFilter}
     * @throws IllegalArgumentException if the minimum or maximum value is less than zero or if the minimum value is greater than the maximum value
     */
    @Contract(value = "_ -> new", pure = true)
    public static @NotNull StringFilter lengthRange(@Range(from = 0, to = Integer.MAX_VALUE) int min,
                                                    @Range(from = 0, to = Integer.MAX_VALUE) int max) throws IllegalArgumentException {
        if (min < 0 || max < 0) {
            throw new IllegalArgumentException("min and max must be positive integer.");
        }

        if (max < min) {
            throw new IllegalArgumentException("min must be less than or equal to max");
        }

        return create(str -> str != null && min <= str.length() && max <= str.length());
    }

    /**
     * Creates a filter to check the length of {@link String}.
     *
     * @param max maximum length
     * @return the {@link StringFilter}
     * @throws IllegalArgumentException if the maximum value is less than zero
     */
    @Contract(value = "_ -> new", pure = true)
    public static @NotNull StringFilter maxLength(@Range(from = 0, to = Integer.MAX_VALUE) int max) {
        if (max < 0) {
            throw new IllegalArgumentException("max must be positive integer.");
        }

        return create(str -> str != null && str.length() <= max);
    }

    /**
     * Creates a filter to check the length of {@link String}.
     *
     * @param min minimum length
     * @return the {@link StringFilter}
     * @throws IllegalArgumentException if the minimum value is less than zero
     */
    @Contract(value = "_ -> new", pure = true)
    public static @NotNull StringFilter minLength(@Range(from = 0, to = Integer.MAX_VALUE) int min) {
        if (min < 0) {
            throw new IllegalArgumentException("min must be positive integer.");
        }

        return create(str -> str != null && min <= str.length());
    }

    /**
     * Create a filter using a regular expression.
     *
     * @param regex the regex
     * @return the {@link StringFilter}
     */
    @Contract(value = "_ -> new", pure = true)
    public static @NotNull StringFilter regex(@NotNull String regex) {
        return regex(Pattern.compile(regex));
    }

    /**
     * Create a filter using a {@link Pattern}.
     *
     * @param pattern the pattern
     * @return the {@link StringFilter}
     */
    @Contract(value = "_ -> new", pure = true)
    public static @NotNull StringFilter regex(@NotNull Pattern pattern) {
        var predicate = pattern.asMatchPredicate();
        return create(str -> str != null && predicate.test(str));
    }
}
