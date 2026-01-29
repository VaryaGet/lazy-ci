package io.github.varyaget.bot.git;

import java.io.File;
import java.io.IOException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.URIish;

/**
 * Git client implementation.
 *
 * @since 1.0
 */
public final class GitClient implements Client {
    /**
     * Target directory.
     */
    private final File targetdir;

    /**
     * Constructor.
     *
     * @param targetdir Target directory
     */
    public GitClient(final File targetdir) {
        this.targetdir = targetdir;
    }

    @Override
    public File clone(final String url) throws Exception {
        GitClient.ensureDirectoryExists(this.targetdir);
        return Git.cloneRepository()
            .setURI(url)
            .setDirectory(
                new File(
                    this.targetdir,
                    new URIish(url).getHumanishName()
                )
            )
            .call()
            .getRepository()
            .getDirectory()
            .getParentFile();
    }

    @Override
    public void pull(final String reponame) throws Exception {
        final File repodir = new File(this.targetdir, reponame);
        if (!repodir.exists() || !repodir.isDirectory()) {
            throw new IllegalArgumentException(
                String.format(
                    "Repository '%s' not found in directory: %s",
                    reponame,
                    this.targetdir.getAbsolutePath()
                )
            );
        }
        try (Git git = Git.open(repodir)) {
            git.pull().call();
        }
    }

    private static void ensureDirectoryExists(final File directory) throws IOException {
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
