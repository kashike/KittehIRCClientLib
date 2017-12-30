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
import org.kitteh.irc.client.library.element.WhoisData;
import org.kitteh.irc.client.library.util.Sanity;
import org.kitteh.irc.client.library.util.ToStringer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Default implementation of {@link WhoisData}.
 */
public class DefaultWhoisData implements WhoisData {
    /**
     * A builder to assist in building {@link DefaultWhoisData}.
     */
    public static class Builder {
        private final Client client;
        private String account;
        private final Set<Channel> channels = new HashSet<>();
        private final String nick;
        private String userString;
        private String host;
        private String realName;
        private String server;
        private String serverDescription;
        private boolean secure;
        private String operatorInformation;
        private Long idleTime;
        private Long signOnTime;
        private String awayMessage;

        /**
         * Constructs the builder.
         *
         * @param client client
         * @param nick nick of the user
         */
        public Builder(@Nonnull Client client, @Nonnull String nick) {
            this.client = client;
            this.nick = nick;
        }

        /**
         * Gets the user's nick.
         *
         * @return nick
         */
        @Nonnull
        public String getNick() {
            return this.nick;
        }

        /**
         * Sets the account.
         *
         * @param account account
         */
        public void setAccount(@Nonnull String account) {
            this.account = account;
        }

        /**
         * Adds channels, not erasing any previously added channels.
         *
         * @param channels channels to add
         */
        public void addChannels(@Nonnull Set<Channel> channels) {
            this.channels.addAll(Sanity.nullCheck(channels, "Channels cannot be null"));
        }

        /**
         * Sets the away message.
         *
         * @param awayMessage away message
         */
        public void setAway(@Nonnull String awayMessage) {
            this.awayMessage = awayMessage;
        }

        /**
         * Sets the user string.
         *
         * @param userString user string
         */
        public void setUserString(@Nonnull String userString) {
            this.userString = userString;
        }

        /**
         * Sets the host.
         *
         * @param host host
         */
        public void setHost(@Nonnull String host) {
            this.host = host;
        }

        /**
         * Sets the real name.
         *
         * @param realName real name
         */
        public void setRealName(@Nonnull String realName) {
            this.realName = realName;
        }

        /**
         * Sets the server.
         *
         * @param server server
         */
        public void setServer(@Nonnull String server) {
            this.server = server;
        }

        /**
         * Sets the server description.
         *
         * @param serverDescription server description
         */
        public void setServerDescription(@Nonnull String serverDescription) {
            this.serverDescription = serverDescription;
        }

        /**
         * Sets that the connection is known to be secure
         */
        public void setSecure() {
            this.secure = true;
        }

        /**
         * Sets the operator information.
         *
         * @param operatorInformation operator information
         */
        public void setOperatorInformation(@Nonnull String operatorInformation) {
            this.operatorInformation = operatorInformation;
        }

        /**
         * Sets the idle time.
         *
         * @param idleTime idle time
         */
        public void setIdleTime(long idleTime) {
            this.idleTime = idleTime;
        }

        /**
         * Sets the sign on time.
         *
         * @param signOnTime sign on time
         */
        public void setSignOnTime(long signOnTime) {
            this.signOnTime = signOnTime;
        }

        /**
         * Builds a new {@link WhoisData} from the provided information.
         *
         * @return new WhoisData
         */
        @Nonnull
        public WhoisData build() {
            return new DefaultWhoisData(this.client, this.account, this.channels, this.nick, this.userString, this.host, this.realName, this.server, this.serverDescription, this.secure, this.operatorInformation, this.idleTime, this.signOnTime, this.awayMessage);
        }
    }

    private final Client client;
    private final String account;
    private final Set<Channel> channels;
    private final String name;
    private final String nick;
    private final String userString;
    private final String host;
    private final long creationTime;
    private final String realName;
    private final String server;
    private final Long idleTime;
    private final String serverDescription;
    private final boolean secureConnection;
    private final String operatorInformation;
    private final Long signOnTime;
    private final boolean away;
    private final String awayMessage;

    /**
     * Creates the default WHOIS data object.
     *
     * @param client client
     * @param account account, if known
     * @param channels channels the user known to be in
     * @param nick nickname
     * @param userString user string
     * @param host host
     * @param realName real name, if known
     * @param server server, if known
     * @param serverDescription server description, if known
     * @param secureConnection if the connection is known to be secure
     * @param operatorInformation any operator information, if known
     * @param idleTime how long the user has been idle, if known
     * @param signOnTime when the user signed on, if known
     * @param awayMessage user away message, if known
     */
    public DefaultWhoisData(@Nonnull Client client, @Nullable String account, @Nonnull Set<Channel> channels, @Nonnull String nick, @Nonnull String userString,
                            @Nonnull String host, @Nullable String realName, @Nullable String server, @Nullable String serverDescription, boolean secureConnection,
                            @Nullable String operatorInformation, @Nullable Long idleTime, @Nullable Long signOnTime, @Nullable String awayMessage) {
        this.client = client;
        this.account = account;
        this.channels = Collections.unmodifiableSet(new HashSet<>(channels));
        this.name = nick + '!' + userString + '@' + host;
        this.nick = nick;
        this.userString = userString;
        this.host = host;
        this.realName = realName;
        this.server = server;
        this.serverDescription = serverDescription;
        this.operatorInformation = operatorInformation;
        this.secureConnection = secureConnection;
        this.idleTime = idleTime;
        this.signOnTime = signOnTime;
        this.away = awayMessage != null;
        this.awayMessage = awayMessage;
        this.creationTime = System.currentTimeMillis();
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
    public String getNick() {
        return this.nick;
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
        return this.userString;
    }

    @Override
    public boolean isAway() {
        return this.away;
    }

    @Nonnull
    @Override
    public String getMessagingName() {
        return this.nick;
    }

    @Nonnull
    @Override
    public String getName() {
        return this.name;
    }

    @Nonnull
    @Override
    public Client getClient() {
        return this.client;
    }

    @Override
    public long getCreationTime() {
        return this.creationTime;
    }

    @Nonnull
    @Override
    public Optional<Long> getIdleTime() {
        return Optional.ofNullable(this.idleTime);
    }

    @Nonnull
    @Override
    public Optional<String> getOperatorInformation() {
        return Optional.ofNullable(this.operatorInformation);
    }

    @Nonnull
    @Override
    public Optional<String> getServerDescription() {
        return Optional.ofNullable(this.serverDescription);
    }

    @Nonnull
    @Override
    public Optional<Long> getSignOnTime() {
        return Optional.ofNullable(this.signOnTime);
    }

    @Override
    public boolean isSecureConnection() {
        return this.secureConnection;
    }

    @Nonnull
    @Override
    public String toString() {
        return new ToStringer(this)
                .add("client", this.client)
                .add("account", this.account)
                .add("channels", this.channels)
                .add("name", this.name)
                .add("creationTime", this.creationTime)
                .add("realName", this.realName)
                .add("server", this.server)
                .add("serverDescription", this.serverDescription)
                .add("secureConnection", this.secureConnection)
                .add("operatorInformation", this.operatorInformation)
                .add("idleTime", this.idleTime)
                .add("signOnTime", this.signOnTime)
                .add("awayMessage", this.awayMessage)
                .toString();
    }
}
