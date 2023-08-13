package com.yecraft.configuration;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ConfigurationTest {

    private Configuration underTest;
    private String version;
    @TempDir
    private Path path;

    @BeforeEach
    void setUp() {
        path = path.resolve("config.yml");
        this.version = "1.0";
        this.underTest = new Config(version, LoggerFactory.getLogger(ConfigurationTest.class), path);
    }

    @Test
    @DisplayName("Config initialize")
    void canInitialize() {
        underTest.initialize();
        assertThat(underTest.getConfiguration()).isNotNull();
    }

    @Test
    void testLoadWithArgument() {
        underTest.load(path.toFile());
        assertThat(underTest.getConfiguration()).isNotNull();
    }

    @Test
    void testSaveWithArgument() {
        underTest.initialize();
        underTest.save(path.toFile());
        assertThat(path.toFile()).exists();
    }

    @Test
    void testLoadWithoutArgument() {
        underTest.load();
        assertThat(underTest.getConfiguration()).isNotNull();
    }

    @Test
    void testDefaults() {
        underTest.initialize();
        assertThat(underTest.getString("string")).hasValue("string");
        assertThat(underTest.getComments("string")).contains(List.of("comment"));
        assertThat(underTest.getString("map.string")).hasValue("string");
    }

    @Test
    void testSaveWithoutArguments() {
        underTest.initialize();
        underTest.save();
        assertThat(underTest.getConfiguration()).isNotNull();
        assertThat(this.path.toFile()).exists();
    }

    @Test
    void checkVersion() {
        underTest.initialize();
        Path path1 = path.resolve("config.yml");
        ConfigCopy copy = new ConfigCopy("1.1", LoggerFactory.getLogger(ConfigurationTest.class), path1);
        copy.initialize();
        assertThat(copy.getString("string")).hasValue("string-copy");
    }

    @Test
    //TODO solve the overwriting issue
    void checkIfDefaultsChangesAfterReload(){
        underTest.initialize();
        underTest.set("string", "string-change");
        underTest.save();
        underTest.initialize();
        assertThat(underTest.getString("string")).hasValue("string-change");
    }

    class Config extends Configuration {

        public Config(String version, @NotNull Logger logger, @NotNull Path path) {
            super(version, logger, path);
        }

        @Override
        public Map<String, Object> defaults() {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("string", "string");
            map.put("map", Map.of("string", "string"));
            return map;
        }

        @Override
        public Map<String, List<String>> defaultComments() {
            Map<String, List<String>> map = new LinkedHashMap<>();
            map.put("string", List.of("comment"));
            return map;
        }
    }

    class ConfigCopy extends Configuration {

        public ConfigCopy(String version, @NotNull Logger logger, @NotNull Path path) {
            super(version, logger, path);
        }

        @Override
        public Map<String, Object> defaults() {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("string", "string-copy");
            return map;
        }

        @Override
        public Map<String, List<String>> defaultComments() {
            return Map.of();
        }
    }
}