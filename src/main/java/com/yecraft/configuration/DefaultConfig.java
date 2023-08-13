package com.yecraft.configuration;

import java.util.List;
import java.util.Map;

public interface DefaultConfig {
    boolean checkVersion();
    Map<String, Object> defaults();
    Map<String, List<String>> defaultComments();
}
