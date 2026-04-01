package com.example.ipgserviceproject.model.output;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Status {
    private int statusCode;
    private String message;
}
