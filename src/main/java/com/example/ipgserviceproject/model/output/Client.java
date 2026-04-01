package com.example.ipgserviceproject.model.output;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Client {
    private String clientIp;
    private String httpMethod;
}
