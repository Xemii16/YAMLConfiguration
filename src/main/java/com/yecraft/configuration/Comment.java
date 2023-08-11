package com.yecraft.configuration;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class Comment {

    private final List<String> comments;

    public Comment(List<String> comments) {
        this.comments = comments;
    }

    public Comment(String... comments) {
        this.comments = Arrays.asList(comments);
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull Comment of(String... comments){
        return new Comment(comments);
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull Comment of(List<String> comments){
        return new Comment(comments);
    }

    public List<String> getComments() {
        return comments;
    }
}
