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

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * An abstract class of {@link Filter}.
 *
 * @param <T> the value type
 */
public class AbstractFilter<T> implements Filter<T> {

    private final Predicate<T> predicate;

    /**
     * The constructor of {@link AbstractFilter}
     *
     * @param predicate the {@link Predicate} to test the value
     */
    protected AbstractFilter(@NotNull Predicate<T> predicate) {
        this.predicate = Objects.requireNonNull(predicate);
    }

    @Override
    public boolean test(T t) {
        return predicate.test(t);
    }
}
