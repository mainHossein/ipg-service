package com.example.ipgserviceproject.model.output.userserviceapi;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.sql.Date;
import java.util.Objects;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UserDtoGetAndPost {
    private Long nationalId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Date birthDate;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        UserDtoGetAndPost userDTOGetAndPost = (UserDtoGetAndPost) o;
        return Objects.equals(nationalId, userDTOGetAndPost.nationalId) && Objects.equals(firstName, userDTOGetAndPost.firstName) && Objects.equals(lastName, userDTOGetAndPost.lastName) && Objects.equals(email, userDTOGetAndPost.email) && Objects.equals(phoneNumber, userDTOGetAndPost.phoneNumber) && Objects.equals(birthDate, userDTOGetAndPost.birthDate);
    }

    @Override
    public int hashCode() {
        int result = Long.hashCode(nationalId);
        result = 31 * result + Objects.hashCode(firstName);
        result = 31 * result + Objects.hashCode(lastName);
        result = 31 * result + Objects.hashCode(email);
        result = 31 * result + Objects.hashCode(phoneNumber);
        result = 31 * result + Objects.hashCode(birthDate);
        return result;
    }
}
