package io.github.varyaget.bot.git;

import java.io.File;

/**
 * Git client interface.
 *
 * @since 1.0
 */
public interface Client {
    /**
     * Clone repository.
     *
     * @param url Repository URL
     * @return Repository directory
     * @throws Exception If fails
     */
    File clone(String url) throws Exception;

    /**
     * Pull repository.
     *
     * @param reponame Repository name
     * @throws Exception If fails
     */
    void pull(String reponame) throws Exception;
}
