package io.github.mannjamin.ducky.util;

import com.google.gson.JsonParser;
import io.github.mannjamin.ducky.McDucky;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.URL;

/**
 * Configuration management controller
 *
 * @author Matthew Struble
 */
public class ConfigUtil {
    /**
     * The path to the configuration file
     */
    private static final String FILE_PATH = "config.json";

    /**
     * Attempts to load the configuration file from disk.
     *
     * @return The configuration object loaded
     * @throws IOException If the configuration file could not be loaded
     */
    private static Configuration loadConfiguration() throws IOException, UndefinedTokenException {
        final Reader r = new FileReader(new File(FILE_PATH));
        final JsonParser parser = new JsonParser();

        final Configuration config = new Configuration(parser.parse(r).getAsJsonObject());

        r.close();
        return config;
    }

    /**
     * Initializes the default configuration file to disk
     *
     * @throws IOException If the configuration file could not be written or copied.
     */
    private static void createConfiguration() throws IOException {
        final URL url = McDucky.class.getResource("/" + FILE_PATH);
        FileUtils.copyURLToFile(url, new File(FILE_PATH));
    }

    /**
     * Attempts to get the configuration, creating a copy if it does not exist
     *
     * @return The configuration object
     * @throws IOException Parsing or file system errors
     */
    public static Configuration getConfiguration() throws IOException, UndefinedTokenException {
        try {
            return ConfigUtil.loadConfiguration();
        } catch (FileNotFoundException e) {
            ConfigUtil.createConfiguration();
            return ConfigUtil.getConfiguration();
        }
    }
}
