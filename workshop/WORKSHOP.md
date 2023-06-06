# Java Security Workshop

## Required software
- Java 11 or higher
- Maven installed
- Decent IDE (preferably IntelliJ Community or Ultimate latest version)
- unrestricted access to your work machine
- A Github account with unrestricted access
- Docker desktop installed

## Before we start

- Fork this repository to your own GitHub account
- Check out the forked repository
```
git clone https://github.com/<your_username>/<forked-repo>.git 
```
- Sign up for a free Snyk account at https://snyk.io/signup (unless you already have one)
- Connect the forked project to your Snyk projects and leave it there.

## Run the application
- Go to the root folder of the application and run using Maven
```
mvn spring-boot:run
```
<!-- - If you run from you IDE, please set this JVM-parameter : `-Dcom.sun.jndi.ldap.object.trustURLCodebase=true` -->
- The application fills itself with data at startup wait until you see `READY` in the console.
- You can access the application on http://localhost:8081
- By default there are two users configured you can access

| Username | Password | User type |
|----------|----------|-----------|
| Admin    | admin    | ADMIN     |
| User     | user     | CUSTOMER  |

---
# Assignments 

Before trying to exploit the application and/or fix vulnerabilities, play around in the app and make yourself familiar with the features.
Essentially it is an application to order coffee's and beer's if you have an account.

- [Assignment - FREELOADER](freeloader/assignment.md)
- [Snyk Tools](tools/snyktools.md)
- [Assignment - OVERWRITE](overwrite/assignment.md)
- [Assignment - I SPY](ispy/assignment.md)
- [Assignment - TAKE A COOKIE](takeacookie/assignment.md)
- [Assignment - YAML GADGET](yamlgadget/assignment.md)
<!-- - [Assignment - LOG4SHELL](log4shell/assignment.md) -->
- [Assignment - SHIP IT](shipit/assignment.md)
- [Assignment - BUILD-A-BOM](buildabom/assignment.md)
- [Assignment - ACTIONS](actions/assignment.md)
- [Assignment - HARDENING](hardening/assignment.md)














