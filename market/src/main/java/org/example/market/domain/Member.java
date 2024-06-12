package org.example.market.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long id;
    private String username;
    private String password;
    private String role; // ROLE_USER, ROLE_ADMIN 등

    @Builder
    public Member(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
