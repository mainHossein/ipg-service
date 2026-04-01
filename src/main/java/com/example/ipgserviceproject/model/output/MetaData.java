package com.example.ipgserviceproject.model.output;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
@Embeddable
public class MetaData {
    @Transient
    private UUID requestId;
    @Embedded
    private Status status;
    @Embedded
    @JsonIgnore
    private Client client;
}

