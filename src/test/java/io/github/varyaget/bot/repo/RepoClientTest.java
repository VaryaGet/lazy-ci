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
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Test for RepoClient.
 *
 * @since 1.0
 */
final class RepoClientTest {

    /**
     * Temporary directory.
     */
    @TempDir
    private Path tempdir;

    @Test
    void testConstructor() {
        final RepoClient client = new RepoClient(this.tempdir.toFile());
        Assertions.assertNotNull(client);
    }

    @Test
    void testRepositoriesWithEmptyDirectory() throws Exception {
        final RepoClient client = new RepoClient(this.tempdir.toFile());
        final List<File> repositories = client.repositories();
        Assertions.assertNotNull(repositories);
        Assertions.assertTrue(repositories.isEmpty());
    }

    @Test
    void testRepositoriesWithNonGitDirectory() throws Exception {
        final File nongitdir = new File(this.tempdir.toFile(), "non-git-dir");
        Assertions.assertTrue(nongitdir.mkdirs());
        final RepoClient client = new RepoClient(this.tempdir.toFile());
        final List<File> repositories = client.repositories();
        Assertions.assertNotNull(repositories);
        Assertions.assertTrue(repositories.isEmpty());
    }

    @Test
    void testRepositoriesWithGitRepository() throws Exception {
        final File gitdir = new File(this.tempdir.toFile(), "git-repo");
        Assertions.assertTrue(gitdir.mkdirs());
        final File dotgitdir = new File(gitdir, ".git");
        Assertions.assertTrue(dotgitdir.mkdirs());
        final RepoClient client = new RepoClient(this.tempdir.toFile());
        final List<File> repositories = client.repositories();
        Assertions.assertNotNull(repositories);
        Assertions.assertEquals(1, repositories.size());
        Assertions.assertEquals(gitdir.getAbsolutePath(), repositories.get(0).getAbsolutePath());
    }

    @Test
    void testRepositoriesWithMultipleGitRepositories() throws Exception {
        for (int idx = 1; idx <= 3; idx = idx + 1) {
            final File gitdir = new File(this.tempdir.toFile(), String.format("repo-%d", idx));
            Assertions.assertTrue(gitdir.mkdirs());
            final File dotgitdir = new File(gitdir, ".git");
            Assertions.assertTrue(dotgitdir.mkdirs());
        }
        final File nongitdir = new File(this.tempdir.toFile(), "non-git-dir");
        Assertions.assertTrue(nongitdir.mkdirs());
        final RepoClient client = new RepoClient(this.tempdir.toFile());
        final List<File> repositories = client.repositories();
        Assertions.assertNotNull(repositories);
        Assertions.assertEquals(3, repositories.size());
    }

    @Test
    void testRepositoriesWithFileNamedDotGit() throws Exception {
        final File gitdir = new File(this.tempdir.toFile(), "not-a-repo");
        Assertions.assertTrue(gitdir.mkdirs());
        final File dotgitfile = new File(gitdir, ".git");
        Assertions.assertTrue(dotgitfile.createNewFile());
        final RepoClient client = new RepoClient(this.tempdir.toFile());
        final List<File> repositories = client.repositories();
        Assertions.assertNotNull(repositories);
        Assertions.assertTrue(repositories.isEmpty());
    }

    @Test
    void testRepositoriesWhenDirectoryDoesNotExist() throws Exception {
        final File nonexist = new File(this.tempdir.toFile(), "non-existent");
        final RepoClient client = new RepoClient(nonexist);
        final List<File> repositories = client.repositories();
        Assertions.assertNotNull(repositories);
        Assertions.assertTrue(repositories.isEmpty());
        Assertions.assertTrue(nonexist.exists());
    }

    @Test
    void testRepositoriesWithNestedGitRepository() throws Exception {
        final File nesteddir = new File(this.tempdir.toFile(), "level1/level2/level3");
        Assertions.assertTrue(nesteddir.mkdirs());
        final File dotgitdir = new File(nesteddir, ".git");
        Assertions.assertTrue(dotgitdir.mkdirs());
        final RepoClient client = new RepoClient(this.tempdir.toFile());
        final List<File> repositories = client.repositories();
        Assertions.assertNotNull(repositories);
        Assertions.assertTrue(repositories.isEmpty());
    }
}
