package com.example.ipgserviceproject.model.output;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "result")
public class ResultObject {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonIgnore
    @Column(insertable = false, updatable = false)
    private UUID transactionId;
    @Embedded
    private MetaData metaData;
    @JsonIgnore
    private final String service = "ipg";
    @CreationTimestamp
    @JsonIgnore
    private Timestamp requestedAt;
}
