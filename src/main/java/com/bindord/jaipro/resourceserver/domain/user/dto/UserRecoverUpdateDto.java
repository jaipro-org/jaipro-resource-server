package com.bindord.jaipro.resourceserver.domain.user.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.util.UUID;

@Setter
@Getter
public class UserRecoverUpdateDto {

    @Id
    @Column(value = "user_recover_id")
    private UUID id;

    private UUID userId;

    private Boolean flagRecover;

    public UserRecoverUpdateDto() {
    }
}
