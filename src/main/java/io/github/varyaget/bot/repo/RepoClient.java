package io.github.varyaget.bot.repo;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class RepoClient implements Client {

    private final File directory;

    public RepoClient(final File directory) {
        this.directory = directory;
    }

    @Override
    public List<File> repositories() throws Exception {
        ensureDirectoryExists(directory);

        return Arrays.stream(directory.listFiles())
            .filter(File::isDirectory)
            .filter(this::isGitRepository)
            .collect(Collectors.toList());
    }

    private boolean isGitRepository(final File directory) {
        final File gitDir = new File(directory, ".git");
        return gitDir.exists() && gitDir.isDirectory();
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
