/*
 * MIT License
 *
 * Copyright (c) 2026 Varvara Getmanskaya
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
