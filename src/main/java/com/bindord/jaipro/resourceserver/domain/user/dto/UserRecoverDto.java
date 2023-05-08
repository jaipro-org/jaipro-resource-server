package com.bindord.jaipro.resourceserver.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
public class UserRecoverDto {

    private UUID id;

    private UUID userId;

    @Size(max = Byte.MAX_VALUE)
    private String verificationCode;

    private Boolean flagRecover;

    @FutureOrPresent
    private LocalDateTime limitDate;

    public UserRecoverDto() {
    }
}
