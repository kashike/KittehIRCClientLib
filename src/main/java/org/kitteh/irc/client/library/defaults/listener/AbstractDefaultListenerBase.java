/*
 * * Copyright (C) 2013-2018 Matt Baxter https://kitteh.org
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
package org.kitteh.irc.client.library.defaults.listener;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.kitteh.irc.client.library.Client;
import org.kitteh.irc.client.library.event.helper.ClientEvent;
import org.kitteh.irc.client.library.event.helper.ClientReceiveServerMessageEvent;
import org.kitteh.irc.client.library.exception.KittehServerMessageException;
import org.kitteh.irc.client.library.feature.ActorTracker;
import org.kitteh.irc.client.library.feature.EventManager;
import org.kitteh.irc.client.library.util.ToStringer;

/**
 * A base for listening to server message events.
 */
public class AbstractDefaultListenerBase {
    private final Client.WithManagement client;

    /**
     * Constructs the listener.
     *
     * @param client client
     */
    public AbstractDefaultListenerBase(Client.@NonNull WithManagement client) {
        this.client = client;
    }

    @Override
    public @NonNull String toString() {
        return new ToStringer(this).toString();
    }

    protected Client.@NonNull WithManagement getClient() {
        return this.client;
    }

    /**
     * Fires an event. Convenience method.
     *
     * @param event event to fire
     * @see EventManager#callEvent(Object)
     */
    protected void fire(@NonNull ClientEvent event) {
        this.client.getEventManager().callEvent(event);
    }

    /**
     * Fires an exception in processing a server message event.
     *
     * @param event event causing trouble
     * @param reason reason for the trouble
     */
    protected void trackException(@NonNull ClientReceiveServerMessageEvent event, @NonNull String reason) {
        this.client.getExceptionListener().queue(new KittehServerMessageException(event.getServerMessage(), reason));
    }

    /**
     * Gets the actor tracker. Convenience method.
     *
     * @return actor tracker
     * @see Client.WithManagement#getActorTracker()
     */
    protected @NonNull ActorTracker getTracker() {
        return this.client.getActorTracker();
    }
}