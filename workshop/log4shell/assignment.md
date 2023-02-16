# Assignment - LOG4SHELL

The application contains a vulnerable Log4j version. It evaluates JNDI commands and you can connect to an LDAP server that you own to create arbitrary code execution.

The log4shell server (`/tools/log4shellserver`) already contains an incomplete LDAP server based on the [marschalsec](https://github.com/mbechler/marshalsec/blob/master/src/main/java/marshalsec/jndi/LDAPRefServer.java) project by [Moritz Bechler](https://github.com/mbechler). 

This server gives a reference to Evil.class which is served to you using an HTTP server. Both the LDAP server and the HTTP are fully functional.

## Part 1
check if we are vulnerable

- Startup the JavaCoffeeShop application using `mvn spring-boot:run`
- go to http://localhost:8081/
- try to login with an incorrect username and password.
- See in the console what is logged
- Check if we use a log4j version that is vulnerable (<2.15)
    - [Hint1](hint1.md)
- Startup the log4shellserver and try to connect to to the server `mvn compile exec:java`
    - [Hint2](hint2.md)
- If the console gives you a `javax.naming.NamingException` with `Root exception is java.lang.ClassCastException`, than you are on the correct spot.

## Part 2
Implement class Evil so it will print “YOU ARE HACKED” when we load it using the LDAP string from before.

- [Hint 3](hint3.md)
- [Hint 4](hint4.md)
- [Hint 5](hint5.md)


## Part 3
Try to execute a command.
For instance open the calculator on your machine.

- [Hint 6](hint6.md)
- [Hint 7](hint7.md)
- [Solution](solution.md)

[WORKSHOP OVERVIEW](../WORKSHOP.MD)