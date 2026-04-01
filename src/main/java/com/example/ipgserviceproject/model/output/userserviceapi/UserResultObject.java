package com.example.ipgserviceproject.model.output.userserviceapi;
import com.example.ipgserviceproject.model.output.MetaData;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserResultObject {
    @Embedded
    private UserDtoGetAndPost user;
    @Embedded
    private MetaData metaData;

}
