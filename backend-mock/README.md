# Bitflow Mock Server

## Available operations

- post /login
- get /info
- get /users
- get /user/{id}
- post /users
- put /user/{id}
- get /projects
- get /project/{id}
- post /projects
- delete /project/{id}
- get /pipelines
- get /pipeline/{id}
- post /pipelines
- put /pipeline/{id}
- delete /pipeline/{id}
- get /pipelinesteps
- get /pipelinestep/{id}

When starting the server, an info mock object and three pipeline steps are created automatically.

Everything else can be posted.

You can find JSON examples for posts in /examples.json