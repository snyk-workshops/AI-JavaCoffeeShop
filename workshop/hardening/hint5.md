# Hint 5

Update to Argon2Id with the appropriate defaults.
Note that we are already using `spring-security` via the `spring-boot-starter-security`

add bouncycastle as a dependency

```xml
<dependency>
    <groupId>org.bouncycastle</groupId>
    <artifactId>bcpkix-jdk15on</artifactId>
    <version>1.70</version>
</dependency>
```

Change the `PasswordEncoder` bean in `SecurityConfig` to

```java
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder(32,64,1,15*1024,2);
    }
```


