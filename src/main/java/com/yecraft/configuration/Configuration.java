package com.yecraft.configuration;

import org.apache.commons.lang3.Validate;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class Configuration extends AbstractConfiguration implements DefaultConfig{

    private final String version;
    private final Logger logger;
    private final File file;
    public Configuration(String version, File file) {
        super();
        this.version = version;
        validateNotNull(file);
        this.file = file;
        this.logger = LoggerFactory.getLogger(Configuration.class);
    }

    public Configuration(String version, Path path) {
        super();
        this.version = version;
        Validate.notNull(path, "File cannot be null");
        Validate.isTrue(path.toFile().getName().endsWith(".yml"), "The file is not a configuration (example.yml)", path.toFile().getName());
        this.file = path.toFile();
        this.logger = LoggerFactory.getLogger(Configuration.class);
    }

    public Configuration(String version, @NotNull Logger logger, @NotNull File file) {
        super();
        this.version = version;
        validateNotNull(file);
        Validate.notNull(logger, "Logger cannot be null");
        this.logger = logger;
        this.file = file;
    }

    public Configuration(String version, @NotNull Logger logger, @NotNull Path path) {
        super();
        this.version = version;
        Validate.notNull(path, "File cannot be null");
        Validate.isTrue(path.toFile().getName().endsWith(".yml"), "The file is not a configuration (example.yml)", path.toFile().getName());
        Validate.notNull(logger, "Logger cannot be null");
        this.logger = logger;
        this.file = path.toFile();
    }

    private static void validateNotNull(File file) {
        Validate.notNull(file, "File cannot be null");
        Validate.isTrue(file.getName().endsWith(".yml"), "The file is not a configuration (example.yml)", file.getName());
    }

    /**
     * Creates a file, directories if they do not exist
     * and also loads the config
     * You need to call the method
     * before getting the values from the config
     */
    @Override
    public void initialize() {
        if (file.exists()){
            load(this.file);
            checkDefaults();
            save(file);
            return;
        }
        createConfig();
    }

    private void checkDefaults(File file) {
        if (!checkVersion()){
            setVersionConfig();
            defaults().forEach((str, obj) -> {
                if (obj instanceof Map<?,?>){
                    createSection(str, (Map<?, ?>) obj);
                    return;
                }
                set(str, obj);
            });
            defaultComments().forEach(this::setComments);
            save(file);
        }
    }

    private void checkDefaults() {
        checkDefaults(this.file);
    }

    private void setVersionConfig() {
        addDefault("version", version);
    }

    private void createConfig() {
        createFile();
        setConfiguration(load(file));
        checkDefaults();
        setVersionConfig();
        save(file);
    }

    private void createFile() {
        try {
            Files.createDirectories(file.toPath().getParent());
            file.createNewFile();
        } catch (IOException e) {
            logger.warn("The file could not be created", e);
        }
    }

    @Override
    protected FileConfiguration load(File file) {
        Validate.notNull(file, "File cannot be null");
        setConfiguration(YamlConfiguration.loadConfiguration(file));
        return getConfiguration();
    }

    @Override
    protected void save(File file) {
        Validate.notNull(file, "File cannot be null");
        FileConfiguration config = getConfiguration();
        Validate.notNull(config, "The configuration cannot be null", config);
        try {
            config.save(file);
        } catch (IOException e) {
            logger.warn("The configuration cannot be saved", e);
        }
    }

    /**
     * Loads the configuration file into memory.
     * It may give an error if the file does not exist or its extension is incorrect
     * @return {@code Configuration} from file
     */
    @Override
    public FileConfiguration load() {
        return load(this.file);
    }

    public abstract Map<String, Object> defaults();
    public abstract Map<String, List<String>> defaultComments();

    /**
     * Saves the configuration to a file
     */
    @Override
    public void save() {
        save(this.file);
    }

    /**
     * Checking the configuration version (to change the default settings if the version has been updated)
     * @return Coincidence with the version specified in the class ({@code true} - matches, {@code false} - does not match)
     */
    @Override
    public boolean checkVersion() {
        Optional<String> optional = getString("version");
        return optional.map(s -> s.equals(version)).orElse(false);
    }
}
