# PipelineApi

All URIs are relative to *http://tba/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**pipelineIdDelete**](PipelineApi.md#pipelineIdDelete) | **DELETE** /pipeline/{id} | 
[**pipelineIdGet**](PipelineApi.md#pipelineIdGet) | **GET** /pipeline/{id} | 
[**pipelineIdOutGet**](PipelineApi.md#pipelineIdOutGet) | **GET** /pipeline/{id}/out | 
[**pipelinePost**](PipelineApi.md#pipelinePost) | **POST** /pipeline | 
[**pipelinesGet**](PipelineApi.md#pipelinesGet) | **GET** /pipelines | 
[**runningGet**](PipelineApi.md#runningGet) | **GET** /running | 


<a name="pipelineIdDelete"></a>
# **pipelineIdDelete**
> List&lt;PipelineResponse&gt; pipelineIdDelete(id)



Try to kill the given pipeline.

### Example
```java
// Import classes:
//import de.cit.backend.agent.ApiException;
//import de.cit.backend.agent.api.PipelineApi;


PipelineApi apiInstance = new PipelineApi();
Integer id = 56; // Integer | 
try {
    List<PipelineResponse> result = apiInstance.pipelineIdDelete(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PipelineApi#pipelineIdDelete");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Integer**|  |

### Return type

[**List&lt;PipelineResponse&gt;**](PipelineResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json, application/xml
 - **Accept**: application/json

<a name="pipelineIdGet"></a>
# **pipelineIdGet**
> List&lt;PipelineResponse&gt; pipelineIdGet(id)



Return a JSON formatted view of the given pipeline. The Errors property can contain hints about how the current Status of the pipeline was reached, but usually the GET /pipeline/:id/out function provides more useful insights.

### Example
```java
// Import classes:
//import de.cit.backend.agent.ApiException;
//import de.cit.backend.agent.api.PipelineApi;


PipelineApi apiInstance = new PipelineApi();
Integer id = 56; // Integer | 
try {
    List<PipelineResponse> result = apiInstance.pipelineIdGet(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PipelineApi#pipelineIdGet");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Integer**|  |

### Return type

[**List&lt;PipelineResponse&gt;**](PipelineResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json, application/xml
 - **Accept**: application/json

<a name="pipelineIdOutGet"></a>
# **pipelineIdOutGet**
> String pipelineIdOutGet(id)



Return the combined standard output and standard error of the given pipeline.

### Example
```java
// Import classes:
//import de.cit.backend.agent.ApiException;
//import de.cit.backend.agent.api.PipelineApi;


PipelineApi apiInstance = new PipelineApi();
Integer id = 56; // Integer | 
try {
    String result = apiInstance.pipelineIdOutGet(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PipelineApi#pipelineIdOutGet");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Integer**|  |

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json, application/xml
 - **Accept**: application/json

<a name="pipelinePost"></a>
# **pipelinePost**
> List&lt;PipelineResponse&gt; pipelinePost(delay)



Return a list of IDs of all currently running pipelines.

### Example
```java
// Import classes:
//import de.cit.backend.agent.ApiException;
//import de.cit.backend.agent.api.PipelineApi;


PipelineApi apiInstance = new PipelineApi();
String delay = "200ms"; // String | 
try {
    List<PipelineResponse> result = apiInstance.pipelinePost(delay);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PipelineApi#pipelinePost");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **delay** | **String**|  | [optional] [default to 200ms]

### Return type

[**List&lt;PipelineResponse&gt;**](PipelineResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json, application/xml
 - **Accept**: application/json

<a name="pipelinesGet"></a>
# **pipelinesGet**
> List&lt;Integer&gt; pipelinesGet()



Return a list of IDs of all pipelines in all states, including failed, finished and killed pipelines

### Example
```java
// Import classes:
//import de.cit.backend.agent.ApiException;
//import de.cit.backend.agent.api.PipelineApi;


PipelineApi apiInstance = new PipelineApi();
try {
    List<Integer> result = apiInstance.pipelinesGet();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PipelineApi#pipelinesGet");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**List&lt;Integer&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json, application/xml
 - **Accept**: application/json

<a name="runningGet"></a>
# **runningGet**
> List&lt;Integer&gt; runningGet()



Return a list of IDs of all currently running pipelines.

### Example
```java
// Import classes:
//import de.cit.backend.agent.ApiException;
//import de.cit.backend.agent.api.PipelineApi;


PipelineApi apiInstance = new PipelineApi();
try {
    List<Integer> result = apiInstance.runningGet();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PipelineApi#runningGet");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**List&lt;Integer&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json, application/xml
 - **Accept**: application/json

