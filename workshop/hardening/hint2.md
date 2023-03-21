# Hint 2

Use your [Snyk tools](../tools/snyktools.md) to find out what libraries are vulnerable and how to what version you need to update.

If this is a transitive (underlying) dependency either update the property of use dependency management like below with snakeyaml.
The properties are defined in the `spring-boot-starter-parent` -> `spring-boot-dependencies` pom file.

```xml
<properties>
    ...
    <snakeyaml.version>2.0</snakeyaml.version>
    ...
</properties>


```

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
