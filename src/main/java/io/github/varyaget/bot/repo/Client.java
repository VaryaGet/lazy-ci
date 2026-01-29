package io.github.varyaget.bot.repo;

import java.io.File;
import java.util.List;

/**
 * Repository client interface.
 *
 * @since 1.0
 */
public interface Client {
    /**
     * Get list of repositories.
     *
     * @return List of repositories
     * @throws Exception If fails
     */
    List<File> repositories() throws Exception;
}
