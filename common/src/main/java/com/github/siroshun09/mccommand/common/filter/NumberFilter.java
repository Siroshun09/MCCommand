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

import java.util.function.Predicate;

/**
 * A class that implements {@link Filter} for the {@link Number}.
 *
 * @param <N> the {@link Number} type
 */
public final class NumberFilter<N extends Number> extends AbstractFilter<N> {

    /**
     * The constructor of {@link NumberFilter}
     *
     * @param predicate the {@link Predicate} to test the value
     */
    private NumberFilter(@NotNull Predicate<N> predicate) {
        super(predicate);
    }

    /**
     * Creates the {@link NumberFilter}.
     *
     * @param predicate the predicate to test the {@link Number}
     * @param <N>       the {@link Number} type
     * @return the {@link NumberFilter}
     */
    @Contract("_ -> new")
    public static <N extends Number> @NotNull NumberFilter<N> create(@NotNull Predicate<N> predicate) {
        return new NumberFilter<N>(predicate);
    }
}
