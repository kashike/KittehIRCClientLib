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
package org.kitteh.irc.client.library.feature.auth;

import net.engio.mbassy.listener.Handler;
import org.kitteh.irc.client.library.Client;
import org.kitteh.irc.client.library.event.client.ClientReceiveNumericEvent;
import org.kitteh.irc.client.library.event.user.PrivateNoticeEvent;
import org.kitteh.irc.client.library.feature.auth.element.EventListening;
import org.kitteh.irc.client.library.feature.auth.element.NickReclamation;
import org.kitteh.irc.client.library.feature.filter.NumericFilter;
import org.kitteh.irc.client.library.util.Format;
import org.kitteh.irc.client.library.util.Sanity;
import org.kitteh.irc.client.library.util.ToStringer;

import javax.annotation.Nonnull;

/**
 * NickServ protocol. Automatically attempts to identify upon connection.
 */
public class NickServ extends AbstractAccountPassProtocol implements EventListening, NickReclamation {
    private class Listener {
        @NumericFilter(4)
        @Handler
        public void listenVersion(ClientReceiveNumericEvent event) {
            NickServ.this.startAuthentication();
        }

        @Handler
        public void listenSuccess(PrivateNoticeEvent event) {
            if (event.getActor().getNick().equals(NickServ.this.getNickServNick())) {
                if (event.getMessage().startsWith("You are now identified")) {
                    int first;
                    String accountName = event.getMessage().substring((first = event.getMessage().indexOf(Format.BOLD.toString()) + 1), event.getMessage().indexOf(Format.BOLD.toString(), first));
                    // TODO do something with this information
                }
            }
        }

        @Nonnull
        @Override
        public String toString() {
            return new ToStringer(this).toString();
        }
    }

    /**
     * The default NickServ nickname.
     */
    public static final String NICK = "NickServ";
    /**
     * The command used to identify with NickServ.
     */
    public static final String IDENTIFY_COMMAND = "IDENTIFY";
    /**
     * The command used to ghost a nick with NickServ.
     */
    public static final String GHOST_COMMAND = "GHOST";
    /**
     * The command used to regain a nick with NickServ.
     */
    public static final String REGAIN_COMMAND = "REGAIN";
    private final Listener listener = new Listener();

    /**
     * Creates a NickServ authentication protocol instance.
     *
     * @param client client for which this will be used
     * @param accountName account name
     * @param password password
     */
    public NickServ(@Nonnull Client client, @Nonnull String accountName, @Nonnull String password) {
        super(client, accountName, password);
    }

    @Nonnull
    @Override
    protected String getAuthentication() {
        return this.formatCommand(IDENTIFY_COMMAND) + ' ' + this.getAccountName() + ' ' + this.getPassword();
    }

    @Override
    public void ghostNick(@Nonnull String nick) {
        Sanity.safeMessageCheck(nick, "Nick");
        this.getClient().sendRawLine(this.formatCommand(GHOST_COMMAND) + ' ' + nick);
    }

    @Override
    public void regainNick(@Nonnull String nick) {
        Sanity.safeMessageCheck(nick, "Nick");
        this.getClient().sendRawLine(this.formatCommand(IDENTIFY_COMMAND) + ' ' + nick);
    }

    private String formatCommand(@Nonnull String command) {
        return "PRIVMSG " + this.getNickServNick() + " :" + command;
    }

    @Nonnull
    @Override
    public Object getEventListener() {
        return this.listener;
    }

    /**
     * Gets the expected NickServ nickname.
     *
     * @return nick of NickServ
     */
    @Nonnull
    protected String getNickServNick() {
        return NICK;
    }
}
