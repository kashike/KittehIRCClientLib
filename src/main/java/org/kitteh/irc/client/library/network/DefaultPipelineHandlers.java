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
package org.kitteh.irc.client.library.network;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.string.LineEncoder;
import io.netty.handler.codec.string.LineSeparator;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Consumer;

public interface DefaultPipelineHandlers {
    static @NonNull IdleStateHandler idleStateHandler() {
        return new IdleStateHandler(250, 0, 0);
    }

    static @NonNull LineEncoder lineEncoder() {
        return new LineEncoder(LineSeparator.WINDOWS);
    }

    static @NonNull DelimiterBasedFrameDecoder lineSplitter(final int maxLineLength) {
        return new DelimiterBasedFrameDecoder(maxLineLength, Unpooled.wrappedBuffer(new byte[]{(byte) '\r', (byte) '\n'}));
    }

    static @NonNull StringEncoder stringEncoder() {
        return new StringEncoder(StandardCharsets.UTF_8);
    }

    static @NonNull StringDecoder stringDecoder() {
        return new StringDecoder(StandardCharsets.UTF_8);
    }

    static @NonNull SimpleChannelInboundHandler<String> inputListener(final Consumer<String> consumer) {
        return new SimpleChannelInboundHandler<String>() {
            @Override
            protected void channelRead0(final ChannelHandlerContext ctx, final String msg) {
                if (msg != null) {
                    consumer.accept(msg);
                }
            }
        };
    }

    static @NonNull MessageToMessageEncoder<String> outputListener(final Consumer<String> consumer) {
        return new MessageToMessageEncoder<String>() {
            @Override
            protected void encode(final ChannelHandlerContext ctx, final String msg, final List<Object> out) {
                consumer.accept(msg);
                out.add(msg);
            }
        };
    }

    static @NonNull ChannelInboundHandlerAdapter inboundExceptionHandler(final Consumer<Exception> consumer) {
        return new ChannelInboundHandlerAdapter() {
            @Override
            public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) {
                if (cause instanceof Exception) {
                    consumer.accept((Exception) cause);
                }
            }
        };
    }

    static @NonNull ChannelOutboundHandlerAdapter outboundExceptionHandler(final Consumer<Exception> consumer) {
        return new ChannelOutboundHandlerAdapter() {
            @Override
            public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) {
                if (cause instanceof Exception) {
                    consumer.accept((Exception) cause);
                }
            }
        };
    }
}
