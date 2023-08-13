# YAMLConfiguration
The library is a port of Bukkit 
Configuration with additional improvements. Instead of receiving null in get methods,
Optional#empty() is received. Also added config with creation, saving and loading.
## Dependency
### Maven
```
<dependency>
  <groupId>com.yecraft</groupId>
  <artifactId>yaml-configuration</artifactId>
  <version>2.0.3-beta</version>
</dependency>
```
### Gradle
```
implementation 'com.yecraft:yaml-configuration:2.0.3-beta'
```

## Basic config

```java
package com.example.plugin.config;

import com.yecraft.configuration.Configuration;

import java.util.Map;
import java.util.List;

class BasicConfig extends Configuration {

    public MainConfiguration(String version, File file) {
        super(version, file);
    }

    @Override
    public Map<String, Object> defaults() {
        // Default values that are added after creating the config
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("redis", false);
        // Creating a section
        map.put("section", Map.of("test, example"));
        return map;
    }

    @Override
    public Map<String, List<String>> defaultComments() {
        Map<String, List<String>> map = new LinkedHashMap<>();
        // To create a comment, the specified path must be created
        map.put("redis", List.of("Whether to enable the database"));
        return map;
    }
}
```

```java
package com.example.plugin;

import com.yecraft.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Optional;

import com.example.plugin.config.BasicConfig;

class Plugin extends JavaPlugin {
    
    private Configuration configuration;
    
    public Plugin(){
        // When the version is changed,
        // the config automatically changes 
        // the default values to those specified in the class
        this.configuration = new BasicConfig("1.0", new File(getDataFolder(), "config.yml"));
    }
    @Override
    public void onEnable(){
        // The file is created if it does not exist,
        // the version is checked and if it does not match the config is updated
        // and downloaded for further actions
        configuration.initialize();
        
        Optional<String> version = configuration.getString("version");
        version.ifPresent(System.out::println);
        
        Optional<String> example = configuration.getString("section.test");
        example.ifPresent(System.out::println);
    }
}
```
