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

package com.github.siroshun09.mccommand.bukkit.paper;

/**
 * A class to check if the server software is Paper or a fork of it.
 */
public final class PaperChecker {

    private PaperChecker() {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks for the existence of the {@link com.destroystokyo.paper.event.server.AsyncTabCompleteEvent} class.
     *
     * @return {@code true} if the class exists, {@code false} otherwise.
     */
    public static boolean check() {
        try {
            Class.forName("com.destroystokyo.paper.event.server.AsyncTabCompleteEvent");
            return true;
        } catch (ClassNotFoundException var1) {
            return false;
        }
    }
}
