package io.github.varyaget.bot.docker;

import java.io.File;

public interface Client {
    void composeUp(File composeDir) throws Exception;
}
