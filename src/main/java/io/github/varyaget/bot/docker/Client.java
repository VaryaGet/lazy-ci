package io.github.varyaget.bot.docker;

import java.io.File;
import java.io.IOException;

/**
 * Docker client interface.
 *
 * @since 1.0
 */
public interface Client {
    /**
     * Run docker compose up.
     *
     * @param composedir Compose directory
     * @throws IOException If fails
     */
    void composeUp(File composedir) throws IOException;
}
