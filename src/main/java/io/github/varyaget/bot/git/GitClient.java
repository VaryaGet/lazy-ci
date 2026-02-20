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
