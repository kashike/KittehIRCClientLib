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
package org.kitteh.irc.client.library.defaults.element;

import org.kitteh.irc.client.library.Client;
import org.kitteh.irc.client.library.element.Channel;
import org.kitteh.irc.client.library.element.User;
import org.kitteh.irc.client.library.util.ToStringer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

public class DefaultUser extends DefaultStaleable implements User {
    private final String account;
    private final String awayMessage;
    private final Set<Channel> channels;
    private final String host;
    private final boolean isAway;
    private final String nick;
    private final String operString;
    private final String realName;
    private final String server;
    private final String user;

    public DefaultUser(@Nonnull Client.WithManagement client, @Nonnull String name, @Nullable String account, @Nullable String awayMessage,
                       @Nonnull String nick, @Nonnull String user, @Nonnull String host, boolean isAway,
                       @Nullable String operString, @Nullable String realName, @Nullable String server, @Nonnull Set<Channel> channels) {
        super(client, name);
        this.account = account;
        this.awayMessage = awayMessage;
        this.nick = nick;
        this.user = user;
        this.host = host;
        this.isAway = isAway;
        this.operString = operString;
        this.realName = realName;
        this.server = server;
        this.channels = Collections.unmodifiableSet(channels);
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof DefaultUser) && (((DefaultUser) o).getClient() == this.getClient()) && ((DefaultUser) o).getLowerCaseName().equals(this.getLowerCaseName());
    }

    @Nonnull
    @Override
    public Optional<String> getAccount() {
        return Optional.ofNullable(this.account);
    }

    @Nonnull
    @Override
    public Optional<String> getAwayMessage() {
        return Optional.ofNullable(this.awayMessage);
    }

    @Nonnull
    @Override
    public Set<Channel> getChannels() {
        return this.channels;
    }

    @Nonnull
    @Override
    public String getHost() {
        return this.host;
    }

    @Nonnull
    @Override
    public String getMessagingName() {
        return this.getNick();
    }

    @Nonnull
    @Override
    public String getNick() {
        return this.nick;
    }

    @Nonnull
    @Override
    public Optional<String> getOperatorInformation() {
        return Optional.ofNullable(this.operString);
    }

    @Nonnull
    @Override
    public Optional<String> getRealName() {
        return Optional.ofNullable(this.realName);
    }

    @Nonnull
    @Override
    public Optional<String> getServer() {
        return Optional.ofNullable(this.server);
    }

    @Nonnull
    @Override
    public String getUserString() {
        return this.user;
    }

    @Override
    public int hashCode() {
        return (this.getLowerCaseName().hashCode() * 2) + this.getClient().hashCode();
    }

    @Override
    public boolean isAway() {
        return this.isAway;
    }

    @Override
    @Nonnull
    public String toString() {
        return new ToStringer(this).add("client", this.getClient()).add("nick", this.nick).add("user", this.user).add("host", this.host).add("channels", this.channels.size()).toString();
    }
}
