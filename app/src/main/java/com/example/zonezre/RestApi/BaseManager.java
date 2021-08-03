package com.example.zonezre.RestApi;


import static com.example.zonezre.RestApi.BaseUrl.URL;

public class BaseManager {

    protected RestApi getRestApi()
    {
        RestApiClient restApiClient = new RestApiClient(URL);
        return restApiClient.getRestApi();
    }
}
