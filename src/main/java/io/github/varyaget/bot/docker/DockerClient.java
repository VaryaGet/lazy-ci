package io.github.varyaget.bot.docker;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class DockerClient implements Client {

    @Override
    public void composeUp(File composeDir) throws Exception {
        Process process = new ProcessBuilder()
            .directory(composeDir)
            .command("docker", "compose", "up", "-d")
            .start();

        if (process.waitFor() != 0) {
            throw new Exception(
                String.format(
                    "Failed to start docker containers. Exit code: %d\nError: %s\nOutput: %s",
                    process.waitFor(),
                    readStream(process.getErrorStream()),
                    readStream(process.getInputStream())
                )
            );
        }
    }

    private String readStream(InputStream stream) throws Exception {
        return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
    }
}
