package io.github.varyaget.bot.repo;

import java.io.File;
import java.util.List;

public interface Client {
    List<File> repositories() throws Exception;
}
