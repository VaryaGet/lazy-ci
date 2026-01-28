package io.github.varyaget.bot.git;

import java.io.File;
import java.io.IOException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.URIish;

public final class GitClient implements Client {
    private final File targetDirectory;

    public GitClient(final File targetDirectory) {
        this.targetDirectory = targetDirectory;
    }

    public File clone(final String url) throws Exception {
        ensureDirectoryExists(this.targetDirectory);
        
        final File repositoryDir = Git.cloneRepository()
            .setURI(url)
            .setDirectory(new File(
                this.targetDirectory,
                new URIish(url).getHumanishName()
            ))
            .call()
            .getRepository()
            .getDirectory()
            .getParentFile();
            
        return repositoryDir;
    }
    
    private void ensureDirectoryExists(final File directory) throws IOException {
        if (!directory.exists() && !directory.mkdirs()) {
            throw new IOException(
                String.format(
                    "Failed to create directory: %s",
                    directory.getAbsolutePath()
                )
            );
        }
    }
}
