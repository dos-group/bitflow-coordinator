# bitflow-backend-agent-api

## Requirements

Building the API client library requires [Maven](https://maven.apache.org/) to be installed.

## Installation

To install the API client library to your local Maven repository, simply execute:

```shell
mvn install
```

To deploy it to a remote Maven repository instead, configure the settings of the repository and execute:

```shell
mvn deploy
```

Refer to the [official documentation](https://maven.apache.org/plugins/maven-deploy-plugin/usage.html) for more information.

### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
    <groupId>cit.bitflow.backend</groupId>
    <artifactId>bitflow-backend-agent-api</artifactId>
    <version>1.0.0</version>
    <scope>compile</scope>
</dependency>
```

### Gradle users

Add this dependency to your project's build file:

```groovy
compile "cit.bitflow.backend:bitflow-backend-agent-api:1.0.0"
```

### Others

At first generate the JAR by executing:

    mvn package

Then manually install the following JARs:

* target/bitflow-backend-agent-api-1.0.0.jar
* target/lib/*.jar

## Getting Started

Please follow the [installation](#installation) instruction and execute the following Java code:

```java

import de.cit.backend.agent.*;
import de.cit.backend.agent.auth.*;
import io.swagger.client.model.*;
import de.cit.backend.agent.api.InfosApi;

import java.io.File;
import java.util.*;

public class InfosApiExample {

    public static void main(String[] args) {
        
        InfosApi apiInstance = new InfosApi();
        try {
            List<Capability> result = apiInstance.capabilitiesGet();
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling InfosApi#capabilitiesGet");
            e.printStackTrace();
        }
    }
}

```

## Documentation for API Endpoints

All URIs are relative to *http://tba/v2*

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*InfosApi* | [**capabilitiesGet**](docs/InfosApi.md#capabilitiesGet) | **GET** /capabilities | 
*InfosApi* | [**infoGet**](docs/InfosApi.md#infoGet) | **GET** /info | 
*InfosApi* | [**pingGet**](docs/InfosApi.md#pingGet) | **GET** /ping | 
*PipelineApi* | [**pipelineIdDelete**](docs/PipelineApi.md#pipelineIdDelete) | **DELETE** /pipeline/{id} | 
*PipelineApi* | [**pipelineIdGet**](docs/PipelineApi.md#pipelineIdGet) | **GET** /pipeline/{id} | 
*PipelineApi* | [**pipelineIdOutGet**](docs/PipelineApi.md#pipelineIdOutGet) | **GET** /pipeline/{id}/out | 
*PipelineApi* | [**pipelinePost**](docs/PipelineApi.md#pipelinePost) | **POST** /pipeline | 
*PipelineApi* | [**pipelinesGet**](docs/PipelineApi.md#pipelinesGet) | **GET** /pipelines | 
*PipelineApi* | [**runningGet**](docs/PipelineApi.md#runningGet) | **GET** /running | 


## Documentation for Models

 - [Capability](docs/Capability.md)
 - [Info](docs/Info.md)
 - [PipelineResponse](docs/PipelineResponse.md)
 - [Tag](docs/Tag.md)


## Documentation for Authorization

All endpoints do not require authorization.
Authentication schemes defined for the API:

## Recommendation

It's recommended to create an instance of `ApiClient` per thread in a multithreaded environment to avoid any potential issues.

## Author

sven.carlin@campus.tu-berlin.de

