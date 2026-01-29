package io.github.varyaget.bot.git;

import java.io.File;

public interface Client {
    File clone(String url) throws Exception;

    void pull(String repositoryName) throws Exception;
}
