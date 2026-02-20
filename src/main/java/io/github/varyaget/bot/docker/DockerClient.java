/*
 * MIT License
 *
 * Copyright (c) 2026 Varvara Getmanskaya
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.varyaget.bot.docker;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Docker client implementation.
 *
 * @since 1.0
 */
public final class DockerClient implements Client {

    @Override
    public void composeUp(final File composedir) throws IOException {
        final Process process = new ProcessBuilder()
            .directory(composedir)
            .command("docker", "compose", "up", "-d")
            .start();
        try {
            if (process.waitFor() != 0) {
                throw new IOException(
                    String.format(
                        "Failed to start docker containers. Exit code: %d\nError: %s\nOutput: %s",
                        process.waitFor(),
                        DockerClient.readStream(process.getErrorStream()),
                        DockerClient.readStream(process.getInputStream())
                    )
                );
            }
        } catch (final InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IOException("Docker process was interrupted", exception);
        }
    }

    /**
     * Read stream.
     *
     * @param stream Stream
     * @return String
     * @throws IOException If cannot read
     */
    private static String readStream(final InputStream stream) throws IOException {
        return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
    }
}
