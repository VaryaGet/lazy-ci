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
