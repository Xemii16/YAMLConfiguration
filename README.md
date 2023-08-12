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
  <version>2.0.3-alpha</version>
</dependency>
```
### Gradle
```
implementation 'com.yecraft:yaml-configuration:2.0.3-alpha'
```
## Use with Bukkit API
If you are using the Bukkit API dependency, add the following setting to your pom.xml
```
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
                <version>3.3.0</version>
                <executions>
                    <execution>
                        <id>shade</id>
                        <phase>package</phase>
                        <goals>
                            <goal>shade</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <relocations>
                        <relocation>
                            <pattern>org.bukkit</pattern>
                            <shadedPattern>com.example.libraries.bukkit.configuration</shadedPattern>
                        </relocation>
                    </relocations>
                </configuration>
            </plugin>
        </plugins>
    </build>
```

## Basic config
```java
class BasicConfig {
    
}
```
