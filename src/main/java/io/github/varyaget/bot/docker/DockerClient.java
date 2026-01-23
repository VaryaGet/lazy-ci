package io.github.varyaget.bot.docker;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class DockerClient implements Client {
    
    @Override
    public void composeUp(File composeDir) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.directory(composeDir);
        processBuilder.command("docker", "compose", "up", "-d");
        Process process = processBuilder.start();

        String output = readStream(process.getInputStream());
        String error = readStream(process.getErrorStream());

        int exitCode = process.waitFor();
        
        if (exitCode != 0) {
            throw new Exception(
                String.format(
                    "Failed to start docker containers. Exit code: %d\nError: %s\nOutput: %s",
                    exitCode,
                    error,
                    output
                )
            );
        }
    }
    
    private String readStream(InputStream stream) throws Exception {
        return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
    }
}
