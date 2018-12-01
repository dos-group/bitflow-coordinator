# InfosApi

All URIs are relative to *http://tba/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**capabilitiesGet**](InfosApi.md#capabilitiesGet) | **GET** /capabilities | 
[**infoGet**](InfosApi.md#infoGet) | **GET** /info | 
[**pingGet**](InfosApi.md#pingGet) | **GET** /ping | 


<a name="capabilitiesGet"></a>
# **capabilitiesGet**
> List&lt;Capability&gt; capabilitiesGet()



### Example
```java
// Import classes:
//import de.cit.backend.agent.ApiException;
//import de.cit.backend.agent.api.InfosApi;


InfosApi apiInstance = new InfosApi();
try {
    List<Capability> result = apiInstance.capabilitiesGet();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling InfosApi#capabilitiesGet");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;Capability&gt;**](Capability.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json, application/xml
 - **Accept**: application/json

<a name="infoGet"></a>
# **infoGet**
> Info infoGet()



### Example
```java
// Import classes:
//import de.cit.backend.agent.ApiException;
//import de.cit.backend.agent.api.InfosApi;


InfosApi apiInstance = new InfosApi();
try {
    Info result = apiInstance.infoGet();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling InfosApi#infoGet");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**Info**](Info.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json, application/xml
 - **Accept**: application/json

<a name="pingGet"></a>
# **pingGet**
> String pingGet()



### Example
```java
// Import classes:
//import de.cit.backend.agent.ApiException;
//import de.cit.backend.agent.api.InfosApi;


InfosApi apiInstance = new InfosApi();
try {
    String result = apiInstance.pingGet();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling InfosApi#pingGet");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json, application/xml
 - **Accept**: string

