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
package org.kitteh.irc.client.library.util.mask;

import org.kitteh.irc.client.library.element.Channel;
import org.kitteh.irc.client.library.element.User;
import org.kitteh.irc.client.library.util.Sanity;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

/**
 * Represents a mask that can match a {@link User}.
 */
public interface Mask extends Predicate<User> {
    /**
     * Represents a mask that can be represented as a {@code String}.
     */
    interface AsString extends Mask {
        /**
         * Gets the string representation of this mask.
         *
         * @return the string representation
         */
        @Nonnull
        String asString();
    }

    /**
     * Tests if the user matches this mask.
     *
     * @param user the user
     * @return {@code true} if user matches this mask
     */
    @Override
    boolean test(@Nonnull final User user);

    /**
     * Tests if the string matches this mask.
     *
     * @param string the string
     * @return {@code true} if string matches this mask
     */
    boolean test(@Nonnull final String string);

    @Override
    default Mask and(final Predicate<? super User> other) {
        return new Mask() {
            @Override
            public boolean test(@Nonnull User user) {
                return Mask.this.test(user) && other.test(user);
            }

            @Override
            public boolean test(@Nonnull String string) {
                return Mask.this.test(string) && ((!(other instanceof Mask)) || ((Mask) other).test(string));
            }
        };
    }

    @Override
    default Mask negate() {
        return new Mask() {
            @Override
            public boolean test(@Nonnull final User user) {
                return !Mask.this.test(user);
            }

            @Override
            public boolean test(@Nonnull final String string) {
                return !Mask.this.test(string);
            }
        };
    }

    @Override
    default Mask or(final Predicate<? super User> other) {
        return new Mask() {
            @Override
            public boolean test(@Nonnull User user) {
                return Mask.this.test(user) || other.test(user);
            }

            @Override
            public boolean test(@Nonnull String string) {
                return Mask.this.test(string) || ((!(other instanceof Mask)) || ((Mask) other).test(string));
            }
        };
    }

    /**
     * Gets a list of users that match this mask in the provided channel.
     *
     * @param channel the channel
     * @return a list of users that match this mask
     */
    @Nonnull
    default List<User> getMatches(@Nonnull final Channel channel) {
        Sanity.nullCheck(channel, "Channel cannot be null");
        return channel.getUsers().stream()
            .filter(this)
            .collect(Collectors.toList());
    }
}
