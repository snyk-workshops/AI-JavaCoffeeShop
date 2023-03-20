# Solution Fix for YAMLGADGET Assignment

The problem is that Snakeyaml `Constructor` by default excepts all available Objects instead of narrowing it down. This can be misused to do arbitrary code execution using classes available in the classpath. 
More real life examples are mentioned in the blog post marked in [hint2](hint2.md)

This is solved in version 2.0 where the default Constructor extends the `SafeConstructor`

If we want to update the version of SnakeYaml without changing the rest we have a couple of options.

### Option 1

The dependencies are defined in the `spring-boot-dependencies` pom file that come intro your application via the `spring-boot-starter-parent`
If you go to this main dependencies pom file you will see that all version of the dependencies used are defined as a property.

You can simple overwrite the property in you top-level pom file

```xml
<properties>
    ...
    <snakeyaml.version>2.0</snakeyaml.version>
    ...
</properties>


```

### Option 2

You can also choose to use the dependency management block in your pom file to substitute for a newer version

```xml
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.yaml</groupId>
                <artifactId>snakeyaml</artifactId>
                <version>2.0</version>
            </dependency>
        </dependencies>
    </dependencyManagement>
```

## Note

This is a braking change in the library, so you also need to do some work in the code to make it all work again.
This however is outside the scope of this assignment. 
