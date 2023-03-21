# Solution Fix for BUILD_A_BOM Assignment

Copy the plugin like in you existing `<build><plugins>` part of your Maven pom.
Every time you call `mvn package` a CycloneDX-sbom file will be created in the `target` directory of your project.

```xml


    <build>
        <finalName>JavaCoffeeShop</finalName>
        <plugins>
            ...
            <plugin>
                <groupId>org.cyclonedx</groupId>
                <artifactId>cyclonedx-maven-plugin</artifactId>
                <version>2.7.1</version>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>makeAggregateBom</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <projectType>library</projectType>
                    <schemaVersion>1.4</schemaVersion>
                    <includeBomSerialNumber>true</includeBomSerialNumber>
                    <includeCompileScope>true</includeCompileScope>
                    <includeProvidedScope>true</includeProvidedScope>
                    <includeRuntimeScope>true</includeRuntimeScope>
                    <includeSystemScope>true</includeSystemScope>
                    <includeTestScope>false</includeTestScope>
                    <includeLicenseText>false</includeLicenseText>
                    <outputReactorProjects>true</outputReactorProjects>
                    <outputFormat>all</outputFormat>
                    <outputName>CycloneDX-Sbom</outputName>
                </configuration>
            </plugin>
            ...
        <plugins>
    <build>
```

### Testing an SBOM

Test the SBOM you just created at https://snyk.io/code-checker/sbom-security/ to see what vulnerabilities are in the system.

