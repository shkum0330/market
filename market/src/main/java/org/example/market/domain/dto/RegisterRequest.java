package org.example.market.domain.dto;

import lombok.Data;
import org.example.market.domain.Member;

import java.io.Serializable;

import static org.example.market.domain.Member.*;

/**
 * DTO for {@link Member}
 */
@Data
public class RegisterRequest implements Serializable {
    private String username;
    private String password;
    private Role role;

    public Member toEntity(){
        return builder().username(username).password(password).role(role).build();
    }
}