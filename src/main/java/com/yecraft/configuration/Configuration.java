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
import java.util.Map;
import java.util.Optional;

public abstract class Configuration extends AbstractConfiguration implements DefaultConfig{

    private final String version;
    private final Logger logger;
    private final File file;
    private Map<String, Object> defaults;
    public Configuration(String version, File file) {
        super();
        this.version = version;
        Validate.notNull(file, "File cannot be null");
        Validate.isTrue(file.getName().endsWith(".yml"), "The file is not a configuration (example.yml)", file.getName());
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
        Validate.notNull(file, "File cannot be null");
        Validate.isTrue(file.getName().endsWith(".yml"), "The file is not a configuration (example.yml)", file.getName());
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

    /**
     * Creates a file, directories if they do not exist
     * and also loads the config
     * You need to call the method
     * before getting the values from the config
     */
    @Override
    public void initialize() {
        if (file.exists()){
            setConfiguration(load(this.file));
            if (!checkVersion()){
                defaults();
                setVersionConfig();
                save(file);
            }
            return;
        }
        createConfig();
    }

    private void setVersionConfig() {
        addDefault("version", version);
    }

    private void createConfig() {
        createFile();
        setConfiguration(load(file));
        defaults();
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
        return YamlConfiguration.loadConfiguration(file);
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
     * @return Configuration from file
     */
    @Override
    public FileConfiguration load() {
        return load(this.file);
    }

    public abstract void defaults();

    /**
     * Saves the configuration to a file
     */
    @Override
    public void save() {
        save(this.file);
    }

    @Override
    public boolean checkVersion() {
        Optional<String> optional = getString("version");
        return optional.map(s -> s.equals(version)).orElse(false);
    }
}
