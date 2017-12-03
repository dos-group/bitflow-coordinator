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




