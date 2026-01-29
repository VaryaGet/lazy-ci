package io.github.varyaget.bot.repo;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

class RepoClientTest {

    @TempDir
    Path tempDir;

    @Test
    void testConstructor() {
        RepoClient client = new RepoClient(tempDir.toFile());
        assertNotNull(client);
    }

    @Test
    void testRepositoriesWithEmptyDirectory() throws Exception {
        RepoClient client = new RepoClient(tempDir.toFile());
        List<File> repositories = client.repositories();
        assertNotNull(repositories);
        assertTrue(repositories.isEmpty());
    }

    @Test
    void testRepositoriesWithNonGitDirectory() throws Exception {
        File nonGitDir = new File(tempDir.toFile(), "non-git-dir");
        assertTrue(nonGitDir.mkdirs());

        RepoClient client = new RepoClient(tempDir.toFile());
        List<File> repositories = client.repositories();
        assertNotNull(repositories);
        assertTrue(repositories.isEmpty());
    }

    @Test
    void testRepositoriesWithGitRepository() throws Exception {
        File gitDir = new File(tempDir.toFile(), "git-repo");
        assertTrue(gitDir.mkdirs());
        File dotGitDir = new File(gitDir, ".git");
        assertTrue(dotGitDir.mkdirs());

        RepoClient client = new RepoClient(tempDir.toFile());
        List<File> repositories = client.repositories();
        assertNotNull(repositories);
        assertEquals(1, repositories.size());
        assertEquals(gitDir.getAbsolutePath(), repositories.get(0).getAbsolutePath());
    }

    @Test
    void testRepositoriesWithMultipleGitRepositories() throws Exception {
        for (int i = 1; i <= 3; i++) {
            File gitDir = new File(tempDir.toFile(), "repo-" + i);
            assertTrue(gitDir.mkdirs());
            File dotGitDir = new File(gitDir, ".git");
            assertTrue(dotGitDir.mkdirs());
        }

        File nonGitDir = new File(tempDir.toFile(), "non-git-dir");
        assertTrue(nonGitDir.mkdirs());

        RepoClient client = new RepoClient(tempDir.toFile());
        List<File> repositories = client.repositories();
        assertNotNull(repositories);
        assertEquals(3, repositories.size());
    }

    @Test
    void testRepositoriesWithFileNamedDotGit() throws Exception {
        File gitDir = new File(tempDir.toFile(), "not-a-repo");
        assertTrue(gitDir.mkdirs());
        File dotGitFile = new File(gitDir, ".git");
        assertTrue(dotGitFile.createNewFile());

        RepoClient client = new RepoClient(tempDir.toFile());
        List<File> repositories = client.repositories();
        assertNotNull(repositories);
        assertTrue(repositories.isEmpty());
    }

    @Test
    void testRepositoriesWhenDirectoryDoesNotExist() throws Exception {
        File nonExistentDir = new File(tempDir.toFile(), "non-existent");
        RepoClient client = new RepoClient(nonExistentDir);

        List<File> repositories = client.repositories();
        assertNotNull(repositories);
        assertTrue(repositories.isEmpty());
        assertTrue(nonExistentDir.exists());
    }

    @Test
    void testRepositoriesWithNestedGitRepository() throws Exception {
        File nestedDir = new File(tempDir.toFile(), "level1/level2/level3");
        assertTrue(nestedDir.mkdirs());
        File dotGitDir = new File(nestedDir, ".git");
        assertTrue(dotGitDir.mkdirs());

        RepoClient client = new RepoClient(tempDir.toFile());
        List<File> repositories = client.repositories();
        assertNotNull(repositories);
        assertTrue(repositories.isEmpty());
    }
}
