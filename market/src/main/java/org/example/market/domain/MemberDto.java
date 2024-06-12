package org.example.market.domain;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link Member}
 */
@Data
public class MemberDto implements Serializable {
    private String username;
    private String password;

    public Member toEntity(String role){
        return Member.builder().username(username).password(password).role(role).build();
    }
}