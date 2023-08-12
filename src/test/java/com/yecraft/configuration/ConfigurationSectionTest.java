package com.yecraft.configuration;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ConfigurationSectionTest {

    private Configuration config;
    @TempDir
    private Path path;
    @BeforeEach
    void setUp() {
        this.config = new Config("1.0", LoggerFactory.getLogger(ConfigurationSectionTest.class), path.resolve("config.yml"));
        config.initialize();
    }

    @AfterEach
    void tearDown() {
        config.save();
    }

    @Test
    void getKeys() {
        Set<String> keys = config.getKeys(true);
        assertThat(keys).matches(set -> set.contains("version"));
    }

    @Test
    void getValues() {
        Map<String, Object> values = config.getValues(true);
        assertThat(values).matches(stringObjectMap -> stringObjectMap.containsKey("version"));
    }

    @Test
    void ifContains() {
        assertThat(config.contains("version")).isTrue();
    }

    @Test
    void testContainsWithIgnoreDefault() {
        config.set("test", "example");
        boolean bool = config.contains("test", true);
        assertThat(bool).isTrue();
    }

    @Test
    void testContainsWithNonIgnoreDefault() {
        config.addDefault("test", "example");
        boolean bool = config.contains("test", false);
        assertThat(bool).isTrue();
    }

    @Test
    void isSet() {
        config.set("test", Set.of("example", "example1"));
        assertThat(config.isSet("test")).isTrue();
    }

    @Test
    void getCurrentPath() {
        Optional<String> currentPath = config.getCurrentPath();
        assertThat(currentPath).isEmpty();
    }

    @Test
    void getName() {
        String name = config.getName();
        assertThat(name).isEmpty();
    }

    @Test
    void getRoot() {
        Optional<org.bukkit.configuration.Configuration> root = config.getRoot();
        assertThat(root).isNotNull();
    }

    @Test
    void getParent() {
        Optional<ConfigurationSection> parent = config.getParent();
        assertThat(parent).isEmpty();
    }

    @Test
    void get() {
        Optional<Object> version = config.get("version");
        assertThat(version).hasValue("1.0");
    }

    @Test
    void testGetWithAnotherValue() {
        Optional<Object> o = config.get("non-exists", null);
        assertThat(o).isEmpty();
    }

    @Test
    void set() {
        String value = "example-test";
        config.set("test", value);
        assertThat(config.getString("test")).hasValue(value);
    }

    @Test
    void createEmptySection() {
        ConfigurationSection section = config.createSection("section");
        assertThat(section).isNotNull();
    }

    @Test
    void createSectionWithValues() {
        ConfigurationSection section = config.createSection("section", Map.of("example", "example1"));
        assertThat(section).isNotNull();
        assertThat(section.get("example")).matches(str -> str.equals("example1"));
    }

    @Test
    void getString() {
        Optional<String> version = config.getString("version");
        assertThat(version).hasValue("1.0");
    }

    @Test
    void testGetString() {
        Optional<String> string = config.getString("non-exists", "");
        assertThat(string).hasValue("");
    }

    @Test
    void isString() {
        config.set("string", "test");
        assertThat(config.isString("string")).isTrue();
    }

    @Test
    void getInt() {
        config.set("int", 1);
        assertThat(config.getInt("int")).hasValue(1);
    }

    @Test
    void testGetInt() {
        assertThat(config.getInt("non-exists", 0)).isEqualTo(0);
    }

    @Test
    void isInt() {
        config.set("int", 1);
        assertThat(config.isInt("int")).isTrue();
    }

    @Test
    void getBoolean() {
        config.set("bool", true);
        assertThat(config.getBoolean("bool")).isTrue();
    }

    @Test
    void testGetBoolean() {
        assertThat(config.getBoolean("non-exists", false)).isFalse();
    }

    @Test
    void isBoolean() {
        config.set("bool", true);
        assertThat(config.isBoolean("bool")).isTrue();
    }

    @Test
    void getDouble() {
        config.set("double", Double.MAX_VALUE);
        assertThat(config.getDouble("double")).hasValue(Double.MAX_VALUE);
    }

    @Test
    void testGetDouble() {
        assertThat(config.getDouble("non-exists", 1.0)).isEqualTo(1.0);
    }

    @Test
    void isDouble() {
        config.set("double", Double.MAX_VALUE);
        assertThat(config.isDouble("double")).isTrue();
    }

    @Test
    void getLong() {
        config.set("long", Long.MAX_VALUE);
        assertThat(config.getLong("long")).contains(Long.MAX_VALUE);
    }

    @Test
    void testGetLong() {
        assertThat(config.getLong("non-exists", Long.MAX_VALUE)).isEqualTo(Long.MAX_VALUE);
    }

    @Test
    void isLong() {
        config.set("long", Long.MAX_VALUE);
        assertThat(config.isLong("long")).isTrue();
    }

    @Test
    void getList() {
        List<String> gege = List.of("gege", "gegegge");
        config.set("list", gege);
        assertThat(config.getList("list")).contains(gege);
    }

    @Test
    void testGetList() {
        List<String> test = List.of("test");
        assertThat(config.getList("non-exists", test)).contains(test);
    }

    @Test
    void isList() {
        config.set("list", List.of("test"));
        assertThat(config.isList("list")).isTrue();
    }

    @Test
    void getStringList() {
        List<String> test = List.of("test", "test1");
        config.set("test", test);
        assertThat(config.getStringList("test")).hasValue(test);
    }

    @Test
    void getIntegerList() {
        List<Integer> value = List.of(1, 2, 3, 4, 5);
        config.set("test", value);
        assertThat(config.getIntegerList("test")).hasValue(value);
    }

    @Test
    void getBooleanList() {
        List<Boolean> value = List.of(true, false, true, false);
        config.set("test", value);
        assertThat(config.getBooleanList("test")).hasValue(value);
    }

    @Test
    void getDoubleList() {
        List<Double> maxValue = List.of(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
        config.set("test", maxValue);
        assertThat(config.getDoubleList("test")).hasValue(maxValue);
    }

    @Test
    void getFloatList() {
        List<Float> maxValue = List.of(Float.MAX_VALUE);
        config.set("test", maxValue);
        assertThat(config.getFloatList("test")).hasValue(maxValue);
    }

    @Test
    void getLongList() {
        List<Long> maxValue = List.of(Long.MAX_VALUE);
        config.set("test", maxValue);
        assertThat(config.getLongList("test")).hasValue(maxValue);
    }

    @Test
    void getByteList() {
        List<Byte> maxValue = List.of(Byte.MAX_VALUE);
        config.set("test", maxValue);
        assertThat(config.getByteList("test")).hasValue(maxValue);
    }

    @Test
    void getCharacterList() {
        List<Character> maxValue = List.of(Character.MAX_VALUE);
        config.set("test", maxValue);
        assertThat(config.getCharacterList("test")).hasValue(maxValue);
    }

    @Test
    void getShortList() {
        List<Short> maxValue = List.of(Short.MAX_VALUE);
        config.set("test", maxValue);
        assertThat(config.getShortList("test")).hasValue(maxValue);
    }

    @Test
    void getMapList() {
        List<Map<?, ?>> test = List.of(Map.of("test", "test"));
        config.set("test", test);
        assertThat(config.getMapList("test")).hasValue(test);
    }

    @Test
    void getObject() {
        config.set("test", Integer.MAX_VALUE);
        assertThat(config.getObject("test", Integer.class)).hasValue(Integer.MAX_VALUE);
    }

    @Test
    void testGetObject() {
        assertThat(config.getObject("non-exists", Integer.class, 5)).hasValue(5);
    }

    @Test
    void getSerializable() {
        Serializible test = new Serializible("test");
        config.set("test", test);
        assertThat(config.getSerializable("test", Serializible.class)).hasValue(test);
    }

    @Test
    void testGetSerializable() {
        Serializible exists = new Serializible("exists");
        assertThat(config.getSerializable("non-exists", Serializible.class, exists)).hasValue(exists);
    }

    @Test
    void getConfigurationSection() {
        config.createSection("test");
        assertThat(config.getConfigurationSection("test")).isPresent();
    }

    @Test
    void isConfigurationSection() {
        config.createSection("test");
        assertThat(config.isConfigurationSection("test")).isTrue();
    }

    @Test
    void getDefaultSection() {
        Optional<ConfigurationSection> defaultSection = config.getDefaultSection();
        assertThat(defaultSection).isEmpty();
    }

    @Test
    void addDefault() {
        config.addDefault("test", "default");
        assertThat(config.get("test")).hasValue("default");
    }

    @Test
    void getComments() {
        config.set("test", "test");
        List<String> comment = List.of("comment");
        config.setComments("test", comment);
        assertThat(config.getComments("test")).hasValue(comment);
    }

    @Test
    void getInlineComments() {
        config.set("test", "test");
        List<String> comment = List.of("comment");
        config.setInlineComments("test", comment);
        assertThat(config.getInlineComments("test")).hasValue(comment);
    }

    @Test
    void setComments() {
        config.set("test", "test");
        List<String> comment = List.of("comment");
        config.setComments("test", comment);
        assertThat(config.getComments("test")).hasValue(comment);
    }

    @Test
    void setInlineComments() {
        config.set("test", "test");
        List<String> comment = List.of("comment");
        config.setInlineComments("test", comment);
        assertThat(config.getInlineComments("test")).hasValue(comment);
    }

    class Config extends Configuration{

        public Config(String version, @NotNull Logger logger, @NotNull Path path) {
            super(version, logger, path);
        }

        @Override
        public Map<String, Object> defaults() {
            return Map.of();
        }

        @Override
        public Map<String, List<String>> defaultComments() {
            return Map.of();
        }
    }

    class Serializible implements ConfigurationSerializable {

        private String name;

        public Serializible(String name) {
            this.name = name;
        }

        @Override
        public @NotNull Map<String, Object> serialize() {
            return Map.of("name", name);
        }
    }
}