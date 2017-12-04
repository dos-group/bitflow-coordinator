# Usage of swagger code generation

It is possbible to generate the projeect online under
* http://editor2.swagger.io/#!/

However that is not recommended as one has no control over package name, artifact- and group-id.
So what we should use is the swagger-cli which can be downloaded here
* https://mvnrepository.com/artifact/io.swagger/swagger-codegen-cli/2.2.3

Once downloaded use it as follows:

```shell
java -jar .\swagger-codegen-cli-2.2.3.jar generate -i swagger.json --group-id cit.bitflow.backend --artifact-id bitflow-backend-api --artifact-version 1.0.0 --api-package de.cit.backend.api --model-package de.cit.backend.api.model -l jaxrs-resteasy -o bitflow-backend-api
```

* i specifies the swagger schema-file
* o specifies the output directory

In order to run the server with jetty (like described in the generated README file) you have to remove the scope-attribute from 'javax.validation' in the pom file

Analog to this, we can generate the agent API by running:

```shell
java -jar .\swagger-codegen-cli-2.2.3.jar generate -i swagger-agent.json --group-id cit.bitflow.backend --artifact-id bitflow-backend-agent-api --artifact-version 1.0.0 --api-package de.cit.backend.agent.api --model-package de.cit.backend.agent.api.model -l java -o bitflow-backend-agent-api
```

You can type

```shell
java -jar .\swagger-codegen-cli-2.2.3.jar help generate
```

to explore more options.

## Modifications to the generated backend-API

After generating all code for the backend-API we have to modify it, to access the backend-mgmt module.

### Add Dependencies
First add the following dependencies to the bitflow-backend-apis pom file (inside the dependencies-tag):


```shell
<dependency>
    <groupId>cit.bitflow.backend</groupId>
    <artifactId>bitflow-backend-mgmt</artifactId>
    <version>1.0.0</version>
</dependency>
<dependency>
    <groupId>javax</groupId>
    <artifactId>javaee-api</artifactId>
    <version>7.0</version>
    <scope>provided</scope>
</dependency>
```

### Include Mgmt-EJBs
After that we can include the mgmt-module. The only classes to be modified should be these inside src/main/java/de/cit/backend/api/impl.
This package should include classes named like 'UserApiServiceImpl'. Since the mgmt-module is using EJB-API, we will inject the business logic using a lookup.

Implement a Constructor to the ApiServiceImpl which looks like the following:

```java
    protected IUserService userService;
	
    public UserApiServiceImpl() {
        Context ctx;
        try {
            ctx = new InitialContext();
            userService = (IUserService)ctx.lookup("java:module/UserService");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
```

The corresponding EJB inside the mgmt-module then should be defnied like this:

```java
@Stateless
@Local(IUserService.class)
public class UserService implements IUserService {

```
and its interface

```java
@Local
public interface IUserService {
```




