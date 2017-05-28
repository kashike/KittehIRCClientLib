/*
 * * Copyright (C) 2013-2017 Matt Baxter http://kitteh.org
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.kitteh.irc.client.library.feature.twitch.messagetag;

import org.kitteh.irc.client.library.Client;
import org.kitteh.irc.client.library.feature.MessageTagManager;
import org.kitteh.irc.client.library.util.TriFunction;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * Message tag for user types.
 */
public class UserType extends MessageTagManager.DefaultMessageTag {
    /**
     * Name of this message tag.
     */
    public static final String NAME = "user-type";

    /**
     * Function to create this message tag.
     */
    public static final TriFunction<Client, String, Optional<String>, UserType> FUNCTION = (client, name, value) -> new UserType(name, value);

    /**
     * Known msg id values, according to Twitch documentation.
     */
    public static final class KnownValues {
        private KnownValues() {
        }

        /**
         * Admin.
         */
        public static final String ADMIN = "admin";
        /**
         * Empty type. Ok then!
         */
        public static final String EMPTY = "empty";
        /**
         * Global mod
         */
        public static final String GLOBAL_MOD = "global_mod";
        /**
         * Mod.
         */
        public static final String MOD = "mod";
        /**
         * Staff.
         */
        public static final String STAFF = "staff";
    }

    private UserType(@Nonnull String name, @Nonnull Optional<String> value) {
        super(name, value);
    }
}