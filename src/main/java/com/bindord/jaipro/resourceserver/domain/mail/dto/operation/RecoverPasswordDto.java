package com.bindord.jaipro.resourceserver.domain.mail.dto.operation;

import com.bindord.jaipro.resourceserver.domain.user.dto.UserRecoverDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RecoverPasswordDto extends UserRecoverDto {

    private String emailReceiver;

}
