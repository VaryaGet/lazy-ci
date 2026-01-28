package io.github.varyaget.bot.git;

import java.io.File;
import java.io.IOException;
import org.eclipse.jgit.api.errors.GitAPIException;

public interface Client {
    File clone(String url) throws Exception;
    void pull(String repositoryName) throws Exception;
}
