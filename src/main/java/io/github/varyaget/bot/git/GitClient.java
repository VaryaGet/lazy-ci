package io.github.varyaget.bot.git;

import java.io.File;
import java.io.IOException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.URIish;

public final class GitClient implements Client {
    public File clone(final String url, final File targetDirectory) throws Exception {
        final File repositoryDir = new File(
            targetDirectory,
            new URIish(url).getHumanishName()
        );
        
        ensureDirectoryExists(targetDirectory);
        
        Git.cloneRepository()
            .setURI(url)
            .setDirectory(repositoryDir)
            .call();
            
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
