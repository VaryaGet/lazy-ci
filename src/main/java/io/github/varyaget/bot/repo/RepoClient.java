package io.github.varyaget.bot.repo;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Repository client implementation.
 *
 * @since 1.0
 */
public final class RepoClient implements Client {

    /**
     * Directory.
     */
    private final File directory;

    /**
     * Constructor.
     *
     * @param directory Directory
     */
    public RepoClient(final File directory) {
        this.directory = directory;
    }

    @Override
    public List<File> repositories() throws Exception {
        RepoClient.ensureDirectoryExists(this.directory);
        return Arrays.stream(this.directory.listFiles())
            .filter(File::isDirectory)
            .filter(RepoClient::isGitRepository)
            .collect(Collectors.toList());
    }

    /**
     * Check if directory is git repository.
     *
     * @param directory Directory
     * @return True if git repository
     */
    private static boolean isGitRepository(final File directory) {
        final File gitdir = new File(directory, ".git");
        return gitdir.exists() && gitdir.isDirectory();
    }

    /**
     * Ensure directory exists.
     *
     * @param directory Directory
     * @throws IOException If cannot create directory
     */
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
