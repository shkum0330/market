package org.example.market.domain.dto;

import lombok.Data;
import org.example.market.domain.Member;

import java.io.Serializable;

/**
 * DTO for {@link Member}
 */
@Data
public class RegisterRequest implements Serializable {
    private String username;
    private String password;

    public Member toEntity(String role){
        return Member.builder().username(username).password(password).role(role).build();
    }
}