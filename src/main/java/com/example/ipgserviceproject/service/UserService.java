package com.example.ipgserviceproject.service;

import com.example.ipgserviceproject.model.output.userserviceapi.UserResultObject;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    Boolean nationalIdValid(long nationalId);
    UserResultObject getUserInfo(long nationalId);
}
