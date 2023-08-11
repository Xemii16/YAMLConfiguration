package com.yecraft.configuration;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public abstract class AbstractConfiguration implements IConfiguration {

    private FileConfiguration configuration;

    public AbstractConfiguration(FileConfiguration configuration) {
        this.configuration = configuration;
    }

    public AbstractConfiguration() {
    }

    @Override
    public @NotNull Set<String> getKeys(boolean deep) {
        return configuration.getKeys(deep);
    }

    @Override
    public @NotNull Map<String, Object> getValues(boolean deep) {
        return configuration.getValues(deep);
    }

    @Override
    public boolean contains(@NotNull String path) {
        return configuration.contains(path);
    }

    @Override
    public boolean contains(@NotNull String path, boolean ignoreDefault) {
        return configuration.contains(path, ignoreDefault);
    }

    @Override
    public boolean isSet(@NotNull String path) {
        return configuration.isSet(path);
    }

    @Override
    public @NotNull Optional<String> getCurrentPath() {
        String currentPath = configuration.getCurrentPath();
        if (!currentPath.isEmpty()) return Optional.of(currentPath);
        return Optional.empty();
    }

    @Override
    public @NotNull String getName() {
        return configuration.getName();
    }

    @Override
    public @NotNull Optional<Configuration> getRoot() {
        Configuration config = configuration.getRoot();
        return Optional.ofNullable(config);
    }

    @Override
    public @NotNull Optional<ConfigurationSection> getParent() {
        ConfigurationSection section = configuration.getParent();
        return Optional.ofNullable(section);
    }

    @Override
    public @NotNull Optional<Object> get(@NotNull String path) {
        Object object = configuration.get(path);
        return Optional.ofNullable(object);
    }

    @Override
    public @NotNull Optional<Object> get(@NotNull String path, @Nullable Object def) {
        Object object = configuration.get(path, def);
        return Optional.ofNullable(object);
    }

    @Override
    public void set(@NotNull String path, @Nullable Object value) {
        configuration.set(path, value);
    }

    @Override
    public @NotNull ConfigurationSection createSection(@NotNull String path) {
        return configuration.createSection(path);
    }

    @Override
    public @NotNull ConfigurationSection createSection(@NotNull String path, @NotNull Map<?, ?> map) {
        return configuration.createSection(path, map);
    }

    @Override
    public @NotNull Optional<String> getString(@NotNull String path) {
        String string = configuration.getString(path);
        return Optional.ofNullable(string);
    }

    @Override
    public @NotNull Optional<String> getString(@NotNull String path, @Nullable String def) {
        String string = configuration.getString(path, def);
        return Optional.ofNullable(string);
    }

    @Override
    public boolean isString(@NotNull String path) {
        return configuration.isString(path);
    }

    @Override
    public @NotNull Optional<Integer> getInt(@NotNull String path) {
        int integer = configuration.getInt(path);
        if (integer != 0) return Optional.of(integer);
        return Optional.empty();
    }

    @Override
    public int getInt(@NotNull String path, int def) {
        return configuration.getInt(path, def);
    }

    @Override
    public boolean isInt(@NotNull String path) {
        return configuration.isInt(path);
    }

    @Override
    public boolean getBoolean(@NotNull String path) {
        return configuration.getBoolean(path);
    }

    @Override
    public boolean getBoolean(@NotNull String path, boolean def) {
        return configuration.getBoolean(path, def);
    }

    @Override
    public boolean isBoolean(@NotNull String path) {
        return configuration.isBoolean(path);
    }

    @Override
    public @NotNull Optional<Double> getDouble(@NotNull String path) {
        double number = configuration.getDouble(path);
        if (number != 0) return Optional.of(number);
        return Optional.empty();
    }

    @Override
    public double getDouble(@NotNull String path, double def) {
        return configuration.getDouble(path, def);
    }

    @Override
    public boolean isDouble(@NotNull String path) {
        return configuration.isDouble(path);
    }

    @Override
    public @NotNull Optional<Long> getLong(@NotNull String path) {
        long number = configuration.getLong(path);
        if (number != 0) return Optional.of(number);
        return Optional.empty();
    }

    @Override
    public long getLong(@NotNull String path, long def) {
        return configuration.getLong(path, def);
    }

    @Override
    public boolean isLong(@NotNull String path) {
        return configuration.isLong(path);
    }

    @Override
    public @NotNull Optional<List<?>> getList(@NotNull String path) {
        List<?> list = configuration.getList(path);
        return Optional.ofNullable(list);
    }

    @Override
    public @NotNull Optional<List<?>> getList(@NotNull String path, @Nullable List<?> def) {
        List<?> list = configuration.getList(path, def);
        return Optional.ofNullable(list);
    }

    @Override
    public boolean isList(@NotNull String path) {
        return configuration.isList(path);
    }

    @Override
    public @NotNull Optional<List<String>> getStringList(@NotNull String path) {
        List<String> list = configuration.getStringList(path);
        if (!list.isEmpty()) return Optional.of(list);
        return Optional.empty();
    }

    @Override
    public @NotNull Optional<List<Integer>> getIntegerList(@NotNull String path) {
        List<Integer> list = configuration.getIntegerList(path);
        if (!list.isEmpty()) return Optional.of(list);
        return Optional.empty();
    }

    @Override
    public @NotNull Optional<List<Boolean>> getBooleanList(@NotNull String path) {
        List<Boolean> list = configuration.getBooleanList(path);
        if (!list.isEmpty()) return Optional.of(list);
        return Optional.empty();
    }

    @Override
    public @NotNull Optional<List<Double>> getDoubleList(@NotNull String path) {
        List<Double> list = configuration.getDoubleList(path);
        if (!list.isEmpty()) return Optional.of(list);
        return Optional.empty();
    }

    @Override
    public @NotNull Optional<List<Float>> getFloatList(@NotNull String path) {
        List<Float> list = configuration.getFloatList(path);
        if (!list.isEmpty()) return Optional.of(list);
        return Optional.empty();
    }

    @Override
    public @NotNull Optional<List<Long>> getLongList(@NotNull String path) {
        List<Long> list = configuration.getLongList(path);
        if (!list.isEmpty()) return Optional.of(list);
        return Optional.empty();
    }

    @Override
    public @NotNull Optional<List<Byte>> getByteList(@NotNull String path) {
        List<Byte> list = configuration.getByteList(path);
        if (!list.isEmpty()) return Optional.of(list);
        return Optional.empty();
    }

    @Override
    public @NotNull Optional<List<Character>> getCharacterList(@NotNull String path) {
        List<Character> list = configuration.getCharacterList(path);
        if (!list.isEmpty()) return Optional.of(list);
        return Optional.empty();
    }

    @Override
    public @NotNull Optional<List<Short>> getShortList(@NotNull String path) {
        List<Short> list = configuration.getShortList(path);
        if (!list.isEmpty()) return Optional.of(list);
        return Optional.empty();
    }

    @Override
    public @NotNull Optional<List<Map<?, ?>>> getMapList(@NotNull String path) {
        List<Map<?, ?>> list = configuration.getMapList(path);
        if (!list.isEmpty()) return Optional.of(list);
        return Optional.empty();
    }

    @Override
    public @NotNull <T> Optional<T> getObject(@NotNull String path, @NotNull Class<T> clazz) {
        T object = configuration.getObject(path, clazz);
        return Optional.ofNullable(object);
    }

    @Override
    public @NotNull <T> Optional<T> getObject(@NotNull String path, @NotNull Class<T> clazz, @Nullable T def) {
        T object = configuration.getObject(path, clazz, def);
        return Optional.ofNullable(object);
    }

    @Override
    public @NotNull <T extends ConfigurationSerializable> Optional<T> getSerializable(@NotNull String path, @NotNull Class<T> clazz) {
        T object = configuration.getSerializable(path, clazz);
        return Optional.ofNullable(object);
    }

    @Override
    public @NotNull <T extends ConfigurationSerializable> Optional<T> getSerializable(@NotNull String path, @NotNull Class<T> clazz, @Nullable T def) {
        T object = configuration.getSerializable(path, clazz, def);
        return Optional.ofNullable(object);
    }

    @Override
    public @NotNull Optional<ConfigurationSection> getConfigurationSection(@NotNull String path) {
        ConfigurationSection section = configuration.getConfigurationSection(path);
        return Optional.ofNullable(section);
    }

    @Override
    public boolean isConfigurationSection(@NotNull String path) {
        return configuration.isConfigurationSection(path);
    }

    @Override
    public @NotNull Optional<ConfigurationSection> getDefaultSection() {
        ConfigurationSection section = configuration.getDefaultSection();
        return Optional.ofNullable(section);
    }

    @Override
    public void addDefault(@NotNull String path, @Nullable Object value) {
        configuration.addDefault(path, value);
    }

    @Override
    public @NotNull Optional<List<String>> getComments(@NotNull String path) {
        List<String> comments = configuration.getComments(path);
        if (!comments.isEmpty()) return Optional.of(comments);
        return Optional.empty();
    }

    @Override
    public @NotNull Optional<List<String>> getInlineComments(@NotNull String path) {
        List<String> inlineComments = configuration.getInlineComments(path);
        if (!inlineComments.isEmpty()) return Optional.of(inlineComments);
        return Optional.empty();
    }

    @Override
    public void setComments(@NotNull String path, @Nullable List<String> comments) {
        configuration.setComments(path, comments);
    }

    @Override
    public void setInlineComments(@NotNull String path, @Nullable List<String> comments) {
        configuration.setInlineComments(path, comments);
    }

    public FileConfiguration getConfiguration() {
        return configuration;
    }

    protected void setConfiguration(FileConfiguration configuration) {
        this.configuration = configuration;
    }

    public abstract void initialize();
    protected abstract FileConfiguration load(File file);
    protected abstract void save(File file);
    public abstract FileConfiguration load();
    public abstract void save();
}
