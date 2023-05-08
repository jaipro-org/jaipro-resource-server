package com.bindord.jaipro.resourceserver.domain.mail.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
public class SendMailDto {


    @NotEmpty
    @Size(max = 128)
    private String subject;

    @NotEmpty
    @Size(max = 2000)
    private String body;

    private Set<String> receivers;

    public SendMailDto() {
    }
}

