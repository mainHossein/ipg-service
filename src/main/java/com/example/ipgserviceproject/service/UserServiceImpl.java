package com.example.ipgserviceproject.service;

import com.example.ipgserviceproject.model.output.userserviceapi.UserResultObject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class UserServiceImpl implements UserService {


    private final RestClient.Builder restClient;

    public UserServiceImpl(@Qualifier("user-service") RestClient.Builder restClient) {
        this.restClient = restClient;
    }

    @Override
    public Boolean nationalIdValid(long nationalId) {
        UserResultObject body = restClient.build().get()
                .uri("/check-national-id/" + nationalId)
                .retrieve()
                .body(UserResultObject.class);
        assert body != null;
        return body.getMetaData().getStatus().getStatusCode() == 200;
    }

    @Override
    public UserResultObject getUserInfo(long nationalId) {
        return restClient.build().get()
                .uri("/" + nationalId)
                .retrieve()
                .body(UserResultObject.class);
    }
}
